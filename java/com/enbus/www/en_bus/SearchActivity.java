package com.enbus.www.en_bus;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity  extends AppCompatActivity {

    private ImageView btnNews;
    private ImageView btnMark;
    private ImageView btnStar;

    ListView  listView;
    List<Routes> allRoutes;
    ArrayList<String> routesName;
    MySqliHandler databaseHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        btnNews  = findViewById(R.id.btnSNews);
        btnMark  = findViewById(R.id.btnSMark);
        btnStar  = findViewById(R.id.btnSStar);
        listView = findViewById(R.id.searchListView);

        databaseHandler = new MySqliHandler(this);
        allRoutes = databaseHandler.getAllRoutes();
        routesName = new ArrayList<>();

        if (allRoutes.isEmpty()){

            Routes r622 = new Routes("Ruta 622");
            allRoutes.add(r622);
            databaseHandler.addRoute(r622);
            Routes r623 = new Routes("Ruta 623");
            allRoutes.add(r623);
            databaseHandler.addRoute(r623);
            Routes r623A = new Routes("Ruta 623-A");
            allRoutes.add(r623A);
            databaseHandler.addRoute(r623A);
            Routes r624 = new Routes("Ruta 624");
            allRoutes.add(r624);
            databaseHandler.addRoute(r624);
            Routes r625 = new Routes("Ruta 625");
            allRoutes.add(r625);
            databaseHandler.addRoute(r625);
            Routes r629 = new Routes("Ruta 629");
            allRoutes.add(r629);
            databaseHandler.addRoute(r629);
            Routes r646 = new Routes("Ruta 646");
            allRoutes.add(r646);
            databaseHandler.addRoute(r646);
            Routes rmacro = new Routes("MACROBUS PARADOR");
            allRoutes.add(rmacro);
            databaseHandler.addRoute(rmacro);
            Routes rmacroe = new Routes("MACROBUS EXPRESS");
            allRoutes.add(rmacroe);
            databaseHandler.addRoute(rmacroe);
            Routes TL1 = new Routes("SITEUR LINEA 1");
            allRoutes.add(TL1);
            databaseHandler.addRoute(TL1);
            Routes TL2 = new Routes("SITEUR LINEA 2");
            allRoutes.add(TL2);
            databaseHandler.addRoute(TL2);
            Routes STL1 = new Routes("SITREN LINEA 1");
            allRoutes.add(STL1);
            databaseHandler.addRoute(STL1);
            Routes STL2 = new Routes("SITREN LINEA 2");
            allRoutes.add(STL2);
            databaseHandler.addRoute(STL2);
            Routes STL3 = new Routes("SITREN LINEA 3");
            allRoutes.add(STL3);
            databaseHandler.addRoute(STL3);
        }

        if(allRoutes.size()>0) {
            for (int i=0; i<allRoutes.size();i++){
                Routes routes = allRoutes.get(i);
                routesName.add(routes.getName());
            }
        }

        rutasAdapter adapter = new rutasAdapter(SearchActivity.this, R.layout.list_item, routesName);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this, RouteComments.class);
                intent.putExtra("RouteName",routesName.get(position));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_down);
            }
        });

        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, NewsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });//end onClick btnNews

        btnMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, MarkActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });//end onClick btnMark

        btnStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, StarActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });//end onClick btnNews



    }

    //Override onCreateOptionsMenu


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_chipocludo,menu);

        MenuItem menuItem=menu.findItem(R.id.searchItem);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<String> results = new ArrayList<>();

                for (String x: routesName){
                    if (x.contains(newText)){
                        results.add(x);
                    }
                }

                ((rutasAdapter)listView.getAdapter()).updateList(results);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    //Override onCreateContextMenu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        menu.setHeaderTitle(routesName.get(info.position));
        getMenuInflater().inflate(R.menu.context_menu,menu);
    }

    //Override onContextItemSelected

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        switch(item.getItemId()){
            case R.id.addcomment:
                Intent intent = new Intent(SearchActivity.this, RouteComments.class);
                intent.putExtra("RouteName",routesName.get(info.position));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_down);
                return true;

            case R.id.addfavorite:
                if (allRoutes.get(info.position).getStatus().equals("yes")){
                    Toast.makeText(SearchActivity.this, routesName.get(info.position)+" Ya se encuentra en tus favoritos", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SearchActivity.this, "Agregaste "+routesName.get(info.position)+" a tus favoritos", Toast.LENGTH_SHORT).show();
                    databaseHandler.setFavorite(routesName.get(info.position));
                }
                return true;

            default:
                return super.onContextItemSelected(item);

        }
    }
}

