<template>
  <div class="wrapC">
    <div class="row">
      <div class="col" style="width: 100%; padding-bottom: 0px;">
        <br>
        <v-card 
          max-width="550"
          class="mx-auto"
        >
        <v-list-item style="padding: 5px 16px 5px 16px;">
          <v-list-item-avatar style="max-height: 40px; max-width: 40px; width: 100%; height: 100%; margin-right: 10px;">
            <v-img :src="`https://i3b303.p.ssafy.io/profileimages/${article.profile_photo}`" alt="article.profile_photo" @click="movePage(article.email)" style="cursor: pointer;"></v-img>
          </v-list-item-avatar>
          <v-list-item-content style="padding-top: 0px; padding-bottom: 0px;">
            <v-list-item-title style="font-size:0.8rem;">{{ article.writer }}</v-list-item-title>
            <v-list-item-subtitle>
              <span style="color: black; white-space: normal;">{{ article.content }}</span> &dash;
              <span style="font-size:0.7rem;">{{ timeForToday(article.reg_time) }}</span> &emsp;
            </v-list-item-subtitle>
          </v-list-item-content>
          </v-list-item>
        </v-card>
      </div>
    </div>
      <div class="row" style="height: 70px;">
        <!-- <v-col sm=11 style="padding-top: 2px;"> -->
        <div class="col-10" style="padding-top: 2px;">
          <v-text-field
            label="댓글"
            single-line
            v-model="userComment"
            @keypress.enter="addComment"
          ></v-text-field>
        <!-- </v-col>
        <v-col style="align: right;"> -->
        </div>
        <div class="col-2">
          <v-btn style="background: black;" fab small dark @click="addComment">
            <v-icon>mdi-pencil</v-icon>
          </v-btn>
        </div>
      </div>
        <!-- </v-col>
      </v-row> -->
  <v-card
    max-width="550"
    class="mx-auto"
    style="margin-bottom: 50px;"
  >
    <v-list v-for="(comment, index) in comments" :key="index">
        <v-list-item
        >
          <v-list-item-avatar style="margin-right: 10px;">
            <v-img :src="`https://i3b303.p.ssafy.io/profileimages/${comment.profile_photo}`" alt="comment.profile_photo" @click="moveUserpage(index)" style="cursor: pointer;"></v-img>
          </v-list-item-avatar>

          <v-list-item-content style="padding-top: 0px; padding-bottom: 0px;">
            <v-list-item-title style="font-size:0.8rem;">{{ comment.nickname }}</v-list-item-title>
            <v-list-item-subtitle>
              <span style="color : black; font-weight:bold; white-space: normal;">{{ comment.content }}</span> &dash;
              <span style="font-size:0.7rem;">{{ timeForToday(comment.reg_time) }}</span> &emsp;
              <span><button v-show="email === comment.email" @click="commentDelete(index)" style="width: 35px; height: 25px; background: crimson; border-radius: 6px;">삭제</button></span>
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
    </v-list>
  </v-card>
    <BottomNav />
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import store from '@/vuex/store'
import { base } from "@/components/common/BaseURL.vue"
import axios from 'axios'
import BottomNav from "@/components/common/BottomNav"

export default {
  name: 'Comment',
  components: {
    BottomNav
  },
  data() {
    return {
      comments: [],
      clicked: false,
      userComment: '',
      email: '',
      article: [],
    }
  },
  watch: {
    clicked(){
      console.log("clickclick")
      axios.get(base + '/tugether/mainfeed/comment',
      {
        params:{
          "article_id": parseInt(this.$route.params.article_id)
        },
        headers: { 
          "jwt-auth-token": localStorage.getItem("token"),
        }
      })
      .then(res => {
        this.comments = res.data.comments;
        this.article = res.data.article;
        console.log(this.article)
      })
      .catch(err => {
        console.log('실패함')
      })
      .finally(()=>{
        this.clicked=false;
      }) 
    }
  },
  // 따로 commit으로 함수 실행시키는게 아니라 computed에 안적어도 동작에는 문제없는듯
  // computed:{
  //   ...mapState(["token", "email"]), //store 공동 저장소에 있는 token 사용하기 위해 선언.
  //   ...mapActions(["getToken", "getEmail"])
  // },
  methods: {
    addComment() {
      this.clicked = true;
      console.log(this.clicked ? 'click : true' : 'click : false')
      if (this.userComment.trim()) {
        axios.post(base + '/tugether/mainfeed/comment', {
          article_id: this.$route.params.article_id,
          // 백엔드를 위한 email null값
          email: '',
          content: this.userComment.trim()
          },
          {
            headers: { 
              "jwt-auth-token": localStorage.getItem("token")
            }
          })
          .then(response => {
            console.log(response.data)
            this.userComment = ''
          })
          .catch(err => {
            console.log('실패함')
          })
        }
    },
    commentDelete(index) {
      console.log(this.comments[index].comment_id)
      console.log(localStorage.getItem("token"))
      axios.get(base + '/tugether/mainfeed/deleteComment',  
        {
          params: {
            "comment_id": parseInt(this.comments[index].comment_id)
          },
          headers: {
            "jwt-auth-token": localStorage.getItem("token")
          }
        })
        .then(response => {
          console.log(response.data)
          this.clicked = true
        })
        .catch(err => {
          console.log('댓글삭제실패함')
        })
    },
    moveUserpage(index){
      if (this.comments[index].email !== localStorage.getItem("email")) {
      // store.commit('getUserEmail', this.comments[index].email)
      localStorage.setItem("userEmail", this.comments[index].email)
      this.$router.push({
        name: 'Userpage'
      })
      } else {
        this.$router.push({
          name: 'Mypage'
        })
      }
    },
    movePage(email) {
      if (email !== localStorage.getItem("email")) {
        localStorage.setItem("userEmail", email)
        this.$router.push({
          name: 'Userpage'
        })
      } else {
        this.$router.push({
          name: 'Mypage'
        })
      }
    },

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
  },
  created() {
    // console.log('Comment.vue 입장')
    this.email = localStorage.getItem("email")
    // console.log(this.email)
    // console.log('article_id : '+this.$route.params.article_id)
    // console.log("comment.vue : " + this.$route.params.article_id)
    axios.get(base + '/tugether/mainfeed/comment',
      {
        params: {
          "article_id": this.$route.params.article_id
        },
        headers: { 
          "jwt-auth-token": localStorage.getItem("token")
        }
      })
      .then(res => {
        this.comments = res.data.comments;
        this.article = res.data.article;
        // console.log(this.article)
        // console.log(this.comments);
      })
      .catch(err => {
        console.log('실패함')
      })
    },
}
</script>

<style scoped>
.wrapC {
  margin-bottom: 65px;
}

[v-clock] ::before {
  content: '로딩중';
}

 /* td {
   padding-top: 5px;
 } */
</style>