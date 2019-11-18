import java.time.*;
import java.util.ArrayList;
public class Event
{
	//Atributos
	private String name;
	private String responsibleProfessor;
	private String responsibleFaculty;
	private int expectedAudience;
	private int realAttendence;
	private LocalDate date;
	private int startTime;
	private int endTime;
	private ArrayList<Auditorium> auditoriums;
	//Constructor
	public Event(String name, String responsibleProfessor, String responsibleFaculty, int expectedAudience, LocalDate date, int startTime, int endTime)
	{
		this.name=name;
		this.responsibleProfessor=responsibleProfessor;
		this.responsibleFaculty=responsibleFaculty;
		this.expectedAudience=expectedAudience;
		this.realAttendence=0;
		this.date=date;
		this.startTime=startTime;
		this.endTime=endTime;
		this.auditoriums = new ArrayList<Auditorium>();
	}
	
	//Métodos de acceso
	public String getName()
	{
		return name;
	}
	public LocalDate getDate()
	{
		return date;
	}
	public int getStartTime()
	{
		return startTime;
	}
	public int getEndTime()
	{
		return endTime;
	}
	public void setRealAttendence(int realAttendence)
	{
		this.realAttendence=realAttendence;
	}
	public int getRealAttendence()
	{
		return realAttendence;
	}
	public ArrayList<Auditorium> getAuditoriums()
	{
		return auditoriums;
	}
	//Analizaores
	public static boolean validateEvent(LocalDate date, int startTime, int endTime)
	{
		boolean validation=true;
		
		if((endTime-startTime)<2   ||  (endTime-startTime)>12  )
		{
			System.out.println("The duration of the event must be between 2 and 12 hours\n");
			validation=false;
		}
		
		if(date.isBefore(LocalDate.now())       ||       ((""+date).equals(""+LocalDate.now())    &&     startTime <= LocalTime.now().getHour())) //Si la fecha del evento está en el pasado, o estan en la misma fecha y la hora inicial del evento está en el pasado. El evento se invalida.
		{
			System.out.println("The event can not be in the past\n");
			validation=false;
		}
		
		if(startTime<7 || endTime>20)
		{
			System.out.println("The event have to be between 7:00 and 20:00\n");
			validation=false;
		}
		
		if(startTime>endTime)
		{
			System.out.println("the end time cannot be before the start time.\n");
			validation=false;
		}
		return validation;
	}
	
	public String showAuditoriumsList()
	{
		String auditoriumsList="";
		for(int i=0; i<auditoriums.size();i++)
		{
			auditoriumsList=auditoriums.get(i).getName()+"   ";
		}
		return auditoriumsList;
	}
	
	@Override
	public String toString()
	{
		return name+"   Date: "+date+"   Time: "+startTime+":00 - "+endTime+":00\n"+"Auditoriums: "+showAuditoriumsList()+"\n";
	}
	
	public String showInfoEvent()
	{
		String eventInfo="";
		eventInfo=("\nEvent: "+name+"\nResponsible Professor: "+responsibleProfessor+"\nResponsible Faculty: "+responsibleFaculty+
		"\nExpected public: "+expectedAudience+"\nReal current attendence: "+realAttendence+" persons"+"\nDate: "+date+"\nStart Time: "+startTime+":00\nEnd Time: "+endTime+":00\n"+"Auditoriums: "+showAuditoriumsList()+"\n\n\n");
		return eventInfo;
	}
}