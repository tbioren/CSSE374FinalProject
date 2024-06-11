CREATE PROCEDURE UnRemoveDependent @UserID INT, @Dep VARCHAR(64)
AS
IF NOT EXISTS (SELECT * FROM Dependent JOIN USERS ON Dependent.ID = USERS.ID WHERE USERS.Username = @Dep) BEGIN
	RAISERROR('No Dependent Exists', 16, 1)
	RETURN 1
END

DECLARE @DepID INT
SELECT @DepID = Dependent.ID
FROM Dependent JOIN USERS ON Dependent.ID = USERS.ID
WHERE USERS.Username = @Dep AND Dependent.EmployeeID = @UserID

UPDATE Dependent
SET isDeleted = 0
WHERE ID = @DepID

    RETURN 0

GRANT EXECUTE ON UnRemoveDependent TO MyID90Application