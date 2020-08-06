<template>
    <div class="feed mypage">
        <div class="wrapB" style="text-align: center;">
            <strong style="font-size: 30px; margin-top: 30px;">프로필 편집</strong>

            <!--프로필 사진-->
            <div class="filebox" id="photo">
                <v-avatar size="150px" style="margin-bottom: 15px;"><img :src=profile_photo></v-avatar>
                <!-- <strong style="color: red; margin-left: 20px">프로필 사진 변경하기</strong> -->
                <label for="image_setting" style="color: red; margin-left: 20px; font-weight: bold;">프로필 사진 변경하기</label>
                <input type="file" id="image_setting">
            </div>
            <!--닉네임-->
            <div class="input-with-label">
                <label for="nickname" style="margin-top: -3px;">닉네임</label>
                <input v-model="nickname" id="nickname" placeholder=nickname type="text" />
            </div>
            <!--비밀번호-->
            <div class="row" style="padding-top: 0px; margin: 0px; border: 1px solid black; border-radius: 3px; height: 50px; width: 100%;  font-weight: 600; font-size: 0.857em;">
                <div class="col-3" style="margin-left: -35px;">
                    <label for="password" style="margin-top: 3px;">비밀번호</label>
                </div>
                <div class="col-2">
                   <button class="button" @click="moveConfirmPW"
                    style="width: 180px; margin-top: -5px; background-color: red;">비밀번호 변경</button>
                </div>
            </div>
            <!--관심태그-->
            <div class="row" style="border: 1px solid black; border-radius: 3px; min-height: 40px; margin-top: 10px;
                padding: 5px 0 0 10px; margin-left: 1px; width: 100%; font-weight: 600; font-size: 0.857em;">
                <label for="favtags" style="margin-top: 5px;">관심태그</label>
                <div style="font-size: 20px; margin-left: 60px; text-align: left;">
                    <span v-for="tags in favtags" :key=tags>
                        #{{ tags }}
                    </span>
                </div>
            </div>
            <v-divider></v-divider>
            <div style="text-align: left; margin-top: 5px;">
                <!--관심태그 추가 및 삭제-->
                <h3>관심태그</h3>
                <WriteInput @add-tag="onAddTag" />
                <WriteList @delete="onRemove" :tagList="tagList" />
            </div>

            <!--버튼-->
            <div id="mypage_buttons">
                <!--마이페이지로 돌아가기 버튼-->
                <button class="button" :style="mybtn1" @mouseover="over1" @mouseout="out1"
                    @click="moveMypage" style="width: 200px; height: 45px; margin-right: 10px;">BACK</button>
                <!--변경한 내용 저장하기 버튼-->
                <button class="button" :style="mybtn2" @mouseover="over2" @mouseout="out2"
                    style="width: 200px; height: 45px;">변경하기</button>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "axios";
import store from '@/vuex/store';
import { mapState, mapActions } from "vuex";
import { base } from "@/components/common/BaseURL.vue"; // baseURL
import WriteList from '@/components/user/WriteList';
import WriteInput from '@/components/user/WriteInput';

export default {
    components: {
        WriteList,
        WriteInput
    },
    data: () => {
        return {
            profile_photo: "",
            nickname: "",
            favtags: ['음악','먹방','게임','메이플스토리','드럼','IT'],
            mybtn1: {
                backgroundColor: "black"
            },
            mybtn2: {
                backgroundColor: "black"
            },
            tagList: [
                // {
                // id: 1,
                // content: '당신의 관심태그를 추가해주세요.',
                // }
            ]
        }
    },
    created() {
        // 프로필 사진, 닉네임, 관심태그 불러오기
        axios
            .get(base + '/tugether/profile', {
                headers:{
                    "jwt-auth-token": localStorage.getItem("token") // 토큰 보내기
                }
            })
            .then((res) => {
                console.log(res.data);
                this.profile_photo = 'https://i3b303.p.ssafy.io/profileimages/' + res.data.profile.profile_photo;
                this.nickname = res.data.profile.nickname;
                // this.favtags = res.data.profile.favtags; // 관심태그 목록
            })
            .catch((err) => {
                console.log("created axios get error")
            });
    },
    computed: {
        ...mapState(["token"]), //store 공동 저장소에 있는 token 사용하기 위해 선언
        ...mapActions(["getToken"])
    },
    methods: {
        // 프로필 변경하기
        changeProfile() {
            axios
                .post(base + '/tugether/profile', {
                    headers:{
                        "jwt-auth-token": localStorage.getItem("token") // 토큰 보내기
                    },
                    // profile_photo: this.profile_photo, // 사진 업로드는 onUpload() 메소드 참조
                    nickname: this.nickname,
                    favtags: this.favtags
                })
                .then(({data}) => {
                    console.log(data.data);

                    alert("프로필 변경이 완료되었습니다.");
                    this.moveMypage(); // 마이페이지로 이동
                })
                .catch((err) => {

                });
        },
        // 사진 업로드
        onUpload() {
            const formdata = new FormData();
            formdata.append('articleimg', this.selectedFile);
        },
        // 버튼에 마우스 갖다대면 빨갛게 변하도록
        over1() {
            this.mybtn1.backgroundColor = "red";
        },
        out1() {
            this.mybtn1.backgroundColor = "black";
        },
        over2() {
            this.mybtn2.backgroundColor = "red";
        },
        out2() {
            this.mybtn2.backgroundColor = "black";
        },
        // 페이지 이동
        moveConfirmPW() {
            this.$router.push("/mypage/mypagepasswordconfirm");
        },
        moveMypage() {
            this.$router.push("/mypage/mypage");
        },
        // 관심태그 추가 및 삭제 기능
        onRemove (tag, index) {
            this.tagList.splice(index, 1);
        },
        onAddTag(tag) {
            this.tagList = [...this.tagList, tag];
            console.log(this.tagList)
        }
    },
}
</script>

<style scoped>
    #mypage_buttons{
        width: 100%;
        margin: 0 auto;
        padding-top: 50px;
        float: left;
    }
    .wrapB:before{
        content: '';
        display: inline-block;
        height: 100%;
        vertical-align: middle;
    }
    .filebox label {
        display: inline-block;
        padding: .5em .75em;
        color: red;
        font-size: inherit;
        line-height: normal;
        vertical-align: middle;
        background-color: white;
        cursor: pointer;
        border: 1px solid white;
        border-radius: .25em;
        -webkit-transition: background-color 0.2s;
        transition: background-color 0.2s;
    }

    .filebox label:hover {
        background-color: white;
    }

    .filebox label:active {
        background-color: white;
    }

    .filebox input[type="file"] {
        position: absolute;
        width: 1px;
        height: 1px;
        padding: 0;
        margin: -1px;
        overflow: hidden;
        clip: rect(0, 0, 0, 0);
        border: 0;
    }
</style>