<template>
    <div class="wrapC" style="text-align: center;">
        <div class="search">
            <div class="container">
                <!--검색어 분류: radio button-->
                <div style="width: 100%; margin-left: 26%; display: inline-block; margin: -90px 0 -50px 26%;">
                    <v-radio-group v-model="category" row>
                        <v-radio id="tag" value="tag" style="float: left;"></v-radio>
                        <label for="tag" style="font-weight: bold; float: right; margin: 3px 15px 0 -10px;">태그</label>&nbsp;
                        <v-radio id="nickname" value="nickname" style="float: left;"></v-radio>
                        <label for="nickname" style="font-weight: bold; float: right; margin: 3px 10px 0 -10px">닉네임</label>
                    </v-radio-group>
                </div>
                <!--선택한 카테고리에 따라 버튼 기능 다르게 부여함-->
                <div v-show="category==='tag'" style="margin-top: -65px;">
                    <input type="text" v-model="keyword" id="search_bar1" placeholder="검색어를 입력하세요"
                        autofocus onFocus="this.value='';" @keyup.enter="searchTag" style="border: 1px solid gray;">
                    <button class="button" v-if="category==='tag'" @click="searchTag">검색</button>
                </div>
                <div v-show="category==='nickname'" style="margin-top: -65px;">
                    <input type="text" v-model="keyword" id="search_bar2" placeholder="검색어를 입력하세요"
                        autofocus onFocus="this.value='';" @keyup.enter="searchUser" style="border: 1px solid gray;">
                    <button class="button" v-if="category==='nickname'" @click="searchUser">검색</button>
                </div>
                <!-- <button class="button" @click="searchTagList">드롭다운</button> -->

                <!--태그 기반 게시글 검색 결과-->
                <div v-show="category==='tag'" class="result">
                    <div style="margin: -10px 0 12px 0;">
                        <!--안내메시지 출력-->
                        <h3>{{ msg_tag }}</h3>
                    </div>
                    <v-row dense class="pt-0" v-show="category==='tag'">
                        <v-col cols="12" v-for="(article, index) in articles" :key="index" :articles="articles">
                            <v-card max-width="344" class="mx-auto">
                            <!-- 프로필이미지, 작성자, 시간(며칠전..), 유튜브 url -->
                            <v-list-item>
                                <v-list-item-avatar class="mr-2" size="40px" @click="moveUserpage(article.email)" style="cursor:pointer"><img :src="`https://i3b303.p.ssafy.io/profileimages/${article.profile_photo}`"></v-list-item-avatar>
                                <v-list-item-content>
                                <v-list-item-title class="headline" @click="moveUserpage(article.email)" style="cursor:pointer; text-align:left;">{{ article.writer }}</v-list-item-title>
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
                                <span v-for="(tag, index2) in article.tag_name" :key="index2">
                                    <v-chip class="ml-2 mr-0" style="cursor:default; font-weight:bold;"
                                        @click="tagSearch(index, index2)">#{{ tag }}</v-chip>
                                </span>
                            </v-chip-group>
                            </v-card>
                        </v-col>
                    </v-row>
                </div>
            
            <!--닉네임 기반 사용자 검색 결과-->
            <div v-show="category==='nickname'" class="result">
                <div style="margin: -10px 0 12px 0;" >
                    <h3>{{ msg_nickname }}</h3>
                </div>

                <div v-for="(user, index) in userList" :key="index" style="text-align: left; margin-top: 10px;">
                    <v-avatar><img :src="`https://i3b303.p.ssafy.io/profileimages/${user.profile_photo}`" alt="image"  @click="moveUserpage(user.email)"></v-avatar>
                    <button @click="moveUserpage(user.email)"><strong style="font-size: 15px; padding-left: 10px;">{{ user.nickname }}</strong></button>
                </div>
            </div>

            <!--container-->
            </div>

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
            category: "tag",
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
            msg_tag: "",
            msg_nickname: "",
            email: ""
        }
    },
    // created() {
    //     if(this.keyword === "") {
    //         return;
    //     } else {
    //         this.keyword = this.$route.params.keyword
    //         this.searchTag();
    //     }
    // },
    watch: {
        // keyword: function() {
        //     this.searchTagList;
        // }
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
        // v-chip에서 태그를 클릭했을 때 해당 태그명을 키워드로 한 태그 기반 검색 기능 수행
        tagSearch(index, index2){
            this.keyword = this.articles[index].tag_name[index2];
            alert(this.keyword + " 키워드로 검색합니다.");
            this.searchTag();
            scroll(0,0); // 페이지의 최상단으로 이동
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
                    this.searchList = res.data.searchList;
                })
                .catch((err) => {
                    console.log("searchTagList function error")
                })
        },
        // 태그 기반 검색 기능
        searchTag() {
            // 검색어를 입력하지 않았을 경우 메소드 종료
            if(this.keyword === "") {
                alert("검색어를 입력해주세요!😊");
                return;
            }
            // 검색어를 정상적으로 입력했을 경우 기능 동작
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
                        this.msg_tag = "검색결과가 없습니다.";
                    } else { // 검색결과가 있을 경우 몇 건의 결과가 있는지 출력
                        this.msg_tag = this.articles.length + "건의 검색결과가 있습니다.";
                    }
                })
                .catch((err) => {
                    console.log("searchTag function error")
                })
        },
        // 사용자 검색 기능 (해당 키워드가 포함된 모든 사용자를 출력함)
        searchUser() {
            // 검색어를 입력하지 않았을 경우 메소드 종료
            if(this.keyword === "") {
                alert("검색어를 입력해주세요!😊");
                return;
            }
            // 검색어를 정상적으로 입력했을 경우 기능 동작
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
                    this.userList = res.data.searchList;
                    // 검색결과가 없을 경우 안내메세지 출력
                    if(this.userList.length == 0) {
                        this.msg_nickname = "검색결과가 없습니다.";
                    } else { // 검색결과가 있을 경우 몇 건의 결과가 있는지 출력
                        this.msg_nickname = this.userList.length + "건의 검색결과가 있습니다.";
                    }
                })
                .catch((err) => {
                    console.log("searchUser function error")
                })
        },
        // 유저페이지로 이동
        moveUserpage(email) {
            this.email = email;
            // 만약 다른 사용자의 글이라면 해당 사용자의 유저페이지로 이동
            if (this.email !== localStorage.getItem("email")) {
                localStorage.setItem("userEmail", this.email);
                this.$router.push("/userpage");
            } else { // 내 글이라면 마이페이지로 이동
                this.$router.push({
                    name: "Mypage",
                });
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
        }
    }
}
</script>

<style scoped>
    .button{
        background: black;
        color: white;
        width: 28%;
        height: 50px;
        float: right;
    }
    #search_bar1{
        float: left;
        width: 70%;
    }
    #search_bar2{
        float: left;
        width: 70%;
    }
    .result {
      margin-bottom: 50px;
    }
    select {
    -webkit-appearance: button;
    transition: ease-in-out 1000000s;
    }
    input[type="radio"] {
    -webkit-appearance: radio;
    }
    input[type="radio"] {
    -webkit-appearance: radio;
    }
    .search_input{
        width: 100%;
        margin: 0 auto;
        display: inline-block;
    }
</style>