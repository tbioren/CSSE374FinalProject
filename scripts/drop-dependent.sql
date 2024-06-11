USE ProjectDatabaseS2G4
Go
CREATE PROCEDURE dbo.DropDependent
       @DependentID int
AS
IF (@DependentID is null) 
Begin
	Raiserror ('Input arguments may not be NULL', 14, 1)
	Return
End

IF (@DependentID not in (Select ID From Dependent Where @DependentID = ID))
Begin
	Raiserror ('Dependent must exist', 14, 1)
	Return
End

Delete From Dependent 
Where ID = @DependentID
GO