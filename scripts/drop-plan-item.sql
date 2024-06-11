USE ProjectDatabaseS2G4
Go
Create Procedure DropPlanItem
	@PlanName varchar(60),
	@FlightID int,
	@UserID int
As
Begin
	If (@PlanName is null or @PlanName = '' or @UserID is null or @FlightID is null)
	BEGIN 
		RAISERROR ('Inputs must not be null', 14, 1)
		RETURN 
	END
	
	If not exists (Select * From USERS where ID = @UserID)
	BEGIN 
		RAISERROR ('User must exist', 14, 1)
		RETURN 
	END
	
	If not exists (Select * From Flight where ID = @FlightID)
	BEGIN 
		RAISERROR ('Flight must exist', 14, 1)
		RETURN 
	END
	
	
	If not exists (Select * From TripPlan where PlanName = @PlanName and UserID = @UserID and FlightID = @FlightID)
	BEGIN 
		RAISERROR ('Plan must exist', 14, 1)
		RETURN 
	END
	
	Update TripPlan
	Set IsVisible = 0
	Where PlanName = @PlanName and UserID = @UserID and FlightID = @FlightID
END
