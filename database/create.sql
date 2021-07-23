--Suppressions des tables

DROP TABLE IF EXISTS tAntecedents;
DROP TABLE IF EXISTS tNotes;
DROP TABLE IF EXISTS tVisits;
DROP TABLE IF EXISTS tDiagnostics;
DROP TABLE IF EXISTS tTreatments;
DROP TABLE IF EXISTS tFolders;
DROP TABLE IF EXISTS tPatients;
DROP TABLE IF EXISTS tParents;
DROP TABLE IF EXISTS tContacts;
DROP TABLE IF EXISTS tUsers;
DROP TABLE IF EXISTS tDoctors;
DROP TABLE IF EXISTS tEstablishments;

--Création des tables

CREATE TABLE IF NOT EXISTS tEstablishments (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
   	identification TEXT NOT NULL,
	designation TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS tDoctors (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
   	firstname TEXT NOT NULL,
	lastname TEXT NOT NULL,
	permit TEXT NOT NULL UNIQUE,
	speciality TEXT NOT NULL,
	establishment INTEGER REFERENCES tEstablishments
);

CREATE TABLE IF NOT EXISTS tUsers (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
   	username TEXT NOT NULL UNIQUE,
	password TEXT NOT NULL,
	doctor INTEGER REFERENCES tDoctors
);


CREATE TABLE IF NOT EXISTS tContacts (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	address TEXT,
	phone TEXT,
	email TEXT
);

CREATE TABLE IF NOT EXISTS tParents (
    fils   INTEGER NOT NULL REFERENCES tPatients,
    parent   INTEGER NOT NULL REFERENCES tPatients
);

CREATE TABLE IF NOT EXISTS tPatients (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	firstname TEXT NOT NULL,
    lastname TEXT NOT NULL,
    gender TEXT NOT NULL,
    dateOfBirth INTEGER NOT NULL,
    birthCity TEXT NOT NULL,
    insuranceNumber TEXT NOT NULL,
	contact INTEGER REFERENCES tContacts
);

CREATE TABLE IF NOT EXISTS tFolders (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	owner INTEGER REFERENCES tPatients
);

CREATE TABLE IF NOT EXISTS tTreatments (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
   	designation TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS tDiagnostics (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
   	designation TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS tVisits (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
   	summary TEXT NOT NULL,
	date_ INTEGER NOT NULL,
	treatment INTEGER REFERENCES tTreatments,
	diagnostic INTEGER REFERENCES tDiagnostics,
	folder INTEGER REFERENCES tFolders,
	doctor INTEGER REFERENCES tDoctors
);

CREATE TABLE IF NOT EXISTS tNotes (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
   	content TEXT NOT NULL,
   	visit INTEGER REFERENCES tVisits
);

CREATE TABLE IF NOT EXISTS tAntecedents (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
   	biginning INTEGER,
	end_ INTEGER,
	treatment INTEGER REFERENCES tTreatments,
	diagnostic INTEGER REFERENCES tDiagnostics,
	folder INTEGER REFERENCES tFolders,
	prescriber INTEGER REFERENCES tDoctors
);



--Insertion des données de test

INSERT INTO tEstablishments (identification, designation)
VALUES
    ('123456' , 'CHUM de montréal'),
    ('111111' , 'Hopital juif');

INSERT INTO tDoctors (firstname, lastname, permit, speciality, establishment)
VALUES
    ('Marsilius', 'Dupuis', '13698', 'médecine de famille', 1),
    ('Telford', 'Bouvier', '36509', 'médecine de famille', 2),
    ('Antoine', 'Jobin', '96887', 'cardiologie', 1);

INSERT INTO tUsers (username, password, doctor)
VALUES
    ('docteur01', '111111', 1),
    ('docteur02', '222222', 2),
    ('docteur03', '333333', 3);

INSERT INTO tContacts (address, phone, email)
VALUES
    ('2401 rue Ontario Ouest Montreal, QC H2X 1Y8', '514-350-9159', 'SusanKMorganti@armyspy.com'),
    ('2401 rue Ontario Ouest Montreal, QC H2X 1Y8', '514-698-9412', 'AlexRPokorny@dayrep.com'),
    ('2864 rue Levy Montreal, QC H3C 5K4', '514-926-9832', 'DavidPokorny@yahoo.com'),
    ('1652 Boulevard Cremazie Quebec, QC G1R 1B8', '418-528-6040', 'WilliamS@gmail.com'),
    ('1109 De la Providence Avenue Hull, QC J8Y 4B1', '819-318-7555', NULL);


INSERT INTO tPatients (firstname, lastname, gender, dateOfBirth, birthCity, insuranceNumber, contact)
VALUES
    ('Susan', 'Morganti', 'FEMALE' , strftime ('%s', '1952-02-04') * 1000.1, 'Montreal', 'MORS12452196', 1),
    ('Alex', 'Pokorny', 'MALE' , strftime ('%s', '1948-10-15') * 1000.1, 'Quebec', 'POKA36952365', 2),
    ('David', 'Pokorny', 'MALE' , strftime ('%s', '1982-09-03') * 1000.1, 'Montreal', 'POKD63259145', 3),
    ('William', 'Snider', 'MALE' , strftime ('%s', '1988-03-14') * 1000.1, 'Quebec', 'SNIW65971398', 4),
    ('Regina', 'Leslie', 'FEMALE' , strftime ('%s', '1956-06-13') * 1000.1, 'Trois Rivieres', 'LESRW36946970', 5);

 INSERT INTO tParents (fils, parent)
 VALUES (3,1), (3,2), (4,5);

 INSERT INTO tFolders (owner)
 VALUES (1), (2), (3), (4), (5);

INSERT INTO tPatients (firstname, lastname, gender, dateOfBirth, birthCity, insuranceNumber, contact)
VALUES
    ('Susan', 'Morganti', 'FEMALE' , strftime ('%s', '1952-02-04') * 1000.1, 'Montreal', 'MORS12452196', 1),
    ('Alex', 'Pokorny', 'MALE' , strftime ('%s', '1948-10-15') * 1000.1, 'Quebec', 'POKA36952365', 2),
    ('David', 'Pokorny', 'MALE' , strftime ('%s', '1982-09-03') * 1000.1, 'Montreal', 'POKD63259145', 3),
    ('William', 'Snider', 'MALE' , strftime ('%s', '1988-03-14') * 1000.1, 'Quebec', 'SNIW65971398', 4),
    ('Regina', 'Leslie', 'FEMALE' , strftime ('%s', '1956-06-13') * 1000.1, 'Trois Rivieres', 'LESRW36946970', 5);

INSERT INTO tTreatments (designation)
VALUES
    ('insuline'),
    ('hypoglycemiant oral'),
    ('corticoides'),
    ('anti-inflammatoires non steroidiens'),
    ('thyroxine'),
    ('acide valproique');

INSERT INTO tDiagnostics (designation)
VALUES
    ('diabète type I'),
    ('diabète type II'),
    ('sclérose en plaques'),
    ('troubles du spectre de l''autisme'),
    ('gastro-entérite'),
    ('allergie aux pénicillines'),
    ('maladie de crohn'),
    ('hypothyroidie'),
    ('épilepsie');

INSERT INTO tVisits (summary, date_, treatment, diagnostic, folder, doctor)
VALUES
    ('après consultation de la glycémie du patient ', strftime ('%s', '2021-01-06') * 1000.1, 1, 1, 1, 1 ),
    ('controle médicale après 3 mois de prise d''insuline', strftime ('%s', '2021-04-01') * 1000.1, NULL, NULL, 1, 1 ),
    ('', strftime ('%s', '2021-02-01') * 1000.1, 2, 2, 3, 2 ), -- diabte 2
    ('', strftime ('%s', '2020-11-03') * 1000.1, 3, 3, 5, 3 ), -- sclerose
    ('', strftime ('%s', '2020-12-05') * 1000.1, NULL, 4, 4, 2 ), -- autisme
    ('', strftime ('%s', '2021-05-05') * 1000.1, 6, 9, 4, 2 ), -- epilepsie
    ('', strftime ('%s', '2021-03-06') * 1000.1, NULL, 5, 3, 1 ), -- gastro
    ('', strftime ('%s', '2021-06-02') * 1000.1, NULL, 6, 3, 1 ), -- allergie peni
    ('', strftime ('%s', '2020-10-06') * 1000.1, 4, 7, 2, 1 ), -- crohn
    ('', strftime ('%s', '2021-05-03') * 1000.1, 5, 8, 1, 1 ); -- thyroide


INSERT INTO tNotes (content, visit)
VALUES
    ('note 1 pour visite 1', 1),
    ('note 2 pour visite 1', 1),
    ('note 1 pour visite 2', 2);


INSERT INTO tAntecedents (biginning, end_, treatment, diagnostic, folder, prescriber)
VALUES
    (strftime ('%s', '2021-01-06') * 1000.1, NULL, 1, 1, 1, 1 ),
    (strftime ('%s', '2021-02-01') * 1000.1, NULL, 2, 2, 3, 2 ),
    (strftime ('%s', '2020-11-03') * 1000.1, NULL, 3, 3, 5, 3 ),
    (NULL, NULL, NULL, 4, 4, 2 ),
    (strftime ('%s', '2021-05-05') * 1000.1, NULL, 6, 9, 4, 2 ),
    (strftime ('%s', '2021-06-02') * 1000.1, NULL, NULL, 6, 3, 1 ),
    (strftime ('%s', '2020-10-06') * 1000.1, NULL, 4, 7, 2, 1 ),
    (strftime ('%s', '2021-05-03') * 1000.1, NULL, 5, 8, 1, 1 );


