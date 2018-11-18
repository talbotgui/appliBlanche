create table lien_role_ressource (ressource_clef varchar(255) not null, role_nom varchar(255) not null, primary key (role_nom, ressource_clef));
create table lien_utilisateur_role (utilisateur_login varchar(255) not null, role_nom varchar(255) not null, primary key (utilisateur_login, role_nom));
create table ressource (clef varchar(255) not null, chemin varchar(100), description varchar(255), primary key (clef));
create table role (nom varchar(255) not null, primary key (nom));
create table utilisateur (login varchar(255) not null, mdp varchar(255), premier_echec timestamp, second_echec timestamp, troisieme_echec timestamp, primary key (login));
alter table lien_role_ressource add constraint FKeq3qlsj4hi17tyx3myd1frbvc foreign key (ressource_clef) references ressource(clef);
alter table lien_role_ressource add constraint FKaoafu0eqls0iu2xmooeinhrou foreign key (role_nom) references role;
alter table lien_utilisateur_role add constraint FKgyh7o9go7ryk79q547je1l3uh foreign key (utilisateur_login) references utilisateur;
alter table lien_utilisateur_role add constraint FKq6r2ku62k7wq6pw8aktc39nyo foreign key (role_nom) references role;
