<template>
  <div>
    <Menu></Menu>

    <v-content>
      <router-view/>
    </v-content>
  </div>
</template>

<!-- Balises pour charger le ts -->
<script lang="ts" src="./App.ts"/>

<!-- Balise pour charger le CSS ("scoped" pour limiter le CSS à ce composant)-->
<style scoped lang="scss" src="./App.scss"/>
