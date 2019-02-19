<template>
  <div>
    <h2 v-t="'connexion_titre'"></h2>
    <input type="text" name="login" v-model="login" :placeholder="$t('connexion_identifiant')">
    <input type="password" autocomplete="current-password" name="password" v-model="mdp" :placeholder="$t('connexion_motDePasse')">
    <v-btn @click="connecter()" v-t="'connexion_boutonConnexion'"></v-btn>
  </div>
</template>

<script lang="ts" src="./page-login.ts"/>
<style scoped lang="scss" src="./page-login.scss"/>
