<template>
  <div class="wrapB" style="margin-bottom: 50px;">
    <br>
      <div>
        <h3>인기검색</h3>
        <SearchStats v-if="searchLoaded" :searchLabels="searchLabels" :searchData="searchData"/>
      </div>
      <br>
      
      <div>
        <h3>인기태그</h3>
        <TagStats v-if="loaded" :labels="labels" :data="data"/>
      </div>
      <br>

      <div>
        <h3>인기피드</h3>
        <FeedStats />
      </div>
    <BottomNav />
  </div>
</template>

<script>
import axios from 'axios'
import { Bar } from 'vue-chartjs'
import { base } from "@/components/common/BaseURL.vue"
import SearchStats from '@/components/user/SearchStats'
import TagStats from '@/components/user/TagStats'
import FeedStats from '@/components/user/FeedStats'
import BottomNav from "@/components/common/BottomNav"

export default {
  name: 'Stats',
  components: {
    // LineChart,
    SearchStats,
    TagStats,
    FeedStats,
    BottomNav,
  },
  data() {
    return {
      loaded: false,
      searchLoaded: false,
      labels: [],
      data: [],
    }
  },
  created() {
    axios.get(base + '/tugether/articletagstats', {
      headers: {
        "jwt-auth-token": localStorage.getItem("token")
      }
    })
    .then(response => {
      this.loaded = true
      console.log(response.data.toparticletags)
      console.log(response.data.toparticletags[0].tag_name)
      console.log(response.data.toparticletags[0].article_cnt)
      this.labels = [
        response.data.toparticletags[0].tag_name,
        response.data.toparticletags[1].tag_name,
        response.data.toparticletags[2].tag_name,
        response.data.toparticletags[3].tag_name,
        response.data.toparticletags[4].tag_name,
      ]
      this.data = [
        response.data.toparticletags[0].article_cnt,
        response.data.toparticletags[1].article_cnt,
        response.data.toparticletags[2].article_cnt,
        response.data.toparticletags[3].article_cnt,
        response.data.toparticletags[4].article_cnt,
      ]

    })
    .catch(error => {
      console.log(error)
    });

    axios.get(base + '/tugether/searchstats', {
      headers: {
        "jwt-auth-token": localStorage.getItem("token")
      }
      })
      .then(response => {
        this.searchLoaded = true
        console.log(response.data.topsearchtags)
        this.topsearchtags = response.data.topsearchtags
        this.searchLabels = [
        response.data.topsearchtags[0].tag_name,
        response.data.topsearchtags[1].tag_name,
        response.data.topsearchtags[2].tag_name,
        response.data.topsearchtags[3].tag_name,
        response.data.topsearchtags[4].tag_name,
      ]
      this.searchData = [
        response.data.topsearchtags[0].article_cnt,
        response.data.topsearchtags[1].article_cnt,
        response.data.topsearchtags[2].article_cnt,
        response.data.topsearchtags[3].article_cnt,
        response.data.topsearchtags[4].article_cnt,
      ]
      })
      .catch(error => {
        console.log(error)
      })
  }
 
  }
</script>
 
<style scoped>
  ul {
    list-style-type: none;
    padding: 0;
  }
 
  li {
    display: inline-block;
    margin: 0 10px;
  }
 
  a {
    color: #42b983;
  }
</style>