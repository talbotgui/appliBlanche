import Vue from 'vue'

export default {

  data: {
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
      reponseSucces => { this.utilisateur = reponseSucces.data; sucessCallback(reponseSucces) },
      erreurCallback
    )
  },

  connecter (login, mdp, sucessCallback, erreurCallback = this.erreurCallbackParDefaut) {
    // Initialisation des paramètres globaux REST (le login est la première méthode rest appelée)
    Vue.http.options.root = 'http://localhost:9090/applicationBlanche/'
    // Vue.http.options.emulateJSON = true

    const options = { method: 'POST', url: 'login', body: { login: login, mdp: mdp } }
    Vue.http(options).then(
      reponseSucces => {
        // Lecture du token, sauvegarde et utilisation dans les entêtes de requête
        const token = reponseSucces.headers.map.authorization[0]
        localStorage.setItem('JWT', token)
        Vue.http.headers.common['Authorization'] = token

        // Chargement des informations personnelles
        this.getUtilisateurMoi(sucessCallback)
      },
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
    return !!localStorage.getItem('JWT')
  }

}
