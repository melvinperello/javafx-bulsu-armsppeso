# Bulacan State University (A.R.M.S.P.P.E.S.O)


**Automated Record Management System of Public Placement and Employment Service Office**


[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)


A simple application to help the Public Placement and Employment Service Office of the Bulacan State University, to make the record keeping of Graduate Students and Potential Employers manageable.


### Features

1. **Graduate Verification**: allows manageable retrieval of the graduated students, a search feature included and complete data encoding functionality.


[![Graduate Verification](https://raw.githubusercontent.com/melvinperello/javafx-bulsu-armsppeso/master/preview-img/03-verification.PNG)](https://raw.githubusercontent.com/melvinperello/javafx-bulsu-armsppeso/master/preview-img/03-verification.PNG)


2. **Company Profile**: Allows to have a manageable record set which allows the office to track company profiles and their preffered course for graduate students, this will allow them to easily endorse students with a skillset that will potentially match an employer.


[![Company](https://raw.githubusercontent.com/melvinperello/javafx-bulsu-armsppeso/master/preview-img/04-company.PNG)](https://raw.githubusercontent.com/melvinperello/javafx-bulsu-armsppeso/master/preview-img/04-company.PNG)


3. **Inquiry**: For an office to receive questions and inquiry is common, this feature allows to easily record each company's inquiry and to add a side note using the description field, whether if they wanted to do a job fair or seminar this will be recorded with ease.


[![Inquiry](https://raw.githubusercontent.com/melvinperello/javafx-bulsu-armsppeso/master/preview-img/05-inquiry.PNG)](https://raw.githubusercontent.com/melvinperello/javafx-bulsu-armsppeso/master/preview-img/05-inquiry.PNG)


4. **Accounts**: Every data needs to be owned, accounts allows the office to track who and when a record was added or updated, it features a simple yet functional *Audit Trails*.


[![Accounts](https://raw.githubusercontent.com/melvinperello/javafx-bulsu-armsppeso/master/preview-img/06-accounts.PNG)](https://raw.githubusercontent.com/melvinperello/javafx-bulsu-armsppeso/master/preview-img/06-accounts.PNG)


5. **Portability**: The application was meant to be light-weight and portable where in data can be transferred from one place to another just like an excel file with icing. The application is using sqlite and stores data along side the executable file which allows it to be copied easily.


**Login Screen**


[![Login](https://raw.githubusercontent.com/melvinperello/javafx-bulsu-armsppeso/master/preview-img/01-login.PNG)](https://raw.githubusercontent.com/melvinperello/javafx-bulsu-armsppeso/master/preview-img/01-login.PNG)


**Menu**


[![Login](https://raw.githubusercontent.com/melvinperello/javafx-bulsu-armsppeso/master/preview-img/02-menu.PNG)](https://raw.githubusercontent.com/melvinperello/javafx-bulsu-armsppeso/master/preview-img/02-menu.PNG)


### How to use ?

**Requires Polaris Java Library**


```bat
git clone https://github.com/melvinperello/polaris-java-library.git
cd polaris-java-library
mvn clean install
```


**Build and Run**
```bat
git clone https://github.com/melvinperello/javafx-bulsu-armsppeso.git
cd javafx-bulsu-armsppeso
mvn clean install

cd target
java -jar javafx-bulsu-armsppeso-1.0.0-SNAPSHOT-jar-with-dependencies.jar
```


*Loggin in for the first time using the power user.*


> Power User: **afterschoolcreatives** Password: **123456**


Cheers,


Melvin