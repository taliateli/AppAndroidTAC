package tac.com.appandroidtac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

import tac.com.appandroidtac.dao.ConexaoSQLite;
import tac.com.appandroidtac.model.Posto;

public class PrecoCombustivel extends AppCompatActivity {

    private ConexaoSQLite conexao;
    private static final NumberFormat FORMAT_CURRENCY = NumberFormat.getCurrencyInstance();

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

        final double latPosto = bundle.getDouble("lat");
        final double lngPosto = bundle.getDouble("lng");

        if (bundle != null) {
            tvNomePosto.setText(bundle.getString("nome"));
            etGasolina.setText(FORMAT_CURRENCY.format(bundle.getDouble("G")));
            etGasolinaAdit.setText(FORMAT_CURRENCY.format(bundle.getDouble("GA")));
            etEtanol.setText(FORMAT_CURRENCY.format(bundle.getDouble("E")));
            etDiesel.setText(FORMAT_CURRENCY.format(bundle.getDouble("D")));
        }

        btRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final double lat = latPosto;
                    final double lng = lngPosto;
                    Posto posto = conexao.consultaPorLatLng(lat, lng);
                    if (posto != null) {
                        NumberFormat nf = NumberFormat.getNumberInstance();
                        posto.setPrecoGasolina(Double.parseDouble(etGasolina.getText().toString().replace("R$", "").replace(",", ".")));
                        posto.setPrecoGasolinaAditivada(Double.parseDouble(etGasolinaAdit.getText().toString().replace("R$", "").replace(",", ".")));
                        posto.setPrecoEtanol(Double.parseDouble(etEtanol.getText().toString().replace("R$", "").replace(",", ".")));
                        posto.setPrecoDiesel(Double.parseDouble(etDiesel.getText().toString().replace("R$", "").replace(",", ".")));
                        conexao.atualizarPrecoCombustivel(posto);
                    }
                    Toast.makeText(PrecoCombustivel.this, "Atualizado com sucesso", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(PrecoCombustivel.this, MapsActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("Erro: ", e.getMessage());
                }
            }
        });

        etGasolina.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                Number numberAmount = 0f;
                try {
                    numberAmount = Float.valueOf(etGasolina.getText().toString());
                } catch (NumberFormatException e1) {
                    e1.printStackTrace();
                    try {
                        numberAmount = FORMAT_CURRENCY.parse(etGasolina.getText().toString());
                    } catch (ParseException e2) {
                        e2.printStackTrace();
                    }
                }
                if (hasFocus) {
                    etGasolina.setText(numberAmount.toString());
                } else {
                    etGasolina.setText(NumberFormat.getCurrencyInstance().format(numberAmount));
                }
            }
        });
        etGasolinaAdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                Number numberAmount = 0f;
                try {
                    numberAmount = Float.valueOf(etGasolinaAdit.getText().toString());
                } catch (NumberFormatException e1) {
                    e1.printStackTrace();
                    try {
                        numberAmount = FORMAT_CURRENCY.parse(etGasolinaAdit.getText().toString());
                    } catch (ParseException e2) {
                        e2.printStackTrace();
                    }
                }
                if (hasFocus) {
                    etGasolinaAdit.setText(numberAmount.toString());
                } else {
                    etGasolinaAdit.setText(NumberFormat.getCurrencyInstance().format(numberAmount));
                }
            }
        });
        etEtanol.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                Number numberAmount = 0f;
                try {
                    numberAmount = Float.valueOf(etEtanol.getText().toString());
                } catch (NumberFormatException e1) {
                    e1.printStackTrace();
                    try {
                        numberAmount = FORMAT_CURRENCY.parse(etEtanol.getText().toString());
                    } catch (ParseException e2) {
                        e2.printStackTrace();
                    }
                }
                if (hasFocus) {
                    etEtanol.setText(numberAmount.toString());
                } else {
                    etEtanol.setText(NumberFormat.getCurrencyInstance().format(numberAmount));
                }
            }
        });
        etDiesel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                Number numberAmount = 0f;
                try {
                    numberAmount = Float.valueOf(etDiesel.getText().toString());
                } catch (NumberFormatException e1) {
                    e1.printStackTrace();
                    try {
                        numberAmount = FORMAT_CURRENCY.parse(etDiesel.getText().toString());
                    } catch (ParseException e2) {
                        e2.printStackTrace();
                    }
                }
                if (hasFocus) {
                    etDiesel.setText(numberAmount.toString());
                } else {
                    etDiesel.setText(NumberFormat.getCurrencyInstance().format(numberAmount));
                }
            }
        });

    }
}
