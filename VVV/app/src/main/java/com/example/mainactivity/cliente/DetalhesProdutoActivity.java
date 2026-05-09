package com.example.mainactivity.cliente;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mainactivity.R;
import com.example.mainactivity.database.AppDatabase;
import com.example.mainactivity.model.Produto;
public class DetalhesProdutoActivity extends AppCompatActivity {
    private ImageView imgDetalheProduto;
    private TextView txtDetalhesNome, txtDetalhesPreco, txtDetalhesDescricao;
    private Button btnComprar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_produto);

        imgDetalheProduto = findViewById(R.id.imgDetalheProduto);
        txtDetalhesNome = findViewById(R.id.txtDetalhesNome);
        txtDetalhesPreco = findViewById(R.id.txtDetalhesPreco);
        txtDetalhesDescricao = findViewById(R.id.txtDetalhesDescricao);
        btnComprar = findViewById(R.id.btnComprar);

        //pegar id do produto
        int idProduto = getIntent().getIntExtra("idProdutoDetalhe", -1);
        if(idProduto != -1){
            carregarProduto(idProduto);
        }else{
            Toast.makeText(this, "Erro ao carregar produto.", Toast.LENGTH_SHORT).show();
            finish();
        }

        //adicionar ao carrinho/lista de pedidos
        btnComprar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(DetalhesProdutoActivity.this, "Produto adicionado ao Pedido", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //esse metodo busca um produto específico por meio do Id
    private void carregarProduto(int id){
        Produto produto = AppDatabase.getInstance(this).produtoDao().buscarProdutoPorId(id);

        if(produto != null){
            txtDetalhesNome.setText(produto.nome);
            txtDetalhesPreco.setText(String.format("R$ %.2f", produto.preco));
            txtDetalhesDescricao.setText(produto.descricao);
            //carregar imagem
            if(produto.imagemUri != null && !produto.imagemUri.isEmpty()){
                try{
                    imgDetalheProduto.setImageURI(Uri.parse(produto.imagemUri));
                }catch(SecurityException e){
                    imgDetalheProduto.setImageResource(android.R.drawable.ic_menu_camera);
                }
            } else{
                imgDetalheProduto.setImageResource(android.R.drawable.ic_menu_camera);
            }

        }

    }
}
