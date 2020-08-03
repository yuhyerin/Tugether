<template>
    <div class="wrapC" style="height: 800px">
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

    </div>
</template>

<script>
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

</style>