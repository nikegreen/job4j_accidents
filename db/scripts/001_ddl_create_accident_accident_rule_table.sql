CREATE TABLE accident (
  id serial primary key,
  name text,
  _text text,
  address text,
  type_id integer,
  status integer
);
comment on table accident is 'Список всех происшествий';
comment on column accident.id is 'идентификатор происшествия';
comment on column accident.name is 'Имя сообщившего о происшествии';
comment on column accident._text is 'Описание подробностей происшествия';
comment on column accident.type_id is 'Тип происшествия по составу и количесву учатников';
comment on column accident.status is 'статус выполнения задачи: принято, выполнено, отказано';

CREATE TABLE accident_rule (
  id serial primary key,
  accident_id integer REFERENCES accident (id),
  rule_id integer
);
comment on table accident_rule is 'Связь списка всех происшествий с выбранными статьями правил';
comment on column accident_rule.id is 'идентификатор связи';
comment on column accident_rule.accident_id is 'идентификатор происшествия из таблицы accident';
comment on column accident_rule.rule_id is 'идентификатор статьи правил дорожного движения из списка rules';
