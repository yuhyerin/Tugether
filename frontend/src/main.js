import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router';
import routes from './routes'
import store from './vuex/store'
import vuetify from './plugins/vuetify';

// import Unicon from 'vue-unicons'
// import { uniYoutube } from 'vue-unicons/src/icons'
// import { uniLink } from 'vue-unicons/src/icons'


Vue.config.productionTip = false

Vue.use(VueRouter)
// Unicon.add([uniYoutube])
// Unicon.add([uniLink])
// Vue.use(Unicon)

const router = new VueRouter({

    mode:"history",
    routes,
});

new Vue({
    router,
    store,
    vuetify,
    render: h => h(App)
}).$mount('#app');


export default router;
