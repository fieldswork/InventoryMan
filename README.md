# InventoryMan

### A basic inventory management application created as part of the SkillStorm SDET Apprenticeship.

### This project is hosted on the AWS free tier! 
Find it [here!](http://inventory-man.s3-website-us-east-1.amazonaws.com) (http://inventory-man.s3-website-us-east-1.amazonaws.com)

*If the link is down, the project may have been taken offline to avoid charges.*

## Facts and Features
 - Full-stack application made with SpringBoot, PostgreSQL, and React
 - CRUD functionality for managing warehouses and inventory
 - Bootstrap UI with card layout for improved readability
 - Sorting and filtering for easy navigation
 - Logic to prevent reaching over-capacity in warehouses
 - Input validation for forms
 - Pre-computed utilization percentage for each warehouse
 - Responsive design for mobile and desktop

## AWS Deployment
 - PostgreSQL database hosted on AWS RDS
 - SpringBoot application hosted on AWS EC2
 - React front-end hosted on AWS S3

## Running InventoryMan Locally
 - Clone the repository
 - Generate `launch.json` in the `.vscode` folder
   - Database credentials not provided - bring your own!
   - Test database was created with PGAdmin4
 - `application.properties` will also need to be edited to match the location of your DB
 - Run the SpringBoot application via command line or an IDE's SpringBoot runner
 - Run the React front-end using `npm start` from within the `frontend` folder
 - Open your browser of choice and navigate to `localhost:3000`

## Project Structure
 - `backend` contains the SpringBoot application
 - `frontend` contains the React front-end

Below is a simplified version of the project structure. The full structure includes additional files and folders for configuration and dependencies.
```
INVENTORYMAN/
├── .vscode/ <NOT INCLUDED>
│   ├── launch.json
│   └── settings.json
├── backend/
│   ├── .mvn
│   └── src/
│       └── main/
│           ├── java/com/skillstorm/inventoryman/
│           │   ├── controllers/
│           │   │   ├── ItemController.java
│           │   │   └── WarehouseController.java
│           │   ├── models/
│           │   │   ├── Item.java
│           │   │   └── Warehouse.java
│           │   ├── repositories/
│           │   │   ├── ItemRepository.java
│           │   │   └── WarehouseRepository.java
│           │   ├── services/
│           │   │   ├── ItemService.java
│           │   │   └── WarehouseService.java
│           │   ├── InventoryManApplication.java
│           │   └── WebConfig.java
│           └── resources/
│               └── application.properties
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   │   ├── HomePage.js
│   │   │   ├── ItemForm.js
│   │   │   ├── ItemList.js
│   │   │   ├── UtilizationBar.js
│   │   │   ├── WarehouseForm.js
│   │   │   └── WarehouseList.js
│   │   ├── services/
│   │   │   ├── itemService.js
│   │   │   └── warehouseService.js
│   │   ├── App.css
│   │   ├── App.js
│   │   ├── index.css
│   │   └── index.js
│   ├── package-lock.json
│   └── package.json
├── .gitignore
└── README.md
```

### FAQ
 - **Q:** Why is it called InventoryMan?
   - **A:** It's like *Inventory* and *Manager*, but the *ager* part was cut off. It's a work in progress, and a pun.
 - **Q:** How were the backend and frontend created?
   - **A:** Backend was created with Spring Initializr, and the frontend was created with Create React App.
 - **Q:** How were the different parts of the project connected?
   - **A:** The React frontend makes HTTP requests to the SpringBoot back-end using Axios.
   - **A:** The SpringBoot backend uses JPA to interact with the PostgreSQL database.
   - **A:** The PostgreSQL database, in the latest version, is hosted on AWS RDS.
 - **Q:** How was the project tested?
   - **A:** The project was tested manually using Postman and the browser.
   - **A:** A lot of edge cases were tested manually, and bugs were fixed as they were found.
 - **Q:** What was the most challenging part of the project?
   - **A:** The most challenging part was getting the front-end and back-end to communicate properly.
   - **A:** The backend was easy to set up, but the front-end required a lot of trial and error.
 - **Q:** Why is the project webpage up, but I can't add or view any cards?
   - **A:** It seems that EC2 spins down after a while, and the database connection is lost.
   - **A:** Since the frontend is hosted on S3, it may look like it's up and working, but it can't connect to the backend.

### Resources I heavily relied on:
 - For getting the initial CRUD functionality set up between the database and backend, this was a great resource: [Spring Boot + React CRUD Example](https://www.bezkoder.com/spring-boot-react-postgresql/)
 - For getting the front-end to communicate with the back-end, this was a great resource: [React Axios Tutorial](https://www.bezkoder.com/react-axios/)
 - I really used a bunch of Bootstrap components, but this was a great resource for getting the cards to look nice: [Bootstrap Card Layout](https://getbootstrap.com/docs/4.0/components/card/) 
   - Bonus for the loading bar I used for a utilization visualization: [Bootstrap Progress Bar](https://getbootstrap.com/docs/4.0/components/progress/)
 - I couldn't have gotten the project up and running on AWS without this guide: [Deploy Full Stack Application (Spring-Boot+MySQL+React) to AWS(EC2, RDS, S3)](https://www.youtube.com/watch?v=YC7NBNICGeY)
 - This was also super helpful for my initial setup on RDS: [Creating an RDS PostgreSQL Database on the AWS Free Tier](https://www.youtube.com/watch?v=I_fTQTsz2nQ)
 - General thanks to all of StackOverflow, all the time, forever.
 - And of course, all of the instruction from Caroline and Erica during the lead-up to this project was invaluable! Thank you both!