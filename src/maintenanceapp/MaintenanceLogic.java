package maintenanceapp;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.IOException;
import java.util.Scanner;
import java.util.Formatter;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author errol
 */

public class MaintenanceLogic {

    private FileHandler fileHandler;

    public MaintenanceLogic() {
        fileHandler = new FileHandler();
    }

    public class FileHandler {
        private Formatter writer = null;
        private Scanner reader = null;
        private String readFileName;
        private String writeFileName;


        public void setFileNames(String readFileName, String writeFileName) {
            this.readFileName = readFileName;
            this.writeFileName = writeFileName;
        }

        public boolean openFileandRead() {
            try {
                reader = new Scanner(Paths.get(readFileName));
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        public boolean openFileandWrite() {
            try {
                writer = new Formatter(writeFileName);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        public boolean writeToFile(List<String> namesToFile) {
            try {
                System.out.println("Writing names to file: ");
                for (String name : namesToFile) {
                    writer.format("%s%n", name);
                    System.out.printf("%s%n", name);
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        }

        public boolean readFile(List<String> namesFromFile) {
            if (reader == null) {
                return false;
            }

            try {
                while (reader.hasNextLine()) {
                    String name = reader.nextLine().trim();
                    if (!name.isEmpty()) {
                        namesFromFile.add(name);
                    }
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        }
    }

    public void setFileNames(String readFileName, String writeFileName) {
        fileHandler.setFileNames(readFileName, writeFileName);
    }

    public boolean openFileandRead() {
        return fileHandler.openFileandRead();
    }

    public boolean openFileandWrite() {
        return fileHandler.openFileandWrite();
    }

    public boolean writeToFile(List<String> namesToFile) {
        return fileHandler.writeToFile(namesToFile);
    }

    public boolean readFile(List<String> namesFromFile) {
        return fileHandler.readFile(namesFromFile);
    }
}



