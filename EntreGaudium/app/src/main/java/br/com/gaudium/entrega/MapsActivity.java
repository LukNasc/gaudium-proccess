package br.com.gaudium.entrega;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.gaudium.entrega.model.DebugLocationRetriever;
import br.com.gaudium.entrega.model.EntregadorObj;
import br.com.gaudium.entrega.model.PedidoJsonObj;
import br.com.gaudium.entrega.model.StatusEntregadorEnum;
import br.com.gaudium.entrega.view.MapsActivityView;
import br.com.gaudium.entrega.webservice.OfertaPedidoWebService;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, MapsActivityView {

    private GoogleMap mMap;
    private LinearLayout layMenuOferta, layMenuColeta, layMenuEntrega;
    private RelativeLayout layColetaButton, layEntregaButton, layMenu;
    private TextView txtEnderecoOferta, txtEnderecoColeta, txtEntrega, txtEntregas;
    private Button btnRejeitar, btnAceitar, btnColetar, btnEntregar, btnDebugAction;

    Handler handler;

    OfertaPedidoWebService ofertaWS;
    DebugLocationRetriever dLocRet;
    EntregadorObj entregadorObj;
    Marker userMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        if (mapFragment == null) return;

        handler = new Handler();
        layMenu = findViewById(R.id.layMenu);

        // Menu de Oferta
        layMenuOferta = findViewById(R.id.layMenuOferta);
        txtEnderecoOferta = findViewById(R.id.txtEnderecoOferta);
        txtEntregas = findViewById(R.id.txtEntregas);
        btnRejeitar = findViewById(R.id.btnRejeitar);
        btnRejeitar.setOnClickListener(view -> onReject());
        btnAceitar = findViewById(R.id.btnAceitar);
        btnAceitar.setOnClickListener(view -> onAccept());

        // Menu de Coleta
        layMenuColeta = findViewById(R.id.layMenuColeta);
        layColetaButton = findViewById(R.id.layColetaButton);
        txtEnderecoColeta = findViewById(R.id.txtEnderecoColeta);
        txtEntrega = findViewById(R.id.txtEntrega);
        btnColetar = findViewById(R.id.btnColetar);
        btnColetar.setOnClickListener(view -> onCollect());

        // Menu de Entrega
        layMenuEntrega = findViewById(R.id.layMenuEntrega);
        layEntregaButton = findViewById(R.id.layEntregaButton);
        txtEnderecoOferta = findViewById(R.id.txtEnderecoOferta);
        btnEntregar = findViewById(R.id.btnEntregar);
        btnEntregar.setOnClickListener(view -> onDeliver());


        btnDebugAction = findViewById(R.id.btnDebugAction);
        btnDebugAction.setOnClickListener(view -> onDebugAction());

        // Preparar e carregar mapa
        mapFragment.getMapAsync(this);

        entregadorObj = EntregadorObj.getInstance();

        dLocRet = new DebugLocationRetriever();
        dLocRet.setLatLng(-22.904093, -43.175293);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dLocRet.getLatLng(), 14f));
        updateMapMarkers();
    }

    /**
     * Método responsável por atualizar os elementos da tela
     */
    private void updateScreen() {
        showMenuOferta(StatusEntregadorEnum.DECIDINDO.equalsEnum(entregadorObj.getStatus()));
        showMenuColeta(StatusEntregadorEnum.COLETANDO.equalsEnum(entregadorObj.getStatus()));
        showMenuEntrega(StatusEntregadorEnum.ENTREGANDO.equalsEnum(entregadorObj.getStatus()));

        if(StatusEntregadorEnum.ENTREGANDO.equalsEnum(entregadorObj.getStatus())){
            PedidoJsonObj.EntregaObj e = entregadorObj.getPedido().getEntregaAtual();
            if(e != null) {
                txtEntrega.setText(String.format(getString(R.string.va_ate_endereco_entrega_id), e.getId()));
            }
        }

        updateDebugButton();

        updateMapMarkers();

        // Deixar por último para ajustar o padding do mapa aos efeitos da mudança de estado
        handler.postDelayed(() -> {
            if (mMap != null) {
                mMap.setPadding(0, 0, 0, layMenu.getHeight());
            }
        }, 300);
    }

    private void updateMapMarkers(){
        if(mMap == null) return;
        if(userMarker == null) {
            userMarker = mMap.addMarker(new MarkerOptions().position(dLocRet.getLatLng()).icon(Util.bitmapDescriptorFromVector(this, R.drawable.pin_user_white)));
        } else{
            userMarker.setPosition(dLocRet.getLatLng());
        }

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userMarker.getPosition(), 14f));
    }

    /**
     * Ação do botão de Rejeitar a oferta de pedido recebida
     * Volta para o momento anterior, DISPONÍVEL
     */
    private void onReject() {
        entregadorObj.setStatus(StatusEntregadorEnum.DISPONIVEL);
        Util.playPop(this);
        updateScreen();
    }

    /**
     * Ação do botão de Aceitar a oferta de pedido recebida
     * Aceita o pedido recebido e avança para o momento COLETANDO
     */
    private void onAccept() {
        entregadorObj.setStatus(StatusEntregadorEnum.COLETANDO);
        Util.playPop(this);
        updateScreen();
    }

    /**
     * Ação do botão de Coletar Produto
     * Confirma recebimento do pedido e avança para o momento ENTREGANDO
     */
    private void onCollect() {
        entregadorObj.setStatus(StatusEntregadorEnum.ENTREGANDO);
        Util.playPop(this);
        updateScreen();
    }

    /**
     * Ação do botão de Entregar Produto
     * Confirma entrega do pedido no endereço e avança para o próximo endereço. Se for o último,
     * encerra a entrega e volta para o momento DISPONÍVEL
     */
    private void onDeliver() {
        entregadorObj.getPedido().getEntregaAtual().setEntregue(true);
        if(entregadorObj.getPedido().getEntregaAtual() == null){
            //FINALIZAR
            entregadorObj.setStatus(StatusEntregadorEnum.DISPONIVEL);
            Toast.makeText(this, R.string.toast_entrega_finalizada, Toast.LENGTH_SHORT).show();
            Util.playCompleted(this);
        } else {
            Util.playPop(this);
        }
        updateScreen();
    }

    /**
     * Método que controla a exibição do menu que exibe a oferta do pedido, com as opções de Aceitar ou Rejeitar
     * @param visible true/false
     */
    public void showMenuOferta(boolean visible){
        layMenuOferta.setVisibility(visible?View.VISIBLE:View.GONE);
        if (entregadorObj.getPedido() != null) {
            txtEntregas.setText("Entregas: "+entregadorObj.getPedido().getEntregas().length);
            txtEnderecoOferta.setText(entregadorObj.getPedido().getEndereco_coleta());
        }
    }

    /**
     * Método que controla a exibição do menu que exibe onde o pedido deve ser coletado. Se o entregador estiver
     * próximo do local, um botão para avançar para a próxima etapa será exibido
     * @param visible indica se deve exibir ou não o menu
     */
    public void showMenuColeta(boolean visible){
        layMenuColeta.setVisibility(visible?View.VISIBLE:View.GONE);
        if (entregadorObj.getPedido() != null) {
            txtEnderecoColeta.setText(entregadorObj.getPedido().getEndereco_coleta());

            //Se estiver a menos de 300 metros, exibe o botão de coletar para avançar para ENTREGANDO
            layColetaButton.setVisibility(dLocRet.distanceToInMeters(entregadorObj.getPedido().getLatLng()) < 300 ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * Método que controla a exibição do menu que contém os dados de onde o pedido deve entregue. Se o entregador estiver
     * próximo do local de entrega, um botão para avançar para o próximo pedido até encerrar o pedido
     * @param visible indica se deve exibir ou não o menu
     */
    public void showMenuEntrega(boolean visible){
        layMenuEntrega.setVisibility(visible?View.VISIBLE:View.GONE);
        if (entregadorObj.getPedido() != null && entregadorObj.getPedido().getEntregaAtual() != null) {
            PedidoJsonObj.EntregaObj entrega = entregadorObj.getPedido().getEntregaAtual();

            // Se estiver a menos de 300 mentros, exibe o botão de entregar para exibir a próxima entrega ou encerrar
            layEntregaButton.setVisibility(dLocRet.distanceToInMeters(entrega.getLatLng()) < 300 ? View.VISIBLE : View.GONE);
        }
    }



    // - - - - - - - - Métodos auxiliares para botão de debug - - - - - - - -

    /**
     * Controla a exibição do botão de debug de acordo com o momento da entrega
     */
    private void updateDebugButton(){
        // Exibe o botão para forçar o recebimento de um pedido de entrega
        if (StatusEntregadorEnum.DISPONIVEL.equalsEnum(entregadorObj.getStatus())){
            btnDebugAction.setText(R.string.debug_button_receber_pedido);
            btnDebugAction.setVisibility(View.VISIBLE);
        }

        // Enquanto decide o botão não deve ser exibido
        if (StatusEntregadorEnum.DECIDINDO.equalsEnum(entregadorObj.getStatus()) && entregadorObj.getPedido() != null){
            btnDebugAction.setVisibility(View.GONE);
        }

        // Exibe o botão para mover o usuário até o endereço de coleta
        if (StatusEntregadorEnum.COLETANDO.equalsEnum(entregadorObj.getStatus())){
            btnDebugAction.setText(R.string.debug_button_endereco_coleta);

            // Exibe botão apenas quando tiver longe do endereço de coleta
            btnDebugAction.setVisibility(dLocRet.distanceToInMeters(entregadorObj.getPedido().getLatLng()) >= 300 ? View.VISIBLE : View.GONE);
        }

        // Exibe o botão para mover o usuário até o endereço de entrega
        if (StatusEntregadorEnum.ENTREGANDO.equalsEnum(entregadorObj.getStatus())){
            btnDebugAction.setText(R.string.debug_button_endereco_entrega);
            btnDebugAction.setVisibility(View.VISIBLE);
            PedidoJsonObj.EntregaObj entrega = entregadorObj.getPedido().getEntregaAtual();

            // Exibe botão apenas quando tiver longe do endereço de entrega
            btnDebugAction.setVisibility(dLocRet.distanceToInMeters(entrega.getLatLng()) >= 300 ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * Método que controla ação de botão de debug. Em cada etapa da corrida ele terá um comportamento diferente
     * para auxiliar o desenvolvimento
     */
    private void onDebugAction() {
        //Enquanto estiver no momento DISPONÍVEL, o botão irá forçar o recebimento de um pedido
        if (StatusEntregadorEnum.DISPONIVEL.equalsEnum(entregadorObj.getStatus())){
            if(ofertaWS == null) {
                ofertaWS = new OfertaPedidoWebService(this);
            }

            //Acionar serviço para obter pedido
            ofertaWS.obterPedido(this, oferta -> {
                if(oferta == null) return;
                entregadorObj.setPedido(oferta);
                entregadorObj.setStatus(StatusEntregadorEnum.DECIDINDO);
                Util.tocarSomVibrar(MapsActivity.this);
                updateScreen();
            });
        }

        //Enquanto estiver no momento COLETANDO, o botão irá locomover o usuário até o endereço de coleta do pedido aceito
        if (StatusEntregadorEnum.COLETANDO.equalsEnum(entregadorObj.getStatus())){
            if(dLocRet.getLatLng().equals(entregadorObj.getPedido().getLatLng())){    //Se já está no endereço, exibe um aviso
                Toast.makeText(this, R.string.toast_endereco_entrega, Toast.LENGTH_SHORT).show();
                return;
            }


            //Mover usuário
            dLocRet.setLatLng(entregadorObj.getPedido().getLat_coleta(), entregadorObj.getPedido().getLng_coleta());
            updateScreen();
        }

        //Enquanto estiver no momento ENTREGANDO, o botão irá locomover o usuário até o endereço de entrega do pedido aceito
        if (StatusEntregadorEnum.ENTREGANDO.equalsEnum(entregadorObj.getStatus())){
            PedidoJsonObj.EntregaObj entrega = entregadorObj.getPedido().getEntregaAtual();

            if (entrega != null) {
                if(dLocRet.getLatLng().equals(entrega.getLatLng())){    //Se já está no endereço, exibe um aviso
                    Toast.makeText(this, R.string.toast_endereco_entrega, Toast.LENGTH_SHORT).show();
                    return;
                }

                //Mover usuário
                dLocRet.setLatLng(entrega.getLat(), entrega.getLng());
                updateScreen();
            }
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
