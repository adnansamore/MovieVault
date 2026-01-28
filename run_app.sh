#!/bin/bash

echo "Compiling source code..."
javac -cp lib/h2.jar src/app/*.java -d src/class

echo "Running the application..."
java -cp "lib/h2.jar:src/class" app.Main
