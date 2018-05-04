package IO;

import Structure.Node;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class TSPReader {

    private String name;
    private String path;
    private String fileName;
    private List<Node> nodes;
    private int bestKnown;
    private int length;

    private int currentBest;
    private double temperature;
    private double coolingRate;
    private long seed;

    public TSPReader(String fileName){
        this.fileName = fileName;
    }

    public void readConfig(){
        File file = new File("../Config/" + fileName + ".config");
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;

        try {
            fileInputStream = new FileInputStream(file);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            String line;

            while ((line = bufferedReader.readLine()) != null) {

                if (line.equals("EOF")) {
                    break;
                }

                String[] strings = line.split("\\s+");
                String str = strings[0];

                switch (str) {
                    case "BEST":
                        currentBest = Integer.parseInt(strings[2]);
                        break;
                    case "TEMPERATURE":
                        temperature = Double.parseDouble(strings[2]);
                        break;
                    case "COOLING":
                        coolingRate = Double.parseDouble(strings[2]);
                        break;
                    case "SEED":
                        seed = Long.parseLong(strings[2]);
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null)
                    fileInputStream.close();
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void read() {

        nodes = new ArrayList<>();

        File file = new File("../Problems/" + fileName + ".tsp");
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;

        try {
            fileInputStream = new FileInputStream(file);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            String line;
            boolean populateMatrix = false;

            while ((line = bufferedReader.readLine()) != null) {

                if (line.equals("EOF")) {
                    break;
                }

                String[] strings = line.split("\\s+");
                String str = strings[0];

                switch (str) {
                    case "NAME":
                        name = strings[2];
                        break;
                    case "LENGTH":
                        length = Integer.parseInt(strings[2]);
                        break;
                    case "BEST_KNOWN":
                        bestKnown = Integer.parseInt(strings[2]);
                        break;
                    case "NODE_COORD_SECTION":
                        populateMatrix = true;
                        line = bufferedReader.readLine();
                        strings = line.split("\\s+");
                        break;
                    default:
                        break;
                }
                if (populateMatrix) {
                    if (strings[0].equals(" ") || strings[0].equals("")) {
                        nodes.add(new Node(Integer.valueOf(strings[1]) - 1, Double.parseDouble(strings[2]), Double.parseDouble(strings[3])));
                    } else {
                        nodes.add(new Node(Integer.valueOf(strings[0]) - 1, Float.parseFloat(strings[1]), Float.parseFloat(strings[2])));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null)
                    fileInputStream.close();
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public int getBestKnown() {
        return bestKnown;
    }

    public void setBestKnown(int bestKnown) {
        this.bestKnown = bestKnown;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getCurrentBest() {
        return currentBest;
    }

    public void setCurrentBest(int currentBest) {
        this.currentBest = currentBest;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getCoolingRate() {
        return coolingRate;
    }

    public void setCoolingRate(double coolingRate) {
        this.coolingRate = coolingRate;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }
}
