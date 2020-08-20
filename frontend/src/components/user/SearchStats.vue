<script>
//Importing Bar class from the vue-chartjs wrapper
import VueCharts from 'vue-chartjs'
import { Line } from 'vue-chartjs'
import { base } from "@/components/common/BaseURL.vue"
import axios from 'axios'
//Exporting this so it can be used in other components
export default {
  extends: Line,
  props: {
    searchLabels: {
      type: Array
    },
    searchData: {
      type: Array
    }
  },
  data() {
    return {
      datacollection: {
        //Data to be represented on x-axis
        labels: this.searchLabels,
        datasets: [
          {
            label: '태그 횟수',
            // backgroundColor: ['rgba(0,0,0,1)', 'rgba(0,0,0,0.8)', 'rgba(0,0,0,0.6)', 'rgba(0,0,0,0.4)', 'rgba(0,0,0,0.2)'],
            backgroundColor: 'transparent',
            pointBackgroundColor: 'black',
            borderWidth: 1,
            pointBorderColor: 'black',
            //Data to be represented on y-axis
            data: this.searchData
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
}
</script>