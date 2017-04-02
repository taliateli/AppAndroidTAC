package tac.com.appandroidtac;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import tac.com.appandroidtac.dao.ConexaoSQLite;
import tac.com.appandroidtac.model.Posto;

public class MainActivity extends Activity {

    private ConexaoSQLite conexao;
    private static final int ADD_ACTION_CODE = 1;

    Button btEntrar;
    TextView tvCadastrarPosto;
    ImageView imgLogo;
    TextView txMarquee;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgLogo = (ImageView) findViewById(R.id.imgLogo);
        imgLogo.setImageResource(R.raw.logotipo);
        txMarquee = (TextView) findViewById(R.id.txMarquee);
        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.combustiveis, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position)+" Selecionado", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setTranslation();

        conexao = new ConexaoSQLite(this);

        btEntrar = (Button) findViewById(R.id.btEntrar);
        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getApplication(), MapsActivity.class);
                    Bundle param = new Bundle();
                    param.putString("combustivel", spinner.getSelectedItem().toString());
                    intent.putExtras(param);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }
        });
        tvCadastrarPosto = (TextView) findViewById(R.id.tvCadastrarPosto);
        tvCadastrarPosto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastroPosto.class);
                startActivityForResult(intent, ADD_ACTION_CODE);

            }
        });


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
            try {
                conexao.cadastrarPosto(posto);
                Toast.makeText(MainActivity.this, "Cadastro efetuado com sucesso", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Log.e("Erro: ", e.getMessage());
            }
        }
    }

    private void setTranslation() {
        TranslateAnimation animation = new TranslateAnimation(
                TranslateAnimation.ABSOLUTE, 1.0f * getScreenSize()[0],
                TranslateAnimation.ABSOLUTE, -1.0f * getScreenSize()[0],
                TranslateAnimation.ABSOLUTE, 0.0f,
                TranslateAnimation.ABSOLUTE, 0.0f);
        animation.setDuration(5000);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.ABSOLUTE);

        txMarquee.startAnimation(animation);
    }

    public int[] getScreenSize() {
        Point size = new Point();
        WindowManager w = getWindowManager();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            w.getDefaultDisplay().getSize(size);
            return new int[]{size.x, size.y};
        } else {
            Display d = w.getDefaultDisplay();
            //noinspection deprecation
            return new int[]{d.getWidth(), d.getHeight()};
        }
    }
}
