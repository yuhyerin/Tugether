<template>
    <div class="wrapC" style="height: 800px">

        <button @click="logout">로그아웃</button>
        <input type="file" ref="articleimg" name="articleimg" v-on:change='onFileSelected()'>
        <v-img v-if="imageUrl" :src="imageUrl" height="350px" width="350px"></v-img>

        <p><textarea cols="60" rows="5" style="border:1px solid black;" v-model="myText"></textarea></p>

        <WriteInput @add-todo="onAddTodo"/>
        <!-- <WriteEmit @delete="onRemove" @checked="onChecked" :todoList="todoList"/> -->
        <WriteEmit @delete="onRemove" :todoList="todoList"/>

        <p><img src="@/assets/images/paperclip.png" style="height: 40px; width: 40px;">영상을 공유하고 싶다면 링크를 달아주세요</p>
        <input type="text" style="width: 540px; margin-bottom: 2px;" v-model="urlLink"/>
        
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
import WriteEmit from '@/components/user/WriteEmit'
import WriteInput from '@/components/user/WriteInput'
import vueFilePond from 'vue-filepond';

// filepond
// import FilePondPluginFileValidateType from 'filepond-plugin-file-validate-type/dist/filepond-plugin-file-validate-type.esm.js';
// import FilePondPluginImagePreview from 'filepond-plugin-image-preview/dist/filepond-plugin-image-preview.esm.js';
// import 'filepond/dist/filepond.min.css';
// import 'filepond-plugin-image-preview/dist/filepond-plugin-image-preview.min.css';
// const FilePond = vueFilePond( FilePondPluginFileValidateType, FilePondPluginImagePreview );

const localhost_url = "http://localhost:8080/tugether/articlewrite"

export default {
  name: 'Write',
  components: {
    WriteEmit,
    WriteInput,
    // FilePond
  },
  data: function() {
    return { 
      imageUrl: null,
      selectedFile: null,
      myText: "",
      urlLink: "",
      // tagList: [],
      // selectedTags: [0, 0, 0, 0, 0, 0, 0, 0],
      // count: 0,
      // favTags: [],
      // btnFunc: {backgroundColor: "gray"},
      todoList: [
        '#고양이',
        '#집사',
        '#냥이'
       
      ],

    }
  },
  computed:{
    ...mapState(["token"]), //store 공동 저장소에 있는 token 사용하기 위해 선언.
    ...mapActions(["getToken"]),
  },
  methods: {
    // handleFilePondInit: function() {
    //   console.log('FilePond has initialized');
    //   this.selectedFile = this.$refs.articleimg.getFiles();
    // },
    logout(){
      
      this.$store.commit('logout');
      localStorage.clear();
      alert("로그아웃 되었습니다.");
      this.$router.push("/")
      
    },
    onRemove (todo, index) {
      this.todoList.splice(index, 1)
    },
    // onChecked(todo) {
    //   todo.isCompleted = !todo.isCompleted
    // },
    onAddTodo(todo) {
      this.todoList = [...this.todoList, todo]
      console.log(this.todoList[1])
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
      formdata.append('taglist', this.todoList);
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
}
}
</script>

<style scoped>
.tag {
  margin: 3px;
}

 
</style>