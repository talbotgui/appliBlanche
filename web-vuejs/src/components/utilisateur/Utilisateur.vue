<template>
  <div class="container-fluid">

    <div class="erreur">{{ messageErreur }}</div>

    <h2>Administration des utilisateurs</h2>

    <div v-for="utilisateur in utilisateurs" :key="utilisateur.login">
      <span>{{ utilisateur.login }}</span>
    </div>

  </div>
</template>

<script>
export default {
  name: 'Utilisateur',
  data () {
    return {
      messageErreur: '',
      utilisateurs: []
    }
  },
  mounted: function () {
    this.rechercher()
  },
  methods: {
    rechercher (event) {
      this.$http.get('http://localhost:9090/applicationBlanche/utilisateur').then(response => {
        this.utilisateurs = response.body
      }, response => {
        this.messageErreur = response.body
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
