<template>
  <v-form v-model="valid" lazy-validation ref="form">
    <!-- Оверлей для подсчета цены здания -->
    <v-card
        color="#F7FAFC"
    >
      <v-card-text class="font-weight-medium" style="font-size: 15pt; ">
        <div style="color: black; text-align: center; margin-bottom: 5%; font-size: 25px; line-height: 1">
          <br>Посчитать стоимость материалов и подключения служб для здания
        </div>
      </v-card-text>

      <v-card-text class="font-weight-medium" style="font-size: 15pt; ">
        <v-overflow-btn
            v-model="ChooseKvartalForBuilding"
            light
            :items="Kvartals"
            name="ChooseKvartalForBuilding"
            label="Выберете квартал"
            editable
            segmented
            required
            v-on:change="getListOfStreets(ChooseKvartalForBuilding)"
        ></v-overflow-btn>

        <v-overflow-btn
            v-model="ChooseStreetForBuilding"
            light
            :items="Streets"
            name="ChooseStreetForBuilding"
            label="Выберете улицу"
            editable
            segmented
            required
            :disabled="streetChoose"
            v-on:change="getListOfBuildings(ChooseStreetForBuilding)"
        ></v-overflow-btn>

        <v-overflow-btn
            v-model="ChooseBuilding"
            light
            :items="Buildings"
            name="ChooseBuilding"
            label="Выберете здание"
            required
            editable
            segmented
            :disabled="buildingChoose"
            v-on:change="getFinalCost(ChooseBuilding)"
        ></v-overflow-btn>

        <v-divider style="margin-bottom: 30px"></v-divider>

        <v-text-field
            light
            label="Итоговая стоимость"
            :value=this.FinalCost
            name="FinalCost"
            readonly
            :color=changeColor()
            background-color=#EDF2F7
            outlined
            style="border-radius: 10px;"
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
  name: "OverlayCostCount",

  data: () => ({
    absolute: true,
    valid: true,
    streetChoose: true,
    buildingChoose: true,

    ChooseKvartalForBuilding: '',
    ChooseStreetForBuilding: '',
    ChooseBuilding: '',
    FinalCost: '',

    Kvartals: [],
    Streets: [],
    Buildings: [],

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

    getListOfBuildings(ChooseStreetForBuilding) {
      this.Buildings = []
      this.buildingChoose = false
      let str = "/api/app/building/street?name=" + ChooseStreetForBuilding
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            for (let i = 0; i < resp.data.length; i++) {
              this.Buildings.push(resp.data[i].name)
            }
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getListOfBuildings(ChooseStreetForBuilding)
      })
    },

    getFinalCost(BuildingName) {
      let str = "/api/app/building/cost?name=" + BuildingName
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            this.FinalCost = resp.data
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getFinalCost(BuildingName)
      })
    },

    changeColor() {
      return this.primaryColor(this.$store.getters.getPrimaryColor)
    },
  },
  beforeMount() {
    this.getListOfKvartals()
  },
}
</script>

<style scoped>

</style>