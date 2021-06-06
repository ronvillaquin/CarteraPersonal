package com.rrvq.carterapersonal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Main2Activity_Categorias extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    Cursor fila;


    // id de los botones view
    ImageButton castinImageGastos[], castinImageIngresos[];
    TextView castintvGastos[], castintvIngresos[];
    int btn_idGastos[] = {R.id.btn_img_1, R.id.btn_img_2, R.id.btn_img_3, R.id.btn_img_4, R.id.btn_img_5, R.id.btn_img_6,
            R.id.btn_img_7, R.id.btn_img_8, R.id.btn_img_9, R.id.btn_img_10, R.id.btn_img_11, R.id.btn_img_12};
    int btn_idIngresos[] = {R.id.btn_img_13, R.id.btn_img_14, R.id.btn_img_15, R.id.btn_img_16};

    public static Activity fa;  // para llamar y fializar el activity desde otro

    //para la publicidad
    Publicidad publicidad = new Publicidad();
    private AdView mAdView, mAdView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2__categorias);

        //*************************  para llamar el objeto de la clase publicidad **************/
        publicidad.mostrarBanner(this);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //*************************  para llamar el objeto de la clase publicidad **************/
        publicidad.mostrarBanner(this);
        mAdView2 = findViewById(R.id.adView2);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mAdView2.loadAd(adRequest2);


        fa = this; // para finalizar el activity llamandolo desde otro activity
        /*********************  Casting de los view ********************************/
        castin_view();
        /*********************  Toolbar  ********************************/
        setSupportActionBar(toolbar);
        flechaBlanca();
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>" + getResources().getString(R.string.categorias) + "</font>"));

        /*********************  Conexion a la base de datos  ********************************/
        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(this, "BDCartera", null, 1);
        SQLiteDatabase baseDeDatos = adminDB.getWritableDatabase();

        /*********************  Imagenes Categoria GASTOS ********************************/
        fila = baseDeDatos.rawQuery("SELECT icon_gasto, inombre_gasto FROM icono_gasto", null);
        if (fila.moveToFirst()) {
            int i = 0;
            do {
                castinImageGastos[i].setImageResource(fila.getInt(0));
                castintvGastos[i].setText(fila.getString(1));
                castinImageGastos[i].setOnClickListener(this);
                i++;
            } while (fila.moveToNext());
        }

        /*********************  Imagenes Categoria INGRESOS ********************************/
        fila = baseDeDatos.rawQuery("SELECT icon_ingreso, inombre_ingreso FROM icono_ingreso", null);
        if (fila.moveToFirst()) {
            int i = 0;
            do {
                castinImageIngresos[i].setImageResource(fila.getInt(0));
                castintvIngresos[i].setText(fila.getString(1));
                castinImageIngresos[i].setOnClickListener(this);
                i++;
            } while (fila.moveToNext());
        }

        baseDeDatos.close();

    }

    /*********************  Casting de los view ********************************/
    public void castin_view() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        // castin de las imagen button de gastos
        castinImageGastos = new ImageButton[]{
                (ImageButton) findViewById(R.id.btn_img_1), (ImageButton) findViewById(R.id.btn_img_2),
                (ImageButton) findViewById(R.id.btn_img_3), (ImageButton) findViewById(R.id.btn_img_4),
                (ImageButton) findViewById(R.id.btn_img_5), (ImageButton) findViewById(R.id.btn_img_6),
                (ImageButton) findViewById(R.id.btn_img_7), (ImageButton) findViewById(R.id.btn_img_8),
                (ImageButton) findViewById(R.id.btn_img_9), (ImageButton) findViewById(R.id.btn_img_10),
                (ImageButton) findViewById(R.id.btn_img_11), (ImageButton) findViewById(R.id.btn_img_12)};
        // castin de las imagen button de gastos
        castinImageIngresos = new ImageButton[]{
                (ImageButton) findViewById(R.id.btn_img_13), (ImageButton) findViewById(R.id.btn_img_14),
                (ImageButton) findViewById(R.id.btn_img_15), (ImageButton) findViewById(R.id.btn_img_16),};
        // Castin de textview 1
        castintvGastos = new TextView[]{
                (TextView) findViewById(R.id.tv1), (TextView) findViewById(R.id.tv2), (TextView) findViewById(R.id.tv3),
                (TextView) findViewById(R.id.tv4), (TextView) findViewById(R.id.tv5), (TextView) findViewById(R.id.tv6),
                (TextView) findViewById(R.id.tv7), (TextView) findViewById(R.id.tv8), (TextView) findViewById(R.id.tv9),
                (TextView) findViewById(R.id.tv10), (TextView) findViewById(R.id.tv11), (TextView) findViewById(R.id.tv12)};
        // Castin de textview 1
        castintvIngresos = new TextView[]{
                (TextView) findViewById(R.id.tv13), (TextView) findViewById(R.id.tv14), (TextView) findViewById(R.id.tv15),
                (TextView) findViewById(R.id.tv16)};
    }

    /*********************  Flecha ATRAS color BLANCA ********************************/
    public void flechaBlanca() {

        // pra colocar la flecha de color blanco de volver
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // flecha de volver atras
    }

    //Verificar si va enviar datos del boton de categoria gastos o categoria cuentas

    /*********************  boton add categria gastos ********************************/
    public void btnAddGastos(View vista) {

        /*********************  Conexion a la base de datos  ********************************/
        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(this, "BDCartera", null, 1);
        SQLiteDatabase baseDeDatos = adminDB.getWritableDatabase();
        fila = baseDeDatos.rawQuery("SELECT * FROM icono_gasto", null);
        int countGastos = fila.getCount();

        if (countGastos < 12) {
            baseDeDatos.close();
            Intent Siguiente = new Intent(this, Main2Activity_PRO.class);
            startActivity(Siguiente);
            /*Intent intent = new Intent(this, Main2Activity_Categorias_add.class);
            intent.putExtra("dato", "gasto");
            startActivity(intent);*/
        } else {
            Intent Siguiente = new Intent(this, Main2Activity_PRO.class);
            startActivity(Siguiente);
//            Toast.makeText(this, getResources().getString(R.string.limitemaximo), Toast.LENGTH_SHORT).show();
        }
    }

    /*********************  boton add categria cuentas ********************************/
    public void btnAddIngresos(View vista) {

        /*********************  Conexion a la base de datos  ********************************/
        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(this, "BDCartera", null, 1);
        SQLiteDatabase baseDeDatos = adminDB.getWritableDatabase();

        fila = baseDeDatos.rawQuery("SELECT * FROM icono_ingreso", null);
        int countIngresos = fila.getCount();

        if (countIngresos < 4) {
            baseDeDatos.close();
            Intent Siguiente = new Intent(this, Main2Activity_PRO.class);
            startActivity(Siguiente);
            /*Intent intent = new Intent(this, Main2Activity_Categorias_add.class);
            intent.putExtra("dato", "ingreso");
            startActivity(intent);*/
        } else {
            Intent Siguiente = new Intent(this, Main2Activity_PRO.class);
            startActivity(Siguiente);
//            Toast.makeText(this, getResources().getString(R.string.limitemaximo), Toast.LENGTH_SHORT).show();
        }
    }

    /*********************  Para recuperar los click de las imagebuttons ********************************/
    @Override
    public void onClick(View v) {

        /*********************  Conexion a la base de datos  ********************************/
        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(this, "BDCartera", null, 1);
        SQLiteDatabase baseDeDatos = adminDB.getWritableDatabase();

        // GASTOS EDITAR Para los iconos de GASTOS
        fila = baseDeDatos.rawQuery("SELECT rowid FROM icono_gasto", null);
        if (fila.moveToFirst()) {
            int i = 0;
            do {
                if (v.getId() == btn_idGastos[i]) {
                    int row = fila.getInt(0);

                    String rowString = String.valueOf(row);
                    Intent intent = new Intent(this, Main2Activity_Categorias_edit.class);
                    intent.putExtra("row_id", rowString);
                    // para saber que icono editar
                    intent.putExtra("dato", "gasto");
                    startActivity(intent);
                }
                i++;
            } while (fila.moveToNext());
        }

        // INGRESO EDITAR para los iconos de INGRESO
        fila = baseDeDatos.rawQuery("SELECT rowid FROM icono_ingreso", null);
        if (fila.moveToFirst()) {
            int i = 0;
            do {
                if (v.getId() == btn_idIngresos[i]) {
                    int row = fila.getInt(0);

                    String rowString = String.valueOf(row);
                    Intent intent = new Intent(this, Main2Activity_Categorias_edit.class);
                    intent.putExtra("row_id", rowString);
                    // para saber que icono editar
                    intent.putExtra("dato", "ingreso");
                    startActivity(intent);
                }
                i++;
            } while (fila.moveToNext());
        }


        baseDeDatos.close();
    }

    /*************************BOTON ATRAS*********************************************/
    // para evitar que usen el boton de regresar de android paraq ue no reguresa los activitys
    //la palabra override es para sobre escribir metodos que ya estan establecidos

    // el codigo que esta e spara refrescar el activity sin necesidad d ellamarlo de nuevo y sin tener un pestañeo
    @Override
    public void onBackPressed() {
        finish();
        Intent siguiente = new Intent(this, MainActivity.class);
        siguiente.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        // con la de arriba se elimian todas todas menos la que se llamo
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);  // con esta elimina solo las q estan delante de la que se llamo
        startActivity(siguiente);
    }


}
