package com.example.mainactivity.cliente;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mainactivity.R;
import com.example.mainactivity.cliente.fragment.HomeFragment;
import com.example.mainactivity.cliente.fragment.PedidoFragment;
import com.example.mainactivity.core.fragment.PerfilFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class PainelClienteActivity extends AppCompatActivity{
    private BottomNavigationView barraNavegacaoCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_cliente);
        //contar visitas
        //acessa as estatisticas, lê qtd de visitas e soma mais 1
        android.content.SharedPreferences prefsEstatisticas = getSharedPreferences("estatisticas_loja", MODE_PRIVATE);
        int visitasAtuais = prefsEstatisticas.getInt("total_visitas", 0);
        prefsEstatisticas.edit().putInt("total_visitas", visitasAtuais + 1).apply();

        barraNavegacaoCliente = findViewById(R.id.barra_navegacao_cliente);
        //tela padrao ao abrir vitrine
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.palco_fragmento_cliente, new HomeFragment()).commit();
        }

        barraNavegacaoCliente.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                Fragment fragmentSelecionado = null;
                int itemId = item.getItemId();

                if(itemId == R.id.menu_home_cliente){
                    fragmentSelecionado = new HomeFragment();
                } else if(itemId == R.id.menu_pedido_cliente){
                    fragmentSelecionado = new PedidoFragment();
                } else if(itemId == R.id.menu_perfil_cliente){
                    fragmentSelecionado = new PerfilFragment();
                }

                if(fragmentSelecionado != null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.palco_fragmento_cliente, fragmentSelecionado).commit();
                    return true;
                }
                return false;
            }
        });
    }
}
