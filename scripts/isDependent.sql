CREATE PROCEDURE isDependent @Username VARCHAR(64)
AS
SELECT * FROM Dependent JOIN USERS ON Dependent.ID = USERS.ID WHERE USERS.Username = @Username
GO