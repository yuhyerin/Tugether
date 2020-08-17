<template>
    <div class="wrapB">
      <br>
      <!-- <div class="column">
        <h3>Line Chart</h3>
        <line-chart></line-chart> -->
      <div>
        <h3>인기검색</h3>
        <!--Bar Chart example-->
        <bar-chart></bar-chart>
        <!-- 위의 코드가 있어야 해당 컴포넌트가 보이지만 아래 코드를 넣지 않으면 vue 문법상 에러가 나서 그냥 넣음 -->
        <SearchStats />
      </div>
      <br>  
      
      <div>
        <h3>인기태그</h3>
        <!--Bar Chart example-->
        <bar-chart></bar-chart>
        <TagStats />
      </div>
      <br>

      <div>
        <h3>인기피드</h3>
        <!--Bar Chart example-->
        <bar-chart></bar-chart>
        <FeedStats />
      </div>
    </div>
</template>
 
<script>
  // import LineChart from '@/components/LineChart'
import SearchStats from '@/components/user/SearchStats'
import TagStats from '@/components/user/TagStats'
import FeedStats from '@/components/user/FeedStats'

export default {
  name: 'Stats',
  components: {
    // LineChart,
    SearchStats,
    TagStats,
    FeedStats
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