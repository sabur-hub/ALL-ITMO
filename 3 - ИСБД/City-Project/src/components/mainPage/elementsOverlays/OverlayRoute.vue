<template>
  <v-form v-model="valid" lazy-validation ref="form">
    <!-- Оверлей маршрута -->
    <v-card
        color="#F7FAFC"
    >
      <v-card-text class="font-weight-medium" style="font-size: 15pt; ">
        <div style="color: black; text-align: center; margin-bottom: 5%; font-size: 25px; line-height: 1">
          <br>Управление транспортными маршрутами
        </div>
      </v-card-text>

      <v-card-text class="font-weight-medium" style="font-size: 15pt; ">
        <div style="margin-top: 5px; margin-bottom: 20px; color: black; font-weight: lighter">
          Выберете элемент или создайте новый
        </div>
        <v-overflow-btn
            light
            v-model="RouteNameList"
            :items="Routes"
            :rules="rules.clearFieldValid"
            name="RouteNameList"
            required
            editable
            segmented
            v-on:change="updateElements(RouteNameList)"
        ></v-overflow-btn>

        <v-btn style="margin-left: 37%; margin-bottom: 5%"
               color=#F16063
               outlined
               :loading="loadingRemove"
               :disabled="removeButton"
               @click="removeElement"
        >
          <v-icon left>
            {{ icons.mdiDelete }}
          </v-icon>
          Удалить
        </v-btn>

        <div style="margin-top: 5%; margin-bottom: 20px; color: black; font-weight: lighter">
          Заполните необходимые поля
        </div>

        <v-text-field
            light
            v-model="RouteType"
            label="Тип транпорта"
            name="SluzbaType"
            type="text"
            :rules="rules.clearFieldValid"
            :color=changeColor()
            background-color=#EDF2F7
            outlined
            style="border-radius: 10px;"
        />

        <v-overflow-btn
            light
            v-model="RouteKvartalStart"
            :items="Kvartals"
            :rules="rules.clearFieldValid"
            label="Квартал отправления"
            name="RouteKvartalStart"
            required
            editable
            segmented
        ></v-overflow-btn>

        <v-overflow-btn
            light
            v-model="RouteKvartalFinish"
            :items="Kvartals"
            label="Квартал прибытия"
            :rules="rules.clearFieldValid"
            name="RouteKvartalFinish"
            required
            editable
            segmented
        ></v-overflow-btn>

        <v-overflow-btn
            v-model="RouteStreets"
            :items="Streets"
            label="Улицы в маршруте"
            multiple
            clearable
            light
            editable
            segmented
        >
          <template v-slot:selection="{ item, index }">

            <span v-if="index === 0">{{ item }}</span>
            <span
                v-if="index === 1"
                class="grey--text text-caption"
            >
                  (+{{ RouteStreets.length - 1 }} others)
                </span>
          </template>
        </v-overflow-btn>
      </v-card-text>

      <v-btn style="margin-left: 25%; margin-bottom: 5%"
             :color=changeColor()
             outlined
             :loading="loadingSave"
             @click="submit"
      >
        <v-icon style="margin-right: 8px">mdi-cloud-upload</v-icon>
        Сохранить и закрыть
      </v-btn>
    </v-card>
  </v-form>
</template>

<script>
import axios from "axios";
import {mdiDelete} from "@mdi/js";

export default {
  name: "OverlayRoute",

  data: () => ({
    icons: {
      mdiDelete,
    },
    loadingRemove: false,
    loadingSave: false,

    absolute: true,
    valid: true,
    removeButton: true,

    RouteNameList: '',

    RouteType: '',
    RouteKvartalStart: '',
    RouteKvartalFinish: '',
    RouteStreets: [],

    Kvartals: [],
    Streets: [],
    Routes: ['Добавить новый элемент'],

    rules: {
      clearFieldValid: [
        v => !!v || 'Поле не может быть пустым'
      ],
      numberValid: [
        v => !!v || 'Поле не может быть пустым',
        v => !!/^\d*$/.test(v) || 'Допустимы только числа',
      ],
    },
  }),
  methods: {
    async submit() {
      if (this.$refs.form.validate()) {
        this.loadingSave = true
        let str
        let data
        if (this.RouteNameList !== this.Routes[0]) {
          str = "/api/app/route/update"
          data = {
            id: this.RouteNameList,
            type: this.RouteType,
            quarterFrom: this.RouteKvartalStart,
            quarterTo: this.RouteKvartalFinish,
            streets: this.RouteStreets,
          }
        } else {
          str = "/api/app/route/save"
          data = {
            type: this.RouteType,
            quarterFrom: this.RouteKvartalStart,
            quarterTo: this.RouteKvartalFinish,
            streets: this.RouteStreets,
          }
        }
        axios.create(this.getHeader()
        ).post(str, data)
            .then(resp => {
              console.log(resp.data)
            }).catch(err => {
          if (this.doRefresh(err.response.status)) this.submit()
        })
        await new Promise(resolve => setTimeout(resolve, this.awaitTimer))
        this.updateOverlay()

        let data2 = {
          dialog: false
        }
        this.$emit('updateParent', {data2})
        this.loadingSave = false
      }
    },

    updateElements(RouteNameList) {
      if (this.RouteNameList !== this.Routes[0]) {
        RouteNameList = RouteNameList.split(" ").pop()
        this.getRouteByType(RouteNameList)
        this.removeButton = false
      } else if (this.RouteNameList === this.Routes[0]) {
        this.removeButton = true
        this.RouteType = ''
        this.RouteKvartalStart = ''
        this.RouteKvartalFinish = ''
        this.RouteStreets = ''
      }
    },

    getListOfRoutes() {
      let str = "/api/app/route/all"
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            for (let i = 0; i < resp.data.length; i++) {
              this.Routes.push('Номер маршрута: ' + resp.data[i].id)
            }
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getListOfRoutes()
      })
    },

    getListOfStreets() {
      let str = "/api/app/street/all"
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            for (let i = 0; i < resp.data.length; i++) {
              this.Streets.push(resp.data[i].name)
            }
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getListOfStreets()
      })
    },

    getRouteByType: function (RouteNameList) {
      let str = "/api/app/route/single?id=" + RouteNameList
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            this.RouteType = resp.data.type
            this.RouteKvartalStart = resp.data.quarterFrom
            this.RouteKvartalFinish = resp.data.quarterTo
            this.RouteStreets = resp.data.streets
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getRouteByType(RouteNameList)
      })
    },

    getListOfKvartals() {
      let str = "/api/app/quarter/all"
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            for (let i = 0; i < resp.data.length; i++) {
              this.Kvartals.push(resp.data[i].name)
            }
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getListOfKvartals()
      })
    },

    async removeElement() {
      this.loadingRemove = true
      let str = "/api/app/route/delete?id=" + this.RouteNameList.split(" ").pop()
      axios.create(this.getHeader()
      ).post(str)
          .then(resp => {
            console.log(resp.data)
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.removeElement()
      })
      this.removeButton = true

      await new Promise(resolve => setTimeout(resolve, this.awaitTimer))
      this.updateOverlay()
      this.loadingRemove = false
    },

    updateOverlay() {
      this.Routes = ['Добавить новый элемент']
      this.getListOfStreets()
      this.getListOfKvartals()
      this.getListOfRoutes()
      this.RouteNameList = this.Routes[0]
      this.updateElements(this.RouteNameList)
    },

    changeColor() {
      return this.primaryColor(this.$store.getters.getPrimaryColor)
    },
  },
  beforeMount() {
    this.updateOverlay()
  },
}
</script>

<style scoped>

</style>