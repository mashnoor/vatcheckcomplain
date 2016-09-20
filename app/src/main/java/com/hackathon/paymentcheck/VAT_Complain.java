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

public class VAT_Complain extends AppCompatActivity {

    EditText name, tokenid, vatreg, mobile, complain, institution, address, complainType;
    Button send_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vat_complain);
        init();
        new DrawerBuilder().withActivity(VAT_Complain.this).build();
        //Adding the items... :D
        PrimaryDrawerItem complain_status = new PrimaryDrawerItem().withName("Complain Status");
        PrimaryDrawerItem general_complain = new PrimaryDrawerItem().withName("General Complain");
        PrimaryDrawerItem vat_complain = new PrimaryDrawerItem().withName("VAT Complain");
        PrimaryDrawerItem vat_check = new PrimaryDrawerItem().withName("VAT Check");

        final Drawer result = new DrawerBuilder()

                .withActivity(VAT_Complain.this)
                .addDrawerItems(complain_status, general_complain, vat_complain, vat_check)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {


                        //Handle the clicks
                        if (position == 0) {
                            Intent i = new Intent(VAT_Complain.this, Complain.class);
                            startActivity(i);


                        } else if (position == 1) {

                            Intent i = new Intent(VAT_Complain.this, General_Complain.class);

                            startActivity(i);


                        } else if (position == 2) {

                        } else if (position == 3) {
                            Intent i = new Intent(VAT_Complain.this, VatCheck.class);
                        }


                        return true;
                    }
                })
                .build();





        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = tokenid.getText().toString().trim();
                String vatregid = vatreg.getText().toString().trim();
                String mobileno = mobile.getText().toString().trim();
                String complaintext = complain.getText().toString().trim();
                String institutionname = institution.getText().toString().trim();
                String type = complainType.getText().toString().trim();
                String complainer_name = name.getText().toString().trim();
                String complainer_address = address.getText().toString();
                if(isValid(token) && isValid(vatregid) && isValid(mobileno) && isValid(complaintext) && isValid(institutionname))
                {
                    new send_data_to_server().execute(token,complainer_name,vatregid,institutionname,complainer_address,complaintext,type, mobileno);
                }
                else
                {
                    showToast("Some fields are blank! Check your inputs");
                }


            }
        });

    }

    private void init() {
        tokenid = (EditText) findViewById(R.id.et_token);
        vatreg = (EditText) findViewById(R.id.et_vat_reg);
        mobile = (EditText) findViewById(R.id.et_mobile);
        complain = (EditText) findViewById(R.id.et_complain);
        institution = (EditText) findViewById(R.id.et_institute);
        address = (EditText) findViewById(R.id.et_address);
        complainType = (EditText) findViewById(R.id.et_complain_type);
        name = (EditText) findViewById(R.id.et_name);
        send_button = (Button) findViewById(R.id.btn_send);

    }
    private boolean isValid(String s)
    {
        if(s.equals(""))
            return false;

        return  true;
    }

    class send_data_to_server extends AsyncTask<String, Void, String>
    {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null)
            {
                showToast("Your complain accepted successfully.");
            }
            else
            {
                showToast("Something went wrong! Please check again");
            }

        }

        @Override
        protected String doInBackground(String... params) {

            try
            {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://192.168.0.126/spyman/public/API/V1.1/complain/vat");

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("token_id", params[0]));
                nameValuePairs.add(new BasicNameValuePair("complainer_name", params[1]));
                nameValuePairs.add(new BasicNameValuePair("vat_reg_id", params[2]));
                nameValuePairs.add(new BasicNameValuePair("institute_name", params[3]));
                nameValuePairs.add(new BasicNameValuePair("address", params[4]));
                nameValuePairs.add(new BasicNameValuePair("complain", params[5]));
                nameValuePairs.add(new BasicNameValuePair("complain_type", params[6]));
                nameValuePairs.add(new BasicNameValuePair("complainer_mobile", params[7]));


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

                return null;

            }

        }
    }


    private void showToast(String msg)
    {
        Toast.makeText(VAT_Complain.this, msg, Toast.LENGTH_LONG).show();
    }

}
