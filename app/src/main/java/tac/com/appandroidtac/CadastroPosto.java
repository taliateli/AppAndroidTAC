package tac.com.appandroidtac;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CadastroPosto extends Activity {

    public static final String EXTRA_NOME = "posto.nome";
    public static final String EXTRA_LATITUDE = "posto.latitude";
    public static final String EXTRA_LONGITUDE = "posto.longitude";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_posto);

        final EditText txNome = (EditText) findViewById(R.id.txNome);
        final EditText txLatitude = (EditText) findViewById(R.id.txLatitude);
        final EditText txLongitude = (EditText) findViewById(R.id.txLongitude);
        Button btSalvar = (Button) findViewById(R.id.btSalvar);

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(EXTRA_NOME, txNome.getText().toString());
                data.putExtra(EXTRA_LATITUDE, Double.parseDouble(txLatitude.getText().toString()));
                data.putExtra(EXTRA_LONGITUDE, Double.parseDouble(txLongitude.getText().toString()));
                setResult(RESULT_OK, data);
                finish();

            }
        });
    }

}
