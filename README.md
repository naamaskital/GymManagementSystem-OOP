# Gym Management System – Java OOP Project

## Overview

This project implements a **Gym Management System** in Java using Object-Oriented Programming principles.

The system simulates the management of a gym, including client registration, instructor hiring, session creation, client enrollment, payments, notifications, and action history tracking. The project focuses on building a structured Java application with multiple interacting entities and clear responsibility separation between classes.

The implementation demonstrates core OOP concepts such as encapsulation, inheritance, abstraction, polymorphism, custom exceptions, and design patterns.

## Main Features

* Gym management using a centralized `Gym` class
* Client registration and unregistration
* Instructor hiring and qualification management
* Session creation by type, date, forum, and instructor
* Client enrollment in sessions
* Validation of session requirements
* Balance updates and payment handling
* Salary payment for employees
* Notification system for clients
* Action history tracking for secretary operations
* Custom exception handling for invalid actions
* Automatic output checking using a Python script

## System Entities

### Gym

Represents the gym itself and manages the main system state, including clients, instructors, sessions, gym balance, and the active secretary.

The `Gym` class is implemented as a Singleton, ensuring that only one gym instance exists during the program execution.

### Secretary

Represents the active gym secretary and serves as the main access point for management operations.

The secretary can:

* Register and unregister clients
* Hire instructors
* Create sessions
* Register clients to lessons
* Send notifications
* Pay salaries
* Print the action history

Former secretaries are deactivated and are not allowed to continue performing system actions.

### Client

Represents a registered gym client.

Each client has personal details, balance information, and a list of received notifications.

### Instructor

Represents a gym instructor.

Each instructor has a salary per hour and a list of session types they are qualified to teach.

### Session

Represents a gym lesson or class.

Each session includes:

* Session type
* Date and time
* Forum type
* Instructor
* Registered participants
* Maximum participant limit
* Price

## Session Types and Rules

The system supports multiple session types, such as:

* Pilates
* Machine Pilates
* Thai Boxing
* Ninja

Each session type may have a different price, participant limit, and requirements.

Before registering a client to a session, the system checks conditions such as:

* The client is registered in the gym
* The client is not already registered to the same session
* The session is in the future
* The client matches the forum requirements
* The client has enough balance
* The session has available spots

## Forum Types

Sessions can be limited by forum type, for example:

* All
* Male
* Female
* Seniors

This allows the system to validate whether a client is allowed to join a specific session.

## Exception Handling

The project includes custom exceptions for invalid system actions, such as:

* Trying to register a client who is too young
* Trying to register the same client twice
* Trying to unregister a client who is not registered
* Trying to assign an instructor to a session type they are not qualified to teach
* Trying to enroll an unregistered client in a lesson

These exceptions make the system behavior clearer and help separate error handling from the main business logic.

## Notifications

The system supports sending notifications to clients in several ways:

* To all clients registered to a specific session
* To all clients registered for sessions on a specific date
* To all gym clients

Each client stores the notifications they receive.

## Action History

The secretary maintains a history of management actions performed in the system.

Examples of tracked actions include:

* Registering clients
* Hiring instructors
* Creating sessions
* Registering clients to lessons
* Sending notifications
* Paying salaries
* Replacing the active secretary

This makes the system easier to debug, test, and understand.

## Project Structure

```text
.
├── Main.java
├── auto_check.py
├── output.txt
└── gym/
    ├── Exception/
    │   ├── ClientNotRegisteredException.java
    │   ├── DuplicateClientException.java
    │   ├── InstructorNotQualifiedException.java
    │   └── InvalidAgeException.java
    ├── customers/
    │   ├── Balance.java
    │   ├── Client.java
    │   ├── Gender.java
    │   ├── Instructor.java
    │   ├── Observer.java
    │   └── Person.java
    └── management/
        ├── Gym.java
        ├── Secretary.java
        └── Sessions/
```

## Technologies Used

* Java
* Object-Oriented Programming
* Custom Exceptions
* Singleton Pattern
* Factory Pattern
* Inheritance and Interfaces
* Encapsulation
* Python for automatic output checking

## How to Run

Compile the project:

```bash
javac Main.java
```

Run the program:

```bash
java Main
```

To run the automatic output check:

```bash
python auto_check.py
```

The checker compiles and runs the Java program, then compares the generated output with the expected output in `output.txt`.

## What I Learned

This project strengthened my experience with designing a full object-oriented system in Java.

Through this project, I practiced:

* Designing multiple interacting classes
* Separating responsibilities between system entities
* Using inheritance and encapsulation
* Implementing custom exceptions
* Managing system state
* Validating business rules
* Handling edge cases
* Tracking actions and system outputs
* Writing code that matches strict expected behavior

## Future Improvements

Possible future improvements include:

* Adding unit tests
* Refactoring the project into a Maven or Gradle structure
* Adding a graphical user interface
* Improving the notification mechanism
* Adding persistent storage using files or a database
* Removing compiled `.class` files from the repository
* Adding more detailed documentation for each class
