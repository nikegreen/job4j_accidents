CREATE TABLE _rule (
  id serial primary key,
  name text
);
comment on table _rule is 'Список всех пунктов правил';
comment on column _rule.id is 'идентификатор пункта правил';
comment on column _rule.name is 'название пункта правил';