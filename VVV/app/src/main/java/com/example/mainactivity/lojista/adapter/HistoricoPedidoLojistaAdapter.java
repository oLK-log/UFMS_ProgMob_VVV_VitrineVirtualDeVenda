package com.example.mainactivity.lojista.adapter;

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
public class HistoricoPedidoLojistaAdapter extends RecyclerView.Adapter<HistoricoPedidoLojistaAdapter.PedidoViewHolder> {
    private List<Pedido> listaPedidos;
    private OnPedidoClickListener listener;

    public interface OnPedidoClickListener{
        void onPedidoClick(Pedido pedido);
    }
    public HistoricoPedidoLojistaAdapter(List<Pedido> listaPedidos, OnPedidoClickListener listener){
        this.listaPedidos=listaPedidos;
        this.listener=listener;
    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido_lojista, parent, false);
        return new PedidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position){
        Pedido pedido = listaPedidos.get(position);

        holder.txtNome.setText(pedido.nomeCliente);
        holder.txtEmail.setText(pedido.emailCliente);
        holder.txtTotal.setText(String.format("R$ %.2f", pedido.valorTotal));
        holder.txtId.setText("Pedido #" + pedido.idPedido);

        //formatacao da data e hora
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String dataFormatada = sdf.format(new Date(pedido.dataTimestamp));
        holder.txtData.setText(dataFormatada);

        holder.itemView.setOnClickListener(v -> listener.onPedidoClick(pedido));

    }

    @Override
    public int getItemCount(){
        return listaPedidos.size();
    }

    static class PedidoViewHolder extends RecyclerView.ViewHolder{
        TextView txtNome, txtEmail, txtTotal, txtData, txtId;

        public PedidoViewHolder(@NonNull View itemView){
            super(itemView);
            txtNome = itemView.findViewById(R.id.txtLojistaNomeCliente);
            txtEmail = itemView.findViewById(R.id.txtLojistaEmailCliente);
            txtTotal = itemView.findViewById(R.id.txtLojistaTotalPedido);
            txtData = itemView.findViewById(R.id.txtLojistaDataPedido);
            txtId = itemView.findViewById(R.id.txtLojistaIdPedido);
        }
    }
}
