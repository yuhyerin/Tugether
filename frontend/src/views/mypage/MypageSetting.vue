<template>
    <div class="feed mypage">
        <div class="wrapB" style="text-align: center;">
            <h1 style="margin-top: 20px;">프로필 편집</h1>
            <div id="image_setting" style="margin-top: -10px;">
                <img src="../../assets/images/profile_default.png" style="size: 100px; margin-bottom: 10px;">
                <strong style="margin-left: 10px; color: blue;">프로필 사진 변경하기</strong>
            </div>
            <!--닉네임-->
            <div class="input-with-label">
                <label for="nickname">닉네임</label>
                <input v-model="nickname" id="nickname" placeholder=nickname type="text" />
            </div>
            <!--비밀번호-->
            <div class="row" style="padding-top: 0px; margin: 0px; border: 1px solid black; border-radius: 3px; height: 50px; width: 100%;  font-weight: 600; font-size: 0.857em;">
                <div class="col-3" style="margin-left: -35px;">
                    <label for="password">비밀번호</label>
                </div>
                <div class="col-2">
                   <button class="button" @click="moveConfirmPW" style="width: 180px; margin-top: -5px;">비밀번호 변경</button>
                </div>
            </div>
            <!--관심태그-->
            <div class="row" style="border: 1px solid black; border-radius: 3px; height: 200px; margin-top: 10px; padding: 5px 0 0 10px;
                margin-left: 1px; width: 100%; font-weight: 600; font-size: 0.857em;">
                <label for="favtags">관심태그</label>
                <div class="col-3" style="font-size: 20px; margin-left: 48px;">
                    <span v-for="tags in favtags" :key=tags>
                        {{ tags }}
                    </span>
                </div>
            </div>       

            <!--버튼-->
            <div id="mypage_buttons">
                <!--마이페이지로 돌아가기 버튼-->
                <button class="button" :style="mybtn1" @mouseover="over1" @mouseout="out1"
                    @click="moveMypage" style="width: 200px; height: 45px; margin-right: 10px;">BACK</button>
                <!--변경한 내용 저장하기 버튼-->
                <button class="button" :style="mybtn2" @mouseover="over2" @mouseout="out2"
                    style="width: 200px; height: 45px;">변경하기</button>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "axios";
import store from '@/vuex/store';
import { mapState, mapActions } from "vuex";
import { base } from "@/components/common/BaseURL.vue"; // baseURL

export default {
    data: () => {
        return {
            profile_photo: "",
            nickname: "",
            favtags: [],
            mybtn1: {
                backgroundColor: "black"
            },
            mybtn2: {
                backgroundColor: "black"
            }
        }
    },
    created() {
        this.nickname = "만두",
        this.favtags = ['음악','먹방','게임'];

        // 프로필 사진, 닉네임, 관심태그
        axios
            .get(base + '/')
            .then({})
            .catch({});
    },
    computed: {

    },
    methods: {
        // 프로필 변경하기
        changeProfile() {
            // axios
            //     .post(base + '/tugether/profile',{
            //         headers:{
            //             "jwt-auth-token": this.$store.state.token
            //         },
            //         profile_photo: this.profile_photo,
            //         nickname: this.nickname,
            //         favtags: this.favtags
            //     })
            //     .then(({data}) => {

            //     })
            //     .catch((err) => {

            //     });
        },
        // 버튼에 마우스 갖다대면 빨갛게 변하도록
        over1() {
            this.mybtn1.backgroundColor = "red";
        },
        out1() {
            this.mybtn1.backgroundColor = "black";
        },
        over2() {
            this.mybtn2.backgroundColor = "red";
        },
        out2() {
            this.mybtn2.backgroundColor = "black";
        },
        // 페이지 이동
        moveConfirmPW() {
            this.$router.push("/mypage/mypagepasswordconfirm");
        },
        moveMypage() {
            this.$router.push("/mypage/mypage");
        }
    },
}
</script>

<style scoped>
    #mypage_buttons{
        width: 100%;
        margin: 0 auto;
        padding-top: 50px;
        float: left;
    }
    .wrapB:before{
        content: '';
        display: inline-block;
        height: 100%;
        vertical-align: middle;
    }
</style>