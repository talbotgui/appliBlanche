<template>
  <div class="container-fluid">

    <div class="erreur col-12">{{ msgErreur }}</div>

    <h2>Connexion</h2>

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

  </div>
</template>

<script>
export default {
  name: 'Connexion',
  data () {
    return {
      msgErreur: null,
      login: '',
      mdp: ''
    }
  },
  methods: {
    connexion (event) {
      this.msgErreur = ''

      const options = {
        url: 'http://localhost:9090/applicationBlanche/v1/utilisateur/moi',
        method: 'GET',
        headers: { Authorization: 'Basic ' + btoa(this.login + ':' + this.mdp) }
      }
      this.$http(options)
        .then(response => {
          console.debug(response.data)
        }, errorResponse => {
          this.msgErreur = 'Erreur de connexion'
        })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.erreur {
  color: red;
  background-color: yellow;
}
</style>
