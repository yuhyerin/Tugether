<template>
  <div class="feed mypage">
      <v-img v-if="imageUrl" :src="imageUrl" style="height: 350px; width: 350px;"></v-img>
  </div>
</template>

<script>
import axios from "axios";
import store from '@/vuex/store';
import { mapState, mapActions } from "vuex";
import { base } from "@/components/common/BaseURL.vue"; // baseURL
import TabItem from '@/components/common/TabItem.vue'

export default {
    components: { Imgtest },
    data: () => {
        return {
            imageUrl:"",
        }
    },
    created() {

        // 토큰 보내기
        axios
            .get(base + '/tugether/profile', {
                headers:{
                    "jwt-auth-token": localStorage.getItem("token")
                }
            })
            .then((res) => {
                    
                    console.log(res.data);
                    console.log(res.data.profile.profile_photo);
                    
                    this.profile_photo = res.data.profile.profile_photo;
                    this.nickname = res.data.profile.nickname;
                    this.following_cnt = res.data.profile.following_cnt;
                    this.follower_cnt = res.data.profile.follower_cnt;

            })
            .catch((err) => {

            });

    },
    methods: {

    },
    computed: {
        ...mapState(["token"]), //store 공동 저장소에 있는 token 사용하기 위해 선언
        ...mapActions(["getToken"]),

    }
}
</script>

<style scoped>
    .button{
        background: black;
        color: white;
        width: 25%;
        height: 35px;
    }
    #tags_test{
        color: white;
        background-color: red;
        /* border: 10px solid red; */
        /* border-radius: 3px; */
    }
    .contents {
    position: relative;
    overflow: hidden;
    width: 280px;
    /* border: 2px solid #000; */
    }
    .item {
    box-sizing: border-box;
    padding: 10px;
    width: 100%;
    transition: all 0.8s ease;
    }
    /* 트랜지션 전용 스타일 */
    .v-leave-active {
    position: absolute;
    }
    .v-enter {
    transform: translateX(-100%);
    }
    .v-leave-to {
    transform: translateX(100%);
    }
</style>