-- Page de connexion
insert into LIBELLE (clef, libelle, langue) values ('connexion_titre', 'Connexion', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('connexion_identifiant', 'Nom d''utilisateur :', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('connexion_motDePasse', 'Mot de passe :', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('connexion_boutonConnexion', 'Connexion', 'fr');
-- Page d'accueil
insert into LIBELLE (clef, libelle, langue) values ('accueil_message', 'Bienvenue dans l''application blanche avec ', 'fr');
-- Composant de menu
insert into LIBELLE (clef, libelle, langue) values ('menu_accueil', 'Accueil', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('menu_titre_administration', 'Administration', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('menu_utilisateur', 'Utilisateurs', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('menu_role', 'Rôles', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('menu_ressource', 'Ressources', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('menu_titre_reservation', 'Réservations', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('menu_reservations', 'Planning', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('menu_adminreservations', 'Chambres & offres', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('menu_titre_consommation', 'Consommations', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('menu_consommation', 'Main courante', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('menu_adminconsommation', 'Produits', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('menu_deconnexion', 'Déconnexion', 'fr');
-- Page de gestion des utilisateurs
insert into LIBELLE (clef, libelle, langue) values ('utilisateur_titre', 'Administration des utilisateurs', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('utilisateur_entete_identifiant', 'Identifiant', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('utilisateur_entete_roles', 'Rôles', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('utilisateur_placeholder_login', 'nom d''utilisateur', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('utilisateur_placeholder_login_validation', 'Le nom d''utilisateur est obligatoire', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('utilisateur_placeholder_login_validation2', 'Le nom d''utilisateur doit faire 6 caractères au minimum', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('utilisateur_placeholder_mdp', 'mot de passe', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('utilisateur_placeholder_mdp_validation', 'Le mot de passe est obligatoire', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('utilisateur_placeholder_mdp_validation2', 'Le mot de passe doit faire 6 caractères au minimum', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('utilisateur_formulaire_titre', 'Ajouter/modifier un utilisateur', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('utilisateur_bouton_creer', 'Sauvegarder cet utilisateur', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('utilisateur_bouton_annuler', 'Annuler', 'fr');
-- Page de gestion des rôles
insert into LIBELLE (clef, libelle, langue) values ('role_titre', 'Administration des rôles', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('role_entete_nom', 'Nom', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('role_placeholder_nom', 'nom', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('role_formulaire_titre', 'Ajouter/modifier un rôle', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('role_bouton_creer', 'Sauvegarder ce rôle', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('role_bouton_annuler', 'Annuler', 'fr');
-- Libellés communs à plusieurs pages 
insert into LIBELLE (clef, libelle, langue) values ('commun_entete_actions', 'Actions', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('commun_tooltip_flagFr', 'Passer en Français', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('commun_tooltip_flagEn', 'Switch to English', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('commun_tooltip_ajouter', 'Ajouter un élément', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('commun_tooltip_editer', 'Editer l''élément', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('commun_tooltip_supprimer', 'Supprimer l''élément', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('commun_tooltip_rechercher', 'Rechercher', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('commun_tooltip_annuler', 'Annuler', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('commun_tooltip_valider', 'Valider', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('calendrier_tooltip_plus','Déplacer d''une semaine dans l''avenir', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('calendrier_tooltip_moins','Déplacer d''une semaine dans le passé', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('calendrier_tooltip_plusPlus','Déplacer d''un mois dans l''avenir', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('calendrier_tooltip_moinsMoins','Déplacer d''un mois dans le passé', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('calendrier_tooltip_dateParDefaut','Déplacer à aujourd''hui', 'fr');
-- Messages d'erreur génériques
insert into LIBELLE (clef, libelle, langue) values ('erreur_connexion', 'Paramètres de connexion incorrects', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('erreur_aucuneConnexionInternet', 'Aucune connexion Internet disponible', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('erreur_securiteParNavigateur', 'Erreur de sécurité détectée par le navigateur. Etes-vous connecté ?', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('erreur_securite', 'Erreur de sécurité lors d''un appel à l''API. Tentez de vous reconnecter', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('erreur_apiNonDisponible', 'API down', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('erreur_http', 'Erreur HTTP', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('erreur_pgm', 'Erreur de programmation', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_DATES_INCOHERENTES', 'Les parametres ''{{0}}'' et ''{{1}}'' ne sont pas cohérents.', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_LOGIN', 'Erreur de connexion', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_LOGIN_MDP', 'Identifiant et/ou mot de passe trop court ({{0}} caractères minimum)', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_LOGIN_VEROUILLE', 'Erreur de connexion - le compte est verrouillé', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_OBJET_DEJA_EXISTANT', 'Objet de type {{0}} avec l''identifiant {{1}} déjà existant', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_OBJET_FONCTIONNELEMENT_EN_DOUBLE', 'L''objet de type ''{{0}}'' existe déjà avec l''attribut ''{{1}}'' et la valeur ''{{2}}''', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_OBJET_NON_EXISTANT', 'Objet de type {{0}} et de référence {{1}} inexistant', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_PARAMETRE_MANQUANT', 'Le paramètre ''{{0}}'' est obligatoire.', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_REFERENCE_NON_VALIDE', 'Référence {{0}} invalide', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_RESERVATION_DATES_INCOHERENTES', 'Les dates de début et fin de cette réservation ne sont pas cohérentes.', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_RESERVATION_DEJA_EXISTANTE', 'Une reservation existe déjà à ces dates', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_ROLE_NOM', 'Nom du role trop court ({{0}} caractères minimum)', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_SHA', 'Erreur de cryptage', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_SUPPRESSION_IMPOSSIBLE_OBJETS_LIES', 'Suppression impossible car un ou plusieurs objets de type ''{{0}}'' sont liés.', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_TRANSITION_ETAT_IMPOSSIBLE', 'Impossible de passer de l''état ''{{0}}'' à l''état ''{{1}}''.', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_RESERVATION_PAS_EN_COURS', 'La réservation ''{{0}}'' n''est pas en cours', 'fr');
-- Page de réservation
insert into LIBELLE (clef, libelle, langue) values ('reservation_titre', 'Détails de la réservation ', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('reservation_placeholder_dateDebut', 'Date de début', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('reservation_placeholder_dateFin', 'Date de fin', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('reservation_placeholder_client', 'Nom du client', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('reservation_placeholder_chambre', 'Chambre', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('reservation_placeholder_formule', 'Formule', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('reservation_bouton_enregistrer', 'Enregistrer', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('reservation_bouton_arriveeClient', 'Arrivée du client', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('reservation_bouton_departClient', 'Départ du client', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('reservations_titre_calendrier','Tableau des réservations', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('reservations_header_date','Date', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('reservations_form_du','Du', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('reservations_form_au','Au', 'fr');
-- Page d'administration des consommations
insert into LIBELLE (clef, libelle, langue) values ('adminConso_titre_listeDesProduits','Liste des produits', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('adminConso_placeholder_nomProduit','Nom du produit', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('adminConso_placeholder_couleurProduit','Couleur', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('adminConso_placeholder_prix','Prix', 'fr');
-- Page d'administration des réservations
insert into LIBELLE (clef, libelle, langue) values ('adminResa_titre_listeDesChambres','Liste des chambres', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('adminResa_titre_listeDesFormules','Liste des formules', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('adminResa_titre_listeDesOptions','Liste des options', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('adminResa_placeholder_prix','Prix', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('adminResa_placeholder_nomOption','Nom de l''option', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('adminResa_placeholder_nomFormule','Nom de la formule', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('adminResa_placeholder_prixParNuit','Prix par nuit', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('adminResa_placeholder_prixParPersonne','Prix par personne', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('adminResa_placeholder_nomChambre','Nom de la chambre', 'fr');
-- Page des consommations
insert into LIBELLE (clef, libelle, langue) values ('consommations_titre_maincourante','Main courante', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('consommations_titre_aucuneReservationEnCours','Aucune réservation en cours', 'fr');
-- Page de gestion des ressources
insert into LIBELLE (clef, libelle, langue) values ('ressource_titre', 'Administration des ressources', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('ressource_entete_clef', 'Clef', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('ressource_entete_description', 'description', 'fr');
