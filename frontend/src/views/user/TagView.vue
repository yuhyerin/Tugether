<template>
  <div>
    <div class="welcome" style="text-align:left">
      <strong style="font-size: 30px;">관심태그 선택</strong>
      <p style="color: gray">{{ $store.state.nickname }}님, 태그를 선택하고</p>
      <p style="color: gray">맞춤화된 피드 추천을 받으세요.({{this.count}}/3)</p>
    </div>
    <TagList @checked="onChecked" :tagList="tagList" />
    <!--관심태그를 하나도 선택하지 않았을 경우 다음 페이지로 넘어갈 수 없음-->
    <button v-show="count==0" class="btn-bottom" @click="msg" :style="btnFunc">시작하기</button>
    <button v-show="count!=0" class="btn-bottom" @click="submitFavTag" :style="btnFunc">시작하기</button>
  </div>
</template>

<script>
import axios from 'axios'
import TagList from '@/components/user/TagList'
import store from '@/vuex/store'
import { mapState, mapActions } from "vuex";
import { base } from "@/components/common/BaseURL.vue"
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
          content: '⌚ 일상',
          isSelected: false,
        },
        {
          id: 2,
          content: '🍔 음식',
          isSelected: false,
        },
        {
          id: 3,
          content: '😆 코미디',
          isSelected: false,
        },
        {
          id: 4,
          content: '🐱 동물',
          isSelected: false,
        },
        {
          id: 5,
          content: '🎹 음악',
          isSelected: false,
        },
        {
          id: 6,
          content: '🎳 스포츠',
          isSelected: false,
        },
        {
          id: 7,
          content: '👕 패션',
          isSelected: false,
        },
        {
          id: 8,
          content: '💄 뷰티',
          isSelected: false,
        },
        {
          id: 9,
          content: '🎮 게임',
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
    msg(){
      alert("관심태그를 선택해주세요!");
    },
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
            this.selectedTags[tag.id] = tag.content
            this.count += 1
        } else {
            this.selectedTags[tag.id] = 0
            this.count -= 1
          }
        if (this.count !== 0 && this.count < 4) {
          this.btnFunc.backgroundColor = "red"
        } else {
          this.btnFunc.backgroundColor = "gray"
        }  
      }
    },

    
    submitFavTag() {
      let i =0;
      let parsefavTags;
      for (i=1; i<10; i++) {
        if(this.selectedTags[i] !== 0) {
          this.favTags.push(i)    
        }
      }
     
      axios.post(base+'/tugether/favtag', 
      {
        taglist: this.favTags,
      },
      {
          headers:{
            "jwt-auth-token": localStorage.getItem("token")
          }
      }
      )
      .then((response) => {
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
  .welcome {
    padding: 30px 0 0 30px;
  }
</style>