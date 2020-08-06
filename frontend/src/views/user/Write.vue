<template>
    <div class="wrapC" style="height: 800px">
        <h2>이미지</h2>
        <div class="row">
          <div class="col">
            <input type="file" ref="articleimg" name="articleimg" v-on:change='onFileSelected()' style="width: 100%">
          </div>
          <div class="col">
            <v-img v-if="imageUrl" :src="imageUrl" style="height: 35f0px; width: 350px;"></v-img>
          </div>
        </div>
        <hr>

        <h2>본문</h2>
        <p><textarea cols="60" rows="5" style="border:1px solid black; width: 100%;" placeholder="입력" v-model="myText"></textarea></p>
        <hr>

        <WriteInput @add-tag="onAddTag" />
        <!-- <WriteList @delete="onRemove" @checked="onChecked" :todoList="todoList"/> -->
        <WriteList @delete="onRemove" :tagList="tagList"/>
        <hr>
        
        <h2>링크</h2>
        <p><img src="@/assets/images/paperclip.png" style="height: 30px; width: 30px;">영상을 공유하고 싶다면 링크를 달아주세요</p>
        <input type="text" style="width: 100%; margin-bottom: 2px;" v-model="urlLink"/>
        
        <button
          v-on:click="onUpload"
          class="btn btn--back btn--login"
        >업로드</button>

    </div>
</template>

<script>
import axios from 'axios'
import store from '@/vuex/store'
import { mapState, mapActions } from "vuex";
import WriteList from '@/components/user/WriteList'
import WriteInput from '@/components/user/WriteInput'
import { bus } from '@/event-bus'

const localhost_url = "http://localhost:8080/tugether/articlewrite"

export default {
  name: 'Write',
  components: {
    WriteList,
    WriteInput,
    // FilePond
  },
  data: function() {
    return {
      imageUrl: null,
      selectedFile: null,
      myText: "",
      urlLink: "",
      tagList: [],
      tagNameList: [],

    }
  },
  computed:{
    ...mapState(["token"]), //store 공동 저장소에 있는 token 사용하기 위해 선언.
    ...mapActions(["getToken"]),
  },
  methods: {

    onRemove (tag, index) {
      this.tagList.splice(index, 1)
      this.tagNameList.splice(index, 1)
      console.log(this.tagNameList)
    },

    onAddTag(tag) {
      this.tagList = [...this.tagList, tag]
      this.tagNameList = [...this.tagNameList, tag.content]
      console.log("태그 Object 리스트   ")
      console.log(this.tagList)
      console.log("태그 String 리스트   ")
      console.log(this.tagNameList)
    },
    onFileSelected(){
      this.selectedFile = this.$refs.articleimg.files[0];
      this.imageUrl = URL.createObjectURL(this.selectedFile);
    },
    onUpload(){
      const formdata = new FormData();
      formdata.append('articleimg', this.selectedFile); //여기서 명시한 키값은 서버에서 사용하기때문에 바꾸면 안됩니당...
      formdata.append('contents',this.myText);
      formdata.append('link',this.urlLink);
      formdata.append('taglist', this.tagNameList);
      console.log(this.tagList)

      // FormData 객체는 그 자체를 로깅하면 빈 객체만을 리턴한다.
      // FormData를 로깅하려면 FormData.entries()를 이용해서, key-value쌍을 뽑아야 한다.
      for(let key of formdata.entries()){
        console.log(`${key}`)
      }
       axios.post(`${localhost_url}`,
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
         this.$router.push('/imgtest')
       })
       .catch(err=>{
         console.log(err);
       });
    },
}
}
</script>

<style scoped>
.tag {
  margin: 3px;
}

 
</style>