package com.example.mainactivity.lojista.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mainactivity.R;
import com.example.mainactivity.model.ItemPedidoDetalhado;
import java.util.List;

public class DetalhesPedidoAdapter extends RecyclerView.Adapter<DetalhesPedidoAdapter.DetalheViewHolder> {

    private List<ItemPedidoDetalhado> listaItens;

    public DetalhesPedidoAdapter(List<ItemPedidoDetalhado> listaItens) {
        this.listaItens = listaItens;
    }

    @NonNull
    @Override
    public DetalheViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produto_detalhe, parent, false);
        return new DetalheViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetalheViewHolder holder, int position) {
        ItemPedidoDetalhado item = listaItens.get(position);

        holder.txtNome.setText(item.nomeProduto);
        holder.txtQtdPreco.setText(item.quantidade + " un. x " + String.format("R$ %.2f", item.precoProduto));

        // Calcula o subtotal -Qtd x Preço Congelado)
        double subtotal = item.quantidade * item.precoProduto;
        holder.txtSubtotal.setText(String.format("R$ %.2f", subtotal));

        // Tenta carregar a imagem real
        if (item.imagemUri != null && !item.imagemUri.isEmpty()) {
            try {
                holder.imgProduto.setImageURI(Uri.parse(item.imagemUri));
            } catch (Exception e) {
                holder.imgProduto.setImageResource(android.R.drawable.ic_menu_camera);
            }
        }
    }

    @Override
    public int getItemCount() {
        return listaItens.size();
    }

    static class DetalheViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduto;
        TextView txtNome, txtQtdPreco, txtSubtotal;

        public DetalheViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduto = itemView.findViewById(R.id.imgDetalheProduto);
            txtNome = itemView.findViewById(R.id.txtDetalheNomeProduto);
            txtQtdPreco = itemView.findViewById(R.id.txtDetalheQtdPreco);
            txtSubtotal = itemView.findViewById(R.id.txtDetalheSubtotal);
        }
    }
}