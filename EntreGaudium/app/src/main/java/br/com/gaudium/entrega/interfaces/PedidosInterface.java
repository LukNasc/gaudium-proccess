package br.com.gaudium.entrega.interfaces;

import br.com.gaudium.entrega.model.PedidoJsonObj;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PedidosInterface {
    String endpoint = "ofertaPedido.php";

    @GET(endpoint)
    Call<PedidoJsonObj> call();

}
