package com.example.mainactivity.model;
//esta classe serve para guardar o estado do itens de um pedido

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "itens_pedido_finalizado")
public class ItemPedidoFinalizado {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int pedidoId;
    public int produtoId;
    public int quantidade;
    public double precoUnitarioHistorico;//no momento da compra
}
