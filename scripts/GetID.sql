CREATE PROCEDURE [dbo].[getID] @Username VARCHAR(64) AS
SELECT ID FROM Users WHERE Username = @Username
    GO