import Vue from 'vue'

export default {

  data: {
    estConnecte: false,
    utilisateur: null
  },

  erreurCallbackParDefaut: reponse => {
    var message = ''
    if (reponse) {
      if (reponse.body && reponse.body.message !== '') {
        message = reponse.body.message
      } else if (reponse.bodyText) {
        message = reponse.bodyText
      } else if (reponse.ok === false) {
        message = 'L\'API ne répond pas'
      } else {
        console.error(reponse)
        message = 'Une erreur inconnue est survenue'
      }
    } else {
      message = 'Une erreur inconnue est survenue'
    }
    window.alert(message)
  },

  getRoles (sucessCallback, erreurCallback = this.erreurCallbackParDefaut) {
    const options = { method: 'GET', url: 'v1/roles' }
    Vue.http(options).then(sucessCallback, erreurCallback)
  },

  getUtilisateurs (sucessCallback, erreurCallback = this.erreurCallbackParDefaut) {
    const options = { method: 'GET', url: 'v1/utilisateurs' }
    Vue.http(options).then(sucessCallback, erreurCallback)
  },

  getUtilisateurMoi (sucessCallback, erreurCallback = this.erreurCallbackParDefaut) {
    const options = { method: 'GET', url: 'v1/utilisateurs/moi' }
    Vue.http(options).then(
      reponseSucces => { this.estConnecte = true; this.utilisateur = reponseSucces.data; sucessCallback(reponseSucces) },
      erreurCallback
    )
  },

  postRole (param, sucessCallback, erreurCallback = this.erreurCallbackParDefaut) {
    const options = { method: 'POST', url: 'v1/roles', body: param }
    Vue.http(options).then(sucessCallback, erreurCallback)
  },

  postUtilisateur (param, sucessCallback, erreurCallback = this.erreurCallbackParDefaut) {
    const options = { method: 'POST', url: 'v1/utilisateurs', body: param }
    Vue.http(options).then(sucessCallback, erreurCallback)
  },

  isUtilisateurConnecte () {
    return this.estConnecte
  },

  setHeaderSecurite (login, mdp) {
    Vue.http.options.root = 'http://localhost:9090/applicationBlanche/'
    Vue.http.headers.common['Authorization'] = 'Basic ' + btoa(login + ':' + mdp)
    Vue.http.options.emulateJSON = true
  }
}
