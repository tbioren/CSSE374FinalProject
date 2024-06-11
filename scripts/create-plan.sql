USE ProjectDatabaseS2G4
Go
Alter Procedure CreatePlan
	@PlanName varchar(60),
	@FlightID int,
	@UserID int,
	@Order int
As
Begin
	If (@PlanName is null or @PlanName = '' or @FlightID is null or @UserID is null or @Order is null)
	BEGIN 
		Raiserror('Inputs may not be null', 14, 1)
		Return
	END
	If not exists (Select * From Flight Where ID = @FlightID)
	BEGIN 
		RAISERROR ('Flight must exist', 14, 1)
		Return
	END
	If not exists (Select * From USERS Where ID = @UserID)
	BEGIN 
		RAISERROR ('User must exist', 14, 1)
		RETURN 
	END
	If exists (Select * from TripPlan where PlanName = @PlanName and FlightOrder = @Order and UserID = @UserID)
	BEGIN 
		RAISERROR ('Two flights may not occupy the same order in a plan', 14, 1)
		Return 
	END
	If exists (Select * from TripPlan Where PlanName = @PlanName and UserID = @UserID and  FlightID = @FlightID)
	BEGIN 
		Update TripPlan
		Set FlightOrder = @Order, IsVisible = 1
		Where PlanName = @PlanName and UserID = @UserID and FlightID = @FlightID
		Return
	END
	
	Insert into TripPlan (FlightID, UserID, PlanName, FlightOrder, IsVisible)
	Values(@FlightID, @UserID, @PlanName, @Order, 1)
END
