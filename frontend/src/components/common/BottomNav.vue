<template>
    <v-bottom-navigation
    fixed
    >
      <v-btn value="home" @click="sendToHome">
        <span >Home</span>
        <v-icon style="color: black;">home</v-icon>
      </v-btn>

      <v-btn value="search">
        <span>Search</span>
        <v-icon style="color: red;">mdi-magnify</v-icon>
      </v-btn>

      <v-btn @click="senToMyPage">
        <span>MyPage</span>
        <v-icon style="color: black;">mdi-account-circle</v-icon>
      </v-btn>
      
      <v-btn>
        <span>Alarm</span>
        <v-icon style="color: red;">mdi-heart</v-icon>
      </v-btn>

      <v-btn>
        <span>Stats</span>
        <v-icon style="color: black;">mdi-chart-bar</v-icon>
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
  }
}
</script>

<style>

</style>