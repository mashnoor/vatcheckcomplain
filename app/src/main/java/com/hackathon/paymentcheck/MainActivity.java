package com.hackathon.paymentcheck;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends Activity {



    EditText purchase_id;
    Button btn_show_info;
    final static String OK_BTN = "OK";
    final static String COMPLAIN_BTN = "Complain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize_all();
        new DrawerBuilder().withActivity(MainActivity.this).build();
        //Adding the items... :D
        PrimaryDrawerItem complain_status = new PrimaryDrawerItem().withName("Complain Status");
        PrimaryDrawerItem general_complain = new PrimaryDrawerItem().withName("General Complain");
        PrimaryDrawerItem vat_complain = new PrimaryDrawerItem().withName("VAT Complain");

        final Drawer result = new DrawerBuilder()

                .withActivity(MainActivity.this)
                .addDrawerItems(complain_status, general_complain, vat_complain)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {


                        //Handle the clicks
                        if (position == 0) {
                            Intent i = new Intent(MainActivity.this, Complain.class);
                            startActivity(i);


                        } else if (position == 1) {

                            Intent i = new Intent(MainActivity.this, General_Complain.class);

                            startActivity(i);



                        }
                        else if(position==2)
                        {
                            Intent i = new Intent(MainActivity.this, VAT_Complain.class);
                            startActivity(i);

                        }


                        return true;
                    }
                })
                .build();






        btn_show_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = purchase_id.getText().toString().trim();
               String[] server_and_id = id.split("\\_");
                String server_address = server_and_id[0];
                String purchase_id = server_and_id[1];
                new grab_info().execute(server_address, purchase_id);




            }
        });



    }


    class grab_info extends AsyncTask<String, Void, Void>
    {

        @Override
        protected Void doInBackground(String... params) {



            return null;
        }
    }


    private void showDialog(String msg, final String btnMsg)
    {
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                btnMsg,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (btnMsg.equals(OK_BTN))
                        {
                            dialog.cancel();
                        }
                        else
                        {
                            showToast("Complain");
                        }
                    }
                });



        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    private void showToast(String msg)
    {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
    }


}
