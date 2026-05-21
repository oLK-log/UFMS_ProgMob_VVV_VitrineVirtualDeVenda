package com.example.mainactivity.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pedidos")
public class Pedido {
    @PrimaryKey(autoGenerate = true)
    public int idPedido;

    public int usuarioId;
    public String nomeCliente;
    public String emailCliente;
    public double valorTotal;
    public long dataTimestamp;
}
