package com.example.mainactivity.cliente.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.R;
import com.example.mainactivity.cliente.adapter.PedidoAdapter;
import com.example.mainactivity.database.AppDatabase;
import com.example.mainactivity.database.dao.PedidoDao;
import com.example.mainactivity.model.ItemPedido;
import com.example.mainactivity.model.ItemPedidoDetalhado;
import com.example.mainactivity.model.Produto;
import com.example.mainactivity.model.Usuario;

import java.util.List;
public class PedidoFragment extends Fragment{
    private RecyclerView rvItensPedido;
    private TextView txtPedidoVazio, txtValorTotalPedido;
    private LinearLayout layoutRodapePedido;
    private Button btnFinalizarPedido;
    private PedidoAdapter adapter;
    private List<ItemPedidoDetalhado> listaItens;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pedido, container, false);

        rvItensPedido = view.findViewById(R.id.rvItensPedido);
        txtPedidoVazio= view.findViewById(R.id.txtPedidoVazio);
        txtValorTotalPedido=view.findViewById(R.id.txtValorTotalPedido);
        layoutRodapePedido=view.findViewById(R.id.layoutRodapePedido);
        btnFinalizarPedido=view.findViewById(R.id.btnFinalizarPedido);

        rvItensPedido.setLayoutManager(new LinearLayoutManager(getContext()));//configura a lista p ser verrtical
        carregarPedido();
        //Clicando no botao finalizar
        btnFinalizarPedido.setOnClickListener(v->{
            SharedPreferences preferenciais = getContext().getSharedPreferences("sessao_vvv", Context.MODE_PRIVATE);
            int idUsuarioAtual = preferenciais.getInt("idUsuario", -1);
            //logica para caso o usuario nao esteja logado
            if(idUsuarioAtual == -1){
                mostrarDialogoVisitante();
            }else{
                finalizarPedido(idUsuarioAtual, "Cliente Registrado", "email@cliente.com");
            }
        });

        carregarConfigurarCoresDaLoja(view);
        return view;
    }
    //Met para exibir caixa de preenchimento de dados add para não logados
    private void mostrarDialogoVisitante(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Finalizar Pedido");
        builder.setMessage("Informe seus dados para Finalizar o Pedido");

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50,10);

        final android.widget.EditText editNome = new android.widget.EditText(getContext());
        editNome.setHint("Nome Completo");
        layout.addView(editNome);

        final android.widget.EditText editEmail = new android.widget.EditText(getContext());
        editEmail.setHint("E-mail");
        editEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        layout.addView(editEmail);

        builder.setView(layout);
        //btn pra finalizar pedido
        builder.setPositiveButton("Finalizar Pedido", ((dialog, which) -> {
            String nome = editNome.getText().toString().trim();
            String email = editEmail.getText().toString().trim();

            if(nome.isEmpty() || email.isEmpty()){
                Toast.makeText(getContext(), "Preencha todos os campos para finalizar!", Toast.LENGTH_SHORT).show();
            }else{
                finalizarPedido(-1, nome, email);
            }
        }));
        builder.setNegativeButton("Cancelar", ((dialog, which) -> dialog.dismiss()));
        builder.create().show();
    }
    //met q carrega o pedido ao pegar o usuario, trazer a lista de itens
    private void carregarPedido(){
        SharedPreferences preferenciais = getContext().getSharedPreferences("sessao_vvv", Context.MODE_PRIVATE);
        int idusuario = preferenciais.getInt("idUsuario", -1);//visitante eh -1

        PedidoDao pedidoDao = AppDatabase.getInstance(getContext()).pedidoDao();
        listaItens=pedidoDao.buscarPedidoDetalhado(idusuario);

        if(listaItens.isEmpty()){
            rvItensPedido.setVisibility(View.GONE);
            layoutRodapePedido.setVisibility(View.GONE);
            txtPedidoVazio.setVisibility(View.VISIBLE);
        }else {
            rvItensPedido.setVisibility(View.VISIBLE);
            layoutRodapePedido.setVisibility(View.VISIBLE);
            txtPedidoVazio.setVisibility(View.GONE);
            //instanciando adapter
            adapter = new PedidoAdapter(listaItens, item -> removerItem(item));
            rvItensPedido.setAdapter(adapter);

            calcularTotal();
        }
        //txtPedidoVazio.setVisibility(View.INVISIBLE);
    }

    //met para remover item da lista de pedido
    private void removerItem(ItemPedidoDetalhado item){
        PedidoDao pedidoDao = AppDatabase.getInstance(getContext()).pedidoDao();

        ItemPedido itemEntity = new ItemPedido();
        itemEntity.id = item.idItemPedido;

        pedidoDao.removerItem(itemEntity);
        Toast.makeText(getContext(), "Item removido!", Toast.LENGTH_SHORT).show();
        carregarPedido();
    }

    //meth para calcular o total do pedido - preco x qutd
    private void calcularTotal(){
        double total = 0.0;

        for (ItemPedidoDetalhado item : listaItens){
            total += item.precoProduto * item.quantidade;
        }
        txtValorTotalPedido.setText(String.format("R$ %.2f", total));
    }

    //metodo para finalizar pedido
    private void finalizarPedido(int idUsuario, String nomeCliente, String emailCliente){
        PedidoDao pedidoDao = AppDatabase.getInstance(getContext()).pedidoDao();

        //pegar estado do carrinho atual
        List<ItemPedidoDetalhado> itensCarrinho = pedidoDao.buscarPedidoDetalhado(idUsuario);
        if (itensCarrinho.isEmpty()) return;
        //calcular total
        double total = 0.0;
        for(ItemPedidoDetalhado item : itensCarrinho){
            total += item.precoProduto * item.quantidade;
        }
        //registrar Pedido
        com.example.mainactivity.model.Pedido novoPedido = new com.example.mainactivity.model.Pedido();
        novoPedido.usuarioId = idUsuario;
        novoPedido.nomeCliente = nomeCliente;
        novoPedido.emailCliente=emailCliente;
        novoPedido.valorTotal=total;
        novoPedido.dataTimestamp= System.currentTimeMillis();
        long idPedidoGerado = pedidoDao.inserirPedido(novoPedido);

        java.util.List<com.example.mainactivity.model.ItemPedidoFinalizado> itensParaSalvar = new java.util.ArrayList<>();

        for(ItemPedidoDetalhado item : itensCarrinho){
            com.example.mainactivity.model.ItemPedidoFinalizado finalizado = new com.example.mainactivity.model.ItemPedidoFinalizado();
            finalizado.pedidoId = (int) idPedidoGerado;
            finalizado.produtoId = item.produtoId;
            finalizado.quantidade = item.quantidade;
            finalizado.precoUnitarioHistorico = item.precoProduto;
            itensParaSalvar.add(finalizado);
        }
        pedidoDao.inserirItensPedidoFinalizado(itensParaSalvar);


        //Tocando efeito sonoro
        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.som_magic);
        if(mediaPlayer != null){
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
        }
        Toast.makeText(getContext(), "Pedido realizado com Sucesso", Toast.LENGTH_SHORT).show();

        //limpar carrinho
        pedidoDao.limparCarrinho(idUsuario);
        //recarregar tela
        carregarPedido();
    }

    // Importe a classe Color no topo do ficheiro, se necessário:
    // import android.graphics.Color;

    private void carregarConfigurarCoresDaLoja(View viewRoot) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Usuario> usuarios = AppDatabase.getInstance(getContext()).usuarioDao().buscarTodos();
                Usuario lojista = null;
                for(Usuario u : usuarios){
                    if("LOJISTA".equals(u.tipoPerfil)){
                        lojista = u;
                        break;
                    }
                }
                if (lojista != null) {
                    final String corPrimaria = lojista.corPrimariaLoja;
                    final String corFundo = lojista.corFundoLoja;

                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                aplicarCoresNaInterface(viewRoot, corPrimaria, corFundo);
                            }
                        });
                    }
                }
            }
        }).start();
    }

    private void aplicarCoresNaInterface(View view, String corPrimaria, String corFundo) {
        try {
            // Pinta o Fundo
            if (corFundo != null && !corFundo.trim().isEmpty()) {
                view.setBackgroundColor(android.graphics.Color.parseColor(corFundo));
            }
            // Pinta o Botão de Finalizar
            if (corPrimaria != null && !corPrimaria.trim().isEmpty()) {
                int corHexConvertida = android.graphics.Color.parseColor(corPrimaria);
                if (btnFinalizarPedido != null) {
                    btnFinalizarPedido.setBackgroundColor(corHexConvertida);
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

}
