<template>
    <div class="wrapC" style="text-align: center;">
        <div class="notice">
            <h1 style="font-size: 30px;">검색</h1>
            <div class="container">
                <select v-model="test" style="border: 1px solid black;">
                    <option value="">검색어 분류</option>
                    <option value="tag">태그</option>
                    <option value="user">사용자</option>
                </select>
                value: {{ test }}
                <div style="margin-top: -55px;">
                    <input type="text" v-model="keyword" id="search_bar" placeholder="검색어를 입력하세요" autofocus>
                    <button class="button" @click="searchTag">태그</button>
                    <button class="button" @click="searchUser">사용자</button>
                </div>
                키워드: {{ keyword }}
            </div>

            <!--태그 기반 게시글 검색 결과-->
            <div v-for="(article, index) in articles" :key="index" :articles="articles">
                작성자 {{ article.writer }}
                글내용 {{ article.content }}
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
            test: "",
            keyword: "",
            articles: [],
            searchList: [],
            options: [
                {
                    value: "tag",
                    title: "태그"
                },
                {
                    value: "user",
                    title: "사용자"
                }
            ]
        }
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
                })
                .err((err) => {
                    console.log("searchTagList function error")
                })
        },
        // 태그 기반 검색 기능
        searchTag() {
            console.log(localStorage.getItem("token"))
            axios
                .post(base + '/tugether/search/tag', 
                    { "keyword": this.keyword },
                    {
                    headers:{
                         "jwt-auth-token": localStorage.getItem("token") // 토큰 보내기
                    }
                })
                .then((res) => {
                    this.articles = res.data.articles;
                    console.log(this.articles)
                })
                .catch((err) => {
                    console.log("searchTag function error")
                })
        },
        // 사용자 검색 기능 (키워드 포함)
        searchUser() {
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
</style>