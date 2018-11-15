-- Page de connexion
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'connexion_titre', 'Connexion', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'connexion_identifiant', 'Nom d''utilisateur :', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'connexion_motDePasse', 'Mot de passe :', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'connexion_boutonConnexion', 'Connexion', 'fr' from LIBELLE;
-- Page d'accueil
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'accueil_message', 'Bienvenue dans l''application blanche avec ', 'fr' from LIBELLE;
-- Composant de menu
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_accueil', 'Accueil', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_titre_administration', 'Administration', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_utilisateur', 'Utilisateurs', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_role', 'Rôles', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_titre_reservation', 'Réservations', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_reservations', 'Planning', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_adminreservations', 'Chambres & offres', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_titre_consommation', 'Consommations', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_consommation', 'Main courante', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_adminconsommation', 'Produits', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_deconnexion', 'Déconnexion', 'fr' from LIBELLE;
-- Page de gestion des utilisateurs
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_titre', 'Administration des utilisateurs', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_entete_identifiant', 'Identifiant', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_entete_roles', 'Rôles', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_placeholder_login', 'nom d''utilisateur', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_placeholder_login_validation', 'Le nom d''utilisateur est obligatoire', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_placeholder_login_validation2', 'Le nom d''utilisateur doit faire 6 caractères au minimum', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_placeholder_mdp', 'mot de passe', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_placeholder_mdp_validation', 'Le mot de passe est obligatoire', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_placeholder_mdp_validation2', 'Le mot de passe doit faire 6 caractères au minimum', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_formulaire_titre', 'Ajouter/modifier un utilisateur', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_bouton_creer', 'Sauvegarder cet utilisateur', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_bouton_annuler', 'Annuler', 'fr' from LIBELLE;
-- Page de gestion des rôles
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'role_titre', 'Administration des rôles', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'role_entete_nom', 'Nom', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'role_placeholder_nom', 'nom', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'role_formulaire_titre', 'Ajouter/modifier un rôle', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'role_bouton_creer', 'Sauvegarder ce rôle', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'role_bouton_annuler', 'Annuler', 'fr' from LIBELLE;
-- Libellés communs à plusieurs pages 
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_entete_actions', 'Actions', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_tooltip_flagFr', 'Passer en Français', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_tooltip_flagEn', 'Switch to English', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_tooltip_ajouter', 'Ajouter un élément', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_tooltip_editer', 'Editer l''élément', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_tooltip_supprimer', 'Supprimer l''élément', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_tooltip_rechercher', 'Rechercher', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_tooltip_annuler', 'Annuler', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_tooltip_valider', 'Valider', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'calendrier_tooltip_plus','Déplacer d''une semaine dans l''avenir', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'calendrier_tooltip_moins','Déplacer d''une semaine dans le passé', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'calendrier_tooltip_plusPlus','Déplacer d''un mois dans l''avenir', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'calendrier_tooltip_moinsMoins','Déplacer d''un mois dans le passé', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'calendrier_tooltip_dateParDefaut','Déplacer à aujourd''hui', 'fr' from LIBELLE;
-- Messages d'erreur génériques
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'erreur_connexion', 'Paramètres de connexion incorrects', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'erreur_aucuneConnexionInternet', 'Aucune connexion Internet disponible', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'erreur_securiteParNavigateur', 'Erreur de sécurité détectée par le navigateur. Etes-vous connecté ?', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'erreur_securite', 'Erreur de sécurité lors d''un appel à l''API. Tentez de vous reconnecter', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'erreur_apiNonDisponible', 'API down', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'erreur_http', 'Erreur HTTP', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'erreur_pgm', 'Erreur de programmation', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_LOGIN', 'Erreur de connexion', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_LOGIN_MDP', 'Identifiant et/ou mot de passe trop court ({{0}} caractères minimum)', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_LOGIN_VEROUILLE', 'Erreur de connexion - le compte est verrouillé', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_ROLE_NOM', 'Nom du role trop court ({{0}} caractères minimum)', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_SHA', 'Erreur de cryptage', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_OBJET_DEJA_EXISTANT', 'Objet de type {{0}} avec l''identifiant {{1}} déjà existant', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_OBJET_NON_EXISTANT', 'Objet de type {{0}} et de référence {{1}} inexistant', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_REFERENCE_NON_VALIDE', 'Référence {{0}} invalide', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_OBJET_FONCTIONNELEMENT_EN_DOUBLE', 'L''objet de type ''{{0}}'' existe déjà avec l''attribut ''{{1}}'' et la valeur ''{{2}}''', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_PARAMETRE_MANQUANT', 'Le paramètre ''{{0}}'' est obligatoire.', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_DATES_INCOHERENTES', 'Les parametres ''{{0}}'' et ''{{1}}'' ne sont pas cohérents.', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'RESERVATION_DEJA_EXISTANTE', 'Une reservation existe déjà à ces dates', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'SUPPRESSION_IMPOSSIBLE_OBJETS_LIES', 'Suppression impossible car un ou plusieurs objets de type ''{{0}}'' sont liés.', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'TRANSITION_ETAT_IMPOSSIBLE', 'Impossible de passer de l''état ''{{0}}'' à l''état ''{{1}}''.', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'RESERVATION_DATES_INCOHERENTES', 'Les dates de début et fin de cette réservation ne sont pas cohérentes.', 'fr' from LIBELLE;
-- Page de réservation
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservation_titre', 'Détails de la réservation ', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservation_placeholder_dateDebut', 'Date de début', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservation_placeholder_dateFin', 'Date de fin', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservation_placeholder_client', 'Nom du client', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservation_placeholder_chambre', 'Chambre', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservation_placeholder_formule', 'Formule', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservation_bouton_enregistrer', 'Enregistrer', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservation_bouton_arriveeClient', 'Arrivée du client', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservation_bouton_departClient', 'Départ du client', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservations_titre_calendrier','Tableau des réservations', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservations_header_date','Date', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservations_form_du','Du', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservations_form_au','Au', 'fr' from LIBELLE;
-- Page d'administration des consommations
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'adminConso_titre_listeDesProduits','Liste des produits', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'adminConso_placeholder_nomProduit','Nom du produit', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'adminConso_placeholder_couleurProduit','Couleur', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'adminConso_placeholder_prix','Prix', 'fr' from LIBELLE;
-- Page d'administration des réservations
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'adminResa_titre_listeDesChambres','Liste des chambres', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'adminResa_titre_listeDesFormules','Liste des formules', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'adminResa_titre_listeDesOptions','Liste des options', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'adminResa_placeholder_prix','Prix', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'adminResa_placeholder_nomOption','Nom de l''option', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'adminResa_placeholder_nomFormule','Nom de la formule', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'adminResa_placeholder_prixParNuit','Prix par nuit', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'adminResa_placeholder_prixParPersonne','Prix par personne', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'adminResa_placeholder_nomChambre','Nom de la chambre', 'fr' from LIBELLE;
-- Page des consommations
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'consommations_titre_maincourante','Main courante', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'consommations_titre_aucuneReservationEnCours','Aucune réservation en cours', 'fr' from LIBELLE;
