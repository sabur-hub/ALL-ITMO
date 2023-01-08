<template>
  <v-row >
    <v-col cols="2">
      <LeftNavigationBar/>
    </v-col>
    <v-col cols="max">
      <CityComponent/>
    </v-col>
  </v-row>
</template>

<script>
import LeftNavigationBar from "@/components/mainPage/LeftNavigationBar";
import CityComponent from "@/components/mainPage/CityComponent";
export default {
  name: "Main",
  components: {LeftNavigationBar, CityComponent}
}
</script>

<style>
::-webkit-scrollbar {
  width: 0;
}
</style>