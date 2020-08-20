<template>
  <div class="wrapC" style="margin-top: 50%;">
    <h1 style="text-align: center;">🍒비밀번호 찾기🍒</h1>
    <br>
    <strong>등록된 이메일로 임시비밀번호를 발송합니다.</strong>
    <div class="input-with-label">
      <label for="email">이메일</label>
      <input v-model="email" @keyup.enter="sendEmail" id="email" placeholder="이메일을 입력해주세요." type="text" />

      <div class="pwfind_buttons">
        <!--로그인 화면으로 돌아가기-->
        <button class="button" :style="mybtn2" @mouseover="over2" @mouseout="out2" @click="sendToLogin"
          style="margin-top: 2px; width: 48%; height: 45px; float: left;">BACK</button>
        <!--등록된 이메일로 임시비밀번호 발급하기-->
        <button class="button" :style="mybtn1" @mouseover="over1" @mouseout="out1" @click="sendEmail"
          style="margin-top: 2px; width: 48%; height: 45px; margin-left: 10px; float: right;" >비밀번호 찾기</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { base } from "@/components/common/BaseURL.vue"; // baseURL

export default {
  name: 'PasswordFind',
  data: () => {
    return {
      // isSubmit: "false",
      email: "",
      mybtn1: {
        backgroundColor: "black"
      },
      mybtn2: {
        backgroundColor: "black"
      }
    }
  },
  methods: {
    sendToMain() {
      this.$router.push('/feed/indexfeed');
    },
    sendToLogin() {
      this.$router.push('/');
    },
    sendEmail () {
      console.log(this.email)
      axios.get(base + '/account/findpw/'+ this.email)
        .then(res => {
          console.log(res.data.status)
          alert("이메일로 임시비밀번호가 발송되었습니다.");
          this.sendToLogin();
        })
        .catch(err => {
          alert("가입된 정보가 없습니다.");
        })
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
    }
  }
}
</script>

<style scoped>
  .pwfind_buttons{
    width: 100%;
    margin: 0 auto;
    display: inline-block;
    padding-top: 20px;
    color: white;
  }
</style>