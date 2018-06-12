import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//this class handles interaction with the user
class ConsoleUi {
    private OpenWeatherMapApi owmApi;
    private CityWeather cityWeather = null;

    private enum Menu {PICK_CITY, CITY_DETAILS}
    private Menu nextMenu = Menu.PICK_CITY;

    private enum CitySearchMethod {NAME, NAME_AND_COUNTRY, ID}
    private CitySearchMethod citySearchMethod = CitySearchMethod.NAME;

    private enum TemperatureUnit {FAHRENHEIT, CELSIUS, KELVIN}
    private TemperatureUnit temperatureUnit = TemperatureUnit.CELSIUS;

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private boolean returnToMenu = true;

    ConsoleUi(OpenWeatherMapApi givenOwmApi) {
        owmApi = givenOwmApi;
    }

    void begin(){
        System.out.println("Welcome to my weather app!");
        while (returnToMenu){
            displayMenu();
        }
    }

    private String getUserInput(){
        String input = "";
        try {
            input = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }

    private void displayMenu(){
        switch (nextMenu){
            case PICK_CITY:
                displayPickCityMenu();
                break;
            case CITY_DETAILS:
                displayCityDetailsMenu();
                break;
            default:
                System.out.println("Failed to display menu");
        }
    }

    private void displayPickCityMenu(){
        System.out.println("1: choose a city");
        System.out.println("2: change search method");
        System.out.println("e: exit");
        String input = getUserInput();
        switch (input){
            case "1":
                String httpResponse = getCityFromApi();
                int statusCode = owmApi.getLatestStatusCode();
                if(statusCode == 200) {
                    cityWeather = new CityWeather(httpResponse);
                    displayCityOverview();
                    nextMenu = Menu.CITY_DETAILS;
                }
                else {
                    System.out.println("City search failed. Http status code: " + statusCode);
                }
                break;
            case "2":
                changeCitySearchMethod();
                break;
            case "e":
                returnToMenu = false;
                break;
            default:
                rejectInput();
        }
    }

    private String getCityFromApi(){
        String input;
        String httpResponse = "";
        switch (citySearchMethod){
            case NAME:
                System.out.println("Please enter the city name:");
                input = getUserInput();
                httpResponse = owmApi.searchCityByName(input);
                break;
            case NAME_AND_COUNTRY:
                System.out.println("Please enter the city name and country code. Example: london,uk");
                input = getUserInput();
                httpResponse = owmApi.searchCityByNameAndCountry(input);
                break;
            case ID:
                System.out.println("Please enter the city ID number:");
                input = getUserInput();
                httpResponse = owmApi.searchCityById(input);
                break;
            default:
                System.out.println("No searchMethod set");
        }
        return httpResponse;
    }

    private void rejectInput(){
        System.out.println("Sorry, that input is invalid. Please try again:");
    }

    private void changeCitySearchMethod() {
        System.out.println("how should we find the city?");
        System.out.println("1: by city name, eg \"london\"");
        System.out.println("2: by name and country code, eg \"london,uk\"");
        System.out.println("3: by OpenWeatherMap ID, eg \"2643743\"");

        String input = getUserInput();
        switch (input) {
            case "1":
                this.citySearchMethod = CitySearchMethod.NAME;
                System.out.println("You chose to use the city name only");
                break;
            case "2":
                this.citySearchMethod = CitySearchMethod.NAME_AND_COUNTRY;
                System.out.println("You chose to use the city name and the country code");
                break;
            case "3":
                this.citySearchMethod = CitySearchMethod.ID;
                System.out.println("You chose to use the OpenWeatherMap ID number");
                break;
            default:
                rejectInput();
        }
    }

    private void displayTemperature(){
        switch (temperatureUnit){
            case CELSIUS:
                System.out.println(cityWeather.getCelsius() + " degrees Celsius");
                break;
            case FAHRENHEIT:
                System.out.println(cityWeather.getFahrenheit() + " degrees Fahrenheit");
                break;
            case KELVIN:
                System.out.println(cityWeather.getKelvin() + " degrees Kelvin");
                break;
            default:
                System.out.println("temperature not set");
        }
    }

    private void displayCityDetailsMenu(){
        System.out.println("1: see city overview");
        System.out.println("2: see weather conditions");
        System.out.println("3: see city data");
        System.out.println("4: change default temperature unit");
        System.out.println("5: change city");
        System.out.println("e: exit");

        String input = getUserInput();
        switch (input){
            case "1":
                displayCityOverview();
                break;
            case "2":
                displayWeatherConditions();
                break;
            case "3":
                displayCityData();
                break;
            case "4":
                changeTemperatureUnit();
                break;
            case "5":
                nextMenu = Menu.PICK_CITY;
                break;
            case "e":
                returnToMenu = false;
        }
    }

    private void displayCityOverview(){
        System.out.println("--------------------------------");
        System.out.println(cityWeather.getCityName() + ", " + cityWeather.getCountryCode());
        System.out.println(cityWeather.getWeatherDescription());
        displayTemperature();
        System.out.println("--------------------------------");
    }

    private void displayWeatherConditions(){
        System.out.println("--------------------------------");
        System.out.println("humidity: " + cityWeather.getHumidity());
        System.out.println("pressure: " + cityWeather.getPressure());
        System.out.println("visibility: " + cityWeather.getVisibility());
        System.out.println("wind speed: " + cityWeather.getWindSpeed());
        System.out.println("wind direction: " + cityWeather.getWindDirectionAsDegrees());
        System.out.println("--------------------------------");
    }

    private void displayCityData(){
        System.out.println("--------------------------------");
        System.out.println(cityWeather.getCityName() + ", " + cityWeather.getCountryCode());
        System.out.println("ID: " + cityWeather.getCityId());
        System.out.println("longitude: " + cityWeather.getLongitude());
        System.out.println("latitude: " + cityWeather.getLatitude());
        System.out.println("--------------------------------");
    }

    private void changeTemperatureUnit(){
        System.out.println("1: Celsius");
        System.out.println("2: Fahrenheit");
        System.out.println("3: Kelvin");
        String input = getUserInput();
        switch (input){
            case "1":
                temperatureUnit = TemperatureUnit.CELSIUS;
                System.out.println("Temperature will be shown as Celsius");
                break;
            case "2":
                temperatureUnit = TemperatureUnit.FAHRENHEIT;
                System.out.println("Temperature will be shown as Fahrenheit");
                break;
            case "3":
                temperatureUnit = TemperatureUnit.KELVIN;
                System.out.println("Temperature will be shown as Kelvin");
                break;
            default:
                rejectInput();
        }
    }
}
