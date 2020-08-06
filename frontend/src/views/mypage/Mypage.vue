<template>
  <div class="feed mypage">
      <div class="wrapB" style="text-align: center;">
        <!-- <h1>마이페이지</h1> -->
        <div id="profile" style="margin-top: 20px;">
            <img src="../../assets/images/profile_default.png" style="size: 100px;"><br>
            <img src ="/tugetherimg/profile/default.png">
            <v-img name="profilephoto" :src="this.profile_photo"></v-img>
            
            <strong style="font-size: 20px;">{{ this.nickname }}</strong><br>
            게시글 <strong style="color: red;">{{ this.article_cnt }}</strong>
            팔로잉 <strong @click="moveFollow" style="color: red; cursor: pointer;">{{ this.following_cnt }}</strong>
            팔로워 <strong @click="moveFollow" style="color: red; cursor: pointer;">{{ this.follower_cnt }}</strong>
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
            nickname: "",
            following_cnt: "",
            follower_cnt: "",
            article_cnt: "",
            favtags: ['음악', '먹방', '게임'],
            currentId: 1,
            list: [
                { id: 1, label: '게시글', content: '콘텐츠1' },
                { id: 2, label: '스크랩', content: '콘텐츠2' },
            ]
        }
    },
    created() {

        // 토큰 보내기
        axios
            .get(base + '/tugether/profile', {
                headers:{
                    "jwt-auth-token": localStorage.getItem("token")
                }
            })
            .then((res) => {
                    
                    console.log(res.data);
                    console.log(res.data.profile.profile_photo);
                    
                    this.profile_photo = res.data.profile.profile_photo;
                    this.nickname = res.data.profile.nickname;
                    this.following_cnt = res.data.profile.following_cnt;
                    this.follower_cnt = res.data.profile.follower_cnt;

            })
            .catch((err) => {

            });

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