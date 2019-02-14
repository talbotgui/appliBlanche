<template>
  <v-container fluid grid-list-xl>
    <v-layout wrap align-center>
      <v-flex d-flex v-for="m in modules" :key="m.nom">
        <v-menu offset-y>
            <v-btn @click="moduleSelectionne=m" slot="activator">
              <em :class="'iconeBouton fa fa-' + m.icone"></em>
              <span class="libelleBouton">{{ m.nom }}</span>
              </v-btn>
            <v-list>
              <v-list-tile v-for="p in moduleSelectionne.pages" :key="p.nom">
                <v-list-tile-title>
                  <router-link :to="p.route">
                    <em :class="'iconeBouton fa fa-' + p.icone"></em>
                    <span>{{ p.nom }}</span></router-link>
                </v-list-tile-title>
              </v-list-tile>
            </v-list>
        </v-menu>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script lang="ts" src="./composant-menu.ts"/>
<style scoped lang="scss" src="./composant-menu.scss"/>
