package com.cafesuspenso.ufcg.cafesuspenso.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cafesuspenso.ufcg.cafesuspenso.Model.Cafeteria;
import com.cafesuspenso.ufcg.cafesuspenso.Model.Product;
import com.cafesuspenso.ufcg.cafesuspenso.Model.QRCodeData;
import com.cafesuspenso.ufcg.cafesuspenso.R;
import com.cafesuspenso.ufcg.cafesuspenso.Task.DownloadImageTask;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class CafeteriaActivity extends AppCompatActivity {
    private Button shareBtn, redeemBtn;
    private Cafeteria cafeteria;
    private TextView namePlace, descriptionPlace, countCoffee;
    private ImageView imagePlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafeteria);

        shareBtn = (Button) findViewById(R.id.share_btn);

        Intent readerIntent = getIntent();
        Bundle bundle = readerIntent.getExtras();

        namePlace = (TextView) findViewById(R.id.namePlace);
        descriptionPlace = (TextView) findViewById(R.id.descriptionPlace);
        countCoffee = (TextView) findViewById(R.id.countCoffee);
        imagePlace = (ImageView) findViewById(R.id.imagePlace);
        if(bundle.getBoolean("qrCode", false)) {
            String qrCodeData = bundle.getString("cafeteria");
            Gson qrCodeDataGson = new Gson();
            Log.d("QRCODE", qrCodeData);
            QRCodeData qrCodeObject = qrCodeDataGson.fromJson(qrCodeData, QRCodeData.class);
            setInformationCafeteria(qrCodeObject);
        } else {
            cafeteria = (Cafeteria) getIntent().getParcelableExtra("cafeteria");
            namePlace.setText(cafeteria.getPlacename());
            descriptionPlace.setText(cafeteria.getComplement());
            countCoffee.setText(cafeteria.getAvailableCoffee()+"");
            Picasso.with(this).load(Uri.parse(cafeteria.getImagem())).fit().into(imagePlace);
        }

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShareCoffeeActivity.class);
                intent.putExtra("cafeteria", cafeteria);
                Log.d("DEBBUGANDO", "Entrou aqui?");
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        redeemBtn = (Button) findViewById(R.id.redeem_btn);
        if(cafeteria.getAvailableCoffee() == 0) {
            redeemBtn.setEnabled(false);
            redeemBtn.setAlpha(.5f);
        }

        redeemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(redeemBtn.isEnabled()) {
                        Intent i = new Intent(getApplicationContext(), RedeemCoffeeActivity.class);
                        i.putExtra("cafeteria", getIntent().getParcelableExtra("cafeteria"));
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else {
                        Toast.makeText(getApplicationContext(), "Não há café disponível para resgate.", Toast.LENGTH_SHORT).show();
                    }

            }
        });
    }

    @Override
    protected void onResume() {
        cafeteria = (Cafeteria) getIntent().getParcelableExtra("cafeteria");
        if (cafeteria != null) {
            Log.d("CAFETERIAAAAAAA", cafeteria.getAvailableCoffee() + "");
            countCoffee.setText(cafeteria.getAvailableCoffee() + "");
        }
        super.onResume();
    }

    private void setInformationCafeteria(QRCodeData qrCodeObject) {
        namePlace.setText(qrCodeObject.getName());
        // Log.d("QRCODE", qrCodeObject.getPlaceImg());
        Picasso.with(this).load(qrCodeObject.getPlaceImg()).fit().into(imagePlace);
        countCoffee.setText(qrCodeObject.getNumCoffees()+"");

        cafeteria = new Cafeteria();
        cafeteria.setId(qrCodeObject.getId());
        cafeteria.setPlacename(qrCodeObject.getName());
        cafeteria.setAvailableCoffee(qrCodeObject.getNumCoffees());
        cafeteria.setImagem(qrCodeObject.getPlaceImg());
    }

}
