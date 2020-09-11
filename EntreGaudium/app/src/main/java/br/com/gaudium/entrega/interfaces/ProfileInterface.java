package br.com.gaudium.entrega.interfaces;

import br.com.gaudium.entrega.model.DadosEntregador;
import br.com.gaudium.entrega.model.PedidoJsonObj;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProfileInterface {

    @GET("/ps/{endpoint}")
    Call<DadosEntregador> callProfile(@Path("endpoint")String  endpoint);
}
