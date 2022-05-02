insert into users (username, password, email, role)
values ('abl', 'pass', 'test@ya.ru', 'ADMIN');
insert into users (username, password, email, role)
values ('petr', 'pass', 'test2@ya.ru', 'ADMIN');
insert into category (name, user_id)
values ('vacation', 1);
insert into category (name, user_id)
values ('car', 1);
insert into category (name, user_id)
values ('food', 1);
insert into holiday_type (name, category_id, user_id)
values ('отпуск на море', 1, 1);
insert into holiday_type (name, category_id, user_id)
values ('отпуск в деревне', 1, 1);
insert into holiday (start_date, end_date, user_id, holiday_type_id)
values ('2020-01-01', '2021-01-01', 1, 1);
insert into category_rename (category_before_id, category_after_id, user_id)
values (1, 2, 1);
insert into description_changer (category_id, user_id, description_pattern)
values (1, 1, 'some_text');
insert into entry (amount, description, date, operation_number, category_id, user_id)
values (1000, 'some_text', '2020-01-01 00:00:00', 1, 1, 1);
insert into entry (amount, description, date, operation_number, category_id, user_id)
values (2000, 'some_text', '2021-01-01 00:00:00', 2, 1, 1);
insert into entry (amount, description, date, operation_number, category_id, user_id)
values (2000, 'some_text', '2020-12-01 00:00:00', 3, 1, 1);