<template>
    <div style="text-align: center;">
        <h1>검색 페이지</h1>
        <!--네비게이션 바-->
        <BottomNav/>
    </div>
</template>

<script>
import axios from "axios";
import store from '@/vuex/store';
import { mapState, mapActions, mapMutations } from "vuex";
import { base } from "@/components/common/BaseURL.vue"; // baseURL
import BottomNav from "@/components/common/BottomNav";

export default {
    components: {
        BottomNav
    },
    data: () => {
        return {

        }
    },
    methods: {

    }
}
</script>

<style>

</style>