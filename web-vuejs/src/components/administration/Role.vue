<template>
  <div class="container-fluid">

    <!--Menu-->
    <m-menu />

    <!-- Administration des roles -->
    <div class="row">
      <h3>Administration des roles</h3>

      <!-- Liste des roles -->
      <table class="table table-striped table-hover">
        <thead>
          <tr>
            <th scope="col">Nom</th>
            <th scope="col">
              <span>Actions</span>
              <em class="fa fa-plus" v-on:click="afficherFormulaireCreation"></em>
            </th>
          </tr>
        </thead>
        <tr v-for="role in roles" :key="role.nom">
          <th scope="row">{{ role.nom }}</th>
        </tr>
      </table>

      <!-- Création d'un role -->
      <div v-if="nouveauRole">

        <!-- Titre -->
        <hr/>
        <h3>Ajouter/modifier un utilisateur</h3>

        <!-- Validation de surface -->
        <div>???validation de surface???</div>

        <!-- Champs -->
        <input id="nouveauRole" v-model.trim="nouveauRole.nom" placeholder="nom" />

        <!-- Boutons -->
        <button class="btn btn-outline-secondary" v-on:click="masquerFormulaireCreation">Annuler</button>
        <button class="btn btn-outline-secondary" v-on:click="creerRole">Créer le role</button>
      </div>
    </div>
  </div>
</template>

<script>
import rest from '../../services/rest'

export default {
  name: 'Role',
  data () {
    return {
      nouveauRole: undefined,
      roles: []
    }
  },

  // Au chargement de la vue, recherche des données
  mounted: function () {
    this.rechercherLesDonnees()
  },

  methods: {

    afficherFormulaireCreation (event) {
      this.nouveauRole = { nom: '' }
    },

    masquerFormulaireCreation (event) {
      this.nouveauRole = undefined
    },

    creerRole (event) {
      rest.postRole(this.nouveauRole, response => { this.nouveauRole = { nom: '' }; this.rechercherLesDonnees() })
    },

    rechercherLesDonnees (event) {
      rest.getRoles(response => { this.roles = response.body })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
