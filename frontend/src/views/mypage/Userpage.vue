<template>
  <div class="feed mypage">
      <div class="wrapB" style="text-align: center;">
        <h2 style="font-weight: bold;">{{ profile.nickname }}님의 마이페이지</h2>

        <!--프로필 영역-->
        <div id="profile" style="margin-top: 20px;">
            <!--프로필 사진-->
            <v-avatar size="130px" style="margin-bottom: 8px;"><img :src= "`https://i3b303.p.ssafy.io/profileimages/${profile.profile_photo}`" alt="image"></v-avatar><br>
            <!--닉네임, 게시글 수, 팔로잉 수, 팔로워 수-->
            <strong style="font-size: 20px;">{{ profile.nickname }}</strong><br>
            <div style="font-size: 15px;">
              게시글 <strong style="color: red; margin-right: 5px;">{{ profile.article_cnt }}</strong>
              팔로워 <strong style="color: red; cursor: pointer; margin-right: 5px;">{{ profile.follower_cnt }}</strong>
              팔로잉 <strong style="color: red; cursor: pointer;">{{ profile.following_cnt }}</strong>
            </div>
        </div>
        <!--나와 상대 유저의 팔로우 관계에 따라 다른 버튼이 보인다.-->
        <div v-if="follow"> <!--true-->
            <!--내가 팔로우하고 있는 사용자라면 '팔로잉' 버튼 활성화-->
            <v-btn class="follow_button" outlined style="color: #3366ff;" @click="unFollow(profile.email)">팔로잉</v-btn>
        </div>
        <div v-else> <!--false-->
            <!--내가 팔로우하고 있지 않은 사용자라면 '팔로우' 버튼 활성화-->
            <v-btn class="follow_button" depressed style="background-color: #3366ff; color: white;" @click="toFollow(profile.email)">팔로우</v-btn>
        </div>
        <!--유저의 관심태그 목록 보여주기-->
        <div id="favtags" style="margin-top: 10px;">
            <span id="tags_test" v-for="tags in favtags" :key=tags>
                <v-chip style="color: white; background-color: red;">#{{ tags }}</v-chip>
            </span>
        </div>

        <!--탭(유저 게시글 & 유저가 스크랩한 글)-->
        <!--유저 게시글-->
          <v-tabs grow style="margin-top: 10px;">
            <v-tab style="font-weight: bold;">게시글</v-tab>
              <v-tab-item style="padding-top: 15px;">
                <v-container>
                  <v-row dense class="pt-0">
                    <v-col cols="12" v-for="(article, index) in articles" :key="article.id" :articles="articles">
                      <!-- <v-mainfeed id="inspire"> -->
                        <v-card max-width="344" class="mx-auto">
                          <!-- 프로필이미지, 작성자, 시간(며칠전..), 유튜브 url -->
                          <v-list-item>
                            <v-list-item-avatar class="mr-2" size="40px" style="cursor:pointer"><img :src="`https://i3b303.p.ssafy.io/profileimages/${article.profile_photo}`"></v-list-item-avatar>
                            <v-list-item-content>
                              <!-- 이미 유저페이지이므로 해당 유저의 글에서 닉네임을 눌러도 아무 일도 일어나지 않음.-->
                              <v-list-item-title class="headline" style="cursor:pointer; text-align:left;">{{ article.writer }}</v-list-item-title>
                              <v-list-item-subtitle style="font-size:0.8rem; text-align:left;">{{ timeForToday(article.reg_time) }}</v-list-item-subtitle>
                            </v-list-item-content>
                            <v-spacer></v-spacer>
                            <a :href="article.link" v-show="article.link!='null'" target="_blank"><img src="@/assets/images/youtube.png" alt="" style="width:35px; height:35px;"></a>
                          </v-list-item>
                          <!-- 이미지, 내용, 태그 -->
                          <iframe v-if="article.image == null && article.link != '' " class="embed-responsive-item" :src="`https://www.youtube.com/embed/${getLink(article.link)}`" allowfullscreen style="width:100%; height:200px"></iframe>
                          <v-img v-if="article.image != null " :src="`https://i3b303.p.ssafy.io/articleimages/${article.image}`" max-height="230"></v-img>
                          <v-card-text class="pb-0" style="color:black; text-align:left;">{{ article.content }}</v-card-text>
                          <v-chip-group column>
                            <span v-for="tag in article.tag_name" :key="tag.name">
                              <v-chip class="ml-2 mr-0" style="cursor:default; font-weight:bold;">#{{ tag }}</v-chip>
                              </span>
                          </v-chip-group>
                          <v-card-actions>
                            <v-btn icon>
                              <v-icon class="mr-1 ml-5" v-show="!article.like" @click="clickedLikeBtn(index)">mdi-heart</v-icon>
                              <v-icon class="mr-1 ml-5" v-show="article.like" @click="clickedLikeBtn(index)" style="color: red;">mdi-heart</v-icon>
                              <span class="subheading mr-2" @click="clickedLikeBtn(index)">{{ article.like_cnt }}명</span>
                            </v-btn>
                            <v-spacer></v-spacer>
                            <v-btn icon>
                              <v-icon class="mr-1" @click="clickedCommentBtnArticle(article, index)">mdi-message-text</v-icon>
                              <span class="subheading mr-2" @click="clickedCommentBtnArticle(article, index)">{{ article.comment_cnt }}개</span>
                            </v-btn>
                            <v-spacer></v-spacer>
                            <v-btn icon>
                              <v-icon class="mr-1" @click="clickedScrapBtnScrap(index)">mdi-bookmark</v-icon>
                              <span class="subheading mr-5" @click="clickedScrapBtnArticle(index)">{{ article.scrap_cnt }}회</span>
                            </v-btn>
                          </v-card-actions>
                        </v-card>
                      <!-- </v-mainfeed> -->
                    </v-col>
                  </v-row>
                </v-container>
              </v-tab-item>

            <!--유저가 스크랩한 글-->
            <v-tab style="font-weight: bold;">스크랩한 글</v-tab>
            <v-tab-item style="padding-top: 15px;">
              <v-container>
                  <v-row dense class="pt-0">
                    <v-col cols="12" v-for="(scrap, index) in scraps" :key="index" :scraps="scraps" style="text-align: left;">
                      <!-- <v-mainfeed id="inspire"> -->
                        <v-card max-width="344" class="mx-auto">
                          <!-- 프로필이미지, 작성자, 시간(며칠전..), 유튜브 url -->
                          <v-list-item>
                            <v-list-item-avatar class="mr-2" @click="moveUserpage(scrap.email)" size="40px" style="cursor:pointer"><img :src="`https://i3b303.p.ssafy.io/profileimages/${scrap.profile_photo}`"></v-list-item-avatar>
                            <v-list-item-content>
                              <v-list-item-title class="headline" @click="moveUserpage(scrap.email)" style="cursor:pointer; text-align:left;">{{ scrap.writer }}</v-list-item-title>
                              <v-list-item-subtitle style="font-size:0.8rem; text-align:left;">{{ timeForToday(scrap.reg_time) }}</v-list-item-subtitle>
                            </v-list-item-content>
                            <v-spacer></v-spacer>
                            <a :href="scrap.link" v-show="scrap.link!='null'" target="_blank"><img src="@/assets/images/youtube.png" alt="" style="width:35px; height:35px;"></a>
                          </v-list-item>
                          <!-- 이미지, 내용, 태그 -->
                          <iframe v-if="scrap.image == null && scrap.link != '' " class="embed-responsive-item" :src="`https://www.youtube.com/embed/${getLink(scrap.link)}`" allowfullscreen style="width:100%; height:200px"></iframe>
                          <v-img v-if="scrap.image != null " :src="`https://i3b303.p.ssafy.io/articleimages/${scrap.image}`" max-height="230"></v-img>
                          <v-card-text class="pb-0" style="color:black; text-align:left;">{{ scrap.content }}</v-card-text>
                          <v-chip-group column>
                            <span v-for="tag in scrap.tag_name" :key="tag.name">
                              <v-chip class="ml-2 mr-0" style="cursor:default; font-weight:bold;">#{{ tag }}</v-chip>
                              </span>
                          </v-chip-group>
                          <v-card-actions>
                            <v-btn icon>
                              <v-icon class="mr-1 ml-5" v-show="!scrap.like" @click="clickedLikeScrapBtn(index)">mdi-heart</v-icon>
                              <v-icon class="mr-1 ml-5" v-show="scrap.like" @click="clickedLikeScrapBtn(index)" style="color: red;">mdi-heart</v-icon>
                              <span class="subheading mr-2" @click="clickedLikeScrapBtn(index)">{{ scrap.like_cnt }}명</span>
                            </v-btn>
                            <v-spacer></v-spacer>
                            <v-btn icon>
                              <v-icon class="mr-1" @click="clickedCommentBtnScrap(scrap, index)">mdi-message-text</v-icon>
                              <span class="subheading mr-2" @click="clickedCommentBtnScrap(scrap, index)">{{ scrap.comment_cnt }}개</span>
                            </v-btn>
                            <v-spacer></v-spacer>
                            <v-btn icon>
                              <v-icon class="mr-1" @click="clickedScrapBtnScrap(index)">mdi-bookmark</v-icon>
                              <span class="subheading mr-5" @click="clickedScrapBtnScrap(index)">{{ scrap.scrap_cnt }}회</span>
                            </v-btn>
                          </v-card-actions>
                        </v-card>
                      <!-- </v-mainfeed> -->
                    </v-col>
                  </v-row>
                </v-container>
              </v-tab-item>
          </v-tabs>
          <!--네비게이션 바-->
          <BottomNav/>
      </div>
  </div>
