<template>
  <v-card
    class="mx-auto"
    max-width="550"
    tile
  >
    <v-list rounded>
      <v-list-item-group color="primary">
        <v-list-item
          v-for="(item, i) in topsearchtags"
          :key="i"
        >
          <v-list-item-icon>
            <v-badge
              color="pink"
              dot
              style="margin-right: 10px;"
            >
              {{ i + 1 }}위
            </v-badge>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title v-text="item.tag_name"></v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list-item-group>
    </v-list>
  </v-card>
</template>

<script>
import axios from 'axios'
import { base } from "@/components/common/BaseURL.vue"

export default {
  name: 'SearchStats',
  data() {
    return {
      topsearchtags: [],
    }
  },
  created() {
    axios.get(base + '/tugether/searchstats', {
      headers: {
        "jwt-auth-token": localStorage.getItem("token")
      }
    })
    .then(response => {
      console.log(response.data.topsearchtags)
      this.topsearchtags = response.data.topsearchtags
    })
    .catch(error => {
      console.log(error)
    })
  }
}
</script>

<style>

</style>