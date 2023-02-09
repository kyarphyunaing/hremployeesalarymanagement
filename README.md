## Employee Salary Management System
This is the backend project of employee salary management system covering multiple user stories as per requirements. 
There are two parts of the system backend that provides consumable APIs in RESTFul way and a frontend that consumes
that API.

## Getting Started
You may need to install the following tools before you clone this repository into your development machine. 

### Prerequisites
1. OpenJDK 18 ( https://openjdk.org/projects/jdk/ )
2. Maven ( https://maven.apache.org/download.cgi ) 
3. NodeJS ( https://nodejs.org/en/ )
4. Angular CLI ( https://angular.io/cli )
5. Mysql Server ( https://dev.mysql.com/downloads/mysql/ ) 
6. Docker and Docker Compose ( https://www.docker.com/products/docker-desktop/ )

### Installation
We are using maven and spring boot and we may need to install some maven packages before any development.

1. Clone the repo
	```sh
		$ git clone https://github.com/kyarphyunaing/hremployeesalarymanagement.git
		$ cd hremployeesalarymanagement && git submodule init && git submodule update
	```
2. Install maven packages
	```sh
		$ mvn clean install
	```
3. Setup the Database
	```sh
		$ mysql -u root -p
		$ CREATE DATABASE employeedb;
	```
4. Run the spring boot app
	```sh
		$ mvn spring-boot:run
	```

Or you can go the docker way.
1. Clone the repo
	```sh
		$ git clone https://github.com/kyarphyunaing/hremployeesalarymanagement.git
		$ cd hremployeesalarymanagement && git submodule init && git submodule update
	```
2. Bring up the services by docker-compose
	```sh
		$ docker-compose up -d
	```

Then everything will be up and running as per the configuration in docker-compose. For development changes,
it is smater to do it on the host machine but if you want to update your docker containers by services here
is how it is done.

```sh
	$ docker-compose up --build --force-recreate --no-deps -d <frontend,app>
```
It take a while to recreate the docker image with updated source and deps.

## Usage
Now a REST service is running a port on your machine and you can consume them as per followed.

	1. CSV File Upload Web Service : http://localhost:8080/users/upload?file 
	   (In employee table, id and login are unique numbers so added backend business logic in service layer for a complex use case of swapping logins between 2 IDs. Moreover, it is added to satify for csv in empty row case, employee column case, comment row case, and so on)
	2. Filtered Employee Web Service : http://localhost:8080/users/users?minSalary=0&maxSalary=4000&offset=0&limit=30&sort=+id
	   (For this webservice, multiple requests are called to backend when clicking features of datatable but it might be to test paging or sorting concepts for this assessment. This service is modified a little to get total employees for pages of datatable in response body. )
	3. Creating Employee Web Service : http://localhost:8080/users/user
	   (It can be requested by json request body
	    {
   			"id": "e11",
    		"login": "john",
    		"name": "john smith",
    		"salary": "1400.50"
		}.)
	4. Retrieving Employee Web Service : http://localhost:8080/users/user?id=1
	5. Updating Employee Web Service : http://localhost:8080/users/user?id=2&login=CeCee&name=CeCee&salary=1300.50 
	6. Deleting Employee Web Service : http://localhost:8080/users/user
	   (It can be requested by json request body
	   {
    		"id": "e02"
		}. It is more appropriate for adding request body instead of request params avoiding from other distraction requests.)

<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<!-- CONTACT -->
## Contact

Kyar Phyu Naing - kyarphyunaing1994@gmail.com
