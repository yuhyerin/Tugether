<template>
    <div class="wrapB" style="text-align: center;">
        <br><br>
        <strong @click="moveMypage" style="cursor: pointer;">마이페이지로 이동</strong>

        <!--탭-->
        <v-tabs grow style="margin-top: 10px;">
            <!--내가 팔로잉하는 사용자 목록-->
            <v-tab style="font-weight: bold;">팔로잉</v-tab>
            <v-tab-item style="padding-top: 15px;">
                <h1>팔로잉 리스트</h1>
            </v-tab-item>
            <!--나를 팔로우하는 사용자 목록-->
            <v-tab style="font-weight: bold;">팔로워</v-tab>
            <v-tab-item style="padding-top: 15px;">
                <h1>팔로워 리스트</h1>
            </v-tab-item>
        </v-tabs>

        <!--네비게이션 바-->
        <BottomNav/>
    </div>
</template>

<script>
import axios from "axios";
import store from '@/vuex/store';
import { mapState, mapActions } from "vuex";
import { base } from "@/components/common/BaseURL.vue"; // baseURL
import BottomNav from "@/components/common/BottomNav"

export default {
    components: { 
        BottomNav
     },
    data: () => {
        return {
        }        
    },
    created() {
        // 팔로잉, 팔로워 목록 띄우기
        axios
            .get(base + '/tugether/follow', {
                headers:{
                    "jwt-auth-token": localStorage.getItem("token") // 토큰 보내기
                }
            })
            .then((res) => {
                console.log(res.data);
            })
            .catch((err) => {
                console.log("created axios get FOLLOW error")
            });
    },
    methods: {
        // 페이지 이동
        moveMypage() {
            this.$router.push("/mypage/Mypage");
        }
    },
    computed: {
        ...mapState(["token"]), //store 공동 저장소에 있는 token 사용하기 위해 선언
        ...mapActions(["getToken"]),
        current() {
            return this.list.find(el => el.id === this.currentId) || {}
        }
    }
}
</script>

<style scoped>
</style>