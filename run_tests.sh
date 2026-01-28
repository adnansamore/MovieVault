#!/bin/bash

echo "Compiling main source..."
javac -cp lib/h2.jar src/app/*.java -d src/class

echo "Compiling tests..."
javac -cp "lib/h2.jar:lib/junit4.jar:lib/hamcrest.jar:src/class" \
  -d src/class src/test/*.java

echo "Running all tests..."
java -cp "lib/h2.jar:lib/junit4.jar:lib/hamcrest.jar:src/class" \
  org.junit.runner.JUnitCore test.DatabaseTest test.PersonTest test.MovieTest test.UtilsTest
