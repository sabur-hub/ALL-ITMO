<template>
  <v-form v-model="valid" lazy-validation ref="form">
    <!-- Оверлей создания Spaceship -->
    <v-card color="#F7FAFC"
    >
      <v-card-text class="font-weight-medium" style="font-size: 15pt">
        <div style="color: black; text-align: center; margin-bottom: 5%; font-size: 25px; line-height: 1">
          <br>Создание космического корабля
        </div>
      </v-card-text>

      <v-card-text class="font-weight-medium" style="font-size: 15pt">
        <v-icon left style="display: inline-block">
          {{ icons.mdiFormTextbox }}
        </v-icon>
        <v-text-field
            light
            label="Название"
            v-model="name"
            name="SpaceshipName"
            type="string"
            :rules="rules.clearFieldValid"
            color=#0E1117
            background-color=#EDF2F7
            outlined
            style="border-radius: 5px; display: inline-block; width: 90%"
        />

        <v-icon left style="display: inline-block">
          {{ icons.mdiShipWheel }}
        </v-icon>
        <v-text-field
            light
            label="Флот"
            v-model="fleet"
            name="SpaceshipFleet"
            type="string"
            :rules="rules.clearFieldValid"
            color=#0E1117
            background-color=#EDF2F7
            outlined
            style="border-radius: 5px; display: inline-block; width: 90%"
        />

        <v-card-text class="font-weight-medium" style="font-size: 20px; text-align: center">
          Координаты
        </v-card-text>
        <v-row>
          <v-col>
            <v-text-field
                dense
                label="Координата X"
                v-model="coordinateX"
                type="number"
                :rules="rules.numberValid"
                color=#0E1117
                background-color=#EDF2F7
                outlined
                style="border-radius: 5px;"
            />
          </v-col>
          <v-col>
            <v-text-field
                dense
                label="Координата Y"
                v-model="coordinateY"
                type="number"
                :rules="rules.numberValid"
                color=#0E1117
                background-color=#EDF2F7
                outlined
                style="border-radius: 5px;"
            />
          </v-col>
        </v-row>
      </v-card-text>

      <v-btn style="margin-left: 25%; margin-bottom: 5%; margin-top: 10px"
             color=#0E1117
             outlined
             :loading="loadingSave"
             @click="submit"
      >
        <v-icon style="margin-right: 8px">mdi-cloud-upload</v-icon>
        Сохранить и закрыть
      </v-btn>
    </v-card>

    <v-alert v-if="errorFlag" type="error" dismissible style="position: absolute; right: 2%; bottom: 2%">
      {{ errorMessage }}
    </v-alert>
  </v-form>
</template>

<script>
import axios from "axios";
import {mdiDelete, mdiFormTextbox, mdiShipWheel} from "@mdi/js";

export default {
  name: "OverlaySSpaceshipCreate",

  data: () => ({
    icons: {
      mdiDelete,
      mdiFormTextbox,
      mdiShipWheel
    },

    errorFlag: false,
    errorMessage: '',

    loadingRemove: false,
    loadingSave: false,

    absolute: true,
    valid: true,

    name: '',
    fleet: '',
    coordinateX: '',
    coordinateY: '',

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
    async submit() {
      if (this.$refs.form.validate()) {
        this.loadingSave = true
        let str = "/starships"
        let data = {
          name: this.name,
          fleet: this.fleet,
          coordinates: {x: this.coordinateX, y: this.coordinateY}
        }
        axios.create(this.getHeader(2)
        ).post(str, data)
            .then(resp => {
              console.log(resp.data)
            })
            .catch(err => {
              this.showError(err.response.data.message)
            })
        await new Promise(resolve => setTimeout(resolve, this.awaitTimer))
        this.updateOverlay()

        this.$emit('updateParent', {
          dialog: false,
        })
        this.loadingSave = false
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
    },

    updateOverlay() {
    },
  },
  beforeMount() {
    this.updateOverlay()
  }
}
</script>

<style scoped>

</style>