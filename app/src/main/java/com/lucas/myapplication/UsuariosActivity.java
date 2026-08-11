package com.lucas.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class UsuariosActivity extends AppCompatActivity {

    private ListView listViewUsuarios;

    private List<Usuario> listaUsuarios;

    private UsuarioAdapter usuarioAdapter;
    
    ActivityResultLauncher<Intent> launcherNovoUsuario = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == UsuariosActivity.RESULT_OK) {
                Intent intent = result.getData();

                Bundle bundle = intent.getExtras();

                if (bundle != null) {
                    String nome = bundle.getString(MainActivity.KEY_NOME);
                    int idade = bundle.getInt(MainActivity.KEY_IDADE);
                    boolean diabetico = bundle.getBoolean(MainActivity.KEY_DIABETICO);
                    String objetivo = bundle.getString(MainActivity.KEY_OBJETIVO);
                    int sexo = bundle.getInt(MainActivity.KEY_SEXO);

                    Usuario usuario = new Usuario(nome, idade, diabetico, Objetivo.valueOf(objetivo), Sexo.values()[sexo]);

                    listaUsuarios.add(usuario);

                    usuarioAdapter.notifyDataSetChanged();
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        setTitle(getString(R.string.controle_de_usuarios));

        listViewUsuarios = findViewById(R.id.listViewUsuarios);

        listViewUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Usuario usuario = (Usuario) listViewUsuarios.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(),
                       getString(R.string.usuario_clicado) + usuario.getNome(),
                        Toast.LENGTH_LONG).show();

            }
        });

        popularListaUsuarios();
    }

    private void popularListaUsuarios() {
        /*String[] usuarios_nomes = getResources().getStringArray(R.array.usuarios_nome);
        int[] usuarios_idades = getResources().getIntArray(R.array.usuarios_idade);
        int[] usuarios_diabeticos = getResources().getIntArray(R.array.usuarios_diabetico);
        int[] usuarios_objetivos = getResources().getIntArray(R.array.usuarios_objetivo);
        int[] usuarios_sexos = getResources().getIntArray(R.array.usuarios_sexo);*/

        listaUsuarios = new ArrayList<>();

        /*Usuario usuario;
        boolean diabetico;
        Objetivo objetivo;
        Sexo sexo;

        Objetivo[] objetivos = Objetivo.values();
        Sexo[] sexos = Sexo.values();

        for (int i = 0; i < usuarios_nomes.length; i++) {
            diabetico = (usuarios_diabeticos[i] == 1);

            objetivo = objetivos[usuarios_objetivos[i]];
            sexo = sexos[usuarios_sexos[i]];

            usuario= new Usuario(usuarios_nomes[i],
                                  usuarios_idades[i],
                                  diabetico,
                                  objetivo,
                                   sexo);

            listaUsuarios.add(usuario);
        }*/

        usuarioAdapter = new UsuarioAdapter(this, listaUsuarios);

        listViewUsuarios.setAdapter(usuarioAdapter);
    }

    public void abrirSobre() {
        Intent intentAbertura = new Intent(this, AutoriaActivity.class);

        startActivity(intentAbertura);
    }

    public void adicionarUsuario() {
        Intent intentAbertura = new Intent(this, MainActivity.class);

        launcherNovoUsuario.launch(intentAbertura);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.usuarios_opcoes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int idMenuItem = item.getItemId();

        if (idMenuItem == R.id.menuItemAdicionar) {
            adicionarUsuario();
            return true;
        } else if (idMenuItem == R.id.menuItemSobre) {
            abrirSobre();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}