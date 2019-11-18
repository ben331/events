import java.time.*;
import java.util.Scanner;
public class Main
{
	private static Scanner reader= new Scanner(System.in);
	private static EventsIcesi eventsIcesi = new EventsIcesi();
	
	public static void main(String[]args)
	{
		//Variables
		int option=0;
		int option2=0;
		int option3=0;
		
		int optionA=0;
		int auditoriumIndex=-1;
		int eventIndex=-1;
		String coordinate="";
		String defect="";
		char row;
		int col=0;
		//Instancias predeterminadas
		inIt();
		
		//Inicio
		System.out.println("Welcome to Events Icesi\n\n");
		
		//Menu 1
		do
		{
			System.out.println("1.Auditoriums \n2.Events \n3.Exit");
			option=reader.nextInt();
			reader.nextLine();
			
			switch(option)
			{
				
				//Lista de auditorios
				case 1:
				
				
				//Menu 2: añadir auditorio, seleccionar auditorio o volver
				do
				{
					System.out.println("\n"+eventsIcesi.showAuditoriumsList());
					
					
					System.out.println("1.Select Auditorium. \n2.Add Auditorium. \n3.Back ");
					option2= reader.nextInt();
					reader.nextLine();
					
					switch(option2)
					{
						case 1:
						System.out.println("Auditorium number: ");
						auditoriumIndex = (reader.nextInt())-1;
						reader.nextLine();
						
						
						//Menu 3: reportar silla defectuosa, si hay un evento en vivo se habilita la opciónn agregar espectador.
						do
						{
							eventsIcesi.getAuditoriums().get(auditoriumIndex).update();   //Actualizar las sillas como desocupadas cuando finalize un evento, y borrar eventos pasados.
						
							System.out.println(""+eventsIcesi.getAuditoriums().get(auditoriumIndex).showInfoAuditorium()+"\n\n\n");
							
							
							if(eventsIcesi.getAuditoriums().get(auditoriumIndex).getCurrentEvent()!=null)
							{
								System.out.println("1.back. \n2.Report defective Chair. \n3.Add new viewer");
								option3= reader.nextInt();
								reader.nextLine();
							}
							else
							{
								System.out.println("1.back. \n2.Report defective Chair");
								option3= reader.nextInt();
								reader.nextLine();
							}
							
							
							switch(option3)
							{	
								case 2:
								System.out.println("Type the position of the chair. (Exmple: A2): ");
								coordinate=reader.nextLine();
								
								System.out.println("Type the defect of this chair: ");
								defect= reader.nextLine();
								
								row=coordinate.charAt(0);
								col=(Integer.parseInt(coordinate.substring(1)))-1;
								
								eventsIcesi.getAuditoriums().get(auditoriumIndex).reportDefectiveChair(row, col, defect);
								
								System.out.println("The chair "+coordinate+" has been reported like a defective chair");
								break;
								
								case 3:
								if(eventsIcesi.getAuditoriums().get(auditoriumIndex).getCurrentEvent()!=null)
								{
									eventsIcesi.getAuditoriums().get(auditoriumIndex).newChairOccupied();
								}
								break;
							}
							
						}while(option3!=1);
						
						break;
						
						case 2:
						addAuditorium();
						break;

					}
				}while(option2!=3);
				break;
				
				case 2:
				do
				{
					System.out.println("\n\n"+eventsIcesi.showEventsList());
					
					System.out.println("\n\n1.Select Event. \n2.Add Event\n3.Back.\n");
					optionA= reader.nextInt();
					reader.nextLine();
					
					switch(optionA)
					{
						case 1:
						
						System.out.println("Event number: ");
						eventIndex = (reader.nextInt())-1;
						reader.nextLine();
						
						System.out.println(eventsIcesi.getEvents().get(eventIndex).showInfoEvent());
						
						break;
						
						case 2:
						addEvent();
						break;
					}
				}while(optionA!=3);
				break;
			}
		}while(option!=3);
	}
	
	
	
	
	
	
	
	public static void addAuditorium()
	{
		//Variables
		String varName;
		String varLocation;
		int varRows;  //Filas.
		int[] varNumberOfChairs; //Número de sillas por fila.
		
		
		//Pedir el nombre
		System.out.println("\nType the auditorium name: ");
		varName=reader.nextLine();
		
		//Pedir la ubicacion del auditorio
		System.out.println("\nType the auditorium location: ");
		varLocation=reader.nextLine();
		
		//Pedir la cantidad de filas de sillas del auditorio
		System.out.println("\nType the number of chairs rows of the auditorium: ");
		varRows=reader.nextInt();
		reader.nextLine();
		varNumberOfChairs= new int[varRows];
		
		//Pedir el número de sillas por fila
		for(int i=0; i<varNumberOfChairs.length; i++)
		{
			System.out.println("\n\nType the number of chairs of the row '"+(i+1)+"': ");
			varNumberOfChairs[i]=reader.nextInt();
			reader.nextLine();
		}
		
		//Agregar Auditorio
		System.out.println(eventsIcesi.addAuditorium(varName, varLocation, varRows, varNumberOfChairs));
	}
	
