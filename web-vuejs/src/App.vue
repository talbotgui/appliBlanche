<template>
  <div id="app">
    {{loading}}
    <router-view v-if="!loading" />
  </div>
</template>

<script>

export default {
  name: 'App',

  data () {
    return { loading: 'Chargement en cours' }
  },

  mounted () {
    this.loading = ''
  }
}
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
.entete {
  text-align: center;
}
</style>
