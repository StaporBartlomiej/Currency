package application;
	
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {
	
	
	 private void addStackPane(HBox hb) {
		 
	        StackPane stack = new StackPane();
	        Rectangle helpIcon = new Rectangle(30.0, 25.0);
	        helpIcon.setFill(new LinearGradient(0,0,0,1, true, CycleMethod.NO_CYCLE,
	            new Stop[]{
	            new Stop(0,Color.web("#4977A3")),
	            new Stop(0.5, Color.web("#B0C6DA")),
	            new Stop(1,Color.web("#9CB6CF")),}));
	        helpIcon.setStroke(Color.web("#D0E6FA"));
	        helpIcon.setArcHeight(3.5);
	        helpIcon.setArcWidth(3.5);
	        
	        Text helpText = new Text("?");
	        helpText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
	        helpText.setFill(Color.WHITE);
	        helpText.setStroke(Color.web("#7080A0")); 
	        
	        stack.getChildren().addAll(helpIcon, helpText);
	        stack.setAlignment(Pos.CENTER_RIGHT);
	        // Add offset to right for question mark to compensate for RIGHT 
	        // alignment of all nodes
	        StackPane.setMargin(helpText, new Insets(0, 10, 0, 0));
	        
	        hb.getChildren().add(stack);
	        HBox.setHgrow(stack, Priority.ALWAYS);
	                
	    }
	 
	 
	 public HBox addHBox() {
		    HBox hbox = new HBox();
		    hbox.setPadding(new Insets(15, 12, 15, 12));
		    hbox.setSpacing(10);
		    hbox.setStyle("-fx-background-color: #336699;");
		    final Label label = new Label();

		    final TextField CurrencyBefore = new TextField();
		    CurrencyBefore.setPromptText("Enter value");

		    Button buttonProjected = new Button("Projected");
		    buttonProjected.setPrefSize(100, 20);
		    buttonProjected.setOnAction(new EventHandler<ActionEvent>() 
		    {
		    	@Override
		    	public void handle(ActionEvent e)
		    	{
		    		label.setText(CurrencyBefore.getText());
		    	}
		    });
		    
		    hbox.getChildren().addAll(CurrencyBefore, buttonProjected,label);

		    return hbox;
		}
	 
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Currency Converter");
			BorderPane border = new BorderPane();
			HBox hbox = new HBox();
			border.setTop(hbox);
			addStackPane(hbox);
			border.setCenter(addHBox());
			
			ObservableList<String> items = FXCollections.observableArrayList("PLN", "Euro");
			
			final ComboBox currencyChooser = new ComboBox(items);
			border.setTop(currencyChooser);
			
			Scene scene = new Scene(border,400,375);
			

			
			
			
			
			
			
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
