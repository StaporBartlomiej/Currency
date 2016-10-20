package application;
	
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Currency Converter");
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(0,10,0,10));

			
			
			ObservableList<String> items = FXCollections.observableArrayList("PLN", "Euro");
			final ComboBox fromCurrency = new ComboBox(items);
			final ComboBox toCurrency = new ComboBox(items);

			
			Label Amount = new Label("Amount");
			grid.add(Amount, 0, 0);
			
			TextField CurrencyBefore = new TextField();
			CurrencyBefore.setPromptText("Enter Value");
			grid.add(CurrencyBefore, 0, 1);
			
			
			Label from = new Label("From Currency");
			grid.add(from, 1, 0);
			
			grid.add(fromCurrency, 1, 1);
			
			Label to = new Label("To Currency");
			grid.add(to, 2, 0);
			
			grid.add(toCurrency, 2, 1);
			
			Label ConvertedAmount = new Label("Converted Amount");
			grid.add(ConvertedAmount, 3, 0);
			
			Label ConvertedAmount2 = new Label();
			
			Button Convert = new Button("Convert");
			grid.add(Convert, 4, 1);

			
			
			
			Scene scene = new Scene(grid,525,375);
			

			
			
			
			
			
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
