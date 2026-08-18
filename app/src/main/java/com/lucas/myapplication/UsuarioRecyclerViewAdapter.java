package com.lucas.myapplication;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UsuarioRecyclerViewAdapter extends RecyclerView.Adapter<UsuarioRecyclerViewAdapter.UsuarioHolder> {

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    private OnCreateContextMenu        onCreateContextMenu;
    private OnContextMenuClickListener onContextMenuClickListener;

    private List<Usuario> listaUsuarios;
    private Context context;

    private String[] objetivos;

    interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }

    interface OnCreateContextMenu {
        void onCreateContextMenu(ContextMenu menu,
                                 View v,
                                 ContextMenu.ContextMenuInfo menuInfo,
                                 int position,
                                 MenuItem.OnMenuItemClickListener menuItemClickListener);
    }

    interface OnContextMenuClickListener {
        boolean onContextMenuItemClick(MenuItem menuItem, int position);
    }

    public class UsuarioHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener,
            View.OnCreateContextMenuListener {

        public TextView textViewValorNome;
        public TextView textViewValorIdade;
        public TextView textViewValorDiabetico;
        public TextView textViewValorObjetivo;
        public TextView textViewValorSexo;

        public UsuarioHolder(@NonNull View itemView) {
            super(itemView);

            textViewValorNome = itemView.findViewById(R.id.textViewValorNome);
            textViewValorIdade = itemView.findViewById(R.id.textViewValorIdade);
            textViewValorDiabetico = itemView.findViewById(R.id.textViewValorDiabetico);
            textViewValorObjetivo = itemView.findViewById(R.id.textViewValorObjetivo);
            textViewValorSexo = itemView.findViewById(R.id.textViewValorSexo);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {

            if (onItemClickListener != null){
                onItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {

            if (onItemLongClickListener != null){
                onItemLongClickListener.onItemLongClick(v, getAdapterPosition());
                return true;
            }
            return false;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            if (onCreateContextMenu != null){
                onCreateContextMenu.onCreateContextMenu(menu,
                        v,
                        menuInfo,
                        getAdapterPosition(),
                        onMenuItemClickListener);
            }
        }

        MenuItem.OnMenuItemClickListener onMenuItemClickListener = new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {

                if (onContextMenuClickListener != null){
                    onContextMenuClickListener.onContextMenuItemClick(item, getAdapterPosition());
                    return true;
                }
                return false;
            }
        };
    }

    public UsuarioRecyclerViewAdapter(Context context, List<Usuario> listaUsuario) {
        this.listaUsuarios = listaUsuario;
        this.context = context;

        objetivos = context.getResources().getStringArray(R.array.objetivo);
    }

    @NonNull
    @Override
    public UsuarioHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.linha_lista_usuarios, parent, false);

        return new UsuarioHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioHolder holder, int position) {
        Usuario usuario = listaUsuarios.get(position);

        holder.textViewValorNome.setText(usuario.getNome());
        holder.textViewValorIdade.setText(String.valueOf(usuario.getIdade()));

        if (usuario.isDiabetico()) {
            holder.textViewValorDiabetico.setText(R.string.diabetico_true);
        } else {
            holder.textViewValorDiabetico.setText(R.string.diabetico_false);
        }

        holder.textViewValorSexo.setText(objetivos[usuario.getSexo()]);

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
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }



    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public OnCreateContextMenu getOnCreateContextMenu() {
        return onCreateContextMenu;
    }

    public void setOnCreateContextMenu(OnCreateContextMenu onCreateContextMenu) {
        this.onCreateContextMenu = onCreateContextMenu;
    }

    public OnContextMenuClickListener getOnContextMenuClickListener() {
        return onContextMenuClickListener;
    }

    public void setOnContextMenuClickListener(OnContextMenuClickListener onContextMenuClickListener) {
        this.onContextMenuClickListener = onContextMenuClickListener;
    }
}
