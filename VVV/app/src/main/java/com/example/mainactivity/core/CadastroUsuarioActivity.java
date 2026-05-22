package com.example.mainactivity.core;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mainactivity.R;
import com.example.mainactivity.database.AppDatabase;
import com.example.mainactivity.model.Usuario;

import java.io.File;
import java.io.FileOutputStream;

public class CadastroUsuarioActivity extends AppCompatActivity{
    private ImageView imgFotoPerfil;
    private EditText editNome, editEmail, editSenha;
    private Button btnTirarFoto, btnCadastrar;
    //variavel para guardar foto antes de salvar
    private Bitmap fotoCapturada = null;
    private RadioGroup rgTipoUsuario;
    private TextView txtRegraTamanho, txtRegraMaiuscula, txtRegraNumero, txtRegraEspecial;
    private boolean isSenhaForte = false;
    private com.google.firebase.auth.FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario); //conexao xom xlm
        auth = com.google.firebase.auth.FirebaseAuth.getInstance();
        //fazer a conexao java e xml
        imgFotoPerfil = findViewById(R.id.imgFotoPerfil);
        editNome = findViewById(R.id.editNome);
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        btnTirarFoto = findViewById(R.id.btnTirarFoto);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        rgTipoUsuario = findViewById(R.id.rgTipoUsuario);
        txtRegraEspecial = findViewById(R.id.txtRegraEspecial);
        txtRegraMaiuscula = findViewById(R.id.txtRegraMaiuscula);
        txtRegraNumero = findViewById(R.id.txtRegraNumero);
        txtRegraTamanho = findViewById(R.id.txtRegraTamanho);

        //para validacao de senha
        editSenha.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validarForcaDaSenha(s.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });

        //configurar acao da camera
        btnTirarFoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){//cria a intencao de abrir camera
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                abrirCamera.launch(intentCamera);
            }
        });

        //Configurar acao de salvar no banco de dados
        btnCadastrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                salvarUsuarioNoBanco();
            }
        });
    }

    //pegar imagem após a camera fechar
    private final ActivityResultLauncher<Intent> abrirCamera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null){
                    //pega a foto, salva na variavel e mostra na tela
                    fotoCapturada = (Bitmap) result.getData().getExtras().get("data");
                    imgFotoPerfil.setImageBitmap(fotoCapturada);
                }
            }
    );

    //funcao para pegar a imagem(bit map) e salvar no armazenamentp
    private String salvarImagemLocal(Bitmap bitmap){
        if(bitmap == null) return "";
        try{
            //criando arquivo localmente
            File arquivo = new File(getFilesDir(), "perfil_" + System.currentTimeMillis()+ ".png");
            FileOutputStream out = new FileOutputStream(arquivo);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            //retornando endereco de URI onde a imagem está salva
            return Uri.fromFile(arquivo).toString();
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    //Banco de DAdos - ROOM e Firebase
    private void salvarUsuarioNoBanco() {
        String nome = editNome.getText().toString();
        String email = editEmail.getText().toString().trim();
        String senha = editSenha.getText().toString();

        //validacoes
        if(nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {//se vazio
            Toast.makeText(this, "Preencha todos os campo!", Toast.LENGTH_SHORT).show();
            return;
        }
        // Verifica se o formato do e-mail é válido
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Por favor, insira um e-mail válido!", Toast.LENGTH_SHORT).show();
            return;
        }
        //verifica se a senha tá forte
        if (!isSenhaForte) {
            Toast.makeText(this, "A senha ainda não cumpre todos os requisitos de segurança!", Toast.LENGTH_LONG).show();
            return; // Bloqueia o registo
        }
        //desativar botao
        btnCadastrar.setEnabled(false);
        btnCadastrar.setText("Criando conta...");

        //aqui houve a mudanca para adaptar ao firebase
        auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()){

                        String caminhoFoto = salvarImagemLocal(fotoCapturada);
                        //tipo de perfil
                        String tipoSelecionado = "CLIENTE";
                        int idRadioSelecionado = rgTipoUsuario.getCheckedRadioButtonId();
                        if(idRadioSelecionado == R.id.rbLojista){
                            tipoSelecionado = "LOJISTA";
                        }
                        //mantendo seguranca com hash
                        String senhaComHash = com.example.mainactivity.security.SecurityUtils.hashPassword(senha);

                        //molde usuario
                        Usuario novoUsuario = new Usuario();
                        novoUsuario.nome = nome;
                        novoUsuario.email = email;
                        novoUsuario.senha = senhaComHash;//salvar a senha com hash
                        novoUsuario.fotoPath = caminhoFoto;
                        novoUsuario.tipoPerfil=tipoSelecionado;
                        //salva o molde
                        AppDatabase.getInstance(this).usuarioDao().cadastrar(novoUsuario);

                        //Limpar campis
                        Toast.makeText(this, "Usuário cadastrado com Suscesso!", Toast.LENGTH_LONG).show();
                        editNome.setText("");
                        editEmail.setText("");
                        editSenha.setText("");
                        imgFotoPerfil.setImageBitmap(null);

                        //voltar tela de login
                        finish();
                    } else{
                        btnCadastrar.setEnabled(true);
                        btnCadastrar.setText("Cadastrar");

                        String mensagemErro = "Erro ao cadastrar na nuvem.";
                        if(task.getException() != null){
                            mensagemErro = task.getException().getMessage();
                        }
                        Toast.makeText(CadastroUsuarioActivity.this, "Falha:" + mensagemErro, Toast.LENGTH_SHORT).show();
                    }
        });
    }

    private void validarForcaDaSenha(String senha){
        //aqui usei regex/expressoes regulares
        boolean temTamanho = senha.length() >= 8;
        boolean temMaiuscula = senha.matches(".*[A-Z].*");
        boolean temNumero = senha.matches(".*[0-9].*");
        boolean temEspecial = senha.matches(".*[@#$%^&+=!?_*~\\-].*");

        //conf cores
        txtRegraTamanho.setTextColor(android.graphics.Color.parseColor(temTamanho ? "#4CAF50" : "#FF0000"));
        txtRegraMaiuscula.setTextColor(android.graphics.Color.parseColor(temMaiuscula ? "#4CAF50" : "#FF0000"));
        txtRegraNumero.setTextColor(android.graphics.Color.parseColor(temNumero ? "#4CAF50" : "#FF0000"));
        txtRegraEspecial.setTextColor(android.graphics.Color.parseColor(temEspecial ? "#4CAF50" : "#FF0000"));

        isSenhaForte = temTamanho && temMaiuscula && temNumero && temEspecial;
    }

}