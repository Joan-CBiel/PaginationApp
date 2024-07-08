#!/bin/bash

# Directory where the JAR files will be placed
DIR="../EXE"

# Compile classes
echo "Compiling classes..."
javac -classpath domain/*.java 
if [ $? -ne 0 ]; then
    echo "Error during compilation of domain."
    exit 1
fi

javac -classpath presentation/*.java
if [ $? -ne 0 ]; then
    echo "Error during compilation of presentation."
    exit 1
fi

echo "Classes compiled!"

# Create destination directory
mkdir -p $DIR

# Create executable JAR
echo "Creating jar..."
jar cfe PaginationApp.jar  domain/*.class presentation/*.class
if [ $? -ne 0 ]; then
    echo "Error during JAR creation."
    exit 1
fi
echo "Jar created!"

# Delete leftover class files
echo "Deleting leftover files..."
rm -f domain/*.class
rm -f presentation/*.class
echo "Leftover files deleted!"

# Move JAR file
mv PaginationApp.jar $DIR/

echo "Compilation ended"
