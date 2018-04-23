<template>
  <div class="container-fluid">

    <!-- Petit menu -->
    <div class="col-lg-1 col-md-1 col-xs-1">
      <div class="bienvenue">Bonjour {{utilisateur.login}}</div>

      <h3>Accueil</h3>
      <router-link to="/administration/utilisateur">Utilisateurs</router-link>
      <router-link to="/administration/role">Roles</router-link>
    </div>

    <!--Cadre dans lequel s'affiche la vue sélectionnée -->
    <router-view/>

  </div>
</template>

<script>
import rest from '../services/rest'

export default {
  name: 'Accueil',
  data () {
    return {
      get utilisateur () {
        return rest.utilisateur
      }
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
