<template>
  <v-form v-model="valid" lazy-validation ref="form">
    <!-- Оверлей готовности всего города -->
    <v-card
        color="#F7FAFC"
    >
      <v-card-text class="font-weight-medium" style="font-size: 15pt; ">
        <div style="color: black; text-align: center; margin-bottom: 5%; font-size: 25px; line-height: 1">
          <br>Готовность всего города
        </div>
      </v-card-text>

      <v-card-text class="font-weight-medium" style="font-size: 15pt; ">

        <v-text-field
            light
            label="Город готов на:"
            :value=this.CityReadiness
            name="CityReadiness"
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
  name: "OverlayCityReadiness",

  data: () => ({
    absolute: true,
    valid: true,

    CityReadiness: 0,

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

    getCityReadiness() {
      let str = "/api/app/percent/city"
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            if (resp.data.result != null) {
              this.CityReadiness = resp.data.result
              this.CityReadiness = this.CityReadiness.toFixed(2) + "%"
            } else this.CityReadiness = "0%"
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getCityReadiness()
      })
    },

    changeColor() {
      return this.primaryColor(this.$store.getters.getPrimaryColor)
    },
  },
  beforeMount() {
    this.getCityReadiness()
    this.CityReadiness = this.CityReadiness + "%"
  },
}
</script>

<style scoped>

</style>