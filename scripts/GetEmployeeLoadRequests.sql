Create Procedure GetEmployeeLoadRequests
	@EmployeeID int,
	@IsVisible bit output
As
Begin
	If @EmployeeID is NULL or @EmployeeID = ''
	Begin
		Raiserror('Can not get EmployeeID', 15, 1)
		Return 1
	End

	Select * 
	From SubmitsLoadRequest
	Where EmployeeID = @EmployeeID and IsVisible = 1
End