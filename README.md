# Generic search engine

This project implements and demonstrated simple generic search engine based on *Term Frequency–Inverse Document Frequency* algorithm

## Quick setup
1. Ensure java-17 is installed on the machine
2. From the project folder type "./mvnw compile"
2. Put the data in simple format e.g. .txt in the folder "src\main\resources\static\data"
3. Launch the Spring Boot application

## Functionality
- Lemmatizes both the files and the query
- Filters both the files and the query from the words mentioned in "src\main\resources\static\stopwords-en.txt"
- Ranks the files based on TFIDF algorithm
- Enables reading the files directly in HTML via writing the name of the file

## Example
In the following example some data was taken from [pubmedlib.](https://pubmed.ncbi.nlm.nih.gov/), the Query "gen" has been inserted into first box :
![alt text](READMEimgs\image.png)

If the wrong file is tried to be opened via interacting with the second search box, following appears in the textbox: 
![alt text](READMEimgs\image-1.png)

If the right name of the file is searched in the second search box, following could be an answer :
![alt text](READMEimgs\image-2.png)

The box could also be changed for better readability :
![alt text](READMEimgs\image-3.png)