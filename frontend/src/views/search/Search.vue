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
                        autofocus onFocus="this.value='';" @keyup.enter="searchTag">
                    <button class="button" v-if="category==='tag'" @click="searchTag">검색</button>
                </div>
                <div v-show="category==='nickname'" style="margin-top: -65px;">
                    <input type="text" v-model="keyword" id="search_bar2" placeholder="검색어를 입력하세요"
                        autofocus onFocus="this.value='';" @keyup.enter="searchUser">
                    <button class="button" v-if="category==='nickname'" @click="searchUser">검색</button>
                </div>
                <!-- <button class="button" @click="searchTagList">드롭다운</button> -->

                <!--태그 기반 게시글 검색 결과-->
                <div v-show="category==='tag'" class="result">
                    <div style="margin: -50px 0 12px 0;">
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
                            </v-card>
                        </v-col>
                    </v-row>
                </div>
            
            <!--닉네임 기반 사용자 검색 결과-->
            <div v-show="category==='nickname'" class="result">
                <div style="margin: -50px 0 12px 0;" >
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
    watch: {
        // keyword: function() {
        //     this.searchTagList;
        // }
    },
    methods: {
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
            if(this.keyword.length == 0) {
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
            if(this.keyword.length == 0) {
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
        width: 25%;
        height: 50px;
        float: right;
    }
    #search_bar{
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
</style>