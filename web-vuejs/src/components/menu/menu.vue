<template>
  <div>
    <v-menu offset-y>
      <v-btn slot="activator">Dropdown</v-btn>
      <v-list>
        <v-list-tile v-for="(item, index) in items" :key="index">
          <v-list-tile-title>
            <router-link :to="item.to">{{ item.title }}</router-link>
          </v-list-tile-title>
        </v-list-tile>
      </v-list>
    </v-menu>
    <v-btn @click="cliquerSurMenu">ClicMoi</v-btn>
  </div>
</template>

<script lang="ts" src="./menu.ts"/>
<style scoped lang="scss" src="./menu.scss"/>
