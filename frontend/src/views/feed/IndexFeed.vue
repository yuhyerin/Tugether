<template>
  <div class="feed newsfeed">
    <div class="wrapB">
      <h1>뉴스피드</h1>

      <button @click="logout">로그아웃</button>
      <button @click="writearticle">게시글 작성하러가기 </button>
      <button @click="moveMypage">마이페이지로 이동하기</button>

      <FeedItem />
      <FeedItem />
      <FeedItem />
      <FeedItem />
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import "../../components/css/feed/feed-item.scss";
import "../../components/css/feed/newsfeed.scss";
import FeedItem from "../../components/feed/FeedItem.vue";
import store from "../../vuex/store"

export default {
  props: ["keyword"],

  components: { FeedItem },

 
  methods:{
    logout(){
      
      this.$store.commit('logout');
      localStorage.clear();
      alert("로그아웃 되었습니다.");
      this.$router.push("/")
      
    },
    
    writearticle(){
      this.$router.push("/write")
    },

    moveMypage() {
      this.$router.push("/mypage/mypage");

    },
  }

};
</script>