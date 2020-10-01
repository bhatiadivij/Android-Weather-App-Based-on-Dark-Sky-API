package csci571.hw9;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class HandlerFunctions {
    String baseUrl = "http://hw8-env.5cfn2z79ec.us-east-2.elasticbeanstalk.com/";

    public Map<String, String> stringToMap(String string){
        Map<String, String> response = new HashMap<String,String>();
        ObjectMapper mapper = new ObjectMapper();
        try{
            response = mapper.readValue(string, new TypeReference<Object>(){});
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return response;
    }
    public Map fetchLocation() throws ExecutionException, InterruptedException, UnsupportedEncodingException{
        String url = "http://ip-api.com/json";
        String result = new Utilities().execute(url).get();
        Map<String, String> response = stringToMap(result);
        return response;
    }

    public Map<String,String> fetchWeatherReport(String lat, String lng, String city, String state) throws ExecutionException, InterruptedException, UnsupportedEncodingException, IOException {
        String url = baseUrl + "weather_search_with_coordinates?lat="+ URLEncoder.encode(lat, "utf-8")+"&lng="+ URLEncoder.encode(lng, "utf-8")+"&city="+URLEncoder.encode(city, "utf-8")+"&state="+URLEncoder.encode(state, "utf-8");
        String result = new Utilities().execute(url).get();
        System.out.println(url);
        Map<String,String> response = stringToMap(result);
        return response;
    }

    public Map<String,String> autoComplete(String query) throws ExecutionException, InterruptedException, UnsupportedEncodingException, IOException {
        String url = baseUrl + "auto_complete?input="+ URLEncoder.encode(query, "utf-8");
        String result = new Utilities().execute(url).get();
        Map<String,String> response = stringToMap(result);
        return response;
    }

    public Map<String,String> weatherSearchUsingCity(String query) throws ExecutionException, InterruptedException, UnsupportedEncodingException, IOException {
        String url = baseUrl + "weather_search?city="+ URLEncoder.encode(query, "utf-8")+"&street="+"&state=";
        String result = new Utilities().execute(url).get();
        System.out.println(url);
        System.out.println(result);
        Map<String,String> response = stringToMap(result);
        return response;
    }

    public Map fetchWeatherDetail(String lat, String lng, String time) throws ExecutionException, InterruptedException, UnsupportedEncodingException{
        String url = baseUrl + "weather_detail?lat="+ URLEncoder.encode(lat, "utf-8")+"&lng="+ URLEncoder.encode(lng, "utf-8")+"&time="+URLEncoder.encode(time, "utf-8");
        String result = new Utilities().execute(url).get();
        Map<String, String> response = stringToMap(result);
        return response;
    }
}