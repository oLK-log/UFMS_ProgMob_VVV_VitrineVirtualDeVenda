package com.example.mainactivity.database.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mainactivity.model.Usuario;

@Dao
public interface UsuarioDao {
    @Insert
    long cadastrar(Usuario usuario);

    //query que buscar tudo do usuario que tiver determinado Login e Senha
    @Query("SELECT * FROM usuarios WHERE email = :email AND senha = :senha LIMIT 1")
    Usuario fazerLogin(String email, String senha);

    //query para buscar usuario com determinado id
    @Query("SELECT * FROM usuarios WHERE idUsuario = :idUsuario LIMIT 1")
    Usuario buscarUsuarioPorId(int idUsuario);
    //query para buscar usuario que tiver determinado Login e nome
    @Query("SELECT * FROM usuarios WHERE email = :email AND nome = :nome LIMIT 1")
    Usuario buscaRedefinirSenha(String email, String nome);
    //Query para atualizar dados de um usuario
    @androidx.room.Update
    void atualizarUsuario(Usuario usuario);
    //buscar usuario pelo email
    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    Usuario buscarEmail(String email);
}
