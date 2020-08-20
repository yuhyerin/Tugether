<template>
  <div class="wrapC">
    <ul>
      <li v-for="tag in tagList" :key="tag.id" >
        <button class="tag" :checked="tag.isSelected" @click="$emit('checked', tag)" :class="{ selected: tag.isSelected }">{{ tag.content }}</button>
      </li>
    </ul>

  </div>
</template>

<script>
import store from "../../vuex/store"

export default {
  name: 'TagList',
  props: {
    tagList: {
      type: Array,
      required: true,
    },
  },
}
</script>

<style>
  .tag {
    width: 90px;
    border: none;
    color: black;
    padding: 15px 15px;
    text-align: center;
    text-decoration: none;
    margin: 20px 7px 15px 7px;
    cursor: pointer;
    border-radius: 20px;
    background-color: lightgray;
  } 
  .selected {
    background-color: red;
    color: white;
  }
  ul {
    list-style: none;
    text-align: center;
    display: table-cell;
    vertical-align: middle;
    padding-top: 70px;
  }
  li {
    display: inline-block;
  }
</style>