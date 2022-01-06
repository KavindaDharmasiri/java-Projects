#mysql -h localhost -p 1234 -u root

DROP DATABASE IF EXISTS AlphaHealthCareHospital;
CREATE DATABASE IF NOT EXISTS AlphaHealthCareHospital;
SHOW DATABASES ;
USE AlphaHealthCareHospital;

#------------------------------------------------------

DROP TABLE IF EXISTS `loginDetail`;
CREATE TABLE IF NOT EXISTS `loginDetail`(
    userName VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL,
    type VARCHAR(10),
    CONSTRAINT PRIMARY KEY (userName)
    );
SHOW TABLES ;
DESCRIBE `loginDetail`;

#------------------------------------------------------

DROP TABLE IF EXISTS Doctor;
CREATE TABLE IF NOT EXISTS Doctor(
    docId VARCHAR(6),
    docName VARCHAR(30) NOT NULL DEFAULT 'Unknown',
    specialist VARCHAR(20),
    address VARCHAR(20),
    nic VARCHAR(12),
    contact VARCHAR(10),
    CONSTRAINT PRIMARY KEY (docId)
    );
SHOW TABLES ;
DESCRIBE Doctor;

#------------------------------------------------------

DROP TABLE IF EXISTS Patient;
CREATE TABLE IF NOT EXISTS Patient(
    patientId VARCHAR(6),
    patientName VARCHAR(20) NOT NULL DEFAULT 'Unknown',
    address VARCHAR(30),
    age int(2),
    nic VARCHAR(12),
    contact VARCHAR(10),
    CONSTRAINT PRIMARY KEY (patientId)
    );
SHOW TABLES ;
DESCRIBE Patient;

#------------------------------------------------------

DROP TABLE IF EXISTS Nurse;
CREATE TABLE IF NOT EXISTS Nurse(
    nurseId VARCHAR(6),
    nurseName VARCHAR(30) NOT NULL DEFAULT 'Unknown',
    address VARCHAR(50),
    nic VARCHAR(12),
    contact VARCHAR(10),
    CONSTRAINT PRIMARY KEY (nurseId)
    );
SHOW TABLES ;
DESCRIBE Nurse;

#------------------------------------------------------

DROP TABLE IF EXISTS Apointment;
CREATE TABLE IF NOT EXISTS Apointment(
    ApoinmentId VARCHAR(6),
    patientId VARCHAR(6),
    docId VARCHAR(6),
    nurseId VARCHAR(6),
    time VARCHAR(5),
    date VARCHAR(10),
    CONSTRAINT PRIMARY KEY (ApoinmentId,patientId,docId,nurseId),
    CONSTRAINT FOREIGN KEY (patientId) REFERENCES Patient (patientId)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (docId) REFERENCES Doctor (docId)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (nurseId) REFERENCES Nurse (nurseId)
        ON UPDATE CASCADE ON DELETE CASCADE
    );
SHOW TABLES ;
DESCRIBE Apointment;

#------------------------------------------------------

DROP TABLE IF EXISTS Room;
CREATE TABLE IF NOT EXISTS Room(
    roomId VARCHAR(6),
    type VARCHAR(50),
    Availability VARCHAR(20),
    price decimal(7,2),
    CONSTRAINT PRIMARY KEY (roomId)
    );
SHOW TABLES ;
DESCRIBE Room;

#------------------------------------------------------

DROP TABLE IF EXISTS Lab;
CREATE TABLE IF NOT EXISTS Lab(
    labId VARCHAR(20),
    type VARCHAR(20),
    availability VARCHAR(20),
    CONSTRAINT PRIMARY KEY (labId)
    );
SHOW TABLES ;
DESCRIBE Lab;

#------------------------------------------------------

DROP TABLE IF EXISTS SurgicalEqupment;
CREATE TABLE IF NOT EXISTS SurgicalEqupment(
    equpmentId VARCHAR(6),
    name VARCHAR(20),
    availability VARCHAR(20),
    QtyOnHand Int(3),
    price decimal(7,2),
    CONSTRAINT PRIMARY KEY (equpmentId)
    );
