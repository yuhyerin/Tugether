<template>
  <div>
    <h1>Like</h1>
    <ul>
      <li v-for="article in articles" :key="article.id">
        <p class="photo" @click="$emit('checked', photo)">{{ article }}</p>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'Like',
  props: {
    articles: {
      type: Array,
    },
    articles_id: {
      type: Array,
    }
  },
}
</script>

<style>

</style>