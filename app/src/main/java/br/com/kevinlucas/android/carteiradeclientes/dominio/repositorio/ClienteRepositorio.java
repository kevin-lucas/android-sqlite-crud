package br.com.kevinlucas.android.carteiradeclientes.dominio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.kevinlucas.android.carteiradeclientes.dominio.entidades.Cliente;

public class ClienteRepositorio {

    SQLiteDatabase conexao;

    public ClienteRepositorio(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public void inserir(Cliente cliente) {
        ContentValues contentValues = new ContentValues(); // valores passados
        contentValues.put("nome", cliente.nome);
        contentValues.put("endereco", cliente.endereco);
        contentValues.put("email", cliente.email);
        contentValues.put("telefone", cliente.telefone);
        conexao.insertOrThrow("cliente", null, contentValues); // insercao no banco de dados e uma mensagem de execao caso ocorra
    }

    public void excluir(int codigo) {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(codigo);
        conexao.delete("cliente", "codigo = ?", parametros);
    }

    public void alterar(Cliente cliente) {
        ContentValues contentValues = new ContentValues(); // valores passados
        contentValues.put("nome", cliente.nome);
        contentValues.put("endereco", cliente.endereco);
        contentValues.put("email", cliente.email);
        contentValues.put("telefone", cliente.telefone);
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(cliente.codigo);
        conexao.update("cliente", contentValues, "codigo = ?", parametros);
    }

    public List<Cliente> buscarTodos() {

        List<Cliente> clientes = new ArrayList<>();
        // conexao.query(); // consulta no banco
        // consultar
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT codigo, nome, endereco, email, telefone ");
        sql.append("FROM cliente");

        Cursor resultado = conexao.rawQuery(sql.toString(), null); // retorna um cursor

        if (resultado.getCount() > 0) {


            resultado.moveToFirst(); // posiciona na primaira posicao

            do {
                Cliente cli = new Cliente();
                cli.codigo = resultado.getInt(resultado.getColumnIndexOrThrow("codigo"));
                cli.nome = resultado.getString(resultado.getColumnIndexOrThrow("nome"));
                cli.endereco = resultado.getString(resultado.getColumnIndexOrThrow("endereco"));
                cli.email = resultado.getString(resultado.getColumnIndexOrThrow("email"));
                cli.telefone = resultado.getString(resultado.getColumnIndexOrThrow("telefone"));
                clientes.add(cli);
            } while (resultado.moveToNext());
        }

        return clientes;
    }

    public Cliente buscarCliente(int codigo) {

        Cliente cliente = new Cliente();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT codigo, nome, endereco, email, telefone ");
        sql.append("FROM cliente ");
        sql.append("WHERE codigo = ?");

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(codigo);

        Cursor resultado = conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getColumnCount() > 0) {

            resultado.moveToFirst(); // posiciona na primaira posicao

            cliente.codigo = resultado.getInt(resultado.getColumnIndexOrThrow("codigo"));
            cliente.nome = resultado.getString(resultado.getColumnIndexOrThrow("nome"));
            cliente.endereco = resultado.getString(resultado.getColumnIndexOrThrow("endereco"));
            cliente.email = resultado.getString(resultado.getColumnIndexOrThrow("email"));
            cliente.telefone = resultado.getString(resultado.getColumnIndexOrThrow("telefone"));

            return cliente;
        }

        return null;
    }

}
