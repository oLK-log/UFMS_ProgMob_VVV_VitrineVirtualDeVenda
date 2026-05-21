package com.example.mainactivity.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mainactivity.model.ItemPedido;
import com.example.mainactivity.model.ItemPedidoDetalhado;

import java.util.List;

@Dao
public interface PedidoDao {
    @Insert
    void inserirItem(ItemPedido item);
    @Update
    void atualizarItem(ItemPedido item);
    @Delete
    void removerItem(ItemPedido item);

    //querys
    //Traz todos os itens que estão no pedido de um cliente pelo id
    @Query("SELECT * FROM item_pedido WHERE usuarioId = :idUsuario")
    List<ItemPedido> buscarPedidoDoUsuario(int idUsuario);

    //Verifica se um produto está no pedido de determinado cliente(somar a quantidade)
    @Query("SELECT * FROM item_pedido WHERE usuarioId = :idUsuario AND produtoId = :idProduto LIMIT 1")
    ItemPedido verificarProdutoNoPedido(int idUsuario, int idProduto);

    //Esvazia o carrinho
    @Query("DELETE FROM item_pedido WHERE usuarioId = :idUsuario")
    void limparPedido(int idUsuario);
    //retorna os itens do pedido com os itens do produto
    // Retorna os itens do pedido já com os dados do produto perfeitamente mesclados
    @Query("SELECT ip.id AS idItemPedido, ip.produtoId, ip.quantidade, p.nome AS nomeProduto, p.preco AS precoProduto, p.imagemUri " +
            "FROM item_pedido ip " +
            "INNER JOIN produtos p ON ip.produtoId = p.id " +
            "WHERE ip.usuarioId = :idUsuario")
    List<ItemPedidoDetalhado> buscarPedidoDetalhado(int idUsuario);
    //Query para limpar o carrinho de um usuario apos a compra
    @Query("DELETE FROM item_pedido WHERE usuarioId = :idUsuario")
    void limparCarrinho(int idUsuario);

}
