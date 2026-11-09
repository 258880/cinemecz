import java.util.ArrayList;

public class reservas
{
    private String _numCuenta, _nombre, _apellido, _numTel, _email, _pin;
    private double _balance;
    private boolean _activo;

    public reservas(String numCuenta, String nombre, String apellido,
                   String numTel, String email, String pin,
                   double balance, boolean activo) {
        this._numCuenta = numCuenta;
        this._nombre = nombre;
        this._apellido = apellido;
        this._numTel = numTel;
        this._email = email;
        this._pin = pin;
        this._balance = balance;
        this._activo = activo;
    }
