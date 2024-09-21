package maintenanceapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SelectionMode;

public class FXMLDocumentController implements Initializable {

    private MaintenanceLogic maintLog;
    private List<String> originalToolsList;
    private List<String> workingToolsList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        maintLog = new MaintenanceLogic();
        listBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        originalToolsList = new ArrayList<>();
        workingToolsList = new ArrayList<>();
    }

    @FXML
    private Button addButton;

    @FXML
    private Button exitButton;

    @FXML
    private ListView<String> listBox;

    @FXML
    private Button loadButton;

    @FXML
    private AnchorPane maintenanceProgramLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField fileNameTextField;

    @FXML
    private Button removeButton;

    @FXML
    private Button resetButton;

    @FXML
    private Button selectButton;

    @FXML
    private Label addToolLabel;

    @FXML
    private TextField toolNameTextField;

    @FXML
    private Label errorMsg;

    @FXML
    void handleAddButtonAction(ActionEvent event) {
        try {
            String newName = toolNameTextField.getText().trim();
            if (!newName.isEmpty()) {
                workingToolsList.add(newName);
                listBox.getItems().clear();
                listBox.getItems().addAll(workingToolsList);
                toolNameTextField.clear();
            } else {
                System.out.println("Please enter a tool name.");
            }
        } catch (Exception e) {
            System.out.println("Error Adding Tool: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleExitButtonAction(ActionEvent event) {
        try {
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Exit Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleLoadButtonAction(ActionEvent event) {
        try {
            originalToolsList.clear();
            workingToolsList.clear(); 

            String fileName = fileNameTextField.getText().trim(); 
            maintLog.setFileNames(fileName, fileName);  

            if (maintLog.openFileandRead()) {
                maintLog.readFile(originalToolsList);  
                workingToolsList.addAll(originalToolsList);  
                listBox.getItems().clear(); 
                listBox.getItems().addAll(workingToolsList);  
            } else {
                System.out.println("Failed to load the tools from file.");
            }
        } catch (Exception e) {
            System.out.println("Error Loading Tools: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleRemoveButtonAction(ActionEvent event) {
        try {
            String selectedTool = listBox.getSelectionModel().getSelectedItem();
            if (selectedTool != null) {
                workingToolsList.remove(selectedTool);  
                listBox.getItems().clear();
                listBox.getItems().addAll(workingToolsList);
            } else {
                System.out.println("Please select a tool to remove.");
            }
        } catch (Exception e) {
            System.out.println("Error Removing Tool: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleResetButtonAction(ActionEvent event) {
        try {
            workingToolsList.clear(); 
            workingToolsList.addAll(originalToolsList); 
            listBox.getItems().clear();
            listBox.getItems().addAll(workingToolsList); 
        } catch (Exception e) {
            System.out.println("Reset Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleSelectButtonAction(ActionEvent event) {
        try {
            List<String> selectedTools = listBox.getSelectionModel().getSelectedItems();

            if (!selectedTools.isEmpty()) {
                StringBuilder selectedNamesString = new StringBuilder();
                for (String tool : selectedTools) {
                    selectedNamesString.append(tool).append("\n");
                }

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Selected Tools");
                alert.setHeaderText("The following tools were selected:");
                alert.setContentText(selectedNamesString.toString());
                alert.showAndWait();
            } else {
                System.out.println("No tools selected.");
            }
        } catch (Exception e) {
            System.out.println("Error Selecting Tools: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleSaveToFileAction(ActionEvent event) {
        try {
            if (maintLog.openFileandWrite()) {
                maintLog.writeToFile(workingToolsList);
            } else {
                System.out.println("Failed to open file for writing.");
            }
        } catch (Exception e) {
            System.out.println("Error Saving Tools to File: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
