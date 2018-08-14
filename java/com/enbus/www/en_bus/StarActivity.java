package com.enbus.www.en_bus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class StarActivity extends AppCompatActivity {

    private ImageView btnNews;
    private ImageView btnMark;
    private ImageView btnSearch;

    ListView favoriteListView;
    List<Routes> allRoutes;
    ArrayList<String> routesName;
    MySqliHandler databaseHandler;
    rutasAdapter adapter;
    TextView EmptyMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);

        btnNews   = findViewById(R.id.btnSTNews);
        btnMark   = findViewById(R.id.btnSTMark);
        btnSearch = findViewById(R.id.btnSTSearch);
        favoriteListView = findViewById(R.id.favoriteListView);
        EmptyMessage = findViewById(R.id.EmptyMessage);

        databaseHandler = new MySqliHandler(this);
        allRoutes = databaseHandler.getAllRoutes();
        routesName = new ArrayList<>();

        if(allRoutes.size()>0) {
            for (int i=0; i<allRoutes.size();i++){
                Routes routes = allRoutes.get(i);
                if (routes.getStatus().equals("yes")){
                    routesName.add(routes.getName());
                }
            }
        }

        if (routesName.isEmpty()){
            EmptyMessage.setVisibility(View.VISIBLE);
        }else{
            EmptyMessage.setVisibility(View.INVISIBLE);
        }

        rutasAdapter adapter = new rutasAdapter(StarActivity.this, R.layout.list_item, routesName);
        favoriteListView.setAdapter(adapter);

        registerForContextMenu(favoriteListView);

        favoriteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StarActivity.this, RouteComments.class);
                intent.putExtra("RouteName",routesName.get(position));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_down);
            }
        });


        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StarActivity.this, NewsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });//end onClick btnNews

        btnMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StarActivity.this, MarkActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });//end onClick btnMark

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StarActivity.this, SearchActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });//end onClick btnSearch
    }

    //Context Menu for options code:

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)menuInfo;
        menu.setHeaderTitle(routesName.get(info.position));
        getMenuInflater().inflate(R.menu.favorites_context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        switch(item.getItemId()){
            case R.id.Delete:
                Toast.makeText(StarActivity.this, "Removiste "+routesName.get(info.position)+" de tus favoritos", Toast.LENGTH_SHORT).show();
                databaseHandler.removeFavorite(routesName.get(info.position));
                this.routesName.remove(info.position);
                ((rutasAdapter)favoriteListView.getAdapter()).notifyDataSetChanged();
                if (routesName.isEmpty()){
                    EmptyMessage.setVisibility(View.VISIBLE);
                }else{
                    EmptyMessage.setVisibility(View.INVISIBLE);
                }
                return true;

            case R.id.Comment:
                Intent intent = new Intent(StarActivity.this, RouteComments.class);
                intent.putExtra("RouteName",routesName.get(info.position));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
}
