package csci571.hw9;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WeeklyWeatherRV extends RecyclerView.Adapter<WeeklyWeatherVH> {

    ArrayList<ArrayList<String>> data;
    Context context;

    public WeeklyWeatherRV(ArrayList<ArrayList<String>> list, Context context) {
        this.data = list;
        this.context = context;
    }

    @Override
    public WeeklyWeatherVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.weekly_list_item, parent, false);
        WeeklyWeatherVH holder = new WeeklyWeatherVH(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(WeeklyWeatherVH holder, int position) {
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
        holder.date.setText(data.get(position).get(0));

        String icon = data.get(position).get(1);
        String label = icon.replaceAll("-", " ").toLowerCase();
        System.out.println(label);
        if(map.containsKey(label)){
            holder.icon.setImageResource(map.get(label));
            if(label.equals("clear day")){
                holder.icon.setColorFilter(ContextCompat.getColor(context, R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
            }
            else {
                holder.icon.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
            }
        }
        else{
            holder.icon.setImageResource(map.get("clear day"));
            holder.icon.setColorFilter(ContextCompat.getColor(context, R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
        }

        holder.min_temp.setText(data.get(position).get(2));
        holder.max_temp.setText(data.get(position).get(3));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}