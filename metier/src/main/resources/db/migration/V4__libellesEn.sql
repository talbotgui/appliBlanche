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
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_deconnexion', 'Logout', 'en' from LIBELLE;
-- Page de gestion des utilisateurs
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_titre', 'Users management', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_entete_identifiant', 'Login', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_entete_roles', 'Profiles', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_placeholder_login', 'username', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_placeholder_mdp', 'password', 'en' from LIBELLE;
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
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'client_placeholder_ville', 'city', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'client_formulaire_titre', 'Add/modify a customer', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'client_bouton_creer', 'Save this customer', 'en' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'client_bouton_annuler', 'Cancel', 'en' from LIBELLE;
-- Libellés communs à plusieurs pages 
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_entete_actions', 'Actions', 'en' from LIBELLE;