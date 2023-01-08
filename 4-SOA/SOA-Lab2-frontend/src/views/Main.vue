<template>
  <v-row >
    <v-col cols="2">
      <LeftNavigationBar/>
    </v-col>
    <v-col cols="max">
      <SearchComponent/>
    </v-col>
  </v-row>
</template>

<script>
import LeftNavigationBar from "@/components/mainPage/LeftNavigationBar";
import SearchComponent from "@/components/mainPage/SearchComponent";
export default {
  name: "Main",
  components: {LeftNavigationBar, SearchComponent}
}
</script>

<style>
::-webkit-scrollbar {
  width: 0;
}
</style>