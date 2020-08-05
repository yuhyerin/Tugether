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

        <!--tab test-->
        <div class="tabs" style="margin-top: 50px;">
            <TabItem
                v-for="item in list"
                v-bind="item" :key="item.id"
                v-model="currentId" style="font-size: 20px;" />
            </div>
            <div class="contents" style="margin: 0 auto; text-align: center;">
            <transition>
                <section class="item" :key="currentId">
                {{ current.content }}
                </section>
            </transition>
        </div>

      </div>
  </div>
</template>

<script>
import axios from "axios";
import store from '@/vuex/store';
import { mapState, mapActions } from "vuex";
import { base } from "@/components/common/BaseURL.vue"; // baseURL
import TabItem from '@/components/common/TabItem.vue'

export default {
    components: { TabItem },
    data: () => {
        return {
            profile_photo: "",
            nickname: "만두",
            following_cnt: "123",
            follower_cnt: "321",
            article_cnt: "10",
            favtags: ['음악', '먹방', '게임'],
            currentId: 1,
            list: [
                { id: 1, label: '게시글', content: '콘텐츠1' },
                { id: 2, label: '스크랩', content: '콘텐츠2' },
            ]
        }
    },
    created() {
        // console.log(localStorage.getItem("token"));

        // 토큰 보내기
        // axios
        //     .post(base + '/tugether', {
        //         headers:{
        //             "jwt-auth-token": localStorage.getItem("token")
        //         }
        //     })
        //     .then(({data}) => {

        //     })
        //     .catch((err) => {

        //     });

        // 프로필 띄우기
        // axios
        //     .get(base + '/tugether')
        //     .then((res) => {
        //         console.log(res.data);
        //     })
        //     .catch((err) => {

        //     });
    },
    methods: {
        // 페이지 이동
        moveSetting() {
            this.$router.push("/mypage/mypagesetting");
        },
        moveWrite() {
            // 글쓰기 페이지로 이동
            // console.log(this.$store.state.token);
            console.log(localStorage.getItem("token"));
        },
        moveFollow() {
            this.$router.push("/mypage/follow");
        }
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
    #tags_test{
        color: white;
        background-color: red;
        /* border: 10px solid red; */
        /* border-radius: 3px; */
    }
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