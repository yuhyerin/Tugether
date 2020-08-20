<template>
    <div class="wrapC" style="margin-top: 48%;">
        <h1>비밀번호 확인</h1>
        <strong>보안을 위해 현재 비밀번호를 다시 한 번 입력해주세요.</strong>
        <div class="input-with-label">
            <label for="password">비밀번호</label>
            <input v-model="password" id="password" :type="passwordType" placeholder="비밀번호를 입력하세요." />
            <!--비밀번호 입력 시 아이콘을 누르면 입력타입을 변경해준다.(text, password)-->
            <span class="eye_icon" @click="showPW"><i class="far fa-eye fa-lg"></i></span>
        </div>

        <!--버튼-->
        <div id="mypage_buttons2">
            <!--프로필 편집 페이지로 돌아가기 버튼-->
            <button class="button" :style="mybtn1" @mouseover="over1" @mouseout="out1"
                @click="moveMypageSetting" style="width: 48%; height: 45px; margin-right: 10px; float: left;">BACK</button>
            <!--현재 비밀번호 입력 완료 버튼-->
            <!--비밀번호가 틀리면 alert창으로 안내해주고, 맞으면 비밀번호 변경페이지로 이동한다.-->
            <button class="button" :style="mybtn2" @mouseover="over2" @mouseout="out2"
                @click="checkPW" style="width: 48%; height: 45px; float: right;">확인</button>
        </div>

        <!--네비게이션바-->
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
    components:{
        BottomNav,
    },
    data: () => {
        return {
            email: "",
            password: "",
            passwordType: "password",
            mybtn1: {
                backgroundColor: "black"
            },
            mybtn2: {
                backgroundColor: "black"
            }
        }
    },
    computed: {
        ...mapState(["token"]), //store 공동 저장소에 있는 token 사용하기 위해 선언
        ...mapActions(["getToken"])
    },
    methods: {
        // 현재 비밀번호가 맞는지 확인
        checkPW() {
            axios
                .get(base + '/tugether/changepw', {
                    headers:{
                        "jwt-auth-token": localStorage.getItem("token"), // 토큰 보내기
                        "password": this.password
                    }
                })
                .then(({data}) => {
                    console.log(data.data);
                    if(data.data === "success"){
                        alert("비밀번호가 확인되었습니다.");
                        this.moveMypagePWchange(); // 비밀번호 변경 페이지로 이동
                    } else{
                        alert("비밀번호를 다시 확인해주세요. ");
                    }
                })
                .catch((err) => {
                    console.log("checkPW function error")
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
        moveMypageSetting() {
            this.$router.push("/mypage/mypagesetting");
        },
        moveMypagePWchange(){
            this.$router.push("/mypage/mypagepasswordchange");
        },
        // 비밀번호 입력 시 아이콘을 누르면 입력타입 변경(text, password)
        showPW() {
            if (this.passwordType === "password") {
                this.passwordType = "text";
            } else {
                this.passwordType = "password";
            }
        },
    }
}
</script>

<style>
    .eye_icon{
        position: absolute;
        top: 50%;
        margin-left: -40px;
        margin-top: -10px;
        z-index: 1;
        color: #4f5b66;
    }
    #mypage_buttons2{
        width: 100%;
        margin: 0 auto;
        padding-top: 20px;
        display: inline-block;
    }
</style>

<style scoped>
    .wrapC:before{
        content: '';
        display: inline-block;
        height: 100%;
        vertical-align: middle;
    }
    .button{
        background: black;
        color: white;
        width: 25%;
        height: 35px;
    }
</style>