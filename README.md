# Student Grade Report Generator

## Overview
The Student Grade Report Generator is a Java-based application that allows users to manage student records, including adding, editing, and deleting students, as well as managing their grades. The application also supports generating reports for courses and saving/loading data from a JSON file.

## Features
- Add new students to courses
- Edit student grades
- View all students in a course
- Delete students from courses
- List grades for every student in a course
- Generate reports to a text file
- Create new courses
- Save and load courses to/from a JSON file

## Requirements
- Java 21

## Setup
1. Clone the repository:
    ```sh
    git clone https://github.com/heev7777/student-grade-report-generator.git
    ```
2. Open the project in IntelliJ IDEA.
3. Ensure you have the required dependencies in your `pom.xml` or `build.gradle` file.

## Usage
1. Run the `Main` class to start the application.
2. Follow the on-screen menu to perform various operations:
    - Add Student
    - Edit Student Grade
    - View All Students
    - Save Courses to JSON
    - Load Courses from JSON
    - Delete Student
    - List Grades for Every Student
    - Generate Report to Text File
    - Create New Course
    - Exit

## File Structure
- `src/main/java`: Contains the source code for the application.
- `src/main/resources`: Contains the JSON files for storing course data.
- `src/test/java`: Contains the test cases for the application.

## Example
Here is an example of how to add a new student:
1. Choose the option `1. Add Student`.
2. Enter the course name.
3. Enter the student ID.
4. Enter the student name.
5. The student will be added to the course and saved to the JSON file.

## Contributing
1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -am 'Add new feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Create a new Pull Request.

## License
This project is licensed under the MIT License - see the `LICENSE` file for details.