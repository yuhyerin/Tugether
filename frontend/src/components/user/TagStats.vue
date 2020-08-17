<script>
//Importing Bar class from the vue-chartjs wrapper
import {Doughnut} from 'vue-chartjs'
import axios from 'axios'
import { base } from "@/components/common/BaseURL.vue"
//Exporting this so it can be used in other components
export default {
  extends: Doughnut,
  data() {
    return {
      datacollection: {
        //Data to be represented on x-axis
        labels: ['A', 'B', 'C', 'D', 'E'],
        datasets: [
          {
            label: '태그 횟수',
            backgroundColor: ['rgba(0, 0, 255, 1)', 'rgba(0, 0, 255, 0.8)', 'rgba(0, 0, 255, 0.6)', 'rgba(0, 0, 255, 0.4)', 'rgba(0, 0, 255, 0.2)'],
            pointBackgroundColor: 'white',
            borderWidth: 1,
            pointBorderColor: '#249EBF',
            //Data to be represented on y-axis
            data: [9, 7, 5, 3, 1]
          }
        ]
      },
      //Chart.js options that controls the appearance of the chart
      options: {
        scales: {
          yAxes: [{
            ticks: {
              beginAtZero: false
            },
            gridLines: {
              display: false
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
//     axios.get(base + '???', {
//       headers: {
//         "jwt-auth-token": localStorage.getItem("token")
//       }
//     })
//     .then(response => {
//       console.log(response.data)
//     })
//     .catch(error => {
//       console.log(error)
//     })
//   }
}
</script>