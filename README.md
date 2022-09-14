# demo-dao-jdbc
<p>Exercício sobre Banco de dados em java, utilizando DAO e  mysql</p>
<br>
<p>Para utilizar, basta alterar o properties.txt dentro da pasta para seu banco de dados</p>
<h2>Script do BD: </h2>
<br>
CREATE TABLE department (<br>
  Id int(11) NOT NULL AUTO_INCREMENT,<br>
  Name varchar(60) DEFAULT NULL,<br>
  PRIMARY KEY (Id)<br>
);<br>
<br>
CREATE TABLE seller (<br>
  Id int(11) NOT NULL AUTO_INCREMENT,<br>
  Name varchar(60) NOT NULL,<br>
  Email varchar(100) NOT NULL,<br>
  BirthDate datetime NOT NULL,<br>
  BaseSalary double NOT NULL,<br>
  DepartmentId int(11) NOT NULL,<br>
  PRIMARY KEY (Id),<br>
  FOREIGN KEY (DepartmentId) REFERENCES department (id)<br>
);<br>
<br>
INSERT INTO department (Name) VALUES <br>
  ('Computers'),<br>
  ('Electronics'),<br>
  ('Fashion'),<br>
  ('Books');<br>
<br>
INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) VALUES <br>
  ('Bob Brown','bob@gmail.com','1998-04-21 00:00:00',1000,1),<br>
  ('Maria Green','maria@gmail.com','1979-12-31 00:00:00',3500,2),<br>
  ('Alex Grey','alex@gmail.com','1988-01-15 00:00:00',2200,1),<br>
  ('Martha Red','martha@gmail.com','1993-11-30 00:00:00',3000,4),<br>
  ('Donald Blue','donald@gmail.com','2000-01-09 00:00:00',4000,3),<br>
  ('Alex Pink','bob@gmail.com','1997-03-04 00:00:00',3000,2);<br>
