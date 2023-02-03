CREATE TABLE accident_type (
  id serial primary key,
  name text
);
comment on table accident is 'Список всех типов происшествий по составу и количесву учатников';
comment on column accident.id is 'идентификатор типа происшествия';
comment on column accident.name is 'описание типа происшествия';
