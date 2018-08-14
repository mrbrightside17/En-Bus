package com.enbus.www.en_bus;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RouteComments extends AppCompatActivity {

    ListView listView;
    List<Comments> allComments;
    ArrayList<String> commentsText;
    ArrayList<String> userText;
    ArrayList<String> ratesText;
    MySqliHandler databaseHandler;
    String routeName;

    Button btnComment;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_comments);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            routeName = bundle.getString("RouteName");
            setTitle("En-Bus | "+routeName);
        }

        listView = findViewById(R.id.listView);
        btnComment = (Button)findViewById(R.id.btnComment);
        editText = findViewById(R.id.editText2);

        databaseHandler = new MySqliHandler(this);

        allComments = databaseHandler.getSumComments(routeName);
        commentsText = new ArrayList<>();
        userText = new ArrayList<>();
        ratesText = new ArrayList<>();


        if (allComments.isEmpty()) {
            fillUp();
        }

        if (allComments.size()>0){
            for (int i=0; i<allComments.size();i++){
                Comments comments = allComments.get(i);
                if(comments.getRoute().equals(routeName)) {
                    commentsText.add(comments.getComment());
                    userText.add(comments.getUser());
                    ratesText.add(comments.getGrade());
                }
            }
        }

        final commentsAdapter adapter = new commentsAdapter(RouteComments.this, R.layout.comments_list_item,userText,commentsText,ratesText);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().isEmpty()){
                    Toast.makeText(RouteComments.this, "Acción Inválida", Toast.LENGTH_SHORT).show();
                }else{
                    Comments comment = new Comments(routeName, editText.getText().toString(), "user123");
                    databaseHandler.addComment(comment);
                    commentsText.add(comment.getComment());
                    userText.add(comment.getUser());
                    ratesText.add(comment.getGrade());
                    adapter.notifyDataSetChanged();
                    Toast.makeText(RouteComments.this, "Comentario agregado exitosamente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        menu.setHeaderTitle(routeName);
        getMenuInflater().inflate(R.menu.comment_options_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        switch(item.getItemId()) {
            case R.id.Delete:
                Toast.makeText(this, "Comentario eliminado.",Toast.LENGTH_SHORT).show();
                databaseHandler.deleteComment(commentsText.get(info.position).toString());
                this.commentsText.remove(info.position);
                this.userText.remove(info.position);
                ((commentsAdapter)listView.getAdapter()).notifyDataSetChanged();
                return true;

            case R.id.Grade:
                final NumberPicker numpicker = new NumberPicker(this);
                numpicker.setMaxValue(5);
                numpicker.setMinValue(0);
                numpicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(numpicker);
                builder.setTitle(routeName);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseHandler.updateGrade(commentsText.get(info.position),Integer.toString(numpicker.getValue()));
                        ratesText.set(info.position, Integer.toString(numpicker.getValue()));
                        ((commentsAdapter)listView.getAdapter()).notifyDataSetChanged();
                    }
                });
                builder.show();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }



    public void fillUp(){

        //622
        Comments r622_1 = new Comments("Ruta 622","Desvio por Av Terranova","User123");
        allComments.add(r622_1);
        databaseHandler.addComment(r622_1);
        Comments r622_2 = new Comments("Ruta 622","Accidente en Lopez Mateos altura minerva","User123");
        allComments.add(r622_2);
        databaseHandler.addComment(r622_2);
        Comments r622_3 = new Comments("Ruta 622","20 minutos de espera en la base.","User123");
        allComments.add(r622_3);
        databaseHandler.addComment(r622_3);

        //623
        Comments r623_1 = new Comments("Ruta 623","Desvio por mercado de abastos","User123");
        allComments.add(r623_1);
        databaseHandler.addComment(r623_1);
        Comments r623_2 = new Comments("Ruta 623","Accidente en avenida mandarina","User123");
        allComments.add(r623_2);
        databaseHandler.addComment(r623_2);
        Comments r623_3 = new Comments("Ruta 623","Ambulancia en circulación cerca de cruz del sur","User123");
        allComments.add(r623_3);
        databaseHandler.addComment(r623_3);

        //623A
        Comments r623A_1 = new Comments("Ruta 623-A","Desvio por plaza Centro Sur","User123");
        allComments.add(r623A_1);
        databaseHandler.addComment(r623A_1);
        Comments r623A_2 = new Comments("Ruta 623-A","Inundación en Lomas del mirador","User123");
        allComments.add(r623A_2);
        databaseHandler.addComment(r623A_2);
        Comments r623A_3 = new Comments("Ruta 623-A","Chófer no da parada en Prepa 5","User123");
        allComments.add(r623A_3);
        databaseHandler.addComment(r623A_3);

        //624
        Comments r624_1 = new Comments("Ruta 624","Carga pesada en Heroes ferrocarrileros","User123");
        allComments.add(r624_1);
        databaseHandler.addComment(r624_1);
        Comments r624_2 = new Comments("Ruta 624","Gorrion y 8 de Julio con obras de repavimentacion","User123");
        allComments.add(r624_2);
        databaseHandler.addComment(r624_2);
        Comments r624_3 = new Comments("Ruta 624","Washington inundado","User123");
        allComments.add(r624_3);
        databaseHandler.addComment(r624_3);

        //625
        Comments r625_1 = new Comments("Ruta 625","Choque en av. Cozumel y Legaspi","User123");
        allComments.add(r625_1);
        databaseHandler.addComment(r625_1);
        Comments r625_2 = new Comments("Ruta 625","Niño atropellado en Lazaro cardenas y Cruz del Sur","User123");
        allComments.add(r625_2);
        databaseHandler.addComment(r625_2);
        Comments r625_3 = new Comments("Ruta 625","Manifestación en López Cotilla","User123");
        allComments.add(r625_3);
        databaseHandler.addComment(r625_3);

        //629
        Comments r629_1 = new Comments("Ruta 629","Inundacion por centro magno, choferes sin dar parada","User123");
        allComments.add(r629_1);
        databaseHandler.addComment(r629_1);
        Comments r629_2 = new Comments("Ruta 629","trafico pesado en zona minerva","User123");
        allComments.add(r629_2);
        databaseHandler.addComment(r629_2);
        Comments r629_3 = new Comments("Ruta 629","Camiones a reventar en la base","User123");
        allComments.add(r629_3);
        databaseHandler.addComment(r629_3);

        //646
        Comments r646_1 = new Comments("Ruta 646","Ambulancias en Arroz y Lazaro cardenas","User123");
        allComments.add(r646_1);
        databaseHandler.addComment(r646_1);
        Comments r646_2 = new Comments("Ruta 646","Accidente en Lopez Mateos altura minerva","User123");
        allComments.add(r646_2);
        databaseHandler.addComment(r646_2);
        Comments r646_3 = new Comments("Ruta 646","Personal de bomberos altura la gran plaza","User123");
        allComments.add(r646_3);
        databaseHandler.addComment(r646_3);

        //MCRP
        Comments MCRP1 = new Comments("MACROBUS PARADOR","Choque en San Juan de Dios","User123");
        allComments.add(MCRP1);
        databaseHandler.addComment(MCRP1);
        Comments MCRP2 = new Comments("MACROBUS PARADOR","Taxista circula dentro de la vía del macro","User123");
        allComments.add(MCRP2);
        databaseHandler.addComment(MCRP2);
        Comments MCRP3 = new Comments("MACROBUS PARADOR","ciclista atropellado cerca del Zoológico","User123");
        allComments.add(MCRP3);
        databaseHandler.addComment(MCRP3);

        //MCRX
        Comments MCRX1 = new Comments("MACROBUS EXPRESS","Riña entre conductores fuera del agua azul","User123");
        allComments.add(MCRX1);
        databaseHandler.addComment(MCRX1);
        Comments MCRX2 = new Comments("MACROBUS EXPRESS","Conductor ebrío choca el macrobus","User123");
        allComments.add(MCRX2);
        databaseHandler.addComment(MCRX2);
        Comments MCRX3 = new Comments("MACROBUS EXPRESS","ciclista atropellado cerca del Zoológico","User123");
        allComments.add(MCRX3);
        databaseHandler.addComment(MCRX3);

        //TL1
        Comments TL11 = new Comments("SITEUR LINEA 1","Tren detenido por inundación en estación Raza","User123");
        allComments.add(TL11);
        databaseHandler.addComment(TL11);
        Comments TL12 = new Comments("SITEUR LINEA 1","Servicio suspendido desde 18 de Marzo hasta Juarez","User123");
        allComments.add(TL12);
        databaseHandler.addComment(TL12);
        Comments TL13 = new Comments("SITEUR LINEA 1","Choque en Estación España entre tren y Particular","User123");
        allComments.add(TL13);
        databaseHandler.addComment(TL13);

        //TL2
        Comments TL21 = new Comments("SITEUR LINEA 2","Manifestación en estación San Juan de dios","User123");
        allComments.add(TL21);
        databaseHandler.addComment(TL21);
        Comments TL22 = new Comments("SITEUR LINEA 2","Robo de bicicleta en Juarez","User123");
        allComments.add(TL22);
        databaseHandler.addComment(TL22);
        Comments TL23 = new Comments("SITEUR LINEA 2","Credencial de estudiante perdida en Belisario","User123");
        allComments.add(TL23);
        databaseHandler.addComment(TL23);

        //STRL1
        Comments STRL11 = new Comments("SITREN LINEA 1","Tráfico pesado en cámara de comercio, postes caídos","User123");
        allComments.add(STRL11);
        databaseHandler.addComment(STRL11);
        Comments STRL12 = new Comments("SITREN LINEA 1","Agentes de tránsito mediando en Vallarta e Inglaterra","User123");
        allComments.add(STRL12);
        databaseHandler.addComment(STRL12);
        Comments STRL13 = new Comments("SITREN LINEA 1","Estacionamiento gratis en Av Américas","User123");
        allComments.add(STRL13);
        databaseHandler.addComment(STRL13);

        //STRL2
        Comments STRL21 = new Comments("SITREN LINEA 2","Agentes de la policía federal en Basilio Vadillo","User123");
        allComments.add(STRL21);
        databaseHandler.addComment(STRL21);
        Comments STRL22 = new Comments("SITREN LINEA 2","Tiempo de espera de 20 minutos en Tetlán","User123");
        allComments.add(STRL22);
        databaseHandler.addComment(STRL22);
        Comments STRL23 = new Comments("SITREN LINEA 2","Paramédicos atentiendo choque grave en Solidaridad","User123");
        allComments.add(STRL23);
        databaseHandler.addComment(STRL23);

        //STRL3
        Comments STRL31 = new Comments("SITREN LINEA 3","Camiones a reventar en altura chapultepec","User123");
        allComments.add(STRL31);
        databaseHandler.addComment(STRL31);
        Comments STRL32 = new Comments("SITREN LINEA 3","Espera de 40 min","User123");
        allComments.add(STRL32);
        databaseHandler.addComment(STRL32);
        Comments STRL33 = new Comments("SITREN LINEA 3","Ruta sin circular por Centro Magno","User123");
        allComments.add(STRL33);
        databaseHandler.addComment(STRL33);
    }
}
