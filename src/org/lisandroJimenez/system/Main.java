/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lisandroJimenez.system;

import java.io.InputStream;
import java.util.Set;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.lisandroJimenez.controller.EliminarClientesController;
import org.lisandroJimenez.controller.FormClientesController;
import org.lisandroJimenez.controller.MenuClientesController;
import org.lisandroJimenez.controller.MenuPrincipalController;
import org.lisandroJimenez.controller.TicketSoporteController;

/**
 *
 * @author Usuario
 */
public class Main extends Application {
    private final String URLVIEW = "/org/lisandroJimenez/view/";
    private Stage stage;
    private Scene scene;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/org/lisandroJimenez/view/MenuPrincipalView.fxml"));
        
        this.stage = stage;
        
        stage.setTitle("Super Kinal app");
        Image icon = new Image("org/lisandroJimenez/image/icono.png");
        stage.getIcons().add(icon);
        MenuPrincipalView();
        stage.show();
        
    }
    
    public Initializable switchScene( String fxmlName, int width, int height) throws Exception{
        Initializable resultado = null;
        FXMLLoader loader = new FXMLLoader();
        InputStream file = Main.class.getResourceAsStream(URLVIEW + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(URLVIEW + fxmlName));
        scene = new Scene((AnchorPane)loader.load(file), width, height);
        stage.setScene(scene);
        stage.sizeToScene();
        
        resultado = (Initializable)loader.getController();
        return resultado;
    }
    
    public void MenuPrincipalView(){
        try{
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController)switchScene("MenuPrincipalView.fxml", 950, 625);
            menuPrincipalView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void MenuClientesView(){
        try{
            MenuClientesController menuClientesView = (MenuClientesController)switchScene("MenuClientesView.fxml", 1100, 625);
            menuClientesView.setStage(this); 
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void FormClientesView(int op){
        try{
            FormClientesController formClientesView = (FormClientesController)switchScene("FormClientesView.fxml", 450, 620);
            formClientesView.setOp(op);
            formClientesView.setStage(this);
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void MenuTicketSoporteView(){
        try{
            TicketSoporteController menuTicketSoporteView = (TicketSoporteController)switchScene("TicketSoporteView.fxml",950, 600);
            menuTicketSoporteView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

   
    
}
