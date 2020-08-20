<template>
    <div class="wrapC" style="margin-top: 48%;">
        <h1>비밀번호 변경</h1>
        <!--비밀번호-->
        <div class="input-with-label">
            <label for="password">신규 비밀번호</label>
            <input v-model="password" id="password" ref="password" :type="passwordType" placeholder="비밀번호를 입력하세요." />
            <!--비밀번호 입력 시 아이콘을 누르면 입력타입을 변경해준다.(text, password)-->
            <span class="eye_icon" @click="showPW1"><i class="far fa-eye fa-lg"></i></span>
        </div>
        <div class="error-text" v-if="error.password" style="color:red">{{error.password}}</div>
        <!--비밀번호 확인-->
        <div class="input-with-label">
            <label for="password-confirm">신규 비밀번호 확인</label>
            <input v-model="passwordConfirm" ref="passwordConfirm" :type="passwordConfirmType" id="password-confirm"
                placeholder="비밀번호를 다시 한 번 입력하세요." style="width: 100%;" />
            <!--비밀번호 입력 시 아이콘을 누르면 입력타입을 변경해준다.(text, password)-->
            <span class="eye_icon" @click="showPW2"><i class="far fa-eye fa-lg"></i></span>
        </div>
        <div class="error-text" v-if="error.passwordConfirm" style="color:red">{{error.passwordConfirm}}</div>

        <!--버튼-->
        <div id="mypage_buttons2">
            <!--프로필 편집 페이지로 돌아가는 버튼 (비밀번호 변경을 원할 경우 재확인 해야함)-->
            <button class="button" :style="mybtn1" @mouseover="over1" @mouseout="out1"
                @click="moveMypageSetting" style="width: 48%; height: 45px; margin-right: 10px; float: left;">BACK</button>
            <!--비밀번호 변경 완료 버튼-->
            <button class="button" :style="mybtn2" @mouseover="over2" @mouseout="out2"
                @click="checkHandler" style="width: 48%; height: 45px; float: right;">비밀번호 변경하기</button>
        </div>
        <BottomNav/>
    </div>
</template>

<script>
import axios from "axios";
import PV from "password-validator";
import store from '@/vuex/store';
import { mapState, mapActions } from "vuex";
import { base } from "@/components/common/BaseURL.vue"; // baseURL
import BottomNav from "@/components/common/BottomNav";

export default {
    components:{
        BottomNav,
    },
    data: () => {
        return {
            email: "",
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
            }
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
        password: function (v) {
        this.checkForm();
        },
        passwordConfirm: function (v) {
        this.checkForm();
        }
    },
    computed: {
        ...mapState(["token"]), //store 공동 저장소에 있는 token 사용하기 위해 선언
        ...mapActions(["getToken"])
    },
    methods: {
        // 비밀번호 변경하기
        changePW() {
            axios
                .post(base + '/tugether/changepw', {
                    password: this.password
                },
                {
                    headers:{
                        "jwt-auth-token": localStorage.getItem("token") // 토큰 보내기
                    }
                })
                .then(({data}) => {
                    // console.log(data.data);
                    alert("비밀번호 변경이 완료되었습니다.");
                    this.moveMypage(); // 마이페이지로 이동
                })
                .catch((err) => {
                    console.log("changePW function error")
                });
        },
        // 비밀번호 유효성 검사 (생성 규칙, 일치 여부 확인)
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
        // 사용자가 입력하지 않은 칸이 있을 경우 포커스 이동 & 모든 유효성 검사를 통과했을 때(적합, 인증 등)만 비밀번호 변경 가능
        checkHandler() {
            let err = true;
            let msg = "";
            !this.password && ((msg = "비밀번호를 입력해주세요!"),(err = false),this.$refs.password.focus());
            err && !this.passwordConfirm && ((msg = "비밀번호 확인을 입력해주세요!"),(err = false),this.$refs.passwordConfirm.focus());

            if (!err) {
                alert(msg);
            } else if (!this.error.password && !this.error.passwordConfirm) {
                this.changePW();
            } else {
                alert("비밀번호가 올바르지 않습니다.");
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
            this.$router.push("/mypage");
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
        }
    }
}
</script>

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