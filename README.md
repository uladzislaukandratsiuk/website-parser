# website-parser

## How to run locally

Run with Main class
```
mvn clean packge
```
```
run WebsiteParserApplication.java
```

Or run with docker
```
mvn clean packge
```
```
docker build . -t website-parser
```
```
docker run -d -p 8080:8080 website-parser
```
##### Don't forget to stop and clean up unused containers and images :)

Website parser api should be available at
```
http://localhost:8080/reviews/{domain}
```
### How it works example 
![](../../Downloads/Kooha-2022-10-13-17-34-27.gif)