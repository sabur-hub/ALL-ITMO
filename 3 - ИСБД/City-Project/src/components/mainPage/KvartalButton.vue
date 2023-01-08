<template>
  <div>
    <v-dialog width="500px" v-model="dialog" v-if="this.KvartalName == null">
      <template v-slot:activator="{ on, attrs }">
        <v-btn dark :color=changeColor() class="mx-auto" outlined @click="openWind='OverlayKvartal'"
               v-bind="attrs" v-on="on"
               style="border-radius: 10px; width: 180px; height: 180px;">
          <v-icon dark>
            mdi-plus
          </v-icon>
        </v-btn>
      </template>

      <OverlayKvartal :indexInArray="count" v-if="openWind === 'OverlayKvartal'" @updateParent="updateDialog"/>
    </v-dialog>


    <v-dialog width="500px" v-model="dialog" v-if="this.KvartalName != null">
      <template v-slot:activator="{ on, attrs }">
        <v-btn dark color=#A0AEC0 class="mx-auto" outlined @click="openWind='OverlayKvartalInfo'"
               v-bind="attrs" v-on="on" x-large
               style="border-radius: 10px; box-shadow: none !important; width: 180px; height: 180px; color: black; ">
          {{ KvartalName }}
        </v-btn>
      </template>

      <OverlayKvartalInfo :indexInArray="count" :KvartalNameDone="KvartalName"
                          v-if="openWind === 'OverlayKvartalInfo'"
                          @updateParent="updateDialog"/>
    </v-dialog>
  </div>
</template>

<script>
import OverlayKvartal from "./elementsOverlays/OverlayKvartal";
import OverlayKvartalInfo from "@/components/mainPage/elementsOverlays/OverlayKvartalInfo";

export default {
  name: "KvartalButton",
  components: {OverlayKvartal, OverlayKvartalInfo},

  props: {
    count: Number,
    KvartalName: String,
  },
  data: () => ({
    dialog: false,
    openWind: '',
    flag: false,
  }),
  methods: {
    updateDialog(data) {
      this.dialog = data.dialog
      this.$emit('updateParent')
    },

    changeColor() {
      return this.primaryColor(this.$store.getters.getPrimaryColor)
    },
  },
  beforeMount() {
  }
}
</script>

<style scoped>

</style>