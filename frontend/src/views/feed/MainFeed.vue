<template>
  <div class="container">
    <div id="mainfeed">
      <h1>뉴스피드({{ this.feed }})</h1>
      <a @click="logout" style="color:blue">로그아웃</a>
      <br>
      <v-tabs grow style="padding-bottom: 10px;">
        <v-tab style="font-weight: bold;" @click="getTagData">태그</v-tab>
          <v-tab-item style="padding-top: 15px;">
            <v-container>
              <v-row dense class="pt-0">
                <v-col cols="12"  v-for="(article, index) in articles" :key="article.id" :articles="articles">
                  <v-mainfeed id="inspire">
                    <v-card max-width="344" class="mx-auto">
                      <v-list-item>
                        <v-list-item-avatar class="mr-2" size="40px" @click="moveUserpage(article.email)" style="cursor:pointer"><img :src="`https://i3b303.p.ssafy.io/profileimages/${article.profile_photo}`"></v-list-item-avatar>
                        <v-list-item-content>
                          <v-list-item-title class="headline" @click="moveUserpage(article.email)" style="cursor:pointer;">{{ article.writer }}</v-list-item-title>
                          <v-list-item-subtitle style="font-size:0.8rem;">{{ timeForToday(article.reg_time) }}</v-list-item-subtitle>
                        </v-list-item-content>
                        <v-spacer></v-spacer>
                        <a :href="article.link" v-if="article.link" target="_blank"><img src="@/assets/images/youtube.png" alt="" style="width:25px; height:25px;"></a>
                      </v-list-item>
                      <v-img :src="`https://i3b303.p.ssafy.io/articleimages/${article.image}`" height="194"></v-img>
                      <v-card-text class="pb-0" style="color:black">{{ article.content }}</v-card-text>
                      <v-chip-group column>
                        <span v-for="tag in article.tag_name" :key="tag.name">
                          <v-chip class="ml-2 mr-0" style="cursor:default; font-weight:bold;">#{{ tag }}</v-chip>
                          </span>
                      </v-chip-group>
                      <!-- <p class="subheading mr-2 ml-2" style="color: gray">00님 외 {{ article.like_cnt }}명이 좋아합니다.</p> -->
                      <!-- <v-divider class="mx-6 my-0"></v-divider> -->
                      <v-card-actions>
                        <v-btn icon>
                          <v-icon class="mr-1 ml-5" v-show="!article.like" @click="clickedLikeBtn(index)">mdi-heart</v-icon>
                          <v-icon class="mr-1 ml-5" v-show="article.like" @click="clickedLikeBtn(index)" style="color: red;">mdi-heart</v-icon>
                          <span class="subheading mr-2">{{ article.like_cnt }}명</span>
                        </v-btn>
                        <v-spacer></v-spacer>
                        <v-btn icon>
                          <v-icon class="mr-1" @click="clickedCommentBtn(article, index)">mdi-message-text</v-icon>
                          <span class="subheading mr-2">{{ article.comment_cnt }}개</span>
                        </v-btn>
                        <v-spacer></v-spacer>
                        <v-btn icon>
                          <v-icon class="mr-1" @click="clickedScrapBtn(index)">mdi-bookmark</v-icon>
                          <span class="subheading mr-5">{{ article.scrap_cnt }}회</span>
                        </v-btn>
                      </v-card-actions>
                      <!-- <p class="subheading mr-2 ml-2" style="color: gray">00님 외 {{ article.like_cnt }}명이 좋아합니다.</p> -->
                    </v-card>
                  </v-mainfeed>
                </v-col>
              </v-row>
            </v-container>
            
            <v-btn class="mx-2" fab dark small color="blue-gray" @click="scrollToTop" style="position: fixed; bottom: 70px; right: 15px;">
              <v-icon dark>mdi-chevron-up</v-icon>
            </v-btn>
          </v-tab-item>
        <v-tab style="font-weight: bold;" @click="getFollowData" >팔로우</v-tab>
          <v-tab-item style="padding-top: 15px;">
             <v-container>
              <v-row dense class="pt-0">
                <v-col cols="12"  v-for="(article, index) in articles" :key="article.id" :articles="articles">
                  <v-mainfeed id="inspire">
                    <v-card max-width="344" class="mx-auto">
                      <v-list-item>
                        <v-list-item-avatar class="mr-2" size="40px" @click="moveUserpage(article.email)" style="cursor:pointer"><img :src="`https://i3b303.p.ssafy.io/profileimages/${article.profile_photo}`"></v-list-item-avatar>
                        <v-list-item-content>
                          <v-list-item-title class="headline" @click="moveUserpage(article.email)" style="cursor:pointer;">{{ article.writer }}</v-list-item-title>
                          <v-list-item-subtitle style="font-size:0.8rem;">{{ timeForToday(article.reg_time) }}</v-list-item-subtitle>
                        </v-list-item-content>
                        <v-spacer></v-spacer>
                        <a :href="article.link" v-if="article.link" target="_blank"><img src="@/assets/images/youtube.png" alt="" style="width:25px; height:25px;"></a>
                      </v-list-item>
                      <v-img :src="`https://i3b303.p.ssafy.io/articleimages/${article.image}`" height="194"></v-img>
                      <v-card-text class="pb-0" style="color:black">{{ article.content }}</v-card-text>
                      <v-chip-group column>
                        <span v-for="tag in article.tag_name" :key="tag.name">
                          <v-chip class="ml-2 mr-0" style="cursor:default; font-weight:bold;">#{{ tag }}</v-chip>
                          </span>
                      </v-chip-group>
                      <!-- <v-divider class="mx-6 my-0"></v-divider> -->
                      <v-card-actions>
                        <v-btn icon>
                          <v-icon class="mr-1 ml-5" v-show="!article.like" @click="clickedLikeBtn(index)">mdi-heart</v-icon>
                          <v-icon class="mr-1 ml-5" v-show="article.like" @click="clickedLikeBtn(index)" style="color: red;">mdi-heart</v-icon>
                          <span class="subheading mr-2">{{ article.like_cnt }}명</span>
                        </v-btn>
                        <v-spacer></v-spacer>
                        <v-btn icon>
                          <v-icon class="mr-1" @click="clickedCommentBtn(article, index)">mdi-message-text</v-icon>
                          <span class="subheading mr-2">{{ article.comment_cnt }}개</span>
                        </v-btn>
                        <v-spacer></v-spacer>
                        <v-btn icon>
                          <v-icon class="mr-1" @click="clickedScrapBtn(index)">mdi-bookmark</v-icon>
                          <span class="subheading mr-5">{{ article.scrap_cnt }}회</span>
                        </v-btn>
                      </v-card-actions>
                    </v-card>
                  </v-mainfeed>
                </v-col>
              </v-row>
            </v-container>
            <v-btn class="mx-2" fab dark small color="blue-gray" @click="scrollToTop" style="position: fixed; bottom: 70px; right: 15px;">
              <v-icon dark>mdi-chevron-up</v-icon>
            </v-btn>
          </v-tab-item>
      </v-tabs>            
      <infinite-loading @infinite="infiniteHandler" :identifier="tag" spinner="circles">
        <div slot="no-more" style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;">목록의 끝입니다 :)</div>
      </infinite-loading>  
      <BottomNav/>
    </div>
  </div>
