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
    <textarea cols="60" rows="4" style="border:1px solid lightgray; width: 100%;" placeholder=" 입력" v-model="myText"></textarea>
    <span> {{ myText.length }} / 300</span>

    <br>
    <br>
    <h3>관심태그</h3>
    <WriteInput @add-tag="onAddTag" />
    <!-- <WriteList @delete="onRemove" @checked="onChecked" :todoList="todoList"/> -->
    <WriteList @delete="onRemove" :tagList="tagList"/>
    <br>

    <h3>링크</h3>
    <p><img src="@/assets/images/paperclip.png" style="height: 30px; width: 30px;">영상을 공유하고 싶다면 링크를 달아주세요</p>
    <input type="text" style="width: 100%; margin-bottom: 2px; height: 40px;" v-model="urlLink" />
    
    <button
      v-on:click="onUpload"
      class="btn btn--back btn--login"
      style="height: 40px; padding-top: 0px;"
    >업로드</button>
    
    <BottomNav/>
  </div>
</template>

<script>
import axios from 'axios'
import store from '@/vuex/store'
import { mapState, mapActions } from "vuex"
import UpdateList from '@/components/user/WriteList'
import UpdateInput from '@/components/user/WriteInput'
import { base } from "@/components/common/BaseURL.vue"; // baseURL
import BottomNav from "@/components/common/BottomNav"

export default {
  name: "Update",
  data: function () {
    return {
      imageUrl: null,
      selectedFile: null,
      myText: "",
      urlLink: "",
      favtags: [],
      tagList: [],
      tagNameList: [],
    }
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
    axios
      .get(base + '/tugether/articleupdate',{
          headers: {
            "jwt-auth-token": localStorage.getItem("token"), // 토큰 보내기
            "article_id": this.$route.params.article_id
          },
        })
      .then(res => {
          // console.log(res.data);
          this.selectedFile = res.data.article.image
          this.myText = res.data.article.content
          this.urlLink = res.data.article.link
          this.tagNameList = res.data.tag.tag_name
          this.favtags = res.data.favtaglist;

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
      if (this.myText.length > 300) {
        alert("300자 이하로 기재해주세요.")
      }
    },

    onRemove (tag, index) {
      this.tagList.splice(index, 1)
    },

    onAddTag(tag) {
      this.tagList = [...this.tagList, tag];
      this.tagNameList = [...this.tagNameList, tag.content];
      console.log(this.tagList)
      // this.tagNameList = [...this.tagNameList, tag.content]
      // console.log(this.tagNameList)

    },
    onFileSelected(){
      this.selectedFile = this.$refs.articleimg.files[0];
      this.imageUrl = URL.createObjectURL(this.selectedFile);
    },
    onUpload(){
      const formdata = new FormData();
      formdata.append('articleimg', this.selectedFile); //여기서 명시한 키값은 서버에서 사용하기때문에 바꾸면 안됩니당...
      formdata.append('contents', this.myText);
      formdata.append('link',this.urlLink);
      formdata.append('taglist', this.tagNameList);
      console.log(this.tagList)

      // FormData 객체는 그 자체를 로깅하면 빈 객체만을 리턴한다.
      // FormData를 로깅하려면 FormData.entries()를 이용해서, key-value쌍을 뽑아야 한다.
      for(let key of formdata.entries()){
        console.log(`${key}`)
      }
       axios.post(base + '/tugether/articlewrite',
       formdata,
        {
            headers:{
              "jwt-auth-token": localStorage.getItem("token"),
              "Content-Type" : 'multipart/form-data; charset=utf-8'
            }
        },
        )
       .then(res=>{
         console.log(res);
         alert("게시글이 작성되었습니다! :)")
         this.$router.push('/mypage/mypage')
       })
       .catch(err=>{
         console.log(err);
       });
       
    },
  }
}
</script>

<style>

</style>