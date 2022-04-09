package com.example.projet_bibliotheque;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


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
            fileChooser.getExtensionFilters().clear();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Document (*.xml)", "*.xml"));
            File myfile = fileChooser.showOpenDialog(null);
            if(myfile != null){
                Bibliotheque bibliotheque2 = (Bibliotheque) unmarshaller.unmarshal(myfile);
                List livres = (List) bibliotheque2.getLivre();
                for (int i = 0; i < livres.size(); i++) {
                    Bibliotheque.Livre livre = (Bibliotheque.Livre) livres.get(i);
                    ouvragedeconnecter myLivre =new ouvragedeconnecter(livre.getTitre(), livre.getAuteur().getPrenom() + " "+ livre.getAuteur().getNom(), livre.getPresentation() , String.valueOf(livre.getParution()), String.valueOf(livre.getColonne()), String.valueOf(livre.getRangee()));
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
                        Tableview.getItems().add((new ouvragedeconnecter
                                (       titreinput.getText(),
                                        auteurinput.getText(),
                                        presentationinput.getText(),
                                        parutioninput.getText(),
                                        colonneinput.getText(),
                                        rangéeinput.getText())));
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

