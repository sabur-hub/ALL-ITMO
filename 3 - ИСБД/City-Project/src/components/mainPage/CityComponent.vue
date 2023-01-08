<template>
  <v-container class="grey lighten-5" style="margin-top: 2%">
    <div v-show="false">
      <img src="../../assets/map1.png" alt/>
      <img src="../../assets/map2.png" alt/>
      <img src="../../assets/map3.png" alt/>
      <img src="../../assets/map4.png" alt/>
    </div>
    <v-img :src=getSrc()
           class="ml-auto mr-auto"
           height="850px"
           style="border-radius: 10px"
    >
      <v-btn disabled v-if="!renderComponent" height="100px" plain
             style="margin-top: 22%; color: black; font-size: 40px; font-weight: bold; text-align: center; margin-left: 23%"
      >
        <pre>Отсутствует покдлючение к серверу</pre>
      </v-btn>

      <v-row style="margin-left: 10px; margin-top: 10px" v-if="renderComponent">
        <v-col v-for="(n, index) in this.$store.getters.allKvartals" :key="index">
          <KvartalButton :count="index" :KvartalName="n" @updateParent="updateDialog"/>
        </v-col>
      </v-row>
    </v-img>
  </v-container>
</template>

<script>
import KvartalButton from "@/components/mainPage/KvartalButton";
import axios from "axios";

export default {
  name: "CityComponent",
  components: {KvartalButton},

  data: () => ({
    KvartalsList: [],
    slotSize: 28,
    renderComponent: false,
    mapPath: '',
  }),

  methods: {
    getListOfKvartals() {
      this.KvartalsList = new Array(28)
      let str = "/api/app/quarter/map"
      axios.create(this.getHeader()
      ).get(str)
          .then(resp => {
            for (let i = 0; i < resp.data.length; i++) {
              if (resp.data[i] != null) {
                this.KvartalsList[i] = resp.data[i].name
              } else this.KvartalsList[i] = null
            }
            this.renderComponent = true
            this.$store.commit('clearAll')
            this.$store.commit('updateKvartalsList', this.KvartalsList)
          }).catch(err => {
        this.renderComponent = false
        if (this.doRefresh(err.response.status)) this.getListOfKvartals()
      })
    },

    updateDialog() {
      this.renderComponent = false
      this.getListOfKvartals()
    },

    changeColor() {
      return this.primaryColor(this.$store.getters.getPrimaryColor)
    },

    getSrc() {
      console.log("Достаю картинку")
      let mode = this.$store.getters.getPrimaryColor
      console.log(mode)
      switch (mode) {
        case 1:
          return require('../../assets/map1.png')
        case 2:
          return require('../../assets/map2.png')
        case 3:
          return require('../../assets/map3.png')
        case 4:
          return require('../../assets/map4.png')
      }
    },
  },
  created() {
    this.getListOfKvartals()
  },
}
</script>

<style scoped>

</style>