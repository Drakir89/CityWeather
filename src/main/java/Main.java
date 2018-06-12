
public class Main {
    public static void main(String[] args){
        OpenWeatherMapApi openWeatherMapApi = new OpenWeatherMapApi(
                "api.openweathermap.org/data/2.5/",
                "4fa886596b9dfea3b5144b3dd2acda6d");
        ConsoleUi consoleUi = new ConsoleUi(openWeatherMapApi);
        consoleUi.begin();
    }
}
