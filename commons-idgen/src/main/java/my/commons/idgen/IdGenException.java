package my.commons.idgen;

import java.io.Serial;

public class IdGenException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public IdGenException(String message) {
        super(message);
    }
}
