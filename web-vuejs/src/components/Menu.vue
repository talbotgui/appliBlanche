<template>
  <div>
    <div id="menu" class="row">
      <nav mat-tab-nav-bar>
        <router-link to="/accueil">{{ $t("menu_accueil") }}</router-link>
        <router-link to="/administration/utilisateur">{{ $t("menu_utilisateur") }}</router-link>
        <router-link to="/administration/role">{{ $t("menu_role") }}</router-link>
        <a href="#" v-on:click.prevent="deconnecter">{{ $t("menu_deconnexion") }}</a>
      </nav>
    </div>
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
#menu {
  background-color: #fff;
  border: 0;
  margin-bottom: 50px;
}
#menu {
  border-top: 5px solid #d10000;
}
#menu a:link,
a:visited {
  border-radius: 0 0 8px 8px;
  padding: 0px 15px;
}
#menu ul {
  margin: 0;
  margin-left: 20px;
  padding: 0;
}
#menu li {
  text-transform: uppercase;
}
#menu a,
#menu router-link {
  float: left;
  font-weight: bold;
  color: #000;
  margin: 0 5px 0 0;
  background-color: #eee;
}
#menu a:hover {
  background-color: #d10000;
  color: #fff;
}
</style>
