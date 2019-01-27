package Gorunum;

import java.awt.Panel;
import java.io.IOException;
import java.lang.*;
import java.util.ArrayList;

import com.sun.org.apache.bcel.internal.generic.NEW;

import Algoritma.DBScan;
import Algoritma.DBScanSonuc;
import Algoritma.OklidMesafesi;
import DosyaDuzenleyici.DosyaOku;
import DosyaDuzenleyici.SonucYazdir;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AnaEkran extends Application {
	private TextField dosyaYoluTf;
	private TextField epsilonTf;
	private TextField minPointsTf;
	private Button calistirBtn;
	private DBScanSonuc sonuc;
	private ArrayList<int[]> veriSeti;
	private String dosyaYolu;
	private String [] veriIsimleri;
	private ScatterChart chart;
	private ComboBox yatayCombo;
	private ComboBox dikeyCombo;
	private AnchorPane pane;

	
    @Override
    public void start(Stage primaryStage) throws Exception {
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AnaEkran.fxml"));

        Scene scene = new Scene(loader.load());


        primaryStage.setScene(scene);

        primaryStage.show();
        
        Button dosyaAcBtn = (Button) scene.lookup("#dosya_ac_btn");
        dosyaYoluTf = (TextField) scene.lookup("#dosya_yolu_tf");
        minPointsTf = (TextField) scene.lookup("#minpoints_tf");
        epsilonTf = (TextField) scene.lookup("#epsilon_tf");
        calistirBtn = (Button) scene.lookup("#calistir_btn");
        yatayCombo = (ComboBox) scene.lookup("#yatay_combo");
        dikeyCombo = (ComboBox) scene.lookup("#dikey_combo");
        pane = (AnchorPane) scene.lookup("#pane");
        
        NumberAxis xAxis = new NumberAxis();

        NumberAxis yAxis = new NumberAxis();

        chart = new ScatterChart(xAxis, yAxis);
        chart.setLayoutY(143);
        chart.setLayoutX(14);
        pane.getChildren().add(chart);
        pane.setRightAnchor(chart, 16d);
        pane.setLeftAnchor(chart, 14d);
        pane.setBottomAnchor(chart, 16d);
        pane.setTopAnchor(chart, 143d);
        calistirBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				veriSeti = new ArrayList<int[]>(DosyaOku.verileriAl(dosyaYolu, ","));
				veriIsimleri = DosyaOku.veriIsimleriniAl(dosyaYolu, ",");
				try {
					double epsilon = Double.parseDouble(epsilonTf.getText());
					int minPoints = Integer.parseInt(minPointsTf.getText());
					sonuc = new DBScan(veriSeti, new OklidMesafesi()).calistir(epsilon, minPoints);
					
					
					
					yatayCombo.getItems().addAll(veriIsimleri);
					yatayCombo.setDisable(false);
					dikeyCombo.getItems().addAll(veriIsimleri);
					dikeyCombo.setDisable(false);
					
					//yatayCombo.getSelectionModel().select(0);
					//dikeyCombo.getSelectionModel().select(0);
					grafikGuncelle();
					SonucYazdir.yazdir(sonuc);
				}catch (Exception e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("epsilon veya minpoints dogru bicimde girilmedi!");
					alert.showAndWait();
				}
				
				
			}
		});
        
        yatayCombo.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue observable, String oldValue, String newValue) {
				grafikGuncelle();	
			}
        });
        
        dikeyCombo.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				grafikGuncelle();
			}
		});
        
        dosyaAcBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser chooser = new FileChooser();
                chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV dosyasi", "*.csv"));
                dosyaYolu= chooser.showOpenDialog(primaryStage).getAbsolutePath();
                dosyaYoluTf.setText(dosyaYolu);
                calistirBtn.setDisable(false);
                epsilonTf.setDisable(false);
                minPointsTf.setDisable(false);
            }
        });      
    }

    private void grafikGuncelle() {
    	if(yatayCombo.getSelectionModel().getSelectedIndex() < 0 || dikeyCombo.getSelectionModel().getSelectedIndex() < 0) return;
    	chart.getXAxis().setLabel(veriIsimleri[yatayCombo.getSelectionModel().getSelectedIndex()]);
    	chart.getYAxis().setLabel(veriIsimleri[dikeyCombo.getSelectionModel().getSelectedIndex()]);
    	chart.getData().clear();
    	XYChart.Series [] series;
    	series = new XYChart.Series[sonuc.kumeSayisi()];
		for(int i = 1; i < sonuc.kumeSayisi();i++) {
			series[i] = new XYChart.Series();
		}
		series[0] = new XYChart.Series();

		for(int i = 0; i < sonuc.kumeSayisi();i++) {
    		chart.getData().add(series[i]);
    	}
    	for(int i = 0; i < veriSeti.size(); i++) {
    		XYChart.Data data = new XYChart.Data(veriSeti.get(i)[yatayCombo.getSelectionModel().getSelectedIndex()], veriSeti.get(i)[dikeyCombo.getSelectionModel().getSelectedIndex()]);
    		series[sonuc.getKumeler(i)].getData().add(data);
    		final int ii= i;
    		data.getNode().setOnMouseClicked(e -> {
    			Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Kayit " + ii + " kumesi " + (sonuc.getKumeler(ii)==0?"sapan deger" : sonuc.getKumeler(ii)));
				alert.showAndWait();
    			System.out.printf(ii+"");
    		});
    	}
		for(int i = 1; i < sonuc.kumeSayisi();i++) {
    		series[i].setName("Kume "+i);
    	}
		series[0].setName("sapan degerler");
    
    }
    
    public static void main(String[] args){
        launch(args);
    }

}
