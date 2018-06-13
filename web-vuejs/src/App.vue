<template>
  <div id="app">
    {{loading}}
    <router-view v-if="!loading" />
  </div>
</template>

<script>
import Vue from 'vue'

export default {
  name: 'App',

  data () {
    return { loading: 'Chargement en cours' }
  },

  mounted () {
    // Initialisation des paramètres globaux REST (le login est la première méthode rest appelée)
    Vue.http.options.root = 'http://localhost:9090/applicationBlanche/'
    Vue.http.options.emulateJSON = true

    // Après un rechargement de la page, si un token JWT est disponible, on le met en place dans les entêtes
    Vue.http.headers.common['Authorization'] = localStorage.getItem('JWT')

    // Chargement des libelles venant de l'API
    const locale = 'fr'
    const cb = (err, message) => {
      if (err) {
        console.error(err)
        return
      }
      this.$i18n.setLocaleMessage('fr', message)
      this.loading = ''
    }
    return fetch('http://localhost:9090/applicationBlanche/i18n/' + locale, { method: 'get', headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' } })
      .then((res) => { return res.json() })
      .then((json) => {
        if (Object.keys(json).length === 0) {
          return Promise.reject(new Error('locale empty !!'))
        } else {
          return Promise.resolve(json)
        }
      })
      .then((message) => { cb(null, message) })
      .catch((error) => { cb(error) })
  }
}
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
.entete {
  text-align: center;
}
</style>
