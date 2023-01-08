<template>
  <v-form v-model="valid" lazy-validation ref="form">
    <!-- Оверлей обслуживающей команды -->
    <v-card
        color="#F7FAFC"
    >
      <v-card-text class="font-weight-medium" style="font-size: 15pt; ">
        <div style="color: black; text-align: center; margin-bottom: 5%; font-size: 25px; line-height: 1">
          <br>Управление обслуживающими командами
        </div>
      </v-card-text>

      <v-card-text class="font-weight-medium" style="font-size: 15pt; ">
        <div style="margin-top: 5px; margin-bottom: 20px; color: black; font-weight: lighter">
          Выберете элемент или создайте новый
        </div>

        <v-overflow-btn
            light
            v-model="ObslugaNameList"
            :items="Obsluga"
            :rules="rules.clearFieldValid"
            name="ObslugaName"
            required
            editable
            segmented
            v-on:change="updateElements(ObslugaNameList)"
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
            v-model="ObslugaPrice"
            label="Тариф за обслуживание"
            name="ObslugaPrice"
            type="number"
            step=0.01
            :rules="rules.numberValid"
            :color=changeColor()
            background-color=#EDF2F7
            outlined
            style="border-radius: 10px;"
        />

        <v-overflow-btn
            light
            v-model="ObslugaKvartal"
            :items="Kvartals"
            :rules="rules.clearFieldValid"
            name="ObslugaKvartal"
            label="Выберете квартал"
            required
            editable
            segmented
        ></v-overflow-btn>

        <v-overflow-btn
            light
            v-model="ObslugaSluzba"
            :items="Sluzba"
            :rules="rules.clearFieldValid"
            name="ObslugaSluzba"
            label="Выберете городскую службу"
            required
            editable
            segmented
        ></v-overflow-btn>
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
  name: "OverlayObsluga",

  data: () => ({
    icons: {
      mdiDelete,
    },
    loadingRemove: false,
    loadingSave: false,

    absolute: true,
    valid: true,
    removeButton: true,

    ObslugaNameList: '',

    ObslugaPrice: '',
    ObslugaKvartal: '',
    ObslugaSluzba: '',

    Obsluga: ['Добавить новый элемент'],
    Kvartals: [],
    Sluzba: [],

    rules: {
      clearFieldValid: [
        v => !!v || 'Поле не может быть пустым'
      ],
      numberValid: [
        v => !!v || 'Поле не может быть пустым',
        v => !!/^\d*?.*\d$/.test(v) || 'Допустимы только числа',
      ],
    },
  }),
  methods: {
    async submit() {
      if (this.$refs.form.validate()) {
        this.loadingSave = true
        let str
        let data
        if (this.ObslugaNameList !== this.Obsluga[0]) {
          str = "/api/app/service_team/update"
          data = {
            id: this.ObslugaNameList,
            rate: this.ObslugaPrice,
            quarterName: this.ObslugaKvartal,
            serviceType: this.ObslugaSluzba,
          }
        } else {
          str = "/api/app/service_team/save"
          data = {
            rate: this.ObslugaPrice,
            quarterName: this.ObslugaKvartal,
            serviceType: this.ObslugaSluzba,
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

    updateElements(ObslugaNameList) {
      if (this.ObslugaNameList !== this.Obsluga[0]) {
        ObslugaNameList = ObslugaNameList.split(" ").pop()
        this.getObslugaByID(ObslugaNameList)
        this.removeButton = false
      } else if (this.ObslugaNameList === this.Obsluga[0]) {
        this.removeButton = true
        this.ObslugaPrice = ''
        this.ObslugaKvartal = ''
        this.ObslugaSluzba = ''
      }
    },

    getListOfObsluga() {
      let str = "/api/app/service_team/all"
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            for (let i = 0; i < resp.data.length; i++) {
              this.Obsluga.push('Обс. команда номер: ' + resp.data[i].id)
            }
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getListOfObsluga()
      })
    },

    getObslugaByID: function (ObslugaNameList) {
      let str = "/api/app/service_team/single?id=" + ObslugaNameList
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            this.ObslugaPrice = resp.data.rate
            this.ObslugaKvartal = resp.data.quarterByQuarterId.name
            this.ObslugaSluzba = resp.data.cityServiceByServiceId.type
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getObslugaByID(ObslugaNameList)
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

    getListOfSluzba() {
      let str = "/api/app/city_service/all"
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            for (let i = 0; i < resp.data.length; i++) {
              this.Sluzba.push(resp.data[i].type)
            }
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getListOfSluzba()
      })
    },

    async removeElement() {
      this.loadingRemove = true
      let str = "/api/app/service_team/delete?id=" + this.ObslugaNameList.split(" ").pop()
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
      this.Obsluga = ['Добавить новый элемент']
      this.getListOfKvartals()
      this.getListOfSluzba()
      this.getListOfObsluga()
      this.ObslugaNameList = this.Obsluga[0]
      this.updateElements(this.ObslugaNameList)
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