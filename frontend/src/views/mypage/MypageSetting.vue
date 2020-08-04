<template>
    <div class="feed mypage">
        <div class="wrapB" style="text-align: center;">
            <h1 style="margin-top: 20px;">프로필 편집</h1>
            <img src="../../assets/images/profile_default.png" style="size: 100px; margin-bottom: 10px;">
            <strong style="margin-left: 10px; color: blue;">프로필 사진 변경하기</strong>
            <!--닉네임-->
            <div class="input-with-label">
                <label for="nickname">닉네임</label>
                <input v-model="nickname" id="nickname" placeholder=nickname type="text" />
            </div>
            <!--비밀번호-->
            <div class="row">
                <div class="col-3" style="margin-left: -35px;">
                    <label for="password">비밀번호</label>
                </div>
                <div class="col-2">
                   <button class="button" @click="moveConfirmPW" style="width: 180px; margin-top: -5px;">비밀번호 변경</button>
                </div>
            </div>
            <!--관심태그-->
            <div class="row" style="height: 200px; margin-top: 10px; padding: 5px 0 0 10px;">
                <label for="favtags">관심태그</label>
                <div class="col-3">
                    <div v-for="tags in favtags" :key=tags>
                        {{ tags }}
                    </div>
                </div>
            </div>       

            <!--버튼-->
            <div id="mypage_buttons">
                <!--마이페이지로 돌아가기 버튼-->
                <button class="button" v-bind:style="mybtn1" @mouseover="over1" @mouseout="out1"
                    @click="moveMypage" style="width: 200px; height: 45px; margin-right: 10px;">BACK</button>
                <!--변경한 내용 저장하기 버튼-->
                <button class="button" v-bind:style="mybtn2" @mouseover="over2" @mouseout="out2"
                    style="width: 200px; height: 45px;">변경하기</button>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "axios";
export default {
    data: () => {
        return {
            // 프로필 사진
            nickname: "",
            password: "",
            passwordType: "password",
            mybtn1: {
                backgroundColor: "black"
            },
            mybtn2: {
                backgroundColor: "black"
            },
            favtags: [],
        }
    },
    created() {
        // 닉네임, 관심태그
    },
    methods: {
        // 프로필 변경하기
        changeProfile() {
            axios // 어디로 보냄?
                .post('http://127.0.0.1:8080',{
                    // 이메일 어떻게?
                    // 프로필 사진
                    nickname: this.nickname,
                    // 관심태그
                })
                .then((res) => {

                })
                .catch((err) => {

                });
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

<style>
    #mypage_buttons{
        width: 100%;
        margin: 0 auto;
        padding-top: 50px;
        float: left;
    }
    .row{
        padding-top: 0px;
        margin: 0px;
        border: 1px solid black;
        border-radius: 3px;
        height: 50px;
        width: 100%;
        font-weight: 600;
        font-size: 0.857em;
    }
    .wrapB:before{
        content: '';
        display: inline-block;
        height: 100%;
        vertical-align: middle;
    }
</style>