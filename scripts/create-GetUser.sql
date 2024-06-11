USE ProjectDatabaseS2G4
GO
CREATE PROCEDURE GetUser @Username VARCHAR(64), @Salt VARCHAR(64) OUTPUT, @PasswordHash VARCHAR(64) OUTPUT
AS
SELECT @Salt = Salt, @PasswordHash = PasswordHash
FROM Users
WHERE Username = @Username