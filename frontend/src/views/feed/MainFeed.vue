<template>
  <div id="mainfeed">
    <h1>뉴스피드({{ this.feed }})</h1>
    <br>
    <div class="change-tab" style="text-align:center; font-family: Arial, Helvetica">
      <button @click="getTagData" :style="tagTab"><h3>태그</h3></button> | 
      <button @click="getFollowData" :style="followTab"><h3>팔로우</h3></button>
      <a @click="logout"> <img src="@/assets/images/logout.png" height="50px" width="50px" style="float:right"/> </a>
    </div> 
    <br>
    <div class="feed-item" v-for="(article, index) in articles" :key="index" :articles="articles">
      <div class="top"> <!-- 프로필이미지, 작성자, 시간(며칠전..), 태그 -->
          <v-avatar size="50px" @click="moveUserpage"><img :src="`https://i3b303.p.ssafy.io/articleimages/${article.profile_photo}`"></v-avatar>
          <!-- <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div> -->
          <div class="user-info">
            <div class="user-name">
              <button>{{ article.writer }}</button>
            </div>
            <p class="date">{{ timeForToday(article.reg_time) }}</p>
          </div>
          <div class="content">
            <span v-for="tag in article.tag_name" :key="tag.name">
              #{{ tag }}
            </span>
          </div>
      </div>

      <div class="feed-body"> <!-- 태그 / 이미지사진/ 내용 / 유튜브 링크 -->
        <!-- <span v-for="tag in article.tag_name" :key="tag.name">
          #{{ tag }}
        </span> -->
        <img :src= "`https://i3b303.p.ssafy.io/articleimages/${article.image}`" class="mt-20; mb-20" alt="image">
        <p>{{ article.content }}</p>

      </div>

      <div class="feed-footer"> <!-- 버튼(좋아요, 댓글, 스크랩) /  -->
        <div class="feed-botton">
          <div class="heart-btn" @click="clickedLikeBtn(index)">
            <i class="far fa-heart" v-show="!article.like"></i>
            <i class="fas fa-heart" v-show="article.like"></i>
            <span class="like-cnt" v-if="article.like_cnt">{{ article.like_cnt }}명이 좋아합니다.</span>
          </div>
          <div class="comment-btn">
            <i class="far fa-comment-alt"></i>
          </div>
          <div class="scrap-btn" @click="clickedScrapBtn(index)">
            <i class="far fa-bookmark"></i>
            <!-- <i class="fas fa-bookmark"></i> -->
            <span class="scrap-cnt" v-if="article.scrap_cnt">{{ article.scrap_cnt }}회</span>
          </div>
        </div>
      </div>
    </div>
    <BottomNav/>
    <infinite-loading @infinite="infiniteHandler" spinner="spiral">
      <div slot="no-more" style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;">목록의 끝입니다 :)</div>
    </infinite-loading>
  </div>
</template>


<script>
import axios from 'axios'
import defaultProfile from "../../assets/images/profile_default.png";
import { mapState } from "vuex";
import "../../components/css/feed/feed-item.scss";
import "../../components/css/feed/newsfeed.scss";
import FeedItem from "../../components/feed/FeedItem.vue";
import store from "../../vuex/store"
import { base } from "@/components/common/BaseURL.vue"; // baseURL
import BottomNav from "@/components/common/BottomNav"
import InfiniteLoading from 'vue-infinite-loading';


