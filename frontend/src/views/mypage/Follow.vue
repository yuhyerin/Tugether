<template>
    <div class="wrapB" style="text-align: center;">
        <br><br>
        <strong @click="moveMypage" style="cursor: pointer;">마이페이지로 이동</strong>
        <!--tab test-->
        <div class="tabs" style="margin-top: 30px;">
            <TabItem
                v-for="item in list"
                v-bind="item" :key="item.id"
                v-model="currentId"/>
            </div>
            <div class="contents" style="margin: 0 auto; text-align: center;">
            <!-- <transition> -->
                <section class="item" :key="currentId">
                {{ current.content }}
                </section>
            <!-- </transition> -->
            <BottomNav/>
        </div>
    </div>
</template>

<script>
import axios from "axios";
import { base } from "@/components/common/BaseURL.vue"; // baseURL
import TabItem from '@/components/common/TabItem.vue'
import BottomNav from "@/components/common/BottomNav"

export default {
    components: { 
        TabItem,
        BottomNav,
     },
    data: () => {
        return {
            currentId: 1,
            list: [
                { id: 1, label: '팔로잉', content: '콘텐츠1' },
                { id: 2, label: '팔로워', content: '콘텐츠2' },
            ]
        }        
    },
    methods: {
        // 페이지 이동
        moveMypage() {
            this.$router.push("/mypage/Mypage");
        }
    },
    computed: {
        current() {
            return this.list.find(el => el.id === this.currentId) || {}
        }
    }
}
</script>

<style scoped>
.contents {
  position: relative;
  overflow: hidden;
  width: 280px;
  /* border: 2px solid #000; */
}
.item {
  box-sizing: border-box;
  padding: 10px;
  width: 100%;
  transition: all 0.8s ease;
}
/* 트랜지션 전용 스타일 */
.v-leave-active {
  position: absolute;
}
.v-enter {
  transform: translateX(-100%);
}
.v-leave-to {
  transform: translateX(100%);
}
</style>