package br.com.gaudium.entrega;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import br.com.gaudium.entrega.model.DadosEntregador;
import br.com.gaudium.entrega.model.Historico;
import br.com.gaudium.entrega.view.UserProfileView;
import br.com.gaudium.entrega.webservice.ProfileWebService;

public class UserActivity extends AppCompatActivity implements ProfileWebService.ProfileCallback, UserProfileView {

    private TextView txtNome, txtSaldo;
    private RecyclerView recyclerView;
    private ImageView imageProfile;

    private AdapterProfileUser adapterProfileUser;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        txtNome = findViewById(R.id.txtName);
        txtSaldo = findViewById(R.id.txtSaldo);
        recyclerView = findViewById(R.id.rvHistoric);
        imageProfile = findViewById(R.id.ivProfile);

        ProfileWebService profileWebService = new ProfileWebService(this);
        profileWebService.obterDadosEntregador(this, this);

    }

    @Override
    public void run(DadosEntregador.Response response) {
        txtNome.setText(response.getNome());

        //Setando uma imagem da API para a imageView
        if (response.getFoto() != null)
            Glide.with(this).load(response.getFoto()).into(imageProfile);
        mountList(response.getHistorico());

    }

    private void mountList(List<Historico> lstHistorico) {
        adapterProfileUser = new AdapterProfileUser(this, lstHistorico, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterProfileUser);
    }

    @Override
    public void atualizaSaldo(float value) {
        txtSaldo.setText("Saldo: R$ " + Util.addCommaPointer(String.valueOf(value)));
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
