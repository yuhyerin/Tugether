<template>
  <div>
    <h1>000님, 환영합니다.</h1>
    <h1>관심태그를 선택해주세요</h1>
    <ul>
      <li v-for="tag in tagList" :key="tag.id">
        <button class="tag" :checked="tag.isSelected" @click="$emit('checked', tag)" :class="{ selected: tag.isSelected }"># {{ tag.content }}</button>
      </li>
    </ul>

  </div>
</template>

<script>
export default {
  name: 'TagList',
  props: {
    tagList: {
      type: Array,
      required: true,
    }
  },
}
</script>

<style>
  .selected {
    background-color: rgb(224, 84, 84);
  }
  .tag {
    width: 100px;
    border: none;
    color: black;
    padding: 15px 15px;
    text-align: center;
    text-decoration: none;
    font-size: 15px;
    margin: 40px;
    cursor: pointer;
    border-radius: 20px;
    background-color: lightgray;
  }
  /* .tag:hover {
    background-color: red;
  } */
  .selected {
    background-color: red;
    color: white;
  }
  ul {
    list-style: none;
  }
  li {
    display: inline;
  }
</style>