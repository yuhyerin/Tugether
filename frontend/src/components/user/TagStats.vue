<script>
//Importing Bar class from the vue-chartjs wrapper
import { Bar } from 'vue-chartjs'
import axios from 'axios'
import { base } from "@/components/common/BaseURL.vue"
//Exporting this so it can be used in other components
export default {
  extends: Bar,
  data() {
    return {
      datacollection: {
        //Data to be represented on x-axis
        labels: [],
        datasets: [
          {
            label: '태그 횟수',
            backgroundColor: ['rgba(255,0,0,1)', 'rgba(255,0,0,0.8)', 'rgba(255,0,0,0.6)', 'rgba(255,0,0,0.4)', 'rgba(255,0,0,0.2)'],
            pointBackgroundColor: 'black',
            borderWidth: 1,
            pointBorderColor: '#249EBF',
            //Data to be represented on y-axis
            data: []
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
  mounted() {
    //renderChart function renders the chart with the datacollection and options object.
    this.renderChart(this.datacollection, this.options)
  },
created() {
    axios.get(base + 'tugether/articletagstats', {
      headers: {
        "jwt-auth-token": localStorage.getItem("token")
      }
    })
    .then(response => {
      console.log(response.data.toparticletags)
      this.datacollection.labels = [
        response.data.toparticletags[0].tag_name,
        response.data.toparticletags[1].tag_name,
        response.data.toparticletags[2].tag_name,
        response.data.toparticletags[3].tag_name,
        response.data.toparticletags[4].tag_name,
      ]
      this.datacollection.datatsets.data = [
        response.data.toparticletags[0].search_cnt,
        response.data.toparticletags[1].search_cnt,
        response.data.toparticletags[2].search_cnt,
        response.data.toparticletags[3].search_cnt,
        response.data.toparticletags[4].search_cnt,
      ]
    })
    .catch(error => {
      console.log(error)
    })
  }
}
</script>