<template>
  <v-form v-model="valid" lazy-validation ref="form">
    <!-- Оверлей принятия завершенных объектов -->
    <v-card
        color="#F7FAFC"
    >
      <v-card-text class="font-weight-medium" style="font-size: 15pt; ">
        <div style="color: black; text-align: center; margin-bottom: 5%; font-size: 25px; line-height: 1">
          <br>Выбранный комитет принимает все завершенные здания
        </div>
      </v-card-text>

      <v-card-text class="font-weight-medium" style="font-size: 15pt; ">
        <v-overflow-btn
            v-model="ChooseComitetForBuilding"
            light
            :items="Comitets"
            name="ChooseComitetForBuilding"
            label="Выберете комитет"
            editable
            segmented
            required
        ></v-overflow-btn>

        <v-btn style="margin-left: 26%; margin-bottom: 5%"
               :color=changeColor()
               outlined
               :loading="loading"
               @click="getNumberOfDoneBuildings(ChooseComitetForBuilding)"
        >
          Принять все здания
        </v-btn>

        <v-divider style="margin-bottom: 30px"></v-divider>

        <v-text-field
            light
            label="Зданий завершено:"
            :value=this.BuildingsFinished
            name="BuildingsFinished"
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
  name: "OverlayFinishBuilding",

  data: () => ({
    absolute: true,
    valid: true,
    loading: false,

    ChooseComitetForBuilding: '',
    BuildingsFinished: 0,
    Comitets: [],

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

    getListOfComitets() {
      let str = "/api/app/committee/all"
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            for (let i = 0; i < resp.data.length; i++) {
              this.Comitets.push(resp.data[i].id)
            }
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getListOfComitets()
      })
    },

    async getNumberOfDoneBuildings(ChooseComitetForBuilding) {
      this.loading = true
      let result
      let str = "/api/app/committee/accept?id=" + ChooseComitetForBuilding
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            result = resp.data
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getNumberOfDoneBuildings(ChooseComitetForBuilding)
      })
      await new Promise(resolve => setTimeout(resolve, this.awaitTimer))
      this.BuildingsFinished = result
      this.loading = false
    },

    changeColor() {
      return this.primaryColor(this.$store.getters.getPrimaryColor)
    },
  },
  beforeMount() {
    this.getListOfComitets()
  },
}
</script>

<style scoped>

</style>