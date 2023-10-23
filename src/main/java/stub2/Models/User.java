package stub2.Models;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @NotBlank(message = " не задан логин ")
    String login;
    @NotBlank(message = " не задан пароль ")
    String password;
    @NotBlank(message = " не указана дата ")
    String date;
    @NotBlank(message = " не задана почта ")
    String email;

    public User(String login, String password, String date, String email) {
        this.login = login;
        this.password = password;
        this.date = date;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", date='" + date + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getDate() {
        return date;
    }

    public String getEmail() {
        return email;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
