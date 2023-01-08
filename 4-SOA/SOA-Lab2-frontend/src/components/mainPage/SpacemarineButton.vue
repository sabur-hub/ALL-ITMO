<template>
  <div>
    <v-dialog width="500px" v-model="dialog" v-if="this.SpacemarineName != null">
      <template v-slot:activator="{ on, attrs }">
        <v-btn dark color=#A0AEC0 class="mx-auto" outlined @click="openWind='OverlaySpacemarine'"
               v-bind="attrs" v-on="on" x-large
               style="border-radius: 5px; box-shadow: none !important; width: 1170px; height: 100px; color: black; margin-bottom: 2%">

          <div style="position: absolute; left: 2%">
            {{ SpacemarineName }}
          </div>
          <div style="position: absolute; left: 15%; font-size: 14px">
            Здоровье: {{ health }}
          </div>
          <div style="position: absolute; left: 30%; font-size: 14px">
            Категория: {{ category }}
          </div>
          <div style="position: absolute; right: 2%; font-size: 14px">`
            {{ creationDate }}
          </div>
        </v-btn>
      </template>

      <OverlaySpacemarine :spacemarineIndex="index" v-if="openWind === 'OverlaySpacemarine'"
                          @updateParent="updateDialog"/>
    </v-dialog>
  </div>
</template>

<script>
import OverlaySpacemarine from "@/components/mainPage/elementsOverlays/OverlaySpacemarine";

export default {
  name: "SpacemarineButton",
  components: {OverlaySpacemarine},

  props: {
    index: Number,
    SpacemarineName: String,
    health: Number,
    category: String,
    weaponType: String,
    meleeWeapon: String,
    creationDate: String,
    starShipId: Number
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
  },
  beforeMount() {
  }
}
</script>

<style scoped>

</style>