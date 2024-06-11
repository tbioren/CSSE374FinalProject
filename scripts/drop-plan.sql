USE ProjectDatabaseS2G4
Go
Create Procedure DropPlan
	@PlanName varchar(60),
	@UserID int
As
Begin
	If (@PlanName is null or @PlanName = '' or @UserID is null)
	BEGIN 
		RAISERROR ('Inputs must not be null', 14, 1)
		RETURN 
	END
	
	If not exists (Select * From USERS where ID = @UserID)
	BEGIN 
		RAISERROR ('User must exist', 14, 1)
		RETURN 
	END
	
	If not exists (Select * From TripPlan where PlanName = @PlanName and UserID = @UserID)
	BEGIN 
		RAISERROR ('Plan must exist', 14, 1)
		RETURN 
	END
	
	Update TripPlan
	Set IsVisible = 0
	Where PlanName = @PlanName and UserID = @UserID
END
