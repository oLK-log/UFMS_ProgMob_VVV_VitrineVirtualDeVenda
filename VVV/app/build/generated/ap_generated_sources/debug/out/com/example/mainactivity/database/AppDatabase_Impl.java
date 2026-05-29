package com.example.mainactivity.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.example.mainactivity.database.dao.PedidoDao;
import com.example.mainactivity.database.dao.PedidoDao_Impl;
import com.example.mainactivity.database.dao.ProdutoDao;
import com.example.mainactivity.database.dao.ProdutoDao_Impl;
import com.example.mainactivity.database.dao.UsuarioDao;
import com.example.mainactivity.database.dao.UsuarioDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile UsuarioDao _usuarioDao;

  private volatile ProdutoDao _produtoDao;

  private volatile PedidoDao _pedidoDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `usuarios` (`idUsuario` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nome` TEXT, `email` TEXT, `senha` TEXT, `fotoPath` TEXT, `tipoPerfil` TEXT, `endereco` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `produtos` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nome` TEXT, `descricao` TEXT, `preco` REAL NOT NULL, `imagemUri` TEXT, `usuarioId` INTEGER NOT NULL, `isDestaque` INTEGER NOT NULL, `videoUri` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `item_pedido` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `usuarioId` INTEGER NOT NULL, `produtoId` INTEGER NOT NULL, `quantidade` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `pedidos` (`idPedido` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `usuarioId` INTEGER NOT NULL, `nomeCliente` TEXT, `emailCliente` TEXT, `valorTotal` REAL NOT NULL, `dataTimestamp` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `itens_pedido_finalizado` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `pedidoId` INTEGER NOT NULL, `produtoId` INTEGER NOT NULL, `quantidade` INTEGER NOT NULL, `precoUnitarioHistorico` REAL NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '5e83f3065ec98e2cb16ab96b81f52e74')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `usuarios`");
        db.execSQL("DROP TABLE IF EXISTS `produtos`");
        db.execSQL("DROP TABLE IF EXISTS `item_pedido`");
        db.execSQL("DROP TABLE IF EXISTS `pedidos`");
        db.execSQL("DROP TABLE IF EXISTS `itens_pedido_finalizado`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsUsuarios = new HashMap<String, TableInfo.Column>(7);
        _columnsUsuarios.put("idUsuario", new TableInfo.Column("idUsuario", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsuarios.put("nome", new TableInfo.Column("nome", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsuarios.put("email", new TableInfo.Column("email", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsuarios.put("senha", new TableInfo.Column("senha", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsuarios.put("fotoPath", new TableInfo.Column("fotoPath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsuarios.put("tipoPerfil", new TableInfo.Column("tipoPerfil", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsuarios.put("endereco", new TableInfo.Column("endereco", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsuarios = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsuarios = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsuarios = new TableInfo("usuarios", _columnsUsuarios, _foreignKeysUsuarios, _indicesUsuarios);
        final TableInfo _existingUsuarios = TableInfo.read(db, "usuarios");
        if (!_infoUsuarios.equals(_existingUsuarios)) {
          return new RoomOpenHelper.ValidationResult(false, "usuarios(com.example.mainactivity.model.Usuario).\n"
                  + " Expected:\n" + _infoUsuarios + "\n"
                  + " Found:\n" + _existingUsuarios);
        }
        final HashMap<String, TableInfo.Column> _columnsProdutos = new HashMap<String, TableInfo.Column>(8);
        _columnsProdutos.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProdutos.put("nome", new TableInfo.Column("nome", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProdutos.put("descricao", new TableInfo.Column("descricao", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProdutos.put("preco", new TableInfo.Column("preco", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProdutos.put("imagemUri", new TableInfo.Column("imagemUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProdutos.put("usuarioId", new TableInfo.Column("usuarioId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProdutos.put("isDestaque", new TableInfo.Column("isDestaque", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProdutos.put("videoUri", new TableInfo.Column("videoUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProdutos = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProdutos = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProdutos = new TableInfo("produtos", _columnsProdutos, _foreignKeysProdutos, _indicesProdutos);
        final TableInfo _existingProdutos = TableInfo.read(db, "produtos");
        if (!_infoProdutos.equals(_existingProdutos)) {
          return new RoomOpenHelper.ValidationResult(false, "produtos(com.example.mainactivity.model.Produto).\n"
                  + " Expected:\n" + _infoProdutos + "\n"
                  + " Found:\n" + _existingProdutos);
        }
        final HashMap<String, TableInfo.Column> _columnsItemPedido = new HashMap<String, TableInfo.Column>(4);
        _columnsItemPedido.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsItemPedido.put("usuarioId", new TableInfo.Column("usuarioId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsItemPedido.put("produtoId", new TableInfo.Column("produtoId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsItemPedido.put("quantidade", new TableInfo.Column("quantidade", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysItemPedido = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesItemPedido = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoItemPedido = new TableInfo("item_pedido", _columnsItemPedido, _foreignKeysItemPedido, _indicesItemPedido);
        final TableInfo _existingItemPedido = TableInfo.read(db, "item_pedido");
        if (!_infoItemPedido.equals(_existingItemPedido)) {
          return new RoomOpenHelper.ValidationResult(false, "item_pedido(com.example.mainactivity.model.ItemPedido).\n"
                  + " Expected:\n" + _infoItemPedido + "\n"
                  + " Found:\n" + _existingItemPedido);
        }
        final HashMap<String, TableInfo.Column> _columnsPedidos = new HashMap<String, TableInfo.Column>(6);
        _columnsPedidos.put("idPedido", new TableInfo.Column("idPedido", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPedidos.put("usuarioId", new TableInfo.Column("usuarioId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPedidos.put("nomeCliente", new TableInfo.Column("nomeCliente", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPedidos.put("emailCliente", new TableInfo.Column("emailCliente", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPedidos.put("valorTotal", new TableInfo.Column("valorTotal", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPedidos.put("dataTimestamp", new TableInfo.Column("dataTimestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPedidos = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPedidos = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPedidos = new TableInfo("pedidos", _columnsPedidos, _foreignKeysPedidos, _indicesPedidos);
        final TableInfo _existingPedidos = TableInfo.read(db, "pedidos");
        if (!_infoPedidos.equals(_existingPedidos)) {
          return new RoomOpenHelper.ValidationResult(false, "pedidos(com.example.mainactivity.model.Pedido).\n"
                  + " Expected:\n" + _infoPedidos + "\n"
                  + " Found:\n" + _existingPedidos);
        }
        final HashMap<String, TableInfo.Column> _columnsItensPedidoFinalizado = new HashMap<String, TableInfo.Column>(5);
        _columnsItensPedidoFinalizado.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsItensPedidoFinalizado.put("pedidoId", new TableInfo.Column("pedidoId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsItensPedidoFinalizado.put("produtoId", new TableInfo.Column("produtoId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsItensPedidoFinalizado.put("quantidade", new TableInfo.Column("quantidade", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsItensPedidoFinalizado.put("precoUnitarioHistorico", new TableInfo.Column("precoUnitarioHistorico", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysItensPedidoFinalizado = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesItensPedidoFinalizado = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoItensPedidoFinalizado = new TableInfo("itens_pedido_finalizado", _columnsItensPedidoFinalizado, _foreignKeysItensPedidoFinalizado, _indicesItensPedidoFinalizado);
        final TableInfo _existingItensPedidoFinalizado = TableInfo.read(db, "itens_pedido_finalizado");
        if (!_infoItensPedidoFinalizado.equals(_existingItensPedidoFinalizado)) {
          return new RoomOpenHelper.ValidationResult(false, "itens_pedido_finalizado(com.example.mainactivity.model.ItemPedidoFinalizado).\n"
                  + " Expected:\n" + _infoItensPedidoFinalizado + "\n"
                  + " Found:\n" + _existingItensPedidoFinalizado);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "5e83f3065ec98e2cb16ab96b81f52e74", "34a5c5c6679ab1f53de3e615a77bc9d1");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "usuarios","produtos","item_pedido","pedidos","itens_pedido_finalizado");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `usuarios`");
      _db.execSQL("DELETE FROM `produtos`");
      _db.execSQL("DELETE FROM `item_pedido`");
      _db.execSQL("DELETE FROM `pedidos`");
      _db.execSQL("DELETE FROM `itens_pedido_finalizado`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(UsuarioDao.class, UsuarioDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ProdutoDao.class, ProdutoDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PedidoDao.class, PedidoDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public UsuarioDao usuarioDao() {
    if (_usuarioDao != null) {
      return _usuarioDao;
    } else {
      synchronized(this) {
        if(_usuarioDao == null) {
          _usuarioDao = new UsuarioDao_Impl(this);
        }
        return _usuarioDao;
      }
    }
  }

  @Override
  public ProdutoDao produtoDao() {
    if (_produtoDao != null) {
      return _produtoDao;
    } else {
      synchronized(this) {
        if(_produtoDao == null) {
          _produtoDao = new ProdutoDao_Impl(this);
        }
        return _produtoDao;
      }
    }
  }

  @Override
  public PedidoDao pedidoDao() {
    if (_pedidoDao != null) {
      return _pedidoDao;
    } else {
      synchronized(this) {
        if(_pedidoDao == null) {
          _pedidoDao = new PedidoDao_Impl(this);
        }
        return _pedidoDao;
      }
    }
  }
}
