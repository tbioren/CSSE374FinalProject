Use ProjectDatabaseS2G4
Go
Alter Procedure [dbo].[CreateLoadRequest]
		@EmployeeID int,
		@FlightID int,
		@TokenCost int = 1
As
Begin
	If (Not Exists(Select * From Flight Where ID = @FlightID))
		Begin
			Raiserror('Flight does not exist.', 14, 1)
			Return 1;
		End
	If (Not Exists(Select * From Employee Where ID = @EmployeeID))
		Begin
			Raiserror('Employee does not exist.', 14, 1)
			Return 1;
		End
	If ((Select Tokens From Employee Where ID = @EmployeeID) < @TokenCost)
		Begin
			Raiserror('Employee does not have enough tokens', 14, 1)
			Return 1;
		End
	If ((Select AirlineID From Employee Where ID = @EmployeeID) = (Select AirlineID From Flight Where ID = @FlightID))
		Begin
			Raiserror('Employee and Flight AirlineID are the same', 15, 1)
			Return 1;
		End
	If (Exists (Select * From SubmitsLoadRequest Where EmployeeID = @EmployeeID and FlightID = @FlightID and IsVisible = 1))
		Begin
			Raiserror('Load request already exists', 15, 1)
			Return 1;
		End

	If (Exists (Select * From SubmitsLoadRequest Where EmployeeID = @EmployeeID and FlightID = @FlightID and IsVisible = 0))
		Begin
			Update SubmitsLoadRequest
			Set Token = @TokenCost
			Where EmployeeID = @EmployeeID and FlightID = @FlightID

			Update SubmitsLoadRequest
			Set IsVisible = 1
			Where EmployeeID = @EmployeeID and FlightID = @FlightID
		End
	Else
		Begin
			Insert Into [SubmitsLoadRequest](EmployeeID, FlightID, DateAndTime, Token)
			Values(@EmployeeID, @FlightID, GETDATE(), @TokenCost)
		End

	
	Update Employee
	Set Tokens = Tokens - @TokenCost
	Where ID = @EmployeeID


End
Go