CREATE TABLE IF NOT EXISTS public.users  (
                         id INTEGER PRIMARY KEY,
                         username VARCHAR(50),
                         password VARCHAR(50),
                         email VARCHAR(50),
                         role VARCHAR(50)
    );

CREATE TABLE IF NOT EXISTS public.categories (
                                                 id INTEGER PRIMARY KEY,
                                                 name VARCHAR(50),
                                                 user_id INTEGER,
                                                 foreign key (user_id) references public.users(id)
);


CREATE TABLE IF NOT EXISTS public.holidays_type (
                               id INTEGER PRIMARY KEY,
                               name VARCHAR(50),
                               category_id INTEGER,
                               foreign key (category_id) references public.categories(id)
    );

CREATE TABLE IF NOT EXISTS public.holidays (
                            id INTEGER PRIMARY KEY,
                            date_start DATE,
                            date_end DATE,
                            user_id INTEGER,
                            holiday_type_id INTEGER,
                            foreign key (user_id) references public.users(id),
                            foreign key (holiday_type_id) references public.holidays_type(id)
    );


CREATE TABLE IF NOT EXISTS public.entries (
                         id INTEGER PRIMARY KEY,
                         amount DECIMAL,
                         description VARCHAR,
                         entry_date TIMESTAMP,
                         number_operation INTEGER,
                         category_id INTEGER,
                         user_id INTEGER,
                         foreign key (user_id) references public.users(id),
                         foreign key (category_id) references public.categories(id)
    );

CREATE TABLE IF NOT EXISTS public.categories_rename (
                                     id INTEGER PRIMARY KEY,
                                     category_before_id INTEGER,
                                     category_after_id INTEGER,
                                     user_id INTEGER,
                                     foreign key (user_id) references public.users(id),
                                     foreign key (category_before_id) references public.categories(id),
                                     foreign key (category_after_id) references public.categories(id)
    );

CREATE TABLE IF NOT EXISTS public.description_changers (
                                        id INTEGER PRIMARY KEY,
                                        description_pattern VARCHAR(100),
                                        category_id INTEGER,
                                        user_id INTEGER,
                                        foreign key (user_id) references public.users(id),
                                        foreign key (category_id) references public.categories(id)
    );