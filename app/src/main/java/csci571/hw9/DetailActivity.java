package csci571.hw9;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

import csci571.hw9.ui.main.SectionsPagerAdapter;

public class DetailActivity extends AppCompatActivity {
    Map<String, String> data;
    ArrayList<String> fav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_WithActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        data = (Map<String, String>) intent.getSerializableExtra("data");
        fav = (ArrayList<String>) intent.getSerializableExtra("fav");
        String title = fav.get(2);

        if(!fav.get(7).isEmpty()){
            title+=", "+fav.get(7);
        }
        if(!fav.get(5).isEmpty()){
            title+=", "+fav.get(5);
        }
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle(title);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), data, fav);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        TextView tabOne = new TextView(this);
        tabOne.setGravity(Gravity.CENTER);
        tabOne.setTextSize(15f);
        tabOne.setTextColor(getResources().getColor(R.color.white));
        tabOne.setText("TODAY");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.calendar_today, 0, 0);
        tabs.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = new TextView(this);
        tabTwo.setGravity(Gravity.CENTER);
        tabTwo.setTextSize(15f);
        tabTwo.setTextColor(getResources().getColor(R.color.white));
        tabTwo.setText("WEEKLY");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.trending_up, 0, 0);
        tabs.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = new TextView(this);
        tabThree.setGravity(Gravity.CENTER);
        tabThree.setTextSize(15f);
        tabThree.setTextColor(getResources().getColor(R.color.white));
        tabThree.setText("PHOTOS");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.google_photos, 0, 0);
        tabs.getTabAt(2).setCustomView(tabThree);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.twitter_icon:
                Map<String, String> currently = (Map<String, String>)(Object) data.get("currently");
                int temp = (int) Math.round(new Double(String.valueOf(currently.get("temperature"))));
                String url = "";
                String place =  fav.get(2);
                if(!fav.get(7).isEmpty()){
                    place+=", "+fav.get(7);
                }
                if(!fav.get(5).isEmpty()){
                    place+=", "+fav.get(5);
                }

                try {
                    url = "https://twitter.com/intent/tweet?text=" + URLEncoder.encode("Check Out " + place + "'s Weather! It is " + temp + "°F! #CSCI571WeatherSearch", "UTF-8");
                }catch (Exception ex){ ex.printStackTrace();}
                System.out.println(Uri.parse(url));
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}