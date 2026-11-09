public class app
{
    public static void main(String[] args)
    {
	empleadoXhora empleado1 = new empleadoXhora(15, 8, "juanin", 55555, 0);
	System.out.println("el salario del esperpento de:" + empleado1.nombre + ":" + empleado1.calcularSalario());
    }
}