	public static void addEvent()
	{
		//Variables
		String name;
		String responsibleProfessor;
		String responsibleFaculty;
		int expectedAudience;
		LocalDate date;
		int year;
		int month;
		int day;
		int startTime;
		int endTime;
		int[] numberOfAuditoriums=null;
		int auditoriumIndex;
		Event event;
		
		
		System.out.println("\nType the event name: ");
		name=reader.nextLine();
		
		System.out.println("\nType the responsible professor of the event: ");
		responsibleProfessor=reader.nextLine();
		
		System.out.println("\nType the responsible faculty of the event");
		responsibleFaculty=reader.nextLine();
		
		System.out.println("\nType the  expected amount of the audience (Example: 34): ");
		expectedAudience=reader.nextInt();
		reader.nextLine();
		
		
		//Validar que el evento tenga un horario válido y una duración válida
		do
		{
			System.out.println("\nType the year of the event (Example: 2022): ");
			year=reader.nextInt();
			reader.nextLine();
			
			System.out.println("\nType the month of the event (Example: 3): ");
			month=reader.nextInt();
			reader.nextLine();
			
			System.out.println("\nType the day of the event (Example: 16): ");
			day=reader.nextInt();
			reader.nextLine();
			
			date = LocalDate.of(year, month, day);
			
			System.out.println("\nRemember that the auditorium is available from 7 to 20 \nRemember that an event can only last between two and twelve hours. \nType the start time of the event without minuts.(Example eight am is: 8) :  ");
			startTime=reader.nextInt();
			reader.nextLine();
			
			System.out.println("\nRemember that the auditorium is available from 7 to 20 \nRemember that an event can only last between two and twelve hours \nType the end time of the event without minuts.(Example three pm is: 15) ");
			endTime=reader.nextInt();
			reader.nextLine();
			
		}while(!Event.validateEvent(date, startTime, endTime));
		
		event = new Event(name, responsibleProfessor, responsibleFaculty, expectedAudience, date, startTime, endTime);
		
		
		//Validar que el evento no se cruce con otros eventos ya registrados en los auditorios
		do
		{
			//Validar que no se exceda en el numero de auditorios para agregar un evento
			do
			{
				System.out.println("\nHow many auditoriums does this event need: ");
				numberOfAuditoriums= new int[reader.nextInt()];
				reader.nextLine();
				
				if(numberOfAuditoriums.length>eventsIcesi.getAuditoriums().size())
				{
					System.out.println("\nThere are not enough auditoriums for this event.");
				}
			}while(numberOfAuditoriums.length>eventsIcesi.getAuditoriums().size());	
			
			
			//
			for(int i=0; i<numberOfAuditoriums.length;i++)
			{
				System.out.println(eventsIcesi.showAuditoriumsList());
				System.out.println("\nSelect the auditorium number "+(i+1)+" for this event\n");
				numberOfAuditoriums[i] = reader.nextInt()-1;
				reader.nextLine();
			}
		}while(!eventsIcesi.validateEvent(event, numberOfAuditoriums));
		
		for(int i=0; i<numberOfAuditoriums.length;i++)
		{
			event.getAuditoriums().add(eventsIcesi.getAuditoriums().get(numberOfAuditoriums[i]));
		}
		
		System.out.println(eventsIcesi.bookEvent(event, numberOfAuditoriums));   //Registrar el evento en los respectivos auditorios
		
		
		eventsIcesi.addEvent(event); //Añadir a la lista de eventos de EventsIcesi
	}
	public static void inIt()
	{
		int[] chairsManuelita={10,10,10,9, 9, 8, 8, 8, 7, 6};
		Auditorium manuelita = new Auditorium("Manuelita", "Frente al Edificio M", 10, chairsManuelita);
		eventsIcesi.getAuditoriums().add(manuelita);
		manuelita.createChairs(chairsManuelita);
		
		Event despedidaApo = new Event("Despedida APO", "Karen Lara", "Facultad de Ingenieria", 20, LocalDate.of(2020, 11, 15), 11, 13);
		despedidaApo.getAuditoriums().add(manuelita);
		
		eventsIcesi.addEvent(despedidaApo);
		eventsIcesi.getAuditoriums().get(0).bookEvent(despedidaApo);
	}
}