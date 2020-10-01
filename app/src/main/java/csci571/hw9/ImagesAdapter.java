package csci571.hw9;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImagesAdapter extends RecyclerView.Adapter {
    private ArrayList<String> cityImages;

    public ImagesAdapter(ArrayList<String> cityImages) {
        this.cityImages = cityImages;
    }

    @NonNull
    @Override
    public ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.image_result_card, parent, false);
        return new ImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ImagesViewHolder imagesViewHolder = (ImagesViewHolder) holder;
        Picasso
                .get()
                .load(cityImages.get(position))
                .fit()
                .centerCrop()
                .into(imagesViewHolder.image);
    }

    @Override
    public int getItemCount() {
        if (cityImages != null) {
            return cityImages.size();
        } else {
            return 0;
        }
    }

    public static class ImagesViewHolder extends RecyclerView.ViewHolder {
        public final ImageView image;
        public View view;
        public ImagesViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            image = itemView.findViewById(R.id.city_image);
        }
    }
}
