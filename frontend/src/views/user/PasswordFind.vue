<template>
  <div class="wrapC">
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
      >전송</button>
      <!-- <button
        class="btn btn--back btn--login"
        style="display: none;"
        @click="sendEmail"
      >재전송</button> -->
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'PasswordFind',
  data: () => {
    return {
      // isSubmit: "false",
      email: "",

    }
  },
  methods: {
    sendEmail () {
      console.log(this.email)
      axios.get(`http://localhost:8080/account/findpw/`+ this.email)
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
.input-with-label {
  margin-top: 250px;
}
</style>