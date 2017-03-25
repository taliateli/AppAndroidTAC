package tac.com.appandroidtac;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import tac.com.appandroidtac.dao.ConexaoSQLite;
import tac.com.appandroidtac.model.Posto;

import static android.R.attr.data;

public class MainActivity extends ListActivity {

    private ConexaoSQLite conexao;
    private PostoArrayAdapter adapter;
    private static final int ADD_ACTION_CODE = 1;

    Button btEntrar;
    Button btCadastrarPosto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conexao = new ConexaoSQLite(this);

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
        btCadastrarPosto = (Button) findViewById(R.id.btCadastrarPosto);
        btCadastrarPosto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastroPosto.class);
                startActivityForResult(intent, ADD_ACTION_CODE);
            }
        });
        adapter = new PostoArrayAdapter(this, conexao.getPostos());
        setListAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ADD_ACTION_CODE) {
            Posto posto = new Posto();
            posto.setNome(data.getExtras().getString(CadastroPosto.EXTRA_NOME));
            posto.setLatitude(data.getExtras().getDouble(
                    CadastroPosto.EXTRA_LATITUDE));
            posto.setLongitude(data.getExtras().getDouble(
                    CadastroPosto.EXTRA_LONGITUDE));
            conexao.cadastrarPosto(posto);
            adapter.add(posto);
        }

    }
}
