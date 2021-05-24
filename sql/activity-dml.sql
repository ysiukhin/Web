-- SELECT id, project_name, project_desc, status kind_id FROM activity;
SELECT id, project_name, project_desc, status kind_id
FROM activity
where id = ?;
-- SELECT id, kind_ru, kind_en FROM kind where id = ?;
-- INSERT INTO kind (kind_ru, kind_en) VALUES (?, ?);
-- UPDATE kind SET kind_ru = ?, kind_en = ? WHERE id = ?;