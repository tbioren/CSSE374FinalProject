CREATE PROCEDURE [dbo].[CreateUserDependent] @Username VARCHAR(64), @Salt VARCHAR(64), @PasswordHash VARCHAR(64), @FirstName VARCHAR(64), @LastName VARCHAR(64), @EmployeeUsername VARCHAR(64)
AS
	-- Check if username is taken
	IF EXISTS(SELECT * FROM Users WHERE Username = @Username) BEGIN
		RAISERROR('Error: Username already taken', 16, 0)
		RETURN 1
END
	IF NOT EXISTS(SELECT * FROM Employee LEFT JOIN Users ON Employee.ID = Users.ID WHERE Users.Username = @EmployeeUsername) BEGIN
		RAISERROR('Error: No employee with that username!', 16, 0)
		RETURN 1
END
	-- Make sure fields aren't blank
	IF @Username IS NULL OR @Salt IS NULL OR @PasswordHash IS NULL OR @LastName IS NULL OR @LastName = '' OR @Username = '' OR @Salt = '' OR @PasswordHash = '' BEGIN
		RAISERROR('Error: Fields cant be blank', 16, 0)
		RETURN 1
END
ELSE BEGIN
		-- Create a User
		INSERT INTO Users (Username, Salt, PasswordHash)
		VALUES(@Username, @Salt, @PasswordHash)

		-- Grab the ID that was generated for the User
		DECLARE @DependentID INT
SELECT @DependentID = ID
FROM Users
WHERE Username = @Username

-- Get Employee's ID
DECLARE @EmployeeID INT
SELECT @EmployeeID = Users.ID
FROM Employee LEFT JOIN Users ON Employee.ID = Users.ID
WHERE Users.Username = @EmployeeUsername

      -- Create a new Dependent
    INSERT INTO Dependent (ID, FirstName, LastName, EmployeeID)
VALUES (@DependentID, @FirstName, @LastName, @EmployeeID)
END
