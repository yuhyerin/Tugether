<template>
  <div class="wrapC" style="overflow: auto;">
    <br />
    <h1 style="text-align: center; margin-bottom: 5px;">🍒회원가입🍒</h1>
    <div class="form-wrap">

      <!--이메일-->
      <div class="row" style="height: 50px;">
        <div class="input-with-label col-4" style="margin-bottom: 0px; padding: 0px 0px 12px 12px;">
          <label style="padding-bottom: 0px; padding-left: 10px;">이메일</label>
          <input v-model="email_id" id="email_id" ref="email_id" placeholder="아이디" type="text" />
        </div>
        <div class="input-with-label col-4" style="margin-bottom: 0px; padding: 0px 6px 12px 6px;">
          <label style="padding-bottom: 0px; padding-left: 10px;">@</label>
          <input v-model="domain" id="domain" ref="domain" placeholder="도메인" type="text" />&nbsp;
        </div>
        <div class="form-group col-4" style="margin: 0px; padding: 0px 10px 0px 0px;">
          <select class="form-control" id="exampleFormControlSelect1" v-model="domain" style="height: 50px; border: 1px solid black; border-radius: 3px;">
            <option value selected="selected">직접 입력</option>
            <option value="naver.com">naver.com</option>
            <option value="gmail.com">gmail.com</option>
            <option value="hanmail.net">hanmail.net</option>
          </select>
        </div>
      </div>

      <!--이메일 사용 가능 여부 및 인증번호 일치 여부 안내문구 출력-->
      <div class="error-text" v-if="error.email"
        style="color: red; margin-top: 10px; margin-bottom: 0px;">{{error.email}}</div>

      <!-- 이메일 중복 체크, 유효성 검사-->
      <div>
        <button class="buttonDuplicate" @click="test"
          style="margin-top: 10px; border-radius: 3px; width: 130px; height: 45px;">이메일 중복 체크</button>
      </div>
      <div class="input-with-label" style="margin-top: 10px;">
        <label for="password">인증번호</label>
        <input v-model="valid_Num" id="valid_Num" type="text" placeholder="인증번호를 입력하세요." style="width: 78%; float: left;" />
        <button class="buttonValidate" @click="checkValid()"
          style="border-radius: 3px; width: 20%; height: 50px; margin-left: 5px; float: right;">확인</button>
      </div>

      <!--비밀번호-->
      <div class="input-with-label">
        <label for="password">비밀번호</label>
        <input v-model="password" id="password" ref="password" :type="passwordType" placeholder="비밀번호를 입력하세요." />
        <!--비밀번호 입력 시 아이콘을 누르면 입력타입을 변경해준다.(text, password)-->
        <span class="eye_icon" @click="showPW1"><i class="far fa-eye fa-lg"></i></span>
      </div>
      <div class="error-text" v-if="error.password" style="color:red">{{error.password}}</div>
      <!--비밀번호 확인-->
      <div class="input-with-label">
      <form>
        <label for="password-confirm">비밀번호 확인</label>
        <input v-model="passwordConfirm" ref="passwordConfirm" :type="passwordConfirmType" id="password-confirm" placeholder="비밀번호를 다시 한 번 입력하세요." style="width: 100%;" />
        <!--비밀번호 입력 시 아이콘을 누르면 입력타입을 변경해준다.(text, password)-->
      </form>
        <span class="eye_icon" @click="showPW2"><i class="far fa-eye fa-lg"></i></span>
      </div>
      <!--비밀번호 일치 여부 안내문구 출력-->
      <div class="error-text" v-if="error.passwordConfirm" style="color:red">{{error.passwordConfirm}}</div>

      <!--닉네임-->
      <div class="input-with-label">
        <label for="nickname">닉네임</label>
        <input v-model="nickname" id="nickname" ref="nickname" placeholder="닉네임을 입력하세요." type="text" />
      </div>

      <!--출생년도-->
      <div class="input-with-label">
        <label for="birth_year">출생년도</label>
        <input v-model="birth_year" id="birth_year" ref="birth_year" placeholder="출생년도를 입력하세요. (예: YYYY)" type="text" />
      </div>

      <!--성별-->
      <div class="row" style="padding-top: 0px; margin: 0px; border: 1px solid black; border-radius: 3px; height: 50px; width: 100%;  font-weight: 600; font-size: 0.857em; ">
        <div class="col-3" style="margin-top: 3px; padding-left: 12px;">
          <label for="gender">성별</label>
        </div>
        <div class="col-2">
          <input type="radio" id="female" value="F" v-model="gender" style="left: -8px; height: 20px; margin: 2px 0 0 52px;" />
          <label for="female" style="padding: 3px 0 0 54px;">여성</label>
        </div>
        <div class="col-1">
          <input type="radio" id="male" value="M" v-model="gender" style="left: -10px; height: 20px; margin: 2px 0 0 60px;" />
          <label for="male" style="padding: 3px 0 0 54px;">남성</label>
        </div>
      </div>
    </div>

    <!--버튼-->
    <div id="signup_buttons">
      <!--홈으로 돌아가기 버튼-->
      <button class="button" :style="mybtn1" @mouseover="over1" @mouseout="out1"
          @click="moveLogin" style="width: 48%; height: 45px; margin-right: 10px; float: left;">BACK</button>
      <!--변경한 내용 저장하기 버튼-->
      <button class="button" :style="mybtn2" @mouseover="over2" @mouseout="out2"
          @click="checkHandler" style="width: 48%; height: 45px; float: right;">가입하기</button>
    </div>

  </div>
