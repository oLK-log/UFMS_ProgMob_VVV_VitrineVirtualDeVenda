package com.example.mainactivity.cliente.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mainactivity.R;
import com.example.mainactivity.database.AppDatabase;
import com.example.mainactivity.model.ItemPedido;
import com.example.mainactivity.model.Produto;
import java.util.List;
import com.example.mainactivity.model.ItemPedidoDetalhado;
public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder> {
    private List<ItemPedidoDetalhado> listaItens;
    private OnItemRemovidoListener listener;

    //escutando mudanca valor
    public interface OnItemRemovidoListener {
        void onRemover(ItemPedidoDetalhado item);
    }

    public PedidoAdapter(List<ItemPedidoDetalhado> listaItens, OnItemRemovidoListener listener) {
        this.listaItens = listaItens;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido_lista, parent, false);
        return new PedidoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
        ItemPedidoDetalhado item = listaItens.get(position);

        holder.txtNome.setText(item.nomeProduto);
        holder.txtQtdPreco.setText(item.quantidade + " un. x "+ String.format("R$ %.2f", item.precoProduto));

        if(item.imagemUri != null && !item.imagemUri.isEmpty()){
            try{
                holder.img.setImageURI(Uri.parse(item.imagemUri));
            } catch (SecurityException e){
                holder.img.setImageResource(android.R.drawable.ic_menu_camera);
            }
        }else{
            holder.img.setImageResource(android.R.drawable.ic_menu_camera);
        }

        holder.btnRemover.setOnClickListener(v -> listener.onRemover(item));
    }

    @Override
    public int getItemCount(){
        return listaItens.size();
    }

    static class PedidoViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtNome, txtQtdPreco;
        ImageButton btnRemover;

        public PedidoViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgItemPedido);
            txtNome = itemView.findViewById(R.id.txtItemNome);
            txtQtdPreco = itemView.findViewById(R.id.txtItemQtdPreco);
            btnRemover = itemView.findViewById(R.id.btnRemoverItemPedido);
        }
    }
}
