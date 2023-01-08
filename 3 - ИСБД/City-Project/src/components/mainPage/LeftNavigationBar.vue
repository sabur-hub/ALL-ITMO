<template>
  <div style="height: 100%">
    <v-card
        class="mx-0"
        height="100%"
    >
      <v-navigation-drawer
          class=EDF2F7
          width="100%"
          permanent
      >

        <v-btn height="100px" plain
               style="margin-top: 20%; color: black; font-size: 40px; font-weight: bold; text-align: center; margin-left: 45px"
               @click="updatePage">
          <pre>City<br>Project</pre>
        </v-btn>

        <div style="text-align: center; font-size: 20px; margin-top: 5%;">
          {{ this.login }}
        </div>

        <div style="margin-top: 15%; margin-left: 30px; margin-right: 20px">
          <v-divider></v-divider>
          <v-switch
              v-model="modeSwitch"
              inset
              :label="modeSwitch ? 'Элементы' : 'Функции'"
              :color=changeColor()
              style="margin-left: 14%"
          ></v-switch>


          <v-dialog width="500px" v-model="dialog" v-if="modeSwitch === true">
            <template v-slot:activator="{ on, attrs }">

              <div>
                <div style="margin-top: 5%; color: black; font-size: 18px; text-align: left;  margin-bottom: 15px">
                  Создание и редактирование объектов:
                </div>

                <v-btn @click="openWind='OverlayBrigada'" v-bind="attrs" v-on="on" width="95%"
                       class="navigationButton" outlined>
                  Стр. бригада
                </v-btn>
                <v-btn @click="openWind='OverlayDostavka'" v-bind="attrs" v-on="on" width="95%"
                       class="navigationButton" outlined>
                  Команда доставки
                </v-btn>
                <v-btn @click="openWind='OverlaySluzba'" v-bind="attrs" v-on="on" width="95%"
                       class="navigationButton" outlined>
                  Городская служба
                </v-btn>
                <v-btn @click="openWind='OverlayObsluga'" v-bind="attrs" v-on="on" width="95%"
                       class="navigationButton" outlined>
                  Обсл. команда
                </v-btn>
                <v-btn @click="openWind='OverlayComitet'" v-bind="attrs" v-on="on" width="95%"
                       class="navigationButton" outlined>
                  Комитет сдачи
                </v-btn>
                <v-btn @click="openWind='OverlayRoute'" v-bind="attrs" v-on="on" width="95%"
                       class="navigationButton" outlined>
                  Тр. маршрут
                </v-btn>
                <v-btn @click="openWind='OverlayMaterial'" v-bind="attrs" v-on="on" width="95%"
                       class="navigationButton" outlined>
                  Стройматериал
                </v-btn>
              </div>

            </template>
            <OverlayBuilding v-if="openWind === 'overlayBuilding'" @updateParent="updateDialog"/>
            <OverlayStreet v-if="openWind === 'OverlayStreet'" @updateParent="updateDialog"/>
            <OverlayKvartal v-if="openWind === 'OverlayKvartal'" @updateParent="updateDialog"/>
            <OverlayBrigada v-if="openWind === 'OverlayBrigada'" @updateParent="updateDialog"/>
            <OverlayDostavka v-if="openWind === 'OverlayDostavka'" @updateParent="updateDialog"/>
            <OverlaySluzba v-if="openWind === 'OverlaySluzba'" @updateParent="updateDialog"/>
            <OverlayObsluga v-if="openWind === 'OverlayObsluga'" @updateParent="updateDialog"/>
            <OverlayComitet v-if="openWind === 'OverlayComitet'" @updateParent="updateDialog"/>
            <OverlayRoute v-if="openWind === 'OverlayRoute'" @updateParent="updateDialog"/>
            <OverlayMaterial v-if="openWind === 'OverlayMaterial'" @updateParent="updateDialog"/>
          </v-dialog>

          <v-dialog width="500px" v-model="dialog" v-if="modeSwitch === false">
            <template v-slot:activator="{ on, attrs }">
              <div>
                <div
                    style="margin-top: 5%; color: black; font-size: 18px; text-align: left;  margin-bottom: 15px">
                  Доступные функции:
                </div>

                <v-btn @click="openWind='OverlayCostCount'" v-bind="attrs" v-on="on" width="95%"
                       class="navigationButton" outlined>
                  Посчитать стоимость
                </v-btn>
                <v-btn @click="openWind='OverlayFinishBuilding'" v-bind="attrs" v-on="on" width="95%"
                       class="navigationButton" outlined>
                  Принять объекты
                </v-btn>
                <div style="margin-top: 10%"></div>
                <v-btn @click="openWind='OverlayCityReadiness'" v-bind="attrs" v-on="on" width="95%"
                       class="navigationButton" outlined>
                  Готовность города
                </v-btn>
                <v-btn @click="openWind='OverlayKvartalReadiness'" v-bind="attrs" v-on="on" width="95%"
                       class="navigationButton" outlined>
                  Готовность квартала
                </v-btn>
                <v-btn @click="openWind='OverlayStreetReadiness'" v-bind="attrs" v-on="on" width="95%"
                       class="navigationButton" outlined>
                  Готовность улицы
                </v-btn>
              </div>
            </template>
            <OverlayCostCount v-if="openWind === 'OverlayCostCount'" @updateParent="updateDialog"/>
            <OverlayFinishBuilding v-if="openWind === 'OverlayFinishBuilding'" @updateParent="updateDialog"/>
            <OverlayCityReadiness v-if="openWind === 'OverlayCityReadiness'" @updateParent="updateDialog"/>
            <OverlayKvartalReadiness v-if="openWind === 'OverlayKvartalReadiness'" @updateParent="updateDialog"/>
            <OverlayStreetReadiness v-if="openWind === 'OverlayStreetReadiness'" @updateParent="updateDialog"/>
          </v-dialog>
          <v-divider style="margin-top: 5%"></v-divider>
        </div>

        <template v-slot:append>
          <v-slider style="width: 200px; margin-left: 17%"
              :color=changeColor()
              v-model="colorSlider"
              max="4"
              min="1"
              v-on:change="switchColors(colorSlider)"
          ></v-slider>
          <v-divider style="margin-bottom: 20px"></v-divider>
          <v-row style="margin: auto">
            <v-btn dark
                   @click="logOut"
                   :color=changeColor()
                   class="mx-auto"
                   style="border-radius: 10px; box-shadow: none !important; margin-left: 5px; margin-bottom: 20px; width: 80%">
              <v-icon left>
                mdi-arrow-left
              </v-icon>
              Выйти из системы
            </v-btn>
          </v-row>
        </template>
      </v-navigation-drawer>
    </v-card>
  </div>
