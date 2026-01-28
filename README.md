# MovieVault

A command-line movie database application built in Java with H2 in-memory database. So the build and run process requires a few manual steps.

## Requirements

- **Java Development Kit (JDK)** 17 or newer
- **H2 Database JAR** (included in `lib/` directory)

## Project Structure

```
MovieVault/
├── lib/
│   └── h2.jar              # H2 database library
|   └── junit4.jar          # JUnit testing framework
|   └── hamcrest.jar             
├── src/
│   ├── app/
│   │   ├── Main.java        # Entry point & command handler
│   │   ├── Database.java    # Database connection & schema setup
│   │   ├── Movie.java       # Movie operationse
│   │   ├── Person.java      # Person (director/actor) 
│   │   └── Utils.java       # Utility functions
│   └── class/               # Compiled .class files
├── TESTING.md
├── run_tests.sh
├── run_app.sh
├── .gitignore
└── README.md
```

## Build

From the project root directory, compile all source files:

```bash
javac -cp lib/h2.jar src/app/*.java -d src/class
```

## Run

Start the application:

```bash
java -cp "lib/h2.jar:src/class" app.Main
```

### Quick Bash Script 

run:

```bash
chmod +x run_tests.sh
./run_tests.sh
```

## Usage

Once the application starts, use the following commands:

### List Movies
```
l [options]
  -v                    Show verbose output (display actors)
  -t "<regex>"          Filter by title (regex, quoted)
  -d "<regex>"          Filter by director (regex, quoted)
  -a "<regex>"          Filter by actor (regex, quoted)
  -la                   Sort by length ascending
  -ld                   Sort by length descending
```

## Manual Test Inputs:

### Add Person
```
> a -p
Name: Adnan
Nationality: Pakistani
```

```
> a -p
Name: Balas
Nationality: Hungarian
```

### Add Movie 
```
> a -m
Title: Inception
Length (hh:mm:ss): 02:15:30
Director: Adnan
Actor (or exit): Adnan
Actor (or exit): Balas
Actor (or exit): exit
```
**Result**
    - Movie "Inception" added successfully!


### Delete Person
```
> d -p <name>
```

### Help
```
> help
> h
> ?
```
Display command help.

### Exit
```
> quit
```

**Edge Cases:**
 - 1. Invalid time format:

```
> a -m
Title: XYZ
Length (hh:mm:ss): 15
```
 **Result**
    - Bad input format; Expected (hh:mm:ss), try again!

 - 2. Duplicate movie:

```
> a -m
Title: Inception
Length (hh:mm:ss): 02:15:30
Director: Adnan
Actor (or exit): Balas
Actor (or exit): exit
```
 **Result**
    - Movie with title "Inception" and director "Adnan" already exists!

 - 3. Conflicting sort options:

```
 > l -la -ld
```
 **Result**
    - Error: Cannot use -la and -ld together

 - 4. Delete people:

        - Delete actor
        ```
        > d -p Balas
        ```
        **Result**
             - Deleted Balas

        - Delete Director
        ```
        > d -p Adnan
        ```
        **Result**
             - Error: Cannot delete: Adnan is a director

## Database Details
 
- **Type:** H2 In-Memory Database
- **Tables:** `person`, `movie`, `actor`
- **Schema:** Auto-created on startup
- **Data Persistence:** None (cleared when application closes)

