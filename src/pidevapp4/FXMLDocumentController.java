/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevapp4;

import Services.TripServices;
import entite.Hotel;
import entite.Trip;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javafx.util.Duration;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import utils.MyConnexion;

/**
 *
 * @author DELL
 */
public class FXMLDocumentController implements Initializable {
             private Connection myConnex = MyConnexion.getInstanceConnex().getConnection();
public static int  nh = 0 ,nf=0 , ne = 0 ;  

    @FXML
    private Label label;
    @FXML
    private TextField txtid;
    @FXML
    private TextField txtVille;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtOffre;
    @FXML
    private TableView<Trip> tabtrip;
    @FXML
    private TableColumn<Trip, Integer> col_id;
    @FXML
    private TableColumn<Trip, String> col_ville;
    @FXML
    private TableColumn<Trip, String> col_description;
    @FXML
    private TableColumn<Trip, String> col_offre;
    @FXML
    private TableColumn<Trip, String> col_periode;
    @FXML
    private TextField txtperiode;
    @FXML
    private AnchorPane page;
    @FXML
    private Label erreurdescrip;
    @FXML
    private Label villeerreur;
    @FXML
    private Label descriperreur;
    @FXML
    private Label offreerreur;
    @FXML
    private Label periodeerreur;
      
   
    @FXML
    private ImageView recaptchaCheckMark;
    @FXML
    private ComboBox<String> cbhotel;
    @FXML
    private Label villererreur;
    
