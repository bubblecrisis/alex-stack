
create table paymee.registered_interest (
  id                        bigserial not null,
  email                     varchar(255) not null,
  ip_address                varchar(255) not null,
  registered                timestamp
  constraint pk_registered_interest primary key (id))
;
