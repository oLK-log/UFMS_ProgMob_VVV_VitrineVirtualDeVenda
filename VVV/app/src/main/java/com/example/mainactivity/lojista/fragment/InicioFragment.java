package com.example.mainactivity.lojista.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.mainactivity.R;
import com.example.mainactivity.database.AppDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class InicioFragment extends Fragment {
    private TextView txtMetricaProdutos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);//infla o layout
        txtMetricaProdutos = view.findViewById(R.id.txtMetricaProdutos);
        return view;
    }

    //utilizei o onResume para atualizar automaticamente sempre que abrir a aba inicio
    @Override
    public void onResume(){
        super.onResume();
        carregarMetricas();
    }

    //metodo para carregar as metricas(por enquanto só tem umna)
    private void carregarMetricas(){
        //pegar idLojista
        SharedPreferences preferenciais = getContext().getSharedPreferences("sessao_vvv", Context.MODE_PRIVATE);
        int idLojista = preferenciais.getInt("idUsuario", -1);
        if(idLojista != -1){
            int totalProdutos = AppDatabase.getInstance(getContext()).produtoDao().contarProdutosDoLojista(idLojista);
            txtMetricaProdutos.setText(String.valueOf(totalProdutos));
        }else {
            txtMetricaProdutos.setText("Indefinido");
        }
    }
}
