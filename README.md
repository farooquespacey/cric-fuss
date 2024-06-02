# CricFuss (Scorecard) application

## What is it

It gives you consolidated scorecard from the historical match given that the match details are fed into it. 

## Prerequisite

- **Java 17**
- **Docker**
- **Postgres**

## Getting Started

To install this example application, run the following commands:

1. `git clone https://github.com/farooquespacey/cric-fuss.git`
2. `docker compose -f docker-compose.yml up -d`

## Build Instruction

1. `mvn clean package`
2. `docker build -t farooquespacey/cric-fuss:latest .`
3. `docker push farooquespacey/cric-fuss`
   
Open `src/main/resources/application.properties` to see details

## Limitations/Variations from the original design
- UI is not complete but didn't want to delay sending the solution that I have so far which works well backend-wise.
- Had to use Java-17 instead of Java-11 since the latest SpringBoot requires 17 version.
- Avoided using Kafka since the addMatchInfo/ takes just a second with my implementation (So made it synchronous operation)
- Renamed scorecard.table to inning.table to make more sense out of it
- Included "wickets" in the "total".{..} attribute as that was missing
- Not finding 'no_balls' in "extras" field in the given schema, nor did I find 'runout' case in the provided schema file.
- Not handling instances like "Rain stopped play", "Match abandoned", etc.
- I thought of using Team and Player as a separate entity but avoided doing as we don't have more than the name to be displayed to the client with regards to Player/Team so no use of having tables with just one field as per the current use case of Scorecard.

