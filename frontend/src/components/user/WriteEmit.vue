<template>
  <div>
    <h1>관심태그 목록</h1>
    <ul>
      <!-- 목록을 보여줄 예정 -->
      <li v-for="(todo, index) in todoList" :key="todo.id">
        <!-- <input
          type="checkbox"
          :checked="todo.isCompleted"
          @click="$emit('checked', todo)"
          style="left: 470px; height: 22px;"
        > -->
        <!-- <button @remove="todoList.splice(index, 1)">Remove</button> -->
        <p :class="{ completed: todo.isCompleted }" >{{ todo.content }}<button type="button" class="btn btn-primary" @click="$emit('delete', todo, index)" style="margin-left: 10px;">X</button></p>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'WriteEmit',
  props: {
    todoList: {
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
