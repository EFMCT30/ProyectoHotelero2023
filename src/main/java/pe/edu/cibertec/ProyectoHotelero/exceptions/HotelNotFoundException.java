package pe.edu.cibertec.ProyectoHotelero.exceptions;

public class HotelNotFoundException extends RuntimeException {
    public HotelNotFoundException(String message) {
        super(message);
    }
}

