<template>
    <div class="wrapC">
        <h1 style="margin-top: 20px;">비밀번호 변경</h1>
        <!--비밀번호-->
        <div class="input-with-label">
            <label for="password">신규 비밀번호</label>
            <input v-model="password" id="password" :type="passwordType" placeholder="비밀번호를 입력하세요." />
            <!--비밀번호 입력 시 아이콘을 누르면 입력타입을 변경해준다.(text, password)-->
            <span class="icon" @click="showPW1"><i class="far fa-eye fa-lg"></i></span>
        </div>
        <div class="error-text" v-if="error.password" style="color:red">{{error.password}}</div>
        <!--비밀번호 확인-->
        <div class="input-with-label">
            <label for="password-confirm">신규 비밀번호 확인</label>
            <input v-model="passwordConfirm" ref="passwordConfirm" :type="passwordConfirmType" id="password-confirm"
                placeholder="비밀번호를 다시 한 번 입력하세요." style="width: 100%;" />
            <!--비밀번호 입력 시 아이콘을 누르면 입력타입을 변경해준다.(text, password)-->
            <span class="icon" @click="showPW2"><i class="far fa-eye fa-lg"></i></span>
        </div>
        <div class="error-text" v-if="error.passwordConfirm" style="color:red">{{error.passwordConfirm}}</div>

        <!--버튼-->
        <div id="mypage_buttons2">
            <!--프로필 편집 페이지로 돌아가는 버튼 (비밀번호 변경을 원할 경우 재확인 해야함)-->
            <button class="button" :style="mybtn1" @mouseover="over1" @mouseout="out1"
                @click="moveMypageSetting" style="width: 200px; height: 45px; margin-right: 10px;">BACK</button>
            <!--비밀번호 변경 완료 버튼-->
            <button class="button" :style="mybtn2" @mouseover="over2" @mouseout="out2"
                @click="changePW" style="width: 200px; height: 45px;">비밀번호 변경하기</button>
        </div>

    </div>
</template>

<script>
import axios from "axios";
import PV from "password-validator";
import { base } from "@/components/common/BaseURL.vue"; // baseURL

export default {
    data: () => {
        return {
            password: "",
            passwordConfirm: "",
            passwordSchema: new PV(),
            passwordType: "password",
            passwordConfirmType: "password",
            mybtn1: {
                backgroundColor: "black"
            },
            mybtn2: {
                backgroundColor: "black"
            },
            error: {
                password: false,
                passwordConfirm: false
            },
        }
    },
    created() {
        // 비밀번호 입력 규칙
        this.passwordSchema
        .is()
        .min(8)
        .is()
        .max(100)
        .has()
        .digits()
        .has()
        .letters();
    },
    watch: {
        passwordConfirm: function (v) {
        this.checkForm();
        },
        password: function (v) {
        this.checkForm();
        },
    },
    methods: {
        checkForm() {
            if (this.password.length >= 0 && !this.passwordSchema.validate(this.password)) {
                this.error.password = "영문,숫자 포함 8자리 이상이어야 합니다.";
            }
            else {
                this.error.password = false;
            }
            if (this.passwordConfirm.length >= 0 && this.password != this.passwordConfirm) {
                this.error.passwordConfirm = "비밀번호가 일치하지 않습니다.";
            }
            else {
                this.error.passwordConfirm = false;
            }
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
        moveMypage(){
            this.$router.push("/mypage/mypage");
        },
        // 비밀번호 입력 시 아이콘을 누르면 입력타입 변경(text, password)
        showPW1() {
            if (this.passwordType === "password") {
                this.passwordType = "text";
            } else {
                this.passwordType = "password";
            }
        },
        showPW2() {
            if (this.passwordConfirmType === "password") {
                this.passwordConfirmType = "text";
            } else {
                this.passwordConfirmType = "password";
            }
        },
        // 비밀번호 변경하기
        changePW() {
            axios // 어디로 보냄?
                .post('http://127.0.0.1:8080', {
                    // 이메일 어떻게?
                    password: this.password,
                })
                .then((res) => {

                })
                .catch((err) => {

                });
            alert("비밀번호 변경이 완료되었습니다.");
            this.moveMypage();
        },
    }
}
</script>

<style>
    .wrapC:before{
        content: '';
        display: inline-block;
        height: 100%;
        vertical-align: middle;
    }
</style>