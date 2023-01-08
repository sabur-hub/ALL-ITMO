<template>
  <v-row>
    <v-col md="4" >
      <LeftPanelAuth/>
    </v-col>
    <v-col>
      <RegisterForm/>
    </v-col>
  </v-row>
</template>

<script>
import LeftPanelAuth from "../components/login/LeftPanelAuth";
import RegisterForm from "@/components/login/RegisterForm";

export default {
  name: "Register",
  components: {RegisterForm, LeftPanelAuth}
}
</script>

<style>
::-webkit-scrollbar {
  width: 0;
}
</style>