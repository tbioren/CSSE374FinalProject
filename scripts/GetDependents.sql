CREATE PROCEDURE GetDependents @EmployeeID INT
AS
SELECT * FROM Dependent WHERE EmployeeID = @EmployeeID
    GO