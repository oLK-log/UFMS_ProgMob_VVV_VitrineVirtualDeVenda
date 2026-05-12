package com.example.mainactivity.core;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mainactivity.R;
import com.example.mainactivity.database.AppDatabase;
import com.example.mainactivity.database.dao.UsuarioDao;
import com.example.mainactivity.model.Usuario;

public class RedefinirSenhaActivity extends AppCompatActivity {
    private EditText editNomeRedefinir, editEmailRedefinir, editSenhaRedefinir;
    private Button btnRedefinir;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_senha);
        //fzdn conexoes
        editNomeRedefinir = findViewById(R.id.editNomeRedefinir);
        editEmailRedefinir = findViewById(R.id.editEmailRedefinir);
        editSenhaRedefinir = findViewById(R.id.editSenhaRedefinir);
        btnRedefinir = findViewById(R.id.btnRedefinir);

        //config btn Redefinir
        btnRedefinir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){redefinirSenhaNoBanco();}
        });
    }

    //altera a senha no banco de dados por meio de uma validacao do nome completo
    private void redefinirSenhaNoBanco(){
        String nomeRedefinir = editNomeRedefinir.getText().toString().trim();
        String email = editEmailRedefinir.getText().toString().trim();
        String novaSenha = editSenhaRedefinir.getText().toString().trim();

        if(nomeRedefinir.isEmpty() || email.isEmpty() || novaSenha.isEmpty()){
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        UsuarioDao dao = AppDatabase.getInstance(this).usuarioDao();
        Usuario usuarioRedefinir = dao.buscaRedefinirSenha(email, nomeRedefinir);

        if(usuarioRedefinir != null){
            usuarioRedefinir.senha=novaSenha;
            dao.atualizarUsuario(usuarioRedefinir);
            Toast.makeText(this, "Senha Atualizada!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RedefinirSenhaActivity.this, LoginActivity.class);
            startActivity(intent);

        }else{
            Toast.makeText(this, "O E-mail ou Nome está incorreto! Verifique e tente novamente.", Toast.LENGTH_LONG).show();
        }
    }
}
