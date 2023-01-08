export default {
    state: {
        KvartalsList: [],
        primaryColor: 1,
    },
    mutations: {
        changeElement(state, index, newName) {
            state.KvartalsList[index] = newName
        },
        updateKvartalsList(state, KvartalsList) {
            state.KvartalsList = KvartalsList
        },
        clearAll(state) {
            state.KvartalsList = []
        },
        setPrimaryColor(state, newColor) {
            state.primaryColor = newColor
        },
    },
    getters: {
        allKvartals(state) {
            return state.KvartalsList
        },
        getPrimaryColor(state) {
            return state.primaryColor
        },
    }
}