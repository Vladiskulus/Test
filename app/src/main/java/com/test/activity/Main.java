package com.test.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.test.R;
import com.test.adapters.GridAdapter;
import com.test.adapters.ListAdapter;
import com.test.model.MovieModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main extends AppCompatActivity implements View.OnClickListener{

    private static String JSON_URL = "https://api.themoviedb.org/3/movie/";
    private static String API = "?api_key=d58a97366ae95fdab0a70cdc8ed8db5f";
    private static String POPULAR = JSON_URL+"popular"+API;
    Context c;
    List<MovieModelClass> movieList;
    RecyclerView rv;
    ListAdapter adapterList;
    GridAdapter adapterGrid;
    URL url;
    Intent i;
    Button grid,list;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        c=getApplicationContext();
        toolbar = findViewById(R.id.toolbar);

        movieList = new ArrayList<>();
        rv = findViewById(R.id.rv);
        GetData getData = new GetData();
        getData.execute();
        grid=findViewById(R.id.btn_grid);
        list=findViewById(R.id.btn_list);
        grid.setOnClickListener(this);
        list.setOnClickListener(this);

        setSupportActionBar(toolbar);
        toolbar.setSubtitle("");
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.account:
                        i = new Intent(Main.this, Account.class);
                        startActivity(i);
                        break;
                    case R.id.exit:
                        i = new Intent(Main.this, Sign_In.class);
                        startActivity(i);
                        break;
                    default:break;
                }
                return false;
            }
        });
        getDrawer(this, toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_grid:
                rv.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
                rv.setAdapter(adapterGrid);
                break;
            case R.id.btn_list:
                rv.setLayoutManager(new LinearLayoutManager(this));
                rv.setAdapter(adapterList);
                break;
            default:break;
        }
    }

    public class GetData extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try {
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(POPULAR);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream inputStream = urlConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    int data = inputStreamReader.read();
                    while (data != -1){
                        current+=(char)data;
                        data = inputStreamReader.read();
                    }
                    return current;
                }catch (MalformedURLException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }finally {
                    if(urlConnection!=null){
                        urlConnection.disconnect();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return current;
        }
        @Override
        protected void onPostExecute(String s){
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for(int i = 0; i<jsonArray.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    MovieModelClass model = new MovieModelClass();
                    model.setName(jsonObject1.getString("title"));
                    model.setRelease(jsonObject1.getString("release_date"));
                    model.setImage(jsonObject1.getString("poster_path"));
                    model.setStars(jsonObject1.getString("vote_average"));
                    model.setInfo(jsonObject1.getString("overview"));
                    model.setLang(jsonObject1.getString("original_language"));
                    model.setPop(jsonObject1.getString("popularity"));
                    movieList.add(model);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
            PutDataIntoRecyclerView(movieList);
        }
    }
    private void PutDataIntoRecyclerView(List<MovieModelClass> movieList){
        adapterList = new ListAdapter(this, movieList);
        adapterGrid = new GridAdapter(this, movieList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapterList);
    }

    public void getDrawer(final Activity activity, Toolbar toolbar) {
        PrimaryDrawerItem drawerHeader = new PrimaryDrawerItem().withIdentifier(1)
                .withName("Профіль").withIcon(R.drawable.account).withSelectable(true);
        SecondaryDrawerItem drawerReg = new SecondaryDrawerItem().withIdentifier(3)
                .withName("Вийти").withIcon(R.drawable.exit).withSelectable(true);
        SecondaryDrawerItem drawerString = new SecondaryDrawerItem().withIdentifier(4)
                .withName("Список").withIcon(R.drawable.list).withSelectable(true);
        SecondaryDrawerItem drawerTab = new SecondaryDrawerItem().withIdentifier(5)
                .withName("Таблиця").withIcon(R.drawable.grid).withSelectable(true);
        final IProfile profile = new ProfileDrawerItem().withIcon(R.drawable.account);
        AccountHeader header = new AccountHeaderBuilder()
                    .withActivity(activity)
                    .withTranslucentStatusBar(true)
                    .withHeaderBackground(R.drawable.gradient)
                    .withOnAccountHeaderProfileImageListener(new AccountHeader.OnAccountHeaderProfileImageListener() {
                        @Override
                        public boolean onProfileImageClick(View view, IProfile profile, boolean current) {
                            return false;
                        }
                        @Override
                        public boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {
                            return false;
                        }
                    })
                    .addProfiles(profile)
                    .build();
            Drawer result = new DrawerBuilder()
                    .withActivity(activity)
                    .withToolbar(toolbar)
                    .withActionBarDrawerToggle(true)
                    .withActionBarDrawerToggleAnimated(true)
                    .withCloseOnClick(true)
                    .withSelectedItem(-1)
                    .withAccountHeader(header)
                    .addDrawerItems(
                            drawerHeader,
                            new DividerDrawerItem(),
                            drawerReg,
                                new DividerDrawerItem(),
                                drawerString,
                                drawerTab
                    )
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                            if (drawerItem.getIdentifier() == 1) {
                                i = new Intent(activity, Account.class);
                                activity.startActivity(i);
                            }else if (drawerItem.getIdentifier() == 3) {
                                i = new Intent(activity, Sign_Up.class);
                                activity.startActivity(i);
                            }else if(drawerItem.getIdentifier() == 4){
                                rv.setLayoutManager(new LinearLayoutManager(activity));
                                rv.setAdapter(adapterList);
                        }else if(drawerItem.getIdentifier() == 5){
                                rv.setLayoutManager(new GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false));
                                rv.setAdapter(adapterGrid);
                        }
                            return true;
                        }
                    })
                    .withShowDrawerOnFirstLaunch(false)
                    .withShowDrawerUntilDraggedOpened(false)
                    .withSliderBackgroundColorRes(R.color.colorPrimary)
                    .build();
        }

}