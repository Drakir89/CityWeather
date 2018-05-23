import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//this class contains methods to
public class OpenWeatherMapApi {
    private HttpClient httpClient = HttpClientBuilder.create().build();
    private String apiAddress;
    private String appId;

    public OpenWeatherMapApi (String apiAddress, String appId){
        this.apiAddress = apiAddress;
        this.appId = appId;
}

//    public CityWeather getWeatherById(String id){}

    public CityWeather getWeatherByCityName (String name){
        String restRequest = apiAddress + "weather?q=" + name + "&APPID=" + appId;
        JSONObject responseJson = requestJsonWithHttp(restRequest);
    }

//    public CityWeather getWeatherByCityNameAndCountry (String name, String countryCode){}

    private JSONObject requestJsonWithHttp(String restRequest){
        String responseString = httpRequestToString(restRequest);
        return new JSONObject(responseString);
    }

    private String httpRequestToString(String restRequest){
        try {
            HttpGet httpRequest = new HttpGet(restRequest);
            HttpResponse httpResponse = httpClient.execute(httpRequest);
            InputStream inputStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String responseString = reader.readLine();
            return responseString;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
