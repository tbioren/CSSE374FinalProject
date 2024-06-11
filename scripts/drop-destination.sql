Use [ProjectDatabaseS2G4]
Go
Create Procedure [dbo].[DropDestination]
		@IATACode char(3)
As
Begin
	If (@IATACode is null or @IATACode = '')
	Begin 
		Raiserror('IATA Code is null or empty.', 14, 1)
		Return 1;
	End
	If (Not Exists(Select * From Destination Where IATACode = @IATACode))
	Begin
		Raiserror('IATA Code does not exist.', 14, 1);
		Return 1;
	End
	Update Destination
	Set IsVisible = 0
	Where IATACode = @IATACode
End
Go

