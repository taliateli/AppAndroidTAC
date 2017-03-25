package tac.com.appandroidtac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.List;

import tac.com.appandroidtac.dao.ConexaoSQLite;
import tac.com.appandroidtac.model.Posto;

public class PrecoCombustivel extends AppCompatActivity {

    private ConexaoSQLite conexao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preco_combustivel);

        conexao = new ConexaoSQLite(this);

        final EditText etGasolina = (EditText) findViewById(R.id.etGasolina);
        final EditText etGasolinaAdit = (EditText) findViewById(R.id.etGasolinaAdit);
        final EditText etEtanol = (EditText) findViewById(R.id.etEtanol);
        final EditText etDiesel = (EditText) findViewById(R.id.etDiesel);
        Button btRegistrar = (Button) findViewById(R.id.btRegistrar);

        TextView tvNomePosto = (TextView) findViewById(R.id.tvNomePosto);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        final int idPosto = bundle.getInt("id");

        if (bundle != null) {
            tvNomePosto.setText(bundle.getString("nome"));
            etGasolina.setText(String.valueOf(bundle.getDouble("G")));
            etGasolinaAdit.setText(String.valueOf(bundle.getDouble("GA")));
            etEtanol.setText(String.valueOf(bundle.getDouble("E")));
            etDiesel.setText(String.valueOf(bundle.getDouble("D")));
        }

        btRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int id = idPosto;
                Posto posto = conexao.consultarPorID(id);
                if (posto != null) {
                    posto.setPrecoGasolina(Double.parseDouble(etGasolina.getText().toString()));
                    posto.setPrecoGasolinaAditivada(Double.parseDouble(etGasolinaAdit.getText().toString()));
                    posto.setPrecoEtanol(Double.parseDouble(etEtanol.getText().toString()));
                    posto.setPrecoDiesel(Double.parseDouble(etDiesel.getText().toString()));
                    conexao.atualizarPrecoCombustivel(posto);
                }
                List<Posto> postos = conexao.getPostos();
                Toast.makeText(PrecoCombustivel.this, "Atualizado com sucesso", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(PrecoCombustivel.this, MapsActivity.class);
                startActivity(intent);
            }
        });

    }
}
