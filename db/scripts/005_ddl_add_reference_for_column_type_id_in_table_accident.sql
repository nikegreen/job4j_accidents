ALTER TABLE accident DROP COLUMN type_id;
ALTER TABLE accident ADD COLUMN type_id integer REFERENCES accident_type (id);
UPDATE accident SET type_id = 1 WHERE type_id IS NULL;