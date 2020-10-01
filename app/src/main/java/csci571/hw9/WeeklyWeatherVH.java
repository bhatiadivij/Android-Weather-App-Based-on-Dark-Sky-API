package csci571.hw9;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class WeeklyWeatherVH extends RecyclerView.ViewHolder {

    TextView date, min_temp, max_temp;
    ImageView icon;

    WeeklyWeatherVH(View itemView) {
        super(itemView);
        date = (TextView) itemView.findViewById(R.id.weekly_card_date);
        min_temp = (TextView) itemView.findViewById(R.id.weekly_card_min_temp);
        max_temp = (TextView) itemView.findViewById(R.id.weekly_card_max_temp);
        icon = (ImageView) itemView.findViewById(R.id.weekly_card_icon);
    }
}