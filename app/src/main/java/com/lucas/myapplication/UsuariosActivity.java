package com.lucas.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsuariosActivity extends AppCompatActivity {
    private RecyclerView recyclerViewUsuarios;
    private RecyclerView.LayoutManager  layoutManager;

    private UsuarioRecyclerViewAdapter usuarioRecyclerViewAdapter;

    private List<Usuario> listaUsuarios;

    private int posicaoSelecionada = -1;

    private ActionMode actionMode;

    private View viewSelecionada;
    private Drawable backgroundDrawable;
    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.usuarios_item_selecionado, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int idMenuItem = item.getItemId();

            if (idMenuItem == R.id.menuItemEditar){
                editarUsuario();
                return true;
            }else
            if (idMenuItem == R.id.menuItemExcluir){
                excluirUsuario();
                mode.finish();
                return true;
            }else{
                return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (viewSelecionada != null) {
                viewSelecionada.setBackground(backgroundDrawable);
            }

            actionMode = null;
            viewSelecionada = null;
            backgroundDrawable = null;

            recyclerViewUsuarios.setEnabled(true);
        }
    };


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

        listaUsuarios = new ArrayList<>();


        usuarioRecyclerViewAdapter = new UsuarioRecyclerViewAdapter(this, listaUsuarios);


        usuarioRecyclerViewAdapter.setOnItemClickListener(new UsuarioRecyclerViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                posicaoSelecionada = position;

                editarUsuario();
            }
        });

        usuarioRecyclerViewAdapter.setOnItemLongClickListener(new UsuarioRecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int position) {
                if (actionMode != null) {
                    return false;
                }

                posicaoSelecionada = position;

                viewSelecionada = view;
                backgroundDrawable = view.getBackground();

                view.setBackgroundColor(Color.LTGRAY);

                recyclerViewUsuarios.setEnabled(false);

                actionMode = startSupportActionMode(actionModeCallback);

                return true;
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

                    Collections.sort(listaUsuarios, Usuario.ordenacaoCrescente);

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

                            Collections.sort(listaUsuarios, Usuario.ordenacaoCrescente);

                            usuarioRecyclerViewAdapter.notifyDataSetChanged();
                        }
                    }

                    posicaoSelecionada = -1;

                    if (actionMode != null) {
                        actionMode.finish();
                    }
                }
            });

    private void editarUsuario(){
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
    private void excluirUsuario() {
        listaUsuarios.remove(posicaoSelecionada);
        usuarioRecyclerViewAdapter.notifyDataSetChanged();
    }
}