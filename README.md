# City Weather App

The application is designed to provide users with personalized recommendations for outdoor activities and clothing based on current weather and air quality conditions in given location.
Users can input the coordinates of their city and receive recommendations for what to wear and whether it's a good day to go outside.
They can also retrieve information about the weather and pollution, application keeps coordinates of cities.

## Technologies

The application currently uses the following frameworks and technologies:

- Spring Boot: Web, Data JPA, Test, Validation
- Unit tests with: JUnit, Mockito
- Lombok
- Maven

## External API used

At the moment, the application is using two external API:

- Air Quality API to retrieve air quality for given location.
- Weather API to retrieve current weather and a 13-day forecast for the given location.

## How to run

To start the application clone this repo and run the `CityWeatherApplication` class or type `./mvnw spring-boot:run` in your IDE terminal.

## Endpoints

The application provides many endpoints, but you should start with retrieving coordinates for city.
- `http://localhost:8080/city-weather-app/localization/city?country-code=PL&city=Kalisz` - retrieves coordinates for given country-code and city like this:
```json
{
    "countryCode": "PL",
    "name": "Poznań",
    "latitude": "52.40692",
    "longitude": "16.92993"
}
```
then you can for example check the current weather for given localization
- `http://localhost:8080/city-weather-app/weather?latitude=52.40692&longitude=16.92993` - retrieves current weather for given coordinates like this:
```json
{
    "symbol": "n400",
    "symbolPhrase": "overcast",
    "temperature": 14,
    "feelsLikeTemp": 14,
    "dewPoint": 8,
    "windSpeed": 9,
    "uvIndex": 0,
    "pressure": 1013.29,
    "visibility": 10000
}
```
the main core of the app is giving recommendations, you can get walk recommendation like this
- `http://localhost:8080/city-weather-app/walk?latitude=52.40692&longitude=16.92993` - retrieves walk recommendation for current air quality and uv index for given coordinates like this:
```json
{
    "recommendation": "RECOMMENDED",
    "aqiScale": "GOOD",
    "uvIndexScale": "LOW"
}
```
and you can get wear recommendation like this
- `http://localhost:8080/city-weather-app/wear?date=YYYY-MM-DD&latitude=52.40692&longitude=16.92993` - retrieves wear recommendation for current weather for given date and coordinates like this:
```json
{
    "weatherDesc": "COOL",
    "head": "NONE",
    "upperBody": "SHIRT",
    "lowerBody": "JEANS",
    "foot": "SNEAKERS",
    "rain": "NO"
}
```

You can find all endpoints here:
![Swagger screenshot](src/main/resources/screenshots/swagger.JPG)

## Test coverage

![Test coverage screenshot](src/main/resources/screenshots/test-coverage.JPG)
