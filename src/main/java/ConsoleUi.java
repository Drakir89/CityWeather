import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//this class handles interaction with the user
public class ConsoleUi {
    private OpenWeatherMapApi owmApi;
    private CityWeather cityWeather = null;

    private enum QuestionToRepeat {PICK_FIRST_CITY, CHANGE_CITY_SEARCH_METHOD, CITY_DETAILS}
    private QuestionToRepeat questionToRepeat = QuestionToRepeat.PICK_FIRST_CITY;

    private enum CitySearchMethod {NAME, NAME_AND_COUNTRY, ID}
    private CitySearchMethod currentCitySearchMethod = CitySearchMethod.NAME;

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public ConsoleUi(OpenWeatherMapApi givenOwmApi) {
        owmApi = givenOwmApi;
    }

    private void repeatQuestion(){
        switch (questionToRepeat){
            case PICK_FIRST_CITY:
                //TODO: insert question method here
                break;
            case CHANGE_CITY_SEARCH_METHOD:
                changeCitySearchMethod();
                break;
            case CITY_DETAILS:
                //TODO: insert question method here
                break;
        }
    }

    private void rejectInput(){
        System.out.println("Sorry, that input is invalid. Please try again:");
        repeatQuestion();
    }

    private void changeCitySearchMethod() {
        try {
            System.out.println("how should we find the city?");
            System.out.println("0: by city name, eg \"london\"");
            System.out.println("1: by name and country code, eg \"london, uk\"");
            System.out.println("2: by OpenWeatherMap ID, eg \"2643743\"");

            String input = reader.readLine();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
