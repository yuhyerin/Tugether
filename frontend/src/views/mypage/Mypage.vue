<template>
  <div class="feed mypage">
      <div class="wrapB" style="text-align: center;">
        <!--프로필 영역-->
        <div id="profile" style="margin-top: 20px;">
            <!--프로필 사진-->
            <v-avatar size="130px" style="margin-bottom: 8px;"><img :src=profile_photo></v-avatar><br>
            <!--닉네임, 내 게시글 수, 팔로잉 수, 팔로워 수-->
            <strong style="font-size: 20px;">{{ nickname }}</strong><br>
            <div style="font-size: 15px;">
              게시글 <strong style="color: red; margin-right: 5px;">{{ article_cnt }}</strong>
              <span @click="moveFollow">팔로워</span> <strong @click="moveFollow" style="color: red; cursor: pointer; margin-right: 5px;">{{ follower_cnt }}</strong>
              <span @click="moveFollow">팔로잉</span> <strong @click="moveFollow" style="color: red; cursor: pointer;">{{ following_cnt }}</strong>
            </div>
        </div>
        <div id="buttons">
            <button class="button" @click="moveSetting">프로필 편집</button>&nbsp;
            <button class="button" @click="moveWrite">글 작성</button>
        </div>
        <!--저장된 관심태그 목록 보여주기-->
        <div style="margin-top: 10px;">
            <span v-for="tags in favtags" :key=tags>
                <v-chip style="color: white; background-color: red;">#{{ tags }}</v-chip>
            </span>
        </div>

        <!--탭(내 게시글 & 내가 스크랩한 글)-->
        <!--내 게시글-->
          <v-tabs grow style="margin-top: 10px;">
            <v-tab style="font-weight: bold;">내 게시글</v-tab>
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
                              <!--마이페이지니까 본인이 작성한 글 닉네임 눌러도 아무 일도 일어나지 않음-->
                              <v-list-item-title class="headline" style="cursor:pointer; text-align:left;">{{ article.writer }}</v-list-item-title>
                              <v-list-item-subtitle style="font-size:0.8rem; text-align:left;">{{ timeForToday(article.reg_time) }}</v-list-item-subtitle>
                            </v-list-item-content>
                            <v-spacer></v-spacer>
                            <a :href="article.link" v-if="article.link" target="_blank"><img src="@/assets/images/youtube.png" alt="" style="width:25px; height:25px;"></a>
                            <!--글 수정, 삭제 기능-->
                            <div style="display: inline-block; float: right;">
                                <span class="article_function" @click="clickedEditBtn(index)" style="margin-right: 5px;">수정</span>
                                <span class="article_function" @click="clickedDeleteBtn(index)">삭제</span>
                            </div>
                          </v-list-item>
                          <!-- 이미지, 내용, 태그 -->
                          <v-img :src="`https://i3b303.p.ssafy.io/articleimages/${article.image}`" height="194"></v-img>
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
                              <v-icon class="mr-1" @click="clickedCommentBtnArticle(index)">mdi-message-text</v-icon>
                              <span class="subheading mr-2" @click="clickedCommentBtnArticle(index)">{{ article.comment_cnt }}개</span>
                            </v-btn>
                            <v-spacer></v-spacer>
                            <v-btn icon>
                              <v-icon class="mr-1" @click="scrapMsg(1)">mdi-bookmark</v-icon>
                              <span class="subheading mr-5" @click="scrapMsg(1)">{{ article.scrap_cnt }}회</span>
                            </v-btn>
                          </v-card-actions>
                        </v-card>
                      <!-- </v-mainfeed> -->
                    </v-col>
                  </v-row>
                </v-container>
              </v-tab-item>

            <!--내가 스크랩한 글-->
            <v-tab style="font-weight: bold;">스크랩한 글</v-tab>
            <v-tab-item style="padding-top: 15px;">
              <v-container>
                  <v-row dense class="pt-0">
                    <v-col cols="12" v-for="(scrap, index) in scraps" :key="index" :scraps="scraps" style="text-align: left;">
                      <v-card max-width="344" class="mx-auto">
                        <!-- 프로필이미지, 작성자, 시간(며칠전..), 유튜브 url -->
                        <v-list-item>
                          <v-list-item-avatar class="mr-2" @click="moveUserpage(scrap.email)" size="40px" style="cursor:pointer"><img :src="`https://i3b303.p.ssafy.io/profileimages/${scrap.profile_photo}`"></v-list-item-avatar>
                          <v-list-item-content>
                            <v-list-item-title class="headline" @click="moveUserpage(scrap.email)" style="cursor:pointer; text-align:left;">{{ scrap.writer }}</v-list-item-title>
                            <v-list-item-subtitle style="font-size:0.8rem; text-align:left;">{{ timeForToday(scrap.reg_time) }}</v-list-item-subtitle>
                          </v-list-item-content>
                          <v-spacer></v-spacer>
                          <a :href="scrap.link" v-if="scrap.link" target="_blank"><img src="@/assets/images/youtube.png" alt="" style="width:25px; height:25px;"></a>
                        <!--스크랩 삭제 기능-->
                        <div style="display: inline-block; float: right;">
                          <span class="article_function" @click="deleteScrap(index)">삭제</span>
                        </div>
                        </v-list-item>
                        <!-- 이미지, 내용, 태그 -->
                        <v-img :src="`https://i3b303.p.ssafy.io/articleimages/${scrap.image}`" height="194"></v-img>
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
                            <v-icon class="mr-1" @click="scrapMsg(2)">mdi-bookmark</v-icon>
                            <span class="subheading mr-5" @click="scrapMsg(2)">{{ scrap.scrap_cnt }}회</span>
                          </v-btn>
                        </v-card-actions>
                      </v-card>
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
import "../../components/css/feed/feed-item.scss";
import "../../components/css/feed/newsfeed.scss";
import FeedItem from "../../components/feed/FeedItem.vue";
import store from '@/vuex/store';
import { mapState, mapActions } from "vuex";
import { base } from "@/components/common/BaseURL.vue"; // baseURL
import BottomNav from "@/components/common/BottomNav";

