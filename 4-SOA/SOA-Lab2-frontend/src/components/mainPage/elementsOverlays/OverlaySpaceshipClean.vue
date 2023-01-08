<template>
  <v-form v-model="valid" lazy-validation ref="form">
    <!-- Оверлей удаление всех солдатов с корабля -->
    <v-card
        color="#F7FAFC"
    >
      <v-card-text class="font-weight-medium" style="font-size: 15pt; ">
        <div style="color: black; text-align: center; margin-bottom: 5%; font-size: 25px; line-height: 1">
          <br>Выгрузить всех космодесантников с космического корабля
        </div>
      </v-card-text>

      <v-card-text class="font-weight-medium" style="font-size: 15pt; ">
        <v-icon left style="display: inline-block">
          {{ icons.mdiShipWheel }}
        </v-icon>
        <v-select
            v-model="ChooseSpaceship"
            light
            :items="Spaceships"
            :item-text="'name'"
            :item-value="'id'"
            :rules="rules.clearFieldValid"
            label="Выберете космический корабль"
            solo
            required
            style="border-radius: 5px; display: inline-block; width: 90%"
        ></v-select>

        <v-btn style="margin-left: 33%; margin-bottom: 2%; margin-top: 20px"
               color=#F16063
               outlined
               :loading="loadingRemove"
               @click="cleanSpaceship"
        >
          <v-icon left>
            {{ icons.mdiFormatTextWrappingOverflow }}
          </v-icon>
          Отчистить
        </v-btn>
      </v-card-text>
      <v-divider style="margin-bottom: 5%"></v-divider>

      <v-btn style="margin-left: 38%; margin-bottom: 5%"
             color=#0E1117
             outlined
             @click="closeDialog"
      >
        Закрыть
      </v-btn>
    </v-card>

    <v-alert v-if="errorFlag" type="error" dismissible style="position: absolute; right: 2%; bottom: 2%">
      {{ errorMessage }}
    </v-alert>
  </v-form>
</template>

<script>
import axios from "axios";
import {mdiFormatTextWrappingOverflow, mdiShipWheel} from "@mdi/js";

export default {
  name: "OverlaySpaceshipClean",

  data: () => ({
    icons: {
      mdiFormatTextWrappingOverflow,
      mdiShipWheel
    },
    absolute: true,
    valid: true,

    errorFlag: false,
    errorMessage: '',

    ChooseSpaceship: '',
    Spaceships: [],
    loadingRemove: false,

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

    getListOfSpaceships() {
      let str = "/starships"
      axios.create(this.getHeader(2)
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            this.Spaceships = resp.data
          })
    },

    async cleanSpaceship() {
      if (this.$refs.form.validate()) {
        this.loadingRemove = true
        let str = "/starships/" + this.ChooseSpaceship + "/unload"
        axios.create(this.getHeader(2)
        ).post(str)
            .then(resp => {
              console.log(resp.data)
            })
            .catch(err => {
              this.showError(err.response.data.message)
            })
        await new Promise(resolve => setTimeout(resolve, this.awaitTimer))
        this.loadingRemove = false
        this.$root.$refs.SearchComp.getListOfSpacemarines();
        this.closeDialog()
      }
    },

    async showError(errorMessage) {
      if (this.errorFlag === true) {
        this.errorMessage = ''
        this.errorFlag = false
        await new Promise(resolve => setTimeout(resolve, 200))
      }
      this.errorMessage = errorMessage
      this.errorFlag = true
      await new Promise(resolve => setTimeout(resolve, this.errorTimer))
      this.errorMessage = ""
      this.errorFlag = false
    }

  },
  beforeMount() {
    this.getListOfSpaceships()
  },
}
</script>

<style scoped>

</style>