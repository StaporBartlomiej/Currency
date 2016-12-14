package application;
	
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Main extends Application implements Currency {
	
	
	private TableView<Person> table = new TableView<Person>();
	
	private final ObservableList<Person> data = FXCollections.observableArrayList(
			new Person("Euro","EUR",findRate("EUR","EUR")),
			new Person("US Dollar","USD",findRate("USD","EUR")),
			new Person("British Pound","GBP",findRate("GBP","EUR")),
			new Person("Canadian Dollar","CAD",findRate("CAD","EUR")),
			new Person("Australian Dollar","AUD",findRate("AUD","EUR")),
			new Person("Japanese Yen","JPY",findRate("JPY","EUR"))
			);
	
	private final ObservableList<String> items = FXCollections.observableArrayList(
			"PLN","EUR","USD","CAD","GBP","JPY","AUD","DZD","ARS","ALL","XAL","AWG",
			"BHD","BBD","BZD","BTN","BWP","BND","BIF","BSD","BDT","BYR","BMD","BOB","BRL","BGN",
			"KHR","KYD","XAF","COP","XCP","HRK","CZK","CNY","CVE","XOF","CLP","KMF","CRC","CUP",
			"DJF","XCD","EGP","ERN","ETB","FJD","DKK","DOP","ECS","SVC","EEK","FKP",
			"HKD","INR","GHC","XAU","GNF","HTG","HUF","IRR","ILS","IDR","GMD","GIP","GTQ","GYD","HNL","ISK","IQD",
			"JOD","KES","LAK","LBP","LRD","LTL","JMD","KZT","KWD","LVL","LSL","LYD",
			"MOP","MWK","MVR","MRO","MXN","MNT","MMK","MKD","MYR","MTL","MUR","MDL","MAD",
			"NAD","ANG","NIO","KPW","OMR","NPR","NZD","NGN","NOK",
			"XPF","XPD","PGK","PEN","XPT","QAR","RUB","PKR","PAB","PYG","PHP","RON","RWF",
			"CHF","WST","SAR","SLL","SGD","SIT","SOS","LKR","SDG","SEK","KRW","STD","SCR","XAG","SKK","SBD","ZAR","SHP","SZL","SYP",
			"TRY","TZS","TTD","AED","UAH","THB","TWD","TOP","TND","UGX","UYU",
			"VUV","VND","ZMK","VEF","YER","ZWD"
			); 
	
	public String findRateAndConvert(BigDecimal amount,String fromCurr, String toCurr)
	{
		try
		{
			MathContext mc = new MathContext(5,RoundingMode.HALF_UP);
			URL url = new URL("http://finance.yahoo.com/d/quotes.csv?f=l1&s="+ fromCurr + toCurr + "=X"); // using Yahoo Financial API
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String line = reader.readLine();
			if(line.length() > 0)
			{
				BigDecimal linebd = new BigDecimal(line);
				BigDecimal result = amount.multiply(linebd,mc) ;
				return String.valueOf(result);
			}
			reader.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return "";
	
	}

	public String findRate(String fromCurr, String toCurr)
	{
		try
		{

			URL url = new URL("http://finance.yahoo.com/d/quotes.csv?f=l1&s="+ fromCurr + toCurr + "=X"); // using Yahoo Financial API
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String line = reader.readLine();
			reader.close();
			return line;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return "";
		
	}



	public void forbidNonIntInput(TextField currencyBefore) // forces user to input numbers, not Strings
	{
		try
		{
			currencyBefore.setTextFormatter( new TextFormatter<>(c ->
			{
			    if ( c.getControlNewText().isEmpty() )
			    {
			        return c;
			    }
			    DecimalFormat format = new DecimalFormat( "#.00" ); 
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
			 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			
				
			
		}
	}


	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Currency Converter");
			BorderPane border = new BorderPane();
			GridPane grid = new GridPane();
			border.setTop(grid);
			table.setEditable(false);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(0,10,0,10));
			
			ComboBox<String> fromCurrency = new ComboBox(items);
			ComboBox<String> toCurrency = new ComboBox(items);
			

			Label amount = new Label("Amount");
			grid.add(amount, 0, 0);
			
			TextField currencyBefore = new TextField();
			currencyBefore.setPromptText("Enter Value");
			grid.add(currencyBefore, 0, 1);
			
			
			forbidNonIntInput(currencyBefore);
			
			
			Label from = new Label("From Currency");
			grid.add(from, 1, 0);
			
			grid.add(fromCurrency, 1, 1);
			
			Label to = new Label("To Currency");
			grid.add(to, 3, 0);
			
			grid.add(toCurrency, 3, 1);
			
			Label ConvertedAmount = new Label("Converted Amount");
			grid.add(ConvertedAmount, 4, 0);
			
			Label ConvertedAmount2 = new Label();
			grid.add(ConvertedAmount2, 4, 1);
			
			Button Convert = new Button("Convert");
			grid.add(Convert, 5, 1);
			Convert.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e)
				{
					BigDecimal amount = new BigDecimal( Double.parseDouble((currencyBefore.getText())));
					String fromcurr = fromCurrency.getSelectionModel().getSelectedItem();
					String tocurr = toCurrency.getSelectionModel().getSelectedItem();
					ConvertedAmount2.setText(findRateAndConvert(amount,fromcurr,tocurr));
				//ConvertedAmount2.setText(CurrencyBefore.getText());
				}
			});
			
			
			
			String temp = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "swap.png";		
			File file = new File(temp);
			Image swap = new Image(file.toURI().toString());
			Button swapButton = new Button();
			swapButton.setGraphic(new ImageView(swap));
			grid.add(swapButton, 2, 1);
			
			swapButton.setOnAction(new EventHandler<ActionEvent>()
					{
						@Override public void handle(ActionEvent e)
						{
							String temp1 = fromCurrency.getSelectionModel().getSelectedItem();
							String temp2 = toCurrency.getSelectionModel().getSelectedItem();
							fromCurrency.setValue(temp2);
							toCurrency.setValue(temp1);
						}
					}
					);
			
			
			
			GridPane grid2 = new GridPane();
			grid2.setHgap(10);
			grid2.setVgap(10);
			grid2.setPadding(new Insets(0,10,0,10));
			border.setCenter(grid2);
			
			Label courseLabel = new Label("Course");
			courseLabel.setFont(new Font("Times New Roman",20));
			courseLabel.setTextFill(Color.CHARTREUSE);
			
			TableColumn currencyName = new TableColumn("Currency Name");
			currencyName.setMinWidth(100);
			currencyName.setCellValueFactory(new PropertyValueFactory<Person,String>("cName"));
			
			TableColumn symbol = new TableColumn("Symbol");
			symbol.setMinWidth(100);
			symbol.setCellValueFactory(new PropertyValueFactory<Person,String>("sm"));
			
			TableColumn course = new TableColumn("Units per EUR");
			course.setMinWidth(100);
			course.setCellValueFactory(new PropertyValueFactory<Person,String>("cour"));

			table.setItems(data);
			
			table.getColumns().addAll(currencyName,symbol,course);
			
			grid2.add(table, 0, 0);
			border.setCenter(table);
			Scene scene = new Scene(border,600,375);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	

	
}