</template>

<script>
import axios from "axios";
import store from '@/vuex/store';
import { mapState, mapActions, mapMutations } from "vuex";
import { base } from "@/components/common/BaseURL.vue"; // baseURL
import BottomNav from "@/components/common/BottomNav";

export default {
    components:{
        BottomNav,
    },
    data: () => {
        return {
            follow: "",
            favtags: [],
            articles: "",
            profile: '',
            scraps: "",
            email: "",
            clicked: false
        }
    },
    watch: {
      clicked() {
        axios.get(base + '/tugether/userpage', {
          params:{
            "userEmail": localStorage.getItem("userEmail")
          },
          headers: {
            "jwt-auth-token": localStorage.getItem("token"),
          }
        })
        .then(response => {
          this.articles = response.data.articles;
          this.scraps = response.data.scraps;
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
    created() {
        this.refresh();
    },
    methods: {

            // 유튜브링크에서 키값 꺼내기 
    getLink(articlelink){
          
          var subValue = 'watch?v=';
          var subValue2 = 'youtu.be/'
          var iValue = articlelink.indexOf(subValue); 
          var iValue2 = articlelink.indexOf(subValue2);
          // 부분 문자열이 대상 문자열 안에 있는지 없는지 확인하기 위해서는 반환되는 값이 -1 인지 살펴보면 됨
          if (iValue != -1) { 
            // https://www.youtube.com/watch?v=hPmS4C08-zA
            // iValue = 24 
            var startidx = iValue + subValue.length
            var endidx = articlelink.indexOf('t=');
            if(endidx != -1){ // 시작시간이 걸려있으면, 
              var front = articlelink.substring(startidx, endidx - 1);
              var back = articlelink.substring(endidx+2, articlelink.length)
              var result = front+'?start='+back;
              return result;
              
            }else{
              return articlelink.substring(startidx, articlelink.length)
            } 
            
          }else if(iValue2 != -1){
            // https://youtu.be/Hnn1Om5PVKA?t=36
            var startidx2 = iValue2 + subValue2.length
            var endidx2 = articlelink.indexOf('t=');
            if(endidx2 != -1){ // 시작시간이 걸려있으면, 
              var front2 = articlelink.substring(startidx2, endidx2 - 1);
              var back2 = articlelink.substring(endidx2+2, articlelink.length)
              var result2 = front2+'?start='+back2;
              return result2;
              
            }else{
              return articlelink.substring(startidx2, articlelink.length)
            } 

          }else{
            console.log('찾고자 하는 영상 URL이 없습니다. ');
          }

    },

        refresh() {
          axios
              .get(base + '/tugether/userpage', 
              {
                  params: {
                      "userEmail": localStorage.getItem("userEmail")
                  },
                  headers: {
                      "jwt-auth-token": localStorage.getItem("token") // 토큰 보내기
                  }
              })
              .then((res) => {
                  // console.log(res.data)
                  // 프로필 띄우기
                  this.profile = res.data.profile;
                  this.follow = res.data.follow;
                  this.favtags = res.data.favtags;
                  // 유저의 게시글, 스크랩한 글 목록 가져오기
                  this.articles = res.data.articles;
                  this.scraps = res.data.scraps;
              })
              .catch((err) => {
                  console.log("created axios get PROFILE and ARTICLES, SCRAPS error")
              })
        },
        // 팔로잉 하기 (팔로우 버튼 누를 시)
        toFollow(email) {
            this.email = email;
            var answer = confirm("팔로우 하시겠습니까?");
            if(answer) { // true
                axios
                    .post(base + '/tugether/mypage/followapply', {
                        email: this.email
                    },
                    {
                        headers:{
                            "jwt-auth-token": localStorage.getItem("token") // 토큰 보내기
                        }
                    })
                    .then(({data}) => {
                        // this.follow = true; // 팔로우 성공
                        this.refresh();
                    })
                    .catch((err) => {
                        console.log("FOLLOW function error")
                });
            } // if
        },
        // 팔로우 삭제 (팔로잉 버튼 누를 시)
        unFollow(email){
            this.email = email;
            var answer = confirm("팔로우를 취소하시겠습니까?");
            if(answer){ // true
                axios
                    .post(base + '/tugether/mypage/followcancel', {
                        email: this.email
                    },
                    {
                        headers:{
                            "jwt-auth-token": localStorage.getItem("token") // 토큰 보내기
                        }
                    })
                    .then(({data}) => {
                        console.log(data.data);
                        alert("팔로우가 취소되었습니다.");
                        // this.follow = false; // 언팔 성공
                        this.refresh();
                    })
                    .catch((err) => {
                        console.log("UNFOLLOW function error")
                });
            } // if
        },
        // 좋아요 기능
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
          .catch(err => {
            console.log('clickLikeBtn FAIL!!!')
          })
        },
        clickedLikeScrapBtn(index) { 
          this.clicked = true;
          axios.get(base + '/tugether/mainfeed/like', {
            params: {
              "article_id": this.scraps[index].article_id,
            },
            headers: { 
              "jwt-auth-token": localStorage.getItem("token"),
            }
          })
          .catch(err => {
            console.log('clickLikeBtn FAIL!!!')
          })
        },
        // 댓글 보기 기능
        clickedCommentBtnArticle(articles, index) {
            this.$router.push({
                name: 'Comment',
                params: {
                  "article_id": this.articles[index].article_id
                }
            })
        },
        clickedCommentBtnScrap(scraps, index) {
            this.$router.push({
                name: 'Comment',
                params: {
                  "article_id": this.scraps[index].article_id
                }
            })
        },
        // 유저페이지로 이동
        moveUserpage(email) {
          this.email = email;
          if (this.email !== localStorage.getItem("email")) {
                localStorage.setItem("userEmail", this.email);
                this.$router.go(this.$router.currentRoute); // 현재 경로와 중복되므로 이를 사용해 다른 사용자의 데이터로 업데이트 해준다.
                scroll(0, 0); // 페이지의 최상단으로 이동
            }
            // 만약 해당 유저가 내 글을 스크랩 했을 경우는 닉네임(또는 프로필사진)을 눌렀을 때 마이페이지로 이동한다.
            else {
              this.$router.push({
                name: 'Mypage'
              })
            }
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

        // 유저페이지의 내 게시글(유저가 쓴 게시글) 스크랩
        clickedScrapBtnArticle(index) {
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

        // 유저페이지의 스크랩한 글 스크랩
        clickedScrapBtnScrap(index) {
          axios.get(base + '/tugether/mainfeed/scrap', {
            params: {
              "article_id": this.scraps[index].article_id,
            },
            headers: {
              "jwt-auth-token": localStorage.getItem("token"),
            }
          })
          .then(response => {
            if(response.data.mycheck) {
              alert('자신의 게시물은 스크랩할 수 없습니다.')
            }
            else if(response.data.scrapcheck) {
              alert('이미 스크랩한 게시물입니다.')
            } 
            else {
              // confirm창 띄우기
              var answer = confirm('스크랩 하시겠습니까?')
                // if 확인이면 axios.post
                if(answer==true){
                  axios.post(base + '/tugether/mainfeed/scrap', {
                    "article_id": this.scraps[index].article_id,
                  },
                  {
                    headers: {
                      "jwt-auth-token": localStorage.getItem("token"),
                    }
                  })
                  .then(response => {
                    this.scraps[index] = response.data.article;
                    console.log('userpage scrap:', response.data)
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
    /* 게시글 크기 조절 */
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
    .article_function{
      font-size: 95%;
      color: navy;
      cursor: pointer;
    }
    .container {
      margin-bottom: 50px;
    }
</style>