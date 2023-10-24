package stub2.Controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCConnection {
    public Connection getConnection() {
        Connection connection = null;
        //URL к базе состоит из протокола:подпротокола://[хоста]:[порта_СУБД]/[БД] и других_сведений
        String url = "jdbc:postgresql://192.168.1.5:5432/habrdb";
        //Имя пользователя БД
        String name = "habrpguser";
        //Пароль
        String password = "pgpwd4habr";
        try {
            //Загружаем драйвер
            Class.forName("org.postgresql.Driver");
            System.out.println("Драйвер подключен");
            //Создаём соединение
            connection = DriverManager.getConnection(url, name, password);
            System.out.println("Соединение установлено");
        } catch (Exception ex) {
            //выводим наиболее значимые сообщения
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }
}
