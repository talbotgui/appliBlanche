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
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_client', 'Clients', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_titre_reservation', 'Réservations', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_reservations', 'Réservations', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'menu_adminreservations', 'Paramètres Réservations', 'fr' from LIBELLE;
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
-- Page de gestion des clients
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'client_titre', 'Gestion des clients', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'client_entete_nom', 'Nom du client', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'client_entete_ville', 'Ville', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'client_entete_nbDossiers', 'Nombre de dossiers', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'client_entete_nbDemandes', 'Nombre de demandes', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'client_entete_dateCreationDernierDossier', 'Date de création du dernier dossier', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'client_placeholder_nom', 'nom', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'client_placeholder_nom_validation', 'Le nom du client est obligatoire', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'client_placeholder_ville', 'ville', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'client_formulaire_titre', 'Ajouter/modifier un client', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'client_bouton_creer', 'Sauvegarder ce client', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'client_bouton_annuler', 'Annuler', 'fr' from LIBELLE;
-- Libellés communs à plusieurs pages 
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_entete_actions', 'Actions', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_tooltip_flagFr', 'Passer en Français', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_tooltip_flagEn', 'Switch to English', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_tooltip_ajouter', 'Ajouter un élément', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_tooltip_editer', 'Editer l''élément', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_tooltip_supprimer', 'Supprimer l''élément', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'commun_tooltip_rechercher', 'Rechercher', 'fr' from LIBELLE;
-- Messages d'erreur génériques
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'erreur_connexion', 'Paramètres de connexion incorrects', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'erreur_aucuneConnexionInternet', 'Aucune connexion Internet disponible', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'erreur_securiteParNavigateur', 'Erreur de sécurité détectée par le navigateur. Etes-vous connecté ?', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'erreur_securite', 'Erreur de sécurité lors d''un appel à l''API. Tentez de vous reconnecter', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'erreur_apiNonDisponible', 'API down', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'erreur_http', 'Erreur HTTP', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'erreur_pgm', 'Erreur de programmation', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'ERREUR_CLIENT_NOM_DEJA_EXISTANT', 'Un client nommé ''{{0}}'' existe déjà', 'fr' from LIBELLE;
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
-- Page de réservation
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservation_placeholder_dateDebut', 'Date de début', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservation_placeholder_dateFin', 'Date de fin', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservation_placeholder_client', 'Nom du client', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservation_placeholder_chambre', 'Chambre', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'reservation_bouton_enregistrer', 'Enregistrer', 'fr' from LIBELLE;
