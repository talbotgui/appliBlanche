create table lien_role_ressource (ressource_clef varchar(255) not null, role_nom varchar(255) not null, primary key (role_nom, ressource_clef));
create table lien_utilisateur_role (utilisateur_login varchar(255) not null, role_nom varchar(255) not null, primary key (utilisateur_login, role_nom));
create table ressource (clef varchar(255) not null, chemin varchar(100), description varchar(255), primary key (clef));
create table role (nom varchar(255) not null, primary key (nom));
create table utilisateur (login varchar(255) not null, mdp varchar(255), premier_echec timestamp, second_echec timestamp, troisieme_echec timestamp, primary key (login));
alter table lien_role_ressource add constraint FKeq3qlsj4hi17tyx3myd1frbvc foreign key (ressource_clef) references ressource(clef);
alter table lien_role_ressource add constraint FKaoafu0eqls0iu2xmooeinhrou foreign key (role_nom) references role;
alter table lien_utilisateur_role add constraint FKgyh7o9go7ryk79q547je1l3uh foreign key (utilisateur_login) references utilisateur;
alter table lien_utilisateur_role add constraint FKq6r2ku62k7wq6pw8aktc39nyo foreign key (role_nom) references role;

create table libelle (id bigint GENERATED BY DEFAULT AS IDENTITY, clef varchar(255), langue varchar(255), libelle varchar(255), primary key (id));
create table chambre (id bigint GENERATED BY DEFAULT AS IDENTITY, nom varchar(255), primary key (id));
create table consommation (id bigint GENERATED BY DEFAULT AS IDENTITY, date_creation date, prix_paye DOUBLE PRECISION, quantite integer, produit_id bigint, reservation_id bigint, primary key (id));
create table produit (id bigint GENERATED BY DEFAULT AS IDENTITY, couleur varchar(255), nom varchar(255), prix DOUBLE PRECISION, primary key (id));
create table reservation (id bigint GENERATED BY DEFAULT AS IDENTITY, client varchar(255), nombre_personnes bigint, etat_courant integer, date_debut date, date_fin date, chambre_id bigint, formule_id bigint, primary key (id));
create table formule (id bigint GENERATED BY DEFAULT AS IDENTITY, nom varchar(255), prix_par_nuit DOUBLE PRECISION, primary key (id));
create table option_reservee (reservation_id bigint GENERATED BY DEFAULT AS IDENTITY, option_id bigint, primary key (option_id, reservation_id));
create table option_reservation (id bigint GENERATED BY DEFAULT AS IDENTITY, nom varchar(255), par_nuit boolean, par_personne boolean, prix DOUBLE PRECISION, primary key (id));
create table moyen_de_paiement (id bigint generated by default as identity (start with 1), montant_associe double, nom varchar(255), primary key (id));
create table paiement (id bigint generated by default as identity (start with 1), date_creation date, montant double, moyen_de_paiement_id bigint, reservation_id bigint, primary key (id));
create table Facture (id bigint GENERATED BY DEFAULT AS IDENTITY, numero BIGINT, pdf BLOB(100M), reservation_id bigint, primary key (id));
alter table consommation add constraint FK9ffj492mae8twsn4qdl08nvw5 foreign key (produit_id) references produit;
alter table consommation add constraint FKmr2pegluby9t2uko43ktg2e79 foreign key (reservation_id) references reservation;
alter table reservation add constraint FKdqj45imhck9x1xd2b8l3oi05t foreign key (chambre_id) references chambre;
alter table option_reservee add constraint FKphd4pjc7r8amlu9oneyac84bc foreign key (reservation_id) references reservation;
alter table option_reservee add constraint FKkbhipal07fe7qothcqfeytmyk foreign key (option_id) references option_reservation;
alter table reservation add constraint FKql8rxeq017b2nx6iw8wvc6sm3 foreign key (formule_id) references formule;
alter table paiement add constraint FKsy12rhsevct14mw63dr63rp3v foreign key (moyen_de_paiement_id) references moyen_de_paiement;
alter table paiement add constraint FK4niq4oqae97gwkjoyq4bkhlv0 foreign key (reservation_id) references reservation;
alter table facture add constraint FKnpyc9g5mq3hgtexo0yfvcgj4e foreign key (reservation_id) references reservation;
