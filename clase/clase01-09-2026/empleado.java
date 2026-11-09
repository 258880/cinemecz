public abstract class empleado
{
    public String nombre;
    public int id;

    public empleado(String nombre, int id, double salario)
    {
	this.nombre = nombre; //this funciona para
	this.id = id;
    }
    
    public abstract double calcularSalario();

  
    public double calcularSalarioMinusTax()
    {
	return 0;
    }
}
