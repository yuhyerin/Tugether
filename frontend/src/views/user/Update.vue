<template>
  <div class="wrapC" style="height: 800px">
    <h3>이미지</h3>
      <div style="height: 230px; border: 1px solid lightgray;">
        <img :src="imageUrl" style="width: 100%; max-height: 230px;">
      </div>
      <div style="width: 100%; height: 15px;">
        <input type="file" ref="articleimg" name="articleimg" @change="onFileSelected" style="width: 100%; border: 0px; padding-left: 0px;">
      </div>
    <br>

    <h3>본문</h3>
    <textarea v-if="myText.length < 300"  maxlength="300" cols="60" rows="4" style="border:1px solid lightgray; width: 100%; height: 50px;" placeholder=" 입력" v-model="myText"></textarea>
    <textarea v-if="myText.length === 300"  maxlength="300" cols="60" rows="4" style="border:1px solid red; width: 100%; height: 50px;" placeholder=" 입력" v-model="myText"></textarea>
    <span> {{ myText.length }} / 300</span>

    <br>
    <br>
    <h3>관심태그</h3>
      <input type="text" v-model="content" @keypress.enter="addTag" style="width: 85%; float: left">
      <button @click="addTag" style="width: 13%; float: right; margin-left: 0px; padding: 0px 10px 0px 10px; height: 50px; background: black; color: white; border: 0px solid skyblue;">추가</button>
      <br>

      
      <div>
      <ul style="padding-top: 0px;">
        <!-- 목록을 보여줄 예정 -->
        <li v-for="(tag, index) in this.tagList" :key="tag.id">
          <span>{{ tag.content }}</span>
          <button @click="onRemove(tag, index)" style="padding: 0px 5px 0px 5px; margin: 3px 10px 3px 10px; height: 20px; background: red; color: white;">X</button>
        </li>
      </ul>
      </div>
    <br>

    <h3>링크</h3>
    <!-- <p>영상을 공유하고 싶다면 링크를 달아주세요</p> -->
    <img src="@/assets/images/paperclip.png" style="float: left; height: 30px; width: 30px;">
    <input type="text"
      placeholder="영상을 공유하고 싶다면 링크를 달아주세요"
      style="float: right; width: 90%; margin-left: 5px; margin-bottom: 2px; height: 40px;" 
      v-model="urlLink" />
    
    <div style="width: 100%; display: inline-block">
      <button
        class="btn btn--back btn--login"
        @click="moveMypage"
        style="width: 49%; height: 40px; padding-top: 0px; margin-top: 30px; margin-right: 0px; float: left;"
        >BACK</button>
      <button
        v-on:click="onUpload"
        class="btn btn--back btn--login"
        style="width: 49%; height: 40px; padding-top: 0px; margin-top: 30px; margin-bottom: 50px; float: right;"
      >수정</button>
    </div>

    <BottomNav/>
  </div>
</template>

<script>
import axios from 'axios'
import store from '@/vuex/store'
import { mapState, mapActions } from "vuex"
import { base } from "@/components/common/BaseURL.vue"; // baseURL
import BottomNav from "@/components/common/BottomNav"

export default {
  name: "Update",
  data: function () {
    return {
      article_id : "",
      imageUrl: null,
      selectedFile: null,
      myText: "",
      urlLink: "",
      favtags: [],
      tagList: [],
      tagNameList: [],
    }
  },
  components: {
    BottomNav
  },
  watch: {
    myText: function() {
      this.checkForm();
    }
  },
  computed:{
    ...mapState(["token"]), //store 공동 저장소에 있는 token 사용하기 위해 선언.
    ...mapActions(["getToken"]),
  },
  created() {
    // this.article_id = this.$route.params.article_id;
    // 초기 수정폼에 글내용 불러오기 
    axios
      .get(base + '/tugether/articleloading',{
          params: {
            "article_id" : parseInt(this.$route.params.article_id)
          },
          headers: {
              "jwt-auth-token": localStorage.getItem("token")
              
          },
        })
      .then(res => {
          this.imageUrl = 'https://i3b303.p.ssafy.io/articleimages/'+res.data.article.image;
          this.selectedFile =res.data.article.image
          this.myText = res.data.article.content
          this.urlLink = res.data.article.link
          this.favtags = res.data.favtaglist
          console.log("관심태그 리스트")
          console.log(this.favtags);
          for(var i=0; i<this.favtags.length; i++) {
              var fav = new Object();
              fav['id'] = Date.now();
              fav['content'] = this.favtags[i]; // this.favtags[i] => content
              // this.tagList[i] = fav;
              this.onAddTag(fav);
          }
        })
      .catch(err => {
        console.log("수정 실패")
      })
    },
  methods: {
    checkForm () {
      if (this.myText.length >= 300) {
        alert("300자 이하로 기재해주세요.")
      }
    },

    onRemove (tag, index) {
      this.tagList.splice(index, 1)
      this.tagNameList.splice(index, 1)
    },

    addTag() {
      if (this.content.trim()) {
        const tag = {
          id: Date.now(),
          content: this.content,
        }
        this.onAddTag(tag)
        this.content = ''
      }
    },

    onAddTag(tag) {
      this.tagList = [...this.tagList, tag];
      this.tagNameList = [...this.tagNameList, tag.content];
      console.log("onAddTag : ")
      console.log(this.tagList)

    },
    onFileSelected(){
      this.selectedFile = this.$refs.articleimg.files[0]; // MultipartFile Object
      this.imageUrl = URL.createObjectURL(this.selectedFile);
    },
    // 수정하기 
    onUpload(){
      const formdata = new FormData();
      formdata.append('article_id',this.$route.params.article_id); //게시글 아이디도 전달.
      formdata.append('articleimg', this.selectedFile); //여기서 명시한 키값은 서버에서 사용하기때문에 바꾸면 안됩니당...
      formdata.append('contents', this.myText);
      formdata.append('link',this.urlLink);
      formdata.append('taglist', this.tagNameList);

      // FormData 객체는 그 자체를 로깅하면 빈 객체만을 리턴한다.
      // FormData를 로깅하려면 FormData.entries()를 이용해서, key-value쌍을 뽑아야 한다.
      for(let key of formdata.entries()){
        console.log(`${key}`)
      }
       axios.post(base + '/tugether/articleupdate',formdata,
        {
          headers:{
            "jwt-auth-token": localStorage.getItem("token"),
            "Content-Type" : 'multipart/form-data; charset=utf-8'
          }
        },
        )
       .then(res=>{
         console.log(res);
         alert("게시글이 수정되었습니다! :)")
         this.$router.push('/mypage')
       })
       .catch(err=>{
         alert("게시글 작성을 실패하였습니다:(")
         console.log(err);
       });
       
    },
    moveMypage() {
      this.$router.push("/mypage");
    },
  }
}
</script>

<style>

</style>