<template>
  <v-form v-model="valid" lazy-validation ref="form">
    <div>
      <!-- Оверлей информации о квартале -->
      <v-card
          color="#F7FAFC"
      >
        <v-card-text class="font-weight-medium" style="font-size: 15pt; ">
          <div style="color: black; text-align: center; margin-bottom: 10%; font-size: 25px">
            <br>{{ this.KvartalNameDone }}
          </div>

          <v-text-field
              light
              v-model="KvartalName"
              placeholder="Введите новое название"
              label="Название квартала"
              name="KvartalName"
              type="text"
              :rules="rules.clearFieldValid"
              color=#F58E43
              background-color=#EDF2F7
              outlined
              style="border-radius: 10px;"
          />

          <v-overflow-btn
              v-model="ChooseStreetForBuilding"
              light
              :items="Streets"
              name="ChooseStreetForBuilding"
              color=#F58E43
              label="Выберете улицу"
              required
              editable
              segmented
              v-on:change="updateListOfBuildings(ChooseStreetForBuilding)"
          ></v-overflow-btn>

          <v-overflow-btn
              v-model="ChooseBuilding"
              light
              :items="Buildings"
              name="ChooseBuilding"
              color=#F58E43
              label="Выберете здание"
              required
              editable
              segmented
              style="margin-top: 10px"
              :disabled="buildingChoose"
              v-on:change="updateButton"
          ></v-overflow-btn>

          <v-dialog width="500px" v-model="dialog">
            <template v-slot:activator="{ on, attrs }">
              <v-btn style="margin-left: 18%; margin-bottom: 5%" @click="openWind='OverlayBuilding2'"
                     v-bind="attrs" v-on="on"
                     :disabled="infoButton"
                     :color=changeColor()
                     outlined
              >
                Показать информацию о доме
              </v-btn>
            </template>
            <OverlayBuilding2 :isChangeable="true" :KvartalNameDone="this.KvartalNameDone"
                              :BuildingNameDone="this.ChooseBuilding"
                              v-if="openWind === 'OverlayBuilding2'"
                              @updateParent="updateDialog"/>
          </v-dialog>
          <v-divider style="margin-bottom: 5%"></v-divider>

          <v-row style="margin-left: 1%; margin-right: 1%; margin-bottom: 3%">
            <v-col>
              <v-dialog width="500px" v-model="dialog">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn style=""
                         :color=changeColor()
                         outlined
                         @click="openWind='OverlayBuilding'"
                         v-bind="attrs" v-on="on"
                         small
                  >
                    <v-icon dark>
                      mdi-plus
                    </v-icon>
                    Добавить здание
                  </v-btn>
                </template>

                <OverlayBuilding :KvartalNameDone="this.KvartalNameDone" :isChangeable="false"
                                 v-if="openWind === 'OverlayBuilding'"
                                 @updateParent="updateDialog"/>
              </v-dialog>
            </v-col>

            <v-col>
              <v-dialog width="500px" v-model="dialog">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn style=""
                         :color=changeColor()
                         outlined
                         @click="openWind='OverlayStreet'"
                         v-bind="attrs" v-on="on"
                         small
                  >
                    <v-icon left>
                      mdi-pencil
                    </v-icon>
                    Управление улицами
                  </v-btn>
                </template>
                <OverlayStreet :KvartalNameDone="this.KvartalNameDone" v-if="openWind === 'OverlayStreet'"
                               @updateParent="updateDialog"/>
              </v-dialog>
            </v-col>
          </v-row>
          <v-divider style="margin-bottom: 5%"></v-divider>
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
               :color=changeColor()
               outlined
               :loading="loadingSave"
               @click="submit"
        >
          <v-icon style="margin-right: 8px">mdi-cloud-upload</v-icon>
          Сохранить и закрыть
        </v-btn>
      </v-card>
    </div>
  </v-form>
</template>

<script>
import axios from "axios";
import OverlayBuilding from "@/components/mainPage/elementsOverlays/OverlayBuilding";
import OverlayStreet from "@/components/mainPage/elementsOverlays/OverlayStreet";
import OverlayBuilding2 from "@/components/mainPage/elementsOverlays/OverlayBuilding";
import {mdiDelete,} from '@mdi/js'

export default {
  name: "OverlayKvartalInfo",
  components: {OverlayBuilding, OverlayStreet, OverlayBuilding2},

  props: {
    indexInArray: Number,
    KvartalNameDone: String,
  },
  data: () => ({
    icons: {
      mdiDelete,
    },
    loadingRemove: false,
    loadingSave: false,

    valid: true,
    dialog: false,
    openWind: '',
    infoButton: true,
    buildingChoose: true,

    absolute: true,

    KvartalName: '',
    KvartalNameOld: '',
    ChooseStreetForBuilding: '',
    ChooseBuilding: '',

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
    updateDialog(data) {
      this.dialog = data.dialog
    },

    async submit() {
      if (this.$refs.form.validate()) {
        this.loadingSave = true
        let str = "/api/app/quarter/update"


        let data = {
          name: this.KvartalName,
          oldName: this.KvartalNameOld,
          index: this.indexInArray
        }
        axios.create(this.getHeader()
        ).post(str, data)
            .then(resp => {
              console.log(resp.data)
            }).catch(err => {
          if (this.doRefresh(err.response.status)) this.submit()
        })
        await new Promise(resolve => setTimeout(resolve, this.awaitTimer))

        this.$store.commit('changeElement', {index: this.indexInArray, newName: this.KvartalName})
        let data2 = {
          dialog: false,
        }
        this.$emit('updateParent', {data2})
        this.loadingSave = false
      }
    },

    getListOfStreets(KvartalName) {
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

    updateListOfBuildings(ChooseStreetForBuilding) {
      this.buildingChoose = false
      this.Buildings = []
      let str = "/api/app/building/street?name=" + ChooseStreetForBuilding
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            console.log(resp.data)
            for (let i = 0; i < resp.data.length; i++) {
              this.Buildings.push(resp.data[i].name)
            }
          }).catch(err => {
        if (this.doRefresh(err.response.status)) this.updateListOfBuildings(ChooseStreetForBuilding)
      })
    },

    updateButton() {
      this.infoButton = this.ChooseBuilding == null;
    },

    async removeElement() {
      this.loadingRemove = true
      let str = "/api/app/quarter/delete?name=" + this.KvartalNameDone

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
    this.KvartalName = this.KvartalNameDone
    this.KvartalNameOld = this.KvartalNameDone
    this.getListOfStreets(this.KvartalNameDone)
  },
}
</script>

<style scoped>

</style>