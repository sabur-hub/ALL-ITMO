<template>
  <v-form v-model="valid" lazy-validation ref="form">
    <!-- Оверлей Spacemarine -->
    <v-card color="#F7FAFC"
    >
      <v-card-text class="font-weight-medium" style="font-size: 15pt">
        <div style="color: black; text-align: center; margin-bottom: 5%; font-size: 25px; line-height: 1">
          <br>Статистика использования оружия ближнего боя
        </div>
      </v-card-text>

      <v-card-text class="font-weight-medium" style="font-size: 15pt">
        <v-text-field
            light
            label="CHAIN_SWORD"
            v-model="CHAIN_SWORD"
            name="CHAIN_SWORD"
            type="number"
            color=#0E1117
            background-color=#EDF2F7
            outlined
            disabled
            style="border-radius: 5px"
        />

        <v-text-field
            light
            label="CHAIN_AXE"
            v-model="CHAIN_AXE"
            name="CHAIN_AXE"
            type="number"
            disabled
            color=#0E1117
            background-color=#EDF2F7
            outlined
            style="border-radius: 5px;"
        />

        <v-text-field
            light
            label="POWER_FIST"
            v-model="POWER_FIST"
            name="POWER_FIST"
            type="number"
            disabled
            color=#0E1117
            background-color=#EDF2F7
            outlined
            style="border-radius: 5px;"
        />
      </v-card-text>

      <v-btn style="margin-left: 40%; margin-bottom: 5%"
             color=#0E1117
             outlined
             :loading="loadingSave"
             @click="submit"
      >
        Закрыть
      </v-btn>
    </v-card>
  </v-form>
</template>

<script>
import axios from "axios";
import {mdiDelete} from "@mdi/js";

export default {
  name: "OverlayMeleeWeapon",
  props: {
    spacemarineIndex: Number,
  },

  data: () => ({
    icons: {
      mdiDelete,
    },

    loadingRemove: false,
    loadingSave: false,

    absolute: true,
    valid: true,

    CHAIN_SWORD: '',
    CHAIN_AXE: '',
    POWER_FIST: '',

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
      this.$emit('updateParent', {
        dialog: false,
      })
    },

    getSpacemarineByID() {
      let str = "/spacemarines/group/melee"
      axios.create(this.getHeader(1)
      ).post(str)
          .then(resp => {
            this.CHAIN_SWORD = resp.data.chain_SWORD
            this.CHAIN_AXE = resp.data.chain_AXE
            this.POWER_FIST = resp.data.power_FIST
          })
    },

    updateOverlay() {
      this.getSpacemarineByID()
    },
  },
  beforeMount() {
    this.updateOverlay()
  }
}
</script>

<style scoped>

</style>