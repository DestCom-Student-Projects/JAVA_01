create table fichier
(
    id   integer      not null
        constraint id
            primary key,
    nom  varchar(255) not null,
    type varchar(5)   not null
);

create table ligne
(
    id         integer not null
        constraint ligne_pk
            primary key,
    param1     integer not null,
    param2     integer not null,
    operateur  char,
    position   integer not null,
    fichier_id integer not null
        constraint ligne_fichier__fk
            references fichier,
    constraint ligne_uk_2
        unique (fichier_id, position)
);

INSERT INTO FICHIER
VALUES (1, 'exemple', 'OP');
INSERT INTO FICHIER
VALUES (2, 'invalide', 'TXT');
INSERT INTO FICHIER
VALUES (3, 'operations', 'OP');

INSERT INTO LIGNE
VALUES (1, 3, 6, '+', 1, 1);
INSERT INTO LIGNE
VALUES (2, 3, 5, '*', 3, 1);
INSERT INTO LIGNE
VALUES (3, 3, 5, '-', 2, 1);

INSERT INTO LIGNE
VALUES (4, 0, 0, NULL, 1, 2);

INSERT INTO LIGNE
VALUES (5, 1, 2, '+', 3, 3);
INSERT INTO LIGNE
VALUES (6, 1, 2, '^', 1, 3);
INSERT INTO LIGNE
VALUES (7, 2, 2, '*', 2, 3);

COMMIT;

