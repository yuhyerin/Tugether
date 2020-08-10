<template>
  <div class="feed mypage">
      <div class="wrapB" style="text-align: center;">
<<<<<<< HEAD
        <button @click="moveMain">메인피드로 이동하기</button><br>
=======
        <!-- <button @click="moveMain">메인피드로 이동하기</button><br> -->
>>>>>>> 5d4ef8aa06f87bfed1b1e134a7525628802f239e
        <strong style="font-size: 30px;">마이페이지</strong>
        <!--프로필 영역-->
        <div id="profile" style="margin-top: 20px;">
            <!--프로필 사진-->
            <v-avatar size="150px"><img :src=profile_photo></v-avatar><br>
            <!--닉네임, 내 게시글 수, 팔로잉 수, 팔로워 수-->
            <strong style="font-size: 20px;">{{ nickname }}</strong><br>
            게시글 <strong style="color: red;">{{ article_cnt }}</strong>
            팔로잉 <strong @click="moveFollow" style="color: red; cursor: pointer;">{{ following_cnt }}</strong>
            팔로워 <strong @click="moveFollow" style="color: red; cursor: pointer;">{{ follower_cnt }}</strong>
        </div>
        <div id="buttons">
            <!-- 나의 프로필 편집 버튼 위치에 다른 사람은 팔로우 버튼이 보인다. (아마도?) -->
            <button class="button" @click="moveSetting">프로필 편집</button>&nbsp;
            <button class="button" @click="moveWrite">글 작성</button>
        </div>
        <div id="favtags" style="margin-top: 10px;">
            <strong>관심태그 </strong>
            <span id="tags_test" v-for="tags in favtags" :key=tags>
                #{{ tags }}&nbsp;
            </span>
        </div>


<!--시작-->
<h3 style="margin-top: 30px;">내 게시글 보기</h3>

    <div class="wrapC" v-for="(article, index) in articles" :key="article.id" :articles="articles" style="text-align: left;">
      <div class="feed-item">
        <div class="top"> <!-- 프로필이미지, 작성자, 시간(며칠전..), 태그 -->
          <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div>
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
        <div class="feed-card"> <!-- 이미지, 내용, 유튜브 url, 작성날짜 -->
          <img :src= "`https://i3b303.p.ssafy.io/articleimages/${article.image}`" alt="image">
          <div class="contentsWrap">
            <h4 class="title">{{ article.content }}</h4>
            <div class="wrap">
              <div class="url">
                <a :href="article.link" v-if="article.link" target="_blank"><img src="@/assets/images/youtube.png" alt="" style="width:25px; height:25px;"></a>
              </div>
              <p class="date">{{article.reg_time.slice(0, 10)}}</p>
            </div>
          </div>
        </div>

        <div class="btn-group wrap" style="padding-left: 40px;"> <!-- 좋아요, 댓글,  -->
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
            <span class="like-cnt" v-if="article.like_cnt !== 0">{{ article.like_cnt }}명이 좋아합니다.</span>
            <!-- <p>{{ $store.state.nickname }}님 외 {{ article.like_cnt }}명이 좋아합니다.</p> -->
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
            {{ cntComment }}
          </div>
          <div class="scrap" @click="clickedScrapBtn(index)">
            <svg
              class="svg-inline--fa fa-share-alt fa-w-14 icon"
              aria-hidden="true"
              data-prefix="far"
              data-icon="share-alt"
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
            <span class="scrap-cnt" v-if="article.scrap_cnt !== 0">{{ article.scrap_cnt }}회</span>
          </div>
        </div>
      </div>
    </div>

<!--끝-->


        <!--tab test-->
        <div class="tabs" style="margin-top: 50px;">
            <TabItem
                v-for="item in list"
                v-bind="item" :key="item.id"
                v-model="currentId" style="font-size: 20px;" />
            </div>
            <div class="contents" style="margin: 0 auto; text-align: center;">
            <transition>
                <section class="item" :key="currentId">
                {{ current.content }}
                </section>
            </transition>
        </div>
        <!--tab test-->
