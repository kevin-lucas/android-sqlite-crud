package br.com.kevinlucas.android.carteiradeclientes;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import br.com.kevinlucas.android.carteiradeclientes.database.DadosOpenHelper;
import br.com.kevinlucas.android.carteiradeclientes.dominio.entidades.Cliente;
import br.com.kevinlucas.android.carteiradeclientes.dominio.repositorio.ClienteRepositorio;


public class MainActivity extends AppCompatActivity {

    private RecyclerView lstDados;
    private FloatingActionButton fab;
    private ConstraintLayout constraintLayout;

    // testando conexao
    private SQLiteDatabase conexao; // representa conexao

    private DadosOpenHelper dadosOpenHelper; // classe que gerencia o banco

    private ClienteAdapter clienteAdapter;
    private ClienteRepositorio clienteRepositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        constraintLayout = findViewById(R.id.layoutContentMain); // layout para exibir mensagem do Snackbar

        lstDados = findViewById(R.id.lstDadosId); // lista de dados

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, CadCliente.class);
                //startActivity(it);
                startActivityForResult(it, 0); // retorno para o activity que o chamou
            }
        });

        testarConexao();

        lstDados.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this); // como os dados sao visualizados
        lstDados.setLayoutManager(linearLayoutManager);

        clienteRepositorio = new ClienteRepositorio(conexao);

        List<Cliente> dados = clienteRepositorio.buscarTodos();

        clienteAdapter = new ClienteAdapter(dados);

        lstDados.setAdapter(clienteAdapter);

    }

    // metodo para testar a conexao
    private void testarConexao() {
        try {
            dadosOpenHelper = new DadosOpenHelper(this); // instancia
            //conexao = dadosOpenHelper.getReadableDatabase(); // apenas ler no banco
            conexao = dadosOpenHelper.getWritableDatabase(); // le e escreve
            //Toast.makeText(MainActivity.this, "Conexao com sucesso", Toast.LENGTH_SHORT).show();
            Snackbar.make(constraintLayout, "Conexao criada com sucesso", Snackbar.LENGTH_LONG).
                    setAction("OK", null);
        } catch (SQLException e) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Error");
            dialog.setMessage(e.getMessage());
            dialog.setNeutralButton("OK", null);
            dialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
/*
    // atualizar os registros
    public void cadatrar(View view) {

    }
*/

    // Atualizar registros automaticamente
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        List<Cliente> dados = clienteRepositorio.buscarTodos();
        clienteAdapter = new ClienteAdapter(dados);
        lstDados.setAdapter(clienteAdapter);
    }

}