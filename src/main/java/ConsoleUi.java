import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//this class handles interaction with the user
public class ConsoleUi {
    private OpenWeatherMapApi owmApi;
    private CityWeather cityWeather = null;

    private enum Menu {PICK_CITY, CITY_DETAILS}
    private Menu nextMenu = Menu.PICK_CITY;

    private enum CitySearchMethod {NAME, NAME_AND_COUNTRY, ID}
    private CitySearchMethod currentCitySearchMethod = CitySearchMethod.NAME;

    private enum TemperatureUnit {FAHRENHEIT, CELSIUS, KELVIN}
    private TemperatureUnit temperatureUnit = TemperatureUnit.CELSIUS;

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private boolean returnToMenu = true;

    public ConsoleUi(OpenWeatherMapApi givenOwmApi) {
        owmApi = givenOwmApi;
    }

    public void beginConversation(){
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
                //TODO: insert menu method here
                break;
            case CITY_DETAILS:
                //TODO: insert menu method here
                break;
            default:
                System.out.println("Failed to display menu");
        }
    }

    private void pickCity(){
        System.out.println("0: enter city name"); //TODO: change response depending on the city search method
        System.out.println("1: change search method");
        String input = getUserInput();
        switch (input){
            case "0":
                //TODO: add method to get cityweather from the api
                nextMenu = Menu.CITY_DETAILS;
                break;
            case "1":
                changeCitySearchMethod();
                break;
            default:
                rejectInput();
        }
    }

    private void rejectInput(){
        System.out.println("Sorry, that input is invalid. Please try again:");
    }

    private void changeCitySearchMethod() {
        System.out.println("how should we find the city?");
        System.out.println("0: by city name, eg \"london\"");
        System.out.println("1: by name and country code, eg \"london, uk\"");
        System.out.println("2: by OpenWeatherMap ID, eg \"2643743\"");

        String input = getUserInput();
        switch (input) {
            case "0":
                this.currentCitySearchMethod = CitySearchMethod.NAME;
                System.out.println("You chose to use the city name only");
                break;
            case "1":
                this.currentCitySearchMethod = CitySearchMethod.NAME_AND_COUNTRY;
                System.out.println("You chose to use the city name and the country code");
                break;
            case "2":
                this.currentCitySearchMethod = CitySearchMethod.ID;
                System.out.println("You chose to use the OpenWeatherMap ID number");
                break;
            default:
                rejectInput();
        }
    }
}