</template>

<script>
import axios from "axios";
import PV from "password-validator";
import * as EmailValidator from "email-validator";
import { base } from "@/components/common/BaseURL.vue"; // baseURL

var valid = "";
export default {
  data: () => {
    return {
      email: "",
      email_id: "",
      domain: "",
      valid_Num: "",
      password: "",
      passwordConfirm: "",
      passwordSchema: new PV(),
      nickname: "",
      gender: "F",
      birth_year: "",
      check: false,
      isLoading: false,
      error: {
        email: "이메일 중복체크를 해주세요😊",
        password: false,
        passwordConfirm: false,
        nickName: false,
      },
      passwordType: "password",
      passwordConfirmType: "password",
      mybtn1: {
        backgroundColor: "black"
      },
      mybtn2: {
        backgroundColor: "black"
      }
    };
  },
  created() {
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
    // 이메일 아이디나 도메인에 변화가 생기면 사용자에게 이메일 중복체크를 하도록 요청함
    email_id: function (v) {
      this.error.email = "이메일 중복체크를 해주세요😊";
    },
    domain: function (v) {
      this.error.email = "이메일 중복체크를 해주세요😊";
    },
  },
  methods: {
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
    // 이메일 유효성 검사
    test() {
      this.email = this.email_id + "@" + this.domain;
      axios
        .get(base + '/account/signup/' + this.email)
        .then((res) => {
          // console.log(res.data);
          valid = res.data.message;
          if (res.data.message) {
            alert("이메일로 인증번호를 발송하였습니다.");
            this.error.email = "이메일로 인증번호를 발송하였습니다.";
          } else {
            alert("이메일 주소를 다시 한 번 확인해주세요.");
            this.error.email = "이미 가입된 이메일 주소입니다.";
          }
        })
        .catch((err) => {
          alert("사용할 수 없는 이메일주소입니다.");
          this.error.email = "사용할 수 없는 이메일입니다.";
        });
    },
    // 이메일로 발송한 인증번호 유효성 검사
    checkValid() {
      if (this.valid_Num == valid) { // 이메일로 발송한 인증번호와 사용자가 입력한 인증번호가 일치할 때
        // console.log(valid);
        this.error.email = false;
        alert("인증번호가 확인되었습니다.");
      } else {
        this.error.email = "인증번호가 일치하지 않습니다.";
      }
    },
    // 회원가입
    signup() {
      axios
        .post(base + '/account/signup/', {
          email: this.email,
          password: this.password,
          nickname: this.nickname,
          gender: this.gender,
          birth_year: this.birth_year,
        })
        .then(({ data }) => {
          // console.log(data.data);
          let msg = "회원가입 시 문제가 발생했습니다.";
          if (data.data === "success") {
            msg = "회원가입이 완료되었습니다.";
          }
          alert(msg);
          this.moveLogin();
        });
    },
    // 페이지 이동
    moveLogin() {
      this.$router.push("/");
    },
    // 사용자가 입력하지 않은 칸이 있을 경우 포커스 이동 & 모든 유효성 검사를 통과했을 때(적합, 인증 등)만 회원가입 가능
    checkHandler() {
      let err = true;
      let msg = "";
      !this.email_id && ((msg = "이메일을 입력해주세요!"),(err = false),this.$refs.email_id.focus());
      err && !this.domain && ((msg = "이메일을 입력해주세요!"),(err = false),this.$refs.domain.focus());
      err && !this.password && ((msg = "비밀번호를 입력해주세요!"),(err = false),this.$refs.password.focus());
      err && !this.passwordConfirm && ((msg = "비밀번호 확인을 입력해주세요!"),(err = false),this.$refs.passwordConfirm.focus());
      err && !this.nickname && ((msg = "닉네임을 입력해주세요!"),(err = false),this.$refs.nickname.focus());
      err && !this.birth_year && ((msg = "출생년도를 입력해주세요!"),(err = false),this.$refs.birth_year.focus());

      if (!err) {
        alert(msg);
      } else if (!this.error.email && !this.error.password && !this.error.passwordConfirm) {
        this.signup();
      }
      else alert("인증절차를 완료해주세요.");
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
  },
};
</script>

<style scoped>
.register-btn {
  width: 100% !important;
  transform: translate(50%, 0%) !important;
  left: -50% !important;
  align-items: center !important;
  text-align: center !important;
  line-height: 50px !important;
  position: relative !important;
  display: flex !important;
  justify-content: center !important;
  margin-top: 40px !important;
  margin-bottom: 0 !important;
}

#container {
  align-content: center;
  margin: 0 auto;
}
#email_id {
  padding: 0px 0px 0px 55px;
}
#domain {
  padding: 0px 0px 0px 35px;
}
.buttonDuplicate {
  background: black;
  color: white;
  width: 25%;
  height: 35px;
}
.buttonValidate {
  background: black;
  color: white;
  width: 25%;
  height: 35px;
}
select {
  -webkit-appearance: button;
  transition: ease-in-out 1000000s;
}
input[type="radio"] {
  -webkit-appearance: radio;
}
input[type="radio"] {
  -webkit-appearance: radio;
}
#signup_buttons{
  width: 100%;
  margin: 0 auto;
  padding-top: 30px;
  /* float: left; */
  display: inline-block;
}
.button{
  background: black;
  color: white;
  width: 25%;
  height: 35px;
}
</style>