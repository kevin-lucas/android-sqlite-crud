package br.com.kevinlucas.android.carteiradeclientes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.kevinlucas.android.carteiradeclientes.dominio.entidades.Cliente;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolderCliente> {

    private List<Cliente> dados;

    public ClienteAdapter(List<Cliente> dados) {
        this.dados = dados;
    }

    @Override
    public ClienteAdapter.ViewHolderCliente onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.linha_cliente, parent, false);

        ViewHolderCliente holderCliente = new ViewHolderCliente(view, parent.getContext());

        return holderCliente;
    }

    @Override
    public void onBindViewHolder(ClienteAdapter.ViewHolderCliente holder, int position) {
        if ((dados != null) && (dados.size() > 0)) {
            Cliente cliente = dados.get(position);
            holder.txtNome.setText(cliente.nome);
            holder.txtTefone.setText(cliente.telefone);
        }
    }

    @Override
    public int getItemCount() {
        return dados.size();
    }

    public class ViewHolderCliente extends RecyclerView.ViewHolder {

        public TextView txtNome;
        public TextView txtTefone;

        public ViewHolderCliente(View itemView, final Context context) {
            super(itemView);

            txtNome = itemView.findViewById(R.id.txtNome);
            txtTefone = itemView.findViewById(R.id.txtTelefone);

            // selecionar itens do recycler
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (dados.size() > 0) {
                        Cliente cliente = dados.get(getLayoutPosition());

                        // Toast.makeText(context,"Cliente: " + cliente.nome, Toast.LENGTH_SHORT).show();

                        Intent it = new Intent(context, CadCliente.class);
                        it.putExtra("cliente", cliente);
                        ((AppCompatActivity) context).startActivityForResult(it, 0);
                    }
                }
            });
        }
    }
}
