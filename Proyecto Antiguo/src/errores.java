public class errores {
    int numero_error;
    String mensaje="";

    public errores(int numero_error, String mensaje) {
        this.numero_error = numero_error;
        this.mensaje = mensaje;
    }

    public int getNumero_error() {
        return numero_error;
    }

    public void setNumero_error(int numero_error) {
        this.numero_error = numero_error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
