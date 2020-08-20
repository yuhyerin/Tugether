<template>
    <div class="wrapB" style="text-align: center;">
        <br><strong @click="moveMypage" style="cursor: pointer;">마이페이지로 이동</strong>

        <!--탭-->
        <v-tabs grow style="margin-top: 10px;" id="tab_wrap">
            <!--나를 팔로우하는 사용자 목록-->
            <v-tab style="font-weight: bold;">팔로워 {{follower_cnt}}명</v-tab>
            <v-tab-item class="follow_item" style="padding-top: 15px;">
                <div v-for="(user, index) in followerList" :key="index" style="text-align: left; padding-bottom: 5px;">
                    <!--프로필사진 또는 닉네임을 클릭하면 해당 유저의 페이지로 이동-->
                    <v-avatar><img :src="`https://i3b303.p.ssafy.io/profileimages/${user.profile_photo}`" alt="image"  @click="moveUserpage(user.email)"></v-avatar>
                    <button @click="moveUserpage(user.email)"><strong style="font-size: 15px; padding-left: 10px;">{{ user.nickname }}</strong></button>
                    <!--내가 팔로우하고 있지 않은 사용자라면 '팔로우' 버튼 활성화-->
                    <span v-if="!user.follow">
                        <v-btn class="follow_button" depressed style="background-color: #3366ff; color: white;" @click="toFollow(user.email)">팔로우</v-btn>
                    </span>
                    <!--내가 팔로우하고 있는 사용자라면 '팔로잉' 버튼 활성화-->
                    <span v-else>
                        <v-btn class="follow_button" outlined style="color: #3366ff;" @click="unFollow(user.email)">팔로잉</v-btn>
                    </span>
                </div>
            </v-tab-item>
            <!--내가 팔로우하는 사용자 목록-->
            <v-tab style="font-weight: bold;">팔로잉 {{following_cnt}}명</v-tab>
            <v-tab-item class="follow_item" style="padding-top: 15px;">
               <div v-for="(user, index) in followingList" :key="index" style="text-align: left; padding-bottom: 5px;">
                    <!--프로필사진 또는 닉네임을 클릭하면 해당 유저의 페이지로 이동-->
                    <v-avatar><img :src="`https://i3b303.p.ssafy.io/profileimages/${user.profile_photo}`" alt="image" @click="moveUserpage(user.email)"></v-avatar>
                    <button @click="moveUserpage(user.email)"><strong style="font-size: 15px; padding-left: 10px;">{{ user.nickname }}</strong></button>
                    <!--내가 팔로우하고 있는 사용자이므로 '팔로잉' 버튼 활성화-->
                    <span>
                        <v-btn class="follow_button" outlined style="color: #3366ff;" @click="unFollow(user.email)">팔로잉</v-btn>
                    </span>
                </div>
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
            // model: "following",
            email: "",
            followerList: [],
            follower_cnt: "",
            followingList: [],
            following_cnt: ""
        }        
    },
    created() {
            this.refresh();
        },
    methods: {
        refresh() {
            // 팔로잉, 팔로워 목록 띄우기
            axios
                .get(base + '/tugether/mypage/followList', {
                    headers:{
                        "jwt-auth-token": localStorage.getItem("token") // 토큰 보내기
                    }
                })
                .then((res) => {
                    // console.log(res.data);
                    // 팔로워 정보
                    this.followerList = res.data.followerList;
                    this.follower_cnt = res.data.follower_cnt;
                    // 팔로잉 정보
                    this.followingList = res.data.followingList;
                    this.following_cnt = res.data.following_cnt;
                })
                .catch((err) => {
                    console.log("created axios get FOLLOW error")
                });
        },
        // 팔로잉 하기 (팔로우 버튼 누를 시)
        toFollow(email) {
            this.email = email;
            var answer = confirm("팔로우 하시겠습니까?");
            if(answer) { // true
                axios
                    .post(base + '/tugether/mypage/followapply', {
                        email: this.email
                    },
                    {
                        headers:{
                            "jwt-auth-token": localStorage.getItem("token") // 토큰 보내기
                        }
                    })
                    .then(({data}) => {
                        console.log(data.data);
                        this.refresh();
                    })
                    .catch((err) => {
                        console.log("FOLLOW function error")
                });
            } // if
        },
        // 팔로우 삭제 (팔로잉 버튼 누를 시)
        unFollow(email){
            this.email = email;
            var answer = confirm("팔로우를 취소하시겠습니까?");
            if(answer){ // true
                axios
                    .post(base + '/tugether/mypage/followcancel', {
                        email: this.email
                    },
                    {
                        headers:{
                            "jwt-auth-token": localStorage.getItem("token") // 토큰 보내기
                        }
                    })
                    .then(({data}) => {
                        console.log(data.data);
                        alert("팔로우가 취소되었습니다.");
                        this.refresh();
                    })
                    .catch((err) => {
                        console.log("UNFOLLOW function error")
                });
            } // if
        },
        // 다른 유저의 페이지로 이동
        moveUserpage(email){
            this.email = email;
            localStorage.setItem("userEmail", this.email)
            this.$router.push({
                name: 'Userpage'
            })
        },
        // 페이지 이동
        moveMypage() {
            this.$router.push("/mypage");
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
    .follow_button{
        float: right;
        margin-top: 7px;
    }
    .follow_item{
        padding: 0 15px;
    }
</style>