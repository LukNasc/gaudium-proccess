package br.com.gaudium.entrega.webservice;

import android.content.Context;
import android.util.Log;

import br.com.gaudium.entrega.model.DadosEntregador;
import br.com.gaudium.entrega.model.PedidoJsonObj;
import br.com.gaudium.entrega.view.UserProfileView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileWebService {
    UserProfileView userProfileView;

    public ProfileWebService(UserProfileView userProfileView) {
        this.userProfileView = userProfileView;
    }

    //Método para obter os dados do usuário
    public void obterDadosEntregador(Context ctx, ProfileCallback callback){
        //Prepara a chamada passando a endpoint
        Call<DadosEntregador> call = new RetrofitConfig(ctx).getServiceProfile().callProfile("perfil.php");

        //Faz requisição a API
        call.enqueue(new Callback<DadosEntregador>() {
           @Override
           public void onResponse(Call<DadosEntregador> call, Response<DadosEntregador> response) {
               callback.run(response.body().getResponse());
           }

           @Override
           public void onFailure(Call<DadosEntregador> call, Throwable t) {
                userProfileView.showToast("Não foi possível obter dados do usuáario: "+t.getMessage());
           }
       });
    }

    public interface ProfileCallback{
        void run(DadosEntregador.Response response);
    }
}