    @FXML
    private TextField lc_text;
    @FXML
    private TextField recherche_text;
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
          affichelistetrip();  
          recherche();
           try {
            fillcombo();
        } 
   catch (SQLException ex) {
        } 
  
    }
     ObservableList combo = FXCollections.observableArrayList();
     public void fillcombo() throws SQLException {
        PreparedStatement pst;
        String query = "select nom from hotel";
        pst = myConnex.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            combo.add(rs.getString("nom"));
            cbhotel.setItems(combo);
        }
    }
   public ObservableList<Trip> show1()
    { 
       

           try {
               ObservableList<Trip> obl =FXCollections.observableArrayList();
                  //exécution de la réquette et enregistrer le resultat dans le resultset
                 PreparedStatement pt= myConnex.prepareStatement("select id_trip, ville_dest,description,offre,periode from trip ");
                  ResultSet rs = pt.executeQuery();
                  while(rs.next()){
                  //obl.add(new Note(rs.getFloat(1),rs.getFloat(2),rs.getFloat(3),rs.getInt(4),rs.getString(5)));
                 Trip ls = new Trip();
                 ls.setId_trip(rs.getInt("id_trip"));

                 ls.setVille_dest(rs.getString("ville_dest"));
                 ls.setDescription(rs.getString("description"));
                 ls.setOffre(rs.getString("offre"));
                 ls.setPeriode(rs.getString("periode"));
               
             

                  
                  System.out.println("");
         obl.add(ls);
                  }
                  return obl;
                  
              } catch (SQLException ex) {
                System.out.println("Erreur"+ex);
              }
           return null;
    } 
    public void affichelistetrip() {
        
           
                         
      //ajouter les valeurs au tableview
      col_id.setCellValueFactory(new PropertyValueFactory<>("id_trip"));
      col_ville.setCellValueFactory(new PropertyValueFactory<>("ville_dest"));
      col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
      col_offre.setCellValueFactory(new PropertyValueFactory<>("offre"));
      col_periode.setCellValueFactory(new PropertyValueFactory<>("periode"));
      ObservableList<Trip> obl =FXCollections.observableArrayList();
     // tableview.setItems(null);
     obl=show1(); 
      tabtrip.setItems(obl);
      System.out.println(""+obl);

                      
    }
    @FXML
    private void ajouter(ActionEvent event) {
              
        TripServices sp = new TripServices();
        Trip e = new Trip();
        
        e.setVille_dest(txtVille.getText());
                e.setDescription(cbhotel.getValue());

        e.setPeriode(txtperiode.getText());
        e.setOffre(txtOffre.getText());
   if ( controlSaisieDescription()){
        if ( controlSaisieOffre()){
            if ( controlSaisiePeriode())
            if ( verifUserChamps()){
         Notifications notificationBuilder = Notifications.create()
                    .title("Trip ajouté")
                    .text("Vous pouvez ajouté d'autre Trip")
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.TOP_RIGHT);
                notificationBuilder.show();

        sp.ajouterTrip(e);
        affichelistetrip();  
           }}   }}
 
        
      public boolean verifUserChamps() {
        int verif = 0;
        String style = " -fx-border-color: red;";

        String styledefault = "-fx-border-color: green;";

   
       
        txtVille.setStyle(styledefault);
        txtperiode.setStyle(styledefault);
        txtOffre.setStyle(styledefault);
        cbhotel.setStyle(styledefault);
     
       
 

        if (txtVille.getText().equals("")) {
            txtVille.setStyle(style);
            verif = 1;
        }
       
        if ( txtperiode.getText().equals("")) {
             txtperiode.setStyle(style);
            verif = 1;
        }
         
        if (txtOffre.getText().equals("")) {
            txtOffre.setStyle(style);
            verif = 1;
        }
       
        if (cbhotel.getValue().isEmpty()){
            cbhotel.setStyle(style);
            verif = 1;
        }
       
        if (verif == 0) {
            return true;
        }
        Alert al = new Alert(Alert.AlertType.ERROR);
        al.setTitle("Alert");
        al.setContentText("Verifier les champs");
        al.setHeaderText(null);
        al.show() ; 
        
        return false;
    }
      public boolean controlSaisieVille(){
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Erreur");
         alert.setHeaderText("Erreur de saisie");
  
        if(checkIfStringContainsNumber(txtVille.getText())){
            alert.setContentText("Les attibuts ne doivent pas contenir des chiffres");
            alert.showAndWait();
            return false;
        }
        return true;
    }
      public boolean controlSaisieDescription(){
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Erreur");
         alert.setHeaderText("Erreur de saisie");
  
        if(checkIfStringContainsNumber(txtDescription.getText())){
            alert.setContentText("Les attibuts ne doivent pas contenir des chiffres");
            alert.showAndWait();
            return false;
        }
        return true;
    }
       public boolean controlSaisiePeriode(){
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Erreur");
         alert.setHeaderText("Erreur de saisie");
  
        if(checkIfStringContainsNumber(txtperiode.getText())){
            alert.setContentText("Les attibuts ne doivent pas contenir des chiffres");
            alert.showAndWait();
            return false;
        }
        return true;
    }
          public boolean controlSaisieOffre(){
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Erreur");
         alert.setHeaderText("Erreur de saisie");
  
        if(checkIfStringContainsNumber(txtOffre.getText())){
            alert.setContentText("Les attibuts ne doivent pas contenir des chiffres");
            alert.showAndWait();
            return false;
        }
        return true;
    }
     public boolean checkIfStringContainsNumber(String str){
        for (int i=0; i<str.length();i++){
            if(str.contains("0") || str.contains("1") || str.contains("2") || str.contains("3") || str.contains("4") || str.contains("5") || str.contains("6") || str.contains("7") || str.contains("8") || str.contains("9")){
                return true;
            }
        }
        return false;
    }
       
        
        private void clearFields() {
        txtid.clear();
        txtVille.clear();
        txtDescription.clear(); 
        txtperiode.clear();
        
        txtOffre.clear(); 
        
        
        
    }

    @FXML
    private void supprimer(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Validation");
        alert.setHeaderText("Voulez vous valider la suppression de ce trip ?");
        //alert.setContentText("");

        Optional<ButtonType> option = alert.showAndWait();
        //confirmation
        if (option.get() == ButtonType.OK) {
        String tit = txtOffre.getText().toString();
        TripServices sp = new TripServices();
        Trip e = new Trip();
        e.setOffre(tit);
        sp.supprimerTrip(e);
        clearFields();
            Notifications notificationBuilder = Notifications.create()
                    .title("Trip Supprimé")
                    .text("Vous pouvez supprimé d'autre Trip")
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.TOP_RIGHT);

            notificationBuilder.show();
                    affichelistetrip();  

        }
    }

    @FXML
    private void modify(ActionEvent event) {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Validation");
        alert.setHeaderText("Voulez vous valider la modification de ce trip ?");

        Optional<ButtonType> option = alert.showAndWait();
        //confirmation
        if (option.get() == ButtonType.OK) { 
        String id_trip=txtid.getText();
        String ville_dest=txtVille.getText();
        String description=txtDescription.getText();
        String periode=txtperiode.getText();

        String offre=txtOffre.getText();
        TripServices sp = new TripServices();
        Trip e = new Trip();
        e.setId_trip(Integer.parseInt(id_trip));
        e.setVille_dest(ville_dest);
        e.setDescription(description);
        e.setPeriode(periode);

        e.setOffre(offre);
        e.setId_trip(Integer.parseInt(id_trip));
        sp.modifierTrip(e);
        clearFields();
          Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Confirmation de la modification!");
                    alert2.setHeaderText(null);
                    
                        Notifications notificationBuilder = Notifications.create()
                    .title("Trip Modifié")
                    .text("Vous pouvez modifié d'autre Trip")
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.TOP_RIGHT);

            notificationBuilder.show();
                    affichelistetrip();  

        
                  }else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Erreur!");
            alert2.setHeaderText(null);
            alert2.setContentText("Le trip n'a pas été modifié");
            alert2.show();
        }


    }

    @FXML
    private void Afficher(MouseEvent event) {
         int index=-1; 
        index=tabtrip.getSelectionModel().getSelectedIndex();
        if (index<= -1)
        {return; } 
                txtid.setText(col_id.getCellData(index).toString());

        txtVille.setText(col_ville.getCellData(index).toString());
        cbhotel.setValue(col_description.getCellData(index).toString());
        txtperiode.setText(col_periode.getCellData(index).toString());
        txtOffre.setText(col_offre.getCellData(index).toString());
    }

    @FXML
    private void GoHotel(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/pidevapp4/GestionHotel.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void recaptcha(MouseEvent event) {

    }

    @FXML
    private void WebView(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/pidevapp4/Web.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }
   
    private void recherche() {
        recherche_text.textProperty().addListener((observable, oldValue, newValue) -> {
    TripServices ser= new TripServices();
String rech = oldValue+newValue ;         
        List<Trip> li =ser.ListClasse1(rech);
        ObservableList<Trip> data = FXCollections.observableArrayList(li);
                  

        
       col_id.setCellValueFactory(
                new PropertyValueFactory<>("id_trip"));
 
         col_ville.setCellValueFactory(  
                new PropertyValueFactory<>("ville")) ; 
        col_description.setCellValueFactory(
                new PropertyValueFactory<>("description"));
     
        
        col_offre.setCellValueFactory(
                new PropertyValueFactory<>("offre"));
        
        col_periode.setCellValueFactory(
                new PropertyValueFactory<>("periode"));
       
        tabtrip.setItems(data);
});
    }
    @FXML
    private void Calcul(ActionEvent event) throws IOException{
            TripServices ser= new TripServices();
        
        List<Trip> li =ser.ListClasse(); 
        int i = 0; 
        
        for ( i=0 ; i<li.size();i++){
        if (li.get(i).getOffre().equals("VIP"))
        
        {nh=nh+1;}  ;
        if (li.get(i).getOffre().equals("Premium")){nf=nf+1 ; } 
        if (li.get(i).getOffre().equals("Basic")){ne=ne+1 ; }  }
        
 FXMLLoader loader = new FXMLLoader();
        
        loader.setLocation(getClass().getResource("NombreOffre.fxml"));
         Parent root = loader.load();
        lc_text.getScene().setRoot(root);
                
    }
}
