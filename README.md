# Library Catalogue

## H2 database

- on first run databases will be created and populated with initial data
- for subsequent runs change (comment out line 4, comment line 5) 'spring.datasource.url' property in application.yaml
- to access H2 console open http://localhost:8080/h2-console, use credentials: admin/admin

## Usage

### Prerequisites
- JDK 11
- maven

### Build 
```
mvn clean install
```

### Run 
```
mvn spring-boot:run
```
