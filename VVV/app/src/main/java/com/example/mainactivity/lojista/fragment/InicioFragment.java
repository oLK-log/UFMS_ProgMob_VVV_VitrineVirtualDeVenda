package com.example.mainactivity.lojista.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.mainactivity.R;
import com.example.mainactivity.core.adapter.DetalhesPedidoAdapter;
import com.example.mainactivity.database.AppDatabase;
import com.example.mainactivity.database.dao.PedidoDao;
import com.example.mainactivity.database.dao.ProdutoDao;
import com.example.mainactivity.lojista.adapter.HistoricoPedidoLojistaAdapter;
import com.example.mainactivity.model.Pedido;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InicioFragment extends Fragment {
    private TextView txtMetricaProdutos, txtMetricaFaturamento, txtMetricaVisitas;
    private RecyclerView rvHistoricoPedidos;
    private HistoricoPedidoLojistaAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_inicio_lojista, container, false);//infla o layout
        txtMetricaProdutos = view.findViewById(R.id.txtMetricaProdutos);
        txtMetricaFaturamento = view.findViewById(R.id.txtMetricaFaturamento);
        txtMetricaVisitas = view.findViewById(R.id.txtMetricaVisitas);
        rvHistoricoPedidos = view.findViewById(R.id.rvHistoricoPedidos);

        //habilitando listagem modo vertical
        rvHistoricoPedidos.setLayoutManager(new LinearLayoutManager(getContext()));
        carregarDadosPainel();
        return view;
    }

    //utilizei o onResume para atualizar automaticamente sempre que abrir a aba inicio
    @Override
    public void onResume(){
        super.onResume();
        carregarDadosPainel();
    }

    //metodo para carregar as metricas(por enquanto só tem umna)
    private void carregarMetricas(){
        //pegar idLojista
        SharedPreferences preferenciais = getContext().getSharedPreferences("sessao_vvv", Context.MODE_PRIVATE);
        int idLojista = preferenciais.getInt("idUsuario", -1);
        if(idLojista != -1){
            int totalProdutos = AppDatabase.getInstance(getContext()).produtoDao().contarProdutosDoLojista(idLojista);
            txtMetricaProdutos.setText(String.valueOf(totalProdutos));
        }else {
            txtMetricaProdutos.setText("Indefinido");
        }
    }

    private void carregarDadosPainel(){
        AppDatabase db = AppDatabase.getInstance(getContext());
        ProdutoDao produtoDao = db.produtoDao();
        PedidoDao pedidoDao = db.pedidoDao();

        //contador de produtos cadastrados
        carregarMetricas();

        //Buscar todos os pedidos finalizados
        List<Pedido> listaPedidos = pedidoDao.buscarTodosOsPedidos();

        //Faturamento e dados de cliente no pedido
        double faturamentoTotal = 0.0;
        for (Pedido p: listaPedidos){
            faturamentoTotal += p.valorTotal;

            com.example.mainactivity.model.Usuario cliente = db.usuarioDao().buscarUsuarioPorId(p.usuarioId);
            if(cliente != null){
                p.nomeCliente = cliente.nome;
                p.emailCliente = cliente.email;
            }/*else {
                p.nomeCliente = "Cliente desconhecido";
                p.emailCliente = "e-mail desconhecido";
            } */ //comentei pois estava setando como cliente desconhecido pois o visitante n eh um cliente propriamente dito   no db
        }
        txtMetricaFaturamento.setText(String.format("R$ %.2f", faturamentoTotal));

        //acessos na loja
        android.content.SharedPreferences prefsEstatisticas = getContext().getSharedPreferences("estatisticas_loja", Context.MODE_PRIVATE);
        int totalVisitas = prefsEstatisticas.getInt("total_visitas", 0);
        txtMetricaVisitas.setText(String.valueOf(totalVisitas));

        //adapter
        adapter = new HistoricoPedidoLojistaAdapter(listaPedidos, pedido -> {
            abrirDetalhesDoPedido(pedido);
        });
        rvHistoricoPedidos.setAdapter(adapter);
    }

    private void abrirDetalhesDoPedido(Pedido pedido){
        //tentando usar efeito deslizante
        com.google.android.material.bottomsheet.BottomSheetDialog bottomSheetDialog =
                new com.google.android.material.bottomsheet.BottomSheetDialog(getContext());

        View viewBottomSheet = LayoutInflater.from(getContext()).inflate(R.layout.layout_bottom_sheet_pedido, null);
        bottomSheetDialog.setContentView(viewBottomSheet);
        //conectando
        TextView txtTitulo = viewBottomSheet.findViewById(R.id.txtTituloBottomSheet);
        RecyclerView rvItens = viewBottomSheet.findViewById(R.id.rvItensBottomSheet);

        txtTitulo.setText("Detalhes do Pedido #" + pedido.idPedido);
        rvItens.setLayoutManager(new LinearLayoutManager(getContext()));//lista vertical

        //fazr busca no dB
        AppDatabase db = AppDatabase.getInstance(getContext());
        List<com.example.mainactivity.model.ItemPedidoDetalhado> itensDoPedido =
                db.pedidoDao().buscarItensDoPedidoFinalizado(pedido.idPedido);

        DetalhesPedidoAdapter detalhesAdapter =
                new DetalhesPedidoAdapter(itensDoPedido);
        rvItens.setAdapter(detalhesAdapter);
        bottomSheetDialog.show();
    }
}
