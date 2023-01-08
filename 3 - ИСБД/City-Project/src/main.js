import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store/store'
import vuetify from './plugins/vuetify'
import axios from "axios";

Vue.mixin({
    methods: {
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
        },
        getHeader() {
            return {
                baseURL: this.hostname,
                headers: {
                    Authorization: localStorage.token
                }
            }
        }
    },
    data: function () {
        return {
            get hostname() {
                return "http://localhost:10520";
            },
            get awaitTimer() {
                return 1000;
            },
            primaryColor(colorIndex) {
                switch (colorIndex) {
                    case 1:
                        return '#F58E43'
                    case 2:
                        return '#3754DB'
                    case 3:
                        return '#505780'
                    case 4:
                        return '#cf1ed5'
                }
            },
        }
    }
})

new Vue({
    store,
    router,
    vuetify,
    render: h => h(App),
}).$mount('#app')
