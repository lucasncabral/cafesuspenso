package com.cafesuspenso.ufcg.cafesuspenso.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cafesuspenso.ufcg.cafesuspenso.Model.Cafeteria;
import com.cafesuspenso.ufcg.cafesuspenso.R;
import com.cafesuspenso.ufcg.cafesuspenso.Task.DownloadImageTask;
import com.google.android.gms.maps.model.LatLng;

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

        cafeteria = getIntent().getParcelableExtra("cafeteria");
        shareBtn = (Button) findViewById(R.id.share_btn);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ShareCoffeeActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        redeemBtn = (Button) findViewById(R.id.redeem_btn);
        redeemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cafeteria.getAvailableCoffee() > 0) {
                    Intent i = new Intent(getApplicationContext(), RedeemCoffeeActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    Toast.makeText(getApplication(), "Não tem café pra resgatar", Toast.LENGTH_SHORT).show();
                }
            }
        });


        namePlace = (TextView) findViewById(R.id.namePlace);
        descriptionPlace = (TextView) findViewById(R.id.descriptionPlace);
        countCoffee = (TextView) findViewById(R.id.countCoffee);
        imagePlace = (ImageView) findViewById(R.id.imagePlace);

        namePlace.setText(cafeteria.getPlacename());
        descriptionPlace.setText(cafeteria.getComplement());
        countCoffee.setText(cafeteria.getAvailableCoffee()+"");
        new DownloadImageTask(imagePlace).execute(cafeteria.getImagem());
    }
}
