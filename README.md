Tech Stack: 
Spring Boot
Open Jdk 11
Swagger
Apache pdf box
commons io
Tesseract

Steps to run in local and some information:

1) Download the code.
2) mvn install
3) Run the main class
4) Pass the environment variable TESSDATA_PREFIX=/project-location/tessdata
5) open browser with http://localhost:8080/swagger-ui
6) use the patent info from ocr/patents.txt and use couple of ids in swagger page.
7) See loggers in console.
8) Text read from pdf/images are not processed just printed in logs.
9) No database involved.
10) Test cases are not covered 100% due to time complexity, Few integration test cases are added.

