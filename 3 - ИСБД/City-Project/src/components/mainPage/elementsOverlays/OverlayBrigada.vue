<template>
  <v-form v-model="valid" lazy-validation ref="form">
    <!-- Оверлей бригады -->
    <v-card
        color="#F7FAFC"
    >
      <v-card-text class="font-weight-medium" style="font-size: 15pt; ">
        <div style="color: black; text-align: center; margin-bottom: 5%; font-size: 25px; line-height: 1">
          <br>Управление строительными бригадами
        </div>
      </v-card-text>

      <v-card-text class="font-weight-medium" style="font-size: 15pt; ">
        <div style="margin-top: 5px; margin-bottom: 20px; color: black; font-weight: lighter">
          Выберете номер бригады или создайте новую
        </div>
        <v-overflow-btn
            light
            v-model="BrigadaNameList"
            :items="Brigada"
            :rules="rules.clearFieldValid"
            name="BrigadaNameList"
            required
            editable
            segmented
            v-on:change="updateElements(BrigadaNameList)"
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
            label="Количество людей в бригаде"
            v-model="BrigadaPeople"
            name="BrigadaPeople"
            type="number"
            :rules="rules.numberValid"
            :color=changeColor()
            background-color=#EDF2F7
            outlined
            style="border-radius: 10px;"
        />

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
  name: "OverlayBrigada",

  data: () => ({
    icons: {
      mdiDelete,
    },
    loadingRemove: false,
    loadingSave: false,

    absolute: true,
    valid: true,
    removeButton: true,

    BrigadaNameList: '',
    BrigadaPeople: '',

    Brigada: ['Добавить новый элемент'],

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
        let str = "/api/app/construction_crew/save"
        let data = {
          size: this.BrigadaPeople,
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

    updateElements(BrigadaNameList) {
      if (BrigadaNameList !== this.Brigada[0]) {
        BrigadaNameList = BrigadaNameList.split(" ").pop()
        this.getBrigadaByID(BrigadaNameList)
        this.removeButton = false
      } else if (BrigadaNameList === this.Brigada[0]) {
        this.BrigadaPeople = ''
        this.removeButton = true
      }
    },

    getListOfBrigada() {
      let str = "/api/app/construction_crew/all"

      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            for (let i = 0; i < resp.data.length; i++) {
              this.Brigada.push('Бригада номер: ' + resp.data[i].id)
            }
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getListOfBrigada()
      })
    },

    async removeElement() {
      this.loadingRemove = true
      let str = "/api/app/construction_crew/delete?id=" + this.BrigadaNameList.split(" ").pop()
      axios.create(
          this.getHeader()
      ).post(str)
          .then(resp => {
            console.log(resp.data)
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.removeElement()
      })
      await new Promise(resolve => setTimeout(resolve, this.awaitTimer))
      this.updateOverlay()
      this.removeButton = true
      this.loadingRemove = false
    },

    getBrigadaByID: function (BrigadaNameList) {
      let str = "/api/app/construction_crew/single?id=" + BrigadaNameList
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data.size)
            this.BrigadaPeople = resp.data.size
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getBrigadaByID(BrigadaNameList)
      })
    },

    updateOverlay() {
      this.Brigada = ['Добавить новый элемент']
      this.BrigadaNameList = this.Brigada[0]
      this.getListOfBrigada()
      this.updateElements(this.BrigadaNameList)
    },

    changeColor() {
      return this.primaryColor(this.$store.getters.getPrimaryColor)
    },
  },
  beforeMount() {
    this.updateOverlay()
  }
}
</script>

<style scoped>

</style>