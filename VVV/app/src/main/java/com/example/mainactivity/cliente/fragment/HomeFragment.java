package com.example.mainactivity.cliente.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.R;
import com.example.mainactivity.cliente.MapaLojaClienteActivity;
import com.example.mainactivity.cliente.adapter.VitrineAdapter;
import com.example.mainactivity.database.AppDatabase;
import com.example.mainactivity.database.dao.ProdutoDao;
import com.example.mainactivity.model.Produto;
import com.example.mainactivity.model.Usuario;

import java.util.List;
public class HomeFragment extends Fragment{
    private RecyclerView recyclerVitrine, recyclerDestaques;
    private ImageButton btnBuscarProduto, btnFiltroProduto, btnMapaLoja;
    private EditText editBuscarProduto;
    private VitrineAdapter adapterVitrine;
    private VitrineAdapter adapterDestaques;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_home_cliente, container, false);
        recyclerVitrine = view.findViewById(R.id.recyclerVitrine);
        editBuscarProduto = view.findViewById(R.id.editBuscarProduto);
        btnBuscarProduto = view.findViewById(R.id.btnBuscarProduto);
        recyclerDestaques = view.findViewById(R.id.recyclerDestaques);
        btnFiltroProduto = view.findViewById(R.id.btnFiltroProduto);
        btnMapaLoja = view.findViewById(R.id.btnMapaLoja);

        //Configuracao do clique do btn do mapa
        btnMapaLoja.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), MapaLojaClienteActivity.class);
                startActivity(intent);
            }
        });

        //configurando as direções da rolagem(destaque horizontal e produtos geral vertical)
        recyclerDestaques.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerVitrine.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        //buscar produto
        btnBuscarProduto.setOnClickListener(v -> {
            String termo = editBuscarProduto.getText().toString().trim();
            List<Produto> resultados;

            // Instancia o DAO
            ProdutoDao produtoDao = AppDatabase.getInstance(getContext()).produtoDao();

            if (termo.isEmpty()) {
                // Se a busca for vazia, volta a mostrar os produtos em destaque da tela inicial
                resultados = produtoDao.buscarProdutos("");
            } else {
                // Se digitou algo, busca pelo nome
                resultados = produtoDao.buscarProdutosPorNome(termo);
            }

            // Atualiza a parte da tela de todos os produtos enviando a nova lista para o Adapter
            if (adapterVitrine != null) {
                adapterVitrine.atualizarLista(resultados);
            }
        });
        carregarConfigurarCoresDaLoja(view);
        return view;
    }

    //atualizar a vitrine quandp p cliente entrar
    @Override
    public void onResume(){
        super.onResume();
        carregarVitrine();
    }
    private void carregarVitrine(){
        ProdutoDao dao = AppDatabase.getInstance(getContext()).produtoDao();
        //para os destaque
        List<Produto> produtosDestaque = dao.buscarProdutosEmDestaque();
        adapterDestaques = new VitrineAdapter(produtosDestaque, R.layout.item_produto_destaque);
        recyclerDestaques.setAdapter(adapterDestaques);
        //para o outro-prod geral
        List<Produto> todosProdutos = dao.buscarProdutos("");
        adapterVitrine = new VitrineAdapter(todosProdutos, R.layout.item_produto_vitrine);
        recyclerVitrine.setAdapter(adapterVitrine);
    }
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
            if (corFundo != null && !corFundo.trim().isEmpty()) {
                view.setBackgroundColor(Color.parseColor(corFundo));
            }

            if (corPrimaria != null && !corPrimaria.trim().isEmpty()) {
                int corHexConvertida = Color.parseColor(corPrimaria);

                LinearLayout layoutBarraBusca = view.findViewById(R.id.layoutBarraBusca);
                if (layoutBarraBusca != null) {
                    layoutBarraBusca.setBackgroundColor(corHexConvertida);
                }

                ImageButton btnBuscarProduto = view.findViewById(R.id.btnBuscarProduto);
                if (btnBuscarProduto != null) {
                    btnBuscarProduto.setColorFilter(corHexConvertida);
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

}
