CREATE PROCEDURE RemoveDependent @Username VARCHAR(64) AS
DECLARE @ID INT
SELECT @ID = Users.ID FROM Dependent JOIN Users ON Dependent.ID = Users.ID WHERE Users.Username = @Username

UPDATE Dependent
SET isDeleted = 1
WHERE ID = @ID
GO