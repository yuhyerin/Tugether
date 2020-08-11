<template>
  <div class="wrapC" style="height: 800px;">
    <div class="row">
      <div class="col" style="width: 80%;">
        <h2>상세보기</h2>
      </div>
      <div class="col" style="width: 20%;">
        <select name="options" id="options">
          <option>Options</option>
          <option value="change" @click="editWrite">수정</option>
          <option value="delete">삭제</option>
        </select>
        <button @click="getArticleId"></button>
      </div>
    </div>
    
    <div class="profile">
      <span>{{ profile_photo }}</span>
      <span>{{ writer }}</span>
    </div>
    <div class="articleImage">
      <img :src= "`https://i3b303.p.ssafy.io/articleimages/${image}`" alt="image">
    </div>
    <div class="content">
      {{ content }}
    </div>
    <div class="time">
      {{ reg_time }}
    </div>
    <hr>

    <h2>댓글작성</h2>
      <CommentInput @add-comment="addToComments" />
    <hr>
      <CommentList @edit-comment="editComments" @delete-comment="deleteComments" v-model="comments" />
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import CommentInput from '@/components/user/CommentInput'
import CommentList from '@/components/user/CommentList'
import axios from 'axios'

export default {
  name: 'Comment',
  components: {
    CommentInput,
    CommentList,
  },
  data() {
    return {
      profile_photo: "",
      writer: "",
      image: "",
      content: "",
      reg_time: "",
    }
  },
  computed:{
    ...mapState(["token"]), //store 공동 저장소에 있는 token 사용하기 위해 선언.
    ...mapActions(["getToken"]),
  },
  methods: {
    addToComments(comment) {
      this.comments = [...this.comments, comment]
    },
    editComments (com, idx) {
      
    },
    deleteComments(com, idx) {
      // this.comments.splice(idx, 1)
    },
    getArticleId() {
      this.article_id = 0
    }
  },
  created() {
    // bus.$on("sendToComment", a_id => {
    //   this.article_id = a_id
    //   console.log(this.article_id)
    // })
    console.log("comment.vue : " + this.article_id)
    axios.get('http://localhost:8080/tugether/mainfeed/comment',{
        headers: { 
          "jwt-auth-token": this.$store.state.token,
          "article_id": this.articles_id
          }
      })
      .then(res => {
        this.profile_photo = res.data.profile.profile_photo,
        this.writer = res.data.article.writer,
        this.image = res.data.article.image,
        this.content = res.data.article.content,
        this.reg_time = res.data.article.reg_time
      })
      .catch(err => {
        console.log('실패함')
      })
    },
}
</script>

<style>

</style>