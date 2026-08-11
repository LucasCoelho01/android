package com.lucas.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_NOME = "KEY_NOME";
    public static final String KEY_IDADE = "KEY_IDADE";
    public static final String KEY_DIABETICO = "KEY_DIABETICO";
    public static final String KEY_OBJETIVO = "KEY_OBJETIVO";
    public static final String KEY_SEXO = "KEY_SEXO";
    private EditText editTextNomeCadastro, editTextIdadeCadastro;
    private CheckBox checkBoxDiabetico;
    private RadioGroup radioGroupObjetivo;
    private Spinner spinnerSexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getString(R.string.novo_usuario));

        editTextNomeCadastro = findViewById(R.id.editTextNomeCadastro);
        editTextIdadeCadastro = findViewById(R.id.editTextIdadeCadastro);
        checkBoxDiabetico = findViewById(R.id.checkBoxDiabeticoCadastro);
        radioGroupObjetivo = findViewById(R.id.radioGroupObjetivo);
        spinnerSexo = findViewById(R.id.spinnerSexo);
    }

    public void salvar() {
        String nome = editTextNomeCadastro.getText().toString();
        String idade = editTextIdadeCadastro.getText().toString();

        if (nome.trim().isEmpty()) {
            Toast.makeText(this, R.string.preencha_o_nome_corretamente, Toast.LENGTH_LONG).show();

            editTextNomeCadastro.requestFocus();
            return;
        }

        if (idade.trim().isEmpty()) {
            Toast.makeText(this, R.string.preencha_a_idade_corretamente, Toast.LENGTH_LONG).show();

            editTextNomeCadastro.requestFocus();
            return;
        }

        int radioGroupId = radioGroupObjetivo.getCheckedRadioButtonId();

        Objetivo objetivo;

        if (radioGroupId == R.id.radioButtonPerderPeso) {
            objetivo = Objetivo.Perder_peso;
        } else if (radioGroupId == R.id.radioButtonGanharMusculo) {
            objetivo =Objetivo.Ganhar_musculo;
        } else if (radioGroupId == R.id.radioButtonAmbos) {
            objetivo = Objetivo.Ambos;
        } else {
            Toast.makeText(this, R.string.faltou_selecionar_o_sexo, Toast.LENGTH_LONG).show();
            return;
        }

        boolean diabetico = checkBoxDiabetico.isChecked();

        int sexo = spinnerSexo.getSelectedItemPosition();

        Intent intentResposta = new Intent();

        intentResposta.putExtra(KEY_NOME, nome);
        intentResposta.putExtra(KEY_IDADE, Integer.parseInt(idade));
        intentResposta.putExtra(KEY_DIABETICO, diabetico);
        intentResposta.putExtra(KEY_OBJETIVO, objetivo.toString());
        intentResposta.putExtra(KEY_SEXO, sexo);

        setResult(MainActivity.RESULT_OK, intentResposta);

        finish();
    }

    public void limpar() {
        editTextNomeCadastro.setText(null);
        editTextIdadeCadastro.setText(null);
        checkBoxDiabetico.setChecked(false);
        radioGroupObjetivo.clearCheck();
        spinnerSexo.setSelection(0);

        editTextNomeCadastro.requestFocus();

        Toast.makeText(this, R.string.campos_apagados, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.usuario_opcoes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int idMenuItem = item.getItemId();

        if (idMenuItem == R.id.menuItemSalvar) {
            salvar();
            return true;
        } else if (idMenuItem == R.id.menuItemLimpar) {
            limpar();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}