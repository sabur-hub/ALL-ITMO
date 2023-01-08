<template>
  <v-form v-model="valid" lazy-validation ref="form">
    <div>
      <!-- Оверлей здания -->
      <v-card
          color="#F7FAFC"
      >
        <v-card-text class="font-weight-medium" style="font-size: 15pt; ">
          <div v-if="isChangeable===false" style="color: black; text-align: center; margin-bottom: 5%; font-size: 25px">
            <br>Создать новое здание
          </div>

          <div v-if="isChangeable===true" style="color: black; text-align: center; margin-bottom: 5%; font-size: 25px">
            <br>Информация о существующем здании
          </div>

          <div v-if="isChangeable===false"
               style="margin-top: 5px; margin-bottom: 20px; color: black; font-weight: lighter">
            Заполните необходимые поля
          </div>

          <div v-if="isChangeable===true"
               style="margin-top: 5px; margin-bottom: 20px; color: black; font-weight: lighter">
            При необходимости вы можете изменить поля
          </div>

          <v-text-field
              light
              v-model="BuildingName"
              label="Название"
              placeholder="Введите новое название"
              name="BuildingName"
              type="text"
              :rules="rules.clearFieldValid"
              :color=changeColor()
              background-color=#EDF2F7
              outlined
              style="border-radius: 10px;"
          />

          <v-text-field
              v-model="BuildingType"
              light
              label="Тип"
              placeholder="Введите новый тип"
              name="BuildingType"
              type="text"
              :rules="rules.clearFieldValid"
              :color=changeColor()
              background-color=#EDF2F7
              outlined
              style="border-radius: 10px;"
          />

          <v-text-field
              light
              v-model="BuildingFloors"
              label="Этажность"
              placeholder="Введите новое значение этажности"
              name="BuildingFloors"
              type="number"
              :color=changeColor()
              background-color=#EDF2F7
              outlined
              :rules="rules.numberValid"
              style="border-radius: 10px;"
          />

          <v-text-field
              light
              v-model="readinessCoefficient"
              label="Коэффициент готовности"
              placeholder="Введите новое значение коэффициента"
              name="readinessCoefficient"
              type="number"
              :color=changeColor()
              background-color=#EDF2F7
              outlined
              :rules="rules.numberValid"
              style="border-radius: 10px;"
          />

          <v-btn v-if="this.isChangeable === false" style="margin-left: 20%; position: absolute;"
                 :color=changeColor()
                 outlined
                 @click="overlayMaterialForBuilding = !overlayMaterialForBuilding"
          >
            Добавить стройматериал
          </v-btn>

          <v-overflow-btn
              v-model="BuildingStreet"
              light
              :items="Streets"
              :rules="rules.clearFieldValid"
              name="BuildingStreet"
              :color=changeColor()
              label="Выберете улицу"
              required
              editable
              segmented
              style="margin-top: 50px"
          ></v-overflow-btn>

          <v-overflow-btn
              light
              v-model="BuildingComitet"
              :items="Comitets"
              :rules="rules.clearFieldValid"
              name="BuildingComitet"
              :color=changeColor()
              label="Выберете комитет"
              required
              editable
              segmented
          ></v-overflow-btn>

          <v-overflow-btn
              light
              v-model="BuildingBrigada"
              :items="Brigada"
              :rules="rules.clearFieldValid"
              :color=changeColor()
              name="BuildingBrigada"
              label="Выберете бригаду"
              required
              editable
              segmented
          ></v-overflow-btn>

          <v-overflow-btn
              v-model="ServiceList"
              :items="Sluzba"
              label="Подключенные службы"
              multiple
              clearable
              light
              color=#F58E43
              editable
              segmented
          >
            <template v-slot:selection="{ item, index }">
              <span v-if="index === 0">{{ item }}</span>
              <span
                  v-if="index === 1"
                  class="grey--text text-caption"
              >
                  (+{{ ServiceList.length - 1 }} others)
                </span>
            </template>
          </v-overflow-btn>
        </v-card-text>

        <v-btn v-if="this.isChangeable === true" style="margin-left: 37%; margin-bottom: 5%"
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
               :color=changeColor()
               outlined
               :loading="loadingSave"
               @click="submit"
        >
          <v-icon style="margin-right: 8px">mdi-cloud-upload</v-icon>
          Сохранить и закрыть
        </v-btn>
      </v-card>

      <!-- Оверлей материала для здания -->
      <v-overlay
          :absolute="absolute"
          :value="overlayMaterialForBuilding"
      >
        <v-card
            height="400px"
            width="500px"
            style="background-color: #F7FAFC"
        >
          <v-card-text class="font-weight-medium" style="font-size: 20pt; ">

            <div style="margin-top: 5%; color: black; text-align: center">
              Добавить стройметариал для здания
            </div>
          </v-card-text>

          <v-card-text class="font-weight-medium" style="font-size: 15pt; ">

            <div style="margin-top: 5px; margin-bottom: 20px; color: black; font-weight: lighter">
              Заполните необходимые поля
            </div>

            <v-overflow-btn
                light
                v-model="BuildingMaterial"
                :items="Materials"
                :rules="rules.clearFieldValid"
                name="BuildingMaterial"
                :color=changeColor()
                label="Выберете стройматериал"
                editable
                segmented
                required
            ></v-overflow-btn>

            <v-text-field
                light
                v-model="BuildingMaterialCount"
                label="Количество стройматериала"
                name="BuildingMaterialCount"
                type="number"
                :rules="rules.numberValid"
                :color=changeColor()
                background-color=#EDF2F7
                outlined
                style="border-radius: 10px; margin-top: 20px"
            />
          </v-card-text>

          <v-btn style="margin-left: 25%; position: absolute; bottom: 5%"
                 :color=changeColor()
                 outlined
                 @click="addMaterial"
          >
            Добавить стройматериал
          </v-btn>
        </v-card>
      </v-overlay>
    </div>
  </v-form>