<<<<<<< HEAD

=======
        <BottomNav/>
>>>>>>> 5d4ef8aa06f87bfed1b1e134a7525628802f239e
      </div>
  </div>
</template>

<script>
import axios from "axios";
import store from '@/vuex/store';
import { mapState, mapActions } from "vuex";
import { base } from "@/components/common/BaseURL.vue"; // baseURL
import TabItem from '@/components/common/TabItem.vue';
<<<<<<< HEAD
=======
import BottomNav from "@/components/common/BottomNav"
>>>>>>> 5d4ef8aa06f87bfed1b1e134a7525628802f239e

export default {
    components: { 
      TabItem,
      BottomNav,
     },
    data: () => {
        return {
            profile_photo: "",
            nickname: "",
            following_cnt: "",
            follower_cnt: "",
            article_cnt: "",
            favtags: [],
            currentId: 1,
            list: [
                { id: 1, label: '게시글', content: '콘텐츠1' },
                { id: 2, label: '스크랩', content: '콘텐츠2' },
            ],
            articles: [],
            scraps: []
        }
    },
    created() {
        // 프로필 띄우기
        axios
            .get(base + '/tugether/profile', {
                headers:{
                    "jwt-auth-token": localStorage.getItem("token") // 토큰 보내기
                }
            })
            .then((res) => {
                console.log(res.data);
                this.profile_photo = 'https://i3b303.p.ssafy.io/profileimages/' + res.data.profile.profile_photo;
                this.nickname = res.data.profile.nickname;
                this.article_cnt = res.data.profile.article_cnt;
                this.following_cnt = res.data.profile.following_cnt;
                this.follower_cnt = res.data.profile.follower_cnt;
                this.favtags = res.data.favtaglist;
            })
            .catch((err) => {
                console.log("created axios get PROFILE error")
            });

        // 내 게시글, 스크랩한 글 목록 가져오기
        axios
            .get(base + '/tugether/profile/articles', {
                headers:{
                    "jwt-auth-token": localStorage.getItem("token") // 토큰 보내기
                }
            })
            .then((res) => {
                console.log(res.data)
                this.articles = res.data.articles;
                this.scraps = res.data.scraps;
                console.log('articles : '+this.articles)
                console.log('scraps : '+this.scraps)
            })
            .catch((err) => {
                console.log("created axios get ARTICLES AND SCRAPS error")
            });
    },
    methods: {
        // 페이지 이동
        moveSetting() {
            this.$router.push("/mypage/mypagesetting");
        },
        moveWrite() {
<<<<<<< HEAD
            // 글쓰기 페이지로 이동
=======
            this.$router.push("/write");
>>>>>>> 5d4ef8aa06f87bfed1b1e134a7525628802f239e
        },
        moveFollow() {
            this.$router.push("/mypage/follow");
        },
        moveMain() {
            this.$router.push("/mainfeed");
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
    },
    computed: {
        ...mapState(["token"]), //store 공동 저장소에 있는 token 사용하기 위해 선언
        ...mapActions(["getToken"]),
        current() {
            return this.list.find(el => el.id === this.currentId) || {}
        }
    }
}
</script>

<style scoped>
    .button{
        background: black;
        color: white;
        width: 25%;
        height: 35px;
    }
    #tags_test{
        color: white;
        background-color: red;
        /* border: 10px solid red; */
        /* border-radius: 3px; */
    }
    .contents {
    position: relative;
    overflow: hidden;
    width: 280px;
    /* border: 2px solid #000; */
    }
    .item {
    box-sizing: border-box;
    padding: 10px;
    width: 100%;
    transition: all 0.8s ease;
    }
    /* 트랜지션 전용 스타일 */
    .v-leave-active {
    position: absolute;
    }
    .v-enter {
    transform: translateX(-100%);
    }
    .v-leave-to {
    transform: translateX(100%);
    }
</style>