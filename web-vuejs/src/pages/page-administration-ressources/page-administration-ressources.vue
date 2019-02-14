<template>
  <v-layout wrap align-center>
    <v-flex xs12 d-flex>
      <h3>Administration des ressources</h3>
    </v-flex>
    <v-flex xs12 d-flex>
      <table>
        <thead><tr><th>Clef</th><th>Description</th></tr></thead>
        <tbody>
          <tr v-for="r in page.content" v-bind:key="r.clef">
            <td>{{r.clef}}</td>
            <td>{{r.description}}</td>
          </tr>
        </tbody>
      </table>
    </v-flex>
    <v-flex xs4 sm2 d-flex>
      <v-select v-model="page.size" :items="listeNbElementsParPage" v-on:change="selectionnerNbElements" label="Elements par page"></v-select>
    </v-flex>
    <v-flex xs4 sm2 d-flex>
      <v-select v-model="page.number" :items="listeIndexesDePage" v-on:change="selectionnerIndexPage" label="Numéro de page"></v-select>
    </v-flex>
  </v-layout>
</template>

<script lang="ts" src="./page-administration-ressources.ts"/>
<style scoped lang="scss" src="./page-administration-ressources.scss"/>
