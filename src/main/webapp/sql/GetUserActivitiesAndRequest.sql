CREATE PROCEDURE `GetUserActivitiesAndRequest`(IN accountId INT, IN lim INT, IN offs INT)
BEGIN
    SELECT at.id,
           kind_en,
           kind_ru,
           activity_en,
           activity_ru,
           ifnull(un.account_activity_id, 0) account_activity_id,
           ifnull(un.request_id, 0)          request_id
    FROM kind ki
             INNER JOIN activity at ON ki.id = at.kind_id
             LEFT JOIN (
        SELECT ac.id account_id, aa.activity_id activity_id, aa.id account_activity_id, rq.id request_id
        FROM account ac
                 INNER JOIN account_activity aa ON ac.id = aa.account_id
                 LEFT JOIN request rq ON
            (ac.id = rq.account_id AND aa.activity_id = rq.activity_id)
        WHERE ac.id = accountId
        UNION ALL
        SELECT ac.id account_id, rq.activity_id activity_id, aa.id account_activity_id, rq.id request_id
        FROM account ac
                 INNER JOIN request rq ON ac.id = rq.account_id
                 LEFT JOIN account_activity aa ON (ac.id = aa.account_id AND rq.activity_id = aa.activity_id)
        WHERE ac.id = accountId
          AND aa.id IS NULL
    ) un ON at.id = un.activity_id
    LIMIT lim OFFSET offs;
END