package com.example.projet_bibliotheque;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class RegistelivreController implements Initializable {

    @FXML
    private TableView<ouvrage> Tableview;

    @FXML
    private TableColumn<ouvrage, String> ColonneTitre;

    @FXML
    private TableColumn<ouvrage, String> ColonneAuteur;

    @FXML
    private TableColumn<ouvrage, String> ColonnePresentation;

    @FXML
    private TableColumn<ouvrage, String> ColonneParution;

    @FXML
    private TableColumn<ouvrage, Integer> ColonneColonne;

    @FXML
    private TableColumn<ouvrage, Integer> ColonneRangé;

    @FXML
    private TableColumn<ouvrage, String> ColonneResume;

    @FXML
    private TableColumn<ouvrage, String> ColonneURL;

    @FXML
    private TableColumn<ouvrage, String> ColonneEtat;

    @FXML
    private TextField auteurinput;

    @FXML
    private TextField presentationinput;

    @FXML
    private TextField parutioninput;

    @FXML
    private TextField colonneinput;

    @FXML
    private TextField rangéeinput;

    @FXML
    private TextField titreinput;

    @FXML
    private TextField urlinput;

    @FXML
    private TextField appercuinput11;

    @FXML
    private TextField résuméinput;

    @FXML
    private  TextField etatinput;

    @FXML
    private Button fonctionvalide;

    @FXML
    private Button fonctionssupprime;

    @FXML
    private Button fonctionmodifier;

    @FXML
    private Label messagederreurformulaire;

    PreparedStatement pst = null;
    FileChooser fileChooser = new FileChooser();
    @FXML
    void ImportFile(ActionEvent event){
        if(Tableview != null){
            Tableview.getItems().clear();
        }
        try {
            JAXBContext jc = JAXBContext.newInstance("com.example.projet_bibliotheque");
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            fileChooser.getExtensionFilters().clear();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Document (*.xml)", "*.xml"));
            File myfile = fileChooser.showOpenDialog(null);
            if(myfile != null){
                Bibliotheque bibliotheque2 = (Bibliotheque) unmarshaller.unmarshal(myfile);
                List livres = (List) bibliotheque2.getLivre();
                for (int i = 0; i < livres.size(); i++) {
                    Bibliotheque.Livre livre = (Bibliotheque.Livre) livres.get(i);
                    ouvrage myLivre =new ouvrage(livre.getTitre(), livre.getAuteur().getPrenom() + " "+ livre.getAuteur().getNom(), livre.getPresentation() , String.valueOf(livre.getParution()), String.valueOf(livre.getColonne()), String.valueOf(livre.getRangee()));
                    Tableview.getItems().add(myLivre);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void SaveFile(ActionEvent event){
        try{
            JAXBContext jc = JAXBContext.newInstance("com.example.projet_bibliotheque");
            fileChooser.getExtensionFilters().clear();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Document (*.xml)", "*.xml"));
            File myfile = fileChooser.showSaveDialog(null);
            if(myfile != null){
                Marshaller marshaller = jc.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                ObjectFactory factory = new ObjectFactory();
                Bibliotheque bibliotheque = factory.createBibliotheque();
                for (int i = 0; i < Tableview.getItems().size(); i++) {
                    Bibliotheque.Livre livre = factory.createBibliothequeLivre();
                    livre.setPresentation(Tableview.getItems().get(i).getPrésentationc());
                    livre.setTitre(Tableview.getItems().get(i).getTitrec());
                    livre.setParution(Integer.parseInt(Tableview.getItems().get(i).getParutionc()));
                    livre.setColonne(Short.parseShort(Tableview.getItems().get(i).getColonnec()));
                    livre.setRangee(Short.parseShort(Tableview.getItems().get(i).getRangéec()));

                    String[] s =Tableview.getItems().get(i).getAuteurc().split(" ");

                    Bibliotheque.Livre.Auteur auteur = factory
                            .createBibliothequeLivreAuteur();
                    auteur.setNom(s[1]);
                    auteur.setPrenom(s[0]);
                    livre.setAuteur(auteur);

                    bibliotheque.getLivre().add(livre);
                }
                marshaller.marshal(bibliotheque, myfile);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    // Fonction d'export
    @FXML
    void SaveToPDF(ActionEvent event) throws FileNotFoundException, DocumentException {
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier PDF (*.PDF)", "*.pdf"));
        File myfile = fileChooser.showSaveDialog(null);
        if(myfile != null){
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(myfile.getPath()));

            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Chunk chunk = new Chunk("Bibliotech !!!!", font);
            document.add(chunk);

            for (int i = 0; i < Tableview.getItems().size(); i++) {
                String chaine = "Titre: " + Tableview.getItems().get(i).getTitrec() +"\n" +
                        "Présantation: " + Tableview.getItems().get(i).getPrésentationc() +"\n" +
                        "Auteur: " + Tableview.getItems().get(i).getTitrec() +"\n" +
                        "Parution: " + Tableview.getItems().get(i).getAuteurc() +"\n" +
                        "Colonne: " + Tableview.getItems().get(i).getColonnec() +"\n" +
                        "Rangée: " + Tableview.getItems().get(i).getRangéec() +"\n\n";
                Paragraph s = new Paragraph(chaine);
                document.add(s);
            }

            document.close();
        }

    }

    @FXML
    void mettreàjourlesdonnées(ActionEvent event) {
        String insertToRegister = "SELECT * FROM Livre_aicha;";
        try{
            Connection ConnectDB = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/aichaexemple?serverTimezone=UTC", "root", "root1234");
            Statement statement = ConnectDB.createStatement();
            ResultSet res = statement.executeQuery(insertToRegister);
            while(res.next()){
                //Récupérer par nom de colonne
                int id = res.getInt("idlivre");
                String nom = res.getString("Titre");
                String age = res.getString("auteur");
                String adresse = res.getString("parution");
                ouvrageArrayList.add(
                        new ouvrage(
                                res.getString("Titre"),
                                res.getString("auteur"),
                                res.getString("presentation"),
                                res.getString("parution"),
                                res.getString("colones"),
                                res.getString("rangees"),
                                res.getString("resumee"),
                                res.getString("etat"),
                                res.getString("url")
                                )
                );
                Tableview.setItems(observableList());

                /*Afficher les valeurs
                System.out.print("ID: " + id);
                System.out.print(", Nom: " + nom);
                System.out.print(", Age: " + age);
                System.out.println(", Adresse: " + adresse);*/
            }

            //étape 6: fermez l'objet de connexion
            ConnectDB.close();
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML
    void modifierlesdonnées(ActionEvent event) {

        if(Tableview.getSelectionModel().getSelectedIndex() == 0){
            edit = true;
            var o =Tableview.getItems().get(Seletionerlaligne());
            titreinput.setText(o.getTitrec());
            auteurinput.setText(o.getAuteurc());
            presentationinput.setText(o.getPrésentationc());
            parutioninput.setText(o.getParutionc());
            colonneinput.setText(String.valueOf(o.getColonnec()));
            rangéeinput.setText(String.valueOf(o.getRangéec()));
            etatinput.setText(o.getEtatc());
            urlinput.setText(o.getUrlc());
            résuméinput.setText(o.getResumec());

        }else { messagederreurformulaire.setText(" Veillez sellectionner la ligne à modifier");}


    }


    @FXML
    void supprimerlaselection(ActionEvent event) {
        if(Tableview.getSelectionModel().getSelectedIndex() == 0){

            ouvrage selectionerlalivre = Tableview.getSelectionModel().getSelectedItem();
            ouvrageArrayList.remove(Seletionerlaligne());
            Tableview.setItems(observableList());
            String insertFields ="DELETE FROM livre_aicha WHERE ";
            String insertValues = "Colones = " + selectionerlalivre.getColonnec() +" AND Rangees = "+ selectionerlalivre.getRangéec();
            String insertToRegister = insertFields + insertValues;
            try{
                Connection ConnectDB = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/aichaexemple?serverTimezone=UTC", "root", "root1234");
                Statement statement = ConnectDB.createStatement();
                statement.executeLargeUpdate(insertToRegister);
            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }
            reinitialiser();
            messagederreurformulaire.setText("");
        }else { messagederreurformulaire.setText(" Veillez sellectionner la ligne à supprimer");}

    }

    @FXML
    void valerderlesinfos(ActionEvent event) {

        if (titreinput.getText().isBlank() == false && auteurinput.getText().isBlank() == false && presentationinput.getText().isBlank() == false  && colonneinput.getText().isBlank() == false && rangéeinput.getText().isBlank() == false && résuméinput.getText().isBlank() == false  &&  etatinput.getText().isBlank() == false) {

            if (!auteurinput.getText().equals("")) {

                if (!edit) {
                    registerLivre();
                    ouvrageArrayList.add(new ouvrage(titreinput.getText(), auteurinput.getText(), presentationinput.getText(), parutioninput.getText(), colonneinput.getText(), rangéeinput.getText(), urlinput.getText(), résuméinput.getText(), urlinput.getText()));
                    Tableview.setItems(observableList());
                } else {
                    var i = Seletionerlaligne();
                    var o = Tableview.getItems().get(i);
                    var col = o.getColonnec();
                    var rag = o.getRangéec();
                    o.setTitrec(titreinput.getText());
                    o.setAuteurc(auteurinput.getText());
                    o.setPrésentationc(presentationinput.getText());
                    o.setParutionc(parutioninput.getText());
                    o.setColonnec(colonneinput.getText());
                    o.setRangéec(rangéeinput.getText());
                    o.setResumec(résuméinput.getText());
                    o.setEtatc(etatinput.getText());
                    o.setUrlc(urlinput.getText());
                    Tableview.getItems().set(i, o);
                    updateLivre(o.getTitrec(),o.getAuteurc(),o.getPrésentationc(),o.getParutionc(),o.getColonnec(),o.getRangéec(),o.getResumec(),o.getUrlc(),o.getEtatc(),col,rag);
                }
                reinitialiser();
                messagederreurformulaire.setText("");
            } else {
                messagederreurformulaire.setText("Veillez entrer les informations");
            }
        }


    }
    public void reinitialiser(){
        titreinput.setText("");
        auteurinput.setText("");
        presentationinput.setText("");
        parutioninput.setText("");
        colonneinput.setText("");
        rangéeinput.setText("");
        urlinput.setText("");
        résuméinput.setText("");
        appercuinput11.setText("");
        etatinput.setText("");
        edit = false;
    }

    ArrayList<ouvrage> ouvrageArrayList = new ArrayList<>();
    ObservableList<ouvrage> livre;
    boolean edit = false;

    public ObservableList<ouvrage> observableList(){
        livre= FXCollections.observableArrayList();
        livre.addAll(ouvrageArrayList);
        return livre;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ColonneTitre.setCellValueFactory(new PropertyValueFactory<ouvrage, String>("titrec"));
        ColonneAuteur.setCellValueFactory(new PropertyValueFactory<ouvrage, String>("auteurc"));
        ColonnePresentation.setCellValueFactory(new PropertyValueFactory<ouvrage, String>("présentationc"));
        ColonneParution.setCellValueFactory(new PropertyValueFactory<ouvrage, String>("parutionc"));
        ColonneColonne.setCellValueFactory(new PropertyValueFactory<ouvrage, Integer>("colonnec"));
        ColonneRangé.setCellValueFactory(new PropertyValueFactory<ouvrage, Integer>("rangéec"));
        ColonneEtat.setCellValueFactory(new PropertyValueFactory<ouvrage, String>("etatc"));
        ColonneURL.setCellValueFactory(new PropertyValueFactory<ouvrage, String>("urlc"));
        ColonneResume.setCellValueFactory(new PropertyValueFactory<ouvrage, String>("resumec"));

        Tableview.setItems(observableList());
    }

    public int Seletionerlaligne(){
        Tableview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        return  Tableview.getSelectionModel().getSelectedIndex();
    }

    public  void registerLivre(){

        //databaseconnection connectNow = new databaseconnection();
        //Connection connectDB = connectNow.getConnection();


        String Titre =titreinput.getText();
        String Auteur =auteurinput.getText();
        String Presentation =presentationinput.getText();
        String Parution=parutioninput.getText();
        Integer Colones =Integer.parseInt(colonneinput.getText());
        Integer Rangees =Integer.parseInt(rangéeinput.getText());
        String Resumee=résuméinput.getText();
        String ETAT = etatinput.getText();
        String URL = urlinput.getText();

        String insertValues = Titre +"','"+ Auteur +"','"+ Presentation + "','" + Parution + "'," + Colones+ "," + Rangees+",'"+Resumee +"','" +ETAT + "','" +URL +"')";
        String insertFields ="insert into livre_aicha(Titre, Auteur, Presentation, Parution, Colones, Rangees, Resumee, Etat, URL) values('";
        String insertToRegister = insertFields + insertValues;

        try{

            Connection ConnectDB = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/aichaexemple?serverTimezone=UTC", "root", "root1234");
            Statement statement = ConnectDB.createStatement();
            statement.executeLargeUpdate(insertToRegister);

            messagederreurformulaire.setText("les données ont été bien enregistrer dans la base de donnée!");

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public  void updateLivre(String titre, String auteur, String présentation, String parution, String colonne, String rangée, String resume, String url, String etat,String col,String reg){

        String Titre =titre;
        String Auteur =auteur;
        String Presentation =présentation;
        String Parution=parution;
        Integer Colones =Integer.parseInt(colonne);
        Integer Rangees =Integer.parseInt(rangée);
        String Resumee=resume;
        String ETAT = etat;
        String URL = url;


        String insertFields ="update livre_aicha set Titre= '"+Titre+"' , Auteur='"+Auteur+"', Presentation='"+Presentation+"', Parution='"+Parution+"', Colones="+Colones+", Rangees="+Rangees+", Resumee="+Resumee+", Etat='"+ETAT+"', URL='"+URL+"'" ;
        String insertValues = "where Colones = " +col +" AND Rangees = "+ reg;
        String insertToRegister = insertFields + insertValues;

        try{

            Connection ConnectDB = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/aichaexemple?serverTimezone=UTC", "root", "root1234");
            Statement statement = ConnectDB.createStatement();
            statement.executeLargeUpdate(insertToRegister);

            messagederreurformulaire.setText("les données ont été bien enregistrer dans la base de donnée!");

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

}
