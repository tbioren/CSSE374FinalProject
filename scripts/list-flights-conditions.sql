Create Procedure ListFlightsConditions
		@eID int,
		@aID char(3) = null,
		@fNumber varchar(32) = null,
		@fromCode char(3) = null,
		@toCode char(3) = null,
		@departureYear int = null,
		@departureMonth int = null,
		@departureDay int = null
AS


Begin
	If (@fromCode is not null AND @toCode is not null AND @fromCode = @toCode)
		Begin
			Raiserror('Destinations are the same', 15, 1)
			Return 1
		End

	Declare @eAID as char(3)
	Set @eAID = (Select AirlineID From Employee Where ID = @eID)
	
	SELECT DISTINCT f.ID, f.AirlineID, f.Number, f.FromCode, f.ToCode, f.DepartureDateTime, f.Load
	From Flight f
	Where f.AirlineID <> @eAID
	AND (@aID is null or f.AirlineID = @aID)
	AND (@fNumber is null or f.Number = @fNumber)
	AND (f.IsVisible = 1)
	AND (Not Exists (Select * From SubmitsLoadRequest s Where EmployeeID = @eID AND FlightID = f.ID AND s.IsVisible = 1))
	AND (@fromCode is null OR f.FromCode = @fromCode)
	AND (@toCode is null OR f.ToCode = @toCode)
	AND (@departureYear is null OR DATEPART(Year, DepartureDateTime) = @departureYear)
	AND (@departureMonth is null OR @departureMonth = -1 OR DATEPART(MONTH, DepartureDateTime) = @departureMonth)
	AND (@departureDay is null OR @departureDay = -1 OR DATEPART(DAY, DepartureDateTime) = @departureDay)
End

