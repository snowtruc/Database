GO
CREATE PROCEDURE sp_q1
    @pname VARCHAR(64),
    @age INT
AS
BEGIN
    DECLARE @years_experience int;
    --Number of Performers +- 10 years from this performer's age
    DECLARE @num_close_performers int;
    DECLARE @avg_experience int;
    SELECT 
        @num_close_performers = COUNT(*),
        @avg_experience = AVG(years_of_experience)
    FROM performer
    WHERE age BETWEEN @age - 10 and @age + 10

    IF @num_close_performers > 0
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