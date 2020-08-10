<template>
  <div class="wrapC">
    <h1 style="text-align: center; padding-top: 240px;">🍒비밀번호 찾기🍒</h1>
    <br>
    <div class="input-with-label">
      <label for="email">이메일</label>
      <input
        v-model="email"
        @keyup.enter="sendEmail"
        id="email"
        type="text"
      />

      <button
        class="btn btn--back btn--login"
        @click="sendEmail"
        style="margin-top: 2px;"
      >비밀번호 찾기</button>
      <button
        class="btn btn--back btn--login"
        @click="sendToLogin"
        style="margin-top: 2px;"
      >BACK</button>
      <!-- <button
        class="btn btn--back btn--login"
        style="display: none;"
        @click="sendEmail"
      >재전송</button> -->
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

    }
  },
  methods: {
    sendToMain() {
      this.$router.push('/feed/indexfeed')
    },
    sendToLogin() {
      this.$router.push('/')
    },
    sendEmail () {
      console.log(this.email)
      axios.get(base + '/account/findpw/'+ this.email)
        .then(res => {
          console.log(res.data.status)
          alert("이메일로 임시비밀번호가 발송되었습니다.")
          this.$router.push("/")
        })
        .catch(err => {
          alert("가입된 정보가 없습니다.")
        })
    }
  }
}
</script>

<style scoped>
/* .input-with-label {
  margin-top: 250px;
} */
</style>