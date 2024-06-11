Create Procedure GetFlights
	@FromCode char(3) = NULL,
	@ToCode char(3) = NULL,
	@AirlineID char(3) = NULL,
	@Load int = NULL,
	@LastLoadUpdate Date = NULL
As

