<template>
<div id="notice" style="margin-bottom: 65px; margin-top:20px;">
  <v-card max-width="344" class="mx-auto" v-for="(notice, index) in notices" :key="notice.id" style="margin-bottom:10px; margin-top:10px;">
    <v-list-item class="noticeitem">
      <v-list-item-avatar @click="moveUserpage(notice.notice_from)" class="mr-3" style="cursor:pointer;"><img :src="`https://i3b303.p.ssafy.io/profileimages/${notice.profile_photo}`"></v-list-item-avatar>
      <v-list-item-content>
        <v-list-item-title class="headline" v-if="notice.notice_type == 1" @click="moveArticleDetail(notice, index)" style="font-size:0.8rem;"><span class="nickname">{{ notice.from_nickname }}</span>님이 회원님의 게시글에 댓글을 남겼습니다.</v-list-item-title>
        <v-list-item-title class="headline" v-if="notice.notice_type == 2" @click="moveArticleDetail(notice, index)" style="font-size:0.8rem;"><span class="nickname">{{ notice.from_nickname }}</span>님이 회원님의 게시글을 좋아합니다.</v-list-item-title>
        <v-list-item-title class="headline" v-if="notice.notice_type == 3" @click="moveUserpage(notice.notice_from)" style="font-size:0.8rem;"><span class="nickname">{{ notice.from_nickname }}</span>님이 회원님을 팔로우하기 시작했습니다.</v-list-item-title>
        <v-list-item-subtitle class="date" style="font-size: 10px;">{{ timeForToday(notice.reg_time) }}</v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>
  </v-card>
  <BottomNav/> 
</div>
</template>

<script>
import axios from 'axios'
import { base } from "@/components/common/BaseURL.vue";
import BottomNav from "@/components/common/BottomNav";

export default {
  name: 'Notice',
  components: { 
    BottomNav,
  },
  data() {
    return {
      notices: [],
      reg_time: '',
      email: '',
    }
  },
  methods: {
    timeForToday(value) {
      const today = new Date();
      const timeValue = new Date(value);

      const betweenTime = Math.floor((today.getTime() - timeValue.getTime()) / 1000 / 60);

      if (betweenTime < 1) return '방금 전';
      if (betweenTime < 60) return `${betweenTime}분 전`;

      const betweenTimeHour = Math.floor(betweenTime / 60);
      if (betweenTimeHour < 24) return `${betweenTimeHour}시간 전`;

      const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
      if (betweenTimeDay < 365) return `${betweenTimeDay}일 전`;

      return `${Math.floor(betweenTimeDay / 365)}년 전`;
    },

    moveUserpage(user_email) {
      this.email = user_email;
      localStorage.setItem("userEmail", this.email);
      // console.log(this.email)
      this.$router.push({
        name: 'Userpage'
      })
    },

    moveArticleDetail(notices, index) {
      this.$router.push({
        name: 'Comment',
        params: {
          article_id: this.notices[index].article_id
        }
      })
    }
  },
   
  created() {
    axios.get(base + '/tugether/notice', {
      headers: { 
        "jwt-auth-token": localStorage.getItem("token"),
      }
    })
    .then(response => {
      this.notices = response.data.notices;
      // console.log(this.notices)
    })
    .catch(err =>{
        console.log("알림 안옴")
    })
  }
}
</script>

<style>
.noticeitem:hover {
  background-color: lightgray;
  cursor: pointer;
}
.nickname {
  font-weight: bold;
}
</style>