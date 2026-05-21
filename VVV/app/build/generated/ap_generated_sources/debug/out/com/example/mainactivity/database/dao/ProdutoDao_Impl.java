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
import com.example.mainactivity.model.Produto;
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
public final class ProdutoDao_Impl implements ProdutoDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Produto> __insertionAdapterOfProduto;

  private final EntityDeletionOrUpdateAdapter<Produto> __deletionAdapterOfProduto;

  private final EntityDeletionOrUpdateAdapter<Produto> __updateAdapterOfProduto;

  private final SharedSQLiteStatement __preparedStmtOfAtualizarDestaque;

  public ProdutoDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProduto = new EntityInsertionAdapter<Produto>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `produtos` (`id`,`nome`,`descricao`,`preco`,`imagemUri`,`usuarioId`,`isDestaque`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Produto entity) {
        statement.bindLong(1, entity.id);
        if (entity.nome == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.nome);
        }
        if (entity.descricao == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.descricao);
        }
        statement.bindDouble(4, entity.preco);
        if (entity.imagemUri == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.imagemUri);
        }
        statement.bindLong(6, entity.usuarioId);
        final int _tmp = entity.isDestaque ? 1 : 0;
        statement.bindLong(7, _tmp);
      }
    };
    this.__deletionAdapterOfProduto = new EntityDeletionOrUpdateAdapter<Produto>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `produtos` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Produto entity) {
        statement.bindLong(1, entity.id);
      }
    };
    this.__updateAdapterOfProduto = new EntityDeletionOrUpdateAdapter<Produto>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `produtos` SET `id` = ?,`nome` = ?,`descricao` = ?,`preco` = ?,`imagemUri` = ?,`usuarioId` = ?,`isDestaque` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Produto entity) {
        statement.bindLong(1, entity.id);
        if (entity.nome == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.nome);
        }
        if (entity.descricao == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.descricao);
        }
        statement.bindDouble(4, entity.preco);
        if (entity.imagemUri == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.imagemUri);
        }
        statement.bindLong(6, entity.usuarioId);
        final int _tmp = entity.isDestaque ? 1 : 0;
        statement.bindLong(7, _tmp);
        statement.bindLong(8, entity.id);
      }
    };
    this.__preparedStmtOfAtualizarDestaque = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE produtos SET isDestaque = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public void inserir(final Produto produto) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfProduto.insert(produto);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void excluir(final Produto produto) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfProduto.handle(produto);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void atualizar(final Produto produto) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfProduto.handle(produto);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void atualizarDestaque(final int idProduto, final boolean status) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfAtualizarDestaque.acquire();
    int _argIndex = 1;
    final int _tmp = status ? 1 : 0;
    _stmt.bindLong(_argIndex, _tmp);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, idProduto);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfAtualizarDestaque.release(_stmt);
    }
  }

  @Override
  public List<Produto> buscarProdutos(final String busca) {
    final String _sql = "SELECT * FROM produtos WHERE nome LIKE '%' || ? || '%'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (busca == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, busca);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfNome = CursorUtil.getColumnIndexOrThrow(_cursor, "nome");
      final int _cursorIndexOfDescricao = CursorUtil.getColumnIndexOrThrow(_cursor, "descricao");
      final int _cursorIndexOfPreco = CursorUtil.getColumnIndexOrThrow(_cursor, "preco");
      final int _cursorIndexOfImagemUri = CursorUtil.getColumnIndexOrThrow(_cursor, "imagemUri");
      final int _cursorIndexOfUsuarioId = CursorUtil.getColumnIndexOrThrow(_cursor, "usuarioId");
      final int _cursorIndexOfIsDestaque = CursorUtil.getColumnIndexOrThrow(_cursor, "isDestaque");
      final List<Produto> _result = new ArrayList<Produto>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Produto _item;
        _item = new Produto();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfNome)) {
          _item.nome = null;
        } else {
          _item.nome = _cursor.getString(_cursorIndexOfNome);
        }
        if (_cursor.isNull(_cursorIndexOfDescricao)) {
          _item.descricao = null;
        } else {
          _item.descricao = _cursor.getString(_cursorIndexOfDescricao);
        }
        _item.preco = _cursor.getDouble(_cursorIndexOfPreco);
        if (_cursor.isNull(_cursorIndexOfImagemUri)) {
          _item.imagemUri = null;
        } else {
          _item.imagemUri = _cursor.getString(_cursorIndexOfImagemUri);
        }
        _item.usuarioId = _cursor.getInt(_cursorIndexOfUsuarioId);
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsDestaque);
        _item.isDestaque = _tmp != 0;
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Produto> buscarProdutosDoLojista(final int idUsuario) {
    final String _sql = "SELECT * FROM produtos WHERE usuarioId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, idUsuario);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfNome = CursorUtil.getColumnIndexOrThrow(_cursor, "nome");
      final int _cursorIndexOfDescricao = CursorUtil.getColumnIndexOrThrow(_cursor, "descricao");
      final int _cursorIndexOfPreco = CursorUtil.getColumnIndexOrThrow(_cursor, "preco");
      final int _cursorIndexOfImagemUri = CursorUtil.getColumnIndexOrThrow(_cursor, "imagemUri");
      final int _cursorIndexOfUsuarioId = CursorUtil.getColumnIndexOrThrow(_cursor, "usuarioId");
      final int _cursorIndexOfIsDestaque = CursorUtil.getColumnIndexOrThrow(_cursor, "isDestaque");
      final List<Produto> _result = new ArrayList<Produto>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Produto _item;
        _item = new Produto();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfNome)) {
          _item.nome = null;
        } else {
          _item.nome = _cursor.getString(_cursorIndexOfNome);
        }
        if (_cursor.isNull(_cursorIndexOfDescricao)) {
          _item.descricao = null;
        } else {
          _item.descricao = _cursor.getString(_cursorIndexOfDescricao);
        }
        _item.preco = _cursor.getDouble(_cursorIndexOfPreco);
        if (_cursor.isNull(_cursorIndexOfImagemUri)) {
          _item.imagemUri = null;
        } else {
          _item.imagemUri = _cursor.getString(_cursorIndexOfImagemUri);
        }
        _item.usuarioId = _cursor.getInt(_cursorIndexOfUsuarioId);
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsDestaque);
        _item.isDestaque = _tmp != 0;
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Produto buscarProdutoPorId(final int idProduto) {
    final String _sql = "SELECT * FROM produtos WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, idProduto);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfNome = CursorUtil.getColumnIndexOrThrow(_cursor, "nome");
      final int _cursorIndexOfDescricao = CursorUtil.getColumnIndexOrThrow(_cursor, "descricao");
      final int _cursorIndexOfPreco = CursorUtil.getColumnIndexOrThrow(_cursor, "preco");
      final int _cursorIndexOfImagemUri = CursorUtil.getColumnIndexOrThrow(_cursor, "imagemUri");
      final int _cursorIndexOfUsuarioId = CursorUtil.getColumnIndexOrThrow(_cursor, "usuarioId");
      final int _cursorIndexOfIsDestaque = CursorUtil.getColumnIndexOrThrow(_cursor, "isDestaque");
      final Produto _result;
      if (_cursor.moveToFirst()) {
        _result = new Produto();
        _result.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfNome)) {
          _result.nome = null;
        } else {
          _result.nome = _cursor.getString(_cursorIndexOfNome);
        }
        if (_cursor.isNull(_cursorIndexOfDescricao)) {
          _result.descricao = null;
        } else {
          _result.descricao = _cursor.getString(_cursorIndexOfDescricao);
        }
        _result.preco = _cursor.getDouble(_cursorIndexOfPreco);
        if (_cursor.isNull(_cursorIndexOfImagemUri)) {
          _result.imagemUri = null;
        } else {
          _result.imagemUri = _cursor.getString(_cursorIndexOfImagemUri);
        }
        _result.usuarioId = _cursor.getInt(_cursorIndexOfUsuarioId);
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsDestaque);
        _result.isDestaque = _tmp != 0;
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
  public List<Produto> buscarProdutosEmDestaque() {
    final String _sql = "SELECT * FROM produtos WHERE isDestaque = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfNome = CursorUtil.getColumnIndexOrThrow(_cursor, "nome");
      final int _cursorIndexOfDescricao = CursorUtil.getColumnIndexOrThrow(_cursor, "descricao");
      final int _cursorIndexOfPreco = CursorUtil.getColumnIndexOrThrow(_cursor, "preco");
      final int _cursorIndexOfImagemUri = CursorUtil.getColumnIndexOrThrow(_cursor, "imagemUri");
      final int _cursorIndexOfUsuarioId = CursorUtil.getColumnIndexOrThrow(_cursor, "usuarioId");
      final int _cursorIndexOfIsDestaque = CursorUtil.getColumnIndexOrThrow(_cursor, "isDestaque");
      final List<Produto> _result = new ArrayList<Produto>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Produto _item;
        _item = new Produto();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfNome)) {
          _item.nome = null;
        } else {
          _item.nome = _cursor.getString(_cursorIndexOfNome);
        }
        if (_cursor.isNull(_cursorIndexOfDescricao)) {
          _item.descricao = null;
        } else {
          _item.descricao = _cursor.getString(_cursorIndexOfDescricao);
        }
        _item.preco = _cursor.getDouble(_cursorIndexOfPreco);
        if (_cursor.isNull(_cursorIndexOfImagemUri)) {
          _item.imagemUri = null;
        } else {
          _item.imagemUri = _cursor.getString(_cursorIndexOfImagemUri);
        }
        _item.usuarioId = _cursor.getInt(_cursorIndexOfUsuarioId);
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsDestaque);
        _item.isDestaque = _tmp != 0;
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Produto> buscarProdutosPorNome(final String termoBusca) {
    final String _sql = "SELECT * FROM produtos WHERE nome LIKE '%' || ? || '%'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (termoBusca == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, termoBusca);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfNome = CursorUtil.getColumnIndexOrThrow(_cursor, "nome");
      final int _cursorIndexOfDescricao = CursorUtil.getColumnIndexOrThrow(_cursor, "descricao");
      final int _cursorIndexOfPreco = CursorUtil.getColumnIndexOrThrow(_cursor, "preco");
      final int _cursorIndexOfImagemUri = CursorUtil.getColumnIndexOrThrow(_cursor, "imagemUri");
      final int _cursorIndexOfUsuarioId = CursorUtil.getColumnIndexOrThrow(_cursor, "usuarioId");
      final int _cursorIndexOfIsDestaque = CursorUtil.getColumnIndexOrThrow(_cursor, "isDestaque");
      final List<Produto> _result = new ArrayList<Produto>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Produto _item;
        _item = new Produto();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfNome)) {
          _item.nome = null;
        } else {
          _item.nome = _cursor.getString(_cursorIndexOfNome);
        }
        if (_cursor.isNull(_cursorIndexOfDescricao)) {
          _item.descricao = null;
        } else {
          _item.descricao = _cursor.getString(_cursorIndexOfDescricao);
        }
        _item.preco = _cursor.getDouble(_cursorIndexOfPreco);
        if (_cursor.isNull(_cursorIndexOfImagemUri)) {
          _item.imagemUri = null;
        } else {
          _item.imagemUri = _cursor.getString(_cursorIndexOfImagemUri);
        }
        _item.usuarioId = _cursor.getInt(_cursorIndexOfUsuarioId);
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsDestaque);
        _item.isDestaque = _tmp != 0;
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int contarProdutosDoLojista(final int idUsuario) {
    final String _sql = "SELECT COUNT(*) FROM produtos WHERE usuarioId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, idUsuario);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
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
