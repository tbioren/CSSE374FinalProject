Create Procedure UpdateLoadRequestCost
		@EmployeeID int,
		@FlightID int,
		@NewTokenCost int
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
	If ((Select AirlineID From Employee Where ID = @EmployeeID) = (Select AirlineID From Flight Where ID = @FlightID))
		Begin
			Raiserror('Employee and Flight AirlineID are the same', 15, 1)
			Return 1;
		End	
	If (Not Exists(Select * From SubmitsLoadRequest Where EmployeeID = @EmployeeID and FlightID = @FlightID))
		Begin
			Raiserror('Load request does not exist', 15, 1)
			Return 1
		End
	If (0 = (Select IsVisible From SubmitsLoadRequest Where EmployeeID = @EmployeeID and FlightID = @FlightID))
		Begin
			Raiserror('Load request is deleted', 15, 1)
			Return 1
		End

	If (@NewTokenCost is not null)
		Begin
			Declare @EmployeeTokens as int
			Set @EmployeeTokens = (Select Tokens From Employee Where ID = @EmployeeID)
	
			Declare @LoadRequestTokenCost as int
			Set @LoadRequestTokenCost = (Select Token From SubmitsLoadRequest Where EmployeeID = @EmployeeID and FlightID = @FlightID and IsVisible = 1)

	
			If ((@NewTokenCost - @LoadRequestTokenCost) > @EmployeeTokens)
				Begin
					Raiserror('Employee does not have enough tokens', 14, 1)
					Return 1;
				End
	
			If (@NewTokenCost > (@LoadRequestTokenCost))
				Begin
					Update Employee
					Set Tokens = Tokens - (@NewTokenCost - @LoadRequestTokenCost)
					Where ID = @EmployeeID
		
					Update SubmitsLoadRequest
					Set Token = @NewTokenCost
					Where EmployeeID = @EmployeeID and FlightID = @FlightID
				End


			If (@NewTokenCost < @LoadRequestTokenCost)
				Begin
					Update Employee
					Set Tokens = Tokens + (@LoadRequestTokenCost - @NewTokenCost)
					Where ID = @EmployeeID

					Update SubmitsLoadRequest
					Set Token = @NewTokenCost
					Where EmployeeID = @EmployeeID and FlightID = @FlightID
				End
			End
		Else
			Begin
				Raiserror('No token value', 15, 1)
				Return 1
			End
End