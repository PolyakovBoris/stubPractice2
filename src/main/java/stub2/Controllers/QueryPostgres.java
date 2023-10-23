package stub2.Controllers;

import stub2.Models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryPostgres {

    // выборка данных SELECT принимает STRING login возвращает объект user
    public User getSelect(String login) throws InvalidQueryException {

        JDBCConnection newConnection = new JDBCConnection();
        ResultSet result1 = null;
        Statement statement = null;
        User user = null;
        try {

            statement = newConnection.getConnection().createStatement();
            String query = String.format("SELECT user_login.login, password, date, email " +
                    "FROM user_login " +
                    "INNER JOIN email " +
                    "ON user_login.login = email.login " +
                    "WHERE user_login.login = '%s'", login);
            result1 = statement.executeQuery(query);
            System.out.println(result1.getMetaData());
            System.out.println("Выводим statement");
            boolean empty = true;
            while (result1.next()) {
                System.out.println("Номер в выборке #" + result1.getRow()
                        + "\t Логин в базе #" + result1.getString("login")
                        + "\t пароль в базе #" + result1.getString("password"));
                user = new User(result1.getString("login"), result1.getString("password"),
                        result1.getString("date"), result1.getString("email"));
                empty = false;
            }
            if (empty) {throw new InvalidQueryException(" Логин не найден в БД ");}
        }

        catch (SQLException e) {
            throw new RuntimeException(e);
        }  finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return user;
    }

    // добавление данных INSERT принимает объект user
    public String makeInsert(User user) throws SQLException {
        JDBCConnection newConnection = new JDBCConnection();
        int counterInsert = 0;

        try (PreparedStatement preparedStatement = newConnection.getConnection().prepareStatement(
                "INSERT INTO user_login values(?, ?, ?); INSERT INTO email values(?, ?)")){
            preparedStatement.setString(1, user.getLogin());

            preparedStatement.setString(2, user.getPassword());

            preparedStatement.setString(3, user.getDate());

            preparedStatement.setString(4, user.getLogin());

            preparedStatement.setString(5, user.getEmail());

            preparedStatement.executeUpdate();
            System.out.println(preparedStatement.getUpdateCount());
            counterInsert = preparedStatement.getUpdateCount();

        } catch (SQLException e) {
            throw new RuntimeException(e);
            }
        return user + String.format("\ninserted rows - %s", counterInsert);
    }
}
