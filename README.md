# Game Management System (Java + Oracle)

## Overview

This project is a desktop-based Game Management System developed using Java (Swing) and Oracle Database. It provides a simple interface to manage players, games, and match records. The application demonstrates database connectivity using JDBC and follows a basic DAO (Data Access Object) structure for separation of concerns.

## Features

* View all players, games, and matches
* Add new players, games, and match records
* Display relational data using SQL JOIN operations
* Graphical User Interface built with Java Swing
* Table-based data display using JTable
* Modular code structure using DAO classes

## Technologies Used

* Java (JDK 8)
* Java Swing (GUI)
* JDBC (Java Database Connectivity)
* Oracle Database (XE or equivalent)
* SQL

## Database Schema

### Players Table

* player_id (Primary Key)
* username
* player_level
* country

### Games Table

* game_id (Primary Key)
* game_name
* genre

### Matches Table

* match_id (Primary Key)
* player_id (Foreign Key)
* game_id (Foreign Key)
* score
* match_date

## Project Structure

* DBConnection.java: Handles database connection
* PlayerDAO.java: Operations related to Players table
* GameDAO.java: Operations related to Games table
* MatchDAO.java: Operations related to Matches table
* GUI.java: Main graphical user interface

## Setup Instructions

1. Install Java JDK 8 or later
2. Install Oracle Database (XE recommended)
3. Ensure Oracle service is running
4. Place the JDBC driver (ojdbc14-10.2.0.4.jar) in the project directory
5. Update database credentials in DBConnection.java if needed

## Compilation

Using Java 1.8 compatibility:

javac -source 1.8 -target 1.8 -cp ".;ojdbc14-10.2.0.4.jar" *.java

Alternatively (recommended for newer JDKs):

javac --release 8 -cp ".;ojdbc14-10.2.0.4.jar" *.java

## Execution

java -cp ".;ojdbc14-10.2.0.4.jar" GUI

## How It Works

* The application connects to the Oracle database using JDBC.
* DAO classes handle all SQL operations such as insert and select.
* The GUI interacts with DAO methods to fetch and display data.
* Match data is displayed using JOIN queries to combine player and game information.

## Limitations

* No update or delete functionality
* Basic input validation
* Simple UI without advanced styling or responsiveness
* Minimal error handling

## Future Enhancements

* Add update and delete operations
* Improve input validation and exception handling
* Enhance GUI with better layouts and styling
* Add search and filtering capabilities
* Implement login/authentication system

## Conclusion

This project demonstrates a complete integration of Java GUI with a relational database using JDBC. It showcases fundamental concepts such as database connectivity, SQL operations, and user interface development in Java.
