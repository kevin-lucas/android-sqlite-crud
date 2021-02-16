package br.com.kevinlucas.android.carteiradeclientes;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.kevinlucas.android.carteiradeclientes.database.DadosOpenHelper;
import br.com.kevinlucas.android.carteiradeclientes.dominio.entidades.Cliente;
import br.com.kevinlucas.android.carteiradeclientes.dominio.repositorio.ClienteRepositorio;


public class CadCliente extends AppCompatActivity {

    private EditText cmpNome;
    private EditText cmpEndereco;
    private EditText cmpEmail;
    private EditText cmpTelefone;
    private ConstraintLayout constraintLayout; // testa funcao inserir

    private Cliente cliente; // testa funcao inserir

    // testando conexao
    private SQLiteDatabase conexao; // testa funcao inserir

    private DadosOpenHelper dadosOpenHelper; // testa funcao inserir

    private ClienteRepositorio clienteRepositorio; // testa funcao inserir

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_cliente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        constraintLayout = findViewById(R.id.layoutContentCadCliente); // testa funcao inserir

        cmpNome = findViewById(R.id.edtNomeId);
        cmpEndereco = findViewById(R.id.edtEnderecoId);
        cmpEmail = findViewById(R.id.edtEmailId);
        cmpTelefone = findViewById(R.id.edtTelefoneId);

        testarConexao(); // testa funcao inserir
        verificaParametro();
    }

    // setar campos selecionados
    private void verificaParametro() {
        Bundle bundle = getIntent().getExtras();

        cliente = new Cliente();

        if (bundle != null && bundle.containsKey("cliente")) {

            cliente = (Cliente) bundle.getSerializable("cliente");

            cmpNome.setText(cliente.nome);
            cmpEndereco.setText(cliente.endereco);
            cmpEmail.setText(cliente.email);
            cmpTelefone.setText(cliente.telefone);

        }
    }

    // testa funcao inserir
    // metodo para testar a conexao
    private void testarConexao() {
        try {
            dadosOpenHelper = new DadosOpenHelper(this); // instancia
            //conexao = dadosOpenHelper.getReadableDatabase(); // apenas ler no banco
            conexao = dadosOpenHelper.getWritableDatabase(); // le e escreve
            //Toast.makeText(MainActivity.this, "Conexao com sucesso", Toast.LENGTH_SHORT).show();
            Snackbar.make(constraintLayout, "Conexao criada com sucesso", Snackbar.LENGTH_LONG).
                    setAction("OK", null);

            clienteRepositorio = new ClienteRepositorio(conexao);
        } catch (SQLException e) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Error");
            dialog.setMessage(e.getMessage());
            dialog.setNeutralButton("OK", null);
            dialog.show();
        }
    }

    // testa funcao inserir
    private void confirmar() {
        if (validarCampos() == false) {
            try {
                if (cliente.codigo == 0) {
                    clienteRepositorio.inserir(cliente);
                } else {
                    clienteRepositorio.alterar(cliente);
                }
                finish();
            } catch (SQLException e) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Error");
                dialog.setMessage(e.getMessage());
                dialog.setNeutralButton("OK", null);
                dialog.show();
            }
        }
    }

    // testa funcao inserir
    // metodo para validar campos
    private boolean validarCampos() {
        boolean res = false;

        String nome = cmpNome.getText().toString();
        String telefone = cmpTelefone.getText().toString();
        String email = cmpEmail.getText().toString();
        String endereco = cmpEndereco.getText().toString();

        // testa funcao inserir
        cliente.nome = nome;
        cliente.email = email;
        cliente.telefone = telefone;
        cliente.endereco = endereco;

        if (res = isCamposVazio(nome)) {
            cmpNome.requestFocus();
        } else if (res = isCamposVazio(endereco)) {
            cmpEndereco.requestFocus();
        } else if (res = !isEmailValido(email)) {
            cmpEmail.requestFocus();
        } else if (res = isCamposVazio(telefone)) {
            cmpTelefone.requestFocus();
        }

        if (res) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Aviso");
            dlg.setMessage("Campos inválidos ou em branco");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
        return res;
    }

    // metodo verifica se o email esta vazios
    private boolean isCamposVazio(String valor) {
        //  TextUtils verifica se o campos eh nulo
        boolean resultado = (TextUtils.isEmpty(valor) || valor.trim().isEmpty()); // trim retira o espaco
        return resultado;
    }

    // verifica se o email eh valido
    private boolean isEmailValido(String email) {
        // Patterns testa a validacao de campos
        boolean resultado = (!isCamposVazio(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        return resultado;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cad_cliente, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_ok:
                // Toast.makeText(this, "Botão Confirmar Selecionado!", Toast.LENGTH_SHORT).show();
                //validarCampos();
                confirmar();
                break;
            case R.id.action_cancel:
                // Toast.makeText(this, "Botão Cancelar Selecionado!", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
