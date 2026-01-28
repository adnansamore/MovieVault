# Unit Testing Guide

This includes JUnit 4 unit tests for core functionality. Tests verify database operations, person/actor management, movie operations, and utility functions.

## Test Summary

| Test Class        | File              | Tests | Coverage                          |
|-------------------|-------------------|-------|-----------------------------------|
| **DatabaseTest**  | DatabaseTest.java | 4     | Connection, tables, schema        |
| **PersonTest**    | PersonTest.java   | 4     | Add, exists, delete               |
| **MovieTest**     | MovieTest.java    | 5     | Add, list, filtering, duplicates  |
| **UtilsTest**     | UtilsTest.java    | 8     | Time conversion                   |


## Prerequisites

JUnit 4 and Hamcrest are already in `lib/` directory:
- `lib/junit4.jar` – JUnit 4 framework
- `lib/hamcrest.jar` – Hamcrest matchers
- `lib/h2.jar` – H2 database driver

No additional downloads needed!

## Run Tests Locally

### Step 1: Compile Main Source Code

```bash
javac -cp lib/h2.jar src/app/*.java -d src/class
```

**What it does:**
- Compiles all `.java` files in `src/app/` (Main, Database, Movie, Person, Utils)
- Places `.class` files in `src/class/`
- Uses `lib/h2.jar` for database classes

### Step 2: Compile Test Code

```bash
javac -cp "lib/h2.jar:lib/junit4.jar:lib/hamcrest.jar:src/class" \
  -d src/class src/test/*.java
```

**What it does:**
- Compiles all test files: DatabaseTest, PersonTest, MovieTest, UtilsTest
- Classpath includes: H2, JUnit4, Hamcrest, and compiled app code
- Outputs `.class` files to `src/class/`

### Step 3: Run All Tests

```bash
java -cp "lib/h2.jar:lib/junit4.jar:lib/hamcrest.jar:src/class" \
  org.junit.runner.JUnitCore test.DatabaseTest test.PersonTest test.MovieTest test.UtilsTest
```

**Expected output:**
```
OK (21 tests)
```

### Run Individual Test Class

```bash
# Test Person 
java -cp "lib/h2.jar:lib/junit4.jar:lib/hamcrest.jar:src/class" \
  org.junit.runner.JUnitCore test.PersonTest

# Test Movie 
java -cp "lib/h2.jar:lib/junit4.jar:lib/hamcrest.jar:src/class" \
  org.junit.runner.JUnitCore test.MovieTest

# Test Utils 
java -cp "lib/h2.jar:lib/junit4.jar:lib/hamcrest.jar:src/class" \
  org.junit.runner.JUnitCore test.UtilsTest

# Test Database setup 
java -cp "lib/h2.jar:lib/junit4.jar:lib/hamcrest.jar:src/class" \
  org.junit.runner.JUnitCore test.DatabaseTest
```

## Quick Bash Script 

run:

```bash
chmod +x run_tests.sh
./run_tests.sh
```

## GitHub Actions CI/CD

Tests automatically run on GitHub (`.github/workflows/tests.yml`):
- **Triggered on:** push and pull_request to main
- **Environment:** Ubuntu + JDK 17
- **Steps:**
  1. Checkout code
  2. Setup JDK 17
  3. Download JUnit4 & Hamcrest
  4. Compile source + tests
  5. Run all 21 tests


