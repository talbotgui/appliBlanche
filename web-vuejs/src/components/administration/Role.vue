<template>
  <div class="row">

    <!--Menu-->
    <div class="col-lg-1 col-md-1 col-xs-1">
      <m-menu />
    </div>

    <!-- Administration des roles -->
    <div class="col-lg-11 col-md-11 col-xs-11">
      <h2>Administration des roles</h2>

      <!-- Liste des roles -->
      <div v-for="role in roles" :key="role.nom">
        <span>{{ role.nom }}</span>
      </div>

      <!-- Création d'un role -->
      <div class="col-lg-12 col-md-12 col-xs-12">
        <input id="nouveauRole" v-model.trim="nouveauRole.nom" placeholder="nom" />
        <button class="btn btn-outline-primary" v-on:click="creer">Créer le role</button>
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
      nouveauRole: { nom: '' },
      roles: []
    }
  },

  // Au chargement de la vue, recherche des données
  mounted: function () {
    this.rechercherLesDonnees()
  },

  methods: {

    creer (event) {
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
