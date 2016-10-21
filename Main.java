package application;
	
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParsePosition;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Main extends Application {
	
	
	private TableView<Person> table = new TableView<Person>();
	
	private final ObservableList<Person> data = FXCollections.observableArrayList(
			new Person("Euro","1 EUR","4.32"),
			new Person("US Dollar","1 USD","3.98"),
			new Person("British Pound","1 GBP","4.86")
			);
	public static void main(String[] args) {
		launch(args);
	}
	
	 private HBox addHBox() {
		 
	        HBox hbox = new HBox();
	        hbox.setPadding(new Insets(15, 12, 15, 12));
	        hbox.setSpacing(10);   // Gap between nodes
	        hbox.setStyle("-fx-background-color: #336699;");
	 
	        Button buttonCurrent = new Button("Current");
	        buttonCurrent.setPrefSize(100, 20);
	 
	        Button buttonProjected = new Button("Projected");
	        buttonProjected.setPrefSize(100, 20);
	        
	        hbox.getChildren().addAll(buttonCurrent, buttonProjected);
	        
	        return hbox;
	    }
	
	
	public String solve(BigDecimal Amount,String FromCurr, String ToCurr)
	{
		BigDecimal convertedAmount = new BigDecimal("0.0");
		BigDecimal course = new BigDecimal("0.0");
		MathContext mc = new MathContext(2);
		
		if((FromCurr == "PLN" && ToCurr == "Euro") || (FromCurr == "Euro" && ToCurr == "PLN"))
		{
			course = new BigDecimal("4.31940008");
		}
		else if((FromCurr == "PLN" && ToCurr == "US Dollar") || (FromCurr == "US Dollar" && ToCurr == "PLN"))
		{
			course = new BigDecimal("3.98066");
		}
		else if((FromCurr == "PLN" && ToCurr == "British Pound") || (FromCurr == "British Pound" && ToCurr == "PLN"))
		{
			course = new BigDecimal("4.85988");
		}
		else if((FromCurr == "Euro" && ToCurr == "US Dollar") || (FromCurr == "US Dollar" && ToCurr == "Euro"))
		{
			course = new BigDecimal("1.08700");
		}
		else if((FromCurr == "Euro" && ToCurr == "British Pound") || (FromCurr == "British Pound" && ToCurr == "Euro"))
		{
			course = new BigDecimal("0.889976");
		}
		else if((FromCurr == "US Dollar" && ToCurr == "British Pound") || (FromCurr == "British Pound" && ToCurr == "US Dollar"))
		{
			course = new BigDecimal("0.818844");
		}
		if((FromCurr == "PLN" && ToCurr == "Euro") ||(FromCurr == "PLN" && ToCurr == "US Dollar")||
				(FromCurr == "PLN" && ToCurr == "British Pound")||(FromCurr == "US Dollar" && ToCurr == "Euro")
				||(FromCurr == "British Pound" && ToCurr == "Euro")||(FromCurr == "US Dollar" && ToCurr == "British Pound"))
		{
			convertedAmount = Amount.divide(course,2,RoundingMode.HALF_UP);
		}
		else if(FromCurr == "Euro" && ToCurr == "PLN"||(FromCurr == "US Dollar" && ToCurr == "PLN")||(FromCurr == "British Pound" && ToCurr == "PLN"
				||FromCurr == "Euro" && ToCurr == "US Dollar")||(FromCurr == "Euro" && ToCurr == "British Pound")
				||FromCurr == "British Pound" && ToCurr == "US Dollar")
		{
			convertedAmount = Amount.multiply(course,mc);
		}
		else if(FromCurr == ToCurr)
		{
			convertedAmount = Amount;
		}
	
		
		//String result = String.format("%.2f", value);
		BigDecimal result = convertedAmount.setScale(2);
		String value = String.valueOf(result.doubleValue());
		
		
		return value;
	}
	

	@Override
	public void start(Stage primaryStage) {
		try {
			//Scene scene = new Scene(new Group());
			primaryStage.setTitle("Currency Converter");
			BorderPane border = new BorderPane();
			//HBox hbox = addHBox();
			GridPane grid = new GridPane();
			border.setTop(grid);
			table.setEditable(false);
			
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(0,10,0,10));
			grid;

			
			
			ObservableList<String> items = FXCollections.observableArrayList("PLN", "Euro", "US Dollar","British Pound"); 
			ComboBox<String> fromCurrency = new ComboBox(items);
			ComboBox<String> toCurrency = new ComboBox(items);
			
			

			
			Label amount = new Label("Amount");
			grid.add(amount, 0, 0);
			
			TextField currencyBefore = new TextField();
			currencyBefore.setPromptText("Enter Value");
			grid.add(currencyBefore, 0, 1);
			
			DecimalFormat format = new DecimalFormat( "#.00" ); // everything up to next comment forces user to input numbers, not Strings
			currencyBefore.setTextFormatter( new TextFormatter<>(c ->
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
					BigDecimal amount = new BigDecimal( Double.parseDouble((currencyBefore.getText())));
					String fromcurr = fromCurrency.getSelectionModel().getSelectedItem();
					String tocurr = toCurrency.getSelectionModel().getSelectedItem();
					ConvertedAmount2.setText(solve(amount,fromcurr,tocurr));
				
				//ConvertedAmount2.setText(CurrencyBefore.getText());
				
				
				
			}
			});
			
			
			
			GridPane grid2 = new GridPane();
			grid2.setHgap(10);
			grid2.setVgap(10);
			grid2.setPadding(new Insets(0,10,0,10));
			
			border.setCenter(grid2);
			
			

			Label courseLabel = new Label("Course");
			courseLabel.setFont(new Font("Times New Roman",20));
			courseLabel.setTextFill(Color.CHARTREUSE);
			//grid.add(CourseLabel, 0, 2);
			
			
			
			
			//courseTable.setPrefWidth(525);
			
			TableColumn currencyName = new TableColumn("Currency Name");
			currencyName.setMinWidth(100);
			currencyName.setCellValueFactory(new PropertyValueFactory<Person,String>("cName"));
			
			TableColumn symbol = new TableColumn("Symbol");
			symbol.setMinWidth(100);
			symbol.setCellValueFactory(new PropertyValueFactory<Person,String>("sm"));
			
			TableColumn course = new TableColumn("Course");
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
