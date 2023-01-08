<template>
  <v-form v-model="valid" lazy-validation ref="form">
    <!-- Оверлей Spacemarine -->
    <v-card color="#F7FAFC"
    >
      <v-card-text class="font-weight-medium" style="font-size: 15pt">
        <div style="color: black; text-align: center; margin-bottom: 5%; font-size: 25px; line-height: 1">
          <br>Информация о космодесантнике
        </div>
      </v-card-text>

      <v-card-text class="font-weight-medium" style="font-size: 15pt">
        <v-icon left style="display: inline-block">
          {{ icons.mdiFormTextbox }}
        </v-icon>
        <v-text-field
            light
            label="Имя"
            v-model="name"
            name="SpacemarineName"
            type="string"
            :rules="rules.clearFieldValid"
            color=#0E1117
            background-color=#EDF2F7
            outlined
            style="border-radius: 5px; display: inline-block; width: 90%"
        />

        <v-icon left style="display: inline-block">
          {{ icons.mdiHeart }}
        </v-icon>
        <v-text-field
            light
            label="Количество здоровья"
            v-model="health"
            name="Health"
            type="number"
            :rules="rules.numberValid"
            color=#0E1117
            background-color=#EDF2F7
            outlined
            style="border-radius: 5px; display: inline-block; width: 90%"
        />

        <v-icon left style="display: inline-block">
          {{ icons.mdiHumanMale }}
        </v-icon>
        <v-select
            label="Категория"
            solo
            v-model="category"
            :items="categoryList"
            :rules="rules.clearFieldValid"
            name="Category"
            required
            editable
            style="display: inline-block; width: 90%"
        ></v-select>

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
                color=#0E1117
                background-color=#EDF2F7
                outlined
                style="border-radius: 5px; margin-bottom: 20px"
            />
          </v-col>
        </v-row>

        <v-icon left style="display: inline-block">
          {{ icons.mdiPistol }}
        </v-icon>
        <v-overflow-btn
            label="Тип оружия"
            light
            v-model="weaponType"
            :items="weaponTypeList"
            :rules="rules.clearFieldValid"
            name="WeaponType"
            required
            editable
            dense
            style="display: inline-block; width: 90%"
        ></v-overflow-btn>

        <v-icon left style="display: inline-block">
          {{ icons.mdiSwordCross }}
        </v-icon>
        <v-overflow-btn
            label="Оружие ближнего боя"
            light
            v-model="meleeWeapon"
            :items="meleeWeaponList"
            :rules="rules.clearFieldValid"
            name="MeleeWeapon"
            required
            editable
            dense
            style="display: inline-block; width: 90%"
        ></v-overflow-btn>

        <v-text-field
            dense
            disabled
            label="Космический корабль"
            v-model="starShipId"
            type="number"
            color=#0E1117
            background-color=#EDF2F7
            outlined
            style="border-radius: 5px;"
        />
      </v-card-text>

      <v-btn style="margin-left: 37%; margin-bottom: 5%"
             color=#F16063
             outlined
             :loading="loadingRemove"
             @click="removeElement"
      >
        <v-icon left>
          {{ icons.mdiDelete }}
        </v-icon>
        Удалить
      </v-btn>

      <v-btn style="margin-left: 25%; margin-bottom: 5%"
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
import {mdiDelete, mdiHeart, mdiHumanMale, mdiSwordCross, mdiPistol, mdiFormTextbox} from "@mdi/js";

export default {
  name: "OverlaySpacemarine",
  props: {
    spacemarineIndex: Number
  },

  data: () => ({
    icons: {
      mdiDelete,
      mdiHeart,
      mdiHumanMale,
      mdiSwordCross,
      mdiPistol,
      mdiFormTextbox
    },

    errorFlag: false,
    errorMessage: '',

    loadingRemove: false,
    loadingSave: false,

    absolute: true,
    valid: true,

    name: '',
    health: '',
    category: '',
    weaponType: '',
    meleeWeapon: '',
    creationDate: '',
    coordinateX: '',
    coordinateY: '',
    starShipId: '',

    categoryList: ["AGGRESSOR", "ASSAULT", "HELIX"],
    weaponTypeList: ["COMBI_FLAMER", "GRENADE_LAUNCHER", "INFERNO_PISTOL"],
    meleeWeaponList: ["CHAIN_SWORD", "CHAIN_AXE", "POWER_FIST"],

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
        let str = "/spacemarines/" + this.spacemarineIndex
        let data = {
          id: this.spacemarineIndex,
          name: this.name,
          health: this.health,
          category: this.category,
          weaponType: this.weaponType,
          meleeWeapon: this.meleeWeapon,
          coordinates: {x: this.coordinateX, y: this.coordinateY}
        }
        axios.create(this.getHeader(1)
        ).put(str, data)
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

    async removeElement() {
      this.loadingRemove = true
      let str = "/spacemarines/" + this.spacemarineIndex
      axios.create(this.getHeader(1)
      ).delete(str)
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
      this.loadingRemove = false
    },

    getSpacemarineByID: function (id) {
      let str = "/spacemarines/" + id
      axios.create(this.getHeader(1)
      ).get(str)
          .then(resp => {
            this.name = resp.data.name
            this.health = resp.data.health
            this.category = resp.data.category
            this.weaponType = resp.data.weaponType
            this.meleeWeapon = resp.data.meleeWeapon
            this.creationDate = resp.data.creationDate
            this.coordinateX = resp.data.coordinates.x
            this.coordinateY = resp.data.coordinates.y
            this.starShipId = resp.data.starShipId
          })
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
      this.getSpacemarineByID(this.spacemarineIndex)
    },
  },
  beforeMount() {
    this.updateOverlay()
  }
}
</script>

<style scoped>

</style>