package IO;

import Structure.Tour;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TSPWriter {

    public void write(String filePath, Tour tour, double temperature, double coolingRate, long seed) {

        String filename = findFileName(filePath);
        //File f = new File(filePath);

        FileOutputStream outputStream;
        try {
            //outputStream = new FileOutputStream("../Opt_Tour/" + filename + ".opt.tour");
            outputStream = new FileOutputStream(filePath);


            String line = "NAME : " + filename + ".opt.tour" + "\n";
            byte[] strToBytes = line.getBytes();
            outputStream.write(strToBytes);

            line = "COMMENT : Solution with NN for " + filename + ".tsp (" + tour.getTourLength() + ")\n";
            strToBytes = line.getBytes();
            outputStream.write(strToBytes);

            line = "PARAMETERS : \n - Temperature : " + temperature +
                    "\n - Cooling Rate : " + coolingRate +
                    "\n - Seed : " + seed + "\n";
            strToBytes = line.getBytes();
            outputStream.write(strToBytes);

            line = "TYPE : TOUR" + "\n";
            strToBytes = line.getBytes();
            outputStream.write(strToBytes);

            line = "DIMENSION : " + tour.getTour().length + "\n";
            strToBytes = line.getBytes();
            outputStream.write(strToBytes);

            line = "LENGTH : " + tour.getTourLength() + "\n";
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("File wrote correctly in: " + "../ALGO_cup_2018_problems/" + filename + ".opt.tour");
    }

    private String findFileName(String filePath) {
        String[] strings = filePath.split("/");
        String[] strings1 = strings[strings.length - 1].split("\\.");
        return strings1[strings1.length - 3];
    }

}
