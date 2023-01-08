import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
    // {
    //   path: '/',
    //   name: 'Auth',
    //   component: Auth
    // },
    {
        path: '/',
        name: 'Auth',
        component: () => import('../views/Auth.vue')
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('../views/Register.vue')
    },
    {
        path: '/main',
        name: 'Main',
        component: () => import('../views/Main.vue')
    },
]

const router = new VueRouter({
    routes
})

export default router
