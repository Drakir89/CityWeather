import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//Represents the external API to the other classes. Primary use is to return CityWeather objects
class OpenWeatherMapApi {
    private HttpClient httpClient = HttpClientBuilder.create().build();
    private String apiAddress;
    private String appId;

    public OpenWeatherMapApi (String apiAddress, String appId){
        this.apiAddress = apiAddress;
        this.appId = appId;
}

    CityWeather getWeatherById(String id){
        String restRequest = apiAddress + "weather?id=" + id + "&APPID=" + appId;
        JSONObject responseAsJson = requestJsonWithHttp(restRequest);
        return new CityWeather(responseAsJson);
    }

    CityWeather getWeatherByCityName (String name){
        String restRequest = apiAddress + "weather?q=" + name + "&APPID=" + appId;
        JSONObject responseAsJson = requestJsonWithHttp(restRequest);
        return new CityWeather(responseAsJson);
    }

    CityWeather getWeatherByCityNameAndCountry (String nameAndCountry){
        String restRequest = apiAddress + "weather?q=" + nameAndCountry + "&APPID=" + appId;
        JSONObject responseAsJson = requestJsonWithHttp(restRequest);
        return new CityWeather(responseAsJson);
    }

    private JSONObject requestJsonWithHttp(String restRequest){
        HttpResponse httpResponse = executeHttpGetRequest(restRequest);
        String responseString = httpResponseToString(httpResponse);
        return new JSONObject(responseString);
    }

    private HttpResponse executeHttpGetRequest(String restRequest){
        HttpResponse httpResponse = null;
        try {
        HttpGet httpRequest = new HttpGet(restRequest);
        httpResponse = httpClient.execute(httpRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpResponse;
    }

    private String httpResponseToString (HttpResponse httpResponse){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
