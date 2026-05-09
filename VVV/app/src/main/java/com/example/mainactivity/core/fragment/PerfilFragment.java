package com.example.mainactivity.core.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mainactivity.R;
import com.example.mainactivity.core.LoginActivity;

public class PerfilFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        Button btnSairSessao = view.findViewById(R.id.btnSairSessao);

        btnSairSessao.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SharedPreferences preferenciais = getActivity().getSharedPreferences("sessao_vvv", Context.MODE_PRIVATE);
                preferenciais.edit().clear().apply();//limpa dados de sessa
                Toast.makeText(getContext(), "Sessão encerrada", Toast.LENGTH_SHORT).show();
                //Voltando para tela de Login
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

                if(getActivity() != null){
                    getActivity().finish();
                }
            }
        });
        return view;
    }
}
