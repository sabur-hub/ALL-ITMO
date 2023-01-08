export default {
    state: {
        Spacemarines: []
    },
    mutations: {
        updateSpacemarines(state, Spacemarines) {
            state.Spacemarines = Spacemarines
        },
        clearAllSpacemarines(state) {
            state.Spacemarines = []
        },
    },
    getters: {
        allSpacemarines(state) {
            return state.Spacemarines
        },
    }
}