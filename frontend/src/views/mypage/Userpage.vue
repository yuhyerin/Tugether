<template>
  <div class="feed mypage">
      <div class="wrapB" style="text-align: center;">
        <h1>다른 사람의 마이페이지</h1>
        <!-- {{ this.$route.params.user_email }} -->

        <!--프로필 영역-->
        <div id="profile" style="margin-top: 20px;">
            <!--프로필 사진-->
            <v-avatar size="150px"><img :src=profile_photo></v-avatar><br>
            <!--닉네임, 게시글 수, 팔로잉 수, 팔로워 수-->
            <strong style="font-size: 20px;">{{ nickname }}</strong><br>
            게시글 <strong style="color: red;">{{ article_cnt }}</strong>
            팔로잉 <strong @click="moveFollow" style="color: red; cursor: pointer;">{{ following_cnt }}</strong>
            팔로워 <strong @click="moveFollow" style="color: red; cursor: pointer;">{{ follower_cnt }}</strong>
        </div>
        <div id="buttons">
            <button class="button" style="backgroundColor: skyblue;">팔로우</button>
        </div>
        <div id="favtags" style="margin-top: 10px;">
            <strong>관심태그</strong>
            <span v-for="tags in favtags" :key=tags>
                {{ tags }}&nbsp;
            </span>
        </div>

        <!--탭(유저 게시글 & 유저가 스크랩한 글)-->
        <v-tabs grow style="margin-top: 10px;">
            <!--유저 게시글-->
            <v-tab style="font-weight: bold;">게시글</v-tab>
            <v-tab-item style="padding-top: 15px;">
                <h1>유저 게시글</h1>
            </v-tab-item>
            <!--유저가 스크랩한 글-->
            <v-tab style="font-weight: bold;">스크랩</v-tab>
            <v-tab-item style="padding-top: 15px;">
                <h1>유저가 스크랩한 글</h1>
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
import { mapState, mapActions } from "vuex";
import { base } from "@/components/common/BaseURL.vue"; // baseURL
import BottomNav from "@/components/common/BottomNav";
import defaultProfile from "../../assets/images/profile_default.png";

export default {
    components:{
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
            text: "테스트2",
            defaultProfile
        }
    },
    created() {
        // 프로필 띄우기

        // 유저의 게시글, 스크랩한 글 목록 가져오기
    },
    methods: {

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
</style>