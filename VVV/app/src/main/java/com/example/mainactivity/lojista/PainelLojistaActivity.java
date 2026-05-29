package com.example.mainactivity.lojista;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.mainactivity.R;
import com.example.mainactivity.core.fragment.PerfilFragment;
import com.example.mainactivity.lojista.fragment.GerenciarFragment;
import com.example.mainactivity.lojista.fragment.InicioFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class PainelLojistaActivity extends AppCompatActivity {

    private BottomNavigationView barraNavegacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_lojista);

        // Mapeando a nossa barra de navegação
        barraNavegacao = findViewById(R.id.barra_navegacao);

        // Lógica para trocar os Fragmentos
        //tela padrao
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.palco_fragmento, new PerfilFragment()).commit();
            barraNavegacao.setSelectedItemId(R.id.menu_perfil);
        }
        //Ouvintes para os cliques
        barraNavegacao.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragmentSelecionado = null;
                int itemId = item.getItemId();
                if(itemId == R.id.menu_inicio){
                    fragmentSelecionado = new InicioFragment();
                } else if (itemId == R.id.menu_gerenciarProdutos) {
                    fragmentSelecionado = new GerenciarFragment();
                } else if( itemId == R.id.menu_perfil){
                    fragmentSelecionado = new PerfilFragment();
                }
                //colocar no palco/tela
                if(fragmentSelecionado != null){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.palco_fragmento, fragmentSelecionado).commit();
                    return true;
                }
                return false;
            }

        });
        criarCanalNotificacao();
        agendarAlarme();
    }

    private void criarCanalNotificacao(){
        //primeiro checa versao(se precisa de canal)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence nome = "Canal Premium Lojista";
            String descricao = "Notificação sobre ofertas e upgrades do sistema VVV";
            int importancia = NotificationManagerCompat.IMPORTANCE_DEFAULT;//faz tocar o som padrao de notificacao

            NotificationChannel canal = new NotificationChannel("CANAL_PREMIUM_VVV", nome, importancia);
            canal.setDescription(descricao);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if(notificationManager != null){
                notificationManager.createNotificationChannel(canal);
            }
        }
    }

    private void agendarAlarme(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmePremiumReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        //tempo
        long tempoParaDisparar = SystemClock.elapsedRealtime() + (15*1000);
        if (alarmManager != null) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
                if(alarmManager.canScheduleExactAlarms()){
                    //tem permissao para usar alarme com tempo exato
                    alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, tempoParaDisparar, pendingIntent);
                }else{
                    //n tem permissao, ent usa alarme inexato
                    alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, tempoParaDisparar, pendingIntent);
                }
            }else {
                alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, tempoParaDisparar, pendingIntent);
            }
        }
    }
}