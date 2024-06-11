USE [master]
GO
/****** Object:  Database [ProjectDatabaseS2G4]    Script Date: 5/16/2024 8:48:29 PM ******/
CREATE DATABASE [ProjectDatabaseS2G4ScriptTest]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'ProjectDatabaseS2G4ScriptTest', FILENAME = N'/var/opt/mssql/data/ProjectDatabaseS2G4ScriptTest.mdf' , SIZE = 14656KB , MAXSIZE = 30720KB , FILEGROWTH = 12%)
 LOG ON 
( NAME = N'ProjectDatabaseS2G4LogScriptTest', FILENAME = N'/var/opt/mssql/data/ProjectDatabaseS2G4LogScriptTest.mdf' , SIZE = 14400KB , MAXSIZE = 51200KB , FILEGROWTH = 17%)
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
USE [ProjectDatabaseS2G4ScriptTest]
GO
/****** Object:  User [MyID90Application]    Script Date: 5/16/2024 8:48:44 PM ******/
CREATE USER [MyID90Application] FOR LOGIN [MyID90Application] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_datareader] ADD MEMBER [MyID90Application]
GO
ALTER ROLE [db_datawriter] ADD MEMBER [MyID90Application]
GO
/****** Object:  Table [dbo].[Airline]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Airline](
	[ID] [char](3) NOT NULL,
	[Alliance] [varchar](32) NULL,
	[IsVisible] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Dependent]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Dependent](
	[ID] [int] NOT NULL,
	[FirstName] [varchar](20) NOT NULL,
	[LastName] [varchar](20) NOT NULL,
	[EmployeeID] [int] NULL,
	[isDeleted] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Destination]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Destination](
	[IATACode] [char](3) NOT NULL,
	[Name] [varchar](32) NULL,
	[IsVisible] [bit] NULL,
 CONSTRAINT [PK__Destinat__EFD6F5BF2DCEB3D7] PRIMARY KEY CLUSTERED 
(
	[IATACode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Employee]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Employee](
	[ID] [int] NOT NULL,
	[FirstName] [varchar](64) NOT NULL,
	[LastName] [varchar](64) NOT NULL,
	[Tokens] [int] NULL,
	[AirlineID] [char](3) NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Flight]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Flight](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[LastLoadUpdate] [date] NULL,
	[Number] [varchar](32) NULL,
	[Load] [int] NULL,
	[DepartureDateTime] [smalldatetime] NULL,
	[FromCode] [char](3) NULL,
	[ToCode] [char](3) NULL,
	[AirlineID] [char](3) NULL,
	[IsVisible] [bit] NULL,
 CONSTRAINT [PK__Flight__3214EC271B957C4F] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SubmitsLoadRequest]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SubmitsLoadRequest](
	[EmployeeID] [int] NOT NULL,
	[FlightID] [int] NOT NULL,
	[DateAndTime] [date] NULL,
	[Token] [int] NULL,
	[IsVisible] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[EmployeeID] ASC,
	[FlightID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TripPlan]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TripPlan](
	[FlightID] [int] NOT NULL,
	[UserID] [int] NOT NULL,
	[PlanName] [varchar](60) NOT NULL,
	[FlightOrder] [int] NULL,
	[IsVisible] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[FlightID] ASC,
	[UserID] ASC,
	[PlanName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Updates]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Updates](
	[EmployeeID] [int] NOT NULL,
	[FlightID] [int] NOT NULL,
	[DateAndTime] [date] NULL,
	[Load] [int] NULL,
	[Token] [int] NULL,
	[IsVisible] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[EmployeeID] ASC,
	[FlightID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[USERS]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[USERS](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Username] [varchar](64) NOT NULL,
	[Salt] [varchar](64) NOT NULL,
	[PasswordHash] [varchar](64) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[Username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [airlineAlliances]    Script Date: 5/16/2024 8:48:44 PM ******/
