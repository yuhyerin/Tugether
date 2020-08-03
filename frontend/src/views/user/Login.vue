<template>
  <div class="user" id="login">

    <menu></menu>
    <div class="wrapC">
      <br>
      <h1>
        즐거운 시간을 함께 나눠요, Tugether😊
      </h1>

      <div class="embed-responsive embed-responsive-16by9">
        <iframe class="embed-responsive-item" src="https://www.youtube.com/embed/ozMBCFd7fFM?autoplay=1" allowfullscreen></iframe>
      </div>
      <br>
      <div class="input-with-label" v-show="!isLogin">
        <label for="email">이메일</label>

        <input
          v-model="email"
          v-bind:class="{error : error.email, complete:!error.email&&email.length!==0}"
          @keyup.enter="Login"
          id="email"
          placeholder="이메일을 입력하세요."
          type="text"
        />
        <label for="email">이메일</label>
        <div class="error-text" v-if="error.email">{{error.email}}</div>
      </div>

      <div class="input-with-label" v-show="!isLogin">
        <input
          v-model="password"
          type="password"
          v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}"
          id="password"
          @keyup.enter="Login"
          placeholder="비밀번호를 입력하세요."
        />
        <label for="password">비밀번호</label>
        <div class="error-text" v-if="error.password">{{error.password}}</div>
      </div>
      
        <button
          v-show="!isLogin"
          class="btn btn--back btn--login"
          @click="login({email, password})"
          :disabled="!isSubmit"
          :class="{disabled : !isSubmit}"
        >로그인</button>
      

      <div v-show="isLogin">
        <h2> 로그인 되었습니다 :) </h2>
      </div>

      <div class="sns-login">
        <!-- <div class="text"> -->
          <!-- <p>SNS 간편 로그인</p>
          <div class="bar"></div> -->
        <!-- </div> -->

        <!-- <kakaoLogin :component="component" />
        <GoogleLogin :component="component" /> -->
      </div>
      <div class="add-option">
        <hr>
        <br>
        <div class="wrap">
          <router-link to="/user/join" class="btn--text">가입하기</router-link>
          <br>
          <router-link to="/passwordfind" class="btn--text">비밀번호 찾기</router-link>

        </div>
      </div>
  <v-bottom-navigation
    v-model="bottomNav"
    dark
    shift
  >
    <v-btn>
      <span>Video</span>
      <v-icon>mdi-television-play</v-icon>
    </v-btn>

    <v-btn>
      <span>Music</span>
      <v-icon>mdi-music-note</v-icon>
    </v-btn>

    <v-btn>
      <span>Book</span>
      <v-icon>mdi-book</v-icon>
    </v-btn>

    <v-btn>
      <span>Image</span>
      <v-icon>mdi-image</v-icon>
    </v-btn>
  </v-bottom-navigation>

    </div>

  </div>
</template>

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
// import KakaoLogin from "../../components/user/snsLogin/Kakao.vue";
// import GoogleLogin from "../../components/user/snsLogin/Google.vue";
import UserApi from "../../api/UserApi";
import store from "../../vuex/store"
import * as axios from 'axios';
import { mapState, mapActions} from "vuex"
import Menu from '../menu/Menu';

const storage = window.sessionStorage;
// const ai = axios.create({
//     baseURL: "http://127.0.0.1:8080/account/"
// });

export default {
  name: 'Login',
  component:{

    'menu': Menu
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
      passwordSchema: new PV(),
      nickname:"",
      error: {
        email: false,
        passowrd: false
      },
      isSubmit: false,
      component: this
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
    ...mapActions(["login"]), // store.js의 Actions에 정의한 함수를 쓰기 위해서 선언해준다.

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
    
    onLogin() {
      if (this.isSubmit) {
        let { email, password } = this;
       
        let data = {
          email,
          password
        };

        storage.setItem("jwt-auth-token", "");
        storage.setItem("login_user","");
        

        // ai.post("/signin",
        //   {email: this.email,
        //   password: this.password
        //   })
        //   .then(res=>{
        //     console.log(res.data.status) // true 
        //     if(res.data.status){
        //         this.message = res.data.data.email+"로 로그인 되었습니다.";
        //         this.nickname = res.data.data.nickname;
        //         console.log(this.message);
        //         console.log(this.nickname);
        //         console.log("토큰: "+res.headers["jwt-auth-token"]);
        //         this.setInfo(
        //           "성공",
        //           res.headers["jwt-auth-token"],
        //           JSON.stringify(res.data.data)
        //         );
        //         storage.setItem("jwt-auth-token", res.headers["jwt-auth-token"]);
        //         storage.setItem("login_user", res.data.data.email);
        //         store.state.login_user_token =  res.headers["jwt-auth-token"];
        //         store.state.login_user_nickname = res.data.data.nickname;
        //         this.isSubmit = true;
                
        //         alert("로그인 성공! 환영합니다 :)");
        //         this.$router.push("/feed/main");
        //     }else{
        //       this.setInfo("", "", "");
        //       this.message = "로그인해주세요.";
        //       alert("입력정보를 확인하세요.");
        //     }
        //   })
        //   .catch(e=>{
        //     this.isSubmit=true;
        //     alert("이메일과 비밀번호를 확인해 주세요");
        //     this.email = "";
        //     this.password=""
        //     this.setInfo("실패",
        //                 "",
        //                 JSON.stringify(e.response || e.message));
        // });


        //요청 후에는 버튼 비활성화
        this.isSubmit = false;

      }
    },
    
    setInfo(status, token, info){
      this.status = status;
      this.token = token;
      this.info = info;
    },

    getInfo(){ //저장된 토큰을 사용하여 회원정보를 가져온다.
      axios.post(
        'http://127.0.0.1:8080/info',
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


