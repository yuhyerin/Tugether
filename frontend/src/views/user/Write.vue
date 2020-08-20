<template>
    <div class="wrapC" style="height: 800px;">
        <h3>이미지</h3>
          <div style="height: 230px; border: 1px solid lightgray;">
            <img :src="imageUrl" style="width: 100%; max-height: 230px;">
          </div>
          <div style="width: 100%; height: 15px;">
            <input type="file" ref="articleimg" name="articleimg" v-on:change='onFileSelected()' style="width: 100%; border: 0px; padding-left: 0px;">
          </div>
        <br>

        <h3>본문</h3>
        <textarea v-if="myText.length < 300"  maxlength="300" cols="60" rows="4" style="border:1px solid lightgray; width: 100%; height: 50px;" placeholder=" 입력" v-model="myText"></textarea>
        <textarea v-if="myText.length === 300"  maxlength="300" cols="60" rows="4" style="border:1px solid red; width: 100%; height: 50px;" placeholder=" 입력" v-model="myText"></textarea>
        <span> {{ myText.length }} / 300</span>
        <br>
        <br>

        <h3>관심태그</h3>
        <WriteInput @add-tag="onAddTag"/>
        <!-- <WriteList @delete="onRemove" @checked="onChecked" :todoList="todoList"/> -->
        <WriteList @delete="onRemove" :tagList="tagList"/>
        <br>

        <div>
        <h3 style="margin-top: 40px;">링크</h3>
        </div>
        <img src="@/assets/images/paperclip.png" style="float: left; height: 30px; width: 10%;">
        <input type="text" 
          placeholder="영상을 공유하고 싶다면 링크를 달아주세요"
          style="float: right; width: 88%; margin-left: 5px; margin-bottom: 2px; height: 40px; border: 1px solid gray;"
          v-model="urlLink" />
        
      <div style="width: 100%; display: inline-block">
        <button
          class="btn btn--back btn--login"
          @click="moveMypage"
          style="width: 49%; height: 40px; padding-top: 0px; margin-top: 30px; margin-right: 0px; float: left; color: white;"
          >BACK</button>
        <button
          v-on:click="onUpload"
          class="btn btn--back btn--login"
          style="width: 49%; height: 40px; padding-top: 0px; margin-top: 30px; margin-bottom: 50px; float: right; color: white;"
        >업로드</button>
      </div>
      <BottomNav/>
    </div>
</template>

<script>
import axios from 'axios'
import store from '@/vuex/store'
import { mapState, mapActions } from "vuex"
import WriteList from '@/components/user/WriteList'
import WriteInput from '@/components/user/WriteInput'
import { bus } from '@/event-bus'
import { base } from "@/components/common/BaseURL.vue"; // baseURL
import BottomNav from "@/components/common/BottomNav"

export default {
  name: 'Write',
  components: {
    WriteList,
    WriteInput,
    BottomNav
  },
  data: function() {
    return {
      imageUrl: null,
      selectedFile: null,
      myText: "",
      urlLink: null,
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
  methods: {
    checkForm () {
      if (this.myText.length >= 300) {
        alert("300자 이하로 기재해주세요.")
      }
    },
    
    onRemove (tag, index) {
      this.tagList.splice(index, 1)
    },

    onAddTag(tag) {
      this.tagList = [...this.tagList, tag]
      this.tagNameList = [...this.tagNameList, tag.content]
      console.log(this.tagList)
      console.log(this.tagNameList)

    },
    onFileSelected(){
      this.selectedFile = this.$refs.articleimg.files[0];
      this.imageUrl = URL.createObjectURL(this.selectedFile);
    },
    onUpload(){

      // 관심태그 리스트를 하나도 입력하지 않은 경우
      if (this.tagList.length == 0) {
        alert("관심태그를 1개 이상 입력해주세요!");
        return;
      }

      // 이미지, 링크 검사 후 글 작성 기능 동작
      const formdata = new FormData();
      if( this.selectedFile == null & (this.urlLink == null || this.urlLink == "") ) { // 두 개 다 작성 안한경우 
        alert("이미지나 링크 중 하나는 작성해주세요 :) ");
        return;

      } else if(this.selectedFile != null) { // 이미지 파일을 등록한 경우
        
        formdata.append('articleimg', this.selectedFile); //여기서 명시한 키값은 서버에서 사용하기때문에 바꾸면 안됩니당...
        formdata.append('contents', this.myText);
        formdata.append('link',this.urlLink);
        formdata.append('taglist', this.tagNameList);


      } else if(this.urlLink !=null) { // 링크를 등록한 경우
         
         formdata.append('contents', this.myText);
         formdata.append('link',this.urlLink);
         formdata.append('taglist', this.tagNameList);
         
      }

      // FormData 객체는 그 자체를 로깅하면 빈 객체만을 리턴한다.
      // FormData를 로깅하려면 FormData.entries()를 이용해서, key-value쌍을 뽑아야 한다.
      // for(let key of formdata.entries()){
      //   console.log(`${key}`)
      // }
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
         this.$router.push('/mypage')
       })
       .catch(err=>{
         console.log(err);
       });
       
       
    },
      moveMypage() {
        this.$router.push("/mypage");
      },
}
}
</script>

<style scoped>
.tag {
  margin: 3px;
}
</style>