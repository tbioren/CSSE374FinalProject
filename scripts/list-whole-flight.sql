Use ProjectDatabaseS2G4
Go
Create Procedure ListWholeFlight
	@Number varchar(32) = NULL,
	@AirlineID char(3) = NULL
AS

If @Number is null or @Number = ''
Begin
	If @AirlineID is null or @AirlineID = ''
	BEGIN 
		Select ID, LastLoadUpdate, Number, Load, DepartureDateTime, FromCode, ToCode, AirlineID
		From Flight
		Where IsVisible = 1
		Return
	END
	
	Select ID, LastLoadUpdate, Number, Load, DepartureDateTime, FromCode, ToCode, AirlineID
	From Flight
	Where IsVisible = 1 And AirlineID = @AirlineID
	Return
End

If @AirlineID is null or @AirlineID = ''
BEGIN
	Select ID, LastLoadUpdate, Number, Load, DepartureDateTime, FromCode, ToCode, AirlineID
	From Flight
	Where IsVisible = 1 And Number = @Number
	Return
End

Select ID, LastLoadUpdate, Number, Load, DepartureDateTime, FromCode, ToCode, AirlineID
From Flight
Where IsVisible = 1 And Number = @Number And AirlineID = @AirlineID
