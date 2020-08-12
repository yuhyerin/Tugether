<template>
  <div>
    <input type="text" v-model="userComment" @keypress.enter="addComment"/>
    <button @click="addComment">작성</button>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import { base } from "@/components/common/BaseURL.vue"; // baseURL
import axios from 'axios'

export default {
  name: 'CommentInput',
  data() {
    return {
      userComment: [],
    }
  },
  computed:{
    ...mapState(["token"]), //store 공동 저장소에 있는 token 사용하기 위해 선언.
    ...mapActions(["getToken"]),
  },
  clicked(){
    // axios
    // then this.clicked=false;
  }
  ,
  methods: {
    addComment() {
      if (this.userComment.trim()) {
        const comment = {
          userComment: this.userComment,
          clickedLikeBtn(index) { 

          // this.clicked = true;
          axios.post(base + '/tugether/mainfeed/like',{
            headers: { 
              "jwt-auth-token": localStorage.getItem("token"),
              "article_id": this.articles[index].article_id,
            }
          })
          .then(response => {
            this.articles[index] = response.data.article;
            console.log(this.articles)
            this.clicked = true;
          })
          .catch(err => {
            console.log('실패함')
          })
        },
      }
        this.$emit('add-comment', comment)
        this.userComment = ''
    }
    }
  }
}
</script>

<style>

</style>