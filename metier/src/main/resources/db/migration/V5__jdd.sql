insert into chambre (id, nom) values (nextval('hibernate_sequence'), 'Bleue');
insert into chambre (id, nom) values (nextval('hibernate_sequence'), 'Rouge');
insert into chambre (id, nom) values (nextval('hibernate_sequence'), 'Jaune');
insert into chambre (id, nom) values (nextval('hibernate_sequence'), 'Verte');
insert into option_reservation (id, nom, prix, par_nuit, par_personne) values (nextval('hibernate_sequence'), 'Lit supplémentaire enfant', 4, true, false);
insert into option_reservation (id, nom, prix, par_nuit, par_personne) values (nextval('hibernate_sequence'), 'Lit supplémentaire adulte', 10, true, false);
insert into option_reservation (id, nom, prix, par_nuit, par_personne) values (nextval('hibernate_sequence'), 'Repas du midi', 12, true, true);
insert into formule (id, nom, prix_par_nuit) values (nextval('hibernate_sequence'), 'Double sans repas du soir', 60);
insert into formule (id, nom, prix_par_nuit) values (nextval('hibernate_sequence'), 'Double avec repas du soir', 69);
insert into formule (id, nom, prix_par_nuit) values (nextval('hibernate_sequence'), 'Double pension complète', 80);
insert into produit (id, couleur, nom, prix) values (nextval('hibernate_sequence'), '#0000FF', 'Bière maison', 5);
insert into produit (id, couleur, nom, prix) values (nextval('hibernate_sequence'), '#00FF00', 'Confiture maison', 7);
insert into produit (id, couleur, nom, prix) values (nextval('hibernate_sequence'), '#0FF000', 'Panier repas', 12);