SHOW TABLES ;
DESCRIBE SurgicalEqupment;

#------------------------------------------------------

DROP TABLE IF EXISTS Drink;
CREATE TABLE IF NOT EXISTS Drink(
    drinkId VARCHAR(6),
    name VARCHAR(20),
    availability VARCHAR(20),
    QtyOnHand Int(3),
    price decimal(10,2),
    CONSTRAINT PRIMARY KEY (drinkId)
    );
SHOW TABLES ;
DESCRIBE Drink;

#------------------------------------------------------

DROP TABLE IF EXISTS Cloth;
CREATE TABLE IF NOT EXISTS Cloth(
    clothId VARCHAR(6),
    name VARCHAR(20),
    availability VARCHAR(20),
    price decimal(7,2),
    CONSTRAINT PRIMARY KEY (clothId)
    );
SHOW TABLES ;
DESCRIBE Cloth;

#------------------------------------------------------

DROP TABLE IF EXISTS Medicine;
CREATE TABLE IF NOT EXISTS Medicine(
    medicineId VARCHAR(6),
    name VARCHAR(20),
    availability VARCHAR(20),
    QtyOnHand Int(3),
    price decimal(7,2),
    CONSTRAINT PRIMARY KEY (medicineId)
    );
SHOW TABLES ;
DESCRIBE Medicine;

#------------------------------------------------------

DROP TABLE IF EXISTS Parking;
CREATE TABLE IF NOT EXISTS Parking(
    parkingSlot VARCHAR(6),
    availability VARCHAR(20),
    CONSTRAINT PRIMARY KEY (parkingSlot)
    );
SHOW TABLES ;
DESCRIBE Parking;

#------------------------------------------------------

DROP TABLE IF EXISTS ParkingDetail;
CREATE TABLE IF NOT EXISTS ParkingDetail(
    patId VARCHAR(6),
    parkingSlot VARCHAR(6),
    CONSTRAINT PRIMARY KEY (patId,parkingSlot)
    );
SHOW TABLES ;
DESCRIBE ParkingDetail;

#------------------------------------------------------

DROP TABLE IF EXISTS MedicineDetail;
CREATE TABLE IF NOT EXISTS MedicineDetail(
    patId VARCHAR (6),
    medicineId VARCHAR(6),
    Qty Int(3),
    price decimal(10,2),
    CONSTRAINT PRIMARY KEY (patId,medicineId),
    CONSTRAINT FOREIGN KEY (patId) REFERENCES Patient (patientId)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (medicineId) REFERENCES Medicine (medicineId)
        ON UPDATE CASCADE ON DELETE CASCADE
    );
SHOW TABLES ;
DESCRIBE MedicineDetail;

#------------------------------------------------------

DROP TABLE IF EXISTS DrinkDetail;
CREATE TABLE IF NOT EXISTS DrinkDetail(
    patId VARCHAR(6),
    drinkId VARCHAR(6),
    Qty Int(3),
    price decimal(10,2),
    CONSTRAINT PRIMARY KEY (patId,drinkId),
    CONSTRAINT FOREIGN KEY (patId) REFERENCES Patient (patientId)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (drinkId) REFERENCES Drink (drinkId)
        ON UPDATE CASCADE ON DELETE CASCADE
    );
SHOW TABLES ;
DESCRIBE DrinkDetail;

#------------------------------------------------------

DROP TABLE IF EXISTS SurgicalEqupmentDetail;
CREATE TABLE IF NOT EXISTS SurgicalEqupmentDetail(
    patId VARCHAR(6),
    equpmentId VARCHAR(6),
    Qty Int(3),
    price decimal(10,2),
    CONSTRAINT PRIMARY KEY (patId,equpmentId),
    CONSTRAINT FOREIGN KEY (patId) REFERENCES Patient (patientId)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (equpmentId) REFERENCES SurgicalEqupment (equpmentId)
        ON UPDATE CASCADE ON DELETE CASCADE
    );
