# City of Chicago Crime Data Project
Investigating City of Chicago crime tends for Fall 2019.

Made for NEIU CS 420 (ISY Group).

## Installation Instructions
1. Unzip project (if needed)
2. With IntelliJ (v2019.2.3RC or higher), choose Import Project. 
3. Choose Import model from external model, select Gradle, and click Finish
4. Go to Preferences (Settings on Windows) -> Build -> Execution and Deployment -> Build Tools -> Gradle
5. Check Automatically import this project on changes in the build script files
6. Select Gradle for the "Build and Run Using..."
7. Change the "Use Gradle" to "wrapper" task in the Gradle Build Script.
8. Make sure Gradle JVM is set to Java 11
9. Press Okay or Apply

10. Next go to File -> Project Structure/Settings -> Project.
11. Set JDK to Java 11 (if it isn't already set)

12. Run the "MainApplication.java" file. On Windows, you'll need to go to gradle options and then select the following: Task -> Application -> Run

13. If the program does not run, check the following information below!

## Libraries/Dependencies
https://mvnrepository.com/artifact/com.google.code.gson/gson
- compile group: 'com.google.code.gson', name: 'gson', version: '2.8.6'

- testCompile group: 'junit', name: 'junit', version: '4.12'

#### Plugins
- id 'org.openjfx.javafxplugin' version '0.0.8'

#### JavaFX
- javafx {modules = [ 'javafx.controls', 'javafx.fxml' ]}

