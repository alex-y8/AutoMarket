# My Personal Project: AutoMarket

## Project Description

This project will be a **car marketplace** application where users can view and purchase a diverse range of cars. 
Users will have the ability to browse a diverse range of cars on the marketplace,
filter them based on specifications, and view detailed stats for each car.
Users can add cars to their personal garage and view this saved list even when the application is quit and rerun.

This application is designed for car enthusiasts and individuals interested in purchasing and managing their virtual 
car collection. It is especially useful to users who appreciate detailed car specifications, statistics, and the 
experience of browsing and acquiring vehicles in a digital marketplace.

This project is of interest to me because growing up, I have always been interested in cars and their specifications. 
So, I have decided to create a car marketplace application with cars on sale, highlighting the details of each car.


## User Stories
- As a user, I want to be able to view the cars being sold on the marketplace
- As a user, I want to be able to filter cars by their specifications
- As a user, I want to be able to view car stats
- As a user, I want to be able to check if I have enough currency to buy a car
- As a user, I want to be able to buy cars from the marketplace
- As a user, I want to be able to add a car to my garage
- As a user, I want to be able to view a list of the cars that I own
- As a user, I want to be able to add/sell my own cars on the market
- As a user, I want to be able to save and load a file that contains cars in my garage
- As a user, when I select the quit option from the application menu, 
I want to be reminded to save my garage, marketplace, and account to file and have the _option_ to do so or not
- As a user, when I start the application, I want to be given the _option_ to load my garage, marketplace, and account
from file
## Instructions for Grader
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by clicking
marketplace, selecting a car, and clicking the "Buy car" button. You'll need to have enough account balance to purchase
the car.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by clicking the
drop-down menu and sorting the list of marketplace/garage cars
- You can locate my visual component by clicking on the marketplace
- You can save the state of my application by clicking the Quit button and then selecting "Save and exit" 
- You can reload the state of my application by clicking "Yes" when prompted when the application starts

## Phase 4: Task 2

Event log example:

Wed Apr 03 16:33:22 PDT 2024\
Car listed onto marketplace: 2016 Audi R8


Wed Apr 03 16:33:22 PDT 2024\
Car listed onto marketplace: 2017 Nissan GT-R


Wed Apr 03 16:33:22 PDT 2024\
Car listed onto marketplace: 1988 BMW M5


Wed Apr 03 16:33:22 PDT 2024\
Car listed onto marketplace: 2011 Bugatti Veyron


Wed Apr 03 16:33:22 PDT 2024\
Car listed onto marketplace: 2013 Ferrari LaFerrari


Wed Apr 03 16:33:22 PDT 2024\
Car listed onto marketplace: 2012 Lamborghini Aventador


Wed Apr 03 16:33:22 PDT 2024\
Car listed onto marketplace: 1994 Mazda MX-5 Miata


Wed Apr 03 16:33:22 PDT 2024\
Car listed onto marketplace: 2019 Porsche 911 GT3 RS


Wed Apr 03 16:33:22 PDT 2024\
Car listed onto marketplace: 1985 Toyota Trueno AE86


Wed Apr 03 16:33:22 PDT 2024\
Car listed onto marketplace: 2018 Honda Civic Type R


Wed Apr 03 16:33:22 PDT 2024\
Car listed onto marketplace: 2015 Dodge Challenger


Wed Apr 03 16:33:22 PDT 2024\
Car listed onto marketplace: 2020 Chevrolet Stingray

Wed Apr 03 16:33:33 PDT 2024\
Set account balance to: 5,000,000


Wed Apr 03 16:33:42 PDT 2024\
Bought car and added to garage: 2011 Bugatti Veyron


Wed Apr 03 16:33:42 PDT 2024\
Car listed onto marketplace: 2011 Bugatti Veyron


Wed Apr 03 16:33:46 PDT 2024\
Removed car from garage: 2011 Bugatti Veyron


Wed Apr 03 16:33:46 PDT 2024\
Set account balance to: 5,000,000


Wed Apr 03 16:34:45 PDT 2024\
Car listed onto marketplace: 2024 MyCar Car 1


## Phase 4: Task 3

I would make the Account class extend the AccountWorkRoom class, since both classes have similar functionality and 
methods. This change would reduce duplicated code, resulting in improved code readability. Also, the GarageWorkRoom 
and Garage classes can be combined into one class since they both have similar functionality and an association with 
the Car class. The Car class has too many associations with other classes (high coupling), so a refactor that removes 
those associations would reduce coupling. So, I would make the list of cars be stored in the AbstractMenu class so that 
all it's subclasses can inherit the list of cars. Additionally, the CarList and SellCar menus can be refactored 
similarly so that they both won't need an association with the Car class. These changes would reduce coupling between 
classes and allow for improved code maintainability.

## Image Credits

Car images from https://forza.fandom.com/wiki/Forza_Wiki

Logo generated by DALL-E 3 with prompt: "car marketplace logo sporty"
