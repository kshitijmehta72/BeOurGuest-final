package com.example.beourguest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PayTM extends AppCompatActivity implements PaytmPaymentTransactionCallback {
    String custid="", orderId="", mid="";
    Bundle mbundle;
    FirebaseAuth mAuth;
    String oid;
    String pay="15";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_tm);
        mAuth = FirebaseAuth.getInstance();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mbundle = getIntent().getBundleExtra("info");
        oid = UUID.randomUUID().toString();
        orderId = oid;
        custid = mAuth.getUid();

        mid = "XEJTUx20020032329398"; //PayTM merchant key
        PayTM.sendUserDetailTOServerdd dl = new PayTM.sendUserDetailTOServerdd();
        dl.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    public class sendUserDetailTOServerdd extends AsyncTask<ArrayList<String>, Void, String> {

        private ProgressDialog dialog = new ProgressDialog(PayTM.this);
        //Server URL
        String url ="https://fragmental-weave.000webhostapp.com/generateChecksum.php";
        String varifyurl = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID="+orderId;
        String CHECKSUMHASH ="";

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }

        protected String doInBackground(ArrayList<String>... alldata) {
            JSONParser jsonParser = new JSONParser(PayTM.this);
            String param=
                    "MID="+mid+
                            "&ORDER_ID=" +orderId+
                            "&CUST_ID="+custid+
                            "&CHANNEL_ID=WAP&TXN_AMOUNT="+pay+"&WEBSITE=DEFAULT"+
                            "&CALLBACK_URL="+varifyurl+"&INDUSTRY_TYPE_ID=Retail";

            JSONObject jsonObject = jsonParser.makeHttpRequest(url,"POST",param);
            Log.e("CheckSum result >>",jsonObject.toString());
            if(jsonObject != null){
                Log.e("CheckSum result >>",jsonObject.toString());
                try {

                    CHECKSUMHASH=jsonObject.has("CHECKSUMHASH")?jsonObject.getString("CHECKSUMHASH"):"";
                    Log.e("CheckSum result >>",CHECKSUMHASH);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return CHECKSUMHASH;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e(" setup acc ","  signup result  " + result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            PaytmPGService Service = PaytmPGService.getProductionService();
            //PayTM HashMap data
            HashMap<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("MID", mid);
            paramMap.put("ORDER_ID", orderId);
            paramMap.put("CUST_ID", custid);
            paramMap.put("CHANNEL_ID", "WAP");
            paramMap.put("TXN_AMOUNT", pay);
            paramMap.put("WEBSITE", "DEFAULT");
            paramMap.put("CALLBACK_URL" ,varifyurl);
            paramMap.put("CHECKSUMHASH" ,CHECKSUMHASH);
            paramMap.put("INDUSTRY_TYPE_ID", "Retail");

            PaytmOrder Order = new PaytmOrder(paramMap);
            Log.e("checksum ", "param "+ paramMap.toString());
            Service.initialize(Order,null);
            Service.startPaymentTransaction(PayTM.this, true, true,
                    PayTM.this  );


        }

    }


    @Override
    public void onTransactionResponse(Bundle bundle) {
        Log.e("checksum ", " respon true " + bundle.toString());
        String suc = "01";
        String check = bundle.getString("RESPCODE");
        String txn = bundle.getString("TXNID");
        //If transaction success goto next intent
        if(suc.compareTo(check)==0) {
            mbundle.putString("PaymentStatus","Success");
            mbundle.putString("TXNID",txn);
            mbundle.putString("OID",oid);
            Intent next = new Intent(PayTM.this,Confirm.class);
            next.putExtra("info",mbundle);
            startActivity(next);
            finish();
        } else {
            //If transaction failed return to previous intent, Toast about transaction failure
            startActivity(new Intent(this,Home_Screen.class));
            finishAffinity();
            Toast.makeText(getApplicationContext(),"Transaction Failed!",Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void networkNotAvailable() {
        startActivity(new Intent(this,Home_Screen.class));
        finishAffinity();
        Toast.makeText(getApplicationContext(),"Transaction Failed!",Toast.LENGTH_LONG).show();
    }

    @Override
    public void clientAuthenticationFailed(String s) {
        startActivity(new Intent(this,Home_Screen.class));
        finishAffinity();
        Toast.makeText(getApplicationContext(),"Transaction Failed!",Toast.LENGTH_LONG).show();
    }

    @Override
    public void someUIErrorOccurred(String s) {
        Log.e("checksum ", " ui fail respon  "+ s );
        startActivity(new Intent(this,Home_Screen.class));
        finishAffinity();
        Toast.makeText(getApplicationContext(),"Transaction Failed!",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1) {
        Log.e("checksum ", " error loading pagerespon true "+ s + "  s1 " + s1);
        startActivity(new Intent(this,Home_Screen.class));
        finishAffinity();
        Toast.makeText(getApplicationContext(),"Transaction Failed!",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressedCancelTransaction() {
        Log.e("checksum ", " cancel call back respon  " );
        startActivity(new Intent(this,Home_Screen.class));
        finishAffinity();
        Toast.makeText(getApplicationContext(),"Transaction Failed!",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTransactionCancel(String s, Bundle bundle) {
        Log.e("checksum ", "  transaction cancel " );
        startActivity(new Intent(this,Home_Screen.class));
        finishAffinity();
        Toast.makeText(getApplicationContext(),"Transaction Failed!",Toast.LENGTH_LONG).show();
    }
}
