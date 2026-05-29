package com.example.mainactivity.cliente;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mainactivity.R;
import com.example.mainactivity.database.AppDatabase;
import com.example.mainactivity.model.Usuario;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
public class MapaLojaClienteActivity extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap mapa;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_loja_cliente);
        //carregar em segundo plano
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa_loja);
        if(mapFragment != null){
            mapFragment.getMapAsync(this);
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap){
        mapa = googleMap;
        buscarExibirEndereco();
    }

    //metodo para buscar o endereco a partir de uma Thread separada
    //busca primeiro todos os usuarios e depois filtra o lojista, e escolhe o que tem o endereco cadastrado
    //obs: futuramente vou ter que adaptar para o Lojista de determinada loja
    private void buscarExibirEndereco(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Usuario> usuarios = AppDatabase.getInstance(MapaLojaClienteActivity.this).usuarioDao().buscarTodos();
                String enderecoLoja = null;

                for(Usuario u : usuarios){
                    if("LOJISTA".equals(u.tipoPerfil) && u.endereco != null && !u.endereco.isEmpty()){
                        enderecoLoja = u.endereco;
                        break;
                    }
                }
                final String finalEnderecoLoja = enderecoLoja;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(finalEnderecoLoja != null){
                            marcarNoMapa(finalEnderecoLoja);
                        }else{
                            Toast.makeText(MapaLojaClienteActivity.this, "O lojista ainda não cadastrou um endereço!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }).start();
    }
    private void marcarNoMapa(String enderecoTexto){
        // o mapa utiliza coordenadas, e como o usuario coloca o endereco em texto precisamo converter para uma coordenada
        // o geocoder faz isso, e comecarmos instanciando por meio da linguagem do celular do cliente
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try{
            List<Address> enderecos = geocoder.getFromLocationName(enderecoTexto, 1);
            if(enderecos != null && !enderecos.isEmpty()){
                Address enderecoEncontrado = enderecos.get(0);
                LatLng posicao = new LatLng(enderecoEncontrado.getLatitude(), enderecoEncontrado.getLongitude());//pega coordenada
                //efeito de marcar endereco e aproximar
                mapa.addMarker(new MarkerOptions().position(posicao).title("Nossa Loja!"));
                mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(posicao, 15f));
            }else {
                Toast.makeText(this, "Não foi possível encontrar o endereço no mapa.", Toast.LENGTH_SHORT).show();
            }
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Erro de rede ao buscar localização.", Toast.LENGTH_SHORT).show();
        }
    }
}
