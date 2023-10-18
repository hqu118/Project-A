package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;

public class GraphUI {

    private String fileName;
    // This is set to true if a valid file has been opened
    private boolean fileStatus;
    // These store the lines, the elements of the set and the elements of the
    // relation
    // when the file is successfully opened
    private final List<String> fileLines;
    private final List<String> setElements;
    private final List<String> relationElements;
    private final List<String> weightElements;

    public static Scanner scanner = new Scanner(System.in);

    public GraphUI() {
        topicScreen();
        fileName = "NULL";
        fileLines = new ArrayList<>();
        fileStatus = false;
        setElements = new ArrayList<>();
        relationElements = new ArrayList<>();
        weightElements = new ArrayList<>();
    }

    private void topicScreen() {
        System.out.println("----------------------------------------------------------------------");
        System.out.println("The Graph Calculator. To know available commands, please type 'help'");
        System.out.println("----------------------------------------------------------------------");
    }

    private boolean processFile() {
        fileLines.clear();
        try {
            File f = new File(fileName);
            if (!f.exists()) {
                setFileStatusFalse();
                return false;
            } else {
                setFileStatusTrue();
            }
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                fileLines.add(s.nextLine());
            }
            s.close();
        } catch (FileNotFoundException e) {
            System.out.println("File reading error");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void setFileStatusTrue() {
        fileStatus = true;
    }

    public void setFileStatusFalse() {
        fileStatus = false;
    }

    // Other getters and setters
    public void setFileName(String file) {
        fileName = createFileName(file);
    }

    public String getFileName() {
        return fileName;
    }

    public List<String> getWeightElements() {
        return weightElements;
    }

    public boolean getFileStatus() {
        return fileStatus;
    }

    public List<String> getSetElements() {
        return setElements;
    }

    public List<String> getRelationElements() {
        return relationElements;
    }

    private String createFileName(String file) {
        String line = System.getProperty("user.dir");
        System.out.println("The current directory is " + line);
        line = line + File.separator + "testcases" + File.separator + file;
        System.out.println("The full path name is: " + line);
        return line;
    }

    private void listSetMembers() {
        System.out.println("The set elements are: " + concatenateElements(Collections.enumeration(setElements)));
    }

    private String concatenateElements(Enumeration<String> elements) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        while (elements.hasMoreElements()) {
            String nextEl = (String) elements.nextElement();
            sb.append(nextEl);
            if (elements.hasMoreElements()) {
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
