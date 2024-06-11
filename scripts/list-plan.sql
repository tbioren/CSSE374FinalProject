USE ProjectDatabaseS2G4
Go
Create Procedure ListPlan
	@PlanName varchar(60),
	@UserID int
AS
BEGIN
	If (@PlanName is null or @PlanName = '' or @UserID is null)
	BEGIN 
		RAISERROR ('Inputs may not be null', 14, 1)
		RETURN 
	END
	
	If not exists (Select * from USERS where ID = @UserID)
	BEGIN 
		RAISERROR ('User must exist', 14, 1)
		RETURN 
	END
	
	SELECT f.ID, f.LastLoadUpdate, f.Number, f.Load, f.DepartureDateTime, f.FromCode, f.ToCode, f.AirlineID
	FROM TripPlan t join Flight f on t.FlightID = f.ID
	Where t.IsVisible = 1 and f.IsVisible = 1 and t.PlanName = @PlanName and t.UserID = @UserID
	Order By t.FlightOrder 
END

	
