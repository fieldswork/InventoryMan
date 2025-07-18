# InventoryMan

### A basic inventory management application created as part of the SkillStorm SDET Apprenticeship

**Update 3/12/25:** InventoryMan has been taken offline to avoid AWS charges, thank you for your interest in this project. Please see the linked resources below for guidance on how to host this application yourself!

**Update 9/6/24:** Project 2 content has been added, including additional tests with Mockito, Jest, and more configured in our Jenkinsfile! ***Huge thanks to my groupmate for this project, Bruna Vicente!***

## Screenshots and Demos

> A demonstration of the card UI layout that was utilized in InventoryMan. Bootstrap components were used for accessibility and style.

![A demonstration of the card UI layout that was utilized in InventoryMan. Bootstrap components were used for accessibility and style.](readme_attachments/warehouse_cards.png)

> A demonstration of the UI layout, depicting multiple item cards, each with their own name, description, quantity, size, and designated warehouse. InventoryMan offers options for sorting based on item attributes, and filtering by a specific warehouse, or all warehouses

![A demonstration of the UI layout, depicting multiple item cards, each with their own name, description, quantity, size, and designated warehouse. InventoryMan offers options for sorting based on item attributes, and filtering by a specific warehouse, or all warehouses.](readme_attachments/item_cards.png)

> UI for adding a warehouse in InventoryMan

![UI for adding a warehouse in InventoryMan](readme_attachments/create.png)

> Demonstration of deleting a warehouse in InventoryMan

![Demonstration of deleting a warehouse in InventoryMan](readme_attachments/delete.gif)

> Demonstration of implemented router and history functionality

![Demonstration of implemented router and history functionality](readme_attachments/history.gif)

> Demonstration of frontend input validation practices

![Demonstration of frontend input validation practices](readme_attachments/inputvalidation.gif)

> A demonstration of the navigation bar in InventoryMan

![A demonstration of the navigation bar in InventoryMan](readme_attachments/navigation.gif)

> Demonstrating filtering capabilities for both the name and utilization of warehouses

![Demonstrating filtering capabilities for both the name and utilization of warehouses](readme_attachments/organize.gif)

> Demonstrating utilization of the browser's popup dialogue box for confirmation of certain destructive actions

![Demonstrating utilization of the browser's popup dialogue box for confirmation of certain destructive actions](readme_attachments/userconfirmation.gif)

> A screenshot of InventoryMan running on Amazon Web Services during automated frontend unit testing.

![A screenshot of InventoryMan running on Amazon Web Services during automated frontend unit testing](readme_attachments/ui.png)

> The Jenkins CI/CD dashboard, showing build information, automated test reports, and more.

![The Jenkins CI/CD dashboard, showing build information, automated test reports, and more.](readme_attachments/jenkins.png)

## Test Reports

[Full console output from Jenkins](readme_attachments/im_cicd_output.txt)

*Tests were executed on Jenkins running on an EC2 instance that pulls from this Github repository via a webhook on the main branch.*

### Cucumber Test Results

> **32 tests** were run across **14 feature files**, with **100% passing**.

![Cucumber test results reads 100% passed 32 tests, with 7 minutes to run. Below are all the tests listed out.](readme_attachments/cucumber_results.png)

### Mockito Test Results
> **84% statement coverage**, **88% branch coverage**.

![A chart that displays 84% statement coverage, with 88% branch coverage.](readme_attachments/jacoco_mockito_results.png)

### SonarCloud Test Results

> Frontend has **87% reported code coverage** with A ratings in Security, Reliability, and Maintainability.

![SonarCloud Report Information for the Frontend](readme_attachments/sonarcloud_frontend.png)

> Backend has **90% reported code coverage** with A ratings in Security, Reliability, and a B rating for Maintainability.

![SonarCloud Report Information for the Frontend](readme_attachments/sonarcloud_backend.png)

*Links to the full reports:*

[SonarCloud InventoryMan Frontend Report](https://sonarcloud.io/summary/overall?id=salmoncore_InventoryMan)

[SonarCloud InventoryMan Backend Report](https://sonarcloud.io/summary/overall?id=salmoncore_inventoryman-backend)

### Jest Test Results

> **88% statement coverage**, with **81% branch coverage**.

![Jest test results](readme_attachments/jest_results.png)

### Burp Suite Demo

[Video Recording on Google Drive](https://drive.google.com/file/d/1gKqvzeaNlauKmKzCstGmns3A_ZwsYgZ2/view?usp=sharing)

### JMeter Results

> 10 samples were taken, with an **average response time of 45 milliseconds** and **0% errors**.

![Throughput, response time, and Percentage of errors](readme_attachments/jmeter_graphs.png)

![Performance chart](readme_attachments/jmeter_chart.png)

## Facts and Features
 - End-to-end testing with Cucumber, Selenium, Jest, Mockito, SonarCloud, and more
 - Full-stack application made with SpringBoot, PostgreSQL, and React
 - CRUD functionality for managing warehouses and inventory
 - Bootstrap UI with card layout for improved readability
 - Sorting and filtering for easy navigation
 - Logic to prevent reaching over-capacity in warehouses
 - Input validation for forms
 - Pre-computed utilization percentage for each warehouse
 - Responsive design for mobile and desktop

## AWS Deployment
 - Amazon Aurora postgres-compatable database hosted on AWS RDS
 - SpringBoot application hosted on Elastic Beanstalk
 - React front-end hosted on AWS S3
 - Jenkins CI/CD pipeline hosted on Amazon EC2

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

### Resources I heavily relied on:
 - For getting the initial CRUD functionality set up between the database and backend, this was a great resource: [Spring Boot + React CRUD Example](https://www.bezkoder.com/spring-boot-react-postgresql/)
 - For getting the front-end to communicate with the back-end, this was a great resource: [React Axios Tutorial](https://www.bezkoder.com/react-axios/)
 - I really used a bunch of Bootstrap components, but this was a great resource for getting the cards to look nice: [Bootstrap Card Layout](https://getbootstrap.com/docs/4.0/components/card/) 
   - Bonus for the loading bar I used for a utilization visualization: [Bootstrap Progress Bar](https://getbootstrap.com/docs/4.0/components/progress/)
 - I couldn't have gotten the project up and running on AWS without this guide: [Deploy Full Stack Application (Spring-Boot+MySQL+React) to AWS(EC2, RDS, S3)](https://www.youtube.com/watch?v=YC7NBNICGeY)
 - This was also super helpful for my initial setup on RDS: [Creating an RDS PostgreSQL Database on the AWS Free Tier](https://www.youtube.com/watch?v=I_fTQTsz2nQ)
 - General thanks to all of StackOverflow, all the time, forever.
 - And of course, all of the instruction from Caroline and Erica during the lead-up to this project was invaluable! Thank you both!
