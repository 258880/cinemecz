import java.util.ArrayList;
import java.util.Scanner;

public class app
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenido al cajero automático");

        while (true)//sirve para que no se salga del menú
	    {
		System.out.println("1. Iniciar sesión");
		System.out.println("2. Salir");
		System.out.print("Selecciona una opción: ");

		int option = scanner.nextInt();//captura el int, de no estar en los cases, te manda al default
	    
		scanner.nextLine();

		switch (option)
		    {
		    case 1:
		
			login(scanner);
			break;
		
		    case 2:
		
			System.out.println("Saliendo...\n\n");
			return;
		
		    default:
		
			System.out.println("Opción no válida. Por favor, intenta de nuevo.\n\n");
		    }
	    }
    }

    private static void login(Scanner scanner)
    {
        ArrayList<funciones> cuentas = calculations.getAccounts();

        System.out.print("Ingrese su número de cuenta: ");
        String numCuenta = scanner.nextLine();

        System.out.print("Ingresa tu nip: ");
        String pin = scanner.nextLine();

        funciones found = null;
        for (funciones cuenta : cuentas)
	    {
		if (cuenta.rNumCuenta().equals(numCuenta) && cuenta.rPin().equals(pin)) {
		    found = cuenta; //se queda en la cuenta donde se encontro la igualdad de pin y numCuenta en la db y se trabaja con dicho objeto.
		    break;//nos manda al siguiente menú
		}
	    }

        if (found == null)// si no encuentra el correo y el nip, manda error
	    {
		System.out.println("Número de cuenta y/o nip erróneos, intenta nuevamente\n");
	    } else if (!found.rActivo())//si se encuentra, pero aparece incativo, te da otro mensaje
	    {
		System.out.println("La cuenta está inactiva, no puede operar\n");
	    } else
	    {
		System.out.println("Bienvenido, " + found.rNombre());
		menu(scanner, found);//el menú trabaja con los datos de la cuenta encontrada
	    }
    }

    private static void menu(Scanner scanner, funciones account)
    {
        System.out.println("Menú de operaciones");

        while (true)
	    {
		System.out.println("1. Consultar saldo");
		System.out.println("2. Retirar dinero");
		System.out.println("3. Depositar dinero");
		System.out.println("4. Salir");
		System.out.print("Selecciona una opción: ");

		int option = scanner.nextInt();

		switch (option) {
		case 1:
		    showBalance(account);//detalles en calculations de estas cuatro opciones
		    break;
		case 2:
		    withdraw(scanner, account);
		    break;
		case 3:
		    deposit(scanner, account);
		    break;
		case 4:
		    System.out.println("Regresando al menú principal\n\n");
		    return;
		default:
		    System.out.println("Opción inválida. Por favor, intenta de nuevo.\n\n");
		}
	    }
    }

    private static void showBalance(funciones account)
    {
        System.out.println("\n" + account.rNombre() + ", tu saldo actual es: "
			   + account.rBalance() + "\n\n");//concatena el nombre actual y su balance
    }

    private static void withdraw(Scanner scanner, funciones account)
    {
        System.out.print("Ingrese la cantidad a retirar: ");
        double amount = scanner.nextDouble();

        if (!calculations.hasEnoughFunds(account.rBalance(), amount))
	    {
		System.out.println("Fondos insuficientes. Su saldo actual es: " + account.rBalance());//se le pone el balance recibido si es que no tiene saldos suficientes para el retiro.
		return;
	    }

        double newBalance = calculations.withdraw(account.rBalance(), amount);
        account.setBalance(newBalance);
        System.out.println("Retiro exitoso.");
        printTicket(scanner, account);
    }

    private static void deposit(Scanner scanner, funciones account)
    {
        System.out.print("Ingrese la cantidad a depositar: ");
        double amount = scanner.nextDouble();

        double newBalance = calculations.deposit(account.rBalance(), amount);//va a la clase de calculos, agarra la funcion deposit y usa, de la clase funciones para checar qué balance tiene.
        account.setBalance(newBalance);//cambia el valor de balance
        printTicket(scanner, account);
    }
    
    private static void printTicket(Scanner scanner, funciones account)
    {
        while (true)
	    {
		System.out.println("\nDeseas Imprimir tu ticket ? (S/N)");
		String choice = scanner.next();//captura qué rollo

		switch (choice.toUpperCase())//es como el toupper() de string.h en c
		    {
		    case "S":
			System.out.println("\nTicket de transacción");
			System.out.println("Nombre del titular: " + account.rNombre());
			System.out.println("Saldo actual: " + account.rBalance() + "\n\n");
			return;
		    case "N":
			System.out.println("No se imprimirá el ticket.\n\n");
			return;
		    default:
			System.out.println("Opción no válida. Por favor, intenta de nuevo.\n\n");
		    }
	    }
    }
}
