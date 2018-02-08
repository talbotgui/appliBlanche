<template>
  <div class="container-fluid">

    <!-- Petit menu pour la section Administration -->
    <div class="col-lg-1 col-md-1 col-xs-1">
      <h3>Administration</h3>
      <router-link to="/administration/utilisateur">Utilisateur</router-link>
    </div>

    <!--Cadre dans lequel s'affiche la vue sélectionnée -->
    <router-view/>

  </div>
</template>

<script>
export default {
  name: 'Administration',
  data () { return {} }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
