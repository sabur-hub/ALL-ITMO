<template>
  <div>
    <v-card class="elevation-0" style="margin-top: 10%; margin-left: 25%">
      <v-row>
        <v-col cols="1  2" md="8">
          <v-card-text class="mt-12">
            <h1
                class="text-center display-2"
                style="color: black; font-weight: bold"
            >Добро пожаловать!</h1>

            <div class="font-center" style="font-size: 20pt;  margin: 20px 20px 50px;text-align: center">
              Введите данные для входа
            </div>

            <v-form
                style="margin-bottom: 20px"
                ref="form"
                lazy-validation>

              <div class="font-center" style="font-size: 14pt; margin-bottom: 10px; text-align: left">
                Имя пользователя
              </div>

              <v-textarea
                  :rules="rules.clearFieldValid"
                  label="Введите имя пользователя"
                  name="Login"
                  type="text"
                  color="black"
                  v-model="login"
                  auto-grow
                  outlined
                  rows="1"
                  style="border-radius: 10px"
              />

              <div class="font-center" style="font-size: 14pt; margin-bottom: 10px; text-align: left">
                Пароль
              </div>

              <v-text-field
                  :rules="rules.clearFieldValid"
                  :append-icon="eyeFlag ? 'mdi-eye' : 'mdi-eye-off'"
                  :type="eyeFlag ? 'text' : 'password'"
                  @click:append="eyeFlag = !eyeFlag"
                  id="password"
                  label="Введите пароль"
                  color="black"
                  v-model="password"
                  auto-grow
                  outlined
                  rows="1"
                  style="border-radius: 10px"
              />
            </v-form>


            <v-btn x-large class="authButton" :color=changeColor()
                   width="100%" dark
                   :loading="loadingLogin"
                   @click="submit()">
              Войти в систему
            </v-btn>

            <v-btn x-large class="authButton" :color=changeColor() width="100%" dark
                   to="/register">
              Зарегистрироваться
            </v-btn>

            <v-alert v-if="error" style="margin-top: 10px"
                     colored-border
                     type="error" outlined
                     elevation="0"
            >
              Неверные данные для входа
            </v-alert>

            <v-alert v-if="errorNetwork" style="margin-top: 10px"
                     colored-border
                     type="error" outlined
                     elevation="0"
            >
              Отсутствует подключение к серверу
            </v-alert>
          </v-card-text>
        </v-col>
      </v-row>
    </v-card>

    <div style="width: 200px; position: absolute; right: 50px; bottom: 20px">
      <v-slider
          :color=changeColor()
          v-model="colorSlider"
          max="4"
          min="1"
          v-on:change="switchColors(colorSlider)"
      ></v-slider>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import router from "../../router";
import {required} from "vuelidate/lib/validators";

export default {
  name: "AuthForm",

  validations: {
    password: {required},
    login: {required},
  },
  data: () => ({
    eyeFlag: false,
    password: '',
    login: '',
    valid: false,
    loadingLogin: false,
    error: false,
    errorNetwork: false,
    colorSlider: 1,

    rules: {
      clearFieldValid: [
        v => !!v || 'Поле не может быть пустым'
      ],
    },
  }),
  methods: {
    submit() {
      if (this.$refs.form.validate()) {
        this.loadingLogin = true
        let data = {
          username: this.login,
          password: this.password
        }
        axios.create({
          baseURL: this.hostname
        }).post('/api/auth/login', data)
            .then(async resp => {
              localStorage.token = resp.data.token
              localStorage.refreshToken = resp.data.refreshToken
              localStorage.login = this.login
              await new Promise(resolve => setTimeout(resolve, this.awaitTimer))
              await router.push({path: '/main'})
              this.loadingLogin = false
            }).catch(async err => {
          this.loadingLogin = false
          if (err.message === 'Network Error') {
            this.errorNetwork = true
          } else {
            await new Promise(resolve => setTimeout(resolve, 500))
            this.error = true
          }
        })
      }
    },

    oneMoreTime() {
      this.password = ''
      this.login = ''
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
  }

}
</script>

<style scoped>
.authButton {
  box-shadow: none !important;
  border-radius: 10px;
  margin-bottom: 20px;
}
</style>