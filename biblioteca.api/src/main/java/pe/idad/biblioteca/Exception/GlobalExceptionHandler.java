package pe.idad.biblioteca.Exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Maneja errores de validación de los DTOs
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> manejarValidaciones(MethodArgumentNotValidException ex) {

        List<String> errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("errores", errores);

        return response;
    }
    // maneja excepciones en tiempo de ejecución generadas
    // en el service o cualquier parte de la aplicación.
    @ExceptionHandler(RuntimeException.class)
    public Map<String, Object> manejarRuntime(RuntimeException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("error", ex.getMessage());

        return response;
    }
}
