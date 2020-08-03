<template>
    <div class="wrapC" style="height: 800px">
      
        <v-img v-if="imageUrl" :src="imageUrl" height="300px" width="300px"></v-img>
        <input type="file" ref="articleimg" name="articleimg" v-on:change='onFileSelected()'>
        
        <p><textarea cols="60" rows="5" style="border:1px solid black;" v-model="myText"></textarea></p>

        <ul>
          <li v-for="tag in tagList" :key="tag.id">
            <button class="tag">#{{ tag.content }}</button>
          </li>
        </ul>

        <p>영상을 공유하고 싶다면 링크를 달아주세요</p>
        <input type="text" style="width: 540px; margin-bottom: 2px;" v-model="urlLink"/>
        
        <button
          @click="onUpload"
          class="btn btn--back btn--login"
        >업로드</button>

    </div>
</template>

<script>
import axios from 'axios'
import store from '@/vuex/store'
import { mapState, mapActions } from "vuex";


const localhost_url = "http://localhost:8080/tugether/articletesty"

export default {
  name: 'Write',
  data: function() {
    return { 
      imageUrl: null,
      selectedFile: null,
      myText: "",
      urlLink: "",
      tagList: [],// 잠시 지워둠 
      selectedTags: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      count: 0,
      favTags: [],
      btnFunc: {backgroundColor: "gray"}
    }
  },
  computed:{
    ...mapState(["token"]), //store 공동 저장소에 있는 token 사용하기 위해 선언.
    ...mapActions(["getToken"]),
  },
  methods: {
    onFileSelected(){
      this.selectedFile = this.$refs.articleimg.files[0];
      this.imageUrl = URL.createObjectURL(this.selectedFile);
    },
    onUpload(){
      const formdata = new FormData();
      formdata.append('articleimg', this.selectedFile); //여기서 명시한 키값은 서버에서 사용하기때문에 바꾸면 안됩니당...
      formdata.append('contents',this.myText);
      formdata.append('link',this.urlLink);

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
       })
       .catch(err=>{
         console.log(err);
       });
    },
    
    
  },
  components: {
    
  }
};
</script>

<style scoped>
.tag {
  margin: 3px;
}
</style>