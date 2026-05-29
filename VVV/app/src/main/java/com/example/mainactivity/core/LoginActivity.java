package com.example.mainactivity.core;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mainactivity.R;
import com.example.mainactivity.cliente.PainelClienteActivity;
import com.example.mainactivity.database.AppDatabase;
import com.example.mainactivity.lojista.PainelLojistaActivity;
import com.example.mainactivity.model.Usuario;
import com.example.mainactivity.security.SecurityUtils;

public class LoginActivity extends AppCompatActivity {
    private EditText editLoginEmail, editLoginSenha;
    private Button btnEntrar;
    private TextView txtIrParaCadastro, txtEntrarConvidado, txtRedefinirSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //conectando xml ao java
        editLoginEmail = findViewById(R.id.editLoginEmail);
        editLoginSenha = findViewById(R.id.editLoginSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        txtIrParaCadastro = findViewById(R.id.txtIrParaCadastro);
        txtEntrarConvidado = findViewById(R.id.txtEntrarConvidado);
        txtRedefinirSenha = findViewById(R.id.txtRedefinirSenha);

        //acoes
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tentarLogin();
            }
        });
        //cadastrar
        txtIrParaCadastro.setOnClickListener(new View.OnClickListener() { //captura a intencao de mudar de tela
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
                startActivity(intent);
            }
        });

        //Entrar como convidade
        txtEntrarConvidado.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SharedPreferences preferenciais = getSharedPreferences("sessao_vvv", MODE_PRIVATE);
                preferenciais.edit().putInt("idUsuario", -1).apply();//-1 indica que não está logado/eh convidade
                Toast.makeText(LoginActivity.this, "Navegando como visitante", Toast.LENGTH_SHORT).show();
                //acessa vitrine Cliente como convidade
                Intent intent = new Intent(LoginActivity.this, PainelClienteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Redefinir Senha
        txtRedefinirSenha.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, RedefinirSenhaActivity.class);
                startActivity(intent);

            }
        });
    }
    //banco de dados
    private void tentarLogin() {
        String email = editLoginEmail.getText().toString();
        String senhaDigitada = editLoginSenha.getText().toString();

        if(email.isEmpty() || senhaDigitada.isEmpty()) {
            Toast.makeText(this, "Preencha o e-mail e senha!", Toast.LENGTH_SHORT).show();
            return;
        }
        // Verifica se o formato do e-mail é válido
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Por favor, insira um e-mail válido!", Toast.LENGTH_SHORT).show();
            return;
        }

        //para aplicar validacao por hash precisamos busca somente o email
        Usuario usuarioBuscado = AppDatabase.getInstance(this).usuarioDao().buscarEmail(email);

        //posteriormente, verificamos se o usuario exite e se a senha bate com o hash
        if(usuarioBuscado != null && SecurityUtils.verifyPassword(senhaDigitada, usuarioBuscado.senha)){
            android.content.SharedPreferences preferenciais = getSharedPreferences("sessao_vvv", MODE_PRIVATE);
            preferenciais.edit().putInt("idUsuario", usuarioBuscado.idUsuario).apply();
            //informar que usuario está logado
            Toast.makeText(this,"Usuário "+ usuarioBuscado.nome + " logado.", Toast.LENGTH_LONG).show();
            if(usuarioBuscado.tipoPerfil != null && usuarioBuscado.tipoPerfil.equals("LOJISTA")){
                Intent intent = new Intent(LoginActivity.this, PainelLojistaActivity.class);
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(LoginActivity.this, PainelClienteActivity.class);
                startActivity(intent);
                finish();
            }
        } else{

            Toast.makeText(this, "E-mail ou senha inválido!", Toast.LENGTH_SHORT).show();
        }
    }
}