package tsp;

import tsp.exception.TSPParseException;

import java.io.*;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class TSPParser {
    final static int EOF = 0;
    final static int NAME = 1;
    final static int TYPE = 2;
    final static int DIM = 3;
    final static int WEIGHTTYPE = 4;
    final static int NODETYPE = 5;
    final static int NODESECTION = 6;

    private BufferedReader source;
    private String instanceName;
    private double[][] vertices;
    private int weightType;

    TSPParser(String path) throws IllegalArgumentException, IOException {
        if (!path.endsWith(".tsp"))
            throw new IllegalArgumentException("input file name has to be in TSP format (*.tsp)");

        final URL url =  this.getClass().getResource(path);

        if (url == null)
            throw new IllegalArgumentException("file \"" + path + "\" not found");

        File f = new File(url.getFile());

        source = new BufferedReader(new InputStreamReader(new FileInputStream(f)));

        instanceName = path;
        vertices = null;
        weightType = -1;
    }

    public String getName() { return instanceName; }

    public double[][] getVertices() { return vertices; }

    public int getWeightType() { return weightType; }

    public void parse() throws IOException, TSPParseException {
        int section = -1;
        String str = null;
        boolean typeIsChecked = false;
        boolean weightTypeIsChecked = false;

        do {
            str = source.readLine();
            section = getSection(str);

            if (section != -1) {
                switch (section) {
                    case NAME:
                        readName(str);
                        break;
                    case TYPE:
                        checkType(str);
                        typeIsChecked = true;
                        break;
                    case DIM:
                        initVerticesArray(str);
                        break;
                    case WEIGHTTYPE:
                        readWeightType(str);
                        weightTypeIsChecked = true;
                        break;
                    case NODETYPE:
                        checkNodeType(str);
                        break;
                    case NODESECTION:
                        readVertices();
                        break;
                }
            }
        } while (!((section == EOF) || (str == null)));

        if (!(typeIsChecked && weightTypeIsChecked))
            throw new TSPParseException("file contains unknown (edge) types");
    }

    private int getSection(String str) throws IOException {
        if (str == null)
            return EOF;

        StringTokenizer tokenizer = new StringTokenizer(str, " :");

        if (!tokenizer.hasMoreTokens())
            return -1;

        String firstToken = tokenizer.nextToken();

        if (firstToken.equalsIgnoreCase("eof"))
            return EOF;
        if (firstToken.equalsIgnoreCase("name"))
            return NAME;
        if (firstToken.equalsIgnoreCase("type"))
            return TYPE;
        if (firstToken.equalsIgnoreCase("dimension"))
            return DIM;
        if (firstToken.equalsIgnoreCase("edge_weight_type"))
            return WEIGHTTYPE;
        if (firstToken.equalsIgnoreCase("node_coord_type"))
            return NODETYPE;
        if (firstToken.equalsIgnoreCase("node_coord_section"))
            return NODESECTION;

        return -1;
    }

    private void readName(String str) {
        StringTokenizer tokenizer = new StringTokenizer(str, ":");
        tokenizer.nextToken();

        if (tokenizer.hasMoreTokens()) {
            instanceName = tokenizer.nextToken().trim();
        }
    }

    protected void checkType(String str) throws TSPParseException {
        StringTokenizer tokenizer = new StringTokenizer(str, ": ");
        tokenizer.nextToken();

        if (tokenizer.hasMoreTokens()) {
            String type = tokenizer.nextToken();
            if (!type.equalsIgnoreCase("tsp"))
                throw new TSPParseException("input data format is not \"TSP\"");
        } else throw new TSPParseException("unknown input data format");
    }

    private void initVerticesArray(String str) throws TSPParseException {
        StringTokenizer tokenizer = new StringTokenizer(str, ":");
        tokenizer.nextToken();

        if (tokenizer.hasMoreTokens()) {
            String dimension = tokenizer.nextToken();
            dimension = dimension.trim();

            int dim = 0;
            try {
                dim = Integer.valueOf(dimension).intValue();
            } catch (NumberFormatException e) {
                throw new TSPParseException("could not parse dimension: " + e.getMessage());
            }
            vertices = new double[dim][2];
        } else throw new TSPParseException("dimension not found");
    }

    private void readWeightType(String str) throws TSPParseException {
        StringTokenizer tokenizer = new StringTokenizer(str, ":");
        tokenizer.nextToken();

        if (tokenizer.hasMoreTokens()) {
            String type = tokenizer.nextToken();
            type = type.trim();

            if (type.equalsIgnoreCase("euc_2d"))
                weightType = 0;
            else if (type.equalsIgnoreCase("geo"))
                weightType = 1;
            else
                throw new TSPParseException("unsupported type of edge weights");
        } else throw new TSPParseException("edge weights type not found");
    }

    private void checkNodeType(String str) throws TSPParseException {
        StringTokenizer tokenizer = new StringTokenizer(str, ":");
        tokenizer.nextToken();

        if (tokenizer.hasMoreTokens()) {
            String type = tokenizer.nextToken();
            type = type.trim();

            if (!type.equalsIgnoreCase("twod_coords"))
                throw new TSPParseException("unsupported node type");
        }
    }

    private void readVertices() throws TSPParseException {
        if (vertices == null)
            throw new TSPParseException("dimension not found");

        try {
            for (int i = 0; i < vertices.length; i++) {
                String str = source.readLine();
                StringTokenizer tokenizer = new StringTokenizer(str);
                while (!tokenizer.hasMoreTokens()) {
                    str = source.readLine();
                    tokenizer = new StringTokenizer(str);
                }

                tokenizer.nextToken();
                vertices[i][0] = Double.valueOf(tokenizer.nextToken()).doubleValue();
                vertices[i][1] = Double.valueOf(tokenizer.nextToken()).doubleValue();

                if (tokenizer.hasMoreTokens())
                    throw new TSPParseException("invalid node format");
            }
        } catch (IOException e) {
            throw new TSPParseException("could not read node data: " + e.getMessage());
        } catch (NumberFormatException e) {
            throw new TSPParseException("could not parse node data: " + e.getMessage());
        } catch (NoSuchElementException e) {
            throw new TSPParseException("missing node data: " + e.getMessage());
        } catch (NullPointerException e) {
            throw new TSPParseException("missing nodes: " + e.getMessage());
        }
    }
}