ALTER TABLE accident_rule DROP COLUMN rule_id;
ALTER TABLE accident_rule ADD COLUMN rule_id integer REFERENCES _rule (id);
UPDATE accident_rule SET rule_id = 1 WHERE rule_id IS NULL;
comment on column accident_rule.rule_id is 'идентификатор статьи правил дорожного движения из списка rules';
