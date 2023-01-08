--Таблицы
CREATE TABLE Article
(
    ID          SERIAL PRIMARY KEY,
    ArticleName TEXT NOT NULL
);

CREATE TABLE Section
(
    ID         SERIAL PRIMARY KEY,
    Article_ID INTEGER REFERENCES article ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
    SectionOrder INTEGER,
    NewestText TEXT DEFAULT NULL,
    LatestDate TIMESTAMP DEFAULT current_timestamp
);

CREATE TABLE Version
(
    ID         SERIAL PRIMARY KEY,
    Section_ID INTEGER REFERENCES section ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
    SectionText TEXT,
    DateEdited TIMESTAMP NOT NULL DEFAULT current_timestamp,
    PersonEdited TEXT,
    IPEdited TEXT,
    Status TEXT DEFAULT 'Ожидает проверки'
);

--Индексы
CREATE INDEX Section_idx ON "section" USING hash("article_id");

CREATE INDEX Version_idx ON "version" USING hash("section_id");
