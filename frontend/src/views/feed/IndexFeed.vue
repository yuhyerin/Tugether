<template>
  <div class="feed newsfeed">
    <div class="wrapB">
      <h1>뉴스피드</h1>
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

  methods : {
    moveMypage() {
      this.$router.push("/mypage/mypage");
    },
  }

};
</script>