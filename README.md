# PaginationApp
An app that reads a one line text document and paginate with the following requirements:
* Each line consists of a maximum of 80 characters, if the line reaches this number of chars in the middle of a word, this one should be placed at the next line.
* Each page has 25 lines, at the end of the page add a separation mark that includes the page number.

## Executing the App
Download the direcotry by zip or clone.
### On Windows
Execute the application in the EXE directory by clicking the PaginationApp.exe
### On Unix
Execute the jar file in EXE.

Comandlines from the root directory of the project:
```
cd EXE
java -jar PaginationApp.jar
```
## Use of the app
* Import a one line document.txt.
* Choose where to save the output document, can be in pdf or txt format.
* Press paginate button.
  
## Source code
All the code is in the src directory.

* domain directory has the code backend code.
* presentation directory has the frontend code.
* exceptions directory has some exceptions used in project.

## JavaDoc 
The javaDoc of the src/ files is in the Doc directory.


