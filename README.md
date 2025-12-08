# Attendance Management System (Java | Swing | CO1–CO6 Integrated Project)

A desktop-based Attendance Management System built using Java, Swing, OOP, Collections, File I/O, and fundamental programming constructs.  
This project demonstrates the complete integration of Course Outcomes CO1 → CO6 in a real-world style application.

## 🚀 Features

### ✔ Add / Update Attendance
Enter attendance data for:
- Lecture  
- Tutorial  
- Practical  
- Skill  

Weighted formula automatically computes final attendance.

### ✔ Search Attendance
Search any student by Roll Number + Subject.

### ✔ Analytics
- Student with Least Attendance  
- Student with Most Attendance  
- Display All Records in Ascending Order  
- Recursive minimum attendance demonstration

### ✔ Data Persistence
All records are saved & loaded automatically from:
```
attendance_data.txt
```

### ✔ GUI (Swing)
User-friendly interface with:
- Input form  
- Search / Analytics bar  
- Output console  
- Attendance table (JTable)

## 🧠 Course Outcome Mapping

| CO | Description | Implemented In |
|----|-------------|----------------|
| CO1 | Control structures & logic | Validations, weighted formula |
| CO2 | Arrays | Building attendance arrays |
| CO3 | Strings, recursion, bitwise ops | Roll parsing, recursive min, even/odd check |
| CO4 | OOP design | Model classes, encapsulation, calculator logic |
| CO5 | Inheritance & polymorphism | Sorting & analysis manager |
| CO6 | File I/O, Collections, GUI | File handling, HashMap, Swing |

## 🛠️ Tech Stack

- Java 8+  
- Swing GUI Framework  
- Collections Framework (Map, List, Streams)  
- OOP Principles  
- File I/O  

## 📂 Project Structure

src/
 ├── AttendanceComponents.java
 ├── AttendanceCalculator.java
 ├── AttendanceAnalytics.java
 ├── BitwiseUtils.java
 ├── AttendanceManager.java
 ├── AttendanceManagerWithSearchSort.java
 └── Main.java
attendance_data.txt

## 📘 How to Run

### 1. Compile
javac Main.java

### 2. Run
java Main

The Swing application will launch automatically.

## 📝 Data Format

Each record stored as:
Name,RollNumber,Subject,Lecture,Tutorial,Practical,Skill

Data loads at startup and saves on exit.

## 📈 UI Layout

- Left Side: Form for adding/updating attendance  
- Top Bar: Search + Analytics actions  
- Center: Output logs  
- Bottom: JTable displaying sorted data  

## 🧩 Key Concepts Demonstrated

- Weighted formula calculation  
- Recursion & arrays-based analysis  
- Bitwise operations (id & 1)  
- Encapsulation, classes, modular design  
- Inheritance for advanced analytics  
- File read/write with error handling  
- Event-driven GUI programming with Swing  
- Collections & Stream API for sorting/filtering  
