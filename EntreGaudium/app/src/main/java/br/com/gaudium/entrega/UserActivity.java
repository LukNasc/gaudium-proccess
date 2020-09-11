package br.com.gaudium.entrega;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.gaudium.entrega.model.DadosEntregador;
import br.com.gaudium.entrega.model.Historico;
import br.com.gaudium.entrega.view.UserProfileView;
import br.com.gaudium.entrega.webservice.ProfileWebService;

public class UserActivity extends AppCompatActivity implements ProfileWebService.ProfileCallback, UserProfileView {

    private TextView txtNome, txtSaldo;
    private RecyclerView recyclerView;

    private AdapterProfileUser adapterProfileUser;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        txtNome = findViewById(R.id.txtName);
        txtSaldo = findViewById(R.id.txtSaldo);
        recyclerView = findViewById(R.id.rvHistoric);


        ProfileWebService profileWebService = new ProfileWebService(this);
        profileWebService.obterDadosEntregador(this, this);

    }

    @Override
    public void run(DadosEntregador.Response response) {
        txtNome.setText(response.getNome());
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
        txtSaldo.setText("Saldo: R$ "+ Util.addCommaPointer(String.valueOf(value)));
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
