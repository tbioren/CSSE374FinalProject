USE ProjectDatabaseS2G4
GO
CREATE PROCEDURE CreateDestination (
	@IATACode CHAR(3),
	@Name VARCHAR(32)
)
AS
	IF @IATACode IS NULL OR @IATACode = '' BEGIN
		RAISERROR('IATA code cannot be null', 14, 1);
		RETURN 1
	END
	IF NOT EXISTS(SELECT * FROM Destination WHERE IATACode = @IATACode) BEGIN
		INSERT INTO Destination(IATACode, Name, IsVisible)
		VALUES (@IATACode, @Name, 1)
		RETURN 0
	END
	ELSE BEGIN
	     Update Destination	
	     Set Name = @Name
	     Where IATACode = @IATACode
	END
GO