export default {
    components: { 
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
            articles: [],
            scraps: [],
            clicked: false,
            email: "",
            tag: true,
            from: '',
            to: '',
        }
    },
    watch: {
      myText: function() {
        this.checkForm();
      },

      clicked() {
        axios.get(base + '/tugether/mypage/articles', {
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
        refresh() {
            // 프로필 띄우기
            axios
                .get(base + '/tugether/profile', 
                {
                    headers:{
                        "jwt-auth-token": localStorage.getItem("token") // 토큰 보내기
                    }
                })
                .then((res) => {
                    // console.log(res.data);
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
                    this.articles = res.data.articles;
                    this.scraps = res.data.scraps;
                })
                .catch((err) => {
                    console.log("created axios get ARTICLES AND SCRAPS error")
                });
        },
        // 게시글 수정
        clickedEditBtn(index) {
          console.log(this.articles[index].article_id)
          this.$router.push({
            name: "Update",
            params: {
              article_id: this.articles[index].article_id
            }
          })
        },
        // 게시글 삭제
        clickedDeleteBtn(index) {
           axios
            .post(base + '/tugether/articledelete',
              { "article_id" : this.articles[index].article_id },
              {
                  headers:{
                    "jwt-auth-token": localStorage.getItem("token"),
                  }
              },
            )
            .then((res) => {
                alert("게시글이 삭제 되었습니다.");
                // console.log("삭제 성공")
                this.refresh(); // 글 삭제 후 내 게시글 리스트를 새로고침 하기 위함
            })
            .catch((err) => {
                alert("게시글 삭제 실패!");
                // console.log("삭제 실패")
            });
        },
        // 스크랩한 게시글 삭제
        deleteScrap(index){
          axios
            .post(base + '/tugether/scrapdelete', 
              { "scrap_id" : this.scraps[index].scrap_id },
              {
                headers:{
                  "jwt-auth-token": localStorage.getItem("token") // 토큰 보내기
                }
              },
            )
            .then((res) => {
              alert("스크랩한 게시글이 삭제 되었습니다.");
              this.refresh(); // 글 삭제 후 내 게시글 리스트를 새로고침 하기 위함
            })
            .catch((err) => {
              alert("스크랩한 게시글 삭제 실패!");
              console.log("deleteScrap function error")
            })
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
        // 마이페이지에서 스크랩 기능은 사용하지 않음
        scrapMsg(num) {
            var msg = "";
            msg = (num == 1) ? "자신의 게시물은 스크랩할 수 없습니다." : "이미 스크랩한 게시물입니다." ;
            alert(msg);
        },
        // 댓글 보기 기능
        clickedCommentBtnArticle(index) {
            this.$router.push({
                name: 'Comment',
                params: {
                  "article_id": parseInt(this.articles[index].article_id)
                }
            })
        },
        clickedCommentBtnScrap(scraps, index) {
            this.$router.push({
                name: 'Comment',
                params: {
                  "article_id": parseInt(this.scraps[index].article_id)
                }
            })
        },
        // 페이지 이동
        moveSetting() {
            this.$router.push("/mypage/mypagesetting");
        },
        moveWrite() {
            this.$router.push("/write");
        },
        moveFollow() {
            this.$router.push("/mypage/follow");
        },
        moveMain() {
            this.$router.push("/mainfeed");
        },
        // 유저페이지로 이동
        moveUserpage(email) {
            this.email = email;
            localStorage.setItem("userEmail", this.email);
            this.$router.push({
                name: 'Userpage'
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
    /* 게시글 크기 조절 */
    .link {
      width: 15px;
      height: 15px;
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