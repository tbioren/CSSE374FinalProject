Use [ProjectDatabaseS2G4]
Go
Create Procedure [dbo].[UpdateFlight]
		@FlightNumber varchar(32),
		@DepartureDateTime smalldatetime,
		@FromCode char(3),
		@ToCode char(3),
		@AirlineID char(3),
		@ID int
As
Begin
	If (@FlightNumber is null or @DepartureDateTime is null or @FromCode is null or @ToCode is null or @AirlineID is null)
	Begin
		Raiserror ('Inputs must not be null', 14, 1)
		Return
	End
	If Not Exists(Select * From Flight Where ID = @ID And IsVisible = 1)
	Begin
		Raiserror ('Flight does not exist', 14, 1)
		Return
	End
	If Not Exists(Select * From Airline Where ID = @AirlineID)
	Begin
		Raiserror('Airline does not exist.', 14, 1)
		Return
	End
	If Not Exists(Select * From Destination Where IATACode = @FromCode)
	Begin
		Raiserror ('FromCode does not exist', 14, 1)
		Return
	End
	If Not Exists(Select * From Destination Where IATACode = @ToCode)
	Begin
		Raiserror ('ToCode does not exist', 14, 1)
		Return
	End
	Update Flight
	Set Number = @FlightNumber, DepartureDateTime = @DepartureDateTime, FromCode = @FromCode, ToCode = @ToCode, AirlineID = @AirlineID
	Where ID = @ID
	End
Go
