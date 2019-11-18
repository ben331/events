public class Chair
{
	//Costantes
	//Dominio del atributo state
	public static final char FAULTY='F';
	public static final char OPERATIONAL='O';
	
	
	//Atributos
	//Estado de la silla. Puede ser operacional o defectuoso.
	private char state;
	//Falso si la silla está vacia, Verdadero si la silla está siendo usada.
	private boolean isOccupied;
	//Descripción del defecto
	public String defect;
	
	
	//Constructor
	public Chair()
	{
		state=OPERATIONAL;
		isOccupied=false;
		defect="Ninguno";
	}
	
	
	//Métodos de acceso
	
	public char getState()
	{
		return state;
	}
	
	public void setState(char state)
	{
		this.state=state;
	}
	
	public boolean getIsOccupied()
	{
		return isOccupied;
	}
	public void setIsOccupied(boolean isOccupied)
	{
		this.isOccupied=isOccupied;
	}
	
	public void setDefect(String defect)
	{
		this.defect=defect;
	}
	//Metodos Analizadores
	/**
	*<b>DES: </b> Este metodo determina la disponibilidad de una silla teniendo en cuanta si esta es operacional y no esta ocupada, de lo contrario esta silla no esta´ra disponinble.<br>
	*@return availability Es un valor lógico que representa si una silla está disponible. availability!=null
	*/
	public boolean determineAvailability()
	{
		boolean availability;
		availability =( (""+state).equals(""+OPERATIONAL)  &&  !isOccupied );
		return availability;
	}
}