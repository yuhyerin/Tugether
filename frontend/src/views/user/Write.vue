<template>
    <div class="wrapC" style="height: 800px">
<<<<<<< HEAD
      <file-pond
        name="test"
        ref="pond"
        label-idle="Images Drop Here"
        v-bind:allow-multiple="false"
        accepted-file-types="image/jpeg, image/png"
        server=localhost_url
        v-bind:files="myFiles"
        v-on:init="handleFilePondInit" />


        <p><textarea cols="60" rows="5" style="border:1px solid black;" v-model="myText"></textarea></p>
        <!-- <p><input type="submit" value="Submit"></p> -->

        <ul>
          <li v-for="tag in tagList" :key="tag.id">
            <button class="tag">#{{ tag.content }}</button>
          </li>
        </ul>

        <p>영상을 공유하고 싶다면 링크를 달아주세요</p>
        <input type="text" style="width: 540px; margin-bottom: 2px;" v-model="urlLink"/>
        
        <button
          class="btn btn--back btn--login"
        >업로드</button>

=======
        <h3>이미지</h3>
          <div style="height: 230px; border: 1px solid lightgray;">
            <img :src="imageUrl" style="width: 100%; max-height: 230px;">
          </div>
          <div style="width: 100%; height: 15px;">
            <input type="file" ref="articleimg" name="articleimg" v-on:change='onFileSelected()' style="width: 100%; border: 0px; padding-left: 0px;">
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
        <input type="text" style="width: 100%; margin-bottom: 2px; height: 40px;" v-model="urlLink"/>
        
        <button
          v-on:click="onUpload"
          class="btn btn--back btn--login"
          style="height: 40px; padding-top: 0px;"
        >업로드</button>
        <BottomNav/>
>>>>>>> 99c531beb08191081b9e0d4b884081f89b200a65
    </div>
</template>

<script>
<<<<<<< HEAD
// Import Vue FilePond
import vueFilePond from 'vue-filepond'

// Import FilePond styles
import 'filepond/dist/filepond.min.css'

// Import FilePond plugins
// Please note that you need to install these plugins separately

// Import image preview plugin styles
import 'filepond-plugin-image-preview/dist/filepond-plugin-image-preview.min.css'

// Import image preview and file type validation plugins
import FilePondPluginFileValidateType from 'filepond-plugin-file-validate-type'
import FilePondPluginImagePreview from 'filepond-plugin-image-preview'
import axios from 'axios'

// Create component
const FilePond = vueFilePond(FilePondPluginFileValidateType, FilePondPluginImagePreview)

var localhost_url = "http://127.0.0.1:8080";

export default {
  name: 'Write',
  data: function() {
    return { 
      myFiles: [],
      myText: "",
      urlLink: "",
      tagList: [
        {
          id: 1,
          content: '일상',
          isSelected: false,
        },
        {
          id: 2,
          content: '음식',
          isSelected: false,
        },
        {
          id: 3,
          content: '코미디',
          isSelected: false,
        },
        {
          id: 4,
          content: '동물',
          isSelected: false,
        },
        {
          id: 5,
          content: '음악',
          isSelected: false,
        },
        {
          id: 6,
          content: '스포츠',
          isSelected: false,
        },
        {
          id: 7,
          content: '패션',
          isSelected: false,
        },
        {
          id: 8,
          content: '뷰티',
          isSelected: false,
        },
        {
          id: 9,
          content: '게임',
          isSelected: false,
        },
      ],
      selectedTags: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      count: 0,
      favTags: [],
      btnFunc: {backgroundColor: "gray"}
    }
  },
  methods: {
    handleFilePondInit: function() {
      console.log('FilePond has initialized');
        // FilePond instance methods are available on `this.$refs.pond`
    },
    
  },
  components: {
    FilePond
  }
};
</script>

<style scoped>

=======
import axios from 'axios'
import store from '@/vuex/store'
import { mapState, mapActions } from "vuex"
import WriteList from '@/components/user/WriteList'
import WriteInput from '@/components/user/WriteInput'
import { bus } from '@/event-bus'
import { base } from "@/components/common/BaseURL.vue"; // baseURL
import BottomNav from "@/components/common/BottomNav"

const localhost_url = "http://localhost:8080/tugether/articlewrite"

export default {
  name: 'Write',
  components: {
    WriteList,
    WriteInput,
    BottomNav,
    // TextareaComponent,
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
      if (this.myText.length > 300) {
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

<style scoped>
.tag {
  margin: 3px;
}

 
>>>>>>> 99c531beb08191081b9e0d4b884081f89b200a65
</style>