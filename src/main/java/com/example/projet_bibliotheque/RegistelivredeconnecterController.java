package com.example.projet_bibliotheque;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class RegistelivredeconnecterController implements Initializable {

    @FXML
    private TableView<ouvragedeconnecter> Tableview;

    @FXML
    private TableColumn<ouvragedeconnecter, String> ColonneTitre;

    @FXML
    private TableColumn<ouvragedeconnecter, String> ColonneAuteur;

    @FXML
    private TableColumn<ouvragedeconnecter, String> ColonnePresentation;

    @FXML
    private TableColumn<ouvragedeconnecter, String> ColonneParution;

    @FXML
    private TableColumn<ouvragedeconnecter, String> ColonneColonne;

    @FXML
    private TableColumn<ouvragedeconnecter, String> ColonneRangé;

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
    private Label messagederreurformulaire;

    @FXML
    private Button fonctionvalide;

    @FXML
    private Button fonctionssupprime;

    @FXML
    private Button fonctionmodifier;

    FileChooser fileChooser = new FileChooser();
    @FXML
    void ImportFile(ActionEvent event){
        if(Tableview != null){
            Tableview.getItems().clear();
        }
        try {
            JAXBContext jc = JAXBContext.newInstance("com.example.projet_bibliotheque");
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            File myfile = fileChooser.showOpenDialog(null);
            Bibliotheque bibliotheque2 = (Bibliotheque) unmarshaller.unmarshal(myfile);
            List livres = (List) bibliotheque2.getLivre();
            for (int i = 0; i < livres.size(); i++) {
                Bibliotheque.Livre livre = (Bibliotheque.Livre) livres.get(i);
                ouvragedeconnecter myLivre =new ouvragedeconnecter(livre.getTitre(), livre.getAuteur().getPrenom() + " "+ livre.getAuteur().getNom(), livre.getPresentation() , String.valueOf(livre.getParution()), String.valueOf(livre.getColonne()), String.valueOf(livre.getRangee()));
                Tableview.getItems().add(myLivre);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void SaveFile(ActionEvent event){
        try{
            JAXBContext jc = JAXBContext.newInstance("com.example.projet_bibliotheque");
            File myfile = fileChooser.showSaveDialog(null);
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
        catch (Exception e){
            e.printStackTrace();
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
            colonneinput.setText(o.getColonnec());
            rangéeinput.setText(o.getRangéec());
        }else { messagederreurformulaire.setText(" Veillez sellectionner la ligne à modifier");}

    }

    @FXML
    void supprimerlaselection(ActionEvent event) {
        if(Tableview.getSelectionModel().getSelectedIndex() == 0){
        ouvragedeconnecterArrayList.remove(Seletionerlaligne());
        Tableview.setItems(observableList());
        reinitialiser();
        messagederreurformulaire.setText("");
        }else { messagederreurformulaire.setText(" Veillez sellectionner la ligne à supprimer");}

    }

    @FXML
    void valerderlesinfos(ActionEvent event) {

        if (titreinput.getText().isBlank() == false && auteurinput.getText().isBlank() == false && presentationinput.getText().isBlank() == false  && parutioninput.getText().isBlank() == false  && colonneinput.getText().isBlank() == false && rangéeinput.getText().isBlank() == false) {
                if (!auteurinput.getText().equals("")) {
                    if (!edit) {
                        ouvragedeconnecterArrayList.add(new ouvragedeconnecter(auteurinput.getText(), presentationinput.getText(), parutioninput.getText(), colonneinput.getText(), rangéeinput.getText(), titreinput.getText()));
                        Tableview.setItems(observableList());
                    } else {
                        var i = Seletionerlaligne();
                        var o = Tableview.getItems().get(i);
                        o.setTitrec(titreinput.getText());
                        o.setAuteurc(auteurinput.getText());
                        o.setPrésentationc(presentationinput.getText());
                        o.setParutionc(parutioninput.getText());
                        o.setColonnec(colonneinput.getText());
                        o.setRangéec(rangéeinput.getText());
                        Tableview.getItems().set(i, o);
                    }
                }
                reinitialiser();
                messagederreurformulaire.setText("");
               }
            else {
                messagederreurformulaire.setText(" Veillez entrer toutes les informations.\n Il ne doit pas avoir de champs vides");
            }
    }


    public void reinitialiser(){
        titreinput.setText("");
        auteurinput.setText("");
        presentationinput.setText("");
        parutioninput.setText("");
        colonneinput.setText("");
        rangéeinput.setText("");
        edit = false;
    }

    ArrayList<ouvragedeconnecter> ouvragedeconnecterArrayList = new ArrayList<>();
    ObservableList<ouvragedeconnecter> livre;
    boolean edit = false;

    public ObservableList<ouvragedeconnecter> observableList(){
        livre= FXCollections.observableArrayList();
        livre.addAll(ouvragedeconnecterArrayList);
        return livre;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ColonneTitre.setCellValueFactory(new PropertyValueFactory<ouvragedeconnecter, String>("titrec"));
        ColonneAuteur.setCellValueFactory(new PropertyValueFactory<ouvragedeconnecter, String>("auteurc"));
        ColonnePresentation.setCellValueFactory(new PropertyValueFactory<ouvragedeconnecter, String>("présentationc"));
        ColonneParution.setCellValueFactory(new PropertyValueFactory<ouvragedeconnecter, String>("parutionc"));
        ColonneColonne.setCellValueFactory(new PropertyValueFactory<ouvragedeconnecter, String>("colonnec"));
        ColonneRangé.setCellValueFactory(new PropertyValueFactory<ouvragedeconnecter, String>("rangéec"));

        Tableview.setItems(observableList());
    }

    public int Seletionerlaligne(){
        Tableview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        return  Tableview.getSelectionModel().getSelectedIndex();
         }

}

