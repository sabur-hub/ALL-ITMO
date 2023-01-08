<template>
  <v-form v-model="valid" lazy-validation ref="form">
    <!-- Оверлей материала -->
    <v-card
        color="#F7FAFC"
    >
      <v-card-text class="font-weight-medium" style="font-size: 15pt; ">
        <div style="color: black; text-align: center; margin-bottom: 5%; font-size: 25px; line-height: 1">
          <br>Управление стройматериалами
        </div>
      </v-card-text>

      <v-card-text class="font-weight-medium" style="font-size: 15pt; ">
        <div style="margin-top: 5px; margin-bottom: 20px; color: black; font-weight: lighter">
          Выберете элемент или создайте новый
        </div>
        <v-overflow-btn
            light
            v-model="MaterialNameList"
            :items="Materials"
            :rules="rules.clearFieldValid"
            name="MaterialNameList"
            required
            editable
            segmented
            v-on:change="updateElements(MaterialNameList)"
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
            v-model="MaterialType"
            label="Тип материала"
            name="MaterialType"
            type="text"
            :rules="rules.clearFieldValid"
            :color=changeColor()
            background-color=#EDF2F7
            outlined
            style="border-radius: 10px;"
        />

        <v-text-field
            light
            v-model="MaterialPrice"
            label="Цена"
            name="MaterialPrice"
            type="number"
            step=0.01
            :rules="rules.numberValid"
            :color=changeColor()
            background-color=#EDF2F7
            outlined
            style="border-radius: 10px;"
        />

        <v-text-field
            light
            v-model="MaterialInStorage"
            label="Количество на складе"
            name="MaterialInStorage"
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
  name: "OverlayMaterial",

  data: () => ({
    icons: {
      mdiDelete,
    },
    loadingRemove: false,
    loadingSave: false,

    absolute: true,
    valid: true,
    removeButton: true,

    MaterialNameList: '',
    MaterialType: '',
    MaterialPrice: '',
    MaterialInStorage: '',
    Materials: ['Добавить новый элемент'],

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
        if (this.MaterialNameList !== this.Materials[0]) {
          str = "/api/app/material/update"
        } else {
          str = "/api/app/material/save"
        }
        let data = {
          type: this.MaterialType,
          price: this.MaterialPrice,
          quantity: this.MaterialInStorage,
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

    updateElements(MaterialNameList) {
      if (this.MaterialNameList !== this.Materials[0]) {
        this.removeButton = false
        this.getMaterialByType(MaterialNameList)
      } else if (this.MaterialNameList === this.Materials[0]) {
        this.removeButton = true
        this.MaterialType = ''
        this.MaterialPrice = ''
        this.MaterialInStorage = ''
      }
    },

    getListOfMaterials() {
      let str = "/api/app/material/all"
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            for (let i = 0; i < resp.data.length; i++) {
              this.Materials.push(resp.data[i].type)
            }
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getListOfMaterials()
      })
    },

    getMaterialByType: function (MaterialNameList) {
      let str = "/api/app/material/single?type=" + MaterialNameList
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            this.MaterialType = resp.data.type
            this.MaterialPrice = resp.data.price
            this.MaterialInStorage = resp.data.quantity
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getMaterialByType(MaterialNameList)
      })
    },

    async removeElement() {
      this.loadingRemove = true
      let str = "/api/app/material/delete?type=" + this.MaterialNameList
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
      this.Materials = ['Добавить новый элемент']
      this.getListOfMaterials()
      this.MaterialNameList = this.Materials[0]
      this.updateElements(this.MaterialNameList)
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