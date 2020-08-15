<template>
  <div class="container">
    <div id="mainfeed">
      <h1>뉴스피드({{ this.feed }})</h1>
      <br>
      <div class="change-tab" style="text-align:center; font-family: Arial, Helvetica">
        <button @click="getTagData" :style="tagTab"><h3>태그</h3></button> | 
        <button @click="getFollowData" :style="followTab"><h3>팔로우</h3></button>
        <a @click="logout"> <img src="@/assets/images/logout.png" height="50px" width="50px" style="float:right"/> </a>
      </div> 
      <br>
      <div class="wrapC" v-for="(article, index) in articles" :key="article.id" :articles="articles">
        <div class="feed-item">
          <div class="top"> <!-- 프로필이미지, 작성자, 시간(며칠전..), 태그 -->
            <v-avatar size="50px" @click="moveUserpage(article.email)"><img :src="`https://i3b303.p.ssafy.io/articleimages/${article.profile_photo}`"></v-avatar>
            <!-- <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div> -->
            <div class="user-info">
              <div class="user-name">
                <button @click="moveUserpage(article.email)" style="font-size: 15px;">{{ article.writer }}</button>
              </div>
              <p class="date">{{ timeForToday(article.reg_time) }}</p>
            </div>
            <div class="content" style="margin-top: -25px;">
              <span v-for="tag in article.tag_name" :key="tag.name">
                #{{ tag }}
              </span>
            </div>
          </div>

          <div class="feed-card"> <!-- 태그 / 이미지사진/ 내용 / 유튜브 링크 -->
            <!-- <span v-for="tag in article.tag_name" :key="tag.name">
              #{{ tag }}
            </span> -->
            <img :src= "`https://i3b303.p.ssafy.io/articleimages/${article.image}`" class="mt-20; mb-20" alt="image">
            <div class="contentsWrap">
              <h4 class="title">{{ article.content }}</h4>
              <div class="wrap">
                <div class="url">
                  <a :href="article.link" v-if="article.link" target="_blank"><img src="@/assets/images/youtube.png" alt="" style="width:25px; height:25px;"></a>
                  <!-- <a :href="article.link" v-if="article.link" target="_blank"><unicon name="youtube" fill="red" ></unicon></a>
                  <a :href="article.link" v-if="article.link" target="_blank"><unicon name="youtube" fill="gray"></unicon></a> -->
                </div>
                <p class="date">{{ article.reg_time.slice(0, 10) }}</p>
              </div>
            </div>
          </div>

          <div class="btn-group wrap"> <!-- 버튼(좋아요, 댓글, 스크랩) /  -->
            <div class="like likeScrap" @click="clickedLikeBtn(index)">
              <svg v-show="article.like" 
                class="svg-inline--fa fa-heart fa-w-16 icon full"
                aria-hidden="true"
                data-prefix="fas"
                data-icon="heart"
                role="img"
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 512 512"
                data-fa-i2svg
              >
                <path
                  fill="red"
                  d="M462.3 62.6C407.5 15.9 326 24.3 275.7 76.2L256 96.5l-19.7-20.3C186.1 24.3 104.5 15.9 49.7 62.6c-62.8 53.6-66.1 149.8-9.9 207.9l193.5 199.8c12.5 12.9 32.8 12.9 45.3 0l193.5-199.8c56.3-58.1 53-154.3-9.8-207.9z"
                />
              </svg>
                
              <svg v-show="!article.like"
                class="svg-inline--fa fa-heart fa-w-16 icon empty"
                aria-hidden="true"
                data-prefix="far"
                data-icon="heart"
                role="img"
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 512 512"
                data-fa-i2svg
              >
                <path
                  fill="gray"
                  d="M458.4 64.3C400.6 15.7 311.3 23 256 79.3 200.7 23 111.4 15.6 53.6 64.3-21.6 127.6-10.6 230.8 43 285.5l175.4 178.7c10 10.2 23.4 15.9 37.6 15.9 14.3 0 27.6-5.6 37.6-15.8L469 285.6c53.5-54.7 64.7-157.9-10.6-221.3zm-23.6 187.5L259.4 430.5c-2.4 2.4-4.4 2.4-6.8 0L77.2 251.8c-36.5-37.2-43.9-107.6 7.3-150.7 38.9-32.7 98.9-27.8 136.5 10.5l35 35.7 35-35.7c37.8-38.5 97.8-43.2 136.5-10.6 51.1 43.1 43.5 113.9 7.3 150.8z"
                />
              </svg>
              <span class="like-cnt" v-if="article.like_cnt">{{ article.like_cnt }}명</span>
            <!-- <p>{{ $store.state.nickname }}님 외 {{ article.like_cnt }}명이 좋아합니다.</p> -->
            </div>
            <!-- <div class="comment"> -->        
            <div class="comment" @click="clickedCommentBtn(article, index)">
              <svg
                class="svg-inline--fa fa-comment-alt fa-w-16 icon"
                aria-hidden="true"
                data-prefix="far"
                data-icon="comment-alt"
                role="img"
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 512 512"
                data-fa-i2svg
              >
                <path
                  fill="currentColor"
                  d="M448 0H64C28.7 0 0 28.7 0 64v288c0 35.3 28.7 64 64 64h96v84c0 7.1 5.8 12 12 12 2.4 0 4.9-.7 7.1-2.4L304 416h144c35.3 0 64-28.7 64-64V64c0-35.3-28.7-64-64-64zm16 352c0 8.8-7.2 16-16 16H288l-12.8 9.6L208 428v-60H64c-8.8 0-16-7.2-16-16V64c0-8.8 7.2-16 16-16h384c8.8 0 16 7.2 16 16v288z"
                />
              </svg>
              <!-- <i class="far fa-comment-alt icon"></i> -->
              <!-- {{ cntComment }} -->
              {{ article.comment_cnt}}
            </div>

            <div class="scrap-btn" @click="clickedScrapBtn(index)">
              <i class="far fa-bookmark"></i>
              <!-- <i class="fas fa-bookmark"></i> -->
              <span class="scrap-cnt" v-if="article.scrap_cnt">{{ article.scrap_cnt }}회</span>
            </div>
          </div>
        </div>
        <div style="position: fixed; bottom: 70px; right: 15px;">
          <button @click="scrollToTop" style="color:red">🔺TOP</button>
        </div>
      </div>  
      <BottomNav/>
      <infinite-loading @infinite="infiniteHandler" spinner="circles">
        <div slot="no-more" style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;">목록의 끝입니다 :)</div>
      </infinite-loading>
    </div>
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
      limit: 0, // 무한스크롤 되면서 갱신될 페이지
      email: ""
    }
  },


  // watch:{
  //   clicked(){
  //     console.log("clickclick")
  //     axios.get(base + '/tugether/mainfeed/', {
  //       params: {
  //         "tag": this.tag,
  //         "limit": this.limit-1
  //       },
  //       headers:{
  //         "jwt-auth-token": localStorage.getItem("token")
  //       }
  //     })
  //     .then(response => {
  //       console.log(response.data)
  //       this.articles = response.data;
  //       this.clicked=false;
  //     })
  //     .catch(err =>{
  //         console.log("no watch")
  //     })
  //     .finally(()=>{
  //         this.clicked=false;
  //     })
  //   }
  // },

  methods: {
    // 메인피드 최상단으로 이동
    scrollToTop() {
      scroll(0, 0)
    },

    // 무한스크롤이 동작할 때 수행할 메소드
    infiniteHandler: async function($state) {
      const EACH_LEN = 1
      console.log('___BOTTOM___')
      console.log(this.limit)
      axios.get(base+'/tugether/mainfeed', { 
        params: {
          "tag": this.tag,
          "limit": this.limit
        },
        headers: {
          "jwt-auth-token": localStorage.getItem("token"),
        }
      })
      .then(response => {
        setTimeout(() => {
          if(response.data.length) {
            this.articles = [...this.articles, ...response.data]
            $state.loaded(); // 데이터 로드가 전부 수행되었다는 것을 알려줌, 다음 리퀘스트가 있을 때까지 대기 상태
            this.limit=this.limit+1;
          //  console.log('AFTERDATA ARRIVE!!', this.articles, response.data, this.limit)

            if(response.data.length / EACH_LEN < 1) {
              $state.complete() // 더이상 불러올 데이터가 없을 때 사용, 이후에는 데이터가 없다는 메시지를 표시하고 더이상 무한스크롤 작업 X
            }
          } 
          else {
            $state.complete()
          }
        })
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
      this.limit = 0

      console.log('followlimit'+this.limit)
      axios.get(base + '/tugether/mainfeed/', {
        params: {
          "tag": this.tag,
          "limit": this.limit,
        },
        headers:{
          "jwt-auth-token": localStorage.getItem("token")
        }
      })
      .then(response => {
        console.log('팔로우 기반 호출')
        this.articles = response.data;
        console.log(this.articles)
        this.clicked = true;
      })
      .catch(err =>{
          console.log("getFollowData X")
      })
    },

    // 태그 기반의 글 목록 불러오기
    getTagData() {
      this.feed = '태그';
      this.tag = true
      this.followTab.color = 'black'
      this.tagTab.color = 'red'
      this.limit = 0
      console.log('getTag', this.limit)
      axios.get(base + '/tugether/mainfeed/', {
        params: {
          "tag": this.tag,
          "limit": this.limit
        },
        headers:{ 
          "jwt-auth-token": localStorage.getItem("token")
        }
      })
      .then(response => {
        console.log(response.data)
        console.log('태그기반 호출')
        this.articles = response.data;
        this.clicked = true; 
      })
      .catch(err =>{
          console.log("getTagData X")
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
      axios.get(base + '/tugether/mainfeed/like', {
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

    clickedCommentBtn(articles, index) {
      this.$router.push({
        name: 'Comment',
        params: {
          article_id: this.articles[index].article_id
        }
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
    // 유저페이지로 이동
    moveUserpage(email) {
      this.email = email;
      localStorage.setItem("userEmail", this.email);
      console.log(this.email)
      this.$router.push({
          name: 'Userpage'
      })
    }
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
.btn-group span {
  font-weight: lighter;
}
/* .link > a {
  text-decoration: none;
  color: red;
  
} */
.link {
  width: 15px;
  height: 15px;
}

.feed-card > img {
  max-height: 225px;
  width: 100%;
}


.title {
  text-overflow: ellipsis;
}

#mainfeed > h1 {
  text-align:center; 
  font-weight:bold; 
  font-size:2.5em; 
  font-family: Arial, Helvetica;
}

#mainfeed {
  padding-top: 0;
}


</style>
<!--https://junistory.blogspot.com/2017/06/css-ellipsis.html 글자 수 제한-->
<!-- https://xetown.com/tips/1110772 -->
