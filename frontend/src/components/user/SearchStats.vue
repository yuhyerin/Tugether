<script>
//Importing Bar class from the vue-chartjs wrapper
import { Line } from 'vue-chartjs'
import axios from 'axios'
import { base } from "@/components/common/BaseURL.vue"
//Exporting this so it can be used in other components
export default {
  extends: Line,
  data() {
    return {
      datacollection: {
        //Data to be represented on x-axis
        labels: ['1', '2', '3', '4', '5'],
        datasets: [
          {
            label: '검색 횟수',
            backgroundColor: 'transparent',
            pointBackgroundColor: 'black',
            borderWidth: 2,
            pointBorderColor: 'black',
            //Data to be represented on y-axis
            data: [50, 40, 30, 20, 10]
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
  // created() {
  //   axios.get(base + '???', {
  //     headers: {
  //       "jwt-auth-token": localStorage.getItem("token")
  //     }
  //   })
  //   .then(response => {
  //     console.log(response.data)
  //   })
  //   .catch(error => {
  //     console.log(error)
  //   })
  // }
}
</script>