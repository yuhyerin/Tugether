<template>
  <div class="wrapB" style="margin-bottom: 50px;">
    <br>
      <div>
        <h3>인기검색</h3>
        <SearchStats />
      </div>
      <br>
      <hr>
      
      <div>
        <h3>인기태그</h3>
        <TagStats />
      </div>
      <br>
      <hr>

      <div>
        <h3>인기피드</h3>
        <FeedStats />
      </div>

    <BottomNav />
  </div>
</template>

<script>
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
    BottomNav
  },
 
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