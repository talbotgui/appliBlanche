<template>
  <div class="container-fluid">

    <div class="erreur">{{ messageErreur }}</div>

    <div class="row">

      <!-- Administration des utilisateurs -->
      <div class="col-lg-4 col-md-6 col-xs-12">
        <h2>Administration des utilisateurs</h2>

        <!-- Liste des utilisateurs-->
        <div>
          <div v-for="utilisateur in utilisateurs" :key="utilisateur.login">
            <span>{{ utilisateur.login }}</span>
          </div>
        </div>

        <!-- Création d'un utilisateur -->
        <div class="col-lg-12 col-md-12 col-xs-12">
          <input id="login" v-model.trim="nouvelUtilisateur.login" placeholder="nom d'utilisateur" />
          <input id="mdp" v-model.trim="nouvelUtilisateur.mdp" placeholder="mot de passe" />
          <button class="btn btn-outline-primary" v-on:click="creerUtilisateur">Créer l'utilisateur</button>
        </div>
      </div>

      <!-- Administration des roles -->
      <div class="col-lg-8 col-md-6 col-xs-12">
        <h2>Administration des roles</h2>

        <!-- Liste des roles -->
        <div v-for="role in roles" :key="role.nom">
          <span>{{ role.nom }}</span>
        </div>

        <!-- Création d'un role -->
        <div class="col-lg-12 col-md-12 col-xs-12">
          <input id="nouveauRole" v-model.trim="nouveauRole.nom" placeholder="nom" />
          <button class="btn btn-outline-primary" v-on:click="creerRole">Créer le role</button>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: 'Administration',
  data () {
    return {
      messageErreur: '',
      nouvelUtilisateur: { login: '', mdp: '' },
      nouveauRole: { nom: '' },
      utilisateurs: [],
      roles: []
    }
  },
  mounted: function () {
    this.rechercherLesDonnees()
  },
  methods: {
    creerUtilisateur (event) {
      this.messageErreur = ''
      this.$http.post('http://localhost:9090/applicationBlanche/utilisateur', this.nouvelUtilisateur, { emulateJSON: true }).then(
        response => {
          this.nouvelUtilisateur = { login: '', mdp: '' }
          this.rechercherLesDonnees()
        },
        response => { this.messageErreur += '\n' + (response.body && response.body.message !== '') ? response.body.message : response.bodyText }
      )
    },
    creerRole (event) {
      this.messageErreur = ''
      this.$http.post('http://localhost:9090/applicationBlanche/role', this.nouveauRole, { emulateJSON: true }).then(
        response => {
          this.nouveauRole = { nom: '' }
          this.rechercherLesDonnees()
        },
        this.traiterErreur
      )
    },
    rechercherLesDonnees (event) {
      this.$http.get('http://localhost:9090/applicationBlanche/utilisateur').then(
        response => { this.utilisateurs = response.body },
        this.traiterErreur
      )
      this.$http.get('http://localhost:9090/applicationBlanche/role').then(
        response => { this.roles = response.body },
        this.traiterErreur
      )
    },
    traiterErreur (response) {
      var messageAajouter = ''
      if (response) {
        if (response.body && response.body.message !== '') {
          messageAajouter = response.body.message
        } else if (response.bodyText) {
          messageAajouter = response.bodyText
        } else if (response.ok === false) {
          messageAajouter = 'L\'API ne répond pas'
        } else {
          console.error(response)
          messageAajouter = 'Une erreur inconnue est survenue'
        }
      } else {
        messageAajouter = 'Une erreur inconnue est survenue'
      }
      this.messageErreur = '\n' + messageAajouter
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
