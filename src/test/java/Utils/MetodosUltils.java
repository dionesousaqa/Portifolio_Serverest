package Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MetodosUltils {
    public static String gerarEmailUnico() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        return "AntonioPereira" + timestamp + "@exemplo.com";
    }
}
