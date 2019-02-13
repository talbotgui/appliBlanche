<template>
  <v-container fluid grid-list-xl>
    <v-layout wrap align-center>
      <h3>Administration des ressources</h3>
      <table>
        <thead>
          <tr>
            <th>Clef</th>
            <th>Description</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="r in page.content" v-bind:key="r.clef">
            <td>{{r.clef}}</td>
            <td>{{r.description}}</td>
          </tr>
        </tbody>
      </table>
      <v-flex xs4 sm4 d-flex>
        <v-select v-bind="page.pageSize" :items="listeNbElementsParPage" label="Elements par page"></v-select>
      </v-flex>
      <v-flex xs4 sm4 d-flex>
        <v-select v-bind="page.pageNumber" :items="listeIndexesDePage" label="Numéro de page"></v-select>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script lang="ts" src="./page-administration-ressources.ts"/>
<style scoped lang="scss" src="./page-administration-ressources.scss"/>