</template>


<script>
import axios from 'axios'
import defaultProfile from "../../assets/images/profile_default.png";
import { mapState } from "vuex";
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
      email:'',
      from: '',
      to: '',
    }
  },


  watch:{
    clicked(){
      console.log("from : "+this.from +" \t to : "+this.to)
      axios.get(base + '/tugether/mainfeed/fromto', {
        params: {
          "tag": this.tag,
          "from": this.from,
          "to": this.to,
        },
        headers: {
          "jwt-auth-token": localStorage.getItem("token"),
        }
      })
      .then(response => {
        console.log('click this.from', this.from)
        console.log('click this.to', this.to)
        console.log('clicked:', response.data)
        this.articles = response.data
        console.log('articles:', this.articles)
        this.clicked=false;
      })
      .catch(err =>{
          console.log("no watch")
      })
      .finally(()=>{
          this.clicked=false;
      })
    }
  },
  
  methods: {
    // 메인피드 최상단으로 이동
    scrollToTop() {
      scroll(0, 0)
    },

    // 무한스크롤이 동작할 때 수행할 메소드
    infiniteHandler($state) {
      const EACH_LEN = 2
      console.log('__BOTTOM__')
      axios.get(base+'/tugether/mainfeed', { 
        params: {
          "limit": this.limit,
          "tag": this.tag,
        },
        headers: {
          "jwt-auth-token": localStorage.getItem("token"),
        }
      })
      .then(response => {
        setTimeout(() => {
          if(response.data.length) {
            this.articles = [...this.articles, ...response.data]
            this.limit += 1;
            this.from = this.articles[0].article_id,
            this.to = this.articles[this.articles.length-1].article_id
            console.log('this.from', this.from)
            console.log('this.to', this.to)
            console.log('infi articles:' , this.articles)
            $state.loaded(); // 데이터 로드가 전부 수행되었다는 것을 알려줌, 다음 리퀘스트가 있을 때까지 대기 상태
            if(response.data.length / EACH_LEN < 1) {
              $state.complete() // 더이상 불러올 데이터가 없을 때 사용, 이후에는 데이터가 없다는 메시지를 표시하고 더이상 무한스크롤 작업 X
            }
          } 
          else {
            $state.complete()
          }
        })
      }, 500)
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

    // 태그 기반의 글 목록 불러오기
    getTagData() {
      this.limit = 0;
      // if(!this.articles) {
      //   this.articles = []
      // }
      this.articles = [

      ]
      this.tag = true;
      this.feed = '태그';
    },

    // 팔로우 기반의 글 목록 불러오기
    getFollowData() {
      this.limit = 0;
      this.articles = [];
      this.tag = false;
      this.feed = '팔로우';
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
        params: {
          "article_id": this.articles[index].article_id,
        },
        headers: { 
          "jwt-auth-token": localStorage.getItem("token"),
        }
      })
      .then(response => {
        this.articles[index] = response.data.article;
        console.log('clicedLikeBtn:', this.articles[index])
        // this.clicked = true;
      })
      .catch(err => {
        console.log('clickLikeBtn FAIL!!!')
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
        params: {
          "article_id": this.articles[index].article_id,
        },
        headers: {
          "jwt-auth-token": localStorage.getItem("token"),
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

    moveUserpage(email) {
      this.email = email;
      localStorage.setItem("userEmail", this.email);
      console.log(this.email)
      this.$router.push({
        name: 'Userpage'
      })
    },
  },
}
</script>

<style scoped>
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