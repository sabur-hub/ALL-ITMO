<template>
  <v-form v-model="valid" lazy-validation ref="form">
    <!-- Оверлей готовности квартала -->
    <v-card
        color="#F7FAFC"
    >
      <v-card-text class="font-weight-medium" style="font-size: 15pt; ">
        <div style="color: black; text-align: center; margin-bottom: 5%; font-size: 25px; line-height: 1">
          <br>Готовность одного квартала
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
            v-on:change="getKvartalReadiness(ChooseKvartal)"

        ></v-overflow-btn>

        <v-text-field
            light
            label="Квартал готов на:"
            :value=this.KvartalReadiness
            name="KvartalReadiness"
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
  name: "OverlayKvartalReadiness",

  data: () => ({
    absolute: true,
    valid: true,

    KvartalReadiness: 0,
    ChooseKvartal: '',
    Kvartals: [],

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

    getKvartalReadiness(KvartalName) {
      let str = "/api/app/percent/quarter?name=" + KvartalName
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            if (resp.data.result != null) {
              this.KvartalReadiness = resp.data.result
              this.KvartalReadiness = this.KvartalReadiness.toFixed(2) + "%"
            } else this.KvartalReadiness = "0%"
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getKvartalReadiness(KvartalName)
      })
    },

    changeColor() {
      return this.primaryColor(this.$store.getters.getPrimaryColor)
    },
  },
  beforeMount() {
    this.getListOfKvartals()
    this.KvartalReadiness = this.KvartalReadiness + "%"
  },
}
</script>

<style scoped>

</style>