SHOW TABLES ;
DESCRIBE SurgicalEqupmentDetail;

#------------------------------------------------------

DROP TABLE IF EXISTS RoomDetail;
CREATE TABLE IF NOT EXISTS RoomDetail(
    patId VARCHAR(6),
    roomId VARCHAR(6),
    price decimal(6,2),
    CONSTRAINT PRIMARY KEY (patId,roomId)
    );
SHOW TABLES ;
DESCRIBE RoomDetail;

#------------------------------------------------------

DROP TABLE IF EXISTS DoctorPatientDetail;
CREATE TABLE IF NOT EXISTS DoctorPatientDetail(
    apoinmentId VARCHAR(6),
    patientId VARCHAR(6),
    pName VARCHAR(20),
    labId VARCHAR(6),
    drinkId VARCHAR(200),
    clothId VARCHAR(6),
    medicineId VARCHAR(200),
    eqId VARCHAR(200),
    CONSTRAINT PRIMARY KEY (apoinmentId,patientId),
    CONSTRAINT FOREIGN KEY (apoinmentId) REFERENCES Apointment (apoinmentId)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (patientId) REFERENCES Patient (patientId)
        ON UPDATE CASCADE ON DELETE CASCADE
    );
SHOW TABLES ;
DESCRIBE DoctorPatientDetail;

#------------------------------------------------------

DROP TABLE IF EXISTS Final;
CREATE TABLE IF NOT EXISTS Final(
    ApointmentId VARCHAR(6),
    patientId VARCHAR(6),
    patientName VARCHAR(30),
    docId VARCHAR(6),
    nurseId VARCHAR(6),
    date VARCHAR(10),
    time VARCHAR(5),
    medicine VARCHAR(200),
    eq VARCHAR(200),
    drink VARCHAR(200),
    cloth VARCHAR(6),
    room VARCHAR(6),
    lab VARCHAR(6),
    parkingSlot VARCHAR(6),
    price decimal(20,2),
    success VARCHAR(10),
    CONSTRAINT PRIMARY KEY (patientId)
    );
SHOW TABLES ;
DESCRIBE Final;

#------------------------------------------------------

DROP TABLE IF EXISTS PatientFinal;
CREATE TABLE IF NOT EXISTS PatientFinal(
    ApointmentId VARCHAR(6),
    patId VARCHAR(6),
    patName VARCHAR(30),
    patAddress VARCHAR(30),
    patAge VARCHAR(30),
    nic VARCHAR(30),
    contact VARCHAR(30),
    docName VARCHAR(30),
    nurseName VARCHAR(30),
    roomType VARCHAR(30),
    roomPrice decimal(6,2),
    labType VARCHAR(30),
    eqName VARCHAR(200),
    eqPrice decimal(10,2),
    drinkName VARCHAR(200),
    drinkPrice decimal(10,2),
    clothName VARCHAR(30),
    clothPrice decimal(6,2),
    mediName VARCHAR(200),
    mediPrice VARCHAR(30),
    date VARCHAR(10),
    time VARCHAR(5),
    fullTotal decimal(10,2)
    );
SHOW TABLES ;
DESCRIBE PatientFinal;

#------------------------------------------------------

DROP TABLE IF EXISTS finalReport;
CREATE TABLE IF NOT EXISTS finalReport(
    dailyIncome VARCHAR(15) DEFAULT "null",
    monthlyIncome VARCHAR(15) DEFAULT "null",
    yearlyIncome VARCHAR(15) DEFAULT "null"
    );
SHOW TABLES ;
DESCRIBE finalReport;

#------------------------------------------------------

DROP TABLE IF EXISTS ApointmentDetailForReport;
CREATE TABLE IF NOT EXISTS ApointmentDetailForReport(
    ApoinmentId VARCHAR(6),
    patientName VARCHAR(30),
    doctorName VARCHAR(30),
    nurseName VARCHAR(30),
    time VARCHAR(5),
    date VARCHAR(10)
    );
SHOW TABLES ;
DESCRIBE ApointmentDetailForReport;

#------------------------------------------------------