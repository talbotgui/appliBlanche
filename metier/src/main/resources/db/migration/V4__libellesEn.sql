-- Page de connexion
insert into LIBELLE (clef, libelle, langue) values ('connexion_titre', 'Login', 'en');
insert into LIBELLE (clef, libelle, langue) values ('connexion_identifiant', 'Username:', 'en');
insert into LIBELLE (clef, libelle, langue) values ('connexion_motDePasse', 'Password:', 'en');
insert into LIBELLE (clef, libelle, langue) values ('connexion_boutonConnexion', 'Log in', 'en');
-- Page d'accueil
insert into LIBELLE (clef, libelle, langue) values ('accueil_message', 'Welcome in this application using ', 'en');
-- Composant de menu
insert into LIBELLE (clef, libelle, langue) values ('menu_accueil', 'Home', 'en');
insert into LIBELLE (clef, libelle, langue) values ('menu_titre_administration', 'Admin', 'en');
insert into LIBELLE (clef, libelle, langue) values ('menu_utilisateur', 'Users', 'en');
insert into LIBELLE (clef, libelle, langue) values ('menu_role', 'Profiles', 'en');
insert into LIBELLE (clef, libelle, langue) values ('menu_ressource', 'Resources', 'en');
insert into LIBELLE (clef, libelle, langue) values ('menu_titre_reservation', 'Reservations', 'en');
insert into LIBELLE (clef, libelle, langue) values ('menu_reservations', 'Planning', 'en');
insert into LIBELLE (clef, libelle, langue) values ('menu_adminreservations', 'Rooms & formula', 'en');
insert into LIBELLE (clef, libelle, langue) values ('menu_titre_consommation', 'Goods', 'en');
insert into LIBELLE (clef, libelle, langue) values ('menu_consommation', 'Goods', 'en');
insert into LIBELLE (clef, libelle, langue) values ('menu_adminconsommation', 'Products', 'en');
insert into LIBELLE (clef, libelle, langue) values ('menu_deconnexion', 'Logout', 'en');
insert into LIBELLE (clef, libelle, langue) values ('menu_monitoring', 'Monitoring', 'en');
insert into LIBELLE (clef, libelle, langue) values ('menu_facturation', 'Billing', 'en');
insert into LIBELLE (clef, libelle, langue) values ('menu_titre_facturation', 'Paiments', 'en');
-- Page de gestion des utilisateurs
insert into LIBELLE (clef, libelle, langue) values ('utilisateur_titre', 'Users management', 'en');
insert into LIBELLE (clef, libelle, langue) values ('utilisateur_entete_identifiant', 'Login', 'en');
insert into LIBELLE (clef, libelle, langue) values ('utilisateur_entete_roles', 'Profiles', 'en');
insert into LIBELLE (clef, libelle, langue) values ('utilisateur_placeholder_login', 'username', 'en');
insert into LIBELLE (clef, libelle, langue) values ('utilisateur_placeholder_login_validation', 'Username is required', 'en');
insert into LIBELLE (clef, libelle, langue) values ('utilisateur_placeholder_login_validation2', 'Username must be 6 characters at least', 'en');
insert into LIBELLE (clef, libelle, langue) values ('utilisateur_placeholder_mdp', 'password', 'en');
insert into LIBELLE (clef, libelle, langue) values ('utilisateur_placeholder_mdp_validation', 'Password is required', 'en');
insert into LIBELLE (clef, libelle, langue) values ('utilisateur_placeholder_mdp_validation2', 'Password must be 6 characters at least', 'en');
insert into LIBELLE (clef, libelle, langue) values ('utilisateur_formulaire_titre', 'Add or modify a user', 'en');
insert into LIBELLE (clef, libelle, langue) values ('utilisateur_bouton_creer', 'Save this user', 'en');
insert into LIBELLE (clef, libelle, langue) values ('utilisateur_bouton_annuler', 'Cancel', 'en');
-- Page de gestion des rôles
insert into LIBELLE (clef, libelle, langue) values ('role_titre', 'Profiles management', 'en');
insert into LIBELLE (clef, libelle, langue) values ('role_entete_nom', 'Name', 'en');
insert into LIBELLE (clef, libelle, langue) values ('role_placeholder_nom', 'name', 'en');
insert into LIBELLE (clef, libelle, langue) values ('role_formulaire_titre', 'Add/modify a profile', 'en');
insert into LIBELLE (clef, libelle, langue) values ('role_bouton_creer', 'Save this profile', 'en');
insert into LIBELLE (clef, libelle, langue) values ('role_bouton_annuler', 'Cancel', 'en');
-- Libellés communs à plusieurs pages 
insert into LIBELLE (clef, libelle, langue) values ('commun_entete_actions', 'Actions', 'en');
insert into LIBELLE (clef, libelle, langue) values ('commun_tooltip_flagFr', 'Passer en Français', 'en');
insert into LIBELLE (clef, libelle, langue) values ('commun_tooltip_flagEn', 'Switch to English ', 'en');
insert into LIBELLE (clef, libelle, langue) values ('commun_tooltip_ajouter', 'Add an item', 'en');
insert into LIBELLE (clef, libelle, langue) values ('commun_tooltip_editer', 'Edit this item', 'en');
insert into LIBELLE (clef, libelle, langue) values ('commun_tooltip_supprimer', 'Delete fhis item', 'en');
insert into LIBELLE (clef, libelle, langue) values ('commun_tooltip_rechercher', 'Search', 'en');
insert into LIBELLE (clef, libelle, langue) values ('commun_tooltip_annuler', 'Cancel', 'en');
insert into LIBELLE (clef, libelle, langue) values ('commun_tooltip_valider', 'Validate', 'en');
insert into LIBELLE (clef, libelle, langue) values ('commmun_champ_obligatoire', 'Date required', 'en');
insert into LIBELLE (clef, libelle, langue) values ('calendrier_tooltip_plus','Move a week forward', 'en');
insert into LIBELLE (clef, libelle, langue) values ('calendrier_tooltip_moins','Move a week backward', 'en');
insert into LIBELLE (clef, libelle, langue) values ('calendrier_tooltip_plusPlus','Move a month forward', 'en');
insert into LIBELLE (clef, libelle, langue) values ('calendrier_tooltip_moinsMoins','Move a month backward', 'en');
insert into LIBELLE (clef, libelle, langue) values ('calendrier_tooltip_dateParDefaut','Move to today', 'en');
-- Messages d'erreur génériques
insert into LIBELLE (clef, libelle, langue) values ('erreur_connexion', 'Invalid credentials', 'en');
insert into LIBELLE (clef, libelle, langue) values ('erreur_aucuneConnexionInternet', 'No Internet connexion', 'en');
insert into LIBELLE (clef, libelle, langue) values ('erreur_securiteParNavigateur', 'Security error detected by the browser. Are you logged in ?', 'en');
insert into LIBELLE (clef, libelle, langue) values ('erreur_securite', 'Security error during API call. Please try to reconnect', 'en');
insert into LIBELLE (clef, libelle, langue) values ('erreur_apiNonDisponible', 'API not available', 'en');
insert into LIBELLE (clef, libelle, langue) values ('erreur_http', 'HTTP error', 'en');
insert into LIBELLE (clef, libelle, langue) values ('erreur_pgm', 'Code error', 'en');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_DATES_INCOHERENTES', 'Parameters ''{{0}}'' and ''{{1}}'' are not valide.', 'en');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_LOGIN', 'Credentials error', 'en');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_LOGIN_MDP', 'Login and/or password is too short ({{0}} minimum)', 'en');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_LOGIN_VEROUILLE', 'Login error - this account is locked', 'en');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_OBJET_DEJA_EXISTANT', 'Object of type {{0}} with id {{1}} already exists', 'en');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_OBJET_FONCTIONNELEMENT_EN_DOUBLE', 'An object of type ''{{0}}'' already exists with attribute''{{1}}'' and value ''{{2}}''', 'en');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_OBJET_NON_EXISTANT', 'Object of type {{0}} and with reference {{1}} do not exist', 'en');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_PARAMETRE_MANQUANT', 'Parameter ''{{0}}'' is required.', 'en');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_REFERENCE_NON_VALIDE', 'Invalid reference {{0}}', 'en');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_RESERVATION_DATES_INCOHERENTES', 'Invalid start and end dates.', 'en');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_RESERVATION_DEJA_EXISTANTE', 'A booking already exists those days', 'en');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_ROLE_NOM', 'Profile name is too short ({{0}} minimum)', 'en');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_SHA', 'Encryption error', 'en');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_SUPPRESSION_IMPOSSIBLE_OBJETS_LIES', 'Can not delete this object because of linked ''{{0}}''.', 'en');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_TRANSITION_ETAT_IMPOSSIBLE', 'Can not change state from ''{{0}}'' to ''{{1}}''.', 'en');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_RESERVATION_PAS_EN_COURS', 'Booking ''{{0}}'' not in progress', 'en');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_CREATION_DOCUMENT', 'Error during document creation', 'en');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_AUCUN_MONTANT', 'No amount neither in payment nor in means of payment', 'en');
-- Page de réservation
insert into LIBELLE (clef, libelle, langue) values ('reservation_titre', 'Booking ', 'en');
insert into LIBELLE (clef, libelle, langue) values ('reservation_placeholder_dateDebut', 'Start date', 'en');
insert into LIBELLE (clef, libelle, langue) values ('reservation_placeholder_dateFin', 'End date', 'en');
insert into LIBELLE (clef, libelle, langue) values ('reservation_placeholder_client', 'Customer', 'en');
insert into LIBELLE (clef, libelle, langue) values ('reservation_placeholder_chambre', 'Room', 'en');
insert into LIBELLE (clef, libelle, langue) values ('reservation_placeholder_formule', 'Formula', 'en');
insert into LIBELLE (clef, libelle, langue) values ('reservation_placeholder_nombrePersonnes', 'Persons count', 'en');
insert into LIBELLE (clef, libelle, langue) values ('reservation_bouton_enregistrer', 'Save', 'en');
insert into LIBELLE (clef, libelle, langue) values ('reservation_bouton_arriveeClient', 'Customer arrival', 'en');
insert into LIBELLE (clef, libelle, langue) values ('reservation_bouton_departClient', 'Customer departure', 'en');
insert into LIBELLE (clef, libelle, langue) values ('reservations_titre_calendrier','Booking board', 'en');
insert into LIBELLE (clef, libelle, langue) values ('reservations_header_date','Date', 'en');
insert into LIBELLE (clef, libelle, langue) values ('reservations_form_du','From', 'en');
insert into LIBELLE (clef, libelle, langue) values ('reservations_form_au','To', 'en');
insert into LIBELLE (clef, libelle, langue) values ('reservation_bouton_annuler', 'Annuler', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('reservation_bouton_annuler', 'Cancel', 'en');
-- Page d'administration des consommations
insert into LIBELLE (clef, libelle, langue) values ('adminConso_titre_listeDesProduits','Products', 'en');
insert into LIBELLE (clef, libelle, langue) values ('adminConso_placeholder_nomProduit','Product name', 'en');
insert into LIBELLE (clef, libelle, langue) values ('adminConso_placeholder_couleurProduit','Color', 'en');
insert into LIBELLE (clef, libelle, langue) values ('adminConso_placeholder_prix','Price', 'en');
-- Page d'administration des réservations
insert into LIBELLE (clef, libelle, langue) values ('adminResa_titre_listeDesChambres','Rooms', 'en');
insert into LIBELLE (clef, libelle, langue) values ('adminResa_titre_listeDesFormules','Formulas', 'en');
insert into LIBELLE (clef, libelle, langue) values ('adminResa_titre_listeDesOptions','Options', 'en');
insert into LIBELLE (clef, libelle, langue) values ('adminResa_placeholder_prix','Pricd', 'en');
insert into LIBELLE (clef, libelle, langue) values ('adminResa_placeholder_nomOption','Option name', 'en');
insert into LIBELLE (clef, libelle, langue) values ('adminResa_placeholder_nomFormule','Formula name', 'en');
insert into LIBELLE (clef, libelle, langue) values ('adminResa_placeholder_prixParNuit','Price per night', 'en');
insert into LIBELLE (clef, libelle, langue) values ('adminResa_placeholder_prixParPersonne','Price per person', 'en');
insert into LIBELLE (clef, libelle, langue) values ('adminResa_placeholder_nomChambre','Room name', 'en');
-- Page des consommations
insert into LIBELLE (clef, libelle, langue) values ('consommations_titre_maincourante','Daybook', 'en');
insert into LIBELLE (clef, libelle, langue) values ('consommations_titre_aucuneReservationEnCours','No booking in progress', 'en');
-- Page de gestion des ressources
insert into LIBELLE (clef, libelle, langue) values ('ressource_titre', 'Resources management', 'en');
insert into LIBELLE (clef, libelle, langue) values ('ressource_entete_clef', 'Key', 'en');
insert into LIBELLE (clef, libelle, langue) values ('ressource_entete_description', 'Description', 'en');
-- Page de monitoring
insert into LIBELLE (clef, libelle, langue) values ('monitoring_titre', 'Monitoring', 'en');
insert into LIBELLE (clef, libelle, langue) values ('monitoring_entete_clef', 'Key', 'en');
insert into LIBELLE (clef, libelle, langue) values ('monitoring_entete_nbAppels', 'Calls', 'en');
insert into LIBELLE (clef, libelle, langue) values ('monitoring_entete_tempsCumule', 'Sum', 'en');
insert into LIBELLE (clef, libelle, langue) values ('monitoring_entete_tempsMoyen', 'Average', 'en');
insert into LIBELLE (clef, libelle, langue) values ('monitoring_entete_tempsMax', 'Max', 'en');
insert into LIBELLE (clef, libelle, langue) values ('monitoring_entete_tempsMin', 'Min', 'en');
insert into LIBELLE (clef, libelle, langue) values ('monitoring_bouton_export', 'Export', 'en');
-- Notifications
insert into LIBELLE (clef, libelle, langue) values ('notification_nouvelleReservation', 'A new booking has been created', 'en');
-- Gestion des moyens de paiement 
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_titre', 'Paiment modes', 'en');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_entete_nom', 'Name', 'en');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_entete_montantAssocie', 'Amount', 'en');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_formulaire_titre', 'Add/edit', 'en');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_placeholder_nom_validation', 'The name is mandatory', 'en');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_placeholder_nom', 'Name', 'en');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_placeholder_montantAssocie', 'Amount', 'en');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_bouton_annuler', 'Cancel', 'en');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_bouton_creer', 'Save', 'en');
-- Facturation
insert into LIBELLE (clef, libelle, langue) values ('cadrelistefacture_titre', 'Booking paid or to paid', 'en');
insert into LIBELLE (clef, libelle, langue) values ('cadrelistefacture_listeReservationsEnCours', 'Bookings in progress', 'en');
insert into LIBELLE (clef, libelle, langue) values ('cadrelistefacture_listeReservationsPayes', 'Paid bookings', 'en');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_titre', 'Paiments', 'en');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_sstitrePaiement', 'Already paid', 'en');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_sstitreNouveau', 'New paiment', 'en');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_aucunPaiement', 'No paiment recorded', 'en');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_bouton_ajouter', 'Add a paiment', 'en');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_placeholder_mdp', 'means of paiment', 'en');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_placeholder_montant', 'Amount', 'en');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_bouton_creer', 'Save', 'en');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_bouton_paiements', 'Paiments', 'en');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_bouton_afficherFacture', 'Display bill', 'en');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_bouton_rendreFactureModifiable', 'Enable bill modifications', 'en');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_bouton_facturer', 'Bill', 'en');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_bouton_editerNote', 'Create a ticket', 'en');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_options', 'Options : ', 'en');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_formules', 'Formula : ', 'en');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_titre', 'Billing for ', 'en');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_restantDu', 'To pay: ', 'en');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_montantTotal', 'Montant total : ', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_montantTotal', 'Total amount: ', 'en');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_consommations', 'Goods: ', 'en');
-- Commun
insert into LIBELLE (clef, libelle, langue) values ('commun_parNuit', ' per night', 'en');
insert into LIBELLE (clef, libelle, langue) values ('commun_parPersonne', ' per person', 'en');