const SERVER_URL = 'https://i3b303.p.ssafy.io'
export default {
  name: 'MainFeed',
  components:{
    BottomNav,
    InfiniteLoading,
  },
  data() {
    return {
      articles: [],
      defaultProfile,
      feed: '태그',
      token: "",
      tag: true,
      reg_time: '',
      clicked: false,
      tagTab: { color: 'red' },
      followTab: { color: 'black' },
      limit: 0,
    }
  },


  // watch:{
  //   clicked(){
  //     console.log("clickclick")
  //     axios.post(base + '/tugether/mainfeed/', {
  //       tag: this.tag,
  //     },
  //     {
  //       headers:{
  //         "jwt-auth-token": localStorage.getItem("token")
  //       }
  //     })
  //     .then(response => {
  //       console.log(response.data.list)
  //       this.articles = response.data.list;
  //       this.clicked=false;
  //     })
  //     .catch(err =>{
  //         console.log("망")
  //     })
  //     .finally(()=>{
  //         this.clicked=false;
  //     })
  //   }
  // },

  methods: {
    infiniteHandler($state) {
      const EACH_LEN = 1
      console.log('___BOTTOM___')
      console.log(this.limit)
      axios.get(base+'/tugether/mainfeed', {
        headers: {
          "jwt-auth-token": localStorage.getItem("token"),
          "limit": this.limit
        }
      })
      .then(response => {
        console.log(response.data)
        console.log('articles: '+response.data)
        setTimeout(() => {
          if(response.data.length) {
            this.articles = [...this.articles, ...response.data]
            $state.loaded();
            this.limit=this.limit+1;
          //  console.log('AFTERDATA ARRIVE!!', this.articles, response.data, this.limit)

            if(response.data.length / EACH_LEN < 1) {
              $state.complete()
            }
          } else {
            $state.complete()
          }
        }, 500)
      })
      .catch(err => {
        console.log('AFTERDATA NO!!')
      })
    },

    logout(){
      this.$store.commit('logout');
      localStorage.clear();
      alert("로그아웃 되었습니다 bye bye :)");
      this.$router.push("/");
    },
    
    getFollowData() {
      this.feed = '팔로우';
      this.followTab.color = 'red'
      this.tagTab.color = 'black'
      this.tag = false
      axios.post(base + '/tugether/mainfeed/', {
        tag: this.tag,
      },
      {
        headers:{
          "jwt-auth-token": localStorage.getItem("token")
          }
      })
      .then(response => {
        console.log('팔로우 기반 호출')
          this.articles = response.data.list;
          console.log(this.articles)
          this.clicked = true;
      })
      .catch(err =>{
          console.log("망")
      })
    },

    // 태그 기반의 글 목록 불러오기
    getTagData() {
      this.feed = '태그';
      this.tag = true
      this.followTab.color = 'black'
      this.tagTab.color = 'red'
      axios.post(base + '/tugether/mainfeed/', {
        tag: this.tag
      },
      {
        headers:{ 
          "jwt-auth-token": localStorage.getItem("token")
        }
      })
      .then(response => {
        console.log(response.data.list)
        console.log('태그기반 호출')
        this.articles = response.data.list;
        this.clicked = true; 
      })
      .catch(err =>{
          console.log("망")
      })
    },

    // 시간 체크
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


    clickedLikeBtn(index) { 

      this.clicked = true;
      axios.get(base + '/tugether/mainfeed/like',{
        headers: { 
          "jwt-auth-token": localStorage.getItem("token"),
          "article_id": this.articles[index].article_id,
        }
      })
      .then(response => {
        this.articles[index] = response.data.article;
        console.log(this.articles)
        // this.clicked = true;
      })
      .catch(err => {
        console.log('실패함')
      })
    },

    

    clickedScrapBtn(index) {
      // 스크랩 여부 확인
      axios.get(base + '/tugether/mainfeed/scrap', {
        headers: {
          "jwt-auth-token": localStorage.getItem("token"),
          "article_id": this.articles[index].article_id,
        }
      })
      .then(response => {
        if (response.data.scrapcheck) {
          alert('이미 스크랩한 게시물입니다.')
        } 
        else {
          // confirm창 띄우기
          var answer = confirm('스크랩 하시겠습니까?')
            // if 확인이면 axios.post
            if(answer==true){
              axios.post(base + '/tugether/mainfeed/scrap', {
                "article_id": this.articles[index].article_id,
              },
              {
                headers: {
                  "jwt-auth-token": localStorage.getItem("token"),
                }
              })
              .then(response => {
                this.articles[index] = response.data.article;
                console.log(response.data)
              })
            }
            // else면
        }
        this.clicked = true;
      })
      .catch(err => {
        console.log('스크랩 실패')
      })
    },

    
    moveUserpage() {
      this.$router.push("/mypage/userpage");
    },
  },

  // created() {
  //   console.log(this.limit)
  //   axios.get(base + '/tugether/mainfeed', {
  //     headers: {
  //       "jwt-auth-token": localStorage.getItem("token"),
  //       "limit": this.limit
  //     }
  //   })
  //   .then(response => {
  //     this.articles = response.data
  //     console.log('CREATED DATA ARRIVE', this.articles)
  //     this.limit = this.limit+1;
  //   })
  //   .catch(err => {
  //     console.log('CREATED DATA NO')
  //   })
  // },
}
</script>

<style scoped>
.feed-botton {
  margin: 15px 0;
  width: calc(100% - 50px);
}
.feed-botton div {
  float: left;
  margin-right: 30px;
  color:#000;
  margin-right: 10px;
  width: auto;
  font-size: 15px;
}

.feed-body > img {
  max-height: 360px;
  width: 100%;
}

</style>
<!--https://junistory.blogspot.com/2017/06/css-ellipsis.html 글자 수 제한-->
<!-- https://xetown.com/tips/1110772 -->