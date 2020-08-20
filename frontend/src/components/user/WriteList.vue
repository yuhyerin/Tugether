<template>
  <div>
    <!-- <h1>관심태그 목록</h1> -->
    <ul style="padding-top: 0px;">
      <!-- 목록을 보여줄 예정 -->
      <span v-for="(tag, index) in tagList" :key="tag.id">
        <span>{{ tag.content }}</span>
        <!-- <v-chip close style="color: white; background-color: red;" @click="$emit('delete', tag, index)">#{{ tag.content }}</v-chip> -->
        <!-- <v-chip v-if="chip1" class="ma-2"
      close
      @click:close="chip1 = false"
    >
      Closable
    </v-chip> -->
        <button @click="$emit('delete', tag, index)" style="padding: 0px 5px 0px 5px; margin: 3px 10px 3px 10px; height: 20px; background: red; color: white;">X</button>
      </span>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'WriteList',
  props: {
    tagList: {
      type: Array,
      required: true,
    },
  },
}
</script>

<style>
.completed {
  text-decoration: line-through;
}
</style>
