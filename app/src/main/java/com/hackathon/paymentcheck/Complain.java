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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Complain extends Activity {

    Button btn_vat_complain, btn_general_complain, btn_check;
    EditText et_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);
        init();
        new DrawerBuilder().withActivity(Complain.this).build();
        //Adding the items... :D
        PrimaryDrawerItem complain_status = new PrimaryDrawerItem().withName("Complain Status");
        PrimaryDrawerItem general_complain = new PrimaryDrawerItem().withName("General Complain");
        PrimaryDrawerItem vat_complain = new PrimaryDrawerItem().withName("VAT Complain");
        PrimaryDrawerItem vat_check = new PrimaryDrawerItem().withName("VAT Status Check");

        final Drawer result = new DrawerBuilder()

                .withActivity(Complain.this)
                .addDrawerItems(complain_status, general_complain, vat_complain, vat_check)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {


                        //Handle the clicks
                        if (position == 0) {



                        } else if (position == 1) {

                            Intent i = new Intent(Complain.this, General_Complain.class);

                            startActivity(i);



                        }
                        else if(position==2)
                        {
                            Intent i = new Intent(Complain.this, VAT_Complain.class);
                            startActivity(i);

                        }
                        else if(position==3)
                        {
                            Intent i = new Intent(Complain.this, VatCheck.class);
                            startActivity(i);

                        }


                        return true;
                    }
                })
                .build();





        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDialog("mash", "mash");
                new get_complain_status().execute(et_id.getText().toString().trim());
            }
        });

    }

    private void init() {

        et_id = (EditText) findViewById(R.id.et_complainid);
        btn_check = (Button) findViewById(R.id.btn_check);
    }


    private void showDialog(String title, String msg)
    {
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(Complain.this);
        builder1.setMessage(msg);
        builder1.setTitle(title);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       dialog.cancel();
                    }
                });



        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    class get_complain_status extends AsyncTask<String, Void, String>
    {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                if(s==null)
                {
                    showDialog("Error", "Something went wrong! Please check");
                    return;
                }
                JSONArray array = new JSONArray(s);
                JSONObject object = array.getJSONObject(0);
                showDialog("Complain Status", "Status: " +object.getString("status") + "\nComplete Date: " +
                        object.getString("completed_date"));
            } catch (JSONException e) {
               showDialog("Not Found!", "No complain found having this id!");
            }

        }

        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            Log.i("-------------", id);
            try
            {

                String json = SrcGrabber.grabSource(Constants.GET_COMPLAIN_INFO_LINK + id);
                Log.i("--------------", json);

                return json;
            }
            catch (Exception e)
            {

                return null;
            }


        }
    }

}
