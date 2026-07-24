package com.lucas.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNomeCadastro, editTextIdadeCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNomeCadastro = findViewById(R.id.editTextNomeCadastro);
        editTextIdadeCadastro = findViewById(R.id.editTextIdadeCadastro);
    }

    public void salvar(View view) {
        String nome = editTextNomeCadastro.getText().toString();
        String idade = editTextIdadeCadastro.getText().toString();

        if (nome == null || nome.trim().isEmpty()) {
            Toast.makeText(this, R.string.preencha_o_nome_corretamente, Toast.LENGTH_LONG).show();

            editTextNomeCadastro.requestFocus();
            return;
        }

        if (idade == null || idade.trim().isEmpty()) {
            Toast.makeText(this, R.string.preencha_a_idade_corretamente, Toast.LENGTH_LONG).show();

            editTextNomeCadastro.requestFocus();
            return;
        }

        Toast.makeText(this,
                        getString(R.string.nome_valor) + nome + "\n" +
                             getString(R.string.idade_valor) + idade + "\n",
                        Toast.LENGTH_LONG).show();
    }

    public void limpar(View view) {
        editTextNomeCadastro.setText(null);
        editTextIdadeCadastro.setText(null);

        editTextNomeCadastro.requestFocus();

        Toast.makeText(this, R.string.campos_apagados, Toast.LENGTH_LONG).show();
    }

}