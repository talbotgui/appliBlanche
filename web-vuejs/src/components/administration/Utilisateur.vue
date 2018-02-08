<template>
  <div class="row">

    <!-- Administration des utilisateurs -->
    <div class="col-lg-4 col-md-6 col-xs-12">
      <h2>Administration des utilisateurs</h2>

      <!-- Liste des utilisateurs-->
      <div>
        <div v-for="utilisateur in utilisateurs" :key="utilisateur.login">
          <span>{{ utilisateur.login }}</span>
        </div>
      </div>

      <!-- Création d'un utilisateur -->
      <div class="col-lg-12 col-md-12 col-xs-12">
        <input id="login" v-model.trim="nouvelUtilisateur.login" placeholder="nom d'utilisateur" />
        <input id="mdp" v-model.trim="nouvelUtilisateur.mdp" placeholder="mot de passe" />
        <button class="btn btn-outline-primary" v-on:click="creer">Créer l'utilisateur</button>
      </div>
    </div>
  </div>
</template>

<script>
import rest from '../../services/rest'

export default {
  name: 'Utilisateur',
  data () {
    return {
      nouvelUtilisateur: { login: '', mdp: '' },
      utilisateurs: []
    }
  },

  // Au chargement de la vue, recherche des données
  mounted: function () {
    this.rechercherLesDonnees()
  },

  methods: {

    creer (event) {
      rest.postUtilisateur(this.nouvelUtilisateur, response => { this.nouvelUtilisateur = { login: '', mdp: '' }; this.rechercherLesDonnees() })
    },

    rechercherLesDonnees (event) {
      rest.getUtilisateurs(response => { this.utilisateurs = response.body })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
