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

public class VatCheck extends Activity {
    Button btn_vat_complain, btn_general_complain, btn_check;
    EditText et_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vat_check);
        init();
        new DrawerBuilder().withActivity(VatCheck.this).build();
        //Adding the items... :D
        PrimaryDrawerItem complain_status = new PrimaryDrawerItem().withName("Complain Status");
        PrimaryDrawerItem general_complain = new PrimaryDrawerItem().withName("General Complain");
        PrimaryDrawerItem vat_complain = new PrimaryDrawerItem().withName("VAT Complain");

        final Drawer result = new DrawerBuilder()

                .withActivity(VatCheck.this)
                .addDrawerItems(complain_status, general_complain, vat_complain)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {


                        //Handle the clicks
                        if (position == 0) {



                        } else if (position == 1) {

                            Intent i = new Intent(VatCheck.this, General_Complain.class);

                            startActivity(i);



                        }
                        else if(position==2)
                        {
                            Intent i = new Intent(VatCheck.this, VAT_Complain.class);
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
                new get_vat_status().execute(et_id.getText().toString().trim());
            }
        });


    }
    private void showDialog(String title, String msg)
    {
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(VatCheck.this);
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
    private void init() {

        et_id = (EditText) findViewById(R.id.et_token);
        btn_check = (Button) findViewById(R.id.btn_check);
    }

    class get_vat_status extends AsyncTask<String, Void, String>
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
                showDialog("VAT Status", "Product Cost: " +object.getString("product_cost") + "\nTotal VAT: " +
                        object.getString("total_vat") + "\n" +
                        "Total Amount: " + object.getString("total_amount") + "\n" +
                        "Created At: " + object.getString("created_at"));
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

                String json = SrcGrabber.grabSource("http://192.168.0.126/spyman/public/API/V1.1/invoice/check/" + id);
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
