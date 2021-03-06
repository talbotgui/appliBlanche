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
insert into LIBELLE (clef, libelle, langue) values ('menu_monitoring', 'Monitoring', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('menu_facturation', 'Facturation', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('menu_titre_facturation', 'Paiements', 'fr');
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
insert into LIBELLE (clef, libelle, langue) values ('commmun_champ_obligatoire', 'Champ obligatoire', 'fr');
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
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_CREATION_DOCUMENT', 'Erreur durant la création du document', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('ERREUR_AUCUN_MONTANT', 'Aucun montant dans le paiement ni dans le moyen de paiement', 'fr');
-- Page de réservation
insert into LIBELLE (clef, libelle, langue) values ('reservation_titre', 'Réservation ', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('reservation_placeholder_dateDebut', 'Date de début', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('reservation_placeholder_dateFin', 'Date de fin', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('reservation_placeholder_client', 'Nom du client', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('reservation_placeholder_chambre', 'Chambre', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('reservation_placeholder_formule', 'Formule', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('reservation_placeholder_nombrePersonnes', 'Nombre de personnes', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('reservation_bouton_enregistrer', 'Enregistrer', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('reservation_bouton_arriveeClient', 'Arrivée du client', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('reservation_bouton_departClient', 'Départ du client', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('reservations_titre_calendrier','Tableau des réservations', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('reservations_header_date','Date', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('reservations_form_du','Du', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('reservations_form_au','Au', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('reservation_bouton_fermer', 'Fermer', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('reservation_bouton_fermer', 'Close', 'en');
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
-- Page de monitoring
insert into LIBELLE (clef, libelle, langue) values ('monitoring_titre', 'Monitoring', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('monitoring_entete_clef', 'Clef', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('monitoring_entete_nbAppels', 'Nb appels', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('monitoring_entete_tempsCumule', 'Cumul', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('monitoring_entete_tempsMoyen', 'Moyen', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('monitoring_entete_tempsMax', 'Max', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('monitoring_entete_tempsMin', 'Min', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('monitoring_bouton_export', 'Exporter', 'fr');
-- Notifications
insert into LIBELLE (clef, libelle, langue) values ('notification_nouvelleReservation', 'Une nouvelle réservation vient d''être enregistrée', 'fr');
-- Gestion des moyens de paiement 
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_titre', 'Liste des moyens de paiement', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_entete_nom', 'Nom', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_entete_montantAssocie', 'Montant associé', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_formulaire_titre', 'Ajout/modifcation', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_placeholder_nom_validation', 'Nom obligatoire', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_placeholder_nom', 'Nom', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_placeholder_montantAssocie', 'Montant associé', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_bouton_annuler', 'Annuler', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_bouton_creer', 'Sauvegarder', 'fr');
-- Facturation
insert into LIBELLE (clef, libelle, langue) values ('cadrelistefacture_titre', 'Liste des réservations facturables/facturées', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('cadrelistefacture_listeReservationsEnCours', 'Réservations en cours :', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('cadrelistefacture_listeReservationsPayes', 'Déjà facturées :', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_titre', 'Paiements', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_sstitrePaiement', 'Enregistrés', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_sstitreNouveau', 'Nouveau paiement', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_aucunPaiement', 'Aucun paiement enregistré', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_bouton_ajouter', 'Ajouter un paiement', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_placeholder_mdp', 'moyen de paiement', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_placeholder_montant', 'montant', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_bouton_creer', 'Enregistrer', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_bouton_paiements', 'Gérer les paiements', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_bouton_afficherFacture', 'Afficher la facture', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_bouton_rendreFactureModifiable', 'Permettre la modification de la facture', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_bouton_facturer', 'Facturer', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_bouton_editerNote', 'Editer une note', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_options', 'Options : ', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_formules', 'Formule : ', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_titre', 'Facturation pour ', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_paiement', 'Paiements : ', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_paiement', 'Payments: ', 'en');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_restantDu', 'Restant dû : ', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_consommations', 'Consommations :', 'fr');
-- Commun
insert into LIBELLE (clef, libelle, langue) values ('commun_parNuit', ' / nuit', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('commun_parPersonne', ' / personne', 'fr');
