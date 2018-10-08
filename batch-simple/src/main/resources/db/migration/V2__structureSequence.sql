create sequence hibernate_sequence start with 1 increment by 1;
create table ADRESSE (id bigint, code_postal varchar(255), rue varchar(255), ville varchar(255), primary key (id));
create table CLIENT (id bigint, date_creation timestamp, nom varchar(255), adresse_id bigint, primary key (id));
alter table CLIENT add constraint UK_bfjdoy2dpussylq7g1s3s1tn8 unique (adresse_id);
alter table CLIENT add constraint FK7asddrxkdd0qh4wbpsin3nngb foreign key (adresse_id) references adresse;