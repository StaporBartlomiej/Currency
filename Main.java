package application;
	
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParsePosition;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application {
	
	
	public String solve(BigDecimal Amount,String FromCurr, String ToCurr)
	{
		BigDecimal ConvertedAmount = new BigDecimal("0.0");
		BigDecimal course = new BigDecimal("0.0");
		MathContext mc = new MathContext(2);
		
		if((FromCurr == "PLN" && ToCurr == "Euro") || (FromCurr == "Euro" && ToCurr == "PLN"))
		{
			course = new BigDecimal("4.31940008");
		}
		if(FromCurr == "PLN" && ToCurr == "Euro")
		{
			ConvertedAmount = Amount.divide(course,2,RoundingMode.HALF_UP);
		}
		else if(FromCurr == "Euro" && ToCurr == "PLN")
		{
			ConvertedAmount = Amount.multiply(course,mc);
		}
		else if((FromCurr =="Euro" && ToCurr == "Euro") || (FromCurr =="PLN" && ToCurr == "PLN"))
		{
			ConvertedAmount = Amount;
		}
	
		
		//String result = String.format("%.2f", value);
		BigDecimal result = ConvertedAmount.setScale(2);
		String value = String.valueOf(result.doubleValue());
		
		
		return value;
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Currency Converter");
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(0,10,0,10));

			
			
			ObservableList<String> items = FXCollections.observableArrayList("PLN", "Euro");
			ComboBox<String> fromCurrency = new ComboBox(items);
			ComboBox<String> toCurrency = new ComboBox(items);

			
			Label Amount = new Label("Amount");
			grid.add(Amount, 0, 0);
			
			TextField CurrencyBefore = new TextField();
			CurrencyBefore.setPromptText("Enter Value");
			grid.add(CurrencyBefore, 0, 1);
			
			DecimalFormat format = new DecimalFormat( "#.00" ); // everything up to next comment forces user to input numbers, not Strings
			CurrencyBefore.setTextFormatter( new TextFormatter<>(c ->
			{
			    if ( c.getControlNewText().isEmpty() )
			    {
			        return c;
			    }

			    ParsePosition parsePosition = new ParsePosition( 0 );
			    Object object = format.parse( c.getControlNewText(), parsePosition );

			    if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() )
			    {
			        return null;
			    }
			    else
			    {
			        return c;
			    }
			}));
			 //next comment
			
			Label from = new Label("From Currency");
			grid.add(from, 1, 0);
			
			grid.add(fromCurrency, 1, 1);
			
			Label to = new Label("To Currency");
			grid.add(to, 2, 0);
			
			grid.add(toCurrency, 2, 1);
			
			Label ConvertedAmount = new Label("Converted Amount");
			grid.add(ConvertedAmount, 3, 0);
			
			Label ConvertedAmount2 = new Label();
			grid.add(ConvertedAmount2, 3, 1);
			
			Button Convert = new Button("Convert");
			grid.add(Convert, 4, 1);
			Convert.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e)
				{
					BigDecimal amount = new BigDecimal( Double.parseDouble((CurrencyBefore.getText())));
					String fromcurr = fromCurrency.getSelectionModel().getSelectedItem();
					String tocurr = toCurrency.getSelectionModel().getSelectedItem();
					ConvertedAmount2.setText(solve(amount,fromcurr,tocurr));
				
				//ConvertedAmount2.setText(CurrencyBefore.getText());
				
				
				
			}
			});

			
			
			
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
