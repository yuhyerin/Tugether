<template>
  <div class="feed mypage">
      <div class="wrapB" style="text-align: center;">
        <!-- <h1>마이페이지</h1> -->
        <div id="profile" style="margin-top: 20px;">
            <img src="../../assets/images/profile_default.png" style="size: 100px;"><br>
            <strong style="font-size: 20px;">{{ nickname }}</strong><br>
            게시글 <strong style="color: red;">{{ article_cnt }}</strong>
            팔로잉 <strong style="color: red;">{{ following_cnt }}</strong>
            팔로워 <strong style="color: red;">{{ follower_cnt }}</strong>
        </div>
        <div id="buttons">
            <!-- 나의 프로필 편집 버튼 위치에 다른 사람은 팔로우 버튼이 보인다. (아마도?) -->
            <button class="button" @click="moveSetting">프로필 편집</button>&nbsp;
            <button class="button" @click="moveWrite">글 작성</button>
        </div>
        <div id="favtags" style="margin-top: 10px;">
            <strong>관심태그</strong>
            <div v-for="tags in favtags" :key=tags>
                {{ tags }}&nbsp;
            </div>
        </div>

        <v-tabs style="centered: true;">
            <v-tab>게시글</v-tab>
            <v-tab>스크랩</v-tab>
            <!-- <v-tab-item>
                <v-card flat>
                    <v-card-text>{{ text }}</v-card-text>
                </v-card>
            </v-tab-item> -->
        </v-tabs>

      </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
    data: () => {
        return {
            nickname: "만두",
            profile_photo: "",
            following_cnt: "123",
            follower_cnt: "321",
            article_cnt: "10",
            favtags: ['음악', '먹방', '게임'],
        }
    },
    created() {
        // 프로필 띄우기
    },
    methods: {
        // 페이지 이동
        moveSetting() {
            this.$router.push("/mypage/mypagesetting")
        },
        moveWrite() {
            // 글쓰기 페이지로 이동
        }
    }
}
</script>

<style>
    .button{
        background: black;
        color: white;
        width: 25%;
        height: 35px;
    }
    /* .v-tabs{
        centered: true;
    } */
</style>