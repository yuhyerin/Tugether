<template>
  <div>
    <ul>
      <li v-for="(com, idx) in comments" :key="idx">
        <p>{{ com }}
        <!-- 수정은 아직 로직 없음 -->
        <button @click="$emit('edit-comment', com, idx)">수정</button>
        <button @click="$emit('delete-comment', com, idx)">삭제</button>
        </p>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'CommentList',
  props: {
    comments: {
      type: Array,
      required: true
    }
  }
}
</script>

<style>

</style>