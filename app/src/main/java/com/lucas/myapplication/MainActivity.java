package com.lucas.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNomeCadastro, editTextIdadeCadastro;
    private CheckBox checkBoxDiabetico;
    private RadioGroup radioGroupObjetivo;
    private Spinner spinnerSexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNomeCadastro = findViewById(R.id.editTextNomeCadastro);
        editTextIdadeCadastro = findViewById(R.id.editTextIdadeCadastro);
        checkBoxDiabetico = findViewById(R.id.checkBoxDiabeticoCadastro);
        radioGroupObjetivo = findViewById(R.id.radioGroupObjetivo);
        spinnerSexo = findViewById(R.id.spinnerSexo);
    }

    public void salvar(View view) {
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

        String objetivo;

        if (radioGroupId == R.id.radioButtonPerderPeso) {
            objetivo = getString(R.string.perder_peso);
        } else if (radioGroupId == R.id.radioButtonGanharMusculo) {
            objetivo = getString(R.string.ganhar_musculo);
        } else if (radioGroupId == R.id.radioButtonAmbos) {
            objetivo = getString(R.string.ambos_cadastro);
        } else {
            Toast.makeText(this, R.string.faltou_selecionar_o_sexo, Toast.LENGTH_LONG).show();
            return;
        }

        boolean diabetico = checkBoxDiabetico.isChecked();

        String sexo = (String) spinnerSexo.getSelectedItem();

        Toast.makeText(this,
                        getString(R.string.nome_valor) + nome + "\n" +
                             getString(R.string.idade_valor) + idade + "\n" +
                             (diabetico ? getString(R.string.diabetico_true): getString(R.string.diabetico_false)) + "\n" +
                             getString(R.string.objetivo) + objetivo + "\n" +
                             getString(R.string.sexo_valor) + sexo,
                        Toast.LENGTH_LONG).show();
    }

    public void limpar(View view) {
        editTextNomeCadastro.setText(null);
        editTextIdadeCadastro.setText(null);
        checkBoxDiabetico.setChecked(false);
        radioGroupObjetivo.clearCheck();
        spinnerSexo.setSelection(0);

        editTextNomeCadastro.requestFocus();

        Toast.makeText(this, R.string.campos_apagados, Toast.LENGTH_LONG).show();
    }
}