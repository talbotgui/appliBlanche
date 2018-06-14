<template>
  <div class="container-fluid">

    <!--Menu-->
    <m-menu />

    <!-- Administration des roles -->
    <div class="row">
      <h3>{{ $t("role_titre") }}</h3>

      <!-- Liste des roles -->
      <table class="table table-striped table-hover">
        <thead>
          <tr>
            <th scope="col">{{ $t("role_entete_nom") }}</th>
            <th scope="col">
              <span>{{ $t("commun_entete_actions") }}</span>
              <em class="fa fa-plus" v-on:click="afficherFormulaireCreation"></em>
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="role in roles" :key="role.nom">
            <th scope="row">{{ role.nom }}</th>
          </tr>
        </tbody>
      </table>

      <!-- Création d'un role -->
      <div v-if="nouveauRole">

        <!-- Titre -->
        <hr/>
        <h3>{{ $t("role_formulaire_titre") }}</h3>

        <!-- Validation de surface -->
        <div v-if="erreurs.length">
          <div class="alert-danger" v-for="erreur in erreurs" :key="erreur">{{ erreur }}</div>
        </div>

        <!-- Champs -->
        <input id="nouveauRole" v-model.trim="nouveauRole.nom" v-bind:placeholder='$t("role_placeholder_nom")' />

        <!-- Boutons -->
        <button class="btn btn-outline-secondary" v-on:click="masquerFormulaireCreation">{{ $t("role_bouton_annuler") }}</button>
        <button class="btn btn-outline-secondary" v-on:click="creerRole">{{ $t("role_bouton_creer") }}</button>
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
      roles: [],
      erreurs: []
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
      if (this.nouveauRole) {
        // Validation de formulaire
        this.erreurs = []
        if (!this.nouveauRole.nom) {
          this.erreurs.push('Le nom de role est obligatoire')
        } else if (this.nouveauRole.nom.length < 3) {
          this.erreurs.push('Le nom de role doit faire 3 caractères au minimum')
        }

        // Appel au service
        if (this.erreurs.length === 0) {
          rest.postRole(this.nouveauRole, response => { this.nouveauRole = { nom: '' }; this.rechercherLesDonnees() })
        }
      }
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
