package tac.com.appandroidtac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tac.com.appandroidtac.dao.ConexaoSQLite;
import tac.com.appandroidtac.model.Usuario;

public class MainActivity extends AppCompatActivity {

    Button btEntrar;
    TextView txCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btEntrar = (Button) findViewById(R.id.btEntrar);
        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Intent intent = new Intent(getApplication(), MapsActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }
        });
        txCadastrar = (TextView) findViewById(R.id.txCadastrar);
        txCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), CadastroUsuario.class);
                startActivity(intent);
            }
        });
    }
}
