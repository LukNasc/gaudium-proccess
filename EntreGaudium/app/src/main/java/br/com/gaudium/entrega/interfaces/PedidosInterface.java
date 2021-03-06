package br.com.gaudium.entrega.interfaces;

import br.com.gaudium.entrega.model.DadosEntregador;
import br.com.gaudium.entrega.model.PedidoJsonObj;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PedidosInterface {

    @GET("/ps/{endpoint}")
    Call<PedidoJsonObj> callPedidos(@Path("endpoint")String  endpoint);


}
