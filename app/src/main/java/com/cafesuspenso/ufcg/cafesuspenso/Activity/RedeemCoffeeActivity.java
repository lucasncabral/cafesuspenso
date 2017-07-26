package com.cafesuspenso.ufcg.cafesuspenso.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cafesuspenso.ufcg.cafesuspenso.Model.Cafeteria;
import com.cafesuspenso.ufcg.cafesuspenso.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class RedeemCoffeeActivity extends AppCompatActivity {

    private TextView codeRedeem;
    private Button shareBtn;

    private ShareDialog shareDialog;
    private CallbackManager callbackManager;
    private String code;
    private Cafeteria cafeteria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_coffee);
        codeRedeem = (TextView) findViewById(R.id.codeRedeem);
        shareBtn = (Button) findViewById(R.id.share_btn);
        cafeteria = getIntent().getParcelableExtra("cafeteria");


        codeRedeem.setText("");
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        // this part is optional
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                generateCode();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Cancel share", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Error share", Toast.LENGTH_SHORT).show();

            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShareFragment();
            }
        });

    }

    private void generateCode() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://cafesuspenso.herokuapp.com/api/user/push_code/" + cafeteria.getId() + "/" + cafeteria.getProduct().getId();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            code = new JSONArray(response).getJSONObject(0).getString("code");
                            codeRedeem.setText(code);
                        } catch (JSONException e) {e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("LoginE toString", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "RO1TNoKtrUfNSclm8jQs8L3RMX43");
                return params;
            }
        };
        queue.add(stringRequest);
        code = "";
    }

    private void showShareFragment() {
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("https://cafesuspenso.herokuapp.com"))
                    .setContentTitle("Café Suspenso")
                    .setContentDescription("Resgatei um café")
                    .build();
            shareDialog.show(linkContent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
