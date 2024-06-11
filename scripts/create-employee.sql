USE [ProjectDatabaseS2G4]
GO
/****** Object:  StoredProcedure [dbo].[CreateUserEmployee]    Script Date: 4/25/2024 3:22:53 PM ******/
CREATE PROCEDURE [dbo].[CreateUserEmployee] @Username VARCHAR(64), @Salt VARCHAR(64), @PasswordHash VARCHAR(64), @FirstName VARCHAR(64), @LastName VARCHAR(64)
AS
	-- Make sure fields aren't blank
	IF @Username IS NULL OR @Salt IS NULL OR @PasswordHash IS NULL OR @LastName IS NULL OR @LastName = '' OR @Username = '' OR @Salt = '' OR @PasswordHash = '' BEGIN
		RAISERROR('Error: Fields cant be blank', 16, 0)
		RETURN 1
END
	-- Check if username is taken
	IF EXISTS(SELECT * FROM Users WHERE Username = @Username) BEGIN
		RAISERROR('Error: Username already taken', 16, 0)
		RETURN 1
END
ELSE BEGIN
		-- Create a User
		INSERT INTO Users (Username, Salt, PasswordHash)
		VALUES(@Username, @Salt, @PasswordHash)

		-- Grab the ID that was generated for the User
		DECLARE @ID INT
SELECT @ID = ID
FROM Users
WHERE Username = @Username

      -- Add an Employee with that ID
    INSERT INTO Employee (ID, Tokens, FirstName, LastName)
VALUES (@ID, 0, @FirstName, @LastName)
    RETURN 0
END
