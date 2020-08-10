<template>
  <div class="wrapC">
    <h1>알림알림</h1>
    <div class="notice">
      <ul>
        <li v-for="notice in notices" :key="notice.id">
          <v-avatar size="50px" @click="moveUserpage"><img :src=notice.profile_photo></v-avatar>
          <span v-if="notice.notice_type == 1" @click="moveArticleDatail">{{ notice.from_nickname }}님이 회원님의 게시글에 댓글을 남겼습니다.</span>
          <span v-else-if="notice.notice_type == 2" @click="moveArticleDetail">{{ notice.from_nickname }}님이 회원님의 게시글을 좋아합니다.</span>
          <span v-else @click="moveUserpage">{{ notice.from_nickname }}님이 회원님을 팔로우하기 시작했습니다.</span>
          <span class="date">{{ timeForToday(notice.reg_time) }}</span>
          <!-- <img :src=notice. alt="image"> -->
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { base } from "@/components/common/BaseURL.vue";

export default {
  name: 'Notice',
  data() {
    return {
      notices: [],
      reg_time: '',
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

    moveUserpage() {
      this.$router.push("/mypage/userpage");
    },

    moveArticleDetail() {
      this.$router.push("/mainfeed/:")
    }
  },
   
  created() {
    axios.get(base + '/tugether/notice', {
      headers: { 
        "jwt-auth-token": localStorage.getItem("token"),
      }
    })
    .then(response => {
      console.log('알림 호출')
      this.notices = response.data.list;
      console.log(this.notices)
    })
    .catch(err =>{
        console.log("알림 안와")
    })
  }
}
</script>

<style>

</style>