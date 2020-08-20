<template>
  <div class="user" id="login">
    <menu></menu>
    <div class="wrapC">
      <br>
      <h1 style="text-align: center;">
        즐거운 시간을 함께 나눠요, Tugether😊
      </h1>

      <div class="embed-responsive embed-responsive-16by9">
        <!-- <img src="https://i3b303.p.ssafy.io/profileimages/튜게더로고.png" style="width:400px; height:400px;"> -->
        <iframe class="embed-responsive-item" src="https://www.youtube.com/embed/ozMBCFd7fFM?autoplay=1" allow="autoplay" allowfullscreen></iframe>
      </div>
      <br>
      <div class="input-with-label" v-show="!isLogin">
        <label for="email">이메일</label>

        <input
          v-model="email"
          v-bind:class="{error : error.email, complete:!error.email&&email.length!==0}"
          @keyup.enter="Login"
          id="email"
          ref="email"
          placeholder="이메일을 입력하세요."
          type="text"
        />
        <label for="email">이메일</label>
        <!-- <div class="error-text" v-if="error.email">{{error.email}}</div> -->
      </div>

      <div class="input-with-label" v-show="!isLogin">
        <input
          v-model="password"
          :type="passwordType"
          v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}"
          id="password"
          ref="password"
          @keyup.enter="Login"
          placeholder="비밀번호를 입력하세요."
        />
        <label for="password">비밀번호</label>
        <!--비밀번호 입력 시 아이콘을 누르면 입력타입을 변경해준다.(text, password)-->
        <span class="eye_icon" @click="showPW"><i class="far fa-eye fa-lg"></i></span>
      </div>

      <button
          class="btn btn--back btn--login"
          @click="checkHandler(email, password)"
          style="color: white;"
        >로그인</button>

      <!-- <div v-show="isLogin">
        <h2> 로그인 되었습니다 :) </h2>
      </div> -->

      <div class="add-option">
        <hr>
        <div class="google-login-btn">
          <GoogleLogin :params="params" :renderParams="renderParams" :onSuccess="googlelogin" style="float: right;">구글로 로그인 </GoogleLogin>
        </div>
        <div class="wrap" style="margin-top: 10px;">
          <router-link to="/user/join" class="btn--text">가입하기</router-link>
          <br>
          <router-link to="/passwordfind" class="btn--text">비밀번호 찾기</router-link>
        </div>
        <!-- <div class="g-signin2" data-onsuccess="onSignIn"></div> -->
        <!-- <GoogleLogin :params="params" :renderParams="renderParams" :onSuccess="googlelogin">Login</GoogleLogin> -->
        <!-- <Button @click="googlelogout()">Logout</Button> -->
      </div>
    </div>
  </div>
</template>
<script src="https://apis.google.com/js/platform.js?onload=init" async defer></script>
<style>
#test{
  background-color: red;
}
</style>

<script>

import "../../components/css/user.scss";
import "../../components/css/style.scss";
import PV from "password-validator";
import * as EmailValidator from "email-validator";
import UserApi from "../../api/UserApi";
import store from "../../vuex/store"
import * as axios from 'axios';
import { mapState, mapActions} from "vuex"
import { base } from "@/components/common/BaseURL.vue"; // baseURL
import GoogleLogin from 'vue-google-login';
import GoogleLoginButton from "../../components/user/snsLogin/Google.vue";

const storage = window.sessionStorage;

export default {
  name: 'Login',
  components:{
    GoogleLogin,
    GoogleLoginButton,
  },

  data: () => {
    return {
      status:"",
      token:"",
      login_user_token:"",
      info:"",
      message: "로그인해주세요.",
      email: "",
      password: "",
      passwordType: "password",
      passwordSchema: new PV(),
      nickname:"",
      error: {
        email: false,
        passowrd: false
      },
      isSubmit: false,
      component: this,
      params: {
          client_id: "963926899908-ncql9skkc6bkmifvg9bhc9jv2asecrcd.apps.googleusercontent.com"
      },
      renderParams: {
          width: 40,
          height: 40,
          longtitle: true,
          theme:'light'
      },
    };
  },
  created() {
    this.component = this;

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
  computed:{
    ...mapState(["isLogin", "isLoginError"])
  },

  watch: {
    password: function(v) {
      this.checkForm();
    },
    email: function(v) {
      this.checkForm();
    }
  },
  methods: {
    ...mapActions(["login", "googlelogin", "googlelogout", "googletoken"]), // store.js의 Actions에 정의한 함수를 쓰기 위해서 선언해준다.

    
    signOut(){
          // var auth2 = gapi.auth2.getAuthInstance();
          // auth2.signOut().then(function () {
          // console.log('User signed out.');
          // });
    },
    // 사용자가 입력하지 않은 칸이 있을 경우 포커스 이동 & 회원 정보가 일치할 경우에만 로그인 가능
    checkHandler(email, password) {
      let err = true;
      let msg = "";
      !this.email && ((msg = "이메일을 입력해주세요!"),(err = false),this.$refs.email.focus());
      err && !this.password && ((msg = "비밀번호를 입력해주세요!"),(err = false),this.$refs.password.focus());
      if (!err) {
        alert(msg);
      } else {
        this.login({email, password});
      }
    },

    checkForm() {
      if (this.email.length >= 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;

      if (
        this.password.length >= 0 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password = false;

      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },

    // 비밀번호 입력 시 아이콘을 누르면 입력타입 변경(text, password)
    showPW() {
      if (this.passwordType === "password") {
        this.passwordType = "text";
      } else {
        this.passwordType = "password";
      }
    },
    
    // onLogin() {
    //   if (this.isSubmit) {
    //     let { email, password } = this;
       
    //     let data = {
    //       email,
    //       password
    //     };

    //     //요청 후에는 버튼 비활성화
    //     this.isSubmit = false;

    //   }
    // },
    
    setInfo(status, token, info){
      this.status = status;
      this.token = token;
      this.info = info;
    },

    getInfo(){ //저장된 토큰을 사용하여 회원정보를 가져온다.
      axios
      .post(base + '/info',
        {
          email: this.email,
          password: this.password
        },
        {
          headers:{
            "jwt-auth-token": storage.getItem("jwt-auth-token")
          }
        }
      )
      .then(res=>{

        console.log("정보조회 성공")
        this.setInfo(
          "정보조회 성공!",
          res.headers.auth_token,
          JSON.stringify(res.data)
        );
      })
      .catch(e=>{
        console.log("정보조회 실패")
        this.setInfo("정보조회 실패",
          "",
          "e.rseponse.data.msg");
      });
    },
  
    init(){
      if(storage.getItem("jwt-auth-token")){
        this.message = storage.getItem("login_user")+" 로 로그인 되었습니다.";
      }else{
        storage.setItem("jwt-auth-token", "");
      }
    }

  },
  mounted(){
    this.init();
    
  },
};
</script>

<style scoped>
</style>