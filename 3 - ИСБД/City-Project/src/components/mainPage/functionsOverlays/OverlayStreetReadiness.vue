<template>
  <v-form v-model="valid" lazy-validation ref="form">
    <!-- Оверлей готовности улицы -->
    <v-card
        color="#F7FAFC"
    >
      <v-card-text class="font-weight-medium" style="font-size: 15pt; ">
        <div style="color: black; text-align: center; margin-bottom: 5%; font-size: 25px; line-height: 1">
          <br>Готовность одной улицы
        </div>
      </v-card-text>

      <v-card-text class="font-weight-medium" style="font-size: 15pt; ">

        <v-overflow-btn
            v-model="ChooseKvartal"
            light
            :items="Kvartals"
            name="ChooseKvartal"
            label="Выберете квартал"
            editable
            segmented
            required
            v-on:change="getListOfStreets(ChooseKvartal)"
        ></v-overflow-btn>

        <v-overflow-btn
            v-model="ChooseStreet"
            light
            :items="Streets"
            name="ChooseStreet"
            label="Выберете улицу"
            editable
            segmented
            required
            :disabled="streetChoose"
            v-on:change="getStreetReadiness(ChooseStreet)"
        ></v-overflow-btn>

        <v-text-field
            light
            label="Квартал готов на:"
            :value=this.StreetReadiness
            name="StreetReadiness"
            readonly
            :color=changeColor()
            background-color=#EDF2F7
            outlined
            style="border-radius: 10px; margin-top: 10%"
        />
      </v-card-text>

      <v-btn style="margin-left: 38%; margin-bottom: 5%"
             :color=changeColor()
             outlined
             @click="closeDialog"
      >
        Закрыть
      </v-btn>
    </v-card>
  </v-form>
</template>

<script>
import axios from "axios";

export default {
  name: "OverlayStreetReadiness",

  data: () => ({
    absolute: true,
    valid: true,
    streetChoose: true,

    StreetReadiness: 0,
    ChooseKvartal: '',
    ChooseStreet: '',

    Kvartals: [],
    Streets: [],

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
    closeDialog() {
      this.$emit('updateParent', {
        dialog: false,
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

    getListOfStreets(KvartalName) {
      this.Streets = []
      this.streetChoose = false
      let str = "/api/app/street/quarter?name=" + KvartalName
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            for (let i = 0; i < resp.data.length; i++) {
              this.Streets.push(resp.data[i].name)
            }
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getListOfStreets(KvartalName)
      })
    },

    getStreetReadiness(StreetName) {
      let str = "/api/app/percent/street?name=" + StreetName
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            if (resp.data.result != null) {
              this.StreetReadiness = resp.data.result
              this.StreetReadiness = this.StreetReadiness.toFixed(2) + "%"
            } else this.StreetReadiness = "0%"
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getStreetReadiness(StreetName)
      })
    },

    changeColor() {
      return this.primaryColor(this.$store.getters.getPrimaryColor)
    },
  },
  beforeMount() {
    this.getListOfKvartals()
    this.StreetReadiness = this.StreetReadiness + "%"
  },
}
</script>

<style scoped>

</style>