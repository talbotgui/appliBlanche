<template>
  <div class="container-fluid">

    <div class="erreur col-12">{{ msgErreur }}</div>

    <h2>{{ $t("connexion_titre") }}</h2>

    <div class="form-group">
      <label for="login">{{ $t("connexion_identifiant") }}</label>
      <input class="form-control" id="login" v-model.trim="login" autocomplete="username login" />
    </div>

    <div class="form-group">
      <label for="mdp">{{ $t("connexion_motDePasse") }}</label>
      <input class="form-control" type="password" id="mdp" v-model="mdp" autocomplete="current-password" />
    </div>

    <button class="btn btn-outline-secondary" v-on:click="connexion">{{ $t("connexion_boutonConnexion") }}</button>

  </div>
</template>

<script>
import rest from '../../services/rest'
import router from '../../router/router'

export default {
  name: 'Connexion',

  data () {
    return {
      msgErreur: null,
      login: 'adminAsupprimer',
      mdp: 'adminAsupprimer'
    }
  },

  methods: {

    connexion (event) {
      // Reset du message d'erreur
      this.msgErreur = ''

      // Recherche des informations de l'utilisateur (pour sauvegarde)
      rest.connecter(this.login, this.mdp,
        // si c'est ok, on change de page
        response => {
          const urlDemandee = this.$route.query.redirect
          if (urlDemandee) {
            router.push(urlDemandee)
          } else {
            router.push('/accueil')
          }
        },

        // Sinon, message d'erreur
        errorResponse => { this.msgErreur = 'Erreur de connexion' }
      )
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
