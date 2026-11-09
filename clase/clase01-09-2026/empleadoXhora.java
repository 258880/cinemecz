
public class empleadoXhora extends empleado //para herencia de la clase empleado
{
    private double _tarifaXhora; //las variables privadas seran solo usados en la clase local
    private int _horasTrabajadas;
    private double _salario;
    
    public empleadoXhora(double tarifaXhora, int horasTrabajadas, String nombre, int id, double salario)
    {
	
	
	super(nombre, id, salario); //llama al constructor de empleado, la firma super debe poseer los mismos parámetros·
	this._tarifaXhora = tarifaXhora;
	this._horasTrabajadas = horasTrabajadas;
	this._salario = salario;
    }

    @Override //reescribe la funcion de empleado aquí en empleadoXhora
    public double calcularSalario()

    {
	return _tarifaXhora * _horasTrabajadas;
    }
}
