package com.lucas.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class UsuarioAdapter extends BaseAdapter {

    private List<Usuario> listaUsuarios;
    private Context context;

    private static class UsuarioHolder {
        public TextView textViewValorNome;
        public TextView textViewValorIdade;
        public TextView textViewValorDiabetico;
        public TextView textViewValorObjetivo;
        public TextView textViewValorSexo;
    }

    public UsuarioAdapter(Context context, List<Usuario> listaUsuario) {
        this.listaUsuarios = listaUsuario;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listaUsuarios.size();
    }

    @Override
    public Object getItem(int i) {
        return listaUsuarios.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UsuarioHolder holder;

        if (convertView == null) {
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.linha_lista_usuarios, parent, false);

            holder = new UsuarioHolder();

            holder.textViewValorNome = convertView.findViewById(R.id.textViewValorNome);
            holder.textViewValorIdade = convertView.findViewById(R.id.textViewValorIdade);
            holder.textViewValorDiabetico = convertView.findViewById(R.id.textViewValorDiabetico);
            holder.textViewValorObjetivo = convertView.findViewById(R.id.textViewValorObjetivo);
            holder.textViewValorSexo = convertView.findViewById(R.id.textViewValorSexo);

            convertView.setTag(holder);
        } else {
            holder = (UsuarioHolder) convertView.getTag();
        }

        Usuario usuario = listaUsuarios.get(position);

        holder.textViewValorNome.setText(usuario.getNome());
        holder.textViewValorIdade.setText(String.valueOf(usuario.getIdade()));

        if (usuario.isDiabetico()) {
            holder.textViewValorDiabetico.setText(R.string.diabetico_true);
        } else {
            holder.textViewValorDiabetico.setText(R.string.diabetico_false);
        }

        switch (usuario.getObjetivo()) {
            case Perder_peso:
                holder.textViewValorObjetivo.setText(R.string.perder_peso);
                break;

            case Ganhar_musculo:
                holder.textViewValorObjetivo.setText(R.string.ganhar_musculo);
                break;

            case Ambos:
                holder.textViewValorObjetivo.setText(R.string.ambos_cadastro);
                break;
        }

        switch (usuario.getSexo()) {
            case prefiro_nao_informar:
                holder.textViewValorSexo.setText(R.string.nao_informar);
                break;

            case masculino:
                holder.textViewValorSexo.setText(R.string.masculino);
                break;

            case feminino:
                holder.textViewValorSexo.setText(R.string.feminino);
                break;
        }

        return convertView;
    }
}
