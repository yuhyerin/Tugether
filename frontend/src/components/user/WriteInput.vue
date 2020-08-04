<template>
  <div>
    <input type="text" v-model="content" @keypress.enter="addTodo">
    <button @click="addTodo">관심태그 등록</button>
  </div>
</template>

<script>
export default {
  name: 'WriteInput',
  data() {
    return {
      content: '',
    }
  },
  methods: {
    addTodo() {
      if (this.content.trim()) {
        const todo = {
          id: Date.now(),
          content: this.content,
        //   isCompleted: false,
        }
        this.$emit('add-todo', todo)
        this.content = ''
      }
    },
  }
}
</script>

<style>

</style>
