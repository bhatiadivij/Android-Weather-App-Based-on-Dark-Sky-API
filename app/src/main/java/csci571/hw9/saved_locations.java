package csci571.hw9;

import android.content.Intent;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.lang.Math;

public class saved_locations extends Fragment {
    public ArrayList<String> fav;
    public Map<String,String> data;
    public saved_locations() {
    }

    public saved_locations(ArrayList<String> fav) {
        this.fav = fav;
        String lat=fav.get(0), lng=fav.get(1), city=fav.get(2), state=fav.get(3);
        HandlerFunctions handlerFunctions = new HandlerFunctions();
        try{
            data = handlerFunctions.fetchWeatherReport(lat, lng, city, state);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        System.out.println(data);
        Map<String, String> currently = (Map<String, String>)(Object) data.get("currently");
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
        System.out.println();
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        View view = inflater.inflate(R.layout.fragment_saved_locations, container, false);
        TextView home_card1_temp = view.findViewById(R.id.home_card1_temp);
        int temp = (int) Math.round(new Double(String.valueOf(currently.get("temperature"))));
        home_card1_temp.setText(String.valueOf(temp) + "°F");

        TextView home_card1_summary = view.findViewById(R.id.home_card1_summary);
        home_card1_summary.setText(currently.get("summary"));
        ImageView home_card1_icon = view.findViewById(R.id.home_card1_icon);

        String label = currently.get("icon").replaceAll("-", " ").toLowerCase();
        if(map.containsKey(label)){
            home_card1_icon.setImageResource(map.get(label));
            if(label.equals("clear day")){
                home_card1_icon.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
            }
            else {
                home_card1_icon.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
            }
        }
        else{
            home_card1_icon.setImageResource(map.get("clear day"));
            home_card1_icon.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
        }

        TextView home_card1_location = view.findViewById(R.id.home_card1_location);
        String location = fav.get(2);
        if(!fav.get(7).isEmpty()){
            location+=", "+fav.get(7);
        }
        if(!fav.get(5).isEmpty()){
            location+=", "+fav.get(5);
        }
        home_card1_location.setText(location);

        TextView home_card2_humidity = view.findViewById(R.id.home_card2_humidity);
        String humidity = String.valueOf((int)Math.round(Double.valueOf(String.valueOf(currently.get("humidity")))*100)) + " %";
        home_card2_humidity.setText(humidity);

        TextView home_card2_pressure = view.findViewById(R.id.home_card2_pressure);
        String pressure = String.format("%.02f", Double.valueOf(String.valueOf(currently.get("pressure")))) + " mb";
        home_card2_pressure.setText(pressure);

        TextView home_card2_windspeed = view.findViewById(R.id.home_card2_windspeed);
        String windspeed = String.format("%.02f", Double.valueOf(String.valueOf(currently.get("windSpeed")))) + " mph";
        home_card2_windspeed.setText(windspeed);

        TextView home_card2_visibility = view.findViewById(R.id.home_card2_visibility);
        String visibility = String.format("%.02f", Double.valueOf(String.valueOf(currently.get("visibility")))) + " km";
        home_card2_visibility.setText(visibility);

        ArrayList<Map<String, String>> daily_data = (ArrayList<Map<String, String>>)(Object)((Map<String, String>)(Object) data.get("daily")).get("data");
        ArrayList<ArrayList<String>> daily_rv_data = new ArrayList<>();

        for(int i =0 ; i<daily_data.size(); i++){
            ArrayList<String> temp_list = new ArrayList<>();

            Long timeStampMillisInLong = Long.valueOf(String.valueOf(daily_data.get(i).get("time")))*1000;
            temp_list.add(new SimpleDateFormat("MM/dd/yyyy").format(new Date(timeStampMillisInLong)));

            String icon = String.valueOf(daily_data.get(i).get("icon"));
            temp_list.add(icon);

            int tempLow = (int) Math.round(new Double(String.valueOf(daily_data.get(i).get("temperatureLow"))));
            temp_list.add(String.valueOf(tempLow));

            int tempHigh = (int) Math.round(new Double(String.valueOf(daily_data.get(i).get("temperatureHigh"))));
            temp_list.add(String.valueOf(tempHigh));
            daily_rv_data.add(temp_list);
        }

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.weekly_card_data);
        WeeklyWeatherRV adapter = new WeeklyWeatherRV(daily_rv_data, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        CardView summary_card = view.findViewById(R.id.saved_location_summary);
        summary_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), DetailActivity.class);
                myIntent.putExtra("fav", (Serializable) fav);
                myIntent.putExtra("data", (Serializable) data);
                getActivity().startActivity(myIntent);
            }
        });
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
