insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'connexion_titre', 'Connexion', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'connexion_identifiant', 'Identifiant :', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'connexion_motDePasse', 'Mot de passe :', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'connexion_boutonConnexion', 'Connexion', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_titre', 'Administration des utilisateurs', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_placeholder_login','nom d''utilisateur', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_placeholder_mdp', 'mot de passe', 'fr' from LIBELLE;
insert into LIBELLE (id, clef, libelle, langue) select count(*) + 1, 'utilisateur_bouton_creer', 'Créer l''utilisateur', 'fr' from LIBELLE;