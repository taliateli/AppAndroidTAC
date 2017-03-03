package tac.com.appandroidtac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import tac.com.appandroidtac.dao.ConexaoSQLite;
import tac.com.appandroidtac.model.Usuario;

public class CadastroUsuario extends AppCompatActivity {

    public static final String USU_NOME = "postosGyn.nomeApelido";
    public static final String USU_SENHA = "postosGyn.senha";

    Button btCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        final EditText txNome = (EditText) findViewById(R.id.txNome);
        final EditText txSenha = (EditText) findViewById(R.id.txSenha);
        btCadastrar = (Button) findViewById(R.id.btCadastrar);
        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(USU_NOME, txNome.getText().toString());
                data.putExtra(USU_SENHA, txSenha.getText().toString());
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}
