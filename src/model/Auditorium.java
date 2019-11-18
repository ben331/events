import java.time.*;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Scanner;
public class Auditorium
{
	//Constantes
	public static final String[] ABC= {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	
	//Atributos
	private String name;
	private String location;
	private Chair[][] chairs;
	private ArrayList<Event> events;
	private Scanner reader= new Scanner(System.in);
	private int higherRow;
	
	//Constructor
	public Auditorium(String name, String location, int rows, int[] numberOfChairs)
	{
		int[] nums = numberOfChairs;
		this.higherRow=higherOf(numberOfChairs);
		int temporal=0;
		
		this.name=name;
		this.location=location;
		chairs = new Chair[rows][higherRow];
		events= new ArrayList<Event>();
	}
	
	//Métodos de acceso
	public String getName()
	{
		return name;
	}
	public String getLocation()
	{
		return location;
	}
	//Analizadores
	/**
	*<b>DES: </b>Este método permite crear las sillas del auditorio, una vez se crea el objeto auditorio; por distribución
	física, la cantidad de sillas que se crearan dependerá del número filas, y del número de sillas por fila recibido en un arreglo como parámetro<br>
	*<b>PRE: </b>El objeto auditorio debe estar instanciado.<br>
	*<b>POST: </b>Las sillas del auditorio deberán estar creadas.<br>
	*@param numberOfChairs. Es una lista de numeros enteros que representan ordenadamente la cantidad de sillas por fila del auditorio. rows!=0 rows!=null
	*/
	public void createChairs(int[] numberOfChairs)
	{
		//Crear un silla en cada posición.
		for(int i=0; i<chairs.length;i++)
		{
			for(int j=0; j<numberOfChairs[i];j++)
			{
				chairs[i][j]= new Chair();
			}
		}
	}
	
	/**
	*<b>DES: </b>permite cambiar el estado de una silla de OPERATIVA a DEFECTUOSA, registrando la descripción del defecto.<br>
	*<b>PRE: </b>El caracter y el número ingresado deben representar la fila y la columna de la silla respectivamente.<br>
	*<b>POST: </b>Las silla en la posición dada cambiará su estado de OPERATIVA a DEFECTUOSA.<br>
	*@param row. Es una letra del abecedario que representa una fila del auditorio. row!="", row!=nul, el equivalente númerico de la letra ingresada debe ser menor al número de filas.
	*@param col. Es un número entero que representa una columna del auditorio. col!=0, col!=null, col debe ser menor o igual al numero de columnas.
	*@param defect. Es texto con una breve descripción del defecto que tenga la silla. defect!=null
	*@return confirmation. Es una cadena de texto que informa si el estado de la silla a cambiado correctamente.
	*/
	public String reportDefectiveChair(char row, int col, String defect)
	{
		int _row=charToInt(row);
		chairs[_row][col].setState(Chair.FAULTY);
		chairs[_row][col].setDefect(defect);
		return "The state of the chair"+row+col+" has been set to faulty";
	}
	
	/**
	*<b>DES: </b>permite obtener la posicion de una letra respecto el orden del abecedario.<br>
	*@param row. Es una letra del abecedario. row!="", row!=nul.
	*@return _row. Es un número entero que representa la posicion de la letra ingresada respecto al abecedario. _row!=null
	*/
	public int charToInt(char row)
	{
		int _row=0;
		for(int i=0; i<ABC.length; i++)
		{
			if((row+"").equalsIgnoreCase(ABC[i]))
			{
				_row=i;
			}
		}
		return _row;
	}
	/**
	*<b>DES: </b>Este método permite asignarle un evento al auditorio si el periodo del evento no se cruza con el de los anteriores.<br>
	*<b>PRE: </b> La duracion del evento ingresado es mínimo de dos horas y máximo de 12 horas, la hora final del evento es mayor que la hora inicial del evento.<br>
	*<b>POST: </b>El evento ingresado se asignará al atributo event del auditorio o no se agregará.<br>
	*@param event. Es un objeto evento. event!=null
	*@return confirmation. Es una cadena de texto que informa si el evento ha sido asignado correctamente.
	*/
	public boolean validateEvent(Event event)
	{
		update();  //Actualizar lista de eventos.
		boolean areThereSpace=true; //	Esta variable representa si hay espacio en el día para reservar el evento ingresado.
		for(int i=0; i<events.size(); i++)
		{
			if(event.getDate().equals(events.get(i).getDate()))                 //Si el evento a ingresar esta en la misma fecha de otro evento reservado y...
			{
				if(   (event.getEndTime() < events.get(i).getStartTime()) |        //Si la hora final del evento a ingresar es menor a la hora inicial de el evento (i)...
				((event.getStartTime() > events.get(i).getEndTime())))             //... O (exclusivo) la hora inicial del evento a ingresar es mayor a la hora inicial del evento(i), entonces...
				{
					continue;                                                                          //Hay espacio para el evento, y comparo con el siguiente evento
				}
				else                                                                                 //Sino, no hay espacio para el evento, y no se compara con mas eventos.
				{
					areThereSpace=false;     
					break;
				}
			}
			else                                                                //Si el evento a ingresar esta en una fecha sin eventos, hay espacio para el evento.
			{
				areThereSpace=true;
				break;
			}
			
		}
		return areThereSpace;
	}
	
	
	/**
	*<b>DES: </b>permite agregar un evento a la lista de eventos del auditorio.<br>
	*<b>POST: </b>El evento ingresado será añadido a la lista de eventos del auditorio.<br>
	*@param event. Es un objeto de la clase Event. event!=null
	*@return confirmation. Es una cadena de texto que informa que el evento se ha agregado correctamente.
	*/
	public String bookEvent(Event event)
	{
		String confirmation="";
		events.add(event);
		confirmation ="The event "+event.getName() +" was booked correctly";
		return confirmation;
	}
	
	/**
	*<b>DES: </b>Este método permite borrar un evento que ya ha pasado, y colocar las sillas como desocupadas cuando no haya eventos<br>
	*<b>PRE: </b> Los ArrayList events y chairs deben estar inicializado.<br>
	*<b>POST: </b> Los eventos que ya hayan pasado serán removidos del ArrayList events, si el auditorio no tienen eventos actuales, las sillas estaran desocupadas.<br>
	*/
	public void update()
	{		
		for(int i=0; i<events.size();i++)
		{
			if(events.get(i).getDate().isBefore(LocalDate.now()))
			{
				events.remove(i);
			}
		}
		if(getCurrentEvent()==null)
		{
			for(int i=0; i<chairs.length;i++)
			{
				for(int j=0; j<chairs[i].length; j++)
				{
					if(chairs[i][j]!=null)
					{
						chairs[i][j].setIsOccupied(false);
					}
				}
			}
		}
	}
	
	/**
	*<b>DES: </b>Este método determina si el auditorio está disponible o ocupado<br>
	*@return state. Es un cadena de texto que representa el estado del auditorio en el momento, disponible, ocupado.
	*/
	
	public String determineState()
	{
		String state="Available";
		for(int i=0; i<events.size(); i++)
		{
			if(events.get(i).getDate().equals(LocalDate.now()))
			{
				if((events.get(i).getStartTime() < LocalTime.now().getHour()) && (events.get(i).getEndTime() > LocalTime.now().getHour()))
				{
					state="Occupied";
				}
			}
		}
		return state;
	}
	
	/**
	*<b>DES: </b>Este evento permite obtener el evento que este en proceso en el auditorio, de no haber ninguno retorna null.<br>
	*<b>PRE: </b>El arraylist events debe estar inicializado.<br>
	*@return event. Es un evento en proceso del presente auditorio, event=null cuando no hayan eventos en proceso.
	*/
	public Event getCurrentEvent()
	{
		Event event=null;
		for(int i=0; i<events.size(); i++)
		{
			if(events.get(i).getDate().equals(LocalDate.now()))
			{
				if((events.get(i).getStartTime() < LocalTime.now().getHour()) && (events.get(i).getEndTime() > LocalTime.now().getHour()))
				{
					event=events.get(i);
				}
			}
		}
		return event;
	}
	
	
	/**
	*<b>DES: </b>Este evento permite establecer la primer silla disponible del auditorio en estado de ocupado.<br>
	*<b>POST: </b>Alguna silla desocupada del arreglo chairs cambiara a estado de occupado.<br>
	*/
	public void newChairOccupied()
	{
		int row;
		int col;
		boolean _continue=true;
		for(int i=0; i<chairs.length && _continue;i++)
		{
			for(int j=0; j<chairs[i].length;j++)
			{
				if( chairs[i][j]!=null && (chairs[i][j].getState()+"").equals(""+Chair.OPERATIONAL) && !chairs[i][j].getIsOccupied())
				{
					chairs[i][j].setIsOccupied(true);
					System.out.println("New viewer entered");
					_continue=false;
					break;
				}
			}
		}
	}
	
	@Override
	public String toString()
	{
		String toString="";
		String nameEvent= getCurrentEvent()==null? "none":getCurrentEvent().getName();
		toString=("Auditorium: "+name+"         State: "+determineState()+"      Current Event: "+nameEvent);
		return toString;
	}
	
	/**
	*<b>DES: </b>Este evento permite obtener los nombres de los eventos reservados en el auditorio.<br>
	*@return _events. Es una cadena de texto que contiene una lista de los nombres de los eventos reservados en el auditorio. _events!=null
	*/
	public String showEvents()
	{
		String _events="";
		for(int i=0; i<events.size();i++)
		{
			_events+=(i+1)+". "+events.get(i).getName()+".\n";
		}
		return _events;
	}
	
	/**
	*<b>DES: </b>permite obtener un texto que representa la matriz de sillas del auditorio, dando información de cada una delas sillas: si es operativa, defectuosa, ocupada, o vacía.<br>
	*@return chairMatrix. Es una cadena de texto que representa la matriz de silla s del auditorio.
	*/
	public String showChairMatrix()
	{
		String chairMatrix="   ";
		for(int i=0; i<higherRow;i++)
		{
			chairMatrix+=(i+1)+"  ";
		}
		for(int i=0; i<chairs.length;i++)
		{
			chairMatrix+="\n"+ABC[i]+"  ";
			for(int j=0;j<chairs[i].length;j++)
			{
				if(chairs[i][j]!=null)
				{
					if((""+chairs[i][j].getState()).equals(""+Chair.OPERATIONAL))
					{
						if(!chairs[i][j].getIsOccupied())
						{
							chairMatrix+="E  ";
						}
						else
						{
							chairMatrix+="O  ";
						}
					}
					else
					{
						chairMatrix+="X  ";
					}
				}
				else
				{
					chairMatrix+="   ";
				}
			}
		}
		
		chairMatrix+="\n\n  E= EMPTY\n  O= OCCUPIED\n  X= FAULTY\n";
		return chairMatrix;
	}
	
	/**
	*<b>DES: </b>Este método permite obtener una reporte del auditorio: dando información básica del auditorio, una representación de la matriz de sillas del auditorio, y de algunos datos estadísticos del auditorio.<br>
	*@return infoAuditorium. Es un reprote del auditorio. infoAuditorium!=null
	*/
	public String showInfoAuditorium()
	{
		int attendence=0;
		int totalChairs=0;
		int faultyChairs=0;
		int availableChairs=0;
		double percentageDefectiveChair=0;
		
		for(int i=0; i<chairs.length;i++)
		{
			for(int j=0; j<chairs[i].length;j++)
			{
				if(chairs[i][j]!=null)
				{
					totalChairs++;
					if((chairs[i][j].getState()+"").equals(""+Chair.FAULTY))
					{
						faultyChairs++;
					}
					else if(chairs[i][j].getIsOccupied())
					{
						attendence++;
					}
				}
			}
		}
		
		if(getCurrentEvent()!=null)
		{
			getCurrentEvent().setRealAttendence(attendence);
		}
		
		availableChairs=totalChairs-(faultyChairs+attendence);
		
		if(totalChairs!=0)
		{
			percentageDefectiveChair=((double)(faultyChairs)*100)/(double)(totalChairs);
		}
		
		
		String infoAuditorium="\n"+toString()+"\n\n"+showChairMatrix()+"\n\n";
		infoAuditorium+="  Total Chairs: "+totalChairs+"   Defective chairs: "+faultyChairs+"  Percentage of Defective Chair: "+(""+percentageDefectiveChair+"0000").substring(0,4)+"%";
		infoAuditorium+="\n  Attendence: "+attendence+"     Available Chairs: "+availableChairs;
		
		return infoAuditorium;
	}
	
	/**
	*<b>DES: </b>Este método permite obtener el número mayor de un arreglo de número dado.<br>
	*@param nums. Es un arreglo de números enteros. nums!=null
	*@return higher. Es el número mayor del arreglo dado. higher!=null.
	*/
	public int higherOf(int[] nums)
	{
		int higher=0;
		int temporal=0;
		for(int i=0; i<nums.length-1; i++)
		{
			for(int j=nums.length-1; j>0; j--)
			{
				if(nums[j]>nums[j-1])
				{
					temporal=nums[j-1];
					nums[j-1]=nums[j];
					nums[j]=temporal;
				}
			}
			
			higher=nums[0];
		}
		return higher;
	}
}