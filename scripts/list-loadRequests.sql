USE ProjectDatabaseS2G4
Go
Create Procedure ListLoadRequests
@Username varchar(64)
AS

Declare @AirlineID char(3)

Select @AirlineID = Employee.AirlineID
From Employee Join USERS On Employee.ID = USERS.ID
Where Username = @Username

SELECT DISTINCT Flight.Number, Flight.ID, Destination.Name, Submitter.Username, Flight.AirlineID, Flight.FromCode, Flight.ToCode, Flight.DepartureDateTime, Flight.Load, SubmitsLoadRequest.Token
From (Flight Join (SubmitsLoadRequest 
	Join USERS Submitter on SubmitsLoadRequest.EmployeeID = Submitter.ID) on Flight.ID = SubmitsLoadRequest.FlightID) 
	Join Destination on Flight.FromCode = Destination.IATACode
Where Flight.IsVisible = 1 And SubmitsLoadRequest.IsVisible = 1 And Flight.AirlineID = @AirlineID
