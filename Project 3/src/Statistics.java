import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;

public class Statistics extends Observation {

    String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss z";
    GregorianCalendar utcDateTime;
    int numberOfReportingStations;
    StatsType statType;
    
    public Statistics(double value, String stid, String dateTimeStr, int numberOfValidStations, StatsType inStatType){
        
        //doesnt test
        super(value, stid);
        this.DATE_TIME_FORMAT = dateTimeStr;
        this.numberOfReportingStations = numberOfValidStations;
        this.statType = inStatType; 
        
    }
    public Statistics(double value, String stid, GregorianCalendar dateTime, int numberOfValidStations, StatsType inStatType){
     
        //doesnt test
        super(value, stid);
        this.utcDateTime = dateTime;
        this.numberOfReportingStations = numberOfValidStations;
        this.statType = inStatType; 
    }
    
    public GregorianCalendar createDateFromString(String dateTimeStr) throws ParseException{
            
        SimpleDateFormat newform = new SimpleDateFormat(DATE_TIME_FORMAT);
        newform.parse(dateTimeStr);
        return (GregorianCalendar) (newform.getCalendar());
    }
    
    public String createStringFromDate(GregorianCalendar calendar) {
        
        SimpleDateFormat newform = new SimpleDateFormat(DATE_TIME_FORMAT);
        return (String) newform.format(DATE_TIME_FORMAT);
        
    }
    public int getNumberOfReportingStations() {
        
        return numberOfReportingStations;
    }
    
    public String getUTCDateTimeString(){
        
        return DATE_TIME_FORMAT;
        
    }
    public boolean newerThan(GregorianCalendar inDateTime) {
        
        return utcDateTime.before(inDateTime);
        
    }
    public boolean olderThan(GregorianCalendar inDateTime) {
        
        return utcDateTime.after(inDateTime);   
    }
    public boolean sameAs(GregorianCalendar inDateTime) {
        
        return utcDateTime.equals(inDateTime);
    }
    public String toString() {
        return DATE_TIME_FORMAT;    
    }
}
