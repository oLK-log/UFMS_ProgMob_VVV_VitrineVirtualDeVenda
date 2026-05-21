package com.example.mainactivity.cliente.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.R;
import com.example.mainactivity.cliente.adapter.VitrineAdapter;
import com.example.mainactivity.database.AppDatabase;
import com.example.mainactivity.database.dao.ProdutoDao;
import com.example.mainactivity.model.Produto;

import java.util.List;
public class HomeFragment extends Fragment{
    private RecyclerView recyclerVitrine, recyclerDestaques;
    private ImageButton btnBuscarProduto, btnFiltroProduto;
    private EditText editBuscarProduto;
    private VitrineAdapter adapterVitrine;
    private VitrineAdapter adapterDestaques;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerVitrine = view.findViewById(R.id.recyclerVitrine);
        editBuscarProduto = view.findViewById(R.id.editBuscarProduto);
        btnBuscarProduto = view.findViewById(R.id.btnBuscarProduto);
        recyclerDestaques = view.findViewById(R.id.recyclerDestaques);
        btnFiltroProduto = view.findViewById(R.id.btnFiltroProduto);

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

}