</template>

<script>
import axios from "axios";
import {mdiDelete} from "@mdi/js";

export default {
  name: "OverlayBuilding",

  props: {
    isChangeable: Boolean,
    KvartalNameDone: String,
    BuildingNameDone: String,
  },
  data: () => ({
    icons: {
      mdiDelete,
    },
    loadingRemove: false,
    loadingSave: false,
    overlayMaterialForBuilding: false,
    valid: true,
    absolute: true,

    BuildingName: '',
    BuildingType: '',
    BuildingFloors: '',
    BuildingStreet: '',
    BuildingComitet: '',
    BuildingBrigada: '',

    BuildingMaterial: '',
    BuildingMaterialCount: '',

    MaterialsList: [],
    QuantityList: [],
    Streets: [],
    Comitets: [],
    Brigada: [],
    Materials: [],
    Sluzba: [],
    ServiceList: [],
    readinessCoefficient: '',

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
        let str
        if (this.isChangeable === true) {
          str = "/api/app/building/update"
        } else {
          str = "/api/app/building/save"
        }
        let data = {
          name: this.BuildingName,
          type: this.BuildingType,
          floorNumber: this.BuildingFloors,
          streetName: this.BuildingStreet,
          committeeId: this.BuildingComitet.split(" ").pop(),
          crewId: this.BuildingBrigada.split(" ").pop(),
          materialList: this.MaterialsList,
          quantityList: this.QuantityList,
          serviceList: this.ServiceList,
          readinessCoefficient: this.readinessCoefficient
        }
        axios.create(this.getHeader()
        ).post(str, data)
            .then(resp => {
              console.log(resp.data.BuildingName)
            }).catch(err => {
          if (this.doRefresh(err.response.status)) this.submit()
        })
        await new Promise(resolve => setTimeout(resolve, this.awaitTimer))
        let data2 = {
          dialog: false
        }
        this.$emit('updateParent', {data2})
        this.loadingSave = false
      }
    },

    addMaterial() {
      if (this.BuildingMaterial !== '') {
        this.MaterialsList.push(this.BuildingMaterial)
        this.QuantityList.push(this.BuildingMaterialCount)
      }
      this.overlayMaterialForBuilding = false
    },

    getBuildingByName(BuildingNameDone) {
      let str = "/api/app/building/single?name=" + BuildingNameDone
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            this.BuildingType = resp.data.type
            this.BuildingFloors = resp.data.floorNumber
            this.BuildingStreet = resp.data.streetByStreetId.name
            this.BuildingComitet = resp.data.committeeId
            this.BuildingBrigada = resp.data.constructionCrewByCrewId.id
            this.MaterialsList = resp.data.materialList
            this.QuantityList = resp.data.quantityList
            this.ServiceList = resp.data.serviceList
            this.readinessCoefficient = resp.data.readinessCoefficient
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getBuildingByName(BuildingNameDone)
      })
    },

    getListOfStreets(KvartalNameDone) {
      let str = "/api/app/street/quarter?name=" + KvartalNameDone
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            for (let i = 0; i < resp.data.length; i++) {
              this.Streets.push(resp.data[i].name)
            }
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getListOfStreets(KvartalNameDone)
      })
    },

    getListOfBrigada() {
      let str = "/api/app/construction_crew/all"
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            for (let i = 0; i < resp.data.length; i++) {
              this.Brigada.push('Номер бригады: ' + resp.data[i].id)
            }
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getListOfBrigada()
      })
    },

    getListOfComitets() {
      let str = "/api/app/committee/all"
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            for (let i = 0; i < resp.data.length; i++) {
              this.Comitets.push('Номер комитета: ' + resp.data[i].id)
            }
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getListOfComitets()
      })
    },

    getListOfSluzba(KvartalName) {
      let str = "/api/app/city_service/quarter?name=" + KvartalName
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            for (let i = 0; i < resp.data.length; i++) {
              this.Sluzba.push(resp.data[i].type)
            }
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getListOfSluzba(KvartalName)
      })
    },

    getListOfMaterials() {
      let str = "/api/app/material/all"
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            for (let i = 0; i < resp.data.length; i++) {
              this.Materials.push(resp.data[i].type)
            }
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.getListOfMaterials()
      })
    },

    async removeElement() {
      this.loadingRemove = true
      let str = "/api/app/building/delete?name=" + this.BuildingNameDone

      axios.create(this.getHeader()
      ).post(str)
          .then(resp => {
            console.log(resp.data)
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.removeElement()
      })
      await new Promise(resolve => setTimeout(resolve, this.awaitTimer))

      let data2 = {
        dialog: false
      }
      this.$emit('updateParent', {data2})
      this.loadingRemove = false
    },

    changeColor() {
      return this.primaryColor(this.$store.getters.getPrimaryColor)
    },
  },
  beforeMount() {
    if (this.isChangeable === true) {
      this.BuildingName = this.BuildingNameDone
      this.getBuildingByName(this.BuildingNameDone)
    }
    this.getListOfStreets(this.KvartalNameDone)
    this.getListOfBrigada()
    this.getListOfComitets()
    this.getListOfMaterials()
    this.getListOfSluzba(this.KvartalNameDone)
  },
}
</script>

<style scoped>

</style>