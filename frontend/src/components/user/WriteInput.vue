<template>
  <div>
    <input type="text" v-model="content" @keypress.enter="addTag" style="width: 85%; float: left; border: 1px solid gray;">
    <button @click="addTag" style="width: 13%; float: right; margin-left: 0px; padding: 0px 10px 0px 10px; height: 50px; background: black; color: white; border: 0px solid skyblue;">추가</button>
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
    addTag() {
      if (this.content.trim()) {
        const tag = {
          id: Date.now(),
          content: this.content,
        }
        this.$emit('add-tag', tag)
        this.content = ''
      }
    },
  }
}
</script>

<style>

</style>
