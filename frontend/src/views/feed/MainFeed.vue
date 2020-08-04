<template>
<div class="container">
  <div id="mainfeed">

    <!-- <div class="btn-group btn-group-toggle" data-toggle="buttons">
      <label class="btn btn-danger active">
        <input type="radio" name="options" id="option1" checked @click="getTagData"> 태그
      </label>
      <label class="btn btn-danger">
        <input type="radio" name="options" id="option2" @click="getFollowData"> 팔로우
      </label>
    </div>
    <br> -->
  
    <button @click="getTagData">태그</button> | 
    <button @click="getFollowData">팔로우</button> 
    <br>
    <h1>{{ this.feed }} 뉴스피드({{ $store.state.nickname }})</h1>
    <br>
    <div v-for="article in articles" :key="article.id">
      <div class="feed-item">
        <div class="top">
          <!-- <Like @selectedLikeBtn="selectedLikeBtn"/> -->
          <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div>
          <div class="user-info">
            <div class="user-name">
              <button>{{ article.writer }}</button>
            </div>
            <p>{{ timeForToday(article.reg_time) }}</p>
            <p class="date"></p>
          </div>
          <div class="content">
            <p>{{ article.image }}</p>
            <p>{{ article.content }}</p>
            <span v-for="tag in article.tag_name" :key="tag.name">
                # {{ tag }}
            </span>
          </div>
        </div>
        <div class="feed-card">
          <div class="img" :style="{'background-image': 'url('+article.download_url+')'}"></div>
          <!-- <div class="img" :src="article.download_url" alt=""></div> -->
          <div class="contentsWrap">
            <h4 class="title">width: {{ article.width }}, height: {{ article.height }}</h4>
            <div class="wrap">
              <div class="url">
                <a :href="article.link">{{article.link}}</a>
              </div>
              <p class="date">{{article.reg_time}}</p>
            </div>
          </div>
        </div>
        <!---->
        <div class="btn-group wrap">
          <div class="like likeScrap" @click="clickedLike(article.like, article.article_id)">
            <svg
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
            <!-- <i class="fas fa-heart icon full"></i> -->
            <svg 
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
                fill="red"
                d="M458.4 64.3C400.6 15.7 311.3 23 256 79.3 200.7 23 111.4 15.6 53.6 64.3-21.6 127.6-10.6 230.8 43 285.5l175.4 178.7c10 10.2 23.4 15.9 37.6 15.9 14.3 0 27.6-5.6 37.6-15.8L469 285.6c53.5-54.7 64.7-157.9-10.6-221.3zm-23.6 187.5L259.4 430.5c-2.4 2.4-4.4 2.4-6.8 0L77.2 251.8c-36.5-37.2-43.9-107.6 7.3-150.7 38.9-32.7 98.9-27.8 136.5 10.5l35 35.7 35-35.7c37.8-38.5 97.8-43.2 136.5-10.6 51.1 43.1 43.5 113.9 7.3 150.8z"
              />
            </svg>
            <!-- <i class="far fa-heart icon empty"></i> -->
            <p>00님 외 {{ likeCnt }}명이 좋아합니다.</p>
            <!-- 00(사용자 본인의 닉네임)님 외 00명이 좋아합니다. 노출-->
          </div>
          <div class="comment">
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
            {{ cntComment }}
          </div>
          <!---->
          <div class="share">
            <svg
              class="svg-inline--fa fa-bookmark-alt fa-w-14 icon"
              aria-hidden="true"
              data-prefix="far"
              data-icon="bookmark-alt"
              role="img"
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 448 512"
              data-fa-i2svg
            >
              <path
                fill="currentColor"
                d="M352 320c-22.608 0-43.387 7.819-59.79 20.895l-102.486-64.054a96.551 96.551 0 0 0 0-41.683l102.486-64.054C308.613 184.181 329.392 192 352 192c53.019 0 96-42.981 96-96S405.019 0 352 0s-96 42.981-96 96c0 7.158.79 14.13 2.276 20.841L155.79 180.895C139.387 167.819 118.608 160 96 160c-53.019 0-96 42.981-96 96s42.981 96 96 96c22.608 0 43.387-7.819 59.79-20.895l102.486 64.054A96.301 96.301 0 0 0 256 416c0 53.019 42.981 96 96 96s96-42.981 96-96-42.981-96-96-96z"
              />
            </svg>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>    
</template>


<script>
import axios from 'axios'
import defaultProfile from "../../assets/images/profile_default.png";

const SERVER_URL = 'http://i3b303.p.ssafy.io'
export default {
  name: 'MainFeed',
  data() {
    return {
      articles: [],
      defaultProfile,
      feed: '',
      token: "",
      selectedLike: false,
      likeCnt: '',
      cntComment: 0,
      tag: true,
      reg_time: '',
    }
  },
  methods: {
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

    // 팔로우 기반의 글 목록 불러오기
    getFollowData() {
      this.feed = '팔로우';
      this.tag = false
      axios.post('http://localhost:8080/tugether/mainfeed/', {
        tag: this.tag,
      },
      {
        headers:{
          "jwt-auth-token": this.$store.state.token
          }
      })
      .then(response => {
          console.log(response.data.list)
          this.articles = response.data.list;
      })
      .catch(err =>{
          console.log("망")
      })
    },

    // 태그 기반의 글 목록 불러오기
    getTagData() {
      this.feed = '태그';
      this.tag = true
      axios.post('http://localhost:8080/tugether/mainfeed/', {
        tag: this.tag
      },
      {
        headers:{ 
          "jwt-auth-token": this.$store.state.token
        }
      })
      .then(response => {
        console.log(response.data.list)
        this.articles = response.data.list;
      })
      .catch(err =>{
          console.log("망")
      })
    },


    // selectedLikeBtn() {
    //   this.selectedLike = !this.selectedLike
    //   if (this.selectedLike) {
    //     this.cntLike += 1
    //   } else {
    //     this.cntLike -= 1
    //   }
    //   console.log(this.selectedLike)
    //   console.log(this.cntLike)
    // },
    clickedLike(like, article_id) {
      
      axios.get('http://localhost:8080/tugether/mainfeed/like',{
        headers: { 
          "jwt-auth-token": this.$store.state.token,
          "article_id" : article_id,
          "like" : !like
          }
      })
      .then(response => {
        this.likeCnt = response.data.like_cnt
        console.log('가라')
      })
    },
  },


  created() {
    this.feed = '태그'
    this.tag = true
    axios.post('http://localhost:8080/tugether/mainfeed/', {
      tag: this.tag,
    },
    {
      headers:{
        "jwt-auth-token": this.$store.state.token
      }
    })
    .then(response => {
      console.log(response.data.list)
      this.articles = response.data.list;
    })
    .catch(err =>{
        console.log("망")
    })
  },
}
</script>

<style>

</style>