# Library Catalogue

## MariaDB database

- open installation directory, e.g. "C:\Program Files\MariaDB 10.6\bin"
- populate database with initial data
```
mysql -u root -proot --default-character-set=utf8 < "PROJECT_DIRECTORY\database\mariadb\schema.sql
mysql -u root -proot --default-character-set=utf8 < "PROJECT_DIRECTORY\database\data.sql
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
