package csci571.hw9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    Map<String, String> data;
    ArrayList<String> fav = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_WithActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        String location = intent.getStringExtra("location");
        System.out.println("location" + location);
        HandlerFunctions handlerFunctions = new HandlerFunctions();
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("clear day", R.drawable.weather_sunny);
        map.put("clear night", R.drawable.weather_night);
        map.put("rain", R.drawable.weather_rainy);
        map.put("sleet", R.drawable.weather_snowy_rainy);
        map.put("snow", R.drawable.weather_snowy);
        map.put("wind", R.drawable.weather_windy_variant);
        map.put("fog", R.drawable.weather_fog);
        map.put("cloudy", R.drawable.weather_cloudy);
        map.put("partly cloudy night", R.drawable.weather_night_partly_cloudy);
        map.put("partly cloudy day", R.drawable.weather_partly_cloudy);
        try {
            TextView searchResultLabel = findViewById(R.id.search_result_label);
            searchResultLabel.setVisibility(View.VISIBLE);

            data = handlerFunctions.weatherSearchUsingCity(location);
            System.out.println(data);
            Map<String, String> currently = (Map<String, String>) (Object) data.get("currently");
            if(data.containsKey("err") && data.get("err").equals("Invalid Address")){
                Toast.makeText(getApplicationContext(), "Invalid Address", Toast.LENGTH_LONG).show();
                onBackPressed();
            }
            fav.add(String.valueOf(data.get("latitude")));
            fav.add(String.valueOf(data.get("longitude")));
            fav.add(String.valueOf(data.get("city")));
            fav.add("");
            fav.add("");
            fav.add("");
            fav.add(String.valueOf(data.get("timezone")));
            fav.add("");
            System.out.println(fav);
            String title = fav.get(2);

            if(!fav.get(7).isEmpty()){
                title+=", "+fav.get(7);
            }
            if(!fav.get(5).isEmpty()){
                title+=", "+fav.get(5);
            }
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            this.getSupportActionBar().setTitle(title);
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);

            TextView home_card1_temp = findViewById(R.id.home_card1_temp);
            int temp = (int) Math.round(new Double(String.valueOf(currently.get("temperature"))));
            home_card1_temp.setText(String.valueOf(temp) + "°F");

            TextView home_card1_summary = findViewById(R.id.home_card1_summary);
            home_card1_summary.setText(currently.get("summary"));

            ImageView home_card1_icon = findViewById(R.id.home_card1_icon);

            String label = currently.get("icon").replaceAll("-", " ").toLowerCase();
            System.out.println(label);
            if(map.containsKey(label)){
                home_card1_icon.setImageResource(map.get(label));
                if(label.equals("clear day")){
                    home_card1_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
                }
                else {
                    home_card1_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
                }
            }
            else{
                home_card1_icon.setImageResource(map.get("clear day"));
                home_card1_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
            }

            TextView home_card1_location = findViewById(R.id.home_card1_location);
            String place = fav.get(2);
            if(!fav.get(3).isEmpty()){
                place+=", "+fav.get(3);
            }
            if(!fav.get(5).isEmpty()){
                place+=", "+fav.get(5);
            }
            home_card1_location.setText(place);

            TextView home_card2_humidity = findViewById(R.id.home_card2_humidity);
            String humidity = String.valueOf((int) Math.round(Double.valueOf(String.valueOf(currently.get("humidity"))) * 100)) + " %";
            home_card2_humidity.setText(humidity);

            TextView home_card2_pressure = findViewById(R.id.home_card2_pressure);
            String pressure = String.format("%.02f", Double.valueOf(String.valueOf(currently.get("pressure")))) + " mb";
            home_card2_pressure.setText(pressure);

            TextView home_card2_windspeed = findViewById(R.id.home_card2_windspeed);
            String windspeed = String.format("%.02f", Double.valueOf(String.valueOf(currently.get("windSpeed")))) + " mph";
            home_card2_windspeed.setText(windspeed);

            TextView home_card2_visibility = findViewById(R.id.home_card2_visibility);
            String visibility = String.format("%.02f", Double.valueOf(String.valueOf(currently.get("visibility")))) + " km";
            home_card2_visibility.setText(visibility);

            ArrayList<Map<String, String>> daily_data = (ArrayList<Map<String, String>>) (Object) ((Map<String, String>) (Object) data.get("daily")).get("data");
            ArrayList<ArrayList<String>> daily_rv_data = new ArrayList<>();

            for (int i = 0; i < daily_data.size(); i++) {
                ArrayList<String> temp_list = new ArrayList<>();

                Long timeStampMillisInLong = Long.valueOf(String.valueOf(daily_data.get(i).get("time"))) * 1000;
                temp_list.add(new SimpleDateFormat("MM/dd/yyyy").format(new Date(timeStampMillisInLong)));

                temp_list.add("");

                int tempLow = (int) Math.round(new Double(String.valueOf(daily_data.get(i).get("temperatureLow"))));
                temp_list.add(String.valueOf(tempLow));

                int tempHigh = (int) Math.round(new Double(String.valueOf(daily_data.get(i).get("temperatureHigh"))));
                temp_list.add(String.valueOf(tempHigh));
                daily_rv_data.add(temp_list);
            }

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.weekly_card_data);
            WeeklyWeatherRV adapter = new WeeklyWeatherRV(daily_rv_data, getApplicationContext());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            CardView summary_card = findViewById(R.id.saved_location_summary);
            summary_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(SearchActivity.this, DetailActivity.class);
                    myIntent.putExtra("fav", (Serializable) fav);
                    myIntent.putExtra("data", (Serializable) data);
                    SearchActivity.this.startActivity(myIntent);
                }
            });

            final FloatingActionButton fab = findViewById(R.id.fab);
            final boolean isInSharedPreferneces = checkIfInSharedPreferences(fav.get(2));
            if(isInSharedPreferneces){
                fab.setImageResource(R.drawable.map_marker_minus);
            }
            else {
                fab.setImageResource(R.drawable.map_marker_plus);
            }
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!isInSharedPreferneces){
                        setSharedPref(fav);
                        String message = fav.get(2) + " was added to favourites";
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        fab.setImageResource(R.drawable.map_marker_minus);
                    }
                    else {
                        String message = fav.get(2) + " was removed from favourites";
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        removeSharedPref(fav);
                        fab.setImageResource(R.drawable.map_marker_plus);
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public ArrayList<ArrayList<String>> getSharedPref(){
        SharedPreferences sharedpreferences = getSharedPreferences("hw9Favs",
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        ArrayList<ArrayList<String>> favList = gson.fromJson(sharedpreferences.getString("favList", ""), ArrayList.class);
        System.out.println(favList);
        return favList;
    }
    public void setSharedPref(ArrayList<String> location){
        SharedPreferences sharedpreferences = getSharedPreferences("hw9Favs",
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        ArrayList<ArrayList<String>> favList = gson.fromJson(sharedpreferences.getString("favList", ""), ArrayList.class);
        if(favList==null){
            favList = new ArrayList<ArrayList<String>>();
        }
        favList.add(location);
        String favListJson = gson.toJson(favList);
        System.out.println(favListJson);
        SharedPreferences.Editor sharedPreferenceEditor = sharedpreferences.edit();
        sharedPreferenceEditor.putString("favList", favListJson);
        sharedPreferenceEditor.apply();
    }
    public boolean checkIfInSharedPreferences(String location){
        ArrayList<ArrayList<String>> favList  = getSharedPref();
        if(favList!=null){
            for(ArrayList<String> fav: favList){
                if(location.contains(fav.get(2)))
                    return true;
            }
        }
        return false;

    }

    public void removeSharedPref(ArrayList<String> location){
        SharedPreferences sharedpreferences = getSharedPreferences("hw9Favs",
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        ArrayList<ArrayList<String>> favList = gson.fromJson(sharedpreferences.getString("favList", ""), ArrayList.class);
        if(favList==null){
            favList = new ArrayList<ArrayList<String>>();
        }
        favList.remove(location);
        String favListJson = gson.toJson(favList);
        System.out.println(favListJson);
        SharedPreferences.Editor sharedPreferenceEditor = sharedpreferences.edit();
        sharedPreferenceEditor.putString("favList", favListJson);
        sharedPreferenceEditor.apply();
    }
}
