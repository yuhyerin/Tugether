<template>
  <div class="wrapC">
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

      <div class="error-text" v-if="error.email" style="color: red; margin-top: 10px; margin-bottom: 10px;">{{error.email}}</div>

      <!-- 이메일 중복 체크, 유효성 검사-->
      <div>
        <button class="buttonDuplicate" @click="test"
          style="margin-top: 0px; border-radius: 3px; width: 130px; height: 45px;">이메일 중복 체크</button>
      </div>
      <div class="input-with-label" style="margin-top: 10px; width: 400px;">
        <label for="password">인증번호</label>
        <input v-model="valid_Num" id="valid_Num" type="text" placeholder="인증번호를 입력하세요." />
      </div>
      <div>
        <button class="buttonValidate" @click="checkValid()"
          style="margin-top: 10px; border-radius: 3px; width: 130px; height: 50px; margin-left: 10px;">인증번호 확인</button>
      </div>

      <!--비밀번호-->
      <div class="input-with-label">
        <label for="password">비밀번호</label>
        <input v-model="password" id="password" ref="password" :type="passwordType" placeholder="비밀번호를 입력하세요." />
      </div>
      <div class="error-text" v-if="error.password" style="color:red">{{error.password}}</div>

      <div class="input-with-label">
        <label for="password-confirm">비밀번호 확인</label>
        <input v-model="passwordConfirm" ref="passwordConfirm" :type="passwordConfirmType" id="password-confirm" placeholder="비밀번호를 다시 한 번 입력하세요." style="width: 100%;" />
      </div>
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
        <div class="col-3" style="padding-left: 12px;">
          <label for="gender">성별</label>
        </div>
        <div class="col-2">
          <input type="radio" id="female" value="F" v-model="gender" style="left: -8px; height: 20px;" />
          <label for="female">여성</label>
        </div>
        <div class="col-1">
          <input type="radio" id="male" value="M" v-model="gender" style="left: -10px; height: 20px;" />
          <label for="male">남성</label>
        </div>
      </div>
    </div>
    <!--홈으로 돌아가기 버튼-->
    <button class="btn-bottom register-btn" @click="moveLogin" style="margin-bottom: 52px; margin-left:0">BACK</button>
    <!--회원가입 동작 버튼-->
    <button class="btn-bottom register-btn" @click="checkHandler" style="margin-left:0; margin-top:10px !important;">가입하기</button>
    <br/>
  </div>
</template>

<script>
import axios from "axios";
import PV from "password-validator";
import * as EmailValidator from "email-validator";
var valid = "";
var localhost_url = "http://127.0.0.1:8080";
var aws_url = "http://i3b303.p.ssafy.io";
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
      isSubmit: false, //요청 후에는 버튼 비활성화 (this.isSubmit = false;)
      passwordType: "password",
      passwordConfirmType: "password",
      checkPasswordRules: [
        (v) =>
          this.password === this.passwordConfirm ||
          "비밀번호가 일치하지 않습니다.",
      ],
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
    test() {
      this.email = this.email_id + "@" + this.domain;
      axios
        .get(localhost_url + "/account/signup/" + this.email)
        .then((res) => {
          console.log(res.data);
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
    checkValid() {
      if (this.valid_Num == valid) { // 이메일로 발송한 인증번호와 사용자가 입력한 인증번호가 일치할 때
        console.log(valid);
        this.error.email = false;
        alert("인증번호가 확인되었습니다.");
        // this.error.email = "인증되었습니다."
      } else {
        this.error.email = "인증번호가 일치하지 않습니다.";
      }
    },
    signup() {
      axios
        .post(localhost_url + "/account/signup/", {
          email: this.email,
          password: this.password,
          nickname: this.nickname,
          gender: this.gender,
          birth_year: this.birth_year,
        })
        .then(({ data }) => {
          console.log(data.data);
          let msg = "회원가입 시 문제가 발생했습니다.";
          if (data.data === "success") {
            msg = "회원가입이 완료되었습니다.";
          }
          alert(msg);
          this.moveLogin();
        });
    },
    moveLogin() {
      this.$router.push("/");
    },
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
  },
};
</script>

<style>
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
</style>