USE ProjectDatabaseS2G4
Go
Create Procedure ListAllPlans
	@UserID int
AS
BEGIN
	If (@UserID is null)
	BEGIN 
		RAISERROR ('Inputs may not be null', 14, 1)
		RETURN 
	END
	
	If not exists (Select * from USERS where ID = @UserID)
	BEGIN 
		RAISERROR ('User must exist', 14, 1)
		RETURN 
	END
	
	SELECT DISTINCT PlanName
	FROM TripPlan t
	Where t.IsVisible = 1 and t.UserID = @UserID
END

	
