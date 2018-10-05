-- Page de connexion
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'connexion_titre', 'Login', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'connexion_identifiant', 'Username:', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'connexion_motDePasse', 'Password:', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'connexion_boutonConnexion', 'Log in', 'en' from LIBELLE;
-- Page d'accueil
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'accueil_message', 'Welcome in this application using ', 'en' from LIBELLE;
-- Composant de menu
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_accueil', 'Home', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_utilisateur', 'Users', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_role', 'Profiles', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_client', 'Customers', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_reservations', 'Reservations', 'en' from LIBELLE;
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
-- Page de gestion des clients
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'client_titre', 'Customer management', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'client_entete_nom', 'Customer name', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'client_entete_ville', 'City', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'client_entete_nbDossiers', 'Files number', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'client_entete_nbDemandes', 'Tickets number', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'client_entete_dateCreationDernierDossier', 'Last file creation date', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'client_placeholder_nom', 'name', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'client_placeholder_nom_validation', 'Customer name is required', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'client_placeholder_ville', 'city', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'client_formulaire_titre', 'Add/modify a customer', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'client_bouton_creer', 'Save this customer', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'client_bouton_annuler', 'Cancel', 'en' from LIBELLE;
-- Libellés communs à plusieurs pages 
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_entete_actions', 'Actions', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_tooltip_flagFr', 'Passer en Français', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_tooltip_flagEn', 'Switch to English ', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_tooltip_ajouter', 'Add an item', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_tooltip_editer', 'Edit this item', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_tooltip_supprimer', 'Delete fhis item', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_tooltip_rechercher', 'Search', 'fr' from LIBELLE;
-- Messages d'erreur génériques
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'erreur_connexion', 'Invalid credentials', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'erreur_aucuneConnexionInternet', 'No Internet connexion', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'erreur_securiteParNavigateur', 'Security error detected by the browser. Are you logged in ?', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'erreur_securite', 'Security error during API call. Please try to reconnect', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'erreur_apiNonDisponible', 'API not available', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'erreur_http', 'HTTP error', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'erreur_pgm', 'Code error', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_CLIENT_NOM_DEJA_EXISTANT', 'A customer ''{{0}}'' already exists', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_LOGIN', 'Credentials error', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_LOGIN_MDP', 'Login and/or password is too short ({{0}} minimum)', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_LOGIN_VEROUILLE', 'Login error - this account is locked', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_ROLE_NOM', 'Profile name is too short ({{0}} minimum)', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_SHA', 'Encryption error', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_OBJET_DEJA_EXISTANT', 'Object of type {{0}} with id {{1}} already exists', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_OBJET_NON_EXISTANT', 'Object of type {{0}} and with reference {{1}} do not exist', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_REFERENCE_NON_VALIDE', 'Invalid reference {{0}}', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_OBJET_FONCTIONNELEMENT_EN_DOUBLE', 'An object of type ''{{0}}'' already exists with attribute''{{1}}'' and value ''{{2}}''', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_PARAMETRE_MANQUANT', 'Parameter ''{{0}}'' is required.', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_DATES_INCOHERENTES', 'Parameters ''{{0}}'' and ''{{1}}'' are not valide.', 'en' from LIBELLE;
-- Page de réservation
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservation_placeholder_dateDebut', 'Start date', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservation_placeholder_dateFin', 'End date', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservation_placeholder_client', 'Customer', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservation_placeholder_chambre', 'Room', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservation_bouton_enregistrer', 'Save', 'en' from LIBELLE;
