package br.com.gaudium.entrega;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import br.com.gaudium.entrega.model.DadosEntregador;
import br.com.gaudium.entrega.model.Historico;
import br.com.gaudium.entrega.view.UserProfileView;

public class AdapterProfileUser extends RecyclerView.Adapter<AdapterProfileUser.Holder> {

    private List<Historico> lstHistorico;
    private Context context;
    private UserProfileView userProfileView;

    private float saldo = 0;

    public AdapterProfileUser(Context ctx, List<Historico> lstHistorico, UserProfileView userProfileView) {
        this.context = ctx;
        this.lstHistorico = lstHistorico;
        this.userProfileView = userProfileView;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_recycler_view, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Historico historic = lstHistorico.get(position);

        holder.txtValue.setText("R$ "+ Util.addCommaPointer(String.valueOf(historic.getValor())));
        holder.txtDesc.setText(historic.getQuando());

        saldo += historic.getValor();
        userProfileView.atualizaSaldo(saldo);

    }

    @Override
    public int getItemCount() {
        return lstHistorico.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        private TextView txtDesc;
        private TextView txtValue;


        public Holder(@NonNull View itemView) {
            super(itemView);

            txtDesc = itemView.findViewById(R.id.txtDesc);
            txtValue = itemView.findViewById(R.id.txtValue);
        }
    }
}
