import java.util.ArrayList;
public class EventsIcesi
{
	//Atributos
	public ArrayList<Auditorium> auditoriums;
	public ArrayList<Event> events;
	
	//Constructor
	public EventsIcesi()
	{
		auditoriums = new ArrayList<Auditorium>();
		events = new ArrayList<Event>();
	}
	
	//Metodos de acceso
	public ArrayList<Auditorium> getAuditoriums()
	{
		return auditoriums;
	}
	
	public ArrayList<Event> getEvents()
	{
		return events;
	}
	//Analizadores
	/**
	*<b>DES: </b>Este evento permite obtener una lista de los auditorios registrados en el programa.<br>
	*@return auditoriumsList. Es una cadena de texto que contiene una lista de los auditorios en el programa.
	*/
	public String showAuditoriumsList()
	{
		String auditoriumsList="";
		for(int i=0; i<auditoriums.size();i++)
		{
			auditoriumsList+=(i+1)+"."+auditoriums.get(i).toString()+"\n\n";
		}
		return auditoriumsList;
	}
	
	/**
	*<b>DES: </b>Este evento permite obtener una lista de los eventos registrados en el programa.<br>
	*@return auditoriumsList. Es una cadena de texto que contiene una lista de los eventos en el programa.
	*/
	public String showEventsList()
	{
		String eventsList="";
		for(int i=0; i<events.size();i++)
		{
			eventsList+=(i+1)+"."+events.get(i).toString()+"\n";
		}
		return eventsList;
	}
	
	
	/**
	*<b>DES: </b>Este método permite crear y agregar un auditorio a la lista de auditorios del programa.<br>
	*<b>PRE: </b>El valor del parámetro rows es igual al tamaño del arreglo numberOfChairs.<br>
	*<b>POST: </b>El auditorio ingresado se asignará al arraylist auditoriums del programa.<br>
	*@param name. Es el nombre del auditorio. name!=null
	*@param location. Es la ubicación del auditorio. location!=null
	*@param rows. Es el número de filas de sillas del auditorio. rows!=0; rows!=null
	*@param numberOfChairs. Es un arreglo de números enteros que representan la cantidad de sillas c´por fila del auditorio. numberOfChairs!=null. numberOfChairs.length=rows
	*@return confirmation. Es una cadena de texto que informa si el evento ha sido asignado correctamente.
	*/
	public String addAuditorium(String name, String location, int rows, int[] numberOfChairs)
	{
		String confirmation="";
		Auditorium newAuditorium = new Auditorium(name, location, rows, numberOfChairs);
		auditoriums.add(newAuditorium);
		auditoriums.get(auditoriums.size()-1).createChairs(numberOfChairs);
		confirmation= "\n\nThe auditorium "+name+" was added correctly\n\n";
		return confirmation;
	}
	
	/**
	*<b>DES: </b>Este método permite crear y agregar un evento a la lista de auditorios del programa.<br>
	*<b>PRE: </b>Se recibe un objeto instanciado de la clase Event.<br>
	*<b>POST: </b>El evento ingresado se asignará al arraylist events del programa.<br>
	*@param event. Es un evento. event!=null
	*/
	public void addEvent(Event event)
	{
		events.add(event);
	}
	

	/**
	*<b>DES: </b>Este método permite validar la entrada de un evento a un auditorio para que este evento no se cruce con los demas eventos que ya tenga el auditorio.<br>
	*<b>PRE: </b>La clase Auditorium debe tener defido el método validateEvent(event) el cual valida que este evento no se cruce con los anteriores.<br>
	*@param event. Es objeto Event. event!=null
	*@param auditoriumsEvent. Es un arreglo de numeros enteros que representan las posiciones de los auditorios a los cuaales se requiere agregar el evento. auditoriumsEvent!=null
	*@return validation. Es un valos lógico. validation=true, cuando el evento no se cruce con los anteriores. event=false, cuando el evento se cruce con algun otro evento de los anteriores. validation!=null
	*/
	public boolean validateEvent(Event event, int[] auditoriumsEvent)
	{
		boolean validation=true;
		
		for(int i=0; i<auditoriumsEvent.length;i++)
		{
			if(i==auditoriumsEvent[i])
			{
				if(!auditoriums.get(i).validateEvent(event))
				{
					System.out.println("There is no space for this event in the auditorium "+auditoriums.get(i).getName()+"\n");
					validation=false;
				}
			}
		}
		return validation;
	}
	
	/**
	*<b>DES: </b>Este método permite crear y agregar un evento a diferentes auditorios.<br>
	*<b>POST: </b>El evento ingresado se asignará al arraylist events de los auditorios corespondientes.<br>
	*@param event. Es objeto Event. event!=null
	*@param auditoriumsEvent. Es un arreglo de numeros enteros que representan las posiciones de los auditorios a los cuaales se requiere agregar el evento. auditoriumsEvent!=null
	*@return confirmation. Es una cadena de texto que informa si el evento ha sido asignado correctamente.
	*/
	public String bookEvent(Event event, int[] auditoriumsEvent)
	{
		String confirmation="";
		
		for(int i=0; i<auditoriumsEvent.length;i++)
		{
			if(i==auditoriumsEvent[i])
			{
				auditoriums.get(i).bookEvent(event);
				confirmation+="\nThe event was booked in the auditorium "+auditoriums.get(i).getName()+"\n";
			}
		}
		return confirmation;
	}
}