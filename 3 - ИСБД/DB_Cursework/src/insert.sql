--Заполнение всех таблиц БД тестовыми данными

INSERT INTO "Квартал" ("id", "Название")
VALUES (1, 'Манхеттен'),
       (2, 'Бронкс'),
       (3, 'Куинс'),
       (4, 'Бруклин'),
       (5, 'Стэттен-Айланд'),
       (6, 'Джерси-Сити');

INSERT INTO "Квартал_Квартал" ("Квартал1_id", "Квартал2_id")
VALUES (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (2, 3),
       (3, 4),
       (4, 5),
       (5, 6);

INSERT INTO "Улица" ("id", "Имя", "Квартал_id")
VALUES (1, 'Бродвей', 1),
       (2, 'Фалтон Стрит', 1),
       (3, 'Парк Авеню', 1),
       (4, 'Лафаетт Авеню', 2),
       (5, 'Дайер Авеню', 2),
       (6, 'Брон Ривер', 2),
       (7, 'Роквей Авеню', 3),
       (8, 'Куинс Бульвар', 3),
       (9, 'Улица Сергея Довлатова', 3),
       (10, 'Бульвар Роквей', 4),
       (11, 'Белт-Паркуэй', 4),
       (12, 'Променад Ригельмана', 4),
       (13, 'Ошен Террас', 5),
       (14, 'Клов Роуд', 5),
       (15, 'Бойл Плаза', 6),
       (16, 'Палисейд Авеню', 6);

INSERT INTO "Улица_Улица" ("Улица1_id", "Улица2_id")
VALUES (1, 2),
       (1, 3),
       (6, 4),
       (6, 5),
       (8, 7),
       (8, 9),
       (10, 11),
       (10, 12),
       (13, 14),
       (15, 16);
INSERT INTO "Комитет_сдачи_объектов" ("id", "Строгость")
VALUES (1, 0),
       (2, 2),
       (3, 4),
       (4, 6),
       (5, 7),
       (6, 8),
       (7, 9);

INSERT INTO "Городская_служба" ("id", "Тип", "Цена")
VALUES (1, 'Газоснабжение', 0.1),
       (2, 'Водоснабжение', 0.5),
       (3, 'Горячее водоснабжение', 2),
       (4, 'Электричество', 0.01);

INSERT INTO "Строй_бригада" ("id", "Размер")
VALUES (1, 56),
       (2, 8),
       (3, 24),
       (4, 88);

INSERT INTO "Маршрут" ("id", "Тип")
VALUES (1, 'Трамвай'),
       (2, 'Трамвай'),
       (3, 'Автобус'),
       (4, 'Метро'),
       (5, 'Автобус'),
       (6, 'Троллейбус');

INSERT INTO "Обслуживающая_команда" ("id", "Тариф", "Квартал_id", "Служба_id")
VALUES (1, 200, 1, 1),
       (2, 100, 1, 2),
       (3, 375, 1, 3),
       (4, 99.99, 1, 4),
       (5, 75, 2, 2),
       (6, 85, 2, 4),
       (7, 80, 3, 1),
       (8, 80, 3, 3),
       (9, 90, 3, 4),
       (10, 45, 4, 2),
       (11, 50, 4, 3),
       (12, 60, 4, 4),
       (13, 115, 5, 1),
       (14, 125, 5, 2),
       (15, 130, 5, 3);

INSERT INTO "Стройматериал" ("id", "Тип", "Количество", "Цена")
VALUES (1, 'Бетон', 107564, 5000),
       (2, 'Стекло', 8640, 22000),
       (3, 'Сталь', 12346, 14400),
       (4, 'Алюминий', 4592, 7900),
       (5, 'Песок', 24830, 3600),
       (6, 'Мрамор', 431, 85600);

INSERT INTO "Служба_доставки" ("id", "Название", "Тариф", "Стройматериал_id")
VALUES (1, 'Доставкин', 200, 1),
       (2, 'Шевлись, Плотва!', 350, 3),
       (3, 'Мрамор в каждый дом', 500, 6),
       (4, 'На крыльях ночи', 300, 2),
       (5, 'Быстрее ветра', 270, 4),
       (6, 'СанСаныч', 265, 5);

INSERT INTO "Служба_доставки_Строй_бригада" ("Служба_доставки_id", "Строй_бригада_id")
VALUES (1, 1),
       (3, 1),
       (6, 2),
       (2, 1),
       (1, 3),
       (2, 3),
       (3, 4);

INSERT INTO "Маршрут_Кварталы" ("Маршрут_id", "Квартал_Отправки", "Квартал_Назначения")
VALUES (1, 1, 1),
       (2, 1, 2),
       (3, 3, 5),
       (4, 4, 1),
       (5, 2, 3),
       (6, 5, 4);

INSERT INTO "Маршрут_Улица" ("Маршрут_id", "Улица_id")
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (3, 7),
       (3, 9),
       (3, 14),
       (3, 13),
       (3, 15);

INSERT INTO "Здание" ("id", "Тип", "Назание", "Этажность", "Улица_id", "Комитет_id", "Бригада_id")
VALUES (1, 'Отель', 'Gilsey House', 6, 10, 5, 1),
       (2, 'Музей', 'Metropolitan', 2, 11, 6, 3),
       (3, 'Церковь', 'Собор Святого Патрика', 1, 3, 6, 4),
       (4, 'Отель', 'Ansonia', 11, 4, 7, 1),
       (5, 'Коммерция', 'Chrysler Building', 89, 2, 1, 4),
       (6, 'Жилой дом', 'Apthorp', 20, 5, 7, 4),
       (7, 'Жилой дом', 'Chanin Building', 100, 6, 7, 3),
       (8, 'Коммерция', 'Штаб-квартира InterActive', 18, 7, 6, 1),
       (9, 'Коммерция', 'Башня Свободы', 95, 8, 3, 3),
       (10, 'Жилой дом', 'Via Verde', 15, 9, 1, 1),
       (11, 'Аэропорт', 'Аэропорт имени Джона Кеннеди', 3, 12, 6, 4),
       (12, 'Коммерция', 'Empire State Building', 103, 1, 7, 4),
       (13, 'Театр', 'Apollo Theater', 5, 14, 4, 1),
       (14, 'Вокзал', 'Grand Central', 1, 2, 5, 2),
       (15, 'Музей', 'Guggenheim Museum', 6, 15, 4, 1),
       (16, 'Коммерция', 'New York Stock Exchange', 9, 16, 2, 3);

INSERT INTO "Городская_служба_Здание" ("Служба_id", "Здание_id")
VALUES (2, 1),
       (3, 1),
       (4, 1),
       (2, 2),
       (4, 2),
       (4, 3);

INSERT INTO "Стройматериал_Здание" ("Стройматериал_id", "Здание_id", "Количество")
VALUES (6, 4, 250),
       (1, 6, 230),
       (2, 6, 50),
       (3, 6, 135);