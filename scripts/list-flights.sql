USE ProjectDatabaseS2G4
Go
Create Procedure ListFlights
		@eID int
AS
SELECT DISTINCT f.ID, f.AirlineID, f.Number, f.FromCode, f.ToCode, f.DepartureDateTime, f.Load
From Employee e
Right Join Flight f on e.AirlineID = f.AirlineID
Where f.IsVisible = 1 and e.ID = @eID
