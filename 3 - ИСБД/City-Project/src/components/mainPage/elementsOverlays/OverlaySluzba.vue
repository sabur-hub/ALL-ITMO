<template>
  <v-form v-model="valid" lazy-validation ref="form">
    <!-- Оверлей службы -->
    <v-card
        color="#F7FAFC"
    >
      <v-card-text class="font-weight-medium" style="font-size: 15pt; ">
        <div style="color: black; text-align: center; margin-bottom: 5%; font-size: 25px; line-height: 1">
          <br>Управление городскими службами
        </div>
      </v-card-text>

      <v-card-text class="font-weight-medium" style="font-size: 15pt; ">
        <div style="margin-top: 5px; margin-bottom: 20px; color: black; font-weight: lighter">
          Выберете элемент или создайте новый
        </div>
        <v-overflow-btn
            light
            v-model="SluzbaNameList"
            :items="Sluzba"
            :rules="rules.clearFieldValid"
            name="SluzbaName"
            required
            editable
            segmented
            v-on:change="updateElements(SluzbaNameList)"
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
            v-model="SluzbaType"
            label="Тип службы"
            name="SluzbaType"
            type="text"
            :rules="rules.clearFieldValid"
            :color=changeColor()
            background-color=#EDF2F7
            outlined
            style="border-radius: 10px;"
        />

        <v-text-field
            light
            v-model="SluzbaPrice"
            label="Цена"
            name="SluzbaPrice"
            type="number"
            step=0.01
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
  name: "OverlaySluzba",

  data: () => ({
    icons: {
      mdiDelete,
    },
    loadingRemove: false,
    loadingSave: false,

    absolute: true,
    valid: true,
    removeButton: true,

    SluzbaNameList: '',
    SluzbaType: '',
    SluzbaPrice: '',
    Sluzba: ['Добавить новый элемент'],

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
        let str = "/api/app/city_service/save"

        let data = {
          type: this.SluzbaType,
          price: this.SluzbaPrice,
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

    updateElements(SluzbaNameList) {
      if (this.SluzbaNameList !== this.Sluzba[0]) {
        this.removeButton = false
        this.getSluzbaByType(SluzbaNameList)
      } else if (this.SluzbaNameList === this.Sluzba[0]) {
        this.removeButton = true
        this.SluzbaType = ''
        this.SluzbaPrice = ''
      }
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

    getSluzbaByType: function (SluzbaNameList) {
      let str = "/api/app/city_service/single?type=" + SluzbaNameList
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            this.SluzbaType = resp.data.type
            this.SluzbaPrice = resp.data.price
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getSluzbaByType(SluzbaNameList)
      })
    },

    async removeElement() {
      this.loadingRemove = true
      let str = "/api/app/city_service/delete?type=" + this.SluzbaNameList
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
      this.Sluzba = ['Добавить новый элемент']
      this.getListOfSluzba()
      this.SluzbaNameList = this.Sluzba[0]
      this.updateElements(this.SluzbaNameList)
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