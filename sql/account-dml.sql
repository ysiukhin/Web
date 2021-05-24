SELECT id, first_name, last_name, middle_name, email, md5, status
FROM account;
-- SELECT id, first_name, last_name, middle_name, email, md5, status FROM account WHERE id = ?;
-- INSERT INTO account (first_name, last_name, middle_name, email, md5, status) VALUES (?, ?, ?, ?, ?, ?);
-- UPDATE account SET first_name = ?, last_name = ?, middle_name = ?, email = ?, md5 = ?, status = ? WHERE id = ?;

-- INSERT INTO account (id, first_name, last_name, middle_name, email, md5, status) VALUES (0, 'administrator', 'администратор', '', 'admin@timecounter.com', 'qwerty', 1);