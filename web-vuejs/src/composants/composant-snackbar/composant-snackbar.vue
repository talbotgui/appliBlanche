<template>
  <div>
    <v-snackbar
      v-model="flagAfficherSnackbar"
      :color="color"
      :multi-line="flagMultiline"
      :timeout="timeout"
    >
      <span v-if="!!message">{{ message.message }}</span>
      <v-btn dark flat @click="flagAfficherSnackbar = false">Close</v-btn>
    </v-snackbar>
  </div>
</template>

<script lang="ts" src="./composant-snackbar.ts"/>
<style scoped lang="scss" src="./composant-snackbar.scss"/>
