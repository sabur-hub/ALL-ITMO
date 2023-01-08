<template>
  <v-form v-model="valid" lazy-validation ref="form">
    <!-- Оверлей доставки -->
    <v-card
        color="#F7FAFC"
    >
      <v-card-text class="font-weight-medium" style="font-size: 15pt; ">
        <div style="color: black; text-align: center; margin-bottom: 5%; font-size: 25px; line-height: 1">
          <br>Управление командами доставки
        </div>
      </v-card-text>

      <v-card-text class="font-weight-medium" style="font-size: 15pt; ">
        <div style="margin-top: 5px; margin-bottom: 20px; color: black; font-weight: lighter">
          Выберете элемент или создайте новый
        </div>
        <v-overflow-btn
            light
            v-model="DostavkaNameList"
            :items="Dostavka"
            :rules="rules.clearFieldValid"
            name="DostavkaNameList"
            color=#F58E43
            required
            editable
            segmented
            v-on:change="updateElements(DostavkaNameList)"
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
            v-model="DostavkaName"
            label="Название"
            name="DostavkaName"
            type="text"
            :rules="rules.clearFieldValid"
            :color=changeColor()
            background-color=#EDF2F7
            outlined
            style="border-radius: 10px;"
        />

        <v-text-field
            light
            v-model="DostavkaPrice"
            label="Тариф за доставку"
            name="DostavkaPrice"
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
            v-model="DostavkaMaterial"
            :items="Materials"
            :rules="rules.clearFieldValid"
            name="DostavkaMaterial"
            label="Выберете стройматериал"
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
  name: "OverlayDostavka",

  data: () => ({
    icons: {
      mdiDelete,
    },
    loadingRemove: false,
    loadingSave: false,

    absolute: true,
    valid: true,
    removeButton: true,

    DostavkaNameList: '',
    DostavkaName: '',
    DostavkaPrice: '',
    DostavkaMaterial: '',

    Materials: [],
    Dostavka: ['Добавить новый элемент'],

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
        if (this.DostavkaNameList !== this.Dostavka[0]) {
          str = "/api/app/delivery_service/update"
        } else {
          str = "/api/app/delivery_service/save"
        }
        let data = {
          name: this.DostavkaName,
          rate: this.DostavkaPrice,
          materialType: this.DostavkaMaterial,
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

    updateElements(DostavkaNameList) {
      if (this.DostavkaNameList !== this.Dostavka[0]) {
        this.removeButton = false
        this.getDostavkaByName(DostavkaNameList)
      } else if (this.DostavkaNameList === this.Dostavka[0]) {
        this.DostavkaName = ''
        this.DostavkaPrice = ''
        this.DostavkaMaterial = ''
        this.removeButton = true
      }
    },

    getListOfDostavka() {
      let str = "/api/app/delivery_service/all"
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            for (let i = 0; i < resp.data.length; i++) {
              this.Dostavka.push(resp.data[i].name)
            }
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getListOfDostavka()
      })
    },

    getDostavkaByName: function (DostavkaNameList) {
      let str = "/api/app/delivery_service/single?name=" + DostavkaNameList
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            this.DostavkaName = resp.data.name
            this.DostavkaPrice = resp.data.rate
            this.DostavkaMaterial = resp.data.materialByMaterialId.type
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getDostavkaByName(DostavkaNameList)
      })
    },

    getListOfMaterial() {
      let str = "/api/app/material/all"
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            for (let i = 0; i < resp.data.length; i++) {
              this.Materials.push(resp.data[i].type)
            }
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getListOfMaterial()
      })
    },

    async removeElement() {
      this.loadingRemove = true
      let str = "/api/app/delivery_service/delete?name=" + this.DostavkaNameList
      axios.create(this.getHeader()
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

    updateOverlay() {
      this.Dostavka = ['Добавить новый элемент']
      this.getListOfDostavka()
      this.getListOfMaterial()
      this.DostavkaNameList = this.Dostavka[0]
      this.updateElements(this.DostavkaNameList)
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