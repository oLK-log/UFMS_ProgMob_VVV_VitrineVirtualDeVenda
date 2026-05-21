package com.example.mainactivity.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.mainactivity.model.ItemPedido;
import com.example.mainactivity.model.ItemPedidoDetalhado;
import com.example.mainactivity.model.ItemPedidoFinalizado;
import com.example.mainactivity.model.Pedido;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PedidoDao_Impl implements PedidoDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ItemPedido> __insertionAdapterOfItemPedido;

  private final EntityInsertionAdapter<Pedido> __insertionAdapterOfPedido;

  private final EntityInsertionAdapter<ItemPedidoFinalizado> __insertionAdapterOfItemPedidoFinalizado;

  private final EntityDeletionOrUpdateAdapter<ItemPedido> __deletionAdapterOfItemPedido;

  private final EntityDeletionOrUpdateAdapter<ItemPedido> __updateAdapterOfItemPedido;

  private final SharedSQLiteStatement __preparedStmtOfLimparPedido;

  public PedidoDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfItemPedido = new EntityInsertionAdapter<ItemPedido>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `item_pedido` (`id`,`usuarioId`,`produtoId`,`quantidade`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ItemPedido entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.usuarioId);
        statement.bindLong(3, entity.produtoId);
        statement.bindLong(4, entity.quantidade);
      }
    };
    this.__insertionAdapterOfPedido = new EntityInsertionAdapter<Pedido>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `pedidos` (`idPedido`,`usuarioId`,`nomeCliente`,`emailCliente`,`valorTotal`,`dataTimestamp`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Pedido entity) {
        statement.bindLong(1, entity.idPedido);
        statement.bindLong(2, entity.usuarioId);
        if (entity.nomeCliente == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.nomeCliente);
        }
        if (entity.emailCliente == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.emailCliente);
        }
        statement.bindDouble(5, entity.valorTotal);
        statement.bindLong(6, entity.dataTimestamp);
      }
    };
    this.__insertionAdapterOfItemPedidoFinalizado = new EntityInsertionAdapter<ItemPedidoFinalizado>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `itens_pedido_finalizado` (`id`,`pedidoId`,`produtoId`,`quantidade`,`precoUnitarioHistorico`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ItemPedidoFinalizado entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.pedidoId);
        statement.bindLong(3, entity.produtoId);
        statement.bindLong(4, entity.quantidade);
        statement.bindDouble(5, entity.precoUnitarioHistorico);
      }
    };
    this.__deletionAdapterOfItemPedido = new EntityDeletionOrUpdateAdapter<ItemPedido>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `item_pedido` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ItemPedido entity) {
        statement.bindLong(1, entity.id);
      }
    };
    this.__updateAdapterOfItemPedido = new EntityDeletionOrUpdateAdapter<ItemPedido>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `item_pedido` SET `id` = ?,`usuarioId` = ?,`produtoId` = ?,`quantidade` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ItemPedido entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.usuarioId);
        statement.bindLong(3, entity.produtoId);
        statement.bindLong(4, entity.quantidade);
        statement.bindLong(5, entity.id);
      }
    };
    this.__preparedStmtOfLimparPedido = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM item_pedido WHERE usuarioId = ?";
        return _query;
      }
    };
  }

  @Override
  public void inserirItem(final ItemPedido item) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfItemPedido.insert(item);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public long inserirPedido(final Pedido pedido) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfPedido.insertAndReturnId(pedido);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void inserirItensPedidoFinalizado(final List<ItemPedidoFinalizado> itens) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfItemPedidoFinalizado.insert(itens);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void removerItem(final ItemPedido item) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfItemPedido.handle(item);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void atualizarItem(final ItemPedido item) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfItemPedido.handle(item);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void limparPedido(final int idUsuario) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfLimparPedido.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, idUsuario);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfLimparPedido.release(_stmt);
    }
  }

  @Override
  public void limparCarrinho(final int idUsuario) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfLimparPedido.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, idUsuario);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfLimparPedido.release(_stmt);
    }
  }

  @Override
  public List<ItemPedido> buscarPedidoDoUsuario(final int idUsuario) {
    final String _sql = "SELECT * FROM item_pedido WHERE usuarioId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, idUsuario);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUsuarioId = CursorUtil.getColumnIndexOrThrow(_cursor, "usuarioId");
      final int _cursorIndexOfProdutoId = CursorUtil.getColumnIndexOrThrow(_cursor, "produtoId");
      final int _cursorIndexOfQuantidade = CursorUtil.getColumnIndexOrThrow(_cursor, "quantidade");
      final List<ItemPedido> _result = new ArrayList<ItemPedido>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final ItemPedido _item;
        _item = new ItemPedido();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        _item.usuarioId = _cursor.getInt(_cursorIndexOfUsuarioId);
        _item.produtoId = _cursor.getInt(_cursorIndexOfProdutoId);
        _item.quantidade = _cursor.getInt(_cursorIndexOfQuantidade);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public ItemPedido verificarProdutoNoPedido(final int idUsuario, final int idProduto) {
    final String _sql = "SELECT * FROM item_pedido WHERE usuarioId = ? AND produtoId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, idUsuario);
    _argIndex = 2;
    _statement.bindLong(_argIndex, idProduto);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUsuarioId = CursorUtil.getColumnIndexOrThrow(_cursor, "usuarioId");
      final int _cursorIndexOfProdutoId = CursorUtil.getColumnIndexOrThrow(_cursor, "produtoId");
      final int _cursorIndexOfQuantidade = CursorUtil.getColumnIndexOrThrow(_cursor, "quantidade");
      final ItemPedido _result;
      if (_cursor.moveToFirst()) {
        _result = new ItemPedido();
        _result.id = _cursor.getInt(_cursorIndexOfId);
        _result.usuarioId = _cursor.getInt(_cursorIndexOfUsuarioId);
        _result.produtoId = _cursor.getInt(_cursorIndexOfProdutoId);
        _result.quantidade = _cursor.getInt(_cursorIndexOfQuantidade);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<ItemPedidoDetalhado> buscarPedidoDetalhado(final int idUsuario) {
    final String _sql = "SELECT ip.id AS idItemPedido, ip.produtoId, ip.quantidade, p.nome AS nomeProduto, p.preco AS precoProduto, p.imagemUri FROM item_pedido ip INNER JOIN produtos p ON ip.produtoId = p.id WHERE ip.usuarioId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, idUsuario);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdItemPedido = 0;
      final int _cursorIndexOfProdutoId = 1;
      final int _cursorIndexOfQuantidade = 2;
      final int _cursorIndexOfNomeProduto = 3;
      final int _cursorIndexOfPrecoProduto = 4;
      final int _cursorIndexOfImagemUri = 5;
      final List<ItemPedidoDetalhado> _result = new ArrayList<ItemPedidoDetalhado>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final ItemPedidoDetalhado _item;
        _item = new ItemPedidoDetalhado();
        _item.idItemPedido = _cursor.getInt(_cursorIndexOfIdItemPedido);
        _item.produtoId = _cursor.getInt(_cursorIndexOfProdutoId);
        _item.quantidade = _cursor.getInt(_cursorIndexOfQuantidade);
        if (_cursor.isNull(_cursorIndexOfNomeProduto)) {
          _item.nomeProduto = null;
        } else {
          _item.nomeProduto = _cursor.getString(_cursorIndexOfNomeProduto);
        }
        _item.precoProduto = _cursor.getDouble(_cursorIndexOfPrecoProduto);
        if (_cursor.isNull(_cursorIndexOfImagemUri)) {
          _item.imagemUri = null;
        } else {
          _item.imagemUri = _cursor.getString(_cursorIndexOfImagemUri);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Pedido> buscarTodosOsPedidos() {
    final String _sql = "SELECT * FROM pedidos ORDER BY dataTimestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdPedido = CursorUtil.getColumnIndexOrThrow(_cursor, "idPedido");
      final int _cursorIndexOfUsuarioId = CursorUtil.getColumnIndexOrThrow(_cursor, "usuarioId");
      final int _cursorIndexOfNomeCliente = CursorUtil.getColumnIndexOrThrow(_cursor, "nomeCliente");
      final int _cursorIndexOfEmailCliente = CursorUtil.getColumnIndexOrThrow(_cursor, "emailCliente");
      final int _cursorIndexOfValorTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "valorTotal");
      final int _cursorIndexOfDataTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "dataTimestamp");
      final List<Pedido> _result = new ArrayList<Pedido>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Pedido _item;
        _item = new Pedido();
        _item.idPedido = _cursor.getInt(_cursorIndexOfIdPedido);
        _item.usuarioId = _cursor.getInt(_cursorIndexOfUsuarioId);
        if (_cursor.isNull(_cursorIndexOfNomeCliente)) {
          _item.nomeCliente = null;
        } else {
          _item.nomeCliente = _cursor.getString(_cursorIndexOfNomeCliente);
        }
        if (_cursor.isNull(_cursorIndexOfEmailCliente)) {
          _item.emailCliente = null;
        } else {
          _item.emailCliente = _cursor.getString(_cursorIndexOfEmailCliente);
        }
        _item.valorTotal = _cursor.getDouble(_cursorIndexOfValorTotal);
        _item.dataTimestamp = _cursor.getLong(_cursorIndexOfDataTimestamp);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<ItemPedidoDetalhado> buscarItensDoPedidoFinalizado(final int idPedido) {
    final String _sql = "SELECT ipf.id AS idItemPedido, ipf.pedidoId, ipf.produtoId, ipf.quantidade, ipf.precoUnitarioHistorico AS precoProduto, p.nome AS nomeProduto, p.imagemUri FROM itens_pedido_finalizado ipf INNER JOIN produtos p ON ipf.produtoId = p.id WHERE ipf.pedidoId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, idPedido);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdItemPedido = 0;
      final int _cursorIndexOfProdutoId = 2;
      final int _cursorIndexOfQuantidade = 3;
      final int _cursorIndexOfPrecoProduto = 4;
      final int _cursorIndexOfNomeProduto = 5;
      final int _cursorIndexOfImagemUri = 6;
      final List<ItemPedidoDetalhado> _result = new ArrayList<ItemPedidoDetalhado>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final ItemPedidoDetalhado _item;
        _item = new ItemPedidoDetalhado();
        _item.idItemPedido = _cursor.getInt(_cursorIndexOfIdItemPedido);
        _item.produtoId = _cursor.getInt(_cursorIndexOfProdutoId);
        _item.quantidade = _cursor.getInt(_cursorIndexOfQuantidade);
        _item.precoProduto = _cursor.getDouble(_cursorIndexOfPrecoProduto);
        if (_cursor.isNull(_cursorIndexOfNomeProduto)) {
          _item.nomeProduto = null;
        } else {
          _item.nomeProduto = _cursor.getString(_cursorIndexOfNomeProduto);
        }
        if (_cursor.isNull(_cursorIndexOfImagemUri)) {
          _item.imagemUri = null;
        } else {
          _item.imagemUri = _cursor.getString(_cursorIndexOfImagemUri);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
