import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//Represents the external API to the other classes. Primary use is to return CityWeather objects
class OpenWeatherMapApi {
    private HttpClient httpClient = HttpClientBuilder.create().build();
    private String apiAddress;
    private String appId;
    private int latestStatusCode;

    OpenWeatherMapApi (String apiAddress, String appId){
        this.apiAddress = apiAddress;
        this.appId = appId;
}

    String searchCityById(String id){
        String restRequest = apiAddress + "weather?id=" + id + "&APPID=" + appId;
        return requestStringWithHttp(restRequest);
    }

    String searchCityByName(String name){
        String restRequest = apiAddress + "weather?q=" + name + "&APPID=" + appId;
        return requestStringWithHttp(restRequest);
    }

    String searchCityByNameAndCountry(String nameAndCountry){
        String restRequest = apiAddress + "weather?q=" + nameAndCountry + "&APPID=" + appId;
        return requestStringWithHttp(restRequest);
    }

    private String requestStringWithHttp(String restRequest){
        restRequest = restRequest.replaceAll("\\s", "%20"); //replace whitespaces with URI escape characters for SPACE
        HttpResponse httpResponse = executeHttpGetRequest(restRequest);
        return httpResponseToString(httpResponse);
    }

    private HttpResponse executeHttpGetRequest(String restRequest){
        HttpResponse httpResponse = null;
        try {
        HttpGet httpRequest = new HttpGet(restRequest);
        httpResponse = httpClient.execute(httpRequest);
        latestStatusCode = httpResponse.getStatusLine().getStatusCode();
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

    int getLatestStatusCode(){
        return latestStatusCode;
    }
}
