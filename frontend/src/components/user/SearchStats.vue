<template>
  <v-card
    class="mx-auto"
    max-width="580"
    tile
    dark
  >
    <v-list rounded>
      <v-list-item-group v-model="item" color="primary">
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
import defaultProfile from "../../assets/images/profile_default.png"
import { base } from "@/components/common/BaseURL.vue"
import BottomNav from "@/components/common/BottomNav"

export default {
  name: 'SearchStats',
  data() {
    return {
      topsearchtags: [],
    }
  },
  created() {
    axios.get(base + 'tugether/searchstats', {
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