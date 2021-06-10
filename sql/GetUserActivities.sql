CREATE PROCEDURE `GetUserActivities`(IN accountId INT, IN lim INT, IN offs INT)
BEGIN
    select aa.id             account_activity_id,
           kind_en,
           kind_ru,
           activity_en,
           activity_ru,
           rec.start         start,
           ifnull(rec.id, 0) record_id
    from account_activity aa
             inner join activity at on aa.activity_id = at.id
             inner join kind ki on at.kind_id = ki.id
             left join record rec on (aa.id = rec.account_activity_id and end is null)
    where aa.account_id = accountId
    order by rec.start desc
    LIMIT lim OFFSET offs;
END