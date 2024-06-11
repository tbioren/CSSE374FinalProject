Use [ProjectDatabaseS2G4]
Go
Create Procedure [dbo].[DropAirline]
		@AirlineID char(3)
As
Begin
	If (@AirlineID is null or @AirlineID = '')
	Begin 
		Raiserror('Airline ID is null or empty.', 14, 1)
		Return 1;
	End
	If (Not Exists(Select * From Airline Where ID = @AirlineID))
	Begin
		Raiserror('Airline ID does not exist.', 14, 1);
		Return 1;
	End
	Update Airline
	Set IsVisible = 0
	Where ID = @AirlineID
End
Go
