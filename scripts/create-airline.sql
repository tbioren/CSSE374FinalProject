Use [ProjectDatabaseS2G4]
Go
Create Procedure [dbo].[CreateAirline]
		@AirlineID char(3),
		@Alliance varchar(32) = null
As
Begin
	If (@AirlineID is null or @AirlineID = '')
	Begin 
		Raiserror('Airline ID is null or empty.', 14, 1)
		Return 1;
	End
	If (Exists(Select * From Airline Where ID = @AirlineID))
	Begin
		Raiserror('Airline ID already exists.', 14, 2);
		Return 1;
	End
	Insert Into [Airline](ID, Alliance, IsVisible)
	Values (@AirlineID, @Alliance, 1)
	Return 0;
End
Go
