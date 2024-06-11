USE ProjectDatabaseS2G4 
GO 

DROP TABLE Airline 
DROP TABLE Flight 

CREATE TABLE Airline ( 
    ID CHAR(3) PRIMARY KEY, 
    Alliance VARCHAR(32),
    IsVisible bit
) 

CREATE TABLE Flight ( 
    ID INT IDENTITY(0,1) PRIMARY KEY, 
    LastLoadUpdate DATE, 
    Number VARCHAR(32),
    Load INT, 
    DepartureDateTime SMALLDATETIME, 
    FromCode CHAR(3), 
    ToCode CHAR(3), 
    AirlineID CHAR(3),
    IsVisible bit,
    FOREIGN KEY (AirlineID) REFERENCES Airline(ID) 
)

Create Table Employee ( 
	ID int Primary Key, 
	FirstName varchar(20) NOT NULL, 
	LastName varchar(20) NOT NULL, 
	Tokens int, 
	AirlineID char(3), 
	Foreign Key(AirlineID) References Airline(ID) 
)  

Create Table Dependent ( 
	ID int Primary Key, 
	FirstName varchar(20) NOT NULL, 
	LastName varchar(20) NOT NULL, 
	EmployeeID int, 
	isDeleted bit,
	Foreign Key(EmployeeID) References Employee(ID) 
)

Create Table SubmitsLoadRequest ( 
	EmployeeID int, 
	FlightID int,  
	DateAndTime Date,  
	Token int, 
	IsVisible bit,
	Primary Key (EmployeeID, FlightID), 
	Foreign Key (EmployeeID) References Employee(ID), 
	Foreign Key (FlightID) References Flight(ID) 
) 

Create Table Updates (  
	EmployeeID int,  
	FlightID int,   
	DateAndTime Date,   
	[Load] int,  
	Token int,
	IsVisible bit,
	Primary Key (EmployeeID, FlightID), 
	Foreign Key (EmployeeID) References Employee(ID), 
	Foreign Key (FlightID) References Flight(ID)
)

Create Table Destination (
       IATACode char(3),
       Name varchar(32),
       IsVisible bit,
       Primary Key (IATACode)
)

Create Table TripPlan (
	FlightID int,
	UserID int,
	PlanName varchar(60),
	FlightOrder int,
	IsVisible bit,
	Primary Key (FlightID, UserID, PlanName),
	Foreign Key (FlightID) References Flight(ID),
	Foreign Key (UserID) References USERS(ID)
)
