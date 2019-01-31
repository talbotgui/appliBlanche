create table moyen_de_paiement (id bigint generated by default as identity (start with 1), montant_associe double, nom varchar(255), primary key (id));
create table paiement (id bigint generated by default as identity (start with 1), date_creation date, montant double, moyen_de_paiement_id bigint, reservation_id bigint, primary key (id));
alter table paiement add constraint FKsy12rhsevct14mw63dr63rp3v foreign key (moyen_de_paiement_id) references moyen_de_paiement;
alter table paiement add constraint FK4niq4oqae97gwkjoyq4bkhlv0 foreign key (reservation_id) references reservation;

INSERT INTO MOYEN_DE_PAIEMENT (montant_associe, nom) VALUES (0,'Carte bancaire');
INSERT INTO MOYEN_DE_PAIEMENT (montant_associe, nom) VALUES (0,'Liquide');
INSERT INTO MOYEN_DE_PAIEMENT (montant_associe, nom) VALUES (80,'DakotaBox');
INSERT INTO MOYEN_DE_PAIEMENT (montant_associe, nom) VALUES (80,'Smartbox');
INSERT INTO MOYEN_DE_PAIEMENT (montant_associe, nom) VALUES (0,'Ch\u00e8que');


insert into LIBELLE (clef, libelle, langue) values ('menu_facturation', 'Facturation', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('menu_titre_facturation', 'Paiements', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('menu_facturation', 'Billing', 'en');
insert into LIBELLE (clef, libelle, langue) values ('menu_titre_facturation', 'Paiments', 'en');

insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_titre', 'Liste des moyens de paiement', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_entete_nom', 'Nom', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_entete_montantAssocie', 'Montant associé', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_formulaire_titre', 'Ajout/modifcation', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_placeholder_nom_validation', 'Nom obligatoire', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_placeholder_nom', 'Nom', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_placeholder_montantAssocie', 'Montant associé', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_bouton_annuler', 'Annuler', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_bouton_creer', 'Sauvegarder', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_titre', 'Paiment modes', 'en');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_entete_nom', 'Name', 'en');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_entete_montantAssocie', 'Amount', 'en');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_formulaire_titre', 'Add/edit', 'en');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_placeholder_nom_validation', 'The name is mandatory', 'en');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_placeholder_nom', 'Name', 'en');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_placeholder_montantAssocie', 'Amount', 'en');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_bouton_annuler', 'Cancel', 'en');
insert into LIBELLE (clef, libelle, langue) values ('moyendepaiement_bouton_creer', 'Save', 'en');

insert into LIBELLE (clef, libelle, langue) values ('cadrelistefacture_titre', 'Liste des réservations facturables/facturées', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('cadrelistefacture_listeReservationsEnCours', 'Réservations en cours :', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('cadrelistefacture_listeReservationsPayes', 'Déjà facturées :', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('cadrelistefacture_titre', 'Booking paid or to paid', 'en');
insert into LIBELLE (clef, libelle, langue) values ('cadrelistefacture_listeReservationsEnCours', 'Bookings in progress', 'en');
insert into LIBELLE (clef, libelle, langue) values ('cadrelistefacture_listeReservationsPayes', 'Paid bookings', 'en');

insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_titre', 'Paiements', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_sstitrePaiement', 'Enregistrés', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_sstitreNouveau', 'Nouveau paiement', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_aucunPaiement', 'Aucun paiement enregistré', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_bouton_ajouter', 'Ajouter un paiement', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_placeholder_mdp', 'moyen de paiement', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_placeholder_montant', 'montant', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_bouton_creer', 'Enregistrer', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_titre', 'Paiments', 'en');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_sstitrePaiement', 'Already paid', 'en');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_sstitreNouveau', 'New paiment', 'en');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_aucunPaiement', 'No paiment recorded', 'en');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_bouton_ajouter', 'Add a paiment', 'en');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_placeholder_mdp', 'means of paiment', 'en');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_placeholder_montant', 'Amount', 'en');
insert into LIBELLE (clef, libelle, langue) values ('dialogpaiement_bouton_creer', 'Save', 'en');

insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_bouton_paiements', 'Gérer les paiements', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_bouton_afficherFacture', 'Afficher la facture', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_bouton_rendreFactureModifiable', 'Permettre la modification de la facture', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_bouton_facturer', 'Facturer', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_bouton_editerNote', 'Editer une note', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_options', 'Options : ', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_formules', 'Formule : ', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_titre', 'Facturation pour ', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_bouton_paiements', 'Paiments', 'en');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_bouton_afficherFacture', 'Display bill', 'en');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_bouton_rendreFactureModifiable', 'Enable bill modifications', 'en');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_bouton_facturer', 'Bill', 'en');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_bouton_editerNote', 'Create a ticket', 'en');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_options', 'Options : ', 'en');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_formules', 'Formula : ', 'en');
insert into LIBELLE (clef, libelle, langue) values ('cadredetailfacture_titre', 'Billing for ', 'en');

insert into LIBELLE (clef, libelle, langue) values ('commun_parNuit', ' / nuit', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('commun_parPersonne', ' / personne', 'fr');
insert into LIBELLE (clef, libelle, langue) values ('commun_parNuit', ' per night', 'en');
insert into LIBELLE (clef, libelle, langue) values ('commun_parPersonne', ' per person', 'en');

