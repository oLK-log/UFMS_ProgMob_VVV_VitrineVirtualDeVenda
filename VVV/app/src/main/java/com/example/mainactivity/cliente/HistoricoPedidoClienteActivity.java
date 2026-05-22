package com.example.mainactivity.cliente;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mainactivity.cliente.adapter.HistoricoPedidoClienteAdapter;

import com.example.mainactivity.R;
import com.example.mainactivity.model.Pedido;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.example.mainactivity.core.adapter.DetalhesPedidoAdapter;

public class HistoricoPedidoClienteActivity extends AppCompatActivity{
    private RecyclerView rvMeusPedidos;
    private TextView txtVoltarPerfil, txtSemPedidos;
    private int usuarioIdLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_pedidos_cliente);

        rvMeusPedidos = findViewById(R.id.rvMeusPedidos);
        txtVoltarPerfil = findViewById(R.id.txtVoltarPerfil);
        txtSemPedidos = findViewById(R.id.txtSemPedidos);

        txtVoltarPerfil.setOnClickListener(v -> finish());

        SharedPreferences prefs = getSharedPreferences("sessao_vvv", Context.MODE_PRIVATE);
        usuarioIdLogado = prefs.getInt("idUsuario", -1);

        carregarPedidosUsuario();
    }

    private void carregarPedidosUsuario(){
        java.util.List<com.example.mainactivity.model.Pedido> meusPedidos =
                com.example.mainactivity.database.AppDatabase.getInstance(this)
                        .pedidoDao().buscarPedidosPorUsuario(usuarioIdLogado);

        if (meusPedidos == null || meusPedidos.isEmpty()) {
            txtSemPedidos.setVisibility(View.VISIBLE);
            rvMeusPedidos.setVisibility(View.GONE);
        } else {
            txtSemPedidos.setVisibility(View.GONE);
            rvMeusPedidos.setVisibility(View.VISIBLE);
            rvMeusPedidos.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));
            HistoricoPedidoClienteAdapter adapter = new HistoricoPedidoClienteAdapter(meusPedidos, new HistoricoPedidoClienteAdapter.OnPedidoClickListener() {
                @Override
                public void onPedidoClick(Pedido pedidoClicado) {
                    mostrarDetalhesBottomSheet(pedidoClicado);
                }
            });
            rvMeusPedidos.setAdapter(adapter);
        }
    }

    private void mostrarDetalhesBottomSheet(Pedido pedido){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);

        View view = getLayoutInflater().inflate(R.layout.layout_bottom_sheet_pedido, null);
        bottomSheetDialog.setContentView(view);

        TextView txtTitulo = view.findViewById(R.id.txtTituloBottomSheet);
        txtTitulo.setText("Detalhes do Pedido #" + pedido.idPedido);

        RecyclerView rvItensBottomSheet = view.findViewById(R.id.rvItensBottomSheet);
        rvItensBottomSheet.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));

        java.util.List<com.example.mainactivity.model.ItemPedidoDetalhado> itensDoPedido =
                com.example.mainactivity.database.AppDatabase.getInstance(this)
                        .pedidoDao().buscarItensDoPedidoFinalizado(pedido.idPedido);

        DetalhesPedidoAdapter detalhesAdapter =
                new DetalhesPedidoAdapter(itensDoPedido);
        rvItensBottomSheet.setAdapter(detalhesAdapter);
        bottomSheetDialog.show();
    }
}
