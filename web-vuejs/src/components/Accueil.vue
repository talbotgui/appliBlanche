<template>
  <div class="container-fluid">
    <m-menu />
    <div>{{ $t("accueil_message") }}VueJS</div>
  </div>
</template>

<script>
import Vue from 'vue'
import Menu from '../components/Menu'

export default {
  name: 'Accueil',
  data () {
    return {

    }
  }
}

Vue.component('m-menu', Menu)
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
