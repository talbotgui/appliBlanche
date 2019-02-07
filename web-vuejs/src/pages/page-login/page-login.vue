<template>
  <div>
    login :
    <input type="text" name="login" v-model="login">
    password :
    <input type="text" name="password" v-model="mdp">
    <v-btn @click="connecter()">Se connecter</v-btn>
  </div>
</template>

<script lang="ts" src="./page-login.ts"/>
<style scoped lang="scss" src="./page-login.scss"/>
