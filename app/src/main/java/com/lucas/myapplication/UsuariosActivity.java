package com.lucas.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UsuariosActivity extends AppCompatActivity {
    private RecyclerView recyclerViewUsuarios;
    private RecyclerView.LayoutManager  layoutManager;

    private UsuarioRecyclerViewAdapter usuarioRecyclerViewAdapter;


    private List<Usuario> listaUsuarios;

    private int posicaoSelecionada = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        setTitle(getString(R.string.controle_de_usuarios));

        recyclerViewUsuarios = findViewById(R.id.listViewUsuarios);

        layoutManager = new LinearLayoutManager(this);

        recyclerViewUsuarios.setLayoutManager(layoutManager);
        recyclerViewUsuarios.setHasFixedSize(true);
        recyclerViewUsuarios.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

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

        usuarioRecyclerViewAdapter = new UsuarioRecyclerViewAdapter(this, listaUsuarios);
        usuarioRecyclerViewAdapter.setOnCreateContextMenu(new UsuarioRecyclerViewAdapter.OnCreateContextMenu() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo, int position, MenuItem.OnMenuItemClickListener menuItemClickListener) {
                getMenuInflater().inflate(R.menu.usuarios_item_selecionado, menu);

                for (int i = 0; i < menu.size(); i++){
                    menu.getItem(i).setOnMenuItemClickListener(menuItemClickListener);
                }
            }
        });

        usuarioRecyclerViewAdapter.setOnContextMenuClickListener(new UsuarioRecyclerViewAdapter.OnContextMenuClickListener() {

            @Override
            public boolean onContextMenuItemClick(MenuItem menuItem, int position) {

                int idMenuItem = menuItem.getItemId();

                if (idMenuItem == R.id.menuItemEditar){
                    editarUsuario(position);
                    return true;
                }else
                if (idMenuItem == R.id.menuItemExcluir){
                    excluirUsuario(position);
                    return true;
                }else{
                    return false;
                }
            }
        });

        usuarioRecyclerViewAdapter.setOnItemClickListener(new UsuarioRecyclerViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                editarUsuario(position);
            }
        });

        recyclerViewUsuarios.setAdapter(usuarioRecyclerViewAdapter);
    }

    public void abrirSobre() {
        Intent intentAbertura = new Intent(this, AutoriaActivity.class);

        startActivity(intentAbertura);
    }
    
    ActivityResultLauncher<Intent> launcherNovoUsuario = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == UsuariosActivity.RESULT_OK) {
                Intent intent = result.getData();

                Bundle bundle = intent.getExtras();

                if (bundle != null) {
                    String nome = bundle.getString(UsuarioActivity.KEY_NOME);
                    int idade = bundle.getInt(UsuarioActivity.KEY_IDADE);
                    boolean diabetico = bundle.getBoolean(UsuarioActivity.KEY_DIABETICO);
                    String objetivo = bundle.getString(UsuarioActivity.KEY_OBJETIVO);
                    int sexo = bundle.getInt(UsuarioActivity.KEY_SEXO);

                    Usuario usuario = new Usuario(nome, idade, diabetico, Objetivo.valueOf(objetivo), sexo);

                    listaUsuarios.add(usuario);

                    usuarioRecyclerViewAdapter.notifyDataSetChanged();
                }
            }
        }
    });
    public void abrirNovousuario(){

        Intent intentAbertura = new Intent(this, UsuarioActivity.class);

        intentAbertura.putExtra(UsuarioActivity.KEY_MODO, UsuarioActivity.MODO_NOVO);

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
            abrirNovousuario();
            return true;
        } else if (idMenuItem == R.id.menuItemSobre) {
            abrirSobre();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    ActivityResultLauncher<Intent> launcherEditarUsuario = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),

            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == UsuariosActivity.RESULT_OK){

                        Intent intent = result.getData();

                        Bundle bundle = intent.getExtras();

                        if (bundle != null){

                            String nome = bundle.getString(UsuarioActivity.KEY_NOME);
                            int idade = bundle.getInt(UsuarioActivity.KEY_IDADE);
                            boolean diabetico = bundle.getBoolean(UsuarioActivity.KEY_DIABETICO);
                            String objetivoTexto = bundle.getString(UsuarioActivity.KEY_OBJETIVO);
                            int sexo = bundle.getInt(UsuarioActivity.KEY_SEXO);

                            Usuario usuario = listaUsuarios.get(posicaoSelecionada);

                            usuario.setNome(nome);
                            usuario.setIdade(idade);
                            usuario.setDiabetico(diabetico);
                            usuario.setSexo(sexo);

                            Objetivo objetivo = Objetivo.valueOf(objetivoTexto);
                            usuario.setObjetivo(objetivo);

                            usuarioRecyclerViewAdapter.notifyDataSetChanged();
                        }
                    }

                    posicaoSelecionada = -1;
                }
            });

    private void editarUsuario(int posicao){

        posicaoSelecionada = posicao;

        Usuario usuario = listaUsuarios.get(posicaoSelecionada);

        Intent intentAbertura = new Intent(this, UsuarioActivity.class);

        intentAbertura.putExtra(UsuarioActivity.KEY_MODO, UsuarioActivity.MODO_EDITAR);

        intentAbertura.putExtra(UsuarioActivity.KEY_NOME, usuario.getNome());
        intentAbertura.putExtra(UsuarioActivity.KEY_IDADE, usuario.getIdade());
        intentAbertura.putExtra(UsuarioActivity.KEY_DIABETICO, usuario.isDiabetico());
        intentAbertura.putExtra(UsuarioActivity.KEY_OBJETIVO, usuario.getObjetivo().toString());
        intentAbertura.putExtra(UsuarioActivity.KEY_SEXO, usuario.getSexo());


        launcherEditarUsuario.launch(intentAbertura);
    }
    private void excluirUsuario(int posicao){
        listaUsuarios.remove(posicao);
        usuarioRecyclerViewAdapter.notifyDataSetChanged();
    }
}