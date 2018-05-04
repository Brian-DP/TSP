package IO;

import Structure.Tour;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TSPWriter {

    public void writeConfig(String filename, String filePath, Tour tour, double temperature, double coolingRate, long seed){

        FileOutputStream outputStream;

        try {
            outputStream = new FileOutputStream("../Config/" + filename + ".config");

            String line = "NAME : " + filename + "\n";
            byte[] strToBytes = line.getBytes();
            outputStream.write(strToBytes);

            line = "BEST : " + tour.getTourLength() + "\n";
            strToBytes = line.getBytes();
            outputStream.write(strToBytes);

            line = "TEMPERATURE : " + temperature + "\n";
            strToBytes = line.getBytes();
            outputStream.write(strToBytes);

            line = "COOLING : " + coolingRate + "\n";
            strToBytes = line.getBytes();
            outputStream.write(strToBytes);

            line = "SEED : " + seed + "\n";
            strToBytes = line.getBytes();
            outputStream.write(strToBytes);

            outputStream.write("EOF".getBytes());
            outputStream.close();

            System.out.println("Configuration file created");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException io){
            io.printStackTrace();
        }
    }

    public void write(String filePath, Tour tour) {

        String filename = findFileName(filePath);

        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(filePath);


            String line = "NAME : " + filename + ".opt.tour" + "\n";
            byte[] strToBytes = line.getBytes();
            outputStream.write(strToBytes);

            line = "COMMENT : Solution with NN for " + filename + ".tsp (" + tour.getTourLength() + ")\n";
            strToBytes = line.getBytes();
            outputStream.write(strToBytes);

            line = "TYPE : TOUR" + "\n";
            strToBytes = line.getBytes();
            outputStream.write(strToBytes);

            line = "DIMENSION : " + tour.getTour().length + "\n";
            strToBytes = line.getBytes();
            outputStream.write(strToBytes);

            line = "TOUR_SECTION" + "\n";
            strToBytes = line.getBytes();
            outputStream.write(strToBytes);

            for (int i = 0; i < tour.getTour().length; i++) {
                line = (tour.getTour()[i]+1) + "\n";
                outputStream.write(line.getBytes());
            }

            outputStream.write(("-1\n").getBytes());
            outputStream.write("EOF".getBytes());

            outputStream.close();


            System.out.println("Solution file created");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            outputStream = new FileOutputStream("../Solutions/" + filename + ".claim");

            String line = tour.getTourLength() + "\n";
            byte[] strToBytes = line.getBytes();
            outputStream.write(strToBytes);
            outputStream.close();

            System.out.println("Claim file created");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException io){
            io.printStackTrace();
        }

    }

    private String findFileName(String filePath) {
        String[] strings = filePath.split("/");
        String[] strings1 = strings[strings.length - 1].split("\\.");
        return strings1[strings1.length - 3];
    }

}
