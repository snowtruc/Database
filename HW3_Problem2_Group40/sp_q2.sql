GO
CREATE PROCEDURE sp_q2
    @pname VARCHAR(64),
    @age INT,
    @did INT
AS
BEGIN
    DECLARE @years_experience int;
    --Number of Performers who have acted in a movie that was directed by a director with a given did
    DECLARE @num_performers int;
    DECLARE @avg_experience int;
    SELECT 
        @num_performers = COUNT(*),
        @avg_experience = AVG(years_exp)
    FROM (
        --Subquery to get years of experience for all distinct actors that were in a movie directed by
        -- given director id
        SELECT AVG(years_of_experience) AS years_exp
        FROM performer p
        LEFT JOIN acted a
            on p.pid = a.pid
        INNER JOIN movie m
            on a.m_name = m.m_name
        WHERE did = @did
        GROUP BY p.pid
    ) matches;

    IF @num_performers > 0
    BEGIN
        --At least one performer in age range
        SET @years_experience = @avg_experience;
    END
    ELSE
    BEGIN
        -- No performers within range
        SET @years_experience = @age - 18;
    END

    --Keep years of experience at least 0 and at most their age
    IF @years_experience < 0
    BEGIN
        SET @years_experience = 0;
    END
    IF @years_experience > @age
    BEGIN
        SET @years_experience = @age;
    END

    INSERT INTO performer
        (p_name, years_of_experience, age)
    VALUEs
        (@pname, @years_experience, @age)
END