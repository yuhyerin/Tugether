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
const SERVER_URL = 'http://localhost:8080'

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
  /* .welcome {
    text-align: center;
    font-family: Arial, Helvetica;
    font-weight: bold;
    margin-top: 17px;
  } */
  /* .tag {
    width: 100px;
    border: none;
    color: black;
    padding: 15px 15px;
    text-align: center;
    text-decoration: none;
    font-size: 15px;
    margin: 20px 15px 15px 15px;
    cursor: pointer;
    border-radius: 20px;
    background-color: lightgray;
  } */
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