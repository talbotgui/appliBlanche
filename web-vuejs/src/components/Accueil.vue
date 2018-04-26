<template>
  <div class="container-fluid">

    <div class="col-lg-1 col-md-1 col-xs-1">
      <m-menu />
    </div>

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
