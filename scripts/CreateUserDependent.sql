USE [ProjectDatabaseS2G4]
GO

/****** Object:  StoredProcedure [dbo].[CreateUserDependent]    Script Date: 5/2/2024 2:46:15 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


CREATE PROCEDURE [dbo].[CreateUserDependent] @Username VARCHAR(64), @Salt VARCHAR(64), @PasswordHash VARCHAR(64), @FirstName VARCHAR(64), @LastName VARCHAR(64), @EmployeeUsername VARCHAR(64), @AddCode INT
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
	IF @Username IS NULL OR @Salt IS NULL OR @PasswordHash IS NULL OR @Username = '' OR @Salt = '' OR @PasswordHash = '' BEGIN
		RAISERROR('Error: Fields cant be blank', 16, 0)
		RETURN 1
END
	-- Make sure the add code is right
	IF NOT EXISTS (SELECT * FROM USERS WHERE Username = @EmployeeUsername AND ID = @AddCode) BEGIN
		RAISERROR('Error: Add code is incorrect', 16, 0);
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
GO


