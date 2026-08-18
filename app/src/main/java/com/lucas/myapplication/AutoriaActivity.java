package com.lucas.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AutoriaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autoria);

        setTitle(R.string.sobre);
    }

    public void abrirSiteAutoria(View view) {
        abrirSite("www.linkedin.com/in/lucas-coelho-dos-santos-1b5888109");
    }

    private void abrirSite(String url) {
        Intent intentAbertura = new Intent(Intent.ACTION_VIEW);

        intentAbertura.setData(Uri.parse(url));

        if (intentAbertura.resolveActivity(getPackageManager()) != null) {
            startActivity(intentAbertura);
        } else {
            Toast.makeText(this, R.string.nenhum_aplicativo_para_abrir_web, Toast.LENGTH_LONG).show();
        }
    }

    public void enviarEmailAutor(View view) {
        enviarEmail(new String[]{"lsantos.1995@alunos.utfpr.edu.br"}, getString(R.string.contato_pelo_aplicativo));
    }

    private void enviarEmail(String[] enderecos, String assunto) {
        Intent intentAbertura = new Intent(Intent.ACTION_SENDTO);

        intentAbertura.setData(Uri.parse("mailto"));
        intentAbertura.putExtra(Intent.EXTRA_EMAIL, enderecos);
        intentAbertura.putExtra(Intent.EXTRA_SUBJECT, assunto);

        if (intentAbertura.resolveActivity(getPackageManager()) != null) {
            startActivity(intentAbertura);
        } else {
            Toast.makeText(this, R.string.nenhum_aplicativo_para_enviar_email, Toast.LENGTH_LONG).show();
        }
    }

    /*@Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idMenuItem = item.getItemId();

        if (idMenuItem == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }*/
}