package com.example.mainactivity.core.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mainactivity.R;
import com.example.mainactivity.cliente.HistoricoPedidoClienteActivity;
import com.example.mainactivity.core.LoginActivity;
import com.example.mainactivity.database.AppDatabase;
import com.example.mainactivity.model.Usuario;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import com.skydoves.colorpickerview.ColorEnvelope;
import android.content.DialogInterface;

import java.util.List;

public class PerfilFragment extends Fragment {
    private ImageView imgAbaPerfil;
    private TextView txtNomeAbaPerfil, txtTipoPerfil;
    private Button btnHistoricoPedidos;
    private LinearLayout layoutEnderecoLojista;
    private EditText editEnderecoLojista, editCorPrimaria, editCorSecundaria, editCorFundo;
    private Button btnSalvarDadosLojista;
    private ImageButton btnPaletaPrimaria, btnPaletaSecundaria, btnPaletaFundo;

    @Nullable//indica que um parametro, retorno de metodo ou varioavel pode conter um valor null
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        Button btnSairSessao = view.findViewById(R.id.btnSairSessao);
        imgAbaPerfil = view.findViewById(R.id.imgAbaPerfil);
        txtNomeAbaPerfil = view.findViewById(R.id.txtNomeAbaPerfil);
        txtTipoPerfil = view.findViewById(R.id.txtTipoPerfil);
        btnHistoricoPedidos = view.findViewById(R.id.btnHistoricoPedidos);
        layoutEnderecoLojista = view.findViewById(R.id.layoutDadosLojista);
        editEnderecoLojista = view.findViewById(R.id.editEnderecoLojista);
        btnSalvarDadosLojista = view.findViewById(R.id.btnSalvarDadosLojista);
        editCorPrimaria = view.findViewById(R.id.editCorPrincipal);
        editCorSecundaria = view.findViewById(R.id.editCorSecundaria);
        editCorFundo = view.findViewById(R.id.editCorFundo);
        btnPaletaPrimaria = view.findViewById(R.id.btnPaletaPrimaria);
        btnPaletaSecundaria = view.findViewById(R.id.btnPaletaSecundaria);
        btnPaletaFundo = view.findViewById(R.id.btnPaletaFundo);


        carregarDadosDoUsuario();

