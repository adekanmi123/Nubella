package com.purplecommerce.nubella;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.purplecommerce.nubella.ApiManager.ApiManager;
import com.purplecommerce.nubella.POJO_models.CheckResponse;
import com.purplecommerce.nubella.POJO_models.UserAddressesResponse;
import com.purplecommerce.nubella.SessionManager.SessionManager;
import com.purplecommerce.nubella.Utils.AppUtils;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import okhttp3.CookieJar;
import okhttp3.OkHttpClient;


public class AddressesActivity extends BaseActivity {


    LinearLayout ll_edit_addresses, ll_parent_new , add_new;
    ArrayList<Boolean> arrayList = new ArrayList<>();
    SessionManager sm ;
    OkHttpClient okHttpClient ;
    Dialog progress_dialog ;
    TextView No_Address ;
    ApiManager apiManager ;
    final static String DELETEADDRESSTAG = "DeleteAddress";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor();
        setContentView(R.layout.activity_addresses);


      init();


//        for (int i = 0 ; i < 2 ; i++){
//            ll_edit_addresses.addView(AddViewOnLinear(ll_edit_addresses , i, addressesResponse.getModel().get(i)));
//            arrayList.add(false);
//        }



        add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddressesActivity.this , Edit_New_AddressActivity.class);
                i.putExtra("FROM" , "NEW");
                startActivity(i);
            }
        });


        apiManager = new ApiManager(AddressesActivity.this, new ApiManager.APIFETCHER() {
            @Override
            public void onAPIRunningState(int a) {

            }

            @Override
            public void onFetchProgress(int progress) {

            }

            @Override
            public void onFetchComplete(String script, String APINAME, Gson gson) {

                if (APINAME.equals(DELETEADDRESSTAG)){

                    CheckResponse checkResponse = new CheckResponse();
                    checkResponse = gson.fromJson(script , CheckResponse.class);
                    if (checkResponse.getCode()==0){
                        GetUserAddresses();
                    }else {
                        Toast.makeText(AddressesActivity.this, ""+checkResponse.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFetchFailed(ANError error) {

            }

            @Override
            public void WhichApi(String APINAME) {

            }
        });



    }


    private void GetUserAddresses(){

        progress_dialog.show();

        AndroidNetworking.get(AppUtils.BASE_URL+"/mobileapi/address/getAddressList")
                .setTag(this).setPriority(Priority.MEDIUM)
                .setOkHttpClient(okHttpClient)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                    }
                }).getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                progress_dialog.dismiss();

                Log.e("**Response", "**Addresses" + response);

                Gson gson = new GsonBuilder().create();

                UserAddressesResponse addressesResponse = new UserAddressesResponse();
                addressesResponse = gson.fromJson("" + response, UserAddressesResponse.class);


                if (addressesResponse.getCode() == 5) {
                    Toast.makeText(AddressesActivity.this, ""+addressesResponse.getMsg(), Toast.LENGTH_SHORT).show();
                } else {

                    if (addressesResponse.getModel().isEmpty()) {

                        No_Address.setVisibility(View.VISIBLE);
                        ll_edit_addresses.setVisibility(View.GONE);

                    } else {

                        No_Address.setVisibility(View.GONE);
                        ll_edit_addresses.setVisibility(View.VISIBLE);

                        ll_edit_addresses.removeAllViews();

                        for (int i = 0; i < addressesResponse.getModel().size(); i++) {

                            ll_edit_addresses.addView(AddViewOnLinear(ll_edit_addresses, i, addressesResponse.getModel().get(i)));
                            arrayList.add(false);

                        }

                    }

                }

            }

            @Override
            public void onError(ANError anError) {
                progress_dialog.dismiss();
                Log.e("errror", "" + anError.getErrorBody());
                Log.e("errror", "" + anError.getErrorDetail());
                Log.e("errror", "" + anError.getMessage());
                Log.e("error", "" + anError.getStackTrace());
                Log.e("error", "" + anError.getCause());

                if (anError.getErrorDetail().equals("connectionError")){
                    Snackbar.make(ll_parent_new, "Check Internet Connection !!", Snackbar.LENGTH_LONG).show();
                }

            }
        });

    }




    private void init() {

        ll_edit_addresses = (LinearLayout)findViewById(R.id.ll_edit_addresses);
        ll_parent_new = (LinearLayout)findViewById(R.id.ll_add_address);

        add_new = (LinearLayout)findViewById(R.id.ll_add_new);
        No_Address = (TextView)findViewById(R.id.no_new_address);

        setTitle("Addresses");

        CookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(AddressesActivity.this));

        okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();

        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v1 = vi.inflate(R.layout.progress_view, null);
        progress_dialog = new Dialog(AddressesActivity.this);
        progress_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress_dialog.setCancelable(false);
        progress_dialog.setContentView(v1);
        AppUtils.clearParentsBackgrounds(v1);




    }

    @Override
    protected void onResume() {
        super.onResume();
        GetUserAddresses();
    }

    public View AddViewOnLinear(LinearLayout ll_Parent, final int position, final UserAddressesResponse.ModelBean modelBean){

        LayoutInflater inflater =(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View myView = inflater.inflate(R.layout.added_addresses_layout, null);


        final FrameLayout radio_frame = (FrameLayout)myView.findViewById(R.id.radio_over_frame);
        RadioButton rd = (RadioButton)myView.findViewById(R.id.Radio_button);
        rd.setText(modelBean.getName()+" " +modelBean.getTelephone());

        if (modelBean.isIs_default_shipping() == true && modelBean.isIs_default_billing() == true){
            rd.setChecked(true);
            radio_frame.setVisibility(View.VISIBLE);
            radio_frame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(radio_frame, "Already Default Address !!", Snackbar.LENGTH_LONG).show();
                }
            });
        }else {
            radio_frame.setVisibility(View.VISIBLE);
            radio_frame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(radio_frame, "Click On Edit For Make Default Address !!", Snackbar.LENGTH_LONG).show();
                }
            });
        }

        TextView address = (TextView)myView.findViewById(R.id.address_detail);
        String streets = TextUtils.join(",", modelBean.getStreet());
        address.setText(modelBean.getCompany()+", "+streets+", "+modelBean.getCity()+", "+modelBean.getRegion()
                +", ("+modelBean.getPostcode()+")");


        TextView Edit_Address = (TextView)myView.findViewById(R.id.txt_edit_address);
        TextView Delete_Address = (TextView)myView.findViewById(R.id.txt_delete_address);

        Edit_Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(AddressesActivity.this , Edit_New_AddressActivity.class);
                i.putExtra("FROM" , "EDIT");
                i.putExtra("MODEL" , (Serializable) modelBean);
                startActivity(i);

            }
        });
        Delete_Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            String url  = "/mobileapi/address/delete?address_id="+modelBean.getEntity_id();

            apiManager.execution_method_get(DELETEADDRESSTAG , url);

            }
        });




        return myView ;

    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = AddressesActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(AddressesActivity.this.getResources().getColor(R.color.colorPrimary));
        } else {
            Window window = AddressesActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }




}
