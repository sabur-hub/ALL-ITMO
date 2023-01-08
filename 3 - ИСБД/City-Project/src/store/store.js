import Vuex from "vuex";
import Vue from "vue";
import kvartalList from "@/store/kvartalList";
Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        kvartalList
    }
})