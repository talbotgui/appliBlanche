<template>
  <div>
    <div class="bienvenue">Bonjour</div>

    <h3>Accueil</h3>
    <router-link to="/administration/utilisateur">Utilisateurs</router-link>
    <router-link to="/administration/role">Roles</router-link>
    <a v-on:click.prevent="deconnecter">Déconnexion</a>
  </div>
</template>

<script>
import rest from '../services/rest'
import router from '../router/router'

export default {
  name: 'Menu',
  data () {
    return {
    }
  },
  methods: {
    deconnecter () {
      console.debug('coucou')
      rest.deconnecter()
      router.push('/login')
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
