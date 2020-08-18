<script>
//Importing Bar class from the vue-chartjs wrapper
import VueCharts from 'vue-chartjs'
import { Bar } from 'vue-chartjs'
import { base } from "@/components/common/BaseURL.vue"
import axios from 'axios'
//Exporting this so it can be used in other components
export default {
  extends: Bar,
  props: {
    labels: {
      type: Array
    },
    data: {
      type: Array
    }
  },
  data() {
    return {
      datacollection: {
        //Data to be represented on x-axis
        labels: this.labels,
        datasets: [
          {
            label: '태그 횟수',
            backgroundColor: ['rgba(255,0,0,1)', 'rgba(255,0,0,0.8)', 'rgba(255,0,0,0.6)', 'rgba(255,0,0,0.4)', 'rgba(255,0,0,0.2)'],
            pointBackgroundColor: 'black',
            borderWidth: 1,
            pointBorderColor: 'black',
            //Data to be represented on y-axis
            data: this.data
          }
        ]
      },
      //Chart.js options that controls the appearance of the chart
      options: {
        scales: {
          yAxes: [{
            ticks: {
              beginAtZero: true
            },
            gridLines: {
              display: true
            }
          }],
          xAxes: [ {
            gridLines: {
              display: false
            }
          }]
        },
        legend: {
            display: true
          },
        responsive: true,
        maintainAspectRatio: false
      }
    }
  },
  //renderChart function renders the chart with the datacollection and options object.
  mounted() {
    this.renderChart(this.datacollection, this.options)
  },
  // created() {
  //   axios.get(base + '/tugether/articletagstats', {
  //     headers: {
  //       "jwt-auth-token": localStorage.getItem("token")
  //     }
  //   })
  //   .then(response => {
  //     console.log(response.data.toparticletags)
  //     console.log(response.data.toparticletags[0].tag_name)
  //     console.log(response.data.toparticletags[0].article_cnt)
  //     this.datacollection.labels = [
  //       response.data.toparticletags[0].tag_name,
  //       response.data.toparticletags[1].tag_name,
  //       response.data.toparticletags[2].tag_name,
  //       response.data.toparticletags[3].tag_name,
  //       response.data.toparticletags[4].tag_name,
  //     ]
  //     this.datacollection.datasets.data = [
  //       response.data.toparticletags[0].article_cnt,
  //       response.data.toparticletags[1].article_cnt,
  //       response.data.toparticletags[2].article_cnt,
  //       response.data.toparticletags[3].article_cnt,
  //       response.data.toparticletags[4].article_cnt,
  //     ]
  //   })
  //   .catch(error => {
  //     console.log(error)
  //   })
  // }
}
</script>