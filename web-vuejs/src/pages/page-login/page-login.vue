<template>
  <div>
    {{ $t('message.login') }}
    <input type="text" name="login" v-model="login">
    {{ $t('message.password') }}
    <input type="text" name="password" v-model="mdp">
    <v-btn @click="connecter()">{{ $t('message.doLogin') }}</v-btn>
  </div>
</template>

<script lang="ts" src="./page-login.ts"/>
<style scoped lang="scss" src="./page-login.scss"/>
