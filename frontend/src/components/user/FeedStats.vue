<template>
  <v-app>
    <v-carousel
      cycle
      height="500"
      hide-delimiter-background
      show-arrows-on-hover
      light
      >
      <v-carousel-item
        v-for="(article, index) in articles" :key="article.id" :articles="articles"
      >
        <v-sheet
          height="100%"
        >
          <v-row
            class="fill-height"
            align="center"
            justify="center"
          >
            <!-- <div class="display-3">{{ article }} Slide</div> -->
            <!-- <v-col cols="12"  v-for="(article, index) in articles" :key="article.id" :articles="articles"> -->
                <v-card max-width="344" class="mx-auto">
                  <v-list-item>
                    <v-list-item-avatar class="mr-2" size="40px" @click="moveUserpage(article.email)" style="cursor:pointer"><button style="background: red; color: white; width: 30px; height: 30px;">{{ index + 1 }}위</button></v-list-item-avatar>
                    <v-list-item-content>
                      <v-list-item-title class="headline" @click="moveUserpage(article.email)" style="cursor:pointer; font-family: NanumBarunGothic !important; font-size: 1rem !important;">{{ article.writer }}</v-list-item-title>
                      <v-list-item-subtitle style="font-size:0.8rem;">{{ timeForToday(article.reg_time) }}</v-list-item-subtitle>
                    </v-list-item-content>
                    <v-spacer></v-spacer>
                    <a :href="article.link" v-show="article.link!='null'" target="_blank"><img src="@/assets/images/youtube.png" alt="" style="width:35px; height:35px;"></a>
                  </v-list-item>
                  <iframe v-if="article.image == null && article.link != '' " class="embed-responsive-item" :src="`https://www.youtube.com/embed/${getLink(article.link)}`" style="width:100%"></iframe>
                  <v-img v-if="article.image != null " :src="`https://i3b303.p.ssafy.io/articleimages/${article.image}`" height="194"></v-img>
                  <v-card-text class="pb-0">{{ article.content }}</v-card-text>
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
                      <v-icon class="mr-1" @click="clickedCommentBtn(index)">mdi-message-text</v-icon>
                      <span class="subheading mr-2 " @click="clickedCommentBtn(index)">{{ article.comment_cnt }}개</span>
                    </v-btn>
                    <v-spacer></v-spacer>
                    <v-btn icon>
                      <v-icon class="mr-1" @click="clickedScrapBtn(index)">mdi-bookmark</v-icon>
                      <span class="subheading mr-5" @click="clickedScrapBtn(index)">{{ article.scrap_cnt }}회</span>
                    </v-btn>
                  </v-card-actions>
                </v-card>
            <!-- </v-col> -->
          </v-row>
        </v-sheet>
      </v-carousel-item>
    </v-carousel>
  </v-app>
    <!-- <v-row dense class="pt-0">
      <v-col cols="12"  v-for="(article, index) in articles" :key="article.id" :articles="articles">
          <v-card max-width="344" class="mx-auto" outlined>
            <v-list-item>
              <v-list-item-avatar class="mr-2" size="40px" @click="moveUserpage(article.email)" style="cursor:pointer"><button style="background: red; color: white; width: 30px; height: 30px;">{{ index + 1 }}위</button></v-list-item-avatar>
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
    </v-row> -->
</template>

<script>
import axios from 'axios'
import { base } from "@/components/common/BaseURL.vue"

export default {
  name: 'FeedStats',
  data() {
    return {
      email: '',
      articles: [],
      clicked: false,
    }
  },
  watch:{
    clicked(){
      axios.get(base + '/tugether/likeystats', {
      headers: {
        "jwt-auth-token": localStorage.getItem("token")
      }
    })
    .then(response => {
      this.articles = response.data.toplikeyarticles
    })
    .catch(error => {
      console.log(error)
    })
    .finally(()=>{
          this.clicked=false;
      })  
    }
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
        // this.clicked = true;
      })
      .catch(err => {
        console.log('clickLikeBtn FAIL!!!')
      })
    },

    clickedCommentBtn(index) {
      this.$router.push({
        name: 'Comment',
        params: {
          article_id: this.articles[index].article_id
        }
      })
    },

    clickedScrapBtn(index) {
      axios.get(base + '/tugether/mainfeed/scrap', {
        params: {
          "article_id": this.articles[index].article_id,
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
                "article_id": this.articles[index].article_id,
              },
              {
                headers: {
                  "jwt-auth-token": localStorage.getItem("token"),
                }
              })
              .then(response => {
                this.articels[index] = response.data.article;
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
      this.$router.push({
        name: 'Userpage'
      })
    },
  },
  created() {
    axios.get(base + '/tugether/likeystats', {
      headers: {
        "jwt-auth-token": localStorage.getItem("token")
      }
    })
    .then(response => {
      this.articles = response.data.toplikeyarticles
    })
    .catch(error => {
      console.log(error)
    })
  }
}
</script>

<style scoped>
.v-window__next{
  float:right  !important ;
  right: 0px !important ;
}

</style>