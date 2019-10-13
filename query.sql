DROP TABLE performer;
DROP TABLE movie;
DROP TABLE acted;
DROP TABLE director;

-- Create the new table for performer
CREATE TABLE performer (
        pid INT IDENTITY(1,1) PRIMARY KEY, -- IDENTITY(1, 1) auto-increments the id 
        p_name VARCHAR(64) NOT NULL,
        years_of_experience INT NOT NULL,
        age INT NOT NULL,
    CONSTRAINT Chk_non_empty_performer CHECK( -- Make sure provided strings are not empty 
        LEN(p_name) > 0
    )
);

--Insert data into performer table
INSERT INTO performer
    (p_name, years_of_experience, age)
VALUES
    ('Morgan', '48', '67'),
    ('Cruz', '14', '28'),
    ('Adams', '1', '16'),
    ('Perry', '18', '32'),
    ('Hanks', '36', '55'),
    ('Hanks', '15', '24'),
    ('Lewis', '13', '32');

-- Create the new table for movie
CREATE TABLE movie (
        m_name VARCHAR(64) PRIMARY KEY NOT NULL,
        genre VARCHAR(64) NOT NULL,
        min INT NOT NULL,
        release_year INT NOT NULL,
        did INT NOT NULL,
    CONSTRAINT Chk_non_empty_movie CHECK( -- Make sure provided strings are not empty 
        LEN(m_name) > 0 AND
        LEN(genre) > 0
    )
);

--Insert data into movie table
INSERT INTO movie
    (m_name, genre, min, release_year, did)
VALUES
    ('Jurassic Park', 'Action', '125', '1984', '2'),
    ('Shawshank Redemption', 'Drama', '105', '2001', '2'),
    ('Fight Club', 'Drama', '144', '2015', '2'),
    ('The Departed', 'Drama', '130', '1969', '3'),
    ('Back to the Future', 'Comedy', '89', '2008', '3'),
    ('The Lion King', 'Animation', '97', '1990', '1'),
    ('Alien', 'Sci-Fi', '115', '2006', '3'),
    ('Toy Story', 'Animation', '104', '1978', '1'),
    ('Scarface', 'Drama', '124', '2003', '1'),
    ('Up', 'Animation', '111', '1999', '4');

-- Create the new table for acted
CREATE TABLE acted (
        pid INT,
        m_name VARCHAR(64) NOT NULL,
    CONSTRAINT Chk_non_empty_acted CHECK( -- Make sure provided strings are not empty 
        LEN(m_name) > 0
    )
);

--Insert data into acted table
INSERT INTO acted
    (pid, m_name)
VALUES
    ('4', 'Fight Club'),
    ('5', 'Fight Club'),
    ('6', 'Shawshank Redemption'),
    ('4', 'Up'),
    ('5', 'Shawshank Redemption'),
    ('1', 'The Departed'),
    ('2', 'Fight Club'),
    ('3', 'Fight Club'),
    ('4', 'Alien');

-- Create the new table for director
CREATE TABLE director (
        did INT IDENTITY(1,1) PRIMARY KEY, -- IDENTITY(1, 1) auto-increments the id 
        d_name VARCHAR(64) NOT NULL,
        earnings REAL NOT NULL,
    CONSTRAINT Chk_non_empty_director CHECK( -- Make sure provided strings are not empty 
        LEN(d_name) > 0
    )
);

--Insert data into director table
INSERT INTO director
    (d_name, earnings)
VALUES
    ('Parker', '580000'),
    ('Black', '2500000'),
    ('Black', '30000'),
    ('Stone', '820000');

-- List all the records in the performer, movie, acted, and director tables
SELECT * FROM performer;
SELECT * FROM movie;
SELECT * FROM acted;
SELECT * FROM director;

-- Find the names of all Action movies
SELECT * FROM movie WHERE genre = 'Action';

-- Find the average length for Drama
SELECT AVG(min) FROM movie WHERE genre = 'Drama';

-- Find the average length for Action
SELECT AVG(min) FROM movie WHERE genre = 'Action';

-- Find the average length for Action
SELECT AVG(min) FROM movie WHERE genre = 'Comedy';

-- Find the average length for Action
SELECT AVG(min) FROM movie WHERE genre = 'Animation';

-- Find the names of all performers with at least 20 years
-- of experience who have acted in a movie directed by Black
SELECT p_name FROM performer WHERE pid IN (SELECT pid FROM acted WHERE m_name IN
(SELECT m_name FROM movie WHERE did IN (SELECT did FROM director WHERE d_name = 'Black'))) AND years_of_experience >= '20';

-- 5. 
SELECT MAX(age) FROM performer WHERE p_name = 'Hanks' OR pid IN
(SELECT pid FROM acted WHERE m_name = 'The Departed');

-- Decrease the earnings of all directors who directed
-- "Up" by 10%
UPDATE director SET earnings = earnings * 0.90 WHERE did IN (SELECT did FROM movie WHERE m_name = 'Up');
SELECT * FROM director ;

-- Delete all movies released in the 70's and 80's.
--DELETE FROM movie WHERE release_year >= '1970' AND release_year <= '1989';
--SELECT * FROM movie;