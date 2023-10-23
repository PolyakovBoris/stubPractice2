package stub2.Controllers;

import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import stub2.Models.User;

import javax.validation.Valid;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@RestController
@RequestMapping(value = "/user")
public class Controller {
    @SneakyThrows
    @GetMapping
    public ResponseEntity<?> getUser(@RequestParam String login) {
        QueryPostgres queryPostgres = new QueryPostgres();

        // запрос данных по пользователю
        User user = queryPostgres.getSelect(login);
        System.out.println(user);
        return ResponseEntity.ok(user);
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<?> postUser(@Valid @RequestBody User user) {


        QueryPostgres queryPostgres = new QueryPostgres();
        user.setLogin(user.getLogin() + new Random().nextInt(1000 - 1));
        user.setDate(LocalDate.now().toString());
        // добавление пользователя
        System.out.println(queryPostgres.makeInsert(user));

        return ResponseEntity.ok(user);

    }
    @ExceptionHandler(Exception.class)

    public ResponseEntity<?> handleException(org.springframework.web.bind.MethodArgumentNotValidException exception) {
        return ResponseEntity
                .status(HttpStatus.valueOf(400))
                .body(LocalDateTime.now().toString() + " Неверный json\n" + exception.getMessage());
    }
    @ExceptionHandler(InvalidQueryException.class)

    public ResponseEntity<?> handleException(InvalidQueryException exception) {
        return ResponseEntity
                .status(HttpStatus.valueOf(500))
                .body(LocalDateTime.now().toString() + exception.getMessage());
    }
}
