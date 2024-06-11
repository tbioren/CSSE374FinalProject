USE ProjectDatabaseS2G4
Go
Create Procedure dbo.UpdateLoadRequest
	@FlightID int,
	@Username varchar(64), --the username of the employee who fulfilled the load request
	@NewLoad int
AS
	If (@FlightID is null or @Username is null or @NewLoad is null)
	BEGIN 
		RAISERROR ('Inputs may not be null', 1, 1)
		RETURN 
	END

	Declare @EmployeeID as int
	
	Select @EmployeeID = ID
	From USERS
	Where Username = @Username
	
	If exists (Select * From Updates Where FlightID = @FlightID and EmployeeID = @EmployeeID)
	BEGIN 
	      Update Updates 
	      Set DateAndTime = Getdate(), Load = @NewLoad
	END
	Else
	BEGIN
		INSERT Into Updates (EmployeeID, FlightID, DateAndTime, Load, Token, IsVisible)
		Values (@EmployeeID, @FlightID, Getdate(), @NewLoad, 1, 1)
	END
	
	Update Employee
	Set Tokens = Tokens + 1
	Where ID = @EmployeeID
	
	Update Flight
	Set Load = @NewLoad, LastLoadUpdate = Getdate()
	Where ID = @FlightID
