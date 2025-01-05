CREATE TABLE MyCharacter (
    id TINYINT UNSIGNED PRIMARY KEY NOT NULL,
    nom VARCHAR(30),
    joursFaim TINYINT UNSIGNED,
    joursSoif TINYINT UNSIGNED
);

CREATE TABLE MyItem (
    id TINYINT UNSIGNED PRIMARY KEY NOT NULL,
    nom VARCHAR(30)
);

CREATE TABLE MyGame (
    id TINYINT UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    jour TINYINT UNSIGNED,
    numSoupe TINYINT UNSIGNED,
    numEau TINYINT UNSIGNED
);
