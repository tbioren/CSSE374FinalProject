USE ProjectDatabaseS2G4
Go
Alter Procedure GetFlight
		@FlightID int
AS
If not exists (SELECT f.ID, f.AirlineID, f.Number, f.FromCode, f.ToCode, f.DepartureDateTime, f.Load
				From Flight f
				Where f.IsVisible = 1 and f.ID = @FlightID)
BEGIN 
	RAISERROR ('Flight does not exist', 1, 1)
	Return
END

				
SELECT f.ID, f.AirlineID, f.Number, f.FromCode, f.ToCode, f.DepartureDateTime, f.Load
From Flight f
Where f.IsVisible = 1 and f.ID = @FlightID
