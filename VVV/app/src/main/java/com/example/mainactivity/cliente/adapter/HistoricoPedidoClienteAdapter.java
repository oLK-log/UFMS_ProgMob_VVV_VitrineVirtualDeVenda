package com.example.mainactivity.cliente.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.R;
import com.example.mainactivity.model.Pedido;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoricoPedidoClienteAdapter extends RecyclerView.Adapter<HistoricoPedidoClienteAdapter.PedidoViewHolder> {
    private List<Pedido> listaPedidos;
    private OnPedidoClickListener listener;

    public interface OnPedidoClickListener{
        void onPedidoClick(Pedido pedido);
    }
    public HistoricoPedidoClienteAdapter(List<Pedido> listaPedidos, OnPedidoClickListener listener){
        this.listaPedidos = listaPedidos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HistoricoPedidoClienteAdapter.PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_historico_pedido_cliente, parent, false);
        return new HistoricoPedidoClienteAdapter.PedidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoricoPedidoClienteAdapter.PedidoViewHolder holder, int position){
        Pedido pedido = listaPedidos.get(position);
        holder.txtIdPedido.setText("Pedido #" + pedido.idPedido);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String dataFormatada = sdf.format(new Date(pedido.dataTimestamp));
        holder.txtDataPedido.setText("Data: " + dataFormatada);

        holder.txtValorTotal.setText(String.format("Total: R$ %.2f", pedido.valorTotal));

        holder.itemView.setOnClickListener(v -> listener.onPedidoClick(pedido));
    }

    @Override
    public int getItemCount() {
        return listaPedidos != null ? listaPedidos.size() : 0;
    }

    static class PedidoViewHolder extends RecyclerView.ViewHolder {
        TextView txtIdPedido, txtDataPedido, txtValorTotal;
        public PedidoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIdPedido = itemView.findViewById(R.id.txtIdPedido);
            txtDataPedido = itemView.findViewById(R.id.txtDataPedido);
            txtValorTotal = itemView.findViewById(R.id.txtValorTotal);
        }
    }
}
