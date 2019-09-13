insert into privilege values
  (1, 'Управление администраторами', 'manage_admins'),
  (2, 'Управление менеджерами', 'manage_managers'),
  (3, 'Управление пользователями', 'manage_users'),
  (4, 'Управление cвоими записями', 'manage_own_records'),
  (5, 'Управление чужими записями', 'manage_other_records'),
  (6, 'Создание пользователя', 'create_user')
;

insert into role values
  (1, 'Администратор', 'admin'), (2, 'Менеджер', 'manager'), (3, 'Пользователь', 'user'), (4, 'Никто', 'nobody');

insert into role_privilege
select admin_id, priv_id
from (select id priv_id from privilege) tmp1
join lateral (select id admin_id from role where alias = 'admin') tmp2 on true

union all

select manager_id, priv_id
from (select id priv_id from privilege where alias in ('manage_managers', 'manage_users')) tmp1
join lateral (select id manager_id from role where alias = 'manager') tmp2 on true

union all

select user_id, priv_id
from (select id priv_id from privilege where alias in ('manage_own_records')) tmp1
join lateral (select id user_id from role where alias = 'user') tmp2 on true

union all

select nobody_id, priv_id
from (select id priv_id from privilege where alias in ('create_user')) tmp1
join lateral (select id nobody_id from role where alias = 'nobody') tmp2 on true
;