# PetInformationSystem

## Project Perspective
The database application aims to provide an online interface for owners to log information about their pets. This includes the pet, owner, vet, veterinarian, vaccinations, medication, and key events, which include meals, exercise, potty, medical checkup, and grooming. The database application is aimed at pet owners seeking to track information about their pets. Typical scenarios the application will support include registering owners and pets, logging in, logging events, deleting events, retrieving event history, tracking pet nutrition, and getting expense reports. Events, in this case, refers to events related to the pet such as meals, exercise, potty, medical checkup, and grooming. 
Additionally, users expand to veterinarians who can utilize tracked pet information if granted permission by the pet owner. This is to aid in providing insight into daily activities and behaviors of the pet such as frequency of meals, exercise, excretion, etc. Users can also be caretakers who can utilize the tracked pet information to understand general habits (e.g. meal time) and login events for the pet taken care of. Overall, the tool aims to support pet owners to easily track and analyze the daily activities of their pet to optimize care.

## Application Installation/Run
--to be added--
  
## Common Use Scenarios
The database application’s primary users are aimed for pet owners seeking to track information about their pet.

### Register Owner & Pet
A pet owner that is not registered can register by filling in forms for their details and the pets they own. Multiple pets can be owned by the same owner. Multiple owners can be owners of the same pet. 
![image](https://github.com/user-attachments/assets/1a115fd4-7f1b-4321-9b67-4fbdeec50b66)

### Register Veterinarian
A veterinarian that is not registered can register by filling in forms for veterinarian. 

### Register Caretaker
A caretaker that is not registered can register by filling in forms for caretakers. 

### User Login
A user can login using their username and password. This gives them access to information they have access to. For owners they will have access to their pet’s information. For caretakers and veterinarians, they will have access to the pet information in which they have been granted access to.
![image](https://github.com/user-attachments/assets/e632eae0-ef12-4110-ab42-870222466c35)

### Adding an Event
An owner can log an event specifying the type, date, and time. The date and time will automatically be set to the current date and time unless specified otherwise.
![image](https://github.com/user-attachments/assets/285dce37-9816-40b2-948d-3ca7c6451e38)
![image](https://github.com/user-attachments/assets/fc86425b-e094-48d8-9544-cb6ee26df748)

### Event History
An owner can get a summary log of all events. The information that will be included are those that are shared in all events.
![image](https://github.com/user-attachments/assets/6beadfae-c1bf-47a1-91c7-0ad21ace38ab)

### Delete an Event
An owner can delete an event made by them. This action is disabled if no events have been added for the pet.

### Calorie Intake
An owner can calculate a pet’s daily calorie intake, which will be retrieved from meal events.

### Add Expense
An owner can enter different types of expenses related to the pet. 
![image](https://github.com/user-attachments/assets/1b5c0d93-1003-425b-8926-cb195547507e)

### Remove Expense
An owner can remove an expense related to the pet. If there are no expenses related to the pet this action will be disabled.

### Expense Report
An owner can calculate the expenses related to the pet over a given time period which will be retrieved from expense entries. 

### Exercise Report 
Provides a report on how active the pet was over a given time period based on duration. 
![image](https://github.com/user-attachments/assets/8582437d-ab74-49f6-b488-9a91493b6bd8)

### Potty Report
Provides a report on how often the pet has gone potty, distinguished by type of potty, over a given time period.
![image](https://github.com/user-attachments/assets/9c763f3e-96a4-4d68-9693-6f2190889931)

### Medical Checkup Report
Provides a report on all medical checkups of the pet. This includes information on the reason for the visit, weight, notes from the visit, the veterinarian and the veterinary
![image](https://github.com/user-attachments/assets/197dab57-beac-4640-aa8e-102876e333a5)

### Training Report
Provide a report on what the training type and how effective the training was over a given time period based on execution time of commands and the time in which commands are held.
![image](https://github.com/user-attachments/assets/01f7b861-a75b-4de5-8d90-dfc1ee7f29e2)

## Analytical Queries 
1. Find the veterinary clinic that takes care of the most <species> (between __ and __kg)
   ![image](https://github.com/user-attachments/assets/30935456-90f3-4ff5-b283-339ff2a15035)
     **Note:** located in the medical checkup page
2. Find the average daily calories consumed by species.
3. For a given owners, find the average amount spent on <eventType> per month.
   ![image](https://github.com/user-attachments/assets/439c0c25-c085-43e9-8853-51fa8ac97971)
     **Note:** located on the expense page. It allows users to select the type of event they would like the average amount spent per month calculated for.
   
4. For a given area what is the groomer that has the highest average rating and has over 10 ratings.
   ![image](https://github.com/user-attachments/assets/9dfcd000-d351-46c1-8ecf-2cdf2db1176c)
   ![image](https://github.com/user-attachments/assets/afd33a31-e15b-4362-ad93-4a002e5b0415)
     **Note:** located on grooming page. It allows users to select the city they want to find a groomer (based on ratings).
   
5. Analyze pet training success by the time of day.
6. For dogs what are the two most common 1 hour time frames in which dogs pee.

----
# References
* Connect MySQL to Java in IntelliJ 2024 | Full JDBC Tutorial for DB Connectivity - https://www.youtube.com/watch?v=9ntKSLLDeSs&t=8s 
* JAVA DATABASE TUTORIAL WITH SQLITE Create School management System - https://www.youtube.com/watch?v=h1rYlMrvNyE
* How to Set Up JavaFX in IntelliJ IDEA | Step-by-Step Guide - https://www.youtube.com/watch?v=hZEin6M09cY
* Populating the combobox from database - https://stackoverflow.com/questions/52085575/populating-the-combobox-from-database
* Returning the Generated Keys in JDBC - https://www.baeldung.com/jdbc-returning-generated-keys