        //setando cores
        btnPaletaPrimaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoDeCor(editCorPrimaria);
            }
        });
        btnPaletaSecundaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoDeCor(editCorSecundaria);
            }
        });
        btnPaletaFundo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoDeCor(editCorFundo);
            }
        });

        //salvando endereco do lojista
        btnSalvarDadosLojista.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                salvarDadosNoBanco();
            }
        });

        //botao de histórico de pedidos
        btnHistoricoPedidos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), HistoricoPedidoClienteActivity.class);
                startActivity(intent);
            }
        });

        btnSairSessao.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SharedPreferences preferenciais = getActivity().getSharedPreferences("sessao_vvv", Context.MODE_PRIVATE);
                preferenciais.edit().clear().apply();//limpa dados de sessa
                Toast.makeText(getContext(), getString(R.string.msg_logout), Toast.LENGTH_SHORT).show();
                //Voltando para tela de Login
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

                if(getActivity() != null){
                    getActivity().finish();
                }
            }
        });

        carregarEConfigurarCoresDaLoja(view);
        return view;
    }

    private void carregarDadosDoUsuario(){
        SharedPreferences preferenciais = getContext().getSharedPreferences("sessao_vvv", Context.MODE_PRIVATE);
        int idUsuario = preferenciais.getInt("idUsuario", -1);

        if(idUsuario == -1){//se visitante
            txtNomeAbaPerfil.setText("Visitante");
            imgAbaPerfil.setImageResource(android.R.drawable.ic_menu_gallery);
            txtTipoPerfil.setVisibility(View.INVISIBLE);
            btnHistoricoPedidos.setVisibility(View.GONE);//n ver botao de historico de oedido
            layoutEnderecoLojista.setVisibility(View.GONE);
            return;
        }else{
            Usuario usuario = AppDatabase.getInstance(getContext()).usuarioDao().buscarUsuarioPorId(idUsuario);
            if(usuario != null){
                txtNomeAbaPerfil.setText(usuario.nome);
                if (usuario.fotoPath != null && !usuario.fotoPath.isEmpty()) {
                    try {
                        imgAbaPerfil.setImageURI(Uri.parse(usuario.fotoPath));
                    } catch (SecurityException e) {
                        imgAbaPerfil.setImageResource(android.R.drawable.ic_menu_camera);
                    }
                }
            }
            //colocar o tipo de usuario
            if(usuario.tipoPerfil.equals("CLIENTE")){
                txtTipoPerfil.setText("Cliente");
                btnHistoricoPedidos.setVisibility(View.VISIBLE);
                layoutEnderecoLojista.setVisibility(View.GONE);
            }else {
                txtTipoPerfil.setText("Lojista");
                btnHistoricoPedidos.setVisibility(View.GONE);
                layoutEnderecoLojista.setVisibility(View.VISIBLE);
                if(usuario.endereco != null){
                    editEnderecoLojista.setText(usuario.endereco);
                }
                if(usuario.corPrimariaLoja != null){
                    editCorPrimaria.setText(usuario.corPrimariaLoja);
                }
                if(usuario.corSecundariaLoja != null){
                    editCorSecundaria.setText(usuario.corSecundariaLoja);
                }
                if(usuario.corFundoLoja != null){
                    editCorFundo.setText(usuario.corFundoLoja);
                }
            }
        }
    }

    //metodo para salvar o endereco no banco
    private void salvarDadosNoBanco(){
        String novoEndereco = editEnderecoLojista.getText().toString().trim();
        String novaCorPrimaria = editCorPrimaria.getText().toString().trim();
        String novaCorSecundaria = editCorSecundaria.getText().toString().trim();
        String novaCorFundo = editCorFundo.getText().toString().trim();

        SharedPreferences preferenciais = getContext().getSharedPreferences("sessao_vvv", Context.MODE_PRIVATE);
        int idUsuario = preferenciais.getInt("idUsuario", -1);

        if(idUsuario != -1){
            Usuario usuario = AppDatabase.getInstance(getContext()).usuarioDao().buscarUsuarioPorId(idUsuario);
            if(usuario !=null){
                usuario.endereco = novoEndereco;
                usuario.corPrimariaLoja = novaCorPrimaria;
                usuario.corSecundariaLoja = novaCorSecundaria;
                usuario.corFundoLoja = novaCorFundo;
                AppDatabase.getInstance(getContext()).usuarioDao().atualizar(usuario);
                Toast.makeText(getContext(), "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //met utilizado para abrir rodas de cores e preencher o campo
    private void mostrarDialogoDeCor(final EditText campoDestino) {
        // 1. Lê a cor atual do campo
        String corAtual = campoDestino.getText().toString().trim();
        int corInicial = android.graphics.Color.WHITE;

        try {
            if (!corAtual.isEmpty()) {
                corInicial = android.graphics.Color.parseColor(corAtual);
            }
        } catch (IllegalArgumentException e) {
            // Mantém branco se o texto for inválido
        }

        ColorPickerDialog.Builder builder = new ColorPickerDialog.Builder(getContext())
                .setTitle("Escolha uma Cor")
                .setPreferenceName("DialogoCorVitrine")
                .attachAlphaSlideBar(false)
                .attachBrightnessSlideBar(true)
                .setBottomSpace(12);

        builder.getColorPickerView().setInitialColor(corInicial);

        builder.setPositiveButton("Confirmar",
                        new ColorEnvelopeListener() {
                            @Override
                            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                                String corHexadecimal = "#" + envelope.getHexCode();
                                campoDestino.setText(corHexadecimal);
                                campoDestino.requestFocus();
                                campoDestino.setSelection(campoDestino.getText().length());
                            }
                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                .setNeutralButton("Limpar Cor",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                campoDestino.setText("");
                                campoDestino.requestFocus();
                                dialogInterface.dismiss();
                            }
                        })
                .show();
    }
    private void carregarEConfigurarCoresDaLoja(View viewRoot) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Usuario> usuarios = AppDatabase.getInstance(getContext()).usuarioDao().buscarTodos();
                Usuario lojista = null;
                for(Usuario u : usuarios){
                    if("LOJISTA".equals(u.tipoPerfil)){
                        lojista = u;
                        break;
                    }
                }
                if (lojista != null) {
                    final String corPrimaria = lojista.corPrimariaLoja;
                    final String corFundo = lojista.corFundoLoja;
                    final String corSecundaria = lojista.corSecundariaLoja;

                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                aplicarCoresNaInterface(viewRoot, corPrimaria, corFundo);
                            }
                        });
                    }
                }
            }
        }).start();
    }

    private void aplicarCoresNaInterface(View view, String corPrimaria, String corFundo) {
        try {
            // Pinta o Fundo do fragmento
            if (corFundo != null && !corFundo.trim().isEmpty()) {
                view.setBackgroundColor(android.graphics.Color.parseColor(corFundo));
            }

            // elementos com a Cor Primária
            if (corPrimaria != null && !corPrimaria.trim().isEmpty()) {
                int corHexConvertida = android.graphics.Color.parseColor(corPrimaria);

                // Pinta o botão do Histórico de Pedidos
                if (btnHistoricoPedidos != null) {
                    //  alteramos a cor do texto e da borda, ou o fundo
                    btnHistoricoPedidos.setBackgroundColor(corHexConvertida);
                    btnHistoricoPedidos.setTextColor(android.graphics.Color.WHITE); // contraste
                }

                //pintar o botão de salvar do Lojista
                if (btnSalvarDadosLojista != null) {
                    btnSalvarDadosLojista.setBackgroundColor(corHexConvertida);
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
