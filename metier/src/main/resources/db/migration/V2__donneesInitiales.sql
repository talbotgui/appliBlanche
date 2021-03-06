insert into chambre (nom) values ('Bleue');
insert into chambre (nom) values ('Rouge');
insert into chambre (nom) values ('Jaune');
insert into chambre (nom) values ('Verte');

insert into option_reservation (nom, prix, par_nuit, par_personne) values ('Lit supplémentaire enfant', 4, true, false);
insert into option_reservation (nom, prix, par_nuit, par_personne) values ('Lit supplémentaire adulte', 10, true, false);
insert into option_reservation (nom, prix, par_nuit, par_personne) values ('Repas du midi', 12, true, true);

insert into formule (nom, prix_par_nuit) values ('Double sans repas du soir', 60);
insert into formule (nom, prix_par_nuit) values ('Double avec repas du soir', 69);
insert into formule (nom, prix_par_nuit) values ('Double pension complète', 80);

insert into produit (couleur, nom, prix) values ('orange', 'Bière blonde maison (1 pack)', 12);
insert into produit (couleur, nom, prix) values ('orange', 'Bière brune maison (1 pack)', 12);
insert into produit (couleur, nom, prix) values ('orange', 'Bière blanche maison (1 pack)', 10);
insert into produit (couleur, nom, prix) values ('orange', 'Bière rousse maison (1 pack)', 12);
insert into produit (couleur, nom, prix) values ('indianred', 'Bière blonde maison (1 unité)', 5);
insert into produit (couleur, nom, prix) values ('indianred', 'Bière brune maison (1 unité)', 5);
insert into produit (couleur, nom, prix) values ('indianred', 'Bière blanche maison (1 unité)', 4);
insert into produit (couleur, nom, prix) values ('indianred', 'Bière rousse maison (1 unité)', 5);
insert into produit (couleur, nom, prix) values ('indianred', 'Kir mûr (compris au dîner)', 0);
insert into produit (couleur, nom, prix) values ('indianred', 'Kir mûr (supplément)', 3);
insert into produit (couleur, nom, prix) values ('indianred', 'Kir cassis (compris au dîner)', 0);
insert into produit (couleur, nom, prix) values ('indianred', 'Kir cassis (supplément)', 3);
insert into produit (couleur, nom, prix) values ('indianred', 'Kir framboise (compris au dîner)', 0);
insert into produit (couleur, nom, prix) values ('indianred', 'Kir framboise (supplément)', 3);
insert into produit (couleur, nom, prix) values ('indianred', 'Sirop (compris au dîner)', 0);
insert into produit (couleur, nom, prix) values ('indianred', 'Sirop (supplément)', 3);
insert into produit (couleur, nom, prix) values ('indianred', 'Jus de fruit (compris au dîner)', 0);
insert into produit (couleur, nom, prix) values ('indianred', 'Jus de fruit (supplément)', 2);
insert into produit (couleur, nom, prix) values ('cyan', 'Confiture fraise maison', 5);
insert into produit (couleur, nom, prix) values ('cyan', 'Confiture cerise maison', 7);
insert into produit (couleur, nom, prix) values ('cyan', 'Confiture framboise maison', 9);
insert into produit (couleur, nom, prix) values ('cyan', 'Confiture poire-vanille maison', 7);
insert into produit (couleur, nom, prix) values ('cyan', 'Confiture potiron maison', 9);
insert into produit (couleur, nom, prix) values ('cyan', 'Confiture abricot-vanille maison', 6);
insert into produit (couleur, nom, prix) values ('blueviolet', 'Panier repas', 12);

INSERT INTO MOYEN_DE_PAIEMENT (montant_associe, nom) VALUES (0,'Carte bancaire');
INSERT INTO MOYEN_DE_PAIEMENT (montant_associe, nom) VALUES (0,'Liquide');
INSERT INTO MOYEN_DE_PAIEMENT (montant_associe, nom) VALUES (80,'DakotaBox');
INSERT INTO MOYEN_DE_PAIEMENT (montant_associe, nom) VALUES (80,'Smartbox');
INSERT INTO MOYEN_DE_PAIEMENT (montant_associe, nom) VALUES (0,'Chèque');
