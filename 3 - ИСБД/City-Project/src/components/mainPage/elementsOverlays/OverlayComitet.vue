<template>
  <v-form v-model="valid" lazy-validation ref="form">
    <!-- Оверлей комитета -->
    <v-card
        color="#F7FAFC"
    >
      <v-card-text class="font-weight-medium" style="font-size: 15pt; ">
        <div style="color: black; text-align: center; margin-bottom: 5%; font-size: 25px; line-height: 1">
          <br>Управление комитетами сдачи объектов
        </div>
      </v-card-text>

      <v-card-text class="font-weight-medium" style="font-size: 15pt; ">
        <div style="margin-top: 5px; margin-bottom: 20px; color: black; font-weight: lighter">
          Выберете элемент или создайте новый
        </div>
        <v-overflow-btn
            light
            v-model="ComitetNameList"
            :items="Comitets"
            :rules="rules.clearFieldValid"
            name="ComitetNameList"
            required
            editable
            segmented
            v-on:change="updateElements(ComitetNameList)"
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
            label="Строгость принятия объектов (от 0 до 9)"
            v-model="ComitetRigor"
            name="ComitetRigor"
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
  name: "OverlayComitet",

  data: () => ({
    icons: {
      mdiDelete,
    },
    loadingRemove: false,
    loadingSave: false,

    absolute: true,
    valid: true,
    removeButton: true,

    ComitetNameList: '',
    ComitetRigor: '',
    Comitets: ['Добавить новый элемент'],

    rules: {
      clearFieldValid: [
        v => !!v || 'Поле не может быть пустым'
      ],
      numberValid: [
        v => !!v || 'Поле не может быть пустым',
        v => !!/^\d*$/.test(v) || 'Допустимы только числа',
        v => !!/^[0-9]$/.test(v) || 'Допустимы только числа в диапазоне 0 - 9',
      ],
    },
  }),
  methods: {
    async submit() {
      if (this.$refs.form.validate()) {
        this.loadingSave = true
        let str = "/api/app/committee/save"
        let data = {
          strictness: this.ComitetRigor,
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

    updateElements(ComitetNameList) {
      if (this.ComitetNameList !== this.Comitets[0]) {
        ComitetNameList = ComitetNameList.split(" ").pop()
        this.getComitetByID(ComitetNameList)
        this.removeButton = false
      } else if (this.ComitetNameList === this.Comitets[0]) {
        this.ComitetRigor = ''
        this.removeButton = true
      }
    },

    getListOfComitets() {
      let str = "/api/app/committee/all"
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            for (let i = 0; i < resp.data.length; i++) {
              this.Comitets.push('Комитет номер: ' + resp.data[i].id)
            }
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getListOfComitets()
      })
    },

    getComitetByID: function (ComitetNameList) {
      let str = "/api/app/committee/single?id=" + ComitetNameList
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            this.ComitetRigor = resp.data.strictness
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getComitetByID(ComitetNameList)
      })
    },

    async removeElement() {
      this.loadingRemove = true
      let str = "/api/app/committee/delete?id=" + this.ComitetNameList.split(" ").pop()
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
      this.Comitets = ['Добавить новый элемент']
      this.getListOfComitets()
      this.ComitetNameList = this.Comitets[0]
      this.updateElements(this.ComitetNameList)
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