CREATE NONCLUSTERED INDEX [airlineAlliances] ON [dbo].[Airline]
(
	[Alliance] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [departureDateTime]    Script Date: 5/16/2024 8:48:44 PM ******/
CREATE NONCLUSTERED INDEX [departureDateTime] ON [dbo].[Flight]
(
	[DepartureDateTime] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [flightLastLoadUpdate]    Script Date: 5/16/2024 8:48:44 PM ******/
CREATE NONCLUSTERED INDEX [flightLastLoadUpdate] ON [dbo].[Flight]
(
	[LastLoadUpdate] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [flightLoad]    Script Date: 5/16/2024 8:48:44 PM ******/
CREATE NONCLUSTERED INDEX [flightLoad] ON [dbo].[Flight]
(
	[Load] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [loadRequestDateTime]    Script Date: 5/16/2024 8:48:44 PM ******/
CREATE NONCLUSTERED INDEX [loadRequestDateTime] ON [dbo].[SubmitsLoadRequest]
(
	[DateAndTime] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX flightNumber ON dbo.Flight (Number asc) GO

ALTER TABLE [dbo].[Airline] ADD  CONSTRAINT [DF_Airline_Alliance]  DEFAULT ('None') FOR [Alliance]
GO
ALTER TABLE [dbo].[Dependent] ADD  DEFAULT ((0)) FOR [isDeleted]
GO
ALTER TABLE [dbo].[Flight] ADD  DEFAULT ((1)) FOR [IsVisible]
GO
ALTER TABLE [dbo].[SubmitsLoadRequest] ADD  DEFAULT ((1)) FOR [IsVisible]
GO
ALTER TABLE [dbo].[Dependent]  WITH CHECK ADD FOREIGN KEY([EmployeeID])
REFERENCES [dbo].[Employee] ([ID])
GO
ALTER TABLE [dbo].[Dependent]  WITH CHECK ADD FOREIGN KEY([EmployeeID])
REFERENCES [dbo].[Employee] ([ID])
GO
ALTER TABLE [dbo].[Dependent]  WITH CHECK ADD FOREIGN KEY([ID])
REFERENCES [dbo].[USERS] ([ID])
GO
ALTER TABLE [dbo].[Dependent]  WITH CHECK ADD FOREIGN KEY([ID])
REFERENCES [dbo].[USERS] ([ID])
GO
ALTER TABLE [dbo].[Employee]  WITH CHECK ADD FOREIGN KEY([AirlineID])
REFERENCES [dbo].[Airline] ([ID])
GO
ALTER TABLE [dbo].[Employee]  WITH CHECK ADD FOREIGN KEY([AirlineID])
REFERENCES [dbo].[Airline] ([ID])
GO
ALTER TABLE [dbo].[Employee]  WITH CHECK ADD FOREIGN KEY([ID])
REFERENCES [dbo].[USERS] ([ID])
GO
ALTER TABLE [dbo].[Employee]  WITH CHECK ADD FOREIGN KEY([ID])
REFERENCES [dbo].[USERS] ([ID])
GO
ALTER TABLE [dbo].[Flight]  WITH CHECK ADD  CONSTRAINT [FK__Flight__AirlineI__3E1D39E1] FOREIGN KEY([AirlineID])
REFERENCES [dbo].[Airline] ([ID])
GO
ALTER TABLE [dbo].[Flight] CHECK CONSTRAINT [FK__Flight__AirlineI__3E1D39E1]
GO
ALTER TABLE [dbo].[Flight]  WITH CHECK ADD  CONSTRAINT [FK__Flight__FromCode__3F115E1A] FOREIGN KEY([FromCode])
REFERENCES [dbo].[Destination] ([IATACode])
GO
ALTER TABLE [dbo].[Flight] CHECK CONSTRAINT [FK__Flight__FromCode__3F115E1A]
GO
ALTER TABLE [dbo].[Flight]  WITH CHECK ADD  CONSTRAINT [FK__Flight__ToCode__40058253] FOREIGN KEY([ToCode])
REFERENCES [dbo].[Destination] ([IATACode])
GO
ALTER TABLE [dbo].[Flight] CHECK CONSTRAINT [FK__Flight__ToCode__40058253]
GO
ALTER TABLE [dbo].[SubmitsLoadRequest]  WITH CHECK ADD FOREIGN KEY([EmployeeID])
REFERENCES [dbo].[Employee] ([ID])
GO
ALTER TABLE [dbo].[SubmitsLoadRequest]  WITH CHECK ADD FOREIGN KEY([EmployeeID])
REFERENCES [dbo].[Employee] ([ID])
GO
ALTER TABLE [dbo].[SubmitsLoadRequest]  WITH CHECK ADD  CONSTRAINT [FK__SubmitsLo__Fligh__43D61337] FOREIGN KEY([FlightID])
REFERENCES [dbo].[Flight] ([ID])
GO
ALTER TABLE [dbo].[SubmitsLoadRequest] CHECK CONSTRAINT [FK__SubmitsLo__Fligh__43D61337]
GO
ALTER TABLE [dbo].[TripPlan]  WITH CHECK ADD FOREIGN KEY([FlightID])
REFERENCES [dbo].[Flight] ([ID])
GO
ALTER TABLE [dbo].[TripPlan]  WITH CHECK ADD FOREIGN KEY([UserID])
REFERENCES [dbo].[USERS] ([ID])
GO
ALTER TABLE [dbo].[Updates]  WITH CHECK ADD FOREIGN KEY([EmployeeID])
REFERENCES [dbo].[Employee] ([ID])
GO
ALTER TABLE [dbo].[Updates]  WITH CHECK ADD FOREIGN KEY([EmployeeID])
REFERENCES [dbo].[Employee] ([ID])
GO
ALTER TABLE [dbo].[Updates]  WITH CHECK ADD FOREIGN KEY([FlightID])
REFERENCES [dbo].[Flight] ([ID])
GO
ALTER TABLE [dbo].[Updates]  WITH CHECK ADD FOREIGN KEY([FlightID])
REFERENCES [dbo].[Flight] ([ID])
GO
/****** Object:  StoredProcedure [dbo].[CreateAirline]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE Procedure [dbo].[CreateAirline]
		@AirlineID char(3),
		@Alliance varchar(32) = null
As
Begin
	If (@AirlineID is null or @AirlineID = '')
	Begin 
		Raiserror('Airline ID is null or empty.', 14, 1)
		Return 1;
	End
	If (Exists(Select * From Airline Where ID = @AirlineID))
	Begin
		Raiserror('Airline ID already exists.', 14, 2);
		Return 1;
	End
	Insert Into [Airline](ID, Alliance, IsVisible)
	Values (@AirlineID, @Alliance, 1)
	Return 0;
End
GO
/****** Object:  StoredProcedure [dbo].[CreateDestination]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[CreateDestination] (
	@IATACode CHAR(3),
	@Name VARCHAR(32)
)
AS
	IF @IATACode IS NULL OR @IATACode = '' BEGIN
		RAISERROR('IATA code cannot be null', 14, 1);
		RETURN 1
	END
	IF NOT EXISTS(SELECT * FROM Destination WHERE IATACode = @IATACode) BEGIN
		INSERT INTO Destination(IATACode, Name, IsVisible)
		VALUES (@IATACode, @Name, 1)
		RETURN 0
	END
	ELSE BEGIN
	     Update Destination	
	     Set Name = @Name
	     Where IATACode = @IATACode
	END

GO
/****** Object:  StoredProcedure [dbo].[CreateFlight]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
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

GO
/****** Object:  StoredProcedure [dbo].[CreateLoadRequest]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE Procedure [dbo].[CreateLoadRequest]
		@EmployeeID int,
		@FlightID int,
		@TokenCost int = 1
As
Begin
	If (Not Exists(Select * From Flight Where ID = @FlightID))
		Begin
			Raiserror('Flight does not exist.', 14, 1)
			Return 1;
		End
	If (Not Exists(Select * From Employee Where ID = @EmployeeID))
		Begin
			Raiserror('Employee does not exist.', 14, 1)
			Return 1;
		End
	If ((Select Tokens From Employee Where ID = @EmployeeID) < @TokenCost)
		Begin
			Raiserror('Employee does not have enough tokens', 14, 1)
			Return 1;
		End
	If ((Select AirlineID From Employee Where ID = @EmployeeID) = (Select AirlineID From Flight Where ID = @FlightID))
		Begin
			Raiserror('Employee and Flight AirlineID are the same', 15, 1)
			Return 1;
		End
	If (Exists (Select * From SubmitsLoadRequest Where EmployeeID = @EmployeeID and FlightID = @FlightID and IsVisible = 1))
		Begin
			Raiserror('Load request already exists', 15, 1)
			Return 1;
		End

	If (Exists (Select * From SubmitsLoadRequest Where EmployeeID = @EmployeeID and FlightID = @FlightID and IsVisible = 0))
		Begin
			Update SubmitsLoadRequest
			Set Token = @TokenCost
			Where EmployeeID = @EmployeeID and FlightID = @FlightID

			Update SubmitsLoadRequest
			Set IsVisible = 1
			Where EmployeeID = @EmployeeID and FlightID = @FlightID
		End
	Else
		Begin
			Insert Into [SubmitsLoadRequest](EmployeeID, FlightID, DateAndTime, Token)
			Values(@EmployeeID, @FlightID, GETDATE(), @TokenCost)
		End

	
	Update Employee
	Set Tokens = Tokens - @TokenCost
	Where ID = @EmployeeID


End
GO
/****** Object:  StoredProcedure [dbo].[CreatePlan]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE Procedure [dbo].[CreatePlan]
	@PlanName varchar(60),
	@FlightID int,
	@UserID int,
	@Order int
As
Begin
	If (@PlanName is null or @PlanName = '' or @FlightID is null or @UserID is null or @Order is null)
	BEGIN 
		Raiserror('Inputs may not be null', 14, 1)
		Return
	END
	If not exists (Select * From Flight Where ID = @FlightID)
	BEGIN 
		RAISERROR ('Flight must exist', 14, 1)
		Return
	END
	If not exists (Select * From USERS Where ID = @UserID)
	BEGIN 
		RAISERROR ('User must exist', 14, 1)
		RETURN 
	END
	If exists (Select * from TripPlan where PlanName = @PlanName and FlightOrder = @Order and UserID = @UserID)
	BEGIN 
		RAISERROR ('Two flights may not occupy the same order in a plan', 14, 1)
		Return 
	END
	If exists (Select * from TripPlan Where PlanName = @PlanName and UserID = @UserID and  FlightID = @FlightID)
	BEGIN 
		Update TripPlan
		Set FlightOrder = @Order, IsVisible = 1
		Where PlanName = @PlanName and UserID = @UserID and FlightID = @FlightID
		Return
	END
	
	Insert into TripPlan (FlightID, UserID, PlanName, FlightOrder, IsVisible)
	Values(@FlightID, @UserID, @PlanName, @Order, 1)
END

GO
/****** Object:  StoredProcedure [dbo].[CreateUpdate]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
Create Procedure [dbo].[CreateUpdate]
		@EmployeeID int,
		@FlightID int,
		@Load int, 
		@Token int
As
Begin
	If (Not Exists(Select * From Employee Where ID = @EmployeeID))
		Begin
			Raiserror('Airline does not exist.', 14, 1)
			Return 1;
		End
	If (Not Exists(Select * From Flight Where ID = @FlightID))
		Begin
			Raiserror('Airline does not exist.', 14, 1)
			Return 1;
		End
	Insert Into [Updates](EmployeeID, FlightID, DateAndTime, [Load], Token)
	Values (@EmployeeID, @FlightID, GETDATE(), @Load, @Token)
End
GO
/****** Object:  StoredProcedure [dbo].[CreateUserDependent]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[CreateUserDependent] @Username VARCHAR(64), @Salt VARCHAR(64), @PasswordHash VARCHAR(64), @FirstName VARCHAR(64), @LastName VARCHAR(64), @EmployeeUsername VARCHAR(64), @AddCode INT
AS
	-- Check if username is taken
	IF EXISTS(SELECT * FROM Users WHERE Username = @Username) BEGIN
		RAISERROR('Error: Username already taken', 16, 0)
		RETURN 1
	END
	IF NOT EXISTS(SELECT * FROM Employee LEFT JOIN Users ON Employee.ID = Users.ID WHERE Users.Username = @EmployeeUsername) BEGIN
		RAISERROR('Error: No employee with that username!', 16, 0)
		RETURN 1
	END
	-- Make sure fields aren't blank
	IF @Username IS NULL OR @Salt IS NULL OR @PasswordHash IS NULL OR @Username = '' OR @Salt = '' OR @PasswordHash = '' BEGIN
		RAISERROR('Error: Fields cant be blank', 16, 0)
		RETURN 1
	END
	-- Make sure the add code is right
	IF NOT EXISTS (SELECT * FROM USERS WHERE Username = @EmployeeUsername AND ID = @AddCode) BEGIN
		RAISERROR('Error: Add code is incorrect', 16, 0);
		RETURN 1
	END
	ELSE BEGIN
		-- Create a User
		INSERT INTO Users (Username, Salt, PasswordHash)
		VALUES(@Username, @Salt, @PasswordHash)

		-- Grab the ID that was generated for the User
		DECLARE @DependentID INT
		SELECT @DependentID = ID
		FROM Users
		WHERE Username = @Username
	
		-- Get Employee's ID
		DECLARE @EmployeeID INT
		SELECT @EmployeeID = Users.ID
		FROM Employee LEFT JOIN Users ON Employee.ID = Users.ID
		WHERE Users.Username = @EmployeeUsername

		-- Create a new Dependent
		INSERT INTO Dependent (ID, FirstName, LastName, EmployeeID)
		VALUES (@DependentID, @FirstName, @LastName, @EmployeeID)
	END
GO
/****** Object:  StoredProcedure [dbo].[CreateUserEmployee]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[CreateUserEmployee] @Username VARCHAR(64), @Salt VARCHAR(64), @PasswordHash VARCHAR(64), @FirstName VARCHAR(64), @LastName VARCHAR(64), @EmployeeAirline char(3)
AS
	-- Make sure fields aren't blank
	IF @Username IS NULL OR @Salt IS NULL OR @PasswordHash IS NULL OR @LastName IS NULL OR @EmployeeAirline IS NULL OR @LastName = '' OR @Username = '' OR @Salt = '' OR @PasswordHash = '' OR @EmployeeAirline = '' BEGIN
		RAISERROR('Error: Fields cant be blank', 16, 0)
		RETURN 1
	END
	-- Check if username is taken
	IF EXISTS(SELECT * FROM Users WHERE Username = @Username) BEGIN
		RAISERROR('Error: Username already taken', 16, 0)
		RETURN 1
	END
	-- Check if airline is valid
	IF NOT EXISTS(SELECT * FROM Airline Where ID = @EmployeeAirline) BEGIN
		RAISERROR('Error: Airline does not exist', 16, 0)
		RETURN 1
	END
	ELSE BEGIN
		-- Create a User
		INSERT INTO Users (Username, Salt, PasswordHash)
		VALUES(@Username, @Salt, @PasswordHash)

		-- Grab the ID that was generated for the User
		DECLARE @ID INT
		SELECT @ID = ID
		FROM Users
		WHERE Username = @Username
	
		-- Add an Employee with that ID
		INSERT INTO Employee (ID, Tokens, FirstName, LastName, AirlineID)
		VALUES (@ID, 0, @FirstName, @LastName, @EmployeeAirline)
		RETURN 0
	END
GO
/****** Object:  StoredProcedure [dbo].[DeleteLoadRequest]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE Procedure [dbo].[DeleteLoadRequest]
	@EmployeeID int,
	@FlightID int
As
Begin
	If (@EmployeeID is NULL or @EmployeeID = '' or @FlightID is NULL or @FlightID = '')
		Begin
			Raiserror('Invalid entry parameters', 15, 1)
			Return 1
		End

	If (Not Exists(Select * From SubmitsLoadRequest Where EmployeeID = @EmployeeID and FlightID = @FlightID))
		Begin
			Raiserror('Load request does not exist', 15, 1)
			Return 1
		End

	If (0 = (Select IsVisible From SubmitsLoadRequest Where EmployeeID = @EmployeeID and FlightID = @FlightID))
		Begin
			Raiserror('Load request is deleted', 15, 1)
			Return 1
		End

	If ((Select AirlineID From Employee Where ID = @EmployeeID) = (Select AirlineID From Flight Where ID = @FlightID))
		Begin
			Raiserror('Employee and Flight AirlineID are the same', 15, 1)
			Return 1;
		End


	
	Begin
		Update SubmitsLoadRequest
		Set IsVisible = 0
		Where EmployeeID = @EmployeeID and FlightID = @FlightID

		Update Employee
		Set Tokens = Tokens + (Select Token From SubmitsLoadRequest Where EmployeeID = @EmployeeID and FlightID = @FlightID)
		Where ID = @EmployeeID
		
		Select IsVisible
		From SubmitsLoadRequest
		Where EmployeeID = @EmployeeID and FlightID = @FlightID
	End
End
GO
/****** Object:  StoredProcedure [dbo].[DropAirline]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
Create Procedure [dbo].[DropAirline]
		@AirlineID char(3)
As
Begin
	If (@AirlineID is null or @AirlineID = '')
	Begin 
		Raiserror('Airline ID is null or empty.', 14, 1)
		Return 1;
	End
	If (Not Exists(Select * From Airline Where ID = @AirlineID))
	Begin
		Raiserror('Airline ID does not exist.', 14, 1);
		Return 1;
	End
	Update Airline
	Set IsVisible = 0
	Where ID = @AirlineID
End

GO
/****** Object:  StoredProcedure [dbo].[DropDependent]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[DropDependent]
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
/****** Object:  StoredProcedure [dbo].[DropDestination]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--Drop Procedure dbo.DropDestination
--Go
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

GO
/****** Object:  StoredProcedure [dbo].[DropEmployee]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[DropEmployee]
       @EmployeeID int
AS
IF (@EmployeeID is null) 
Begin
	Raiserror ('Input arguments may not be NULL', 14, 1)
	Return
End

IF (@EmployeeID not in (Select ID From Employee Where @EmployeeID = ID))
Begin
	Raiserror ('EmployeeID must exist', 14, 1)
	Return
End

Delete From Employee 
Where ID = @EmployeeID

GO
/****** Object:  StoredProcedure [dbo].[DropFlight]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
Create Procedure [dbo].[DropFlight]
		@FlightID int
As
Begin
	If (@FlightID is null or @FlightID = '')
	Begin 
		Raiserror('Flight ID is null or empty.', 14, 1)
		Return 1;
	End
	If (Not Exists(Select * From Flight Where ID = @FlightID))
	Begin
		Raiserror('Flight ID does not exist.', 14, 1);
		Return 1;
	End
	
	Update Flight
	Set IsVisible = 0
	Where ID = @FlightID
	
	Update SubmitsLoadRequest
	Set IsVisible = 0
	Where FlightID = @FlightID

	Update Updates
	Set IsVisible = 0
	Where FlightID = @FlightID
	
End

GO
/****** Object:  StoredProcedure [dbo].[DropPlan]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE Procedure [dbo].[DropPlan]
	@PlanName varchar(60),
	@UserID int
As
Begin
	If (@PlanName is null or @PlanName = '' or @UserID is null)
	BEGIN 
		RAISERROR ('Inputs must not be null', 14, 1)
		RETURN 
	END
	
	If not exists (Select * From USERS where ID = @UserID)
	BEGIN 
		RAISERROR ('User must exist', 14, 1)
		RETURN 
	END
	
	If not exists (Select * From TripPlan where PlanName = @PlanName and UserID = @UserID)
	BEGIN 
		RAISERROR ('Plan must exist', 14, 1)
		RETURN 
	END
	
	Update TripPlan
	Set IsVisible = 0
	Where PlanName = @PlanName and UserID = @UserID
END

GO
/****** Object:  StoredProcedure [dbo].[DropPlanItem]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE Procedure [dbo].[DropPlanItem]
	@PlanName varchar(60),
	@FlightID int,
	@UserID int
As
Begin
	If (@PlanName is null or @PlanName = '' or @UserID is null or @FlightID is null)
	BEGIN 
		RAISERROR ('Inputs must not be null', 14, 1)
		RETURN 
	END
	
	If not exists (Select * From USERS where ID = @UserID)
	BEGIN 
		RAISERROR ('User must exist', 14, 1)
		RETURN 
	END
	
	If not exists (Select * From Flight where ID = @FlightID)
	BEGIN 
		RAISERROR ('Flight must exist', 14, 1)
		RETURN 
	END
	
	
	If not exists (Select * From TripPlan where PlanName = @PlanName and UserID = @UserID and FlightID = @FlightID)
	BEGIN 
		RAISERROR ('Plan must exist', 14, 1)
		RETURN 
	END
	
	Update TripPlan
	Set IsVisible = 0
	Where PlanName = @PlanName and UserID = @UserID and FlightID = @FlightID
END

GO
/****** Object:  StoredProcedure [dbo].[GetDependents]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[GetDependents] @EmployeeID INT
AS
	SELECT Username
	FROM Dependent LEFT JOIN Users ON Dependent.ID = Users.ID
	WHERE Dependent.EmployeeID = @EmployeeID AND Dependent.isDeleted = 0
GO
/****** Object:  StoredProcedure [dbo].[GetFlight]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE Procedure [dbo].[GetFlight]
		@FlightID int
AS
If not exists (SELECT f.ID, f.AirlineID, f.Number, f.FromCode, f.ToCode, f.DepartureDateTime, f.Load
				From Flight f
				Where f.IsVisible = 1 and f.ID = @FlightID)
BEGIN 
	RAISERROR ('Flight does not exist', 1, 1)
	Return
END

				
SELECT f.ID, f.AirlineID, f.Number, f.FromCode, f.ToCode, f.DepartureDateTime, f.Load
From Flight f
Where f.IsVisible = 1 and f.ID = @FlightID

GO
/****** Object:  StoredProcedure [dbo].[getID]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getID] @Username VARCHAR(64) AS
	SELECT ID FROM Users WHERE Username = @Username
GO
/****** Object:  StoredProcedure [dbo].[GetTokens]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[GetTokens]
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
/****** Object:  StoredProcedure [dbo].[GetUser]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[GetUser] @Username VARCHAR(64)
AS
SELECT Salt, PasswordHash
FROM Users
WHERE Username = @Username
GO
/****** Object:  StoredProcedure [dbo].[isDependent]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[isDependent] @Username VARCHAR(64)
AS
SELECT * FROM Dependent JOIN USERS ON Dependent.ID = USERS.ID WHERE USERS.Username = @Username
GO
/****** Object:  StoredProcedure [dbo].[ListAirlines]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
Create Procedure [dbo].[ListAirlines]
AS
SELECT DISTINCT ID
From Airline
GO
/****** Object:  StoredProcedure [dbo].[ListAllPlans]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
Create Procedure [dbo].[ListAllPlans]
	@UserID int
AS
BEGIN
	If (@UserID is null)
	BEGIN 
		RAISERROR ('Inputs may not be null', 14, 1)
		RETURN 
	END
	
	If not exists (Select * from USERS where ID = @UserID)
	BEGIN 
		RAISERROR ('User must exist', 14, 1)
		RETURN 
	END
	
	SELECT DISTINCT PlanName
	FROM TripPlan t
	Where t.IsVisible = 1 and t.UserID = @UserID
END

	
GO
/****** Object:  StoredProcedure [dbo].[ListDestinations]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
Create Procedure [dbo].[ListDestinations]
AS
SELECT DISTINCT IATACode, Name
From Destination
Where IsVisible = 1
GO
/****** Object:  StoredProcedure [dbo].[ListFlights]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE Procedure [dbo].[ListFlights]
		@eID int
AS
SELECT DISTINCT f.ID, f.AirlineID, f.Number, f.FromCode, f.ToCode, f.DepartureDateTime, f.Load
From Employee e
Right Join Flight f on e.AirlineID = f.AirlineID
Where f.IsVisible = 1 and e.ID = @eID

GO
/****** Object:  StoredProcedure [dbo].[ListFlightsConditions]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE Procedure [dbo].[ListFlightsConditions]
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

GO
/****** Object:  StoredProcedure [dbo].[ListLoadRequests]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--Drop Procedure ListLoadRequests
--Go
CREATE Procedure [dbo].[ListLoadRequests]
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

GO
/****** Object:  StoredProcedure [dbo].[ListPlan]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE Procedure [dbo].[ListPlan]
	@PlanName varchar(60),
	@UserID int
AS
BEGIN
	If (@PlanName is null or @PlanName = '' or @UserID is null)
	BEGIN 
		RAISERROR ('Inputs may not be null', 14, 1)
		RETURN 
	END
	
	If not exists (Select * from USERS where ID = @UserID)
	BEGIN 
		RAISERROR ('User must exist', 14, 1)
		RETURN 
	END
	
	SELECT f.ID, f.LastLoadUpdate, f.Number, f.Load, f.DepartureDateTime, f.FromCode, f.ToCode, f.AirlineID
	FROM TripPlan t join Flight f on t.FlightID = f.ID
	Where t.IsVisible = 1 and f.IsVisible = 1 and t.PlanName = @PlanName and t.UserID = @UserID
	Order By t.FlightOrder 
END

	
GO
/****** Object:  StoredProcedure [dbo].[ListWholeFlight]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE Procedure [dbo].[ListWholeFlight]
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
GO
/****** Object:  StoredProcedure [dbo].[listYourLoadRequests]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
Create Procedure [dbo].[listYourLoadRequests]
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
GO
/****** Object:  StoredProcedure [dbo].[RemoveDependent]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[RemoveDependent] @Username VARCHAR(64) AS
	DECLARE @ID INT
	SELECT @ID = Users.ID FROM Dependent JOIN Users ON Dependent.ID = Users.ID WHERE Users.Username = @Username

	UPDATE Dependent
	SET isDeleted = 1
	WHERE ID = @ID
GO
/****** Object:  StoredProcedure [dbo].[UnRemoveDependent]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[UnRemoveDependent] @UserID INT, @Dep VARCHAR(64)
AS
IF NOT EXISTS (SELECT * FROM Dependent JOIN USERS ON Dependent.ID = USERS.ID WHERE USERS.Username = @Dep) BEGIN
	RAISERROR('No Dependent Exists', 16, 1)
	RETURN 1
END

DECLARE @DepID INT
SELECT @DepID = Dependent.ID
FROM Dependent JOIN USERS ON Dependent.ID = USERS.ID
WHERE USERS.Username = @Dep AND Dependent.EmployeeID = @UserID

UPDATE Dependent
SET isDeleted = 0
WHERE ID = @DepID

RETURN 0

GRANT EXECUTE ON UnRemoveDependent TO MyID90Application
GO
/****** Object:  StoredProcedure [dbo].[UpdateFlight]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
Create Procedure [dbo].[UpdateFlight]
		@FlightNumber varchar(32),
		@DepartureDateTime smalldatetime,
		@FromCode char(3),
		@ToCode char(3),
		@AirlineID char(3),
		@ID int
As
Begin
	If (@FlightNumber is null or @DepartureDateTime is null or @FromCode is null or @ToCode is null or @AirlineID is null)
	Begin
		Raiserror ('Inputs must not be null', 14, 1)
		Return
	End
	If Not Exists(Select * From Flight Where ID = @ID And IsVisible = 1)
	Begin
		Raiserror ('Flight does not exist', 14, 1)
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
	Update Flight
	Set Number = @FlightNumber, DepartureDateTime = @DepartureDateTime, FromCode = @FromCode, ToCode = @ToCode, AirlineID = @AirlineID
	Where ID = @ID
	End

GO
/****** Object:  StoredProcedure [dbo].[UpdateLoadRequest]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE Procedure [dbo].[UpdateLoadRequest]
	@FlightID int,
	@Username varchar(64), --the username of the employee who fulfilled the load request
	@NewLoad int
AS
	If (@FlightID is null or @Username is null or @NewLoad is null)
	BEGIN 
		RAISERROR ('Inputs may not be null', 1, 1)
		RETURN 
	END

	Declare @EmployeeID as int
	
	Select @EmployeeID = ID
	From USERS
	Where Username = @Username
	
	If exists (Select * From Updates Where FlightID = @FlightID and EmployeeID = @EmployeeID)
	BEGIN 
	      Update Updates 
	      Set DateAndTime = Getdate(), Load = @NewLoad
	END
	Else
	BEGIN
		INSERT Into Updates (EmployeeID, FlightID, DateAndTime, Load, Token, IsVisible)
		Values (@EmployeeID, @FlightID, Getdate(), @NewLoad, 1, 1)
	END
	
	Update Employee
	Set Tokens = Tokens + 1
	Where ID = @EmployeeID
	
	Update Flight
	Set Load = @NewLoad, LastLoadUpdate = Getdate()
	Where ID = @FlightID

GO
/****** Object:  StoredProcedure [dbo].[UpdateLoadRequestCost]    Script Date: 5/16/2024 8:48:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE Procedure [dbo].[UpdateLoadRequestCost]
		@EmployeeID int,
		@FlightID int,
		@NewTokenCost int
As
Begin
	If (Not Exists(Select * From Flight Where ID = @FlightID))
		Begin
			Raiserror('Flight does not exist.', 14, 1)
			Return 1;
		End
	If (Not Exists(Select * From Employee Where ID = @EmployeeID))
		Begin
			Raiserror('Employee does not exist.', 14, 1)
			Return 1;
		End
	If ((Select AirlineID From Employee Where ID = @EmployeeID) = (Select AirlineID From Flight Where ID = @FlightID))
		Begin
			Raiserror('Employee and Flight AirlineID are the same', 15, 1)
			Return 1;
		End	
	If (Not Exists(Select * From SubmitsLoadRequest Where EmployeeID = @EmployeeID and FlightID = @FlightID))
		Begin
			Raiserror('Load request does not exist', 15, 1)
			Return 1
		End
	If (0 = (Select IsVisible From SubmitsLoadRequest Where EmployeeID = @EmployeeID and FlightID = @FlightID))
		Begin
			Raiserror('Load request is deleted', 15, 1)
			Return 1
		End

	If (@NewTokenCost is not null)
		Begin
			Declare @EmployeeTokens as int
			Set @EmployeeTokens = (Select Tokens From Employee Where ID = @EmployeeID)
	
			Declare @LoadRequestTokenCost as int
			Set @LoadRequestTokenCost = (Select Token From SubmitsLoadRequest Where EmployeeID = @EmployeeID and FlightID = @FlightID and IsVisible = 1)

	
			If ((@NewTokenCost - @LoadRequestTokenCost) > @EmployeeTokens)
				Begin
					Raiserror('Employee does not have enough tokens', 14, 1)
					Return 1;
				End
	
			If (@NewTokenCost > (@LoadRequestTokenCost))
				Begin
					Update Employee
					Set Tokens = Tokens - (@NewTokenCost - @LoadRequestTokenCost)
					Where ID = @EmployeeID
		
					Update SubmitsLoadRequest
					Set Token = @NewTokenCost
					Where EmployeeID = @EmployeeID and FlightID = @FlightID
				End


			If (@NewTokenCost < @LoadRequestTokenCost)
				Begin
					Update Employee
					Set Tokens = Tokens + (@LoadRequestTokenCost - @NewTokenCost)
					Where ID = @EmployeeID

					Update SubmitsLoadRequest
					Set Token = @NewTokenCost
					Where EmployeeID = @EmployeeID and FlightID = @FlightID
				End
			End
		Else
			Begin
				Raiserror('No token value', 15, 1)
				Return 1
			End
End
GO

GRANT EXECUTE ON CreateAirline TO MyID90Application
GRANT EXECUTE ON CreateDestination TO MyID90Application
GRANT EXECUTE ON CreateFlight TO MyID90Application
GRANT EXECUTE ON CreateLoadRequest TO MyID90Application
GRANT EXECUTE ON CreatePlan TO MyID90Application
GRANT EXECUTE ON CreateUpdate TO MyID90Application
GRANT EXECUTE ON CreateUserDependent TO MyID90Application
GRANT EXECUTE ON CreateUserEmployee TO MyID90Application
GRANT EXECUTE ON DeleteLoadRequest TO MyID90Application
GRANT EXECUTE ON DropAirline TO MyID90Application
GRANT EXECUTE ON DropDependent TO MyID90Application
GRANT EXECUTE ON DropDestination TO MyID90Application
GRANT EXECUTE ON DropEmployee TO MyID90Application
GRANT EXECUTE ON DropFlight TO MyID90Application
GRANT EXECUTE ON DropPlan TO MyID90Application
GRANT EXECUTE ON DropPlanItem TO MyID90Application
GRANT EXECUTE ON GetDependents TO MyID90Application
GRANT EXECUTE ON GetFlight TO MyID90Application
GRANT EXECUTE ON getID TO MyID90Application
GRANT EXECUTE ON GetTokens TO MyID90Application
GRANT EXECUTE ON GetUser TO MyID90Application
GRANT EXECUTE ON isDependent TO MyID90Application
GRANT EXECUTE ON ListAirlines TO MyID90Application
GRANT EXECUTE ON ListAllPlans TO MyID90Application
GRANT EXECUTE ON ListDestinations TO MyID90Application
GRANT EXECUTE ON ListFlights TO MyID90Application
GRANT EXECUTE ON ListFlightsConditions TO MyID90Application
GRANT EXECUTE ON ListLoadRequests TO MyID90Application
GRANT EXECUTE ON ListPlan TO MyID90Application
GRANT EXECUTE ON ListWholeFlight TO MyID90Application
GRANT EXECUTE ON listYourLoadRequests TO MyID90Application
GRANT EXECUTE ON RemoveDependent TO MyID90Application
GRANT EXECUTE ON UnRemoveDependent TO MyID90Application
GRANT EXECUTE ON UpdateFlight TO MyID90Application
GRANT EXECUTE ON UpdateLoadRequest TO MyID90Application
GRANT EXECUTE ON UpdateLoadRequestCost TO MyID90Application

USE [master]
GO
ALTER DATABASE [ProjectDatabaseS2G4] SET  READ_WRITE 
GO
