<template>
  <div class="wrapC" style="height: 800px;">
    <div class="row">
      <div class="col" style="width: 100%;">
        <h3>피드영역 들어와야함</h3>
      </div>
    </div>
    
    <!-- <div class="profile">
      <span>{{ profile_photo }}</span>
      <span>{{ nickname }}</span>
    </div>

    <div class="content">
      {{ content }}
    </div>

    <div class="time">
      {{ reg_time }}
    </div> -->
    <!-- <hr>

    <h3>댓글작성</h3> -->
      <v-row>
        <v-col sm=10 style="padding-top: 2px;">
          <v-text-field
            label="댓글"
            single-line
            v-model="userComment"
          ></v-text-field>
        </v-col>
   
        <!-- <input type="text" v-model="userComment" @keypress.enter="addComment" style="width: 90%;"/> -->
        <!-- <button @click="addComment" style="margin-left: 2px; width: 10%; background: lightgray;">작성</button> -->
        <v-col>
          <v-btn style="background: black; margin-left: 27px;" fab small dark @click="addComment">
            <v-icon>mdi-pencil</v-icon>
          </v-btn>
        </v-col>
      </v-row>
    <hr>
    <div>
    <!-- <h3>댓글목록</h3> -->
      <table>
        <colgroup>
          <col style="width: 10%;">
          <col style="width: 20%;">
          <col style="width: 46%;">
          <col style="width: 19%;">
          <!-- <col style="width: 8%;"> -->
          <col style="width: 5%;">
        </colgroup>
        <thead>
          <tr>
            <th scope="commentWriterProfilePhoto"></th>
            <th scope="commentWriterNickName"></th>
            <th scope="commentWriterContent"></th>
            <th scope="commentWriterRegTime"></th>
            <!-- <th scope="commentWriterRegTime"></th> -->
            <th scope="commentDelete"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(comment, index) in comments" :key="index">
            <td scope="col"><img :src="`https://i3b303.p.ssafy.io/profileimages/${comment.profile_photo}`" alt="comment.profile_photo" style="width: 100%; height: 100%; max-width: 30px; max-height: 30px;"></td>
            <td scope="col">{{ comment.nickname }}</td>
            <td scope="col">{{ comment.content }}</td>
            <td scope="col" style="color: gray;">{{ timeForToday(comment.reg_time) }}</td>
            <!-- <td scope="col" v-show="userEmail === comment.email"><button style="width: 35px; height: 25px; background: skyblue; border-radius: 5px;">수정</button></td> -->
            <td scope="col" v-show="userEmail === comment.email" @click="commentDelete(index)"><button style="width: 40px; height: 30px; background: crimson; border-radius: 6px;">삭제</button></td>
          </tr>
        </tbody>
      </table>
    </div>
    <!-- <v-simple-table>
      <template v-slot:default>
        <thead>
          <tr>
            <th class="text-left"></th>
            <th class="text-left"></th>
            <th class="text-left"></th>
            <th class="text-left"></th>
            <th class="text-left"></th>
            <th class="text-left"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(comment, index) in comments" :key="index">
            <td><img :src="`https://i3b303.p.ssafy.io/profileimages/${comment.profile_photo}`" alt="comment.profile_photo" style="width: 100%; height: 100%; max-width: 40px; max-height: 40px;"></td>
            <td>{{ comment.nickname }}</td>
            
            <td>{{ comment.content }}</td>
            <td>{{ timeForToday(comment.reg_time) }}</td>
            <td v-show="userEmail === comment.email">수정</td>
            <td v-show="userEmail === comment.email" @click="commentDelete(index)">삭제</td>
          </tr>
        </tbody>
      </template>
    </v-simple-table> -->
    <!-- <td><input type="text" readonly="readonly" value=""/>{{ comment.content }}</td> -->
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
      userEmail: ''
    }
  },
  watch: {
    clicked(){
      console.log("clickclick")
      axios.get(base + '/tugether/mainfeed/comment',
      {
        headers: { 
          "jwt-auth-token": localStorage.getItem("token"),
          "article_id": this.$route.params.article_id
        }
      })
      .then(res => {
        this.comments = res.data.comments;
        console.log(this.comments)
      })
      .catch(err => {
        console.log('실패함')
      })
      .finally(()=>{
        this.clicked=false;
      }) 
    }
  },
  computed:{
    ...mapState(["token", "email"]), //store 공동 저장소에 있는 token 사용하기 위해 선언.
    ...mapActions(["getToken", "getEmail"])
  },
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
          headers: {
            "jwt-auth-token": localStorage.getItem("token"),
            "comment_id": this.comments[index].comment_id
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
    this.userEmail = localStorage.getItem("email")
    console.log(this.userEmail)
    console.log("comment.vue : " + this.$route.params.article_id)
    axios.get('http://localhost:8080/tugether/mainfeed/comment',
      {
        headers: { 
          "jwt-auth-token": localStorage.getItem("token"),
          "article_id": this.$route.params.article_id
        }
      })
      .then(res => {
        this.comments = res.data.comments;
        console.log(this.comments);
      })
      .catch(err => {
        console.log('실패함')
      })
    },
}
</script>

<style scoped>
 td {
   padding-top: 10px;
 }
</style>