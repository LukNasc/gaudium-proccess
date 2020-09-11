package br.com.gaudium.entrega.webservice;

import android.content.Context;
import android.util.Log;
import android.view.View;

import br.com.gaudium.entrega.R;
import br.com.gaudium.entrega.interfaces.PedidosInterface;
import br.com.gaudium.entrega.interfaces.ProfileInterface;
import br.com.gaudium.entrega.model.PedidoJsonObj;
import br.com.gaudium.entrega.view.MapsActivityView;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    Retrofit retrofit;
    MapsActivityView view;
    private static final String TAG = "retrofit_error";

    public RetrofitConfig(Context context){
        this.view = view;
        try{
            this.retrofit = new Retrofit.Builder()
                    .baseUrl("http://3.95.2.52/ps/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
    }


    public PedidosInterface getServicePedidos(){
        return this.retrofit.create(PedidosInterface.class);
    }

    public ProfileInterface getServiceProfile(){
        return this.retrofit.create(ProfileInterface.class);
    }

}
