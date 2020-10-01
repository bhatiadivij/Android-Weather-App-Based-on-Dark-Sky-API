package csci571.hw9;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;


import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public PagerAdapter myPagerAdapter;
    public ViewPager pager;
    public TabLayout tab;
    public FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        initSharedPref();
        ArrayList<ArrayList<String>> favList = getSharedPref();
        ArrayList<String> currentLocation = getCurrentLocation();

        if (favList!=null){
            if (favList.size()>0){
                favList.add(0, currentLocation);
            }
            else {
                favList.add(currentLocation);
            }
        }
        else {
            favList = new ArrayList<ArrayList<String>>();
            favList.add(currentLocation);
        }
        pager = (ViewPager)findViewById(R.id.favs_viewpager);
        tab = findViewById(R.id.tab_layout);
        System.out.println("Shared Pref"+ favList);
        int tabNum = favList.size();
        for (int i = 0; i <tabNum; i++) {
            tab.addTab(tab.newTab());
        }
        myPagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabNum, favList);
        pager.setAdapter(myPagerAdapter);
        tab.setupWithViewPager(pager);
        fab = findViewById(R.id.fab);
        if (pager.getCurrentItem() == 0){
            fab.hide();
        }
        System.out.println("Current Item: "+ pager.getCurrentItem());
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                FloatingActionButton floatingActionButton = findViewById(R.id.fab);
                if(position>0){
                    floatingActionButton.show();

                }
                else {
                    floatingActionButton.hide();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = pager.getCurrentItem();
                ArrayList<ArrayList<String>> favs = getSharedPref();
                System.out.println("Delete Index" + index);
                String message = favs.get(index-1).get(2) + " was removed from favourites";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                removeSharedPref(favs.get(index-1));
                refreshPageAdapter();
                FloatingActionButton floatingActionButton = findViewById(R.id.fab);
                floatingActionButton.hide();
            }
        });

    }

    public void refreshPageAdapter(){
        ArrayList<ArrayList<String>> favList = getSharedPref();
        ArrayList<String> currentLocation = getCurrentLocation();
        if (favList!=null){
            if (favList.size()>0){
                favList.add(0, currentLocation);
            }
            else {
                favList.add(currentLocation);
            }
        }
        else {
            favList = new ArrayList<ArrayList<String>>();
            favList.add(currentLocation);
        }

        System.out.println("Shared Pref"+ favList);
        int tabNum = favList.size();
        for (int i = 0; i <tabNum; i++) {
            tab.addTab(tab.newTab());
        }
        myPagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabNum, favList);
        pager.setAdapter(myPagerAdapter);
        tab.setupWithViewPager(pager);
    }
    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Resumed");
        refreshPageAdapter();
    }


    public ArrayList<String> getCurrentLocation(){
        HandlerFunctions handlerFunctions = new HandlerFunctions();
        try{
            Map<String, String> locationMap = handlerFunctions.fetchLocation();
            String resp[] = {String.valueOf(locationMap.get("lat")), String.valueOf(locationMap.get("lon")), locationMap.get("city"), locationMap.get("regionName"), locationMap.get("country"), locationMap.get("countryCode"), locationMap.get("timezone"), locationMap.get("region")};
            return new ArrayList<String>(Arrays.asList(resp));
        }
        catch (Exception ex){
            ex.printStackTrace();
            String resp[] = {};
            return new ArrayList<String>(Arrays.asList(resp));
        }
    }
    public void initSharedPref(){
        SharedPreferences sharedpreferences = getSharedPreferences("hw9Favs",
                Context.MODE_PRIVATE);
        if (!sharedpreferences.contains("favList")) {
            sharedpreferences.getString("favList", new Gson().toJson(new ArrayList<ArrayList<String>>()));
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

    public boolean getLocationPermission(){
        System.out.println("Inside get Permission");
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("Provide Access to Location")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                    }
                }
                return;
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchMenuButton = menu.findItem(R.id.map_search_icon);
        final SearchView searchView = (SearchView) searchMenuButton.getActionView();
        searchView.setIconified(false);
        searchView.setBackgroundColor(getResources().getColor(R.color.grey_components));
        searchView.setQueryHint("Search...");
        searchView.requestFocus();
        searchView.onActionViewExpanded();
        searchView.setImeOptions(2);
        final int textViewID = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        final SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete)searchView.findViewById(R.id.search_src_text);
        searchAutoComplete.setDropDownBackgroundResource(android.R.color.background_light);

        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int itemIndex, long id) {
                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchView.setQuery(queryString, false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextChange(String newText){
                HandlerFunctions handlerFunctions = new HandlerFunctions();
                try{
                        ArrayList<Map<String, String>> locations = (ArrayList<Map<String, String>>) (Object) handlerFunctions.autoComplete(newText).get("predictions");
                        ArrayList<String> result = new ArrayList<>();
                        for(Map<String, String> location: locations){
                            String place = String.valueOf(location.get("description"));
                            result.add(place);
                        }
                    ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_dropdown_item_1line, result);
                    searchAutoComplete.setAdapter(autoCompleteAdapter);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query){
                Intent myIntent = new Intent(MainActivity.this, SearchActivity.class);
                myIntent.putExtra("location", query);
                MainActivity.this.startActivity(myIntent);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.map_search_icon) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
