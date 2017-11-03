package com.cafesuspenso.ufcg.cafesuspenso.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cafesuspenso.ufcg.cafesuspenso.Fragment.LevelUpFragment;
import com.cafesuspenso.ufcg.cafesuspenso.Model.Cafeteria;
import com.cafesuspenso.ufcg.cafesuspenso.Model.Connection;
import com.cafesuspenso.ufcg.cafesuspenso.Model.Product;
import com.cafesuspenso.ufcg.cafesuspenso.PagseguroDemo.AppUtil;
import com.cafesuspenso.ufcg.cafesuspenso.PagseguroDemo.MainActivity2;
import com.cafesuspenso.ufcg.cafesuspenso.PagseguroDemo.PagSeguroAddress;
import com.cafesuspenso.ufcg.cafesuspenso.PagseguroDemo.PagSeguroAreaCode;
import com.cafesuspenso.ufcg.cafesuspenso.PagseguroDemo.PagSeguroBrazilianStates;
import com.cafesuspenso.ufcg.cafesuspenso.PagseguroDemo.PagSeguroBuyer;
import com.cafesuspenso.ufcg.cafesuspenso.PagseguroDemo.PagSeguroCheckout;
import com.cafesuspenso.ufcg.cafesuspenso.PagseguroDemo.PagSeguroFactory;
import com.cafesuspenso.ufcg.cafesuspenso.PagseguroDemo.PagSeguroItem;
import com.cafesuspenso.ufcg.cafesuspenso.PagseguroDemo.PagSeguroPayment;
import com.cafesuspenso.ufcg.cafesuspenso.PagseguroDemo.PagSeguroPhone;
import com.cafesuspenso.ufcg.cafesuspenso.PagseguroDemo.PagSeguroShipping;
import com.cafesuspenso.ufcg.cafesuspenso.PagseguroDemo.PagSeguroShippingType;
import com.cafesuspenso.ufcg.cafesuspenso.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShareCoffeeActivity extends AppCompatActivity {

    private ShareDialog shareDialog;
    private CallbackManager callbackManager;
    private Cafeteria cafeteria;
    private Product product;
    private int qntdDisponiveis, resgatados, compartilhados, level;
    private ImageView imageProduct;
    private String token;

    // Pagamento


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_coffee);

        cafeteria = getIntent().getParcelableExtra("cafeteria");

        TextView name = (TextView) findViewById(R.id.txt_name_product);
        TextView price = (TextView) findViewById(R.id.txt_preco);

        imageProduct = (ImageView) findViewById(R.id.imageProduct);

        name.setText(cafeteria.getProduct().getName());
        price.setText("R$ " + cafeteria.getProduct().getPrice());

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        qntdDisponiveis = sharedPref.getInt("cafesDisponiveis", 0);
        token = sharedPref.getString("token", "");
        resgatados = sharedPref.getInt("cafesResgatados", 0);
        compartilhados = sharedPref.getInt("cafesCompartilhados", 0);
        Picasso.with(this).load(Uri.parse(cafeteria.getProduct().getImage())).centerCrop().resize(160, 160)
                .into(imageProduct);

        level = sharedPref.getInt("level", 0);

        /*
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        */


        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        // this part is optional
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }

        });

        Button share = (Button) findViewById(R.id
                .share_btn);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pay();
                //shop();
            }
        });

    }

    void pay(){
        final PagSeguroFactory pagseguro = PagSeguroFactory.instance();
        List<PagSeguroItem> shoppingCart = new ArrayList<>();
        shoppingCart.add(pagseguro.item("123", "Café Suspenso", BigDecimal.valueOf(1.00), 1, 300));
        PagSeguroPhone buyerPhone = pagseguro.phone(PagSeguroAreaCode.DDD83, "987914492");
        PagSeguroBuyer buyer = pagseguro.buyer("Lucas Cabral", "23/07/1996", "07642762470", "test@email.com.br", buyerPhone);
        PagSeguroAddress buyerAddress = pagseguro.address("Rua Riachuelo", "1540", "Casa", "Jardim Paulistano", "51030330", "Campina Grande", PagSeguroBrazilianStates.PARAIBA);
        PagSeguroShipping buyerShippingOption = pagseguro.shipping(PagSeguroShippingType.PAC, buyerAddress);
        PagSeguroCheckout checkout = pagseguro.checkout("Ref0001", shoppingCart, buyer, buyerShippingOption);
        // starting payment process
        new PagSeguroPayment(this).pay(checkout.buildCheckoutXml());
    }

    private void shop() {
        toServer();

        new AlertDialog.Builder(this)
                .setMessage("Deseja compartilhar em suas redes sociais?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        shareWithFacebook();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), CafeteriaActivity.class);
        intent.putExtra("cafeteria", cafeteria);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void calculateLevel() {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("cafesCompartilhados", compartilhados + 1);
        compartilhados++;

        switch (level){
            case 0:
                if(compartilhados - resgatados > 4) {
                    editor.putInt("level", level + 1);
                    level++;
                    callFragment("Café com leite");
                }
                break;
            case 1:
                if(compartilhados - resgatados > 9) {
                    editor.putInt("level", level + 1);
                    level++;
                    callFragment("Café grande");
                }
                break;
        }
        editor.apply();
    }

    private void callFragment(String type) {
        Intent intent = new Intent(this, LevelUpFragment.class);
        intent.putExtra("status", "Level UP");
        intent.putExtra("text", "Você subiu de level, você recebeu a insígnia " + type + "! Agora você podera resgatar uma quantidade maior de cafés por dia! Continue compartilhando para subir de level!");
        this.startActivity(intent);
    }


    private void toServer() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Connection.getUrl() + "/api/user/shared_products/" + cafeteria.getId() + "/" + cafeteria.getProduct().getId();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent readerIntent = new Intent(getApplicationContext(), CafeteriaActivity.class);
                        Bundle bundle = new Bundle();
                        cafeteria.setAvailableCoffee(cafeteria.getAvailableCoffee() + 1);
                        readerIntent.putExtra("cafeteria", cafeteria);
                        bundle.putBoolean("qrCode", true);
                        readerIntent.putExtras(bundle);
                        calculateLevel();
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
                params.put("Authorization", token);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void shareWithFacebook() {
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse(Connection.getUrl() + ""))
                    .setQuote("Café Suspenso")
                    .setContentTitle("Café Suspenso")
                    .setContentDescription("Resgatei um café")
                    .build();
            shareDialog.show(linkContent);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            // se foi uma tentativa de pagamento
            if(requestCode==PagSeguroPayment.PAG_SEGURO_REQUEST_CODE){
                // exibir confirmação de cancelamento
                final String msg = getString(R.string.transaction_cancelled);
                AppUtil.showConfirmDialog(this, msg, null);
            }
        } else if (resultCode == RESULT_OK) {
            // se foi uma tentativa de pagamento
            if(requestCode==PagSeguroPayment.PAG_SEGURO_REQUEST_CODE){
                // exibir confirmação de sucesso
                final String msg = getString(R.string.transaction_succeded);
                shop();
                //AppUtil.showConfirmDialog(this, msg, null);
            }
        }
        else if(resultCode == PagSeguroPayment.PAG_SEGURO_REQUEST_CODE){
            switch (data.getIntExtra(PagSeguroPayment.PAG_SEGURO_EXTRA, 0)){
                case PagSeguroPayment.PAG_SEGURO_REQUEST_SUCCESS_CODE:{
                    final String msg =getString(R.string.transaction_succeded);
                    shop();
                    //AppUtil.showConfirmDialog(this,msg,null);
                    break;
                }
                case PagSeguroPayment.PAG_SEGURO_REQUEST_FAILURE_CODE:{
                    final String msg = getString(R.string.transaction_error);
                    AppUtil.showConfirmDialog(this,msg,null);
                    break;
                }
                case PagSeguroPayment.PAG_SEGURO_REQUEST_CANCELLED_CODE:{
                    final String msg = getString(R.string.transaction_cancelled);
                    AppUtil.showConfirmDialog(this,msg,null);
                    break;
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
