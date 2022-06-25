package br.com.pjc.controle_servidor.modules.error;

public class ClientPreconditionFailed extends RuntimeException{
    public ClientPreconditionFailed() {
    }

    public ClientPreconditionFailed(String message) {
        super(message);
    }

    public ClientPreconditionFailed(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientPreconditionFailed(Throwable cause) {
        super(cause);
    }

    public ClientPreconditionFailed(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
