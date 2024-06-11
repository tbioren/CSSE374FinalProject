Use [ProjectDatabaseS2G4]
Go
Create Procedure [dbo].[CreateUpdate]
		@EmployeeID int,
		@FlightID int,
		@Load int, 
		@Token int
As
Begin
	If (Not Exists(Select * From Employee Where ID = @EmployeeID))
		Begin
			Raiserror('Airline does not exist.', 14, 1)
			Return 1;
		End
	If (Not Exists(Select * From Flight Where ID = @FlightID))
		Begin
			Raiserror('Airline does not exist.', 14, 1)
			Return 1;
		End
	Insert Into [Updates](EmployeeID, FlightID, DateAndTime, [Load], Token)
	Values (@EmployeeID, @FlightID, GETDATE(), @Load, @Token)
End