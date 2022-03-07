/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevapp4;

import Services.HotelServices;
import entite.Hotel;
import entite.Trip;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import utils.MyConnexion;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class GestionHotelController implements Initializable {
             private Connection myConnex = MyConnexion.getInstanceConnex().getConnection();

    @FXML
    private TextField tfid;
    @FXML
    private TextField tfdescrip;
    @FXML
    private TextField tfprix;
    @FXML
    private TextField tfnom;
    private TextField tfimg;
    @FXML
    private TableView<Hotel> tabhotel;
    @FXML
    private TableColumn<Hotel, Integer> tabid;
    @FXML
    private TableColumn<Hotel, String> tabdescrip;
    @FXML
    private TableColumn<Hotel, String> tabprix;
    @FXML
    private TableColumn<Hotel, String> tabnom;
    @FXML
    private TableColumn<Hotel, String> tabimg;
    @FXML
    private ImageView imghotel;
 private String path;
    File selectedFile;
    @FXML
    private TextField tfimage;
    @FXML
    private Button upload;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
          affichelisteHotel();  
               imghotel.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                } else {
                    event.consume();
                }
            }
        });

        // Dropping over surface
        imghotel.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    path = null;
                    for (File file : db.getFiles()) {
                        path = file.getName();
                        selectedFile = new File(file.getAbsolutePath());
                        System.out.println("Drag and drop file done and path=" + file.getAbsolutePath());//file.getAbsolutePath()="C:\Users\X\Desktop\ScreenShot.6.png"
                        imghotel.setImage(new Image("file:" + file.getAbsolutePath()));
//                        screenshotView.setFitHeight(150);
//                        screenshotView.setFitWidth(250);
                        upload.setText(path);
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });

        imghotel.setImage(new Image("file:C:\\Users\\DELL\\Desktop\\InterTrip\\animation2.gif"));
          
    }    
    public ObservableList<Hotel> show1()
    { 
       

           try {
               ObservableList<Hotel> obl =FXCollections.observableArrayList();
                  //exécution de la réquette et enregistrer le resultat dans le resultset
                 PreparedStatement pt= myConnex.prepareStatement("select id_hotel, descrip,prix,nom,img  from hotel ");
                  ResultSet rs = pt.executeQuery();
                  while(rs.next()){
                  //obl.add(new Note(rs.getFloat(1),rs.getFloat(2),rs.getFloat(3),rs.getInt(4),rs.getString(5)));
                 Hotel ls = new Hotel();
                 ls.setId_hotel(rs.getInt("id_hotel"));

                 ls.setDescrip(rs.getString("descrip"));
                 ls.setPrix(rs.getString("prix"));
                 ls.setNom(rs.getString("nom"));
                 ls.setImg(rs.getString("img"));
               
             

                  
                  System.out.println("");
         obl.add(ls);
                  }
                  return obl;
                  
              } catch (SQLException ex) {
                System.out.println("Erreur"+ex);
              }
           return null;
    } 
    public void affichelisteHotel() {
        
           
                         
      //ajouter les valeurs au tableview
      tabid.setCellValueFactory(new PropertyValueFactory<>("id_hotel"));
      tabdescrip.setCellValueFactory(new PropertyValueFactory<>("descrip"));
      tabprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
      tabnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
      tabimg.setCellValueFactory(new PropertyValueFactory<>("img"));
      ObservableList<Hotel> obl =FXCollections.observableArrayList();
     // tableview.setItems(null);
     obl=show1(); 
      tabhotel.setItems(obl);
      System.out.println(""+obl);

                      
    }

    @FXML
    private void AjouterHotel(ActionEvent event) {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Validation");
        alert.setHeaderText("Voulez vous valider l'ajout de cet hôtel ?");
        //alert.setContentText("");

        Optional<ButtonType> option = alert.showAndWait();
        //confirmation
        if (option.get() == ButtonType.OK) {         
        HotelServices sp = new HotelServices();
        Hotel e = new Hotel();
        
        e.setDescrip(tfdescrip.getText());
                e.setPrix(tfprix.getText());

        e.setNom(tfnom.getText());
                e.setImg(path);
  //e.setImg(tfimage.getText());
if (controlSaisieDescription()){
    if(controlSaisieNom()){
        if(controlSaisiePrix()){
        if (verifUserChamps()) {
 
        sp.ajouterHotel(e);
        
        clearFields();
                   Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Confirmation de l'ajout!");
                    alert2.setHeaderText(null);
                    alert2.setContentText("L'hôtel a été bien ajouté");
                    alert2.show();
                    affichelisteHotel();       }else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Erreur!");
            alert2.setHeaderText(null);
            alert2.setContentText("L'hôtel n'a pas été ajouté");
            alert2.show();}

      }}}}}
      public boolean verifUserChamps() {
        int verif = 0;
        String style = " -fx-border-color: red;";

        String styledefault = "-fx-border-color: green;";

   
       
        tfdescrip.setStyle(styledefault);
        tfprix.setStyle(styledefault);
        tfnom.setStyle(styledefault);
            
       
 

        if (tfdescrip.getText().equals("")) {
            tfdescrip.setStyle(style);
            verif = 1;
        }
       
        if ( tfprix.getText().equals("")) {
             tfprix.setStyle(style);
            verif = 1;
        }
         
        if (tfnom.getText().equals("")) {
            tfnom.setStyle(style);
            verif = 1;
        }
       
         else {
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
       public boolean controlSaisieDescription(){
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Erreur");
         alert.setHeaderText("Erreur de saisie");
  
        if(checkIfStringContainsNumber(tfdescrip.getText())){
            alert.setContentText("Les attibuts ne doivent pas contenir des chiffres");
            alert.showAndWait();
            return false;
        }
        return true;
    }
         public boolean controlSaisieNom(){
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Erreur");
         alert.setHeaderText("Erreur de saisie");
  
        if(checkIfStringContainsNumber(tfnom.getText())){
            alert.setContentText("Les attibuts ne doivent pas contenir des chiffres");
            alert.showAndWait();
            return false;
        }
        return true;
    }
          public boolean controlSaisiePrix(){
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Erreur");
         alert.setHeaderText("Erreur de saisie");
  
        if(checkIfStringContainsNumber2(tfprix.getText())){
            alert.setContentText("Le prix ne doit pas contenir des alphabets");
            alert.showAndWait();
            return false;
        }
        return true;
    }
          public boolean checkIfStringContainsNumber2(String str){
        for (int i=0; i<str.length();i++){
            if(str.contains("a") || str.contains("b") || str.contains("c") || str.contains("d") || str.contains("e") || str.contains("f") || str.contains("g") || str.contains("h") || str.contains("i") || str.contains("j")|| str.contains("k")|| str.contains("l")|| str.contains("m")|| str.contains("n")|| str.contains("o")|| str.contains("p")|| str.contains("q")|| str.contains("r")|| str.contains("s")|| str.contains("t")|| str.contains("u")|| str.contains("v")|| str.contains("w")|| str.contains("y")|| str.contains("z")){
                return true;
            }
        }
        return false;
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
        tfid.clear();
        tfprix.clear();
        tfnom.clear(); 
        tfimage.clear();
//imghotel.clear();
        tfdescrip.clear(); 
            imghotel.setImage(null);
 upload.setText("Upload");        
                imghotel.setImage(new Image("file:C:\\Users\\DELL\\Desktop\\InterTrip\\animation2.gif"));
        
        
        
    }

    @FXML
    private void ModifierHotel(ActionEvent event) {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Validation");
        alert.setHeaderText("Voulez vous valider la modification de cet hôtel ?");
        //alert.setContentText("");

        Optional<ButtonType> option = alert.showAndWait();
        //confirmation
        if (option.get() == ButtonType.OK) {    
        String id_hotel=tfid.getText();
        String prix=tfprix.getText();
        String nom=tfnom.getText();
        String img=path;

        String descrip=tfdescrip.getText();
        HotelServices sp = new HotelServices();
        Hotel e = new Hotel();
        e.setId_hotel(Integer.parseInt(id_hotel));
        e.setPrix(prix);
        e.setNom(nom);
        e.setImg(img);

        e.setDescrip(descrip);
        e.setId_hotel(Integer.parseInt(id_hotel));
        sp.modifierHotel(e);
        clearFields();
                     Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Confirmation de le modification!");
                    alert2.setHeaderText(null);
                    alert2.setContentText("L'hôtel a été bien modifié");
                    alert2.show();
                    affichelisteHotel();       }else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Erreur!");
            alert2.setHeaderText(null);
            alert2.setContentText("L'hôtel n'a pas été modifié");
            alert2.show();}
    }

    @FXML
    private void SupprimerHotel(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Validation");
        alert.setHeaderText("Voulez vous valider la suppression de cet hôtel ?");
        //alert.setContentText("");

        Optional<ButtonType> option = alert.showAndWait();
        //confirmation
        if (option.get() == ButtonType.OK) {
        String tit = tfnom.getText().toString();
        HotelServices sp = new HotelServices();
        Hotel e = new Hotel();
        e.setNom(tit);
        sp.supprimerHotel(e);
        clearFields();
                     
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Confirmation de le suppression!");
                    alert2.setHeaderText(null);
                    alert2.setContentText("L'hôtel a été bien supprimé");
                    alert2.show();
                    affichelisteHotel();       }else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Erreur!");
            alert2.setHeaderText(null);
            alert2.setContentText("L'hôtel n'a pas été supprimé");
            alert2.show();
    }}

    @FXML
    private void AfficherHotel(MouseEvent event) {
         int index=-1; 
        index=tabhotel.getSelectionModel().getSelectedIndex();
        if (index<= -1)
        {return; } 
                tfid.setText(tabid.getCellData(index).toString());

        tfnom.setText(tabnom.getCellData(index).toString());
        tfdescrip.setText(tabdescrip.getCellData(index).toString());
        tfprix.setText(tabprix.getCellData(index).toString());
        tfimg.setText(tabimg.getCellData(index).toString());
            imghotel.setImage(new Image(tabimg.getCellData(index)));
        imghotel.setFitHeight(175);
        imghotel.setFitWidth(320);
      
    }

    @FXML
    private void UploadImage(ActionEvent event) 
//        FileChooser fc = new FileChooser();
//
//        String imageFile = "";
//        fc.setInitialDirectory(new File(System.getProperty ("user.home")));
//fc.getExtensionFilters().clear(); 
//fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
//
//File file = fc.showOpenDialog(null); 
//        if (file != null) {
//            imageFile = file.getAbsolutePath();
//            tfimg.setText(imageFile);
//            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
//                    alert2.setTitle("Confirmation du téléchargement de l'image!");
//                    alert2.setHeaderText(null);
//                    alert2.setContentText("Votre image a bien été téléchargée");
//                    alert2.show();  
//    }
throws MalformedURLException {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(System.getProperty("user.home") + "\\Desktop"));
        fc.setTitle("Veuillez choisir l'image");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image", "*.jpg", "*.png"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg")
        );
        selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {

            path = selectedFile.getName();
//                path = selectedFile.toURI().toURL().toExternalForm();
            imghotel.setImage(new Image(selectedFile.toURI().toURL().toString()));
            imghotel.setFitHeight(150);
            imghotel.setFitWidth(250);
            upload.setText(path);

        }
}
   

    @FXML
    private void handle(DragEvent event) {
    }
}
