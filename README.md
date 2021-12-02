# Library Catalogue

## MariaDB database
### Installation Instruction
#### Install binaries
- Ubuntu: Read instruction here https://www.digitalocean.com/community/tutorials/how-to-install-mariadb-on-ubuntu-20-04-ru
- OS X: Read instruction here https://mariadb.com/kb/en/installing-mariadb-on-macos-using-homebrew/
#### Setup user
- Add user `admin` with password `admin`
```
GRANT ALL ON *.* TO 'admin'@'%' IDENTIFIED BY 'password' WITH GRANT OPTION;
```
- Flush the privileges
```
FLUSH PRIVILEGES;
```
#### Import initial data
- Populate database with initial data
```
mysql -u admin -padmin --default-character-set=utf8 < "PROJECT_DIRECTORY\database\mariadb\schema.sql
mysql -u admin -padmin --default-character-set=utf8 < "PROJECT_DIRECTORY\database\data.sql
```

## H2 database

- on first run databases will be created and populated with initial data
- for subsequent runs change (comment out line 3, comment line 4) 'spring.datasource.url' property in application.yaml
- to access H2 console open http://localhost:8080/h2-console, use credentials: admin/admin

## Usage

### Prerequisites
- [JDK 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)
- [Maven](https://maven.apache.org/install.html)
- [MariaDB](https://mariadb.com/kb/en/installing-mariadb-msi-packages-on-windows/)

### Build 
```
mvn clean install
```

### Run 
```
mvn spring-boot:run
```

### Use
#### Web UI
Open http://localhost:8080

#### REST API
Use [Postman](https://www.postman.com/) to test API described on [SwaggerHub](https://app.swaggerhub.com/apis/akrawchuk89/LibCat/v1)
