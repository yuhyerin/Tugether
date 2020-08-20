<template>
    <v-bottom-navigation fixed>
      <v-btn value="home" @click="sendToHome">
        <span>Home</span>
        <v-icon style="color: black; padding-top: 20px;">home</v-icon>
      </v-btn>

      <v-btn value="search" @click="sendToSearchPage">
        <span>Search</span>
        <v-icon style="color: red; padding-top: 20px;">mdi-magnify</v-icon>
      </v-btn>

      <v-btn @click="senToMyPage">
        <span>MyPage</span>
        <v-icon style="color: black; padding-top: 20px;">mdi-account-circle</v-icon>
      </v-btn>
      
      <v-btn @click="sendToNotice">
        <span>Alarm</span>
        <v-icon style="color: red; padding-top: 20px;">mdi-bell</v-icon>
      </v-btn>

      <v-btn @click="senToStatPage">
        <span>Stats</span>
        <v-icon style="color: black; padding-top: 20px;">mdi-chart-bar</v-icon>
      </v-btn>
    </v-bottom-navigation>
</template>

<script>

export default {
  name: "BottomNav",
  methods: {
    sendToHome () {
        this.$router.push('/mainfeed')
    },
    senToMyPage () {
        this.$router.push('/mypage')
    },
    sendToNotice () {
        this.$router.push('/notice')
    },
    senToStatPage () {
        this.$router.push('/stats')
    },
    sendToSearchPage () {
        scroll(0,0); // 페이지 최상단으로 이동
        this.$router.push('/search');
    }
  }
}
</script>

<style scoped>
</style>