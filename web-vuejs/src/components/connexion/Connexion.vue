<template>
  <div class="container-fluid">

    <div class="erreur">{{ msgErreur }}</div>

    <h2>Connexion</h2>

    <form v-on:submit.prevent="connexion">
      <div class="col-lg-5 col-md-6 col-xs-12">
        <span>Identifiant</span>
        <input id="login" v-model.trim="login" autocomplete="username login" />
      </div>
      <div class="col-lg-5 col-md-6 col-xs-12">
        <span>Mot de passe</span>
        <input type="password" id="mdp" v-model="mdp" autocomplete="current-password" />
      </div>
      <div class="col-lg-2 col-md-12 col-xs-12">
        <button class="btn btn-outline-primary" v-on:click="connexion">Connexion</button>
      </div>
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
      console.debug('connexion')
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
