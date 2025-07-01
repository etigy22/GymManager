Project by Noé, Felix, Etienne
# Gym Manager

## Introduction

Gym Manager is a Java application designed to help manage gym memberships, courses, and enrollments. The project is structured using a modular approach with repositories, services, and a user interface layer. It is built with Gradle for easy dependency management and building, and it uses SQLite for local data storage.

### Project Requirements

- UML-Klassendiagramm (z.B. mit draw.io) `/uml`
- Namenskonventionen `done`
- Schleifen (for, while, do while) `done`
- Bedingte Anweisungen (If-Else, Case) `done`
- Einsatz komplexer Datenstrukturen (z.B. multidimensionale Arrays, etc. ...) `src/main/java/com/gymmanager/repository`
- Klassen `done`
- Zugriffskontrolle (private, protected, public) `done`
- Vererbung `zB: src/main/java/com/gymmanager/ui`
- Abstrakte Klassen, bzw. Interfaces `src/main/java/com/gymmanager/repository`
- Paketstruktur `done`
- Exception Handling `src/main/java/com/gymmanager/exception`
- Javadoc Dokumentation `/doc`
- Unit-Tests (z.B. mit JUnit, TestNG) `src/test/java/com/gymmanager`
- Build-Tools (Ant, Maven, Gradle) `done`
- Einbindung externer Java Bibliotheken (z.B. SQLite, JUnit) `done`

### Features

- CRUD operations for gym members
- CRUD operations for courses
- Enroll members in courses
- Track course attendance
- Data persistence using SQLite
- User-friendly interface for staff operations

## How to Run

### Prerequisites

- Java 21 or higher
- Gradle 22 or higher (or use the included Gradle Wrapper)

### Other dependencies (managed by Gradle)

- org.xerial:sqlite-jdbc:3.42.0.0 (SQLite JDBC Driver)
- org.junit.jupiter:junit-jupiter:5.10.2 (JUnit Jupiter for testing)

### Build and Run on Mac/Linux

1. **Clone the repository** and navigate to the project root directory.

2. **Build the project** using Gradle:

   ```sh
   ./gradlew build

3. **Run the application**:

   ```sh
   ./gradlew run
   ```
- **Note:** Alternatively, after building your project using Gradle you may be able to run the main class directly from your IDE. Find it in `src/main/java/com/gymmanager/ui/GymApp.java`.

### Running on Windows

1. Ensure Java is installed and `JAVA_HOME` is set in your environment variables.
2. Open Command Prompt.
3. Run: `gradlew.bat build`

## Design Patterns Used

- **Singleton**: Ensures a course has only one instance and provides a global point of access to it.
- **Factory**: Creates objects without exposing the instantiation logic to the client.
- **Observer**: Allows objects to be notified of state changes in other objects.
- **MVC (Model-View-Controller)**: Separates application logic, user interface, and input control.

## Project Structure

- `src/main/java/com/gymmanager/`
    - `ui/` – User interface components and main application entry point
    - `service/` – Business logic and service classes
    - `repository/` – Data access and repository classes
    - `model/` – Domain models and entities
---
- `src/test/java/com/gymmanager/` – Unit and integration test classes
---
- `doc/` – JavaDoc documentation
- `uml/` – UML diagrams for visual representation of the system architecture
- `gradle/wrapper/` – Gradle wrapper files \(should be tracked\)
- `build.gradle` – Gradle build configuration
- `gradlew`, `gradlew.bat` – Gradle wrapper scripts
- `.gitignore` – Git ignore rules
- `README.md` – Project documentation
- `.gitattributes` – Git attributes for handling line endings and merge strategies

## Recommendations

- This project was made in IntelliJ IDEA on a Mac, so it is recommended to use it for the best experience.
- Have a look at the UML diagram located in the `uml` folder.
- Have a look at the JavaDoc documentation located in the `doc` folder. Open the `index.html` file in a web browser to view it.