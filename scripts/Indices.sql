CREATE INDEX departureDateTime ON Flight(DepartureDateTime)

CREATE INDEX loadRequestDateTime ON SubmitsLoadRequest(DateAndTime)

CREATE INDEX airlineAlliances ON Airline(Alliance)

CREATE INDEX flightLoad ON Flight([Load])

CREATE INDEX flightLastLoadUpdate ON Flight(LastLoadUpdate)