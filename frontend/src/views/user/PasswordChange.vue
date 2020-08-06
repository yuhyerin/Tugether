<template>
  <div class="wrapC">
      <br>
      <br>
      <br>
      <br>
      <br>
      <br>
      <br>
      <br>
    <div class="input-with-label">
        <label for="email">이메일</label>
        <input type="text"
          id="email"
          v-model="email"
          readonly="readonly"
        />

      </div>
      <div class="input-with-label">
        <label for="password">신규 비밀번호</label>
        <input :type="passwordType" id="password" v-model="password" />
        <!--비밀번호 입력 시 아이콘을 누르면 입력타입을 변경해준다.(text, password)-->
        <span class="icon" @click="showPW1"><i class="far fa-eye fa-lg"></i></span>
      </div>

      <div class="input-with-label">
        <label for="password">신규 비밀번호 확인</label>
        <input :type="passwordConfirmType" id="password-confirm" v-model="passwordConfirm" @keyup.enter="passwordCheck" />
        <!--비밀번호 입력 시 아이콘을 누르면 입력타입을 변경해준다.(text, password)-->
        <span class="icon" @click="showPW2"><i class="far fa-eye fa-lg"></i></span>
      </div>

      <button
        class="btn btn--back btn--login"
        @click="passwordCheck"
      >변경하기</button>

      <button
        class="btn btn--back btn--login"
        @click="sendToMain"
        style="margin-top: 1px;"
      >다음에 변경</button>

      <v-bottom-navigation
      v-model="bottomNav"
      fixed
    >
      <v-btn value="home">
        <span>Home</span>
        <v-icon>home</v-icon>
      </v-btn>

      <v-btn value="recent">
        <span>Search</span>
        <v-icon>fas fa-search</v-icon>
      </v-btn>

      <v-btn>
        <span>MyPage</span>
        <v-icon>mdi-book</v-icon>
      </v-btn>
      
      <v-btn value="favorites">
        <span>Alarm</span>
        <v-icon>mdi-history</v-icon>
      </v-btn>

      <v-btn value="nearby">
        <span>Stats</span>
        <v-icon>mdi-map-marker</v-icon>
      </v-btn>
    </v-bottom-navigation>

    </div>
      <!-- 이건 추후 비밀번호 '변경'에서! -->
      <!-- <label for="email">기존 비밀번호</label>
      <input type="text"
      />
    </div>

    <div class="input-with-label">
      <input type="password"
      />
      <label for="password">신규 비밀번호</label>
    </div>

    <div class="input-with-label">
      <input type="password"
      />
      <label for="password">신규 비밀번호 확인</label>
    </div>

    <button class="btn btn--back btn--login"
      >변경
    </button>
  </div> -->
  
</template>

<script>
import axios from 'axios';
import store from '@/vuex/store';
import { base } from "@/components/common/BaseURL.vue"; // baseURL

export default {
    name: 'PasswordChange',
    data: () => {
      return {
        password: "",
        passwordConfirm: "",
        bottomNav: 'recent',
        email: "",
        passwordType: "password",
        passwordConfirmType: "password"
      }
    },
    created(){
      this.email=this.$store.state.email;
    },
    // watch: {
    //   passwordConfirm: function () {
    //     if (this.passwordConfirm !== this.password) {
    //       alert(" ")
    //     }
    //   }
    // },
    methods: {
      passwordCheck () {
        if (this.passwordConfirm !== this.password) {
          alert("비밀번호가 일치하지 않습니다.")
          return
        }
        // 이것을 통해 비밀번호가 일치할 경우에 sendPassword 메소드로 넘어간다.
        this.sendPassword();
      },
      sendPassword () {
        console.log(this.email)
        axios.post(base + '/account/changepw', {
          email: this.email,
          password: this.password,
          // passwordConfirm: this.passwordConfirm
          })
          .then(res => {
            console.log(res.data.status)
            alert("비밀번호가 변경되었습니다.")
            // 관심 태그 선택안한 사람이면 메인이 아니라 태그 선택으로 이동
            this.$router.push("/")
          })
          .catch(err => {
            alert("비밀번호를 다시 설정해주세요.")
          })
      },
      sendToMain () {
        // 관심태그 안한 사람이면 메인이 아니라 태그 선택으로 이동
        this.$router.push("/feed/main")
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

</style>