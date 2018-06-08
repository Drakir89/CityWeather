import org.json.JSONObject;

import java.math.BigDecimal;

//this class holds and interprets data for the weather in a single city
//TODO: Currently class explodes if the JSON object doens't fit. plz fix
public class CityWeather {
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String weatherDescription;
    private BigDecimal temperature;
    private int pressure;
    private int humidity;
    private int visibility;
    private BigDecimal windSpeed;
    private int windDirectionAsDegrees;
    private String countryCode;
    private String cityName;
    private int cityId;

    CityWeather(JSONObject jsonSource){
        longitude = jsonSource.getJSONObject("coord").getBigDecimal("lon");
        latitude = jsonSource.getJSONObject("coord").getBigDecimal("lat");
        //I have no idea why the next json data is in an array of one, but that's what the api gives you
        weatherDescription = jsonSource.getJSONArray("weather").getJSONObject(0).getString("description");
        temperature = jsonSource.getJSONObject("main").getBigDecimal("temp");
        pressure = jsonSource.getJSONObject("main").getInt("pressure");
        humidity = jsonSource.getJSONObject("main").getInt("humidity");
        visibility = jsonSource.getInt("visibility");
        windSpeed = jsonSource.getJSONObject("wind").getBigDecimal("speed");
        windDirectionAsDegrees = jsonSource.getJSONObject("wind").getInt("deg");
        countryCode = jsonSource.getJSONObject("sys").getString("country");
        cityName = jsonSource.getString("name");
        cityId = jsonSource.getInt("id");
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public BigDecimal getKelvin() {
        return temperature;
    }

    public BigDecimal getCelsius() {
        return temperature.subtract(new BigDecimal("273.15"));
    }

    public BigDecimal getFahrenheit(){
        return temperature.multiply(new BigDecimal("1.8").subtract(new BigDecimal("459.67")));
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getVisibility() {
        return visibility;
    }

    public BigDecimal getWindSpeed() {
        return windSpeed;
    }

    public int getWindDirectionAsDegrees() {
        return windDirectionAsDegrees;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCityName() {
        return cityName;
    }

    public int getCityId() {
        return cityId;
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