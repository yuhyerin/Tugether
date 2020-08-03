<template>
<div id="app">
  <v-app id="inspire">
    <v-row>
      <v-col cols="12" sm="6" offset-sm="3">
        <v-card height="50px">
          <v-card-title class="red white--text">
            <span class="headline">Tugether</span>
  
            <v-spacer></v-spacer>
  
            <v-menu bottom left>
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  dark
                  icon
                  v-bind="attrs"
                  v-on="on"
                >
                  <v-icon>mdi-dots-vertical</v-icon>
                </v-btn>
              </template>
  
              <v-list>
                <v-list-item
                  v-for="(item, i) in items"
                  :key="i"
                  @click="BtnClick"
                >
                  <v-list-item-title>{{ item.title }}</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </v-card-title>
  
        </v-card>
      </v-col>
    </v-row>
  </v-app>
</div>
</template>

<script>
export default {
    name: 'menu',
    data: () => ({
      items: [
        { title: 'Click Me' },
        { title: 'Click Me' },
        { title: 'Click Me' },
        { title: 'Click Me 2' },
      ],
    }),
    methods:{
        BtnClick(){
            console.log("버튼클릭했다!!");
        }
    }
}
</script>