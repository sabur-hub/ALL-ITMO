<template>
  <v-card :color=changeColor() height="100%" style="border-radius: 0">

    <v-card-text class="font-weight-medium" style="font-size: 30pt; ">

      <v-btn plain style="margin-top: 10%; margin-left: 8%; color: black; font-size: 40px" to="/">
        City Project
      </v-btn>
    </v-card-text>
    <v-img src="../../assets/city.jpg"
           class="ml-auto mr-auto"
           width="70%"
           height="70%"
           style="border-radius:20px; margin-top: 10%"
    />
  </v-card>
</template>

<script>
export default {
  name: "LeftPanelAuth",

  methods: {
    changeColor() {
      return this.primaryColor(this.$store.getters.getPrimaryColor)
    },
  },
  beforeMount() {
  }
}
</script>

