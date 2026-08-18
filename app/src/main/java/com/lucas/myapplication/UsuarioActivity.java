package com.lucas.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class UsuarioActivity extends AppCompatActivity {

    public static final String KEY_NOME = "KEY_NOME";
    public static final String KEY_IDADE = "KEY_IDADE";
    public static final String KEY_DIABETICO = "KEY_DIABETICO";
    public static final String KEY_OBJETIVO = "KEY_OBJETIVO";
    public static final String KEY_SEXO = "KEY_SEXO";
    public static final String KEY_MODO     = "MODO";

    public static final int MODO_NOVO   = 0;
    public static final int MODO_EDITAR = 1;

    private EditText editTextNomeCadastro, editTextIdadeCadastro;
    private CheckBox checkBoxDiabetico;
    private RadioGroup radioGroupObjetivo;
    private Spinner spinnerSexo;

    private RadioButton radioButtonGanharMusculo, radioButtonPerderPeso, radioButtonAmbos;

    private int modo;

    private Usuario usuarioOriginal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNomeCadastro = findViewById(R.id.editTextNomeCadastro);
        editTextIdadeCadastro = findViewById(R.id.editTextIdadeCadastro);
        checkBoxDiabetico = findViewById(R.id.checkBoxDiabeticoCadastro);
        radioGroupObjetivo = findViewById(R.id.radioGroupObjetivo);
        spinnerSexo = findViewById(R.id.spinnerSexo);
        radioButtonGanharMusculo = findViewById(R.id.radioButtonGanharMusculo);
        radioButtonPerderPeso = findViewById(R.id.radioButtonPerderPeso);
        radioButtonAmbos = findViewById(R.id.radioButtonAmbos);

        Intent intentAbertura = getIntent();

        Bundle bundle = intentAbertura.getExtras();

        if (bundle != null){

            modo = bundle.getInt(KEY_MODO);

            if (modo == MODO_NOVO){
                setTitle(getString(R.string.novo_usuario));
            }else{
                setTitle(getString(R.string.editar));

                String nome          = bundle.getString(UsuarioActivity.KEY_NOME);
                int idade            = bundle.getInt(UsuarioActivity.KEY_IDADE);
                boolean diabetico     = bundle.getBoolean(UsuarioActivity.KEY_DIABETICO);
                String objetivoTexto = bundle.getString(UsuarioActivity.KEY_OBJETIVO);
                int sexo             = bundle.getInt(UsuarioActivity.KEY_SEXO);


                Objetivo objetivo = Objetivo.valueOf(objetivoTexto);

                usuarioOriginal = new Usuario(nome, idade, diabetico, objetivo, sexo);

                editTextNomeCadastro.setText(nome);
                editTextIdadeCadastro.setText(String.valueOf(idade));
                checkBoxDiabetico.setChecked(diabetico);
                spinnerSexo.setSelection(sexo);

                if (objetivo == Objetivo.Ganhar_musculo){
                    radioButtonGanharMusculo.setChecked(true);
                }else
                if (objetivo == Objetivo.Perder_peso){
                    radioButtonPerderPeso.setChecked(true);
                }else
                if (objetivo == Objetivo.Ambos){
                    radioButtonAmbos.setChecked(true);
                }
            }
        }
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

    public void salvar() {
        String nome = editTextNomeCadastro.getText().toString();
        String idadeString = editTextIdadeCadastro.getText().toString();

        if (nome.trim().isEmpty()) {
            Toast.makeText(this, R.string.preencha_o_nome_corretamente, Toast.LENGTH_LONG).show();

            editTextNomeCadastro.requestFocus();
            return;
        }

        if (idadeString.trim().isEmpty()) {
            Toast.makeText(this, R.string.preencha_a_idade_corretamente, Toast.LENGTH_LONG).show();

            editTextNomeCadastro.requestFocus();
            return;
        }

        int idade = Integer.parseInt(idadeString);

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

        if (modo == MODO_EDITAR
                && nome.equalsIgnoreCase(usuarioOriginal.getNome())
                && idade == usuarioOriginal.getIdade()
                && diabetico == usuarioOriginal.isDiabetico()
                && objetivo == usuarioOriginal.getObjetivo()
                && sexo == usuarioOriginal.getSexo()) {

            setResult(UsuarioActivity.RESULT_CANCELED);

            finish();
            return;
        }


        Intent intentResposta = new Intent();

        intentResposta.putExtra(KEY_NOME, nome);
        intentResposta.putExtra(KEY_IDADE, idade);
        intentResposta.putExtra(KEY_DIABETICO, diabetico);
        intentResposta.putExtra(KEY_OBJETIVO, objetivo.toString());
        intentResposta.putExtra(KEY_SEXO, sexo);

        setResult(UsuarioActivity.RESULT_OK, intentResposta);

        finish();
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