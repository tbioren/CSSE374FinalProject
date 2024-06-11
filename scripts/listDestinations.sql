USE ProjectDatabaseS2G4
Go
Create Procedure ListDestinations
AS
SELECT DISTINCT IATACode, Name
From Destination
Where IsVisible = 1
