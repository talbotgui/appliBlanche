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
insert into produit (id, couleur, nom, prix) values (nextval('hibernate_sequence'), 'orange', 'Bière blonde maison (1 pack)', 12);
insert into produit (id, couleur, nom, prix) values (nextval('hibernate_sequence'), 'orange', 'Bière brune maison (1 pack)', 12);
insert into produit (id, couleur, nom, prix) values (nextval('hibernate_sequence'), 'orange', 'Bière blanche maison (1 pack)', 10);
insert into produit (id, couleur, nom, prix) values (nextval('hibernate_sequence'), 'orange', 'Bière rousse maison (1 pack)', 12);
insert into produit (id, couleur, nom, prix) values (nextval('hibernate_sequence'), 'indianred', 'Bière blonde maison (1 unité)', 5);
insert into produit (id, couleur, nom, prix) values (nextval('hibernate_sequence'), 'indianred', 'Bière brune maison (1 unité)', 5);
insert into produit (id, couleur, nom, prix) values (nextval('hibernate_sequence'), 'indianred', 'Bière blanche maison (1 unité)', 4);
insert into produit (id, couleur, nom, prix) values (nextval('hibernate_sequence'), 'indianred', 'Bière rousse maison (1 unité)', 5);
insert into produit (id, couleur, nom, prix) values (nextval('hibernate_sequence'), 'indianred', 'Kir mûr (compris au dîner)', 0);
insert into produit (id, couleur, nom, prix) values (nextval('hibernate_sequence'), 'indianred', 'Kir mûr (supplément)', 3);
insert into produit (id, couleur, nom, prix) values (nextval('hibernate_sequence'), 'indianred', 'Kir cassis (compris au dîner)', 0);
insert into produit (id, couleur, nom, prix) values (nextval('hibernate_sequence'), 'indianred', 'Kir cassis (supplément)', 3);
insert into produit (id, couleur, nom, prix) values (nextval('hibernate_sequence'), 'indianred', 'Kir framboise (compris au dîner)', 0);
insert into produit (id, couleur, nom, prix) values (nextval('hibernate_sequence'), 'indianred', 'Kir framboise (supplément)', 3);
insert into produit (id, couleur, nom, prix) values (nextval('hibernate_sequence'), 'indianred', 'Sirop (compris au dîner)', 0);
insert into produit (id, couleur, nom, prix) values (nextval('hibernate_sequence'), 'indianred', 'Sirop (supplément)', 3);
insert into produit (id, couleur, nom, prix) values (nextval('hibernate_sequence'), 'indianred', 'Jus de fruit (compris au dîner)', 0);
insert into produit (id, couleur, nom, prix) values (nextval('hibernate_sequence'), 'indianred', 'Jus de fruit (supplément)', 2);
insert into produit (id, couleur, nom, prix) values (nextval('hibernate_sequence'), 'cyan', 'Confiture fraise maison', 5);
insert into produit (id, couleur, nom, prix) values (nextval('hibernate_sequence'), 'cyan', 'Confiture cerise maison', 7);
insert into produit (id, couleur, nom, prix) values (nextval('hibernate_sequence'), 'cyan', 'Confiture framboise maison', 9);
insert into produit (id, couleur, nom, prix) values (nextval('hibernate_sequence'), 'cyan', 'Confiture poire-vanille maison', 7);
insert into produit (id, couleur, nom, prix) values (nextval('hibernate_sequence'), 'cyan', 'Confiture potiron maison', 9);
insert into produit (id, couleur, nom, prix) values (nextval('hibernate_sequence'), 'cyan', 'Confiture abricot-vanille maison', 6);
insert into produit (id, couleur, nom, prix) values (nextval('hibernate_sequence'), 'blueviolet', 'Panier repas', 12);