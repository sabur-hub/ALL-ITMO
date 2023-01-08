<template>
  <v-row>
    <v-col md="4" >
      <LeftPanelAuth/>
    </v-col>
    <v-col cols="max">
      <AuthForm/>
    </v-col>
  </v-row>
</template>

<script>
import LeftPanelAuth from "../components/login/LeftPanelAuth";
import AuthForm from "../components/login/AuthForm";

export default {
  name: "Auth",
  components: {AuthForm, LeftPanelAuth}
}
</script>

<style>
::-webkit-scrollbar {
  width: 0;
}
</style>