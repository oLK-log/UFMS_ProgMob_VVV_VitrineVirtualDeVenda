package com.example.mainactivity.cliente.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.R;
import com.example.mainactivity.model.ItemPedidoDetalhado;

import java.util.List;

public class DetalhesPedidoClienteAdapter extends RecyclerView.Adapter<DetalhesPedidoClienteAdapter.ItemViewHolder>{
    private List<ItemPedidoDetalhado> listaItens;

    public DetalhesPedidoClienteAdapter(List<ItemPedidoDetalhado> listaItens) {
        this.listaItens = listaItens;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_detalhe_produto_pedido, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ItemPedidoDetalhado item = listaItens.get(position);

        holder.txtNomeProduto.setText(item.nomeProduto);
        holder.txtQuantidade.setText("x" + item.quantidade);
        holder.txtPreco.setText(String.format("R$ %.2f", item.precoProduto * item.quantidade));
    }

    @Override
    public int getItemCount() {
        return listaItens != null ? listaItens.size() : 0;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView txtNomeProduto, txtQuantidade, txtPreco;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNomeProduto = itemView.findViewById(R.id.txtDetalheNomeProduto);
            txtQuantidade = itemView.findViewById(R.id.txtDetalheQtdPreco);
            txtPreco = itemView.findViewById(R.id.txtDetalhesPreco);
        }
    }
}
