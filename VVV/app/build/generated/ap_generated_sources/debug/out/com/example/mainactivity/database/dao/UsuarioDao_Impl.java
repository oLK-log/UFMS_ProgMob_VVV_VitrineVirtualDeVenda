package com.example.mainactivity.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.mainactivity.model.Usuario;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UsuarioDao_Impl implements UsuarioDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Usuario> __insertionAdapterOfUsuario;

  private final EntityDeletionOrUpdateAdapter<Usuario> __updateAdapterOfUsuario;

  public UsuarioDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUsuario = new EntityInsertionAdapter<Usuario>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `usuarios` (`idUsuario`,`nome`,`email`,`senha`,`fotoPath`,`tipoPerfil`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Usuario entity) {
        statement.bindLong(1, entity.idUsuario);
        if (entity.nome == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.nome);
        }
        if (entity.email == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.email);
        }
        if (entity.senha == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.senha);
        }
        if (entity.fotoPath == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.fotoPath);
        }
        if (entity.tipoPerfil == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.tipoPerfil);
        }
      }
    };
    this.__updateAdapterOfUsuario = new EntityDeletionOrUpdateAdapter<Usuario>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `usuarios` SET `idUsuario` = ?,`nome` = ?,`email` = ?,`senha` = ?,`fotoPath` = ?,`tipoPerfil` = ? WHERE `idUsuario` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Usuario entity) {
        statement.bindLong(1, entity.idUsuario);
        if (entity.nome == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.nome);
        }
        if (entity.email == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.email);
        }
        if (entity.senha == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.senha);
        }
        if (entity.fotoPath == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.fotoPath);
        }
        if (entity.tipoPerfil == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.tipoPerfil);
        }
        statement.bindLong(7, entity.idUsuario);
      }
    };
  }

  @Override
  public long cadastrar(final Usuario usuario) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfUsuario.insertAndReturnId(usuario);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void atualizarUsuario(final Usuario usuario) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfUsuario.handle(usuario);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Usuario fazerLogin(final String email, final String senha) {
    final String _sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (email == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, email);
    }
    _argIndex = 2;
    if (senha == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, senha);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdUsuario = CursorUtil.getColumnIndexOrThrow(_cursor, "idUsuario");
      final int _cursorIndexOfNome = CursorUtil.getColumnIndexOrThrow(_cursor, "nome");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfSenha = CursorUtil.getColumnIndexOrThrow(_cursor, "senha");
      final int _cursorIndexOfFotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "fotoPath");
      final int _cursorIndexOfTipoPerfil = CursorUtil.getColumnIndexOrThrow(_cursor, "tipoPerfil");
      final Usuario _result;
      if (_cursor.moveToFirst()) {
        _result = new Usuario();
        _result.idUsuario = _cursor.getInt(_cursorIndexOfIdUsuario);
        if (_cursor.isNull(_cursorIndexOfNome)) {
          _result.nome = null;
        } else {
          _result.nome = _cursor.getString(_cursorIndexOfNome);
        }
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _result.email = null;
        } else {
          _result.email = _cursor.getString(_cursorIndexOfEmail);
        }
        if (_cursor.isNull(_cursorIndexOfSenha)) {
          _result.senha = null;
        } else {
          _result.senha = _cursor.getString(_cursorIndexOfSenha);
        }
        if (_cursor.isNull(_cursorIndexOfFotoPath)) {
          _result.fotoPath = null;
        } else {
          _result.fotoPath = _cursor.getString(_cursorIndexOfFotoPath);
        }
        if (_cursor.isNull(_cursorIndexOfTipoPerfil)) {
          _result.tipoPerfil = null;
        } else {
          _result.tipoPerfil = _cursor.getString(_cursorIndexOfTipoPerfil);
        }
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
  public Usuario buscarUsuarioPorId(final int idUsuario) {
    final String _sql = "SELECT * FROM usuarios WHERE idUsuario = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, idUsuario);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdUsuario = CursorUtil.getColumnIndexOrThrow(_cursor, "idUsuario");
      final int _cursorIndexOfNome = CursorUtil.getColumnIndexOrThrow(_cursor, "nome");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfSenha = CursorUtil.getColumnIndexOrThrow(_cursor, "senha");
      final int _cursorIndexOfFotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "fotoPath");
      final int _cursorIndexOfTipoPerfil = CursorUtil.getColumnIndexOrThrow(_cursor, "tipoPerfil");
      final Usuario _result;
      if (_cursor.moveToFirst()) {
        _result = new Usuario();
        _result.idUsuario = _cursor.getInt(_cursorIndexOfIdUsuario);
        if (_cursor.isNull(_cursorIndexOfNome)) {
          _result.nome = null;
        } else {
          _result.nome = _cursor.getString(_cursorIndexOfNome);
        }
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _result.email = null;
        } else {
          _result.email = _cursor.getString(_cursorIndexOfEmail);
        }
        if (_cursor.isNull(_cursorIndexOfSenha)) {
          _result.senha = null;
        } else {
          _result.senha = _cursor.getString(_cursorIndexOfSenha);
        }
        if (_cursor.isNull(_cursorIndexOfFotoPath)) {
          _result.fotoPath = null;
        } else {
          _result.fotoPath = _cursor.getString(_cursorIndexOfFotoPath);
        }
        if (_cursor.isNull(_cursorIndexOfTipoPerfil)) {
          _result.tipoPerfil = null;
        } else {
          _result.tipoPerfil = _cursor.getString(_cursorIndexOfTipoPerfil);
        }
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
  public Usuario buscaRedefinirSenha(final String email, final String nome) {
    final String _sql = "SELECT * FROM usuarios WHERE email = ? AND nome = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (email == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, email);
    }
    _argIndex = 2;
    if (nome == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, nome);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdUsuario = CursorUtil.getColumnIndexOrThrow(_cursor, "idUsuario");
      final int _cursorIndexOfNome = CursorUtil.getColumnIndexOrThrow(_cursor, "nome");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfSenha = CursorUtil.getColumnIndexOrThrow(_cursor, "senha");
      final int _cursorIndexOfFotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "fotoPath");
      final int _cursorIndexOfTipoPerfil = CursorUtil.getColumnIndexOrThrow(_cursor, "tipoPerfil");
      final Usuario _result;
      if (_cursor.moveToFirst()) {
        _result = new Usuario();
        _result.idUsuario = _cursor.getInt(_cursorIndexOfIdUsuario);
        if (_cursor.isNull(_cursorIndexOfNome)) {
          _result.nome = null;
        } else {
          _result.nome = _cursor.getString(_cursorIndexOfNome);
        }
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _result.email = null;
        } else {
          _result.email = _cursor.getString(_cursorIndexOfEmail);
        }
        if (_cursor.isNull(_cursorIndexOfSenha)) {
          _result.senha = null;
        } else {
          _result.senha = _cursor.getString(_cursorIndexOfSenha);
        }
        if (_cursor.isNull(_cursorIndexOfFotoPath)) {
          _result.fotoPath = null;
        } else {
          _result.fotoPath = _cursor.getString(_cursorIndexOfFotoPath);
        }
        if (_cursor.isNull(_cursorIndexOfTipoPerfil)) {
          _result.tipoPerfil = null;
        } else {
          _result.tipoPerfil = _cursor.getString(_cursorIndexOfTipoPerfil);
        }
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
  public Usuario buscarEmail(final String email) {
    final String _sql = "SELECT * FROM usuarios WHERE email = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (email == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, email);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdUsuario = CursorUtil.getColumnIndexOrThrow(_cursor, "idUsuario");
      final int _cursorIndexOfNome = CursorUtil.getColumnIndexOrThrow(_cursor, "nome");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfSenha = CursorUtil.getColumnIndexOrThrow(_cursor, "senha");
      final int _cursorIndexOfFotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "fotoPath");
      final int _cursorIndexOfTipoPerfil = CursorUtil.getColumnIndexOrThrow(_cursor, "tipoPerfil");
      final Usuario _result;
      if (_cursor.moveToFirst()) {
        _result = new Usuario();
        _result.idUsuario = _cursor.getInt(_cursorIndexOfIdUsuario);
        if (_cursor.isNull(_cursorIndexOfNome)) {
          _result.nome = null;
        } else {
          _result.nome = _cursor.getString(_cursorIndexOfNome);
        }
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _result.email = null;
        } else {
          _result.email = _cursor.getString(_cursorIndexOfEmail);
        }
        if (_cursor.isNull(_cursorIndexOfSenha)) {
          _result.senha = null;
        } else {
          _result.senha = _cursor.getString(_cursorIndexOfSenha);
        }
        if (_cursor.isNull(_cursorIndexOfFotoPath)) {
          _result.fotoPath = null;
        } else {
          _result.fotoPath = _cursor.getString(_cursorIndexOfFotoPath);
        }
        if (_cursor.isNull(_cursorIndexOfTipoPerfil)) {
          _result.tipoPerfil = null;
        } else {
          _result.tipoPerfil = _cursor.getString(_cursorIndexOfTipoPerfil);
        }
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
