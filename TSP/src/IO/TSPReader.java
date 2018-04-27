package IO;

import Structure.Node;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class TSPReader {

    private String name;
    private String path;
    private List<Node> nodes;
    private int bestKnown;
    private int length;

    public TSPReader(String path){
        this.path = path;
    }

    public void read() {

        nodes = new ArrayList<>();

        File file = new File(path);
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
}
