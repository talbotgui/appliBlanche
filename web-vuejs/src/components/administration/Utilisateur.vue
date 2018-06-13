<template>
  <div class="container-fluid">

    <!--Menu-->
    <m-menu />

    <!-- Administration des utilisateurs -->
    <div class="row">
      <h3>{{ $t("utilisateur_titre") }}</h3>

      <!-- Liste des utilisateurs-->
      <table class="table table-striped table-hover">
        <thead>
          <tr>
            <th scope="col">Identifiant</th>
            <th scope="col">Rôles</th>
            <th scope="col">
              <span>Actions</span>
              <em class="fa fa-plus" v-on:click="afficherFormulaireCreation"></em>
            </th>
          </tr>
        </thead>
        <tr v-for="utilisateur in utilisateurs" :key="utilisateur.login">
          <th scope="row">{{ utilisateur.login }}</th>
          <td>
            <span v-for="role in utilisateur.roles" :key="role.nom">{{role.nom}}</span>
          </td>
          <td>
            <em class="fa fa-edit" v-on:click="selectionnerUtilisateur(utilisateur)"></em>
          </td>
        </tr>
      </table>

      <!-- Création d'un utilisateur -->
      <div v-if="nouvelUtilisateur">

        <!-- Titre -->
        <hr/>
        <h3>Ajouter/modifier un utilisateur</h3>

        <!-- Validation de surface -->
        <div>???validation de surface???</div>

        <!-- Champs -->
        <input id="login" v-model.trim="nouvelUtilisateur.login" v-bind:placeholder='$t("utilisateur_placeholder_login")' required/>
        <input id="mdp" v-model.trim="nouvelUtilisateur.mdp" v-bind:placeholder='$t("utilisateur_placeholder_mdp")' required/>

        <!-- Boutons -->
        <button class="btn btn-outline-secondary" v-on:click="masquerFormulaireCreation">{{ $t("utilisateur_bouton_annuler") }}</button>
        <button class="btn btn-outline-secondary" v-on:click="creerUtilisateur">{{ $t("utilisateur_bouton_creer") }}</button>
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
      nouvelUtilisateur: undefined,
      utilisateurs: []
    }
  },

  // Au chargement de la vue, recherche des données
  mounted: function () {
    this.rechercherLesDonnees()
  },

  methods: {

    afficherFormulaireCreation (event) {
      this.nouvelUtilisateur = { login: '', mdp: '' }
    },

    masquerFormulaireCreation (event) {
      this.nouvelUtilisateur = undefined
    },

    selectionnerUtilisateur (utilisateurSelectionne) {
      this.nouvelUtilisateur = utilisateurSelectionne
    },

    creerUtilisateur (event) {
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
