# Read Me First
This API will be used to fake fishing forms traffic in order to walnut good data in a bunch of fake data

# How to build & run project
## Prerequisite
- JAVA 17
- MAVEN
- ChromeDriver ( for MAC users : brew install chromedriver )
## Build
mvn clean install
## Run api
mvn spring-boot:run

# How to test
curl --location --request POST "http://localhost:8080/api/traffic-faker" --header "Content-Type: application/json" --data-raw "{\"url\" : \"https://staythai.com/Vymqfb39ax/RU5uPXCSV2/index.html#\"}"
