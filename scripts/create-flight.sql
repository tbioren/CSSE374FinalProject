Use [ProjectDatabaseS2G4]
Go
Create Procedure [dbo].[CreateFlight]
		@FlightNumber varchar(32),
		@DepartureDateTime smalldatetime,
		@FromCode char(3),
		@ToCode char(3),
		@AirlineID char(3),
		@ID int Output
As
Begin
	If (@FlightNumber is null or @DepartureDateTime is null or @FromCode is null or @ToCode is null or @AirlineID is null)
	Begin
		Raiserror ('Inputs must not be null', 14, 1)
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
	Insert Into [Flight](Number, DepartureDateTime, FromCode, ToCode, AirlineID, IsVisible)
	Values (@FlightNumber, @DepartureDateTime, @FromCode, @ToCode, @AirlineID, 1)

	Set @ID = @@IDENTITY
End
Go
