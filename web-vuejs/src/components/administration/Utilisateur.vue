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
        <button class="btn btn-outline-primary" v-on:click="creerUtilisateur">Créer l'utilisateur</button>
      </div>
    </div>

    <!-- Administration des roles -->
    <div class="col-lg-8 col-md-6 col-xs-12">
      <h2>Administration des roles</h2>

      <!-- Liste des roles -->
      <div v-for="role in roles" :key="role.nom">
        <span>{{ role.nom }}</span>
      </div>

      <!-- Création d'un role -->
      <div class="col-lg-12 col-md-12 col-xs-12">
        <input id="nouveauRole" v-model.trim="nouveauRole.nom" placeholder="nom" />
        <button class="btn btn-outline-primary" v-on:click="creerRole">Créer le role</button>
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
      nouveauRole: { nom: '' },
      utilisateurs: [],
      roles: []
    }
  },

  // Au chargement de la vue, recherche des données
  mounted: function () {
    this.rechercherLesDonnees()
  },

  methods: {

    creerUtilisateur (event) {
      rest.postUtilisateur(this.nouvelUtilisateur, response => { this.nouvelUtilisateur = { login: '', mdp: '' }; this.rechercherLesDonnees() })
    },

    creerRole (event) {
      rest.postRole(this.nouveauRole, response => { this.nouveauRole = { nom: '' }; this.rechercherLesDonnees() })
    },

    rechercherLesDonnees (event) {
      rest.getUtilisateurs(response => { this.utilisateurs = response.body })
      rest.getRoles(response => { this.roles = response.body })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
