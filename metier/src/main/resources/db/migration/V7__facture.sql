create table Facture (id bigint GENERATED BY DEFAULT AS IDENTITY, numero BIGINT, pdf BLOB(100M), reservation_id bigint, primary key (id));
alter table facture add constraint FKnpyc9g5mq3hgtexo0yfvcgj4e foreign key (reservation_id) references reservation;