package com.example.david.mazerunner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Vittoria extends AppCompatActivity {

    private Button rigioca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vittoria);

        rigioca=findViewById(R.id.rigioca);
        rigioca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Vittoria.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
