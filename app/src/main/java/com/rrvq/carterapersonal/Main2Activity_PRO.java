package com.rrvq.carterapersonal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class Main2Activity_PRO extends AppCompatActivity {

    Toolbar toolbar;
    String nombreDB = "BDCartera";
    int compra = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2__pro);

        castin_view();

        /*********************  Toolbar  ********************************/
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'></font>"));
        flechaBlanca();



    }
    /*********************  Casting de los view ********************************/
    public void castin_view() {
        toolbar = findViewById(R.id.toolbar);

    }

    /*********************  Flecha ATRAS color BLANCA ********************************/
    public void flechaBlanca() {

        // pra colocar la flecha de color blanco de volver
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // flecha de volver atras
    }

    /*********************  Casting de los view ********************************/
    public void btnComprar(View vista){

        //permisos para escribir en la sd del celular
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Verifica permisos para Android 6.0+
            permisoUsuarioGuardado();
        }


        /*********************** DIALOGO PARA Respaldar  *********************************/
        final AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle(getResources().getString(R.string.copiadeseguridad));
        dialogo.setMessage(getResources().getString(R.string.segurocopiacompra));
        dialogo.setCancelable(false);
        //para el bootn aceptar del dialogo
        dialogo.setPositiveButton(getResources().getString(R.string.si), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo, int id) {

                //permisos para escribir en la sd del celular
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //Verifica permisos para Android 6.0+
                    permisoUsuarioGuardado();
                }

                if (compra == 1) {
                    //llamamos el metodo par aexportar la base de dato
                    exportDB(Main2Activity_PRO.this);

                    finish();
                    Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.rrvq.carterapersonalpro");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }else {
                    Toast.makeText(Main2Activity_PRO.this, getResources().getString(R.string.debedarpermisos),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        // para bootn neutral
        dialogo.setNeutralButton(getResources().getString(R.string.cancelar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        // para el boton cancelar del dialogo
        dialogo.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo, int id) {

                finish();
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.rrvq.carterapersonalpro");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        dialogo.show();





    }

    /*********************** Para exportar la base de datos sqlite  *********************************/
    public void exportDB(Context context) {
        try {
//            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            File sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

            if (sd.canWrite()) {
                String backupDBPath = String.format("%s.bak", nombreDB);
                File currentDB = context.getDatabasePath(nombreDB);
                File backupDB = new File(sd, backupDBPath);

                // exportar
                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();

                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.respaldocompleto) +
                                    " " + backupDB.toString(),
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.respaldofallido),
                            Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //permiso ara poder escribir en la sd
    private void permisoUsuarioGuardado() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para leer.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
        } else {
            compra = 1;
            Log.i("Mensaje", "Se tiene permiso para leer!");
        }
    }

    /*************************BOTON ATRAS*********************************************/
    // para evitar que usen el boton de regresar de android paraq ue no reguresa los activitys
    //la palabra override es para sobre escribir metodos que ya estan establecidos

    // el codigo que esta e spara refrescar el activity sin necesidad d ellamarlo de nuevo y sin tener un pestañeo
    @Override
    public void onBackPressed() {
        finish();
    }



}
