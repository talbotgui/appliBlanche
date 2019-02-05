<template>
  <div id="app">
    <!-- Menu -->
    <Menu></Menu>
    <div id="nav">
      <router-link to="/">Accueil</router-link>|
      <router-link to="/about">infos</router-link>
    </div>

    <!-- Routeur -->
    <router-view/>
  </div>
</template>

<!-- Balises pour charger le ts -->
<script lang="ts" src="./App.ts"/>

<!-- Balise pour charger le CSS ("scoped" pour limiter le CSS à ce composant)-->
<style scoped lang="scss" src="./App.scss"/>
