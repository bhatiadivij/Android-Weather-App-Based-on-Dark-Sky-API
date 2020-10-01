package csci571.hw9;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImagesViewHolder extends RecyclerView.ViewHolder {
    public final ImageView image;
    View view;
    public ImagesViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
        image = itemView.findViewById(R.id.city_image);

    }
}
