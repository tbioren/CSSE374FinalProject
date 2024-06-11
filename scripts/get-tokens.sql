USE ProjectDatabaseS2G4
Go
CREATE PROCEDURE dbo.GetTokens
       @Username varchar (64)
AS
IF @Username is null
Begin
	Raiserror ('Input arguments may not be NULL', 14, 1)
	Return
End

IF not exists (Select u.Username From USERS u Join Employee e on u.ID = e.ID Where @Username = u.Username)
Begin
	Raiserror ('Employee must exist', 14, 1)
	Return
End

Select e.Tokens as EmployeeTokens
From Employee e Join USERS u on e.ID = u.ID
Where u.Username = @Username
GO
