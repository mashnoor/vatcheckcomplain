package com.hackathon.paymentcheck;

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
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

public class General_Complain extends AppCompatActivity {

    EditText et_mobile, et_complain, et_institute, et_address, et_name, et_complain_type;
    Button send_complain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general__complain);

        init();
        new DrawerBuilder().withActivity(General_Complain.this).build();
        //Adding the items... :D
        PrimaryDrawerItem complain_status = new PrimaryDrawerItem().withName("Complain Status");
        PrimaryDrawerItem general_complain = new PrimaryDrawerItem().withName("General Complain");
        PrimaryDrawerItem vat_complain = new PrimaryDrawerItem().withName("VAT Complain");
        PrimaryDrawerItem vat_check = new PrimaryDrawerItem().withName("VAT Status Check");

        final Drawer result = new DrawerBuilder()

                .withActivity(General_Complain.this)
                .addDrawerItems(complain_status, general_complain, vat_complain, vat_check)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {


                        //Handle the clicks
                        if (position == 0) {
                            Intent i = new Intent(General_Complain.this, Complain.class);
                            startActivity(i);


                        } else if (position == 1) {





                        }
                        else if(position==2)
                        {
                            Intent i = new Intent(General_Complain.this, VAT_Complain.class);
                            startActivity(i);

                        }
                        else if(position==3)
                        {
                            Intent i = new Intent(General_Complain.this, VatCheck.class);
                            startActivity(i);

                        }


                        return true;
                    }
                })
                .build();



        send_complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = et_mobile.getText().toString().trim();
                String complain = et_complain.getText().toString().trim();
                String institue = et_institute.getText().toString().trim();
                String address = et_address.getText().toString().trim();
                String name = et_name.getText().toString().trim();
                String type = et_complain_type.getText().toString().trim();
                new send_data_to_server().execute(mobile,complain,institue, address, name, type);


            }
        });

    }

    private void init() {
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_complain = (EditText) findViewById(R.id.et_complain);
        et_institute = (EditText) findViewById(R.id.et_institute);
        et_address = (EditText) findViewById(R.id.et_address);
        et_name = (EditText) findViewById(R.id.et_name);
        et_complain_type = (EditText) findViewById(R.id.et_type);
        send_complain = (Button) findViewById(R.id.btn_send);
    }

    class send_data_to_server extends AsyncTask<String, Void, String>
    {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null)
            {
                showToast("Your complain is received successfully.");
               // showToast(s);
            }
            else
            {
                showToast("Something went wrong!");
            }

        }

        @Override
        protected String doInBackground(String... params) {

            try
            {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://192.168.0.126/spyman/public/API/V1.1/complain/general");

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


                nameValuePairs.add(new BasicNameValuePair("complainer_mobile", params[0]));
                nameValuePairs.add(new BasicNameValuePair("complain", params[1]));
                nameValuePairs.add(new BasicNameValuePair("institute_name", params[2]));
                nameValuePairs.add(new BasicNameValuePair("address", params[3]));
                nameValuePairs.add(new BasicNameValuePair("complainer_name", params[4]));
                nameValuePairs.add(new BasicNameValuePair("complain_type", params[5]));




                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity, "UTF-8");
                Log.i("--------------", responseString);
                return responseString;

            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;

            }

        }
    }
    private void showToast(String msg)
    {
        Toast.makeText(General_Complain.this, msg, Toast.LENGTH_LONG).show();
    }


}
