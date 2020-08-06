<template>
  <div>
    <!-- <h1>관심태그 목록</h1> -->
    <ul>
      <!-- 목록을 보여줄 예정 -->
      <li v-for="(tag, index) in tagList" :key="tag.id">
        <!-- <input
          type="checkbox"
          :checked="todo.isCompleted"
          @click="$emit('checked', todo)"
          style="left: 470px; height: 22px;"
        > -->
        <!-- <button @remove="todoList.splice(index, 1)">Remove</button> -->
        <!-- <span :class="{ completed: tag.isCompleted }">{{ tag.content }}</span> -->
        <span>{{ tag.content }}</span>
        <button @click="$emit('delete', tag, index)" style="padding: 0px 5px 0px 5px; margin: 3px 10px 3px 10px; height: 20px; background: red; color: white;">X</button>
      </li>
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
