<template>
    <div class="feed mypage">
        <div class="wrapB" style="text-align: center; margin-top: 5%; overflow: auto;">
            <strong style="font-size: 30px; margin-bottom: 10px;">프로필 편집</strong>

            <!--프로필 사진-->
            <div class="filebox" id="photo">
                <v-avatar size="150px" style="margin-bottom: 15px;"><img :src=imageUrl></v-avatar>
                <label for="profile_photo" style="color: red; margin-left: 20px; font-weight: bold;">프로필 사진 변경하기</label>
                <input type="file" id="profile_photo" ref="profile_photo" name="profile_photo" @change="onFileSelected">
            </div>
            <!--사진 첨부 시 안내 메세지 출력-->
            <strong>{{ upload_ok }}</strong>

            <!--닉네임-->
            <div class="input-with-label">
                <label for="nickname" style="margin-top: -3px;">닉네임</label>
                <input v-model="nickname" id="nickname" placeholder=nickname type="text" />
            </div>

            <!--비밀번호-->
            <div class="row" style="padding-top: 0px; margin: 0px; border: 1px solid black; border-radius: 3px; height: 50px; width: 100%;  font-weight: 600; font-size: 0.857em;">
                <div class="col-3" style="margin-left: -10px;">
                    <label for="password" style="margin-top: 3px;">비밀번호</label>
                </div>
                <div class="col-2">
                   <button class="button" @click="moveConfirmPW"
                    style="width: 180px; margin: -5px 0px 0px 30px; background-color: red; font-size: 13px;">비밀번호 변경</button>
                </div>
            </div>
            <v-divider></v-divider>

            <!--관심태그 추가 및 삭제-->
            <div style="text-align: left; margin-top: 5px;">
                <h3>관심태그</h3>
                <WriteInput @add-tag="onAddTag" />
                <WriteList @delete="onRemove" :tagList="tagList" />
            </div>

            <!--버튼-->
            <div id="mypage_buttons">
                <!--마이페이지로 돌아가기 버튼-->
                <button class="button" :style="mybtn1" @mouseover="over1" @mouseout="out1"
                    @click="moveMypage" style="width: 48%; height: 45px; margin-right: 10px; float: left;">BACK</button>
                <!--변경한 내용 저장하기 버튼-->
                <button class="button" :style="mybtn2" @mouseover="over2" @mouseout="out2"
                    @click="changeProfile" style="width: 48%; height: 45px; float: right;">변경하기</button>
            </div>

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
import WriteList from '@/components/user/WriteList';
import WriteInput from '@/components/user/WriteInput';
import BottomNav from "@/components/common/BottomNav";

export default {
    components: {
        WriteList,
        WriteInput,
        BottomNav
    },
    data: () => {
        return {
            imageUrl: null,
            selectedFile: null,
            profile_photo: "",
            nickname: "",
            favtags: ['음악','먹방','게임','메이플스토리','드럼','IT'],
            mybtn1: {
                backgroundColor: "black"
            },
            mybtn2: {
                backgroundColor: "black"
            },
            tagList: [],
            tagNameList: [],
            upload_ok: ""
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
                // console.log(res.data);
                this.imageUrl = 'https://i3b303.p.ssafy.io/profileimages/' + res.data.profile.profile_photo;
                this.profile_photo = 'https://i3b303.p.ssafy.io/profileimages/' + res.data.profile.profile_photo;
                this.nickname = res.data.profile.nickname;
                this.favtags = res.data.favtaglist;

                for(var i=0; i<this.favtags.length; i++) {
                    var fav = new Object();
                    fav['id'] = Date.now();
                    fav['content'] = this.favtags[i]; // this.favtags[i] => content
                    // this.tagList[i] = fav;
                    this.onAddTag(fav);
                }
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
        // 사진 업로드
        onFileSelected(){
            this.selectedFile = this.$refs.profile_photo.files[0];
            this.imageUrl = URL.createObjectURL(this.selectedFile);
            this.upload_ok = "사진이 첨부되었습니다😊";
        },
        // 프로필 변경하기
        changeProfile() {
            const formdata = new FormData();
            formdata.append('profile_photo', this.selectedFile);
            formdata.append('nickname', this.nickname);
            formdata.append('taglist', this.tagNameList);

            // FormData 객체는 그 자체를 로깅하면 빈 객체만을 리턴한다.
            // FormData를 로깅하려면 FormData.entries()를 이용해서, key-value쌍을 뽑아야 한다.
            for(let key of formdata.entries()){
                // console.log(`${key}`)
            }
            axios.post(base + '/tugether/profilechange',
            formdata,
                {
                    headers:{
                        "jwt-auth-token": localStorage.getItem("token"),
                        "Content-Type" : 'multipart/form-data; charset=utf-8'
                    }
                },
                )
            .then((res) => {
                // console.log(res);
                alert("프로필 변경이 완료되었습니다.");
                this.moveMypage(); // 마이페이지로 이동
            })
            .catch((err) => {
                console.log("change profile function error")
            });
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
            if(localStorage.getItem("googletoken")){
                alert("구글로그인 회원은 비밀번호 변경기능이 제한됩니다 :) ")
            } else {
                this.$router.push("/mypage/mypagepasswordconfirm");
            }
        },
        moveMypage() {
            this.$router.push("/mypage");
        },
        // 관심태그 추가 및 삭제 기능
        onRemove (tag, index) {
            this.tagList.splice(index, 1);
            this.tagNameList.splice(index, 1);
        },
        onAddTag(tag) {
            this.tagList = [...this.tagList, tag];
            this.tagNameList = [...this.tagNameList, tag.content];
            // console.log(this.tagList)
        }
    },
}
</script>

<style scoped>
    #mypage_buttons{
        width: 100%;
        margin: 0 auto;
        padding-top: 30px;
        display: inline-block;
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
    .button{
        background: black;
        color: white;
        width: 25%;
        height: 35px;
    }
</style>