<template>
  <div class="feed mypage">
      <div class="wrapB" style="text-align: center;">
        <!-- <h1>마이페이지</h1> -->
        <div id="profile" style="margin-top: 20px;">
            <img src="../../assets/images/profile_default.png" style="size: 100px;"><br>
            <strong style="font-size: 20px;">{{ nickname }}</strong><br>
            게시글 <strong style="color: red;">{{ article_cnt }}</strong>
            팔로잉 <strong @click="moveFollow" style="color: red; cursor: pointer;">{{ following_cnt }}</strong>
            팔로워 <strong @click="moveFollow" style="color: red; cursor: pointer;">{{ follower_cnt }}</strong>
        </div>
        <div id="buttons">
            <!-- 나의 프로필 편집 버튼 위치에 다른 사람은 팔로우 버튼이 보인다. (아마도?) -->
            <button class="button" @click="moveSetting">프로필 편집</button>&nbsp;
            <button class="button" @click="moveWrite">글 작성</button>
        </div>
        <div id="favtags" style="margin-top: 10px;">
            <strong>관심태그 </strong>
            <span id="tags_test" v-for="tags in favtags" :key=tags>
                #{{ tags }}&nbsp;
            </span>
        </div>

        <!-- <v-tabs style="centered: true;">
            <v-tab>게시글</v-tab>
            <v-tab>스크랩</v-tab> -->
            <!-- <v-tab-item>
                <v-card flat>
                    <v-card-text>{{ text }}</v-card-text>
                </v-card>
            </v-tab-item> -->
        <!-- </v-tabs> -->

        <!--tab test-->
        <div class="tabs" style="margin-top: 30px;">
            <TabItem
                v-for="item in list"
                v-bind="item" :key="item.id"
                v-model="currentId"/>
            </div>
            <div class="contents" style="margin: 0 auto; text-align: center;">
            <!-- <transition> -->
                <section class="item" :key="currentId">
                {{ current.content }}
                </section>
            <!-- </transition> -->
        </div>

      </div>
  </div>
</template>

<script>
import axios from "axios";
import { base } from "@/components/common/BaseURL.vue"; // baseURL
import TabItem from '@/components/common/TabItem.vue'

export default {
    components: { TabItem },
    data: () => {
        return {
            nickname: "만두",
            profile_photo: "",
            following_cnt: "123",
            follower_cnt: "321",
            article_cnt: "10",
            favtags: ['음악', '먹방', '게임'],
            currentId: 1,
            list: [
                { id: 1, label: '게시글', content: '콘텐츠1' },
                { id: 2, label: '스크랩한 글', content: '콘텐츠2' },
            ]
        }
    },
    created() {
        // 프로필 띄우기
    },
    methods: {
        // 페이지 이동
        moveSetting() {
            this.$router.push("/mypage/mypagesetting");
        },
        moveWrite() {
            // 글쓰기 페이지로 이동
        },
        moveFollow() {
            this.$router.push("/mypage/follow");
        }
    },
    computed: {
        current() {
            return this.list.find(el => el.id === this.currentId) || {}
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
    #tags_test{
        color: white;
        background-color: red;
        /* border: 10px solid red; */
        /* border-radius: 3px; */
    }
</style>

<style scoped>
.contents {
  position: relative;
  overflow: hidden;
  width: 280px;
  /* border: 2px solid #000; */
}
.item {
  box-sizing: border-box;
  padding: 10px;
  width: 100%;
  transition: all 0.8s ease;
}
/* 트랜지션 전용 스타일 */
.v-leave-active {
  position: absolute;
}
.v-enter {
  transform: translateX(-100%);
}
.v-leave-to {
  transform: translateX(100%);
}
</style>