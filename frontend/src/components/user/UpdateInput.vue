<template>
  <div>

    <input type="text" v-model="content" @keypress.enter="addTag">
    <button @click="addTag" style="margin-left: 10px; padding: 0px 10px 0px 10px; height: 50px; background: black; color: white; border: 0px solid skyblue;">추가</button>
  </div>
</template>

<script>
export default {
  name: 'UpdateInput',
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
        this.$emit('add-tag', tag.content)
        this.content = ''
      }
    },
  }
}
</script>

<style>

</style>
