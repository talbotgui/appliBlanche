import Vue from 'vue'

export default {

  data: {
    estConnecte: false,
    utilisateur: null
  },

  getRoles (sucessCallback, erreurCallback) {
    const options = { method: 'GET', url: 'v1/role' }
    Vue.http(options).then(sucessCallback, erreurCallback)
  },

  getUtilisateurs (sucessCallback, erreurCallback) {
    const options = { method: 'GET', url: 'v1/utilisateur' }
    Vue.http(options).then(sucessCallback, erreurCallback)
  },

  getUtilisateurMoi (sucessCallback, erreurCallback) {
    const options = { method: 'GET', url: 'v1/utilisateur/moi' }
    Vue.http(options).then(
      reponseSucces => { this.estConnecte = true; this.utilisateur = reponseSucces.data; sucessCallback(reponseSucces) },
      erreurCallback
    )
  },

  postRole (param, sucessCallback, erreurCallback) {
    const options = { method: 'POST', url: 'v1/role', body: param }
    Vue.http(options).then(sucessCallback, erreurCallback)
  },

  postUtilisateur (param, sucessCallback, erreurCallback) {
    const options = { method: 'POST', url: 'v1/utilisateur', body: param }
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
