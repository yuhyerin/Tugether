<template>
  <div class="wrapC">
    <div class="notice">
      <h1>알림</h1>
      <div class="media" v-for="(notice, index) in notices" :key="notice.id">
        <!-- <div class="timenotice">{{}}</div> -->
         <!-- <p>{{ getMonday(notice.reg_time)}}</p> -->
      
        <v-avatar size="40px" @click="moveUserpage(notice.notice_from)" class="mr-3" style="hover"><img :src="`https://i3b303.p.ssafy.io/profileimages/${notice.profile_photo}`"></v-avatar>
        <div class="media-body">
          <span v-if="notice.notice_type == 1" @click="moveArticleDetail(index)">{{ notice.from_nickname }}님이 회원님의 게시글에 댓글을 남겼습니다.</span>
          <span v-else-if="notice.notice_type == 2" @click="moveArticleDetail(index)">{{ notice.from_nickname }}님이 회원님의 게시글을 좋아합니다.</span>
          <span v-else-if="notice.notice_type == 3" @click="moveUserpage(notice.notice_from)">{{ notice.from_nickname }}님이 회원님을 팔로우하기 시작했습니다.</span>
          <span class="date" style="font-size: 10px;">{{ timeForToday(notice.reg_time) }}</span>
        </div>
      </div>

    </div>

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
    }
  },
  methods: {
    getMonday(d) {
      d = new Date(d);
      var day = d.getDay(),
      diff = d.getDate() - day + (day == 0 ? -6:1); // adjust when day is sunday
      return new Date(d.setDate(diff));
      
    },

    // timeClass() {
    //   const date = new Date();
    //   if (type === 'day') {
    //     regStart = formatDate(date)
    //     regEnd = formatDate(new Date(date.valueOf() + 1000 * 3600 * 24))
    //     console.log(regStart, regEnd)
    //   } else if (type === 'week') { // 이번주 월요일 ~ 일요일
    //     regStart = new Date(date.getFullYear(), date.getMonth(), 1)
    //     regEnd = new Date(date.getFullYear(), date.getMonth()+1, 0)
    //   } else if (type === 'month') { // 이번달 1일 ~ 31일
    //     regStart = formatDate(date)
    //     regEnd = formatDate(new Date(date.valueOf() + 1000 * 3600 * 24 * 31))
    //   }
    // },
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
      this.$router.push({
        name: 'Userpage',
        params: {
          user_email: this.user_email
        },
        // path: "/userpage"
      });
    },


    moveArticleDetail(index) {
      console.log(this.notices[index])
      this.$router.push({
        name: 'Comment',
        params: {
          "article_id": this.notices[index].article_id
        }
      })
    },
  },
   
  created() {
    axios.get(base + '/tugether/notice', {
      headers: { 
        "jwt-auth-token": localStorage.getItem("token"),
      }
    })
    .then(response => {
      console.log('알림 호출')
      this.notices = response.data.notices;
      console.log(this.notices)
    })
    .catch(err =>{
        console.log("알림 안와")
    })
  }
}
</script>

<style>
.wrapC {
  margin-bottom: 65px;
}

.media {
  background-color: rgba(0, 0, 0, .03);
  margin-bottom: 7px;
  border-radius: 3px;
  padding: 7px;
  text-align: left;
}
.media:hover {
  background-color: lightgray;
  cursor: pointer;
}

.notice > h1 {
  text-align:center; 
  font-weight:bold; 
  font-size:2.5em; 
  font-family: Arial, Helvetica;
  padding: 15px 0px;
}
</style>