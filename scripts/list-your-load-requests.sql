Create Procedure listYourLoadRequests
	@eID int
AS
Select f.ID, f.AirlineID, f.Number, f.FromCode, f.ToCode, f.DepartureDateTime, f.Load, s.Token
From SubmitsLoadRequest s
Join Flight f on s.FlightID = f.ID
Join Employee e on s.EmployeeID = e.ID
Where s.EmployeeID = @eID 
AND e.AirlineID <> f.AirlineID
AND s.IsVisible = 1
AND f.IsVisible = 1
