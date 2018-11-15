-- Page de connexion
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'connexion_titre', 'Login', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'connexion_identifiant', 'Username:', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'connexion_motDePasse', 'Password:', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'connexion_boutonConnexion', 'Log in', 'en' from LIBELLE;
-- Page d'accueil
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'accueil_message', 'Welcome in this application using ', 'en' from LIBELLE;
-- Composant de menu
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_accueil', 'Home', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_titre_administration', 'Admin', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_utilisateur', 'Users', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_role', 'Profiles', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_ressource', 'Resources', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_titre_reservation', 'Reservations', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_reservations', 'Planning', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_adminreservations', 'Rooms & formula', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_titre_consommation', 'Goods', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_consommation', 'Goods', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_adminconsommation', 'Products', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_deconnexion', 'Logout', 'en' from LIBELLE;
-- Page de gestion des utilisateurs
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_titre', 'Users management', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_entete_identifiant', 'Login', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_entete_roles', 'Profiles', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_placeholder_login', 'username', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_placeholder_login_validation', 'Username is required', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_placeholder_login_validation2', 'Username must be 6 characters at least', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_placeholder_mdp', 'password', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_placeholder_mdp_validation', 'Password is required', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_placeholder_mdp_validation2', 'Password must be 6 characters at least', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_formulaire_titre', 'Add or modify a user', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_bouton_creer', 'Save this user', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_bouton_annuler', 'Cancel', 'en' from LIBELLE;
-- Page de gestion des rôles
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'role_titre', 'Profiles management', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'role_entete_nom', 'Name', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'role_placeholder_nom', 'name', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'role_formulaire_titre', 'Add/modify a profile', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'role_bouton_creer', 'Save this profile', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'role_bouton_annuler', 'Cancel', 'en' from LIBELLE;
-- Libellés communs à plusieurs pages 
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_entete_actions', 'Actions', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_tooltip_flagFr', 'Passer en Français', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_tooltip_flagEn', 'Switch to English ', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_tooltip_ajouter', 'Add an item', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_tooltip_editer', 'Edit this item', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_tooltip_supprimer', 'Delete fhis item', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_tooltip_rechercher', 'Search', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_tooltip_annuler', 'Cancel', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_tooltip_valider', 'Validate', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'calendrier_tooltip_plus','Move a week forward', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'calendrier_tooltip_moins','Move a week backward', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'calendrier_tooltip_plusPlus','Move a month forward', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'calendrier_tooltip_moinsMoins','Move a month backward', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'calendrier_tooltip_dateParDefaut','Move to today', 'en' from LIBELLE;
-- Messages d'erreur génériques
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'erreur_connexion', 'Invalid credentials', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'erreur_aucuneConnexionInternet', 'No Internet connexion', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'erreur_securiteParNavigateur', 'Security error detected by the browser. Are you logged in ?', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'erreur_securite', 'Security error during API call. Please try to reconnect', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'erreur_apiNonDisponible', 'API not available', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'erreur_http', 'HTTP error', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'erreur_pgm', 'Code error', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_LOGIN', 'Credentials error', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_LOGIN_MDP', 'Login and/or password is too short ({{0}} minimum)', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_LOGIN_VEROUILLE', 'Login error - this account is locked', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_ROLE_NOM', 'Profile name is too short ({{0}} minimum)', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_SHA', 'Encryption error', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_OBJET_DEJA_EXISTANT', 'Object of type {{0}} with id {{1}} already exists', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_OBJET_NON_EXISTANT', 'Object of type {{0}} and with reference {{1}} do not exist', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_REFERENCE_NON_VALIDE', 'Invalid reference {{0}}', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_OBJET_FONCTIONNELEMENT_EN_DOUBLE', 'An object of type ''{{0}}'' already exists with attribute''{{1}}'' and value ''{{2}}''', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_PARAMETRE_MANQUANT', 'Parameter ''{{0}}'' is required.', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_DATES_INCOHERENTES', 'Parameters ''{{0}}'' and ''{{1}}'' are not valide.', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'RESERVATION_DEJA_EXISTANTE', 'A reservation already exists those days', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'SUPPRESSION_IMPOSSIBLE_OBJETS_LIES', 'Can not delete this object because of linked ''{{0}}''.', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'TRANSITION_ETAT_IMPOSSIBLE', 'Can not change state from ''{{0}}'' to ''{{1}}''.', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'RESERVATION_DATES_INCOHERENTES', 'Invalid start and end dates.', 'en' from LIBELLE;
-- Page de réservation
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservation_titre', 'Booking details of ', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservation_placeholder_dateDebut', 'Start date', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservation_placeholder_dateFin', 'End date', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservation_placeholder_client', 'Customer', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservation_placeholder_chambre', 'Room', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservation_placeholder_formule', 'Formula', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservation_bouton_enregistrer', 'Save', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservation_bouton_arriveeClient', 'Customer arrival', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservation_bouton_departClient', 'Customer departure', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservations_titre_calendrier','Booking board', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservations_header_date','Date', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservations_form_du','From', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservations_form_au','To', 'en' from LIBELLE;
-- Page d'administration des consommations
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'adminConso_titre_listeDesProduits','Products', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'adminConso_placeholder_nomProduit','Product name', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'adminConso_placeholder_couleurProduit','Color', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'adminConso_placeholder_prix','Price', 'en' from LIBELLE;
-- Page d'administration des réservations
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'adminResa_titre_listeDesChambres','Rooms', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'adminResa_titre_listeDesFormules','Formulas', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'adminResa_titre_listeDesOptions','Options', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'adminResa_placeholder_prix','Pricd', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'adminResa_placeholder_nomOption','Option name', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'adminResa_placeholder_nomFormule','Formula name', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'adminResa_placeholder_prixParNuit','Price per night', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'adminResa_placeholder_prixParPersonne','Price per person', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'adminResa_placeholder_nomChambre','Room name', 'en' from LIBELLE;
-- Page des consommations
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'consommations_titre_maincourante','Daybook', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'consommations_titre_aucuneReservationEnCours','No booking in progress', 'en' from LIBELLE;
-- Page de gestion des ressources
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ressource_titre', 'Resources management', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ressource_entete_clef', 'Key', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ressource_entete_description', 'Description', 'en' from LIBELLE;
