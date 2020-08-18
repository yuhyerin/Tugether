<template>
    <div class="wrapC" style="text-align: center;">
        <div class="search">
            <!-- <h1 style="font-size: 30px;">검색</h1> -->
            <div class="container">
                <select v-model="category" style="border: 1px solid black;">
                    <option value="">검색어 분류</option>
                    <option value="tag">태그</option>
                    <option value="nickname">닉네임</option>
                </select>
                value: {{ category }}
                <!--선택한 카테고리에 따라 버튼 기능 다르게 부여함-->
                <div style="margin-top: -55px;">
                    <input type="text" v-model="keyword" id="search_bar" placeholder="검색어를 입력하세요" autofocus>
                    <button class="button" v-if="category===''" @click="selectPlz" @keyup.enter="selectPlz">검색</button>
                    <button class="button" v-if="category==='tag'" @click="searchTag" @keyup.enter="searchTag">검색</button>
                    <button class="button" v-if="category==='nickname'" @click="searchUser" @keyup.enter="searchUser">검색</button>
                </div>

                <!--태그 기반 게시글 검색 결과-->
                <div>
                    <h2>{{ msg }}</h2>
                </div>

                  <v-row dense class="pt-0">
                    <v-col cols="12" v-for="(article, index) in articles" :key="index" :articles="articles">
                        <v-card max-width="344" class="mx-auto">
                          <!-- 프로필이미지, 작성자, 시간(며칠전..), 유튜브 url -->
                          <v-list-item>
                            <v-list-item-avatar class="mr-2" size="40px" style="cursor:pointer"><img :src="`https://i3b303.p.ssafy.io/profileimages/${article.profile_photo}`"></v-list-item-avatar>
                            <v-list-item-content>
                              <v-list-item-title class="headline" style="cursor:pointer; text-align:left;">{{ article.writer }}</v-list-item-title>
                              <v-list-item-subtitle style="font-size:0.8rem; text-align:left;">{{ timeForToday(article.reg_time) }}</v-list-item-subtitle>
                            </v-list-item-content>
                            <v-spacer></v-spacer>
                            <a :href="article.link" v-if="article.link" target="_blank"><img src="@/assets/images/youtube.png" alt="" style="width:25px; height:25px;"></a>
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
                    </v-col>
                  </v-row>
            </div>

            <!--닉네임 기반 사용자 검색 결과-->



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
    components: {
        BottomNav
    },
    data: () => {
        return {
            category: "",
            keyword: "",
            articles: [],
            searchList: [],
            userList: [],
            options: [
                {
                    value: "tag",
                    title: "태그"
                },
                {
                    value: "nickname",
                    title: "닉네임"
                }
            ],
            msg: ""
        }
    },
    methods: {
        selectPlz() {
            alert("검색어 분류를 선택해주세요!😊");
        },
        // 태그 기반 검색: 유사검색어 드롭 다운
        searchTagList() {
            axios
                .get(base + '/tugether/search/tag', {
                    params:{
                    "keyword": this.keyword
                    },
                    headers:{
                         "jwt-auth-token": localStorage.getItem("token") // 토큰 보내기
                    }
                })
                .then((res) => {
                    console.log(res.data)
                })
                .err((err) => {
                    console.log("searchTagList function error")
                })
        },
        // 태그 기반 검색 기능
        searchTag() {
            axios
                .post(base + '/tugether/search/tag',
                { "keyword": this.keyword },
                { 
                    headers: {
                        "jwt-auth-token": localStorage.getItem("token") // 토큰 보내기
                    }
                })
                .then((res) => {
                    this.articles = res.data.articles;
                    // 검색결과가 없을 경우 안내메세지 출력
                    if(this.articles.length == 0) {
                        this.msg = "검색결과가 없습니다.";
                    } else {
                        // this.msg = "'" + this.keyword + "'" + "으로 검색한 결과입니다.";
                        this.msg = "";
                    }
                    // this.keyword = ""; // 검색창 초기화
                })
                .catch((err) => {
                    console.log("searchTag function error")
                })
        },
        // 사용자 검색 기능 (키워드 포함)
        searchUser() {
            alert("테스트!")
            axios
                .get(base + '/tugether/search/user',
                {
                    params:{
                        "keyword" : this.keyword
                    },
                    headers:{
                         "jwt-auth-token": localStorage.getItem("token") // 토큰 보내기
                    }
                })
                .then((res) => {
                    console.log(res.data)
                    this.searchList = res.data.list;
                })
                .catch((err) => {
                    console.log("searchUser function error")
                })
        },
        // 좋아요 기능 (url?)
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
        }
    },
}
</script>

<style scoped>
    .button{
        background: black;
        color: white;
        width: 25%;
        height: 50px;
        float: right;
    }
    #search_bar{
        float: left;
        width: 70%;
    }
</style>