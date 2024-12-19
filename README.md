# Car Rental App

## Description 

"Car Rental App" is a command-line Java application. It is built in using Maven for dependency management and project structure. 

## Table of Contents

- [Prerequisites](#prerequisites)
- [How to Set Up and Run](#how-to-set-up-and-run)
  - [Cloning the Repository](#cloning-the-repository)
  - [Setting up the database](#setting-up-the-database)
  - [Building the Project](#building-the-project)
  - [Running the Application](#running-the-application)
- [Instructions](#instructions)
- [Testing](#testing)
- [Contributors](#contributors)



## Prerequisites 

Please ensure you have the following installed to setup and run the application.

- Java 21+
- Mavan 3.9.9+
- pgAdmin4

### Cloning the repository 

To clone the repository, use the following command: 

 ```bash
  git clone https://github.com/pockche123/CarRentalApp.git
  cd <CarRentalApp Directory>
  ```
### Setting up the database 

To set up the database, go to the resources folder to locate the CarRental.sql file. 
 - Copy the CarRental.sql file.
 - In the pgAdmin4, create a new database named CarRental.
 - Paste and Run the contents of the sql file in the CarRental database.
 - Update the postgres password to "password".

### Building the project 

To buid the project using maven, use the following command: 

 ```bash
  mvn clean install 
  ```

### Running the application 

To run the application after it has been successfully built, use the following command: 

 ```bash
  java -jar target/CarRentals-1.0-SNAPSHOT.jar
  ```


## Instructions 

Follow the on-screen prompts provided by the app as it runs

## Testing 

To run the unit tests, use the following command: 

 ```bash
  mvn test
  ```

## Contributors 

- Parjal Rai
- Jeffery Aideyan-Ohonba
- Ridwa Nul Hoque









