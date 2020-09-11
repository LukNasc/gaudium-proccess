package br.com.gaudium.entrega.webservice;

import android.content.Context;
import android.util.Log;

import br.com.gaudium.entrega.model.DadosEntregador;
import br.com.gaudium.entrega.model.PedidoJsonObj;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileWebService {
    public void obterDadosEntregador(Context ctx, ProfileCallback callback){
        Call<DadosEntregador> call = new RetrofitConfig(ctx).getServiceProfile().callProfile("perfil.php");
        call.enqueue(new Callback<DadosEntregador>() {
           @Override
           public void onResponse(Call<DadosEntregador> call, Response<DadosEntregador> response) {
               callback.run(response.body().getResponse());
           }

           @Override
           public void onFailure(Call<DadosEntregador> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
           }
       });
    }

    public interface ProfileCallback{
        void run(DadosEntregador.Response response);
    }
}
