USE ProjectDatabaseS2G4
Go
Create Procedure [dbo].[DropFlight]
		@FlightID int
As
Begin
	If (@FlightID is null or @FlightID = '')
	Begin 
		Raiserror('Flight ID is null or empty.', 14, 1)
		Return 1;
	End
	If (Not Exists(Select * From Flight Where ID = @FlightID))
	Begin
		Raiserror('Flight ID does not exist.', 14, 1);
		Return 1;
	End
	
	Update Flight
	Set IsVisible = 0
	Where ID = @FlightID
	
	Update SubmitsLoadRequest
	Set IsVisible = 0
	Where FlightID = @FlightID

	Update Updates
	Set IsVisible = 0
	Where FlightID = @FlightID
	
End
Go
