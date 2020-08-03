<template>
  <div>
    <TagList @checked="onChecked" :tagList="tagList" />
    <button class="btn-bottom" @click="submitFavTag" :style="btnFunc">시작하기</button>
  </div>
</template>

<script>
import axios from 'axios'
import TagList from '@/components/user/TagList'
import store from '@/vuex/store'
import { mapState, mapActions } from "vuex";

const SERVER_URL = 'http://localhost:8080'

export default {
  name: 'TagView',
  components: {
    TagList,
  },
  data() {
    return {
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
      btnFunc: {backgroundColor: "gray"},
      nickName: "",
    }
  },
  computed:{
    ...mapState(["token"]), //store 공동 저장소에 있는 token 사용하기 위해 선언.
    ...mapActions(["getToken"]),
  },
  methods: {
    onChecked(tag) {
      tag.isSelected = !tag.isSelected
      if (this.count > 2) {
        tag.isSelected = !tag.isSelected
        if (tag.isSelected) {
          this.selectedTags[tag.id] = 0
          tag.isSelected = !tag.isSelected
          this.count -= 1
        } else {
          alert('최대 3개까지 선택 가능합니다.')
        }
      } else {
          if (tag.isSelected) {
            // this.selectedTags = [...this.selectedTags, tag]
            // this.selectedTags.push(tag.content)
            // this.selectedTags.splice(tag.id, 0, tag.content)
            this.selectedTags[tag.id] = tag.content
            this.count += 1
        } else {
            // this.selectedTags.pop(tag.content)
            // var index = this.selectedTags.findIndex(function(item) {
            //   return item.id === tag
            //   console.log(item)
            // })
            // this.selectedTags.splice(index, 1)
            this.selectedTags[tag.id] = 0
            this.count -= 1
          }
        if (this.count !== 0 && this.count < 4) {
          this.btnFunc.backgroundColor = "red"
        } else {
          this.btnFunc.backgroundColor = "gray"
        }  
      }
      // const parseselectedTags = JSON.parse(JSON.stringify(this.selectedTags))
      // console.log(parseselectedTags)
    },

    
    submitFavTag() {
      let i =0;
      let parsefavTags;
      for (i=1; i<10; i++) {
        if(this.selectedTags[i] !== 0) {
          this.favTags.push(i)
          // parsefavTags = JSON.parse(JSON.stringify(this.favTags))

          
        }
      }
      console.log("보낼때 데이터 뭐야??")
      console.log(this.favTags)
      console.log("이거토큰맞지??? "+ this.$store.state.token)
     
      axios.post(`${SERVER_URL}/tugether/favtag`, 
      {
        taglist: this.favTags,
      },
      {
          headers:{
            "jwt-auth-token": this.$store.state.token
          }
      }
      )
      .then((response) => {
        console.log("크하하 결과다!!!"+ response.data.status)
        this.$router.push('/mainfeed')

      })
      .catch(error => {
        console.log(error.response)
        
      })
    }  
  }
}
</script>

<style>
  .btn-bottom {
    width: calc(100% - 40px);
    background: #000;
    color: #fff;
    height: 50px;
    text-align: center;
    line-height: 50px;
    position: fixed;
    left: 20px;
    bottom: 17px;
    cursor: pointer;
  }
  .btn-bottom:hover {
    text-decoration: none;
    background-color: red;
  }
</style>