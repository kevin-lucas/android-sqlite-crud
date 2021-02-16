package br.com.kevinlucas.android.carteiradeclientes.database;

public class ScriptDLL {
    public static String getCreateTableCliente() {

        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS cliente (  ");
        sql.append("codigo INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sql.append("nome VARCHAR (250) NOT NULL DEFAULT '',  ");
        sql.append("endereco VARCHAR (255) NOT NULL DEFAULT '',  ");
        sql.append("email VARCHAR (200) NOT NULL DEFAULT '',  ");
        sql.append("telefone VARCHAR (20) NOT NULL DEFAULT '');  ");

        return sql.toString();

    }

}
