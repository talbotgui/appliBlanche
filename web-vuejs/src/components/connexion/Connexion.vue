<template>
  <div class="hello">

    <div class="erreur">{{ msgErreur }}</div>

    <h2>Connexion</h2>
    <form v-on:submit.prevent="connexion">
      <label for="login">Identifiant</label><input id="login" v-model.trim="login" />
      <label for="mdp">Mot de passe</label><input type="password" id="mdp" v-model="mdp" />
      <button v-on:click="connexion">Connexion</button>
    </form>
  </div>
</template>

<script>
export default {
  name: 'Connexion',
  data () {
    return {
      msgErreur: 'oups',
      login: '',
      mdp: ''
    }
  },
  methods: {
    connexion (event) {
      this.$http.get('http://localhost:9090/applicationBlanche/utilisateur').then(response => {
        console.debug(response)
      }, response => {
        console.error(response)
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
