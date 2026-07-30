package com.lucas.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class UsuariosActivity extends AppCompatActivity {

    private ListView listViewUsuarios;

    private List<Usuario> listaUsuarios;

    private UsuarioAdapter usuarioAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

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
        String[] usuarios_nomes = getResources().getStringArray(R.array.usuarios_nome);
        int[] usuarios_idades = getResources().getIntArray(R.array.usuarios_idade);
        int[] usuarios_diabeticos = getResources().getIntArray(R.array.usuarios_diabetico);
        int[] usuarios_objetivos = getResources().getIntArray(R.array.usuarios_objetivo);
        int[] usuarios_sexos = getResources().getIntArray(R.array.usuarios_sexo);

        listaUsuarios = new ArrayList<>();

        Usuario usuario;
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
        }

        usuarioAdapter = new UsuarioAdapter(this, listaUsuarios);

        listViewUsuarios.setAdapter(usuarioAdapter);
    }
}