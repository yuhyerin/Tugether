<template>
  <div class="container">
    <v-row dense class="pt-0">
      <v-col cols="12"  v-for="(article, index) in articles" :key="article.id" :articles="articles">
        <v-mainfeed id="inspire">
          <v-card max-width="344" class="mx-auto" outlined>
            <v-list-item>
              <v-list-item-avatar class="mr-2" size="40px" @click="moveUserpage(article.email)" style="cursor:pointer"><img :src="`https://i3b303.p.ssafy.io/articleimages/${article.profile_photo}`"></v-list-item-avatar>
              <v-list-item-content>
                <v-list-item-title class="headline" @click="moveUserpage(article.email)" style="cursor:pointer;">{{ article.writer }}</v-list-item-title>
                <v-list-item-subtitle style="font-size:0.8rem;">{{ timeForToday(article.reg_time) }}</v-list-item-subtitle>
              </v-list-item-content>
              <v-spacer></v-spacer>
              <a :href="article.link" v-if="article.link" target="_blank"><img src="@/assets/images/youtube.png" alt="" style="width:25px; height:25px;"></a>
            </v-list-item>
            <v-img :src="`https://i3b303.p.ssafy.io/articleimages/${article.image}`" height="194"></v-img>
            <v-card-text class="pb-0">{{ article.content }}</v-card-text>
            <v-chip-group column>
              <span v-for="tag in article.tag_name" :key="tag.name">
                <v-chip class="ml-2 mr-0" outlined pill style="cursor:default;">#{{ tag }}</v-chip>
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
    <BottomNav/>
  </div>
</template>

<script>
import axios from 'axios'
import defaultProfile from "../../assets/images/profile_default.png"
import { base } from "@/components/common/BaseURL.vue"
import BottomNav from "@/components/common/BottomNav"

export default {
  name: 'FeedStats',
  data() {
    return {
      email: '',
      articles: [],
      clicked: false,
      defaultProfile,
    }
  },
  components:{
    BottomNav,
  },
  methods: {
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
      // this.clicked = true;
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
        this.clicked = true;
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
  created() {
    axios.get(base + 'tugether/likeystats', {
      headers: {
        "jwt-auth-token": localStorage.getItem("token")
      }
    })
    .then(response => {
      console.log(response.data.toplikeyarticles)
      this.articles = response.data.toplikeyarticles
    })
    .catch(error => {
      console.log(error)
    })
  }
}
</script>

<style>

</style>