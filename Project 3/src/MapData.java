import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumMap;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

public class MapData extends Graphic {

    /**
     * 
     */
    private ArrayList<Observation> sradData = new ArrayList<Observation>();
    private ArrayList<Observation> tairData = new ArrayList<Observation>();
    private ArrayList<Observation> ta9mData = new ArrayList<Observation>();

    //private int NUMBER_OF_MISSING_OBSERVATION = 10;

    private Integer numberOfStations = null;

    String TA9M = "TA9M";
    String TAIR = "TAIR";
    String SRAD = "SRAD";
    String STID = "STID";

    int stidPosition = 0;
    int tairPosition = 4;
    int sradPosition = 13;
    int ta9mPosition = 14;

    String MESONET = "Mesonet";

    String directory;

    private Statistics tairMin;
    private Statistics tairMax;
    private Statistics tairAverage;
    private Statistics ta9mMin;
    private Statistics ta9mMax;
    private Statistics ta9mAverage;
    private Statistics sradMin;
    private Statistics sradMax;
    private Statistics sradAverage;
    private Statistics sradTotal;

    private String fileName;
    private GregorianCalendar utcDateTime;

    /**
     * creates mapData object 
     * 
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param directory
     */
    public MapData(int year, int month, int day, int hour, int minute, String directory)
    {

        this.directory = directory;

        utcDateTime = new GregorianCalendar(year, month, day, hour, minute);

    }

    /**
     * Parses the given mdf file into their arraylists 
     * 
     * @throws IOException
     */
    public void parseFile() throws IOException 
    {

        BufferedReader br = new BufferedReader(new FileReader(this.createFileName(utcDateTime.get(Calendar.YEAR),
                utcDateTime.get(Calendar.MONTH), utcDateTime.get(Calendar.DATE), utcDateTime.get(Calendar.HOUR_OF_DAY),
                utcDateTime.get(Calendar.MINUTE), directory)));

        br.readLine();
        br.readLine();
        br.readLine();

        // int count = 0;

        String record = br.readLine();

        while (record != null) 
        {

            String[] temp = record.trim().split("\\s+");

            sradData.add(new Observation(Double.parseDouble(temp[sradPosition]), temp[stidPosition]));
            tairData.add(new Observation(Double.parseDouble(temp[tairPosition]), temp[stidPosition]));
            ta9mData.add(new Observation(Double.parseDouble(temp[ta9mPosition]), temp[stidPosition]));

            // Both double and value saved and souted so thats good

            record = br.readLine();

        }

        br.close();

        calculateStatistics(tairData, TAIR);
        calculateStatistics(sradData, SRAD);
        calculateStatistics(ta9mData, TA9M);

    }

    /**
     * 
     * Create's a filename with the given data points 
     * 
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param directory
     * @return
     */
    
    public String createFileName(int year, int month, int day, int hour, int minute, String directory) 
    {

        fileName = String.format("%s/%04d%02d%02d%02d%02d.mdf", directory, year, month, day, hour, minute);

        return fileName;

    }
    
    /**
     * 
     * Create's max, min, average for the given paramid and data 
     * 
     * @param inData
     * @param paramid
     */

    private void calculateStatistics(ArrayList<Observation> inData, String paramid)
    {

        /**
         * Calculates the minimum statistcs object and sets it to a temporary statistic object
         */
        
        double mintemp = inData.get(0).getValue();
        int counter1 = 0;
        for (int i = 0; i < inData.size(); i++)
        {
            if (inData.get(i).isValid() && inData.get(i) != null && inData.get(i).getValue() < mintemp) 
            {
                mintemp = inData.get(i).getValue();
                numberOfStations = inData.size();
                counter1 = i;
            }
        }
        Statistics tempMin = new Statistics(mintemp, inData.get(counter1).getStid(), utcDateTime, numberOfStations,
                StatsType.MINIMUM);

        /**
         * Calculates the maximum temp
         */
        double maxtemp = inData.get(0).getValue();
        int counter2 = 0;
        for (int i = 0; i > inData.size(); i++)
        {

            if (inData.get(i).isValid() && inData.get(i) != null && inData.get(i).getValue() > mintemp) 
            {

                maxtemp = inData.get(i).getValue();
                numberOfStations = inData.size();
                counter2 = i;
                
            }

        }
        Statistics tempMax = new Statistics(maxtemp, inData.get(counter2).getStid(), utcDateTime, numberOfStations,
                StatsType.MAXIMUM);

        /**
         * Calculates the average temp
         */
        
        double total = 0;
        for (int i = 0; i < inData.size(); i++)
        {
            if (inData.get(i).isValid() && inData.get(i) != null) 
            {
                total = total + inData.get(i).getValue();
            }
        }
        double tempavg = total / inData.size();
        Statistics tempAvg = new Statistics(tempavg, MESONET, utcDateTime, numberOfStations, StatsType.AVERAGE);

        // TA9M, TAIR, SRAD

        if (paramid == TA9M)
        {
            ta9mMin = tempMin;
            ta9mMax = tempMax;
            ta9mAverage = tempAvg;
        } 
        else if (paramid == TAIR)
        {
            tairMin = tempMin;
            tairMax = tempMax;
            tairAverage = tempAvg;
        } 
        else if (paramid == SRAD)
        {
            sradMin = tempMin;
            sradMax = tempMax;
            sradAverage = tempAvg;
            sradTotal = new Statistics(total, MESONET, utcDateTime, numberOfStations, StatsType.TOTAL);

        }

    }

    public String toString() 
    {

        String thing =

                ("=========================================================\n" + "===" + " "
                        + String.format("%04d" + "-" + "%02d" + "-" + "%02d  ", utcDateTime.get(Calendar.YEAR),
                                utcDateTime.get(Calendar.MONTH), utcDateTime.get(Calendar.DATE))
                        +

                        String.format("%02d" + ":" + "%02d" + " ===\n", utcDateTime.get(Calendar.HOUR_OF_DAY),
                                utcDateTime.get(Calendar.MINUTE))

                        + "=========================================================\n" +

                        "Maximum Air Temperature[1.5m] = " +

                        String.format("%.1f" + " C at " + "%s\n", tairMax.getValue(), tairMax.getStid()) +

                        "Minimum Air Temperature[1.5m] = " +

                        String.format("%.1f" + " C at " + "%s\n", tairMin.getValue(), tairMin.getStid()) +

                        "Average Air Temperature[1.5m] = " +

                        String.format("%.1f" + " C at " + "%s\n", tairAverage.getValue(), tairAverage.getStid()) +

                        "=========================================================\n\n"

                        +

                        "=========================================================\n"

                        + "Maximum Air Temperature[9.0m] = " +

                        String.format("%.1f" + " C at " + "%s\n", ta9mMax.getValue(), ta9mMax.getStid()) +

                        "Minimum Air Temperature[9.0m] = " +

                        String.format("%.1f" + " C at " + "%s\n", ta9mMin.getValue(), ta9mMin.getStid()) +

                        "Average Air Temperature[9.0m] = " +

                        String.format("%.1f" + " C at " + "%s\n", ta9mAverage.getValue(), ta9mAverage.getStid()) +

                        "=========================================================\n\n" +

                        "=========================================================\n"
                        + "Maximum Solar Radiation[1.5m] = " +

                        String.format("%.1f" + " W/m^2 at " + "%s\n", sradMax.getValue(), sradMax.getStid()) +

                        "Minimum Solar Radiation[1.5m] = " +

                        String.format("%.1f" + " W/m^2 at " + "%s\n", sradMin.getValue(), sradMin.getStid()) +

                        "Average Solar Radiation[1.5m] = " +

                        String.format("%.1f" + " W/m^2 at " + "%s\n", sradAverage.getValue(), sradAverage.getStid())
                        + "=========================================================");

        return thing;
    }

    public Observation getSradAverage()
    {
        return sradAverage;
    }

    public Observation getSradMax() 
    {
        return sradMax;
    }

    public Observation getSradMin() 
    {
        return sradMin;
    }

    public Observation getSradTotal() 
    {
        return sradTotal;
    }

    public Observation getTa9mAverage() 
    {
        return ta9mAverage;
    }

    public Observation getTa9mMax() 
    {
        return ta9mMax;
    }

    public Observation getTa9mMin() 
    {
        return ta9mMin;
    }

    public Observation getTairAverage() 
    {
        return tairAverage;
    }

    public Observation getTairMax() 
    {
        return tairMax;
    }

    public Observation getTairMin() 
    {
        return tairMin;
    }

}
