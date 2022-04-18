-- drop table public.entry;
-- drop table public.holiday;
-- drop table public.holiday_type;
-- drop table public.description_changer;
-- drop table public.category_rename;
-- drop table public.category;
-- drop table public.user;


CREATE TABLE IF NOT EXISTS public.user
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(50) NOT NULL,
    email    VARCHAR(50) UNIQUE,
    role     VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS public.category
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(50) NOT NULL,
    user_id INTEGER NOT NULL,
    foreign key (user_id) references public.user (id)
);


CREATE TABLE IF NOT EXISTS public.holiday_type
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(50) UNIQUE,
    category_id INTEGER NOT NULL,
    foreign key (category_id) references public.category (id)
);

CREATE TABLE IF NOT EXISTS public.holiday
(
    id              SERIAL PRIMARY KEY,
    date_start      DATE NOT NULL,
    date_end        DATE NOT NULL,
    user_id         INTEGER NOT NULL,
    holiday_type_id INTEGER NOT NULL,
    foreign key (user_id) references public.user (id),
    foreign key (holiday_type_id) references public.holiday_type (id)
);


CREATE TABLE IF NOT EXISTS public.entry
(
    id               SERIAL PRIMARY KEY,
    amount           DECIMAL NOT NULL,
    description      VARCHAR(100) NOT NULL,
    entry_date       TIMESTAMP NOT NULL,
    number_operation INTEGER NOT NULL,
    category_id      INTEGER NOT NULL,
    user_id          INTEGER NOT NULL,
    foreign key (user_id) references public.user (id),
    foreign key (category_id) references public.category (id)
);

CREATE TABLE IF NOT EXISTS public.category_rename
(
    id                 SERIAL PRIMARY KEY,
    category_before_id INTEGER NOT NULL,
    category_after_id  INTEGER NOT NULL,
    user_id            INTEGER NOT NULL,
    foreign key (user_id) references public.user (id),
    foreign key (category_before_id) references public.category (id),
    foreign key (category_after_id) references public.category (id)
);

CREATE TABLE IF NOT EXISTS public.description_changer
(
    id                  SERIAL PRIMARY KEY,
    description_pattern VARCHAR(100) NOT NULL,
    category_id         INTEGER NOT NULL,
    user_id             INTEGER NOT NULL,
    foreign key (user_id) references public.user (id),
    foreign key (category_id) references public.category (id)
);