Create Procedure DeleteLoadRequest
	@EmployeeID int,
	@FlightID int
As
Begin
	If (@EmployeeID is NULL or @EmployeeID = '' or @FlightID is NULL or @FlightID = '')
		Begin
			Raiserror('Invalid entry parameters', 15, 1)
			Return 1
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

	If ((Select AirlineID From Employee Where ID = @EmployeeID) = (Select AirlineID From Flight Where ID = @FlightID))
		Begin
			Raiserror('Employee and Flight AirlineID are the same', 15, 1)
			Return 1;
		End


	
	Begin
		Update SubmitsLoadRequest
		Set IsVisible = 0
		Where EmployeeID = @EmployeeID and FlightID = @FlightID

		Update Employee
		Set Tokens = Tokens + (Select Token From SubmitsLoadRequest Where EmployeeID = @EmployeeID and FlightID = @FlightID)
		Where ID = @EmployeeID
		
		Select IsVisible
		From SubmitsLoadRequest
		Where EmployeeID = @EmployeeID and FlightID = @FlightID
	End
End