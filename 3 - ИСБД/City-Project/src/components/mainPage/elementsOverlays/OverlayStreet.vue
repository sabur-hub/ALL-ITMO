<template>
  <v-form v-model="valid" lazy-validation ref="form">
    <!-- Оверлей улицы -->
    <v-card
        color="#F7FAFC"
    >
      <v-card-text class="font-weight-medium" style="font-size: 15pt; ">
        <div style="color: black; text-align: center; margin-bottom: 5%; font-size: 25px">
          <br>Создать новую улицу
        </div>
      </v-card-text>

      <v-card-text class="font-weight-medium" style="font-size: 15pt; ">
        <div style="margin-top: 5px; margin-bottom: 20px; color: black; font-weight: lighter">
          Выберете элемент или создайте новый
        </div>
        <v-overflow-btn
            light
            v-model="StreetNameList"
            :items="Streets"
            :rules="rules.clearFieldValid"
            name="SluzbaName"
            required
            editable
            segmented
            v-on:change="updateElements(StreetNameList)"
        ></v-overflow-btn>

        <v-btn style="margin-left: 37%; margin-bottom: 5%"
               color=#F16063
               outlined
               :disabled="removeButton"
               :loading="loadingRemove"
               @click="removeElement"
        >
          <v-icon left>
            {{ icons.mdiDelete }}
          </v-icon>
          Удалить
        </v-btn>

        <div style="margin-top: 10%; margin-bottom: 20px; color: black; font-weight: lighter">
          Заполните необходимые поля
        </div>

        <v-text-field
            light
            v-model="StreetName"
            label="Название улицы"
            name="StreetName"
            type="text"
            :rules="rules.clearFieldValid"
            :color=changeColor()
            background-color=#EDF2F7
            outlined
            style="border-radius: 10px;"
        />

        <v-overflow-btn
            v-model="StreetToStreetName"
            :items="StreetsForChoose"
            label="Пересекаемые улицы"
            multiple
            clearable
            light
            editable
            segmented
            :rules="rules.clearFieldValid"
        >
          <template v-slot:selection="{ item, index }">

            <span v-if="index === 0">{{ item }}</span>
            <span
                v-if="index === 1"
                class="grey--text text-caption"
            >
                  (+{{ StreetToStreet.length - 1 }} others)
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
  name: "OverlayStreet",

  props: {
    KvartalNameDone: String,
  },
  data: () => ({
    icons: {
      mdiDelete,
    },
    loadingRemove: false,
    loadingSave: false,

    absolute: true,
    valid: true,
    removeButton: true,

    StreetNameList: '',
    StreetName: '',
    StreetNameOld: '',
    StreetToStreetName: '',

    StreetToStreet: [],
    Streets: ['Добавить новый элемент'],
    StreetsForChoose: [],

    rules: {
      clearFieldValid: [
        v => !!v || 'Поле не может быть пустым'
      ],
      checkStreet: [
        v => v === this.StreetNameList || 'Улица не может пересекать сама себя'
      ],
    },
  }),
  methods: {
    async submit() {
      if (this.$refs.form.validate()) {
        this.loadingSave = true
        let str
        if (this.StreetNameList !== this.Streets[0]) {
          str = "/api/app/street/update"
        } else {
          str = "/api/app/street/save"
        }
        let data = {
          name: this.StreetName,
          oldName: this.StreetNameOld,
          streetList: this.StreetToStreetName,
          quarterName: this.KvartalNameDone,
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

    updateElements(StreetNameList) {
      if (this.StreetNameList !== this.Streets[0]) {
        //this.StreetsForChoose.slice(this.StreetsForChoose.indexOf(this.StreetNameList))
        this.getStreetByName(StreetNameList)
        this.removeButton = false
      } else if (this.StreetNameList === this.Streets[0]) {
        this.removeButton = true
        this.StreetName = ''
        this.StreetToStreetName = ''
      }
    },

    getStreetByName: function (StreetNameList) {
      let str = "/api/app/street/single?name=" + StreetNameList
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            this.StreetName = resp.data.name
            this.StreetNameOld = resp.data.name
            this.StreetToStreetName = resp.data.streetList
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getStreetByName(StreetNameList)
      })
    },

    getListOfStreets(KvartalName) {
      let str = "/api/app/street/quarter?name=" + KvartalName
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            for (let i = 0; i < resp.data.length; i++) {
              this.Streets.push(resp.data[i].name)
              this.StreetsForChoose.push(resp.data[i].name)
            }
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getListOfStreets(KvartalName)
      })
    },

    async removeElement() {
      this.loadingRemove = true
      let str = "/api/app/street/delete?name=" + this.StreetNameList
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
      this.Streets = ['Добавить новый элемент']
      this.KvartalName = this.KvartalNameDone
      this.StreetNameList = this.Streets[0]
      this.updateElements(this.StreetNameList)
      this.getListOfStreets(this.KvartalNameDone)
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