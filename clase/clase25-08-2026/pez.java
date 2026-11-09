public class pez
{
    private String _nombre;
    private int _peso;
    private String _color;
    private int _tamano;
    private String _habitat;
    private String  _estado;
    //SE USA "_" PARA VARIABLES DE TIPO PRIVADAS
    
    public pez(String nombre, int peso, String color, int tamano, String habitat, String estado)

					     
    {
	this._nombre = nombre;
	this._peso = peso;
	this._color = color;
	this._tamano = tamano;
	this._habitat = habitat;
	this._estado  = estado;
	
    }

    public void infoPez()
    {
	System.out.println("Nombre:" + this._nombre);
	System.out.println("Peso:" + this._peso);
	System.out.println("Color" + this._color);
	System.out.println("Tamano" + this._tamano);
	System.out.println("Habitat" + this._habitat);
	System.out.println("Estado" + this._estado);
    }
    
}
