package csci571.hw9.ui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csci571.hw9.ImagesAdapter;
import csci571.hw9.R;

public class PlaceholderFragment extends Fragment {

    private int tabnum;
    private  Map<String, String> data;
    private ArrayList<String> fav;

    public PlaceholderFragment(int index, Map<String, String> data, ArrayList<String> fav){
        tabnum = index;
        this.data = data;
        this.fav = fav;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view;
        System.out.println(tabnum);
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

        if (tabnum == 0){
            view = inflater.inflate(R.layout.fragment_detail, container, false);
            Map<String, String> currently = (Map<String, String>)(Object) data.get("currently");
            String windSpeed = String.format("%.02f", Double.valueOf(String.valueOf(currently.get("windSpeed")))) + " mph";
            String pressure = String.format("%.02f", Double.valueOf(String.valueOf(currently.get("pressure")))) + " mb";
            String precipitation = String.format("%.02f", Double.valueOf(String.valueOf(currently.get("precipIntensity")))) + " mmph";
            String temperature = String.format("%.02f", Double.valueOf(String.valueOf(currently.get("temperature")))) + "°F";
            String humidity = String.format("%.02f", Double.valueOf(String.valueOf(currently.get("humidity")))*100 )+ " %";
            String visibility = String.format("%.02f", Double.valueOf(String.valueOf(currently.get("visibility")))) + " km";
            String cloudCover = String.format("%.02f", Double.valueOf(String.valueOf(currently.get("cloudCover")))*100 )+ " %";
            String ozone = String.format("%.02f", Double.valueOf(String.valueOf(currently.get("ozone")))) + " DU";
            String summary = String.valueOf(currently.get("icon"));
            summary = summary.replaceAll("-"," ");
            TextView windSpeedTV = view.findViewById(R.id.detail_card_windspeed);
            windSpeedTV.setText(windSpeed);

            TextView pressureTV = view.findViewById(R.id.detail_card_pressure);
            pressureTV.setText(pressure);

            TextView precipitationTV = view.findViewById(R.id.detail_card_precipitation);
            precipitationTV.setText(precipitation);

            TextView temperatureTV = view.findViewById(R.id.detail_card_temperature);
            temperatureTV.setText(temperature);

            TextView humidityTV = view.findViewById(R.id.detail_card_humidity);
            humidityTV.setText(humidity);

            TextView visibilityTV = view.findViewById(R.id.detail_card_visibility);
            visibilityTV.setText(visibility);

            TextView cloudCoverTV = view.findViewById(R.id.detail_card_cloud_cover);
            cloudCoverTV.setText(cloudCover);

            TextView ozoneTV = view.findViewById(R.id.detail_card_ozone);
            ozoneTV.setText(ozone);

            TextView summaryTV = view.findViewById(R.id.detail_card_summary);
            summaryTV.setText(summary);

            ImageView imageView = view.findViewById(R.id.summary_icon);
            String label = currently.get("icon").replaceAll("-", " ").toLowerCase();

            if(map.containsKey(label)){
                imageView.setImageResource(map.get(label));
                if(label.equals("clear day")){
                    imageView.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
                }
                else {
                    imageView.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
                }
            }
            else{
                imageView.setImageResource(map.get("clear day"));
                imageView.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
            }
        }
        else if(tabnum == 1){
            view = inflater.inflate(R.layout.graph_detail, container, false);
            LineChart weekly_chart = (LineChart) view.findViewById(R.id.weekly_chart);
            List<Entry> entries1 = new ArrayList<Entry>();
            List<Entry> entries2 = new ArrayList<Entry>();
            ArrayList<Map<String, String>> daily_data = (ArrayList<Map<String, String>>) (Object) ((Map<String, String>)(Object) data.get("daily")).get("data");
            String summary = (((Map<String, String>)(Object) data.get("daily"))).get("summary");
            String icon = (((Map<String, String>)(Object) data.get("daily"))).get("icon");
            TextView summaryCard = view.findViewById(R.id.weekly_summary);
            summaryCard.setText(summary);

            ImageView imageView = view.findViewById(R.id.weekly_detail_card2_icon);
            String label = icon.replaceAll("-", " ").toLowerCase();

            if(map.containsKey(label)){
                imageView.setImageResource(map.get(label));
                if(label.equals("clear day")){
                    imageView.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
                }
                else {
                    imageView.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
                }
            }
            else{
                imageView.setImageResource(map.get("clear day"));
                imageView.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
            }

            int [][] minTemp = new int[8][2];
            int [][] maxTemp = new int[8][2];
            int i = 0;
            for(Map<String, String> d: daily_data){
                minTemp[i][0] = i;
                minTemp[i][1] = (int) Math.round(new Double(String.valueOf(d.get("temperatureLow"))));
                maxTemp[i][0] = i;
                maxTemp[i][1] = (int) Math.round(new Double(String.valueOf(d.get("temperatureHigh"))));
                i++;
            }

            for (int[] data : minTemp) {
                entries1.add(new Entry(data[0], data[1]));
            }
            for (int[] data : maxTemp) {
                entries2.add(new Entry(data[0], data[1]));
            }

            LineDataSet setComp1 = new LineDataSet(entries1, "Minimum Temperature");
            setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp1.setColors(new int[] { R.color.colorAccent}, getContext());

            LineDataSet setComp2 = new LineDataSet(entries2, "Maximum Temperature");
            setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp2.setColors(new int[] { R.color.yellow}, getContext());

            List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(setComp1);
            dataSets.add(setComp2);

            LineData data = new LineData(dataSets);
            weekly_chart.setDrawGridBackground(false);
            Legend legend = weekly_chart.getLegend();
            legend.setTextColor(Color.rgb(255,255,255));
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            legend.setXEntrySpace(10f);
            legend.setTextSize(15f);
            weekly_chart.getAxisLeft().setTextColor(Color.rgb(255,255,255));
            weekly_chart.getAxisRight().setTextColor(Color.rgb(255,255,255));
            weekly_chart.getXAxis().setTextColor(Color.rgb(255,255,255));
            weekly_chart.getLegend().setTextColor(Color.rgb(255,255,255));
            weekly_chart.setData(data);
            weekly_chart.invalidate();
        }
        else {
            view = inflater.inflate(R.layout.images_fragment, container, false);
            try{
                ArrayList<Map<String, String>> cityImages = (ArrayList<Map<String, String>>)(Object) data.get("city_images");
                ArrayList<String> images = new ArrayList<>();
                for(Map<String, String> data: cityImages){
                    images.add(data.get("link"));
                }
                System.out.println(images);
                RecyclerView imageRecyclerView;
                RecyclerView.Adapter adapter;

                imageRecyclerView = (RecyclerView) view.findViewById(R.id.image_results);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
                imageRecyclerView.setLayoutManager(mLayoutManager);

                adapter = new ImagesAdapter(images);
                imageRecyclerView.setAdapter(adapter);


            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return view;
    }
}