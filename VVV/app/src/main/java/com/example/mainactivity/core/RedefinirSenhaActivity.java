package com.example.mainactivity.core;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mainactivity.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class RedefinirSenhaActivity extends AppCompatActivity{
    private TextInputEditText editEmailRecuperacao;
    private Button btnEnviarLink;
    private TextView txtVoltarLogin;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_senha);

        //inicializar firebase
        auth = FirebaseAuth.getInstance();

        editEmailRecuperacao= findViewById(R.id.editEmailRecuperacao);
        btnEnviarLink = findViewById(R.id.btnEnviarLink);
        txtVoltarLogin = findViewById(R.id.txtVoltarLogin);

        txtVoltarLogin.setOnClickListener(v -> finish());

        btnEnviarLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                enviarEmailRecuperacao();
            }
        });
    }

    private void enviarEmailRecuperacao(){
        String email = editEmailRecuperacao.getText().toString().trim();
        //validacoes
        if(email.isEmpty()){
            Toast.makeText(this, "O campo não pode estar vazio!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Insira um e-mail válido!", Toast.LENGTH_SHORT).show();
            return;
        }

        btnEnviarLink.setEnabled(false);
        btnEnviarLink.setText("Enviando...");

        //conectando/solicitando ao firebase
        auth.sendPasswordResetEmail(email).addOnCompleteListener(
                task -> {
                    btnEnviarLink.setEnabled(true);
                    btnEnviarLink.setText("Enviar Link");

                    if(task.isSuccessful()){
                        Toast.makeText(RedefinirSenhaActivity.this, "E-mail de recuperação enviado! Verifique sua caixa de entrada.", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(RedefinirSenhaActivity.this, "Erro ao enviar. Verifique se o e-mail está correto!", Toast.LENGTH_SHORT).show();
                        String erroExato = "Erro desconhecido";
                        if (task.getException() != null) {
                            erroExato = task.getException().getMessage();
                        }

                        Toast.makeText(RedefinirSenhaActivity.this,
                                "Falha: " + erroExato,
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}



/*
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
*/