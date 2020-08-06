<template>
  <div>
    <!-- <h2>관심태그</h2> -->
    <input type="text" v-model="content" @keypress.enter="addTag">
    <button @click="addTag" style="margin-left: 10px; padding: 0px 10px 0px 10px; height: 50px; background: black; color: white; border: 0px solid skyblue;">추가</button>
    <!-- <div class="row">
      <div class="col-5">
        <input type="text" v-model="content" @keypress.enter="addTag">
      </div>
      <div class="col-1">
        <button @click="addTag" type="button" class="btn btn-dark" style="padding-top: 0px; padding-bottom: 0px; height: 50px;">추가</button>
      </div>
    </div> -->
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
        //   isCompleted: false,
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
