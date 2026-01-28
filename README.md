# MovieVault

A command-line movie database application built in Java with H2 in-memory database. So the build and run process requires a few manual steps.

# Clone & Run

## 1. Clone the repo
```bash
git clone https://github.com/adnansamore/MovieVault.git
```

## 2. Go into the project folder
```bash
cd MovieVault
```

## 3. Compile the Java source files
```bash
javac -cp lib/h2.jar src/app/*.java -d src/class
```

## 4. Run the application
```bash
java -cp "lib/h2.jar:src/class" app.Main
```

**Optional:** Use the provided script:
```bash
chmod +x run_app.sh
./run_app.sh
```



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

### Edge Cases:
 -  **Invalid time format:**

```
> a -m
Title: XYZ
Length (hh:mm:ss): 15
```

    - Bad input format; Expected (hh:mm:ss), try again!

 -  **Duplicate movie:**

```
> a -m
Title: Inception
Length (hh:mm:ss): 02:15:30
Director: Adnan
Actor (or exit): Balas
Actor (or exit): exit
```

    - Movie with title "Inception" and director "Adnan" already exists!

 -  **Conflicting sort options:**

```
 > l -la -ld
```

    - Error: Cannot use -la and -ld together

 -  **Delete people:**

        - Delete actor

        ```bash
        > d -p Balas
        ```

             - Deleted Balas

        - Delete Director

        ```bash
        > d -p Adnan
        ```

             - Error: Cannot delete: Adnan is a director

## Database Details
 
- **Type:** H2 In-Memory Database
- **Tables:** `person`, `movie`, `actor`
- **Schema:** Auto-created on startup
- **Data Persistence:** None (cleared when application closes)

