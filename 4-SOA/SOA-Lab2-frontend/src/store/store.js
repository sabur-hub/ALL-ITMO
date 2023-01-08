import Vuex from "vuex";
import Vue from "vue";
import spacemarines from "@/store/spacemarines";
Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        spacemarines
    }
})