#!/bin/bash

# Directory where the JAR files will be placed
SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
DIR = $(pwd)

#change to script directory
cd $SCRIPT_DIR

# Compile classes
echo "Compiling classes..."
javac  domain/*.java
if [ $? -ne 0 ]; then
    echo "Error during compilation of domain."
    exit 1
fi

javac presentation/*.java
if [ $? -ne 0 ]; then
    echo "Error during compilation of presentation."
    exit 1
fi

echo "Classes compiled!"

# Create destination directory
mkdir -p ../EXE

# Create executable JAR
echo "Creating jar..."
jar cfm PaginationApp.jar manifest.txt domain/*.class presentation/*.class 
if [ $? -ne 0 ]; then
    echo "Error during JAR creation."
    exit 1
fi
echo "Jar created!"

# Delete leftover class files
echo "Deleting leftover files..."
rm -f domain/*.class
rm -f presentation/*.class
#echo "Leftover files deleted!"

# Move JAR file
mv PaginationApp.jar ../EXE/

# Retrune to working directory
cd $DIR

echo "Compilation ended"
