package br.com.gaudium.entrega.webservice;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.com.gaudium.entrega.interfaces.PedidosInterface;
import br.com.gaudium.entrega.model.PedidoJsonObj;
import br.com.gaudium.entrega.view.MapsActivityView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfertaPedidoWebService {
//	String URL = "http://3.95.2.52/ps/ofertaPedido.php";
	MapsActivityView view;

	public OfertaPedidoWebService(MapsActivityView view){
		this.view = view;
	}

	public void obterPedido(Context ctx, OfertaPedidoCallback callback){
		//TODO: Trocar pedido hard-coded por chamada ao backend
		PedidoJsonObj.PedidoObj p = new PedidoJsonObj.PedidoObj();
		p.setLat_coleta(p.getLat_coleta());
		p.setLng_coleta(p.getLng_coleta());
		p.setEndereco_coleta(p.getEndereco_coleta());

		List<PedidoJsonObj.EntregaObj>  lstDestino = new ArrayList<>();
		PedidoJsonObj.EntregaObj[] destino = new PedidoJsonObj.EntregaObj[0];
		if(p.getEntregas() != null){
			for(PedidoJsonObj.EntregaObj obj: p.getEntregas()){
				lstDestino.add(obj);
			}
			destino = (PedidoJsonObj.EntregaObj[]) lstDestino.toArray();

		}
		p.setEntrega(destino);

		Call<PedidoJsonObj> call = new RetrofitConfig(view, ctx).getService().call();
		call.enqueue(new Callback<PedidoJsonObj>() {
			@Override
			public void onResponse(Call<PedidoJsonObj> call, Response<PedidoJsonObj> response) {
				callback.run(response.body().getResponse());
			}

			@Override
			public void onFailure(Call<PedidoJsonObj> call, Throwable t) {
				view.showToast("Erro ao obter pedido: "+t.getMessage());
			}
		});

	}


	public interface OfertaPedidoCallback{
		void run(PedidoJsonObj.PedidoObj ofertaPedido);
	}
}
