import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router';
import routes from './routes'
import store from './vuex/store'
import vuetify from './plugins/vuetify';

Vue.config.productionTip = false

Vue.use(VueRouter)

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
