# PetInformationSystem

## Project Perspective
The database application aims to provide an online interface for owners to log information about their pets. This includes the pet, owner, vet, veterinarian, vaccinations, medication, and key events, which include meals, exercise, potty, medical checkup, and grooming. The database application is aimed at pet owners seeking to track information about their pets. Typical scenarios the application will support include registering owners and pets, logging in, logging events, deleting events, retrieving event history, tracking pet nutrition, and getting expense reports. Events, in this case, refers to events related to the pet such as meals, exercise, potty, medical checkup, and grooming. 
Additionally, users expand to veterinarians who can utilize tracked pet information if granted permission by the pet owner. This is to aid in providing insight into daily activities and behaviors of the pet such as frequency of meals, exercise, excretion, etc. Users can also be caretakers who can utilize the tracked pet information to understand general habits (e.g. meal time) and login events for the pet taken care of. Overall, the tool aims to support pet owners to easily track and analyze the daily activities of their pet to optimize care.

## Application Installation/Run
1. Execute the provided SQL script we used MySQL Workbench.
2. Open the project file. There will be a folder in src/main/java/Database/dbConnection.java Please open this file and change the dbURL, dbUser, and dbPassword to match your MySQL configuration.
3. Run the application. The main method is in src/main/java/Login/LoginApp.java

Notes: Ensure that JavaFX SDK is set up properly to run the UI. The pom.xml file has a list of the project's dependencies. 

---
## Common Use Scenarios
The database application’s primary users are aimed for pet owners seeking to track information about their pet.

### Register Owner & Pet
A pet owner that is not registered can register by filling in forms for their details and the pets they own. Multiple pets can be owned by the same owner. Multiple owners can be owners of the same pet. 
<br />![image](https://github.com/user-attachments/assets/0753463d-dc53-4f30-a327-c7beb9226f7d)
<br />![image](https://github.com/user-attachments/assets/1a115fd4-7f1b-4321-9b67-4fbdeec50b66)

### Register Veterinarian
A veterinarian that is not registered can register.

### Register Caretaker
A caretaker that is not registered can register.

### User Login
A user can login using their username and password. This gives them access to information they have access to. For owners they will have access to their pet’s information. For caretakers and veterinarians, they will have access to the pet information in which they have been granted access to.
<br />![image](https://github.com/user-attachments/assets/c58bd9d2-11f6-4d93-b963-c9583b2c78de)
<br />![image](https://github.com/user-attachments/assets/d5385f8a-e6c7-474c-887a-9132b2a8c55e)

### Adding an Event
An owner can log an event specifying the type, date, and time. The date and time will automatically be set to the current date and time unless specified otherwise.
<br />![image](https://github.com/user-attachments/assets/285dce37-9816-40b2-948d-3ca7c6451e38)
<br />![image](https://github.com/user-attachments/assets/876b4b78-5d07-4d9f-a3d6-9b925a4959b2)
<br /> Each event type button will bring the user to a new page to log the event specific information. A single event can consist of many event types but only one of each.

### Event History
An owner can get a summary log of all events. The information that will be included are those that are shared in all events.
<br />![image](https://github.com/user-attachments/assets/3af8cd2c-1a4c-4268-bab0-8af61b12c715)
<br /> First the owner would choose the pet for which they want the event history of. Then they will be brought to a pet page in which they can open up the event history for the pet.
<br />![image](https://github.com/user-attachments/assets/c2e32588-59db-45cf-b5d5-0a0ca34f7314)
<br />![image](https://github.com/user-attachments/assets/5f709c89-ceec-4d60-a0fc-25081734c067)

### Delete an Event
An owner can delete an event made by them. This action is disabled if no events have been added for the pet.
<br />![image](https://github.com/user-attachments/assets/88656a68-80e7-4415-9252-df6f15984ff1)
<br />![image](https://github.com/user-attachments/assets/94b92ec2-a524-411a-8b79-cb86fd505b68)

### Add Expense
An owner can enter different types of expenses related to the pet. 
<br />![image](https://github.com/user-attachments/assets/70ac6334-8cc3-4eba-a0ae-de5064c63a6c)

### Remove Expense
An owner can remove an expense related to the pet. If there are no expenses related to the pet this action will be disabled.
<br />![image](https://github.com/user-attachments/assets/df78f119-a5ec-42ed-970f-5030d415774e)
<br />![image](https://github.com/user-attachments/assets/e1adbde2-d5d3-4e5f-bdc8-99ed66ebed5f)

### Exercise Report 
Provides a report on how active the pet was over a given time period based on duration. 
<br />![image](https://github.com/user-attachments/assets/8582437d-ab74-49f6-b488-9a91493b6bd8)
<br />![image](https://github.com/user-attachments/assets/dc49bf5c-d91e-429d-a8c0-473e20db1eb8)


### Potty Report
Provides a report on how often the pet has gone potty, distinguished by type of potty, over a given time period.
<br />![image](https://github.com/user-attachments/assets/9c763f3e-96a4-4d68-9693-6f2190889931)

### Training Report
Provide a report on what the training type and how effective the training was over a given time period based on execution time of commands and the time in which commands are held.
<br />![image](https://github.com/user-attachments/assets/01f7b861-a75b-4de5-8d90-dfc1ee7f29e2)
<br />![image](https://github.com/user-attachments/assets/7cd7dab6-e56e-4d67-a74d-96f216ab89fa)

## Analytical Queries 
1. Find the veterinary clinic that takes care of the most <species> (between __ and __kg)
   <br />![image](https://github.com/user-attachments/assets/30935456-90f3-4ff5-b283-339ff2a15035)
   <br />**Note:** located in the medical checkup page
2. For a given owners, find the average amount spent on <eventType> per month.
   <br />![image](https://github.com/user-attachments/assets/439c0c25-c085-43e9-8853-51fa8ac97971)
   <br />**Note:** located on the expense page. It allows users to select the type of event they would like the average amount spent per month calculated for.
   
3. For a given area what is the groomer that has the highest average rating and has over 10 ratings.
   <br />![image](https://github.com/user-attachments/assets/9dfcd000-d351-46c1-8ecf-2cdf2db1176c)
   <br />![image](https://github.com/user-attachments/assets/afd33a31-e15b-4362-ad93-4a002e5b0415)
   <br />**Note:** located on grooming page. It allows users to select the city they want to find a groomer (based on ratings).
   
4. Analyze pet training success by the time of day.
   ![image](https://github.com/user-attachments/assets/2d236e28-e6c8-4649-91b4-148193b48fc0)

5. Provides a report on all medical checkups of the pet. This includes information on the reason for the visit, weight, notes from the visit, the veterinarian and the veterinary
<br />![image](https://github.com/user-attachments/assets/197dab57-beac-4640-aa8e-102876e333a5)

----
# References
* Connect MySQL to Java in IntelliJ 2024 | Full JDBC Tutorial for DB Connectivity - https://www.youtube.com/watch?v=9ntKSLLDeSs&t=8s 
* JAVA DATABASE TUTORIAL WITH SQLITE Create School management System - https://www.youtube.com/watch?v=h1rYlMrvNyE
* How to Set Up JavaFX in IntelliJ IDEA | Step-by-Step Guide - https://www.youtube.com/watch?v=hZEin6M09cY
* Populating the combobox from database - https://stackoverflow.com/questions/52085575/populating-the-combobox-from-database
* Returning the Generated Keys in JDBC - https://www.baeldung.com/jdbc-returning-generated-keys

