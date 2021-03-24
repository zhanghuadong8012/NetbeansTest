/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_sample;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 * FXML Controller class
 *
 * @author zhanghuadong
 */
public class FXMLController implements Initializable {

    @FXML
    private TableColumn<User, Integer> idColumn;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableView<User> tableView;
    @FXML
    private TextField fullnameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;
    
    
//    public void changFirstNameCellEvent(CellEditEvent event){
//        tableView.getSelectionModel().getSelectedItem().setUsername(event.getNewValue().toString());
//    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setEditable(true);
        usernameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        ObservableList<User> list = FXCollections.observableArrayList();
        
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        
        try {
            list = getUsers();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        tableView.getItems().addAll(list);
        tableView.setItems(list);

        
    }    

    private ObservableList<User> getUsers() throws SQLException {
        ObservableList<User> users = FXCollections.observableArrayList();
        User user;
        int id;
        String username,password;
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx_registration", "root", "password");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from registration");
        while(resultSet.next()){
            id = resultSet.getInt("id");
            username = resultSet.getString("full_name");
            password = resultSet.getString("password");
            user = new User(id,username,password);
            users.add(user);
            
        }
        return users;
    }

    @FXML
    private void addTableViewButton(ActionEvent event) {
        if(fullnameTextField.getText().matches("[1-9][0-9$]*")){
            User user = new User(Integer.parseInt(fullnameTextField.getText()),emailTextField.getText(),passwordTextField.getText());
            tableView.getItems().add(user);

            fullnameTextField.clear();
            emailTextField.clear();
            passwordTextField.clear();
        }
    }

    @FXML
    private void delTableViewButton(ActionEvent event) {
        
        tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());
    }
    
}
