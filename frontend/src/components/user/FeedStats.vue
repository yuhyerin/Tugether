<script>
//Importing Bar class from the vue-chartjs wrapper
import {Bar} from 'vue-chartjs'
import axios from 'axios'
import { base } from "@/components/common/BaseURL.vue"
//Exporting this so it can be used in other components
export default {
  extends: Bar,
  data() {
    return {
      datacollection: {
        //Data to be represented on x-axis
        labels: ['1', '2', '3', '4', '5'],
        datasets: [
          {
            label: '좋아요 횟수',
            backgroundColor: ['rgba(255,0,0,1)', 'rgba(255,0,0,0.8)', 'rgba(255,0,0,0.6)', 'rgba(255,0,0,0.4)', 'rgba(255,0,0,0.2)'],
            pointBackgroundColor: 'black',
            borderWidth: 1,
            pointBorderColor: '#249EBF',
            //Data to be represented on y-axis
            data: [100, 80, 60, 40, 20]
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
//   created() {
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