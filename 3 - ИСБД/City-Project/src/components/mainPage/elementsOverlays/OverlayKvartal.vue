<template>
  <v-form v-model="valid" lazy-validation ref="form">
    <!-- Оверлей квартала -->
    <v-card
        color="#F7FAFC"
    >
      <v-card-text class="font-weight-medium" style="font-size: 15pt; ">
        <div style="color: black; text-align: center; margin-bottom: 5%; font-size: 25px">
          <br>Создать новый квартал
        </div>
      </v-card-text>

      <v-card-text class="font-weight-medium" style="font-size: 15pt; ">

        <div style="margin-top: 5px; margin-bottom: 20px; color: black; font-weight: lighter">
          Заполните необходимые поля
        </div>

        <v-text-field
            light
            v-model="KvartalName"
            label="Название квартала"
            name="KvartalName"
            type="text"
            :rules="rules.clearFieldValid"
            color=#F58E43
            background-color=#EDF2F7
            outlined
            style="border-radius: 10px;"
        />

      </v-card-text>

      <v-btn style="margin-left: 25%; margin-bottom: 5%"
             :color=changeColor()
             outlined
             @click="submit"
      >
        <v-icon style="margin-right: 8px">mdi-plus</v-icon>
        Добавить и закрыть
      </v-btn>
    </v-card>
  </v-form>
</template>

<script>
import axios from "axios";

export default {
  name: "OverlayKvartal",

  props: {
    indexInArray: Number,
  },
  data: () => ({
    absolute: true,
    valid: true,

    KvartalName: '',

    rules: {
      clearFieldValid: [
        v => !!v || 'Поле не может быть пустым'
      ],
    },
  }),
  methods: {
    submit() {
      if (this.$refs.form.validate()) {
        let str = "/api/app/quarter/save"

        let data = {
          name: this.KvartalName,
          index: this.indexInArray,
        }
        axios.create(this.getHeader()
        ).post(str, data)
            .then(resp => {
              let data2 = {
                dialog: false
              }
              this.$emit('updateParent', {data2})
              console.log(resp.data.KvartalName)
            }).catch(err => {
          if (this.doRefresh(err.response.status)) this.submit()
        })
      }
    },

    changeColor() {
      return this.primaryColor(this.$store.getters.getPrimaryColor)
    },
  }
}
</script>

<style scoped>

</style>