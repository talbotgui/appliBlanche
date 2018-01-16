create table adresse (id bigint generated by default as identity (start with 1), code_postal varchar(255), rue varchar(255), ville varchar(255), primary key (id));
create table client (id bigint generated by default as identity (start with 1), date_creation timestamp, nom varchar(255), client_id bigint, primary key (id));
create table demande (id bigint generated by default as identity (start with 1), description_courte varchar(255), description_longue varchar(255), dossier_id bigint, primary key (id));
create table dossier (id bigint generated by default as identity (start with 1), date_creation timestamp, nom varchar(255), client_id bigint, primary key (id));
create table lien_role_ressource (ressource_clef varchar(255) not null, role_nom varchar(255) not null, primary key (role_nom, ressource_clef));
create table lien_utilisateur_role (utilisateur_login varchar(255) not null, role_nom varchar(255) not null, primary key (utilisateur_login, role_nom));
create table ressource (clef varchar(255) not null, description varchar(255), primary key (clef));
create table role (nom varchar(255) not null, primary key (nom));
create table utilisateur (login varchar(255) not null, mdp varchar(255), premier_echec timestamp, second_echec timestamp, troisieme_echec timestamp, primary key (login));
alter table client add constraint UK_bfjdoy2dpussylq7g1s3s1tn8 unique (client_id);
alter table client add constraint FK7asddrxkdd0qh4wbpsin3nngb foreign key (client_id) references adresse;
alter table demande add constraint FKkjo0b7iv3durxywr03yd3454k foreign key (dossier_id) references dossier;
alter table dossier add constraint FKpu2xjfb2nd4x94oy6wieqvoam foreign key (client_id) references client;
alter table lien_role_ressource add constraint FKeq3qlsj4hi17tyx3myd1frbvc foreign key (ressource_clef) references ressource;
alter table lien_role_ressource add constraint FKaoafu0eqls0iu2xmooeinhrou foreign key (role_nom) references role;
alter table lien_utilisateur_role add constraint FKgyh7o9go7ryk79q547je1l3uh foreign key (utilisateur_login) references utilisateur;
alter table lien_utilisateur_role add constraint FKq6r2ku62k7wq6pw8aktc39nyo foreign key (role_nom) references role;