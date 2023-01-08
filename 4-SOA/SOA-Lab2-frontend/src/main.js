import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store/store'
import vuetify from './plugins/vuetify'
//import axios from "axios";

Vue.mixin({
    methods: {
        /*
        doRefresh(status) {
            let data = {
                refreshToken: localStorage.refreshToken
            }
            if (status === 403) {
                let flag = false
                axios.create({
                    baseURL: this.hostname,
                    headers: {
                        Authorization: localStorage.token
                    }
                }).post("/api/refresh/token", data)
                    .then(async resp => {
                        localStorage.token = resp.data.token
                        localStorage.refreshToken = resp.data.refreshToken
                        await new Promise(resolve => setTimeout(resolve, this.awaitTimer))

                        flag = true
                    })
                return flag
            }
        },*/
        getHeader(serviceNumber) {
            if (serviceNumber === 1) {
                return {
                    baseURL: this.firstHostname,
                    headers: {
                        'Access-Control-Allow-Origin': '*',
                        'Access-Control-Allow-Headers': '*',
                        'Access-Control-Allow-Credentials': 'true'
                    }
                }
            } else if (serviceNumber === 2) {
                return {
                    baseURL: this.secondHostname,
                    headers: {
                        'Access-Control-Allow-Origin': '*',
                        'Access-Control-Allow-Headers': '*',
                        'Access-Control-Allow-Credentials': 'true'
                    }
                }
            }
        }
    },
    data: function () {
        return {
            get firstHostname() {
                return "http://localhost:10000";
            },
            get secondHostname() {
                return "http://localhost:10100";
            },
            get awaitTimer() {
                return 1000;
            },
            get errorTimer() {
                return 5000;
            }
        }
    }
})

new Vue({
    store,
    router,
    vuetify,
    render: h => h(App),
}).$mount('#app')
