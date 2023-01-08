<template>
  <div style="height: 100%">
    <v-card class="mx-0" height="100%">
      <v-navigation-drawer class=EDF2F7 width="100%" permanent>
        <v-btn height="100px" plain
               style="margin-top: 20%; color: black; font-size: 30px; font-weight: bold; text-align: center; margin-left: 1px"
               @click="updatePage">
          <pre>Лабораторная<br>работа №2</pre>
        </v-btn>

        <div style="margin-top: 15%; margin-left: 30px; margin-right: 20px">
          <v-divider></v-divider>
          <v-switch
              v-model="modeSwitch"
              inset
              :label="modeSwitch ? 'Первый сервис' : 'Второй сервис'"
              color=#0E1117
              style="margin-left: 14%"
          ></v-switch>


          <v-dialog width="500px" v-model="dialog" v-if="modeSwitch === true">
            <template v-slot:activator="{ on, attrs }">

              <div>
                <v-btn @click="openWind='OverlaySpacemarineCreate'" v-bind="attrs" v-on="on" width="95%"
                       class="navigationButton" style="color: black; border-radius: 1px; margin-top: 40px">
                  Новый солдат
                </v-btn>
                <v-btn @click="openWind='OverlayMeleeWeapon'" v-bind="attrs" v-on="on" width="95%"
                       class="navigationButton" style="color: black; border-radius: 1px">
                  Инфо по оружию
                </v-btn>
              </div>

            </template>
            <OverlaySpacemarineCreate v-if="openWind === 'OverlaySpacemarineCreate'" @updateParent="updateDialog"/>
            <OverlayMeleeWeapon v-if="openWind === 'OverlayMeleeWeapon'" @updateParent="updateDialog"/>
          </v-dialog>

          <v-dialog width="500px" v-model="dialog" v-if="modeSwitch === false">
            <template v-slot:activator="{ on, attrs }">
              <div>
                <v-btn @click="openWind='OverlaySpaceshipCreate'" v-bind="attrs" v-on="on" width="95%"
                       class="navigationButton" style="color: black; border-radius: 1px; margin-top: 40px">
                  Новый корабль
                </v-btn>
                <v-btn @click="openWind='OverlaySpaceshipClean'" v-bind="attrs" v-on="on" width="95%"
                       class="navigationButton" style="color: black; border-radius: 1px;">
                  Отчистить корабль
                </v-btn>
                <v-btn @click="openWind='OverlaySpaceshipAddMarine'" v-bind="attrs" v-on="on" width="95%"
                       class="navigationButton" style="color: black; border-radius: 1px;">
                  Загрузить на корабль
                </v-btn>
              </div>
            </template>

            <OverlaySpaceshipCreate v-if="openWind === 'OverlaySpaceshipCreate'" @updateParent="updateDialog"/>
            <OverlaySpaceshipClean v-if="openWind === 'OverlaySpaceshipClean'" @updateParent="updateDialog"/>
            <OverlaySpaceshipAddMarine v-if="openWind === 'OverlaySpaceshipAddMarine'" @updateParent="updateDialog"/>
          </v-dialog>
          <v-divider style="margin-top: 5%"></v-divider>
        </div>

        <template v-slot:append>
          <v-card-text style="margin-left: 4%; margin-bottom:20px; font-size: 18px">
            Митрофанов Егор <br> Любкин Андрей <br><br> P34101
          </v-card-text>
        </template>
      </v-navigation-drawer>
    </v-card>
  </div>
</template>

<script>
import OverlaySpacemarineCreate from "@/components/mainPage/elementsOverlays/OverlaySpacemarineCreate";
import OverlayMeleeWeapon from "@/components/mainPage/elementsOverlays/OverlayMeleeWeapon";
import OverlaySpaceshipCreate from "@/components/mainPage/elementsOverlays/OverlaySpaceshipCreate";
import OverlaySpaceshipClean from "@/components/mainPage/elementsOverlays/OverlaySpaceshipClean";
import OverlaySpaceshipAddMarine from "@/components/mainPage/elementsOverlays/OverlaySpaceshipAddMarine";

export default {
  name: "LeftNavigationBar",

  components: {
    OverlaySpacemarineCreate,
    OverlayMeleeWeapon,
    OverlaySpaceshipCreate,
    OverlaySpaceshipClean,
    OverlaySpaceshipAddMarine
  },
  data: () => ({
    dialog: false,
    openWind: '',
    modeSwitch: Boolean,
    login: '',
  }),
  methods: {
    updateDialog(data) {
      this.dialog = data.dialog
    },

    updatePage() {
      this.$router.go()
    },
  },
  beforeMount() {
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