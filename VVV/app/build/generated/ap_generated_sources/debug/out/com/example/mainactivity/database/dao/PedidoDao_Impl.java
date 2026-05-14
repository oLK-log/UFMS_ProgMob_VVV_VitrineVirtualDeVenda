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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