</template>

<script>
import OverlayBuilding from "./elementsOverlays/OverlayBuilding";
import OverlayStreet from "./elementsOverlays/OverlayStreet";
import OverlayKvartal from "./elementsOverlays/OverlayKvartal";
import OverlayBrigada from "./elementsOverlays/OverlayBrigada";
import OverlayDostavka from "./elementsOverlays/OverlayDostavka";
import OverlaySluzba from "./elementsOverlays/OverlaySluzba";
import OverlayObsluga from "./elementsOverlays/OverlayObsluga";
import OverlayRoute from "@/components/mainPage/elementsOverlays/OverlayRoute";
import OverlayComitet from "@/components/mainPage/elementsOverlays/OverlayComitet";
import OverlayCostCount from "@/components/mainPage/functionsOverlays/OverlayCostCount";
import OverlayFinishBuilding from "@/components/mainPage/functionsOverlays/OverlayFinishBuilding";
import OverlayCityReadiness from "@/components/mainPage/functionsOverlays/OverlayCityReadiness";
import OverlayKvartalReadiness from "@/components/mainPage/functionsOverlays/OverlayKvartalReadiness";
import OverlayMaterial from "@/components/mainPage/elementsOverlays/OverlayMaterial";
import OverlayStreetReadiness from "@/components/mainPage/functionsOverlays/OverlayStreetReadiness";
import router from "@/router";

export default {
  name: "LeftNavigationBar",

  components: {
    OverlayBuilding,
    OverlayStreet,
    OverlayKvartal,
    OverlayBrigada,
    OverlayDostavka,
    OverlaySluzba,
    OverlayObsluga,
    OverlayRoute,
    OverlayComitet,
    OverlayMaterial,
    OverlayCostCount,
    OverlayFinishBuilding,
    OverlayCityReadiness,
    OverlayKvartalReadiness,
    OverlayStreetReadiness,
  },
  data: () => ({
    dialog: false,
    openWind: '',
    modeSwitch: Boolean,
    login: '',
    colorSlider: '',
  }),
  methods: {
    updateDialog(data) {
      this.dialog = data.dialog
    },

    logOut() {
      localStorage.token = ''
      localStorage.refreshToken = ''
      localStorage.login = ''
      router.push({path: '/'})
    },

    updatePage() {
      this.$router.go()
    },

    changeColor() {
      return this.primaryColor(this.$store.getters.getPrimaryColor)
    },

    switchColors(colorSlider) {
      switch (colorSlider) {
        case 1:
          this.$store.commit('setPrimaryColor', 1)
          break;
        case 2:
          this.$store.commit('setPrimaryColor', 2)
          break;
        case 3:
          this.$store.commit('setPrimaryColor', 3)
          break;
        case 4:
          this.$store.commit('setPrimaryColor', 4)
          break;
      }
    }
  },
  beforeMount() {
    this.colorSlider = this.$store.getters.getPrimaryColor
    this.modeSwitch = true
    this.login = localStorage.login
  }
}
</script>

<style scoped>
.navigationButton {
  border-radius: 10px;
  margin-bottom: 15px;
  color: #A0AEC0
}
</style>