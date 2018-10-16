import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

//this class holds and interprets data for the weather in a single city
class CityWeather {
    private final String UNAVAILABLE = "unavailable";

    private BigDecimal longitude;
    private boolean longitudeFound = false;
    private BigDecimal latitude;
    private boolean latitudeFound = false;
    private String weatherDescription;
    private boolean weatherDescriptionFound = false;
    private BigDecimal temperature;
    private boolean temperatureFound = false;
    private int pressure;
    private boolean pressureFound = false;
    private int humidity;
    private boolean humidityFound = false;
    private int visibility;
    private boolean visibilityFound = false;
    private BigDecimal windSpeed;
    private boolean windSpeedFound = false;
    private int windDirectionAsDegrees;
    private boolean windDirectionAsDegreesFound = false;
    private String countryCode;
    private boolean countryCodeFound = false;
    private String cityName;
    private boolean cityNameFound = false;
    private int cityId;
    private boolean cityIdFound = false;

    CityWeather(String apiResponse){
        JSONObject jsonSource = new JSONObject(apiResponse);

        //there is an example of the expected JSON object at the bottom of the class
        final String JSON_COORD_KEY = "coord";
        final String JSON_LON_KEY = "lon";
        final String JSON_LAT_KEY = "lat";
        final String JSON_WEATHER_KEY = "weather";
        final String JSON_DESCRIPTION_KEY = "description";
        final String JSON_MAIN_KEY = "main";
        final String JSON_TEMP_KEY = "temp";
        final String JSON_PRESSURE_KEY = "pressure";
        final String JSON_HUMIDITY_KEY = "humidity";
        final String JSON_VISIBILITY_KEY = "visibility";
        final String JSON_WIND_KEY = "wind";
        final String JSON_SPEED_KEY = "speed";
        final String JSON_DEG_KEY = "deg";
        final String JSON_SYS_KEY = "sys";
        final String JSON_COUNTRY_KEY = "country";
        final String JSON_NAME_KEY = "name";
        final String JSON_ID_KEY = "id";

        if (jsonSource.has(JSON_COORD_KEY)) {
            if (jsonSource.getJSONObject(JSON_COORD_KEY).has(JSON_LON_KEY)) {
                longitude = jsonSource.getJSONObject(JSON_COORD_KEY).getBigDecimal(JSON_LON_KEY);
                longitudeFound = true;
            }
            if (jsonSource.getJSONObject(JSON_COORD_KEY).has(JSON_LAT_KEY)){
                latitude = jsonSource.getJSONObject(JSON_COORD_KEY).getBigDecimal(JSON_LAT_KEY);
                latitudeFound = true;
            }
        }
        //I have no idea why the next json data is in an array of one, but that's what the api gives you
        if (jsonSource.has(JSON_WEATHER_KEY)) {
            if (!jsonSource.getJSONArray(JSON_WEATHER_KEY).isNull(0)){
                if(jsonSource.getJSONArray(JSON_WEATHER_KEY).getJSONObject(0).has(JSON_DESCRIPTION_KEY)) {
                    weatherDescription = jsonSource.getJSONArray(JSON_WEATHER_KEY).getJSONObject(0).getString(JSON_DESCRIPTION_KEY);
                    weatherDescriptionFound = true;
                }
            }
        }
        if (jsonSource.has(JSON_MAIN_KEY)) {
            if (jsonSource.getJSONObject(JSON_MAIN_KEY).has(JSON_TEMP_KEY)) {
                temperature = jsonSource.getJSONObject(JSON_MAIN_KEY).getBigDecimal(JSON_TEMP_KEY);
                temperatureFound = true;
            }
            if (jsonSource.getJSONObject(JSON_MAIN_KEY).has(JSON_PRESSURE_KEY)) {
                pressure = jsonSource.getJSONObject(JSON_MAIN_KEY).getInt(JSON_PRESSURE_KEY);
                pressureFound = true;
            }
            if (jsonSource.getJSONObject(JSON_MAIN_KEY).has(JSON_HUMIDITY_KEY)) {
                humidity = jsonSource.getJSONObject(JSON_MAIN_KEY).getInt(JSON_HUMIDITY_KEY);
                humidityFound = true;
            }
        }
        if (jsonSource.has(JSON_VISIBILITY_KEY)) {
            visibility = jsonSource.getInt(JSON_VISIBILITY_KEY);
            visibilityFound = true;
        }
        if (jsonSource.has(JSON_WIND_KEY)) {
            if (jsonSource.getJSONObject(JSON_WIND_KEY).has(JSON_SPEED_KEY)) {
                windSpeed = jsonSource.getJSONObject(JSON_WIND_KEY).getBigDecimal(JSON_SPEED_KEY);
                windSpeedFound = true;
            }
            if (jsonSource.getJSONObject(JSON_WIND_KEY).has(JSON_DEG_KEY))
                windDirectionAsDegrees = jsonSource.getJSONObject(JSON_WIND_KEY).getInt(JSON_DEG_KEY);
                windDirectionAsDegreesFound = true;
        }
        if (jsonSource.has(JSON_SYS_KEY)) {
            if (jsonSource.getJSONObject(JSON_SYS_KEY).has(JSON_COUNTRY_KEY)) {
                countryCode = jsonSource.getJSONObject(JSON_SYS_KEY).getString(JSON_COUNTRY_KEY);
                countryCodeFound = true;
            }
        }
        if (jsonSource.has(JSON_NAME_KEY)) {
            cityName = jsonSource.getString(JSON_NAME_KEY);
            cityNameFound = true;
        }
        if (jsonSource.has(JSON_ID_KEY)) {
            cityId = jsonSource.getInt(JSON_ID_KEY);
            cityIdFound = true;
        }
    }

    String getLongitude() {
        return (longitudeFound ? longitude.toString() : UNAVAILABLE);
    }

    String getLatitude() {
        return (latitudeFound ? latitude.toString() : UNAVAILABLE);
    }

    String getWeatherDescription() {
        return (weatherDescriptionFound ? weatherDescription : UNAVAILABLE);
    }

    String getKelvin() {
        return (temperatureFound ? temperature.toString() : UNAVAILABLE);
    }

    String getCelsius() {
        String response;
        if (temperatureFound) {
            BigDecimal celsius = temperature.subtract(new BigDecimal("273.15"));
            response = celsius.toString();
        } else
            response = UNAVAILABLE;
        return response;
    }

    String getFahrenheit(){
        String response;
        if (temperatureFound) {
            BigDecimal fahrenheit = temperature.multiply(new BigDecimal("1.8"));
            fahrenheit = fahrenheit.subtract(new BigDecimal("459.67"));
            fahrenheit = fahrenheit.setScale(2, BigDecimal.ROUND_HALF_UP);
            response = fahrenheit.toString();
        } else
            response = UNAVAILABLE;
        return (response);
    }

    String getPressure() {
        return (pressureFound ? String.valueOf(pressure) : UNAVAILABLE);
    }

    String getHumidity() {
        return (humidityFound ? String.valueOf(humidity) : UNAVAILABLE);
    }

    String getVisibility() {
        return (visibilityFound ? String.valueOf(visibility) : UNAVAILABLE);
    }

    String getWindSpeed() {
        return (windSpeedFound ? windSpeed.toString() : UNAVAILABLE);
    }

    String getWindDirectionAsDegrees() {
        return (windDirectionAsDegreesFound ? String.valueOf(windDirectionAsDegrees) : UNAVAILABLE);
    }

    String getCountryCode() {
        return (countryCodeFound ? countryCode : UNAVAILABLE);
    }

    String getCityName() {
        return (cityNameFound ? cityName : UNAVAILABLE);
    }

    String getCityId() {
        return (cityIdFound ? String.valueOf(cityId) : UNAVAILABLE);
    }
}

/*
    below is an example of the type of JSON object that this class uses
{
    "coord": {
        "lon": 153.02,
        "lat": -27.47
    },
    "weather": [
        {
            "id": 800,
            "main": "Clear",
            "description": "clear sky",
            "icon": "01n"
        }
    ],
    "base": "stations",
    "main": {
        "temp": 288.58,
        "pressure": 1022,
        "humidity": 82,
        "temp_min": 287.15,
        "temp_max": 290.15
    },
    "visibility": 10000,
    "wind": {
        "speed": 1.5,
        "deg": 160
    },
    "clouds": {
        "all": 0
    },
    "dt": 1527156000,
    "sys": {
        "type": 1,
        "id": 8164,
        "message": 0.0022,
        "country": "AU",
        "sunrise": 1527107168,
        "sunset": 1527145391
    },
    "id": 2174003,
    "name": "Brisbane",
    "cod": 200
}*/