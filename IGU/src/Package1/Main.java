package Package1;



import Package2.ApplyPrewittOperator;


import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;  
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TextField;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;



public class Main extends Application{
	
	String image1;
	String image2;

	public String getImage1() {
		return image1;
	}

	public void setImage1(String image1) {
		this.image1 = image1;
	}

	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

	public void startAlgorithm(String image1,
			String image2,
			String algorithm,
			Boolean vertical,
			Boolean horizontal) {
		
		if(algorithm == null) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION); 
		    alert.setTitle("No algorithm selected!"); 
		    alert.setHeaderText(null); 
		    alert.setContentText("Please select an algorithm!"); 
		    alert.showAndWait(); 
		    return;
		} else if(algorithm != "Prewitt") {
			Alert alert = new Alert(Alert.AlertType.INFORMATION); 
		    alert.setTitle("Algorithm not available"); 
		    alert.setHeaderText(null);
		    alert.setContentText(algorithm + " not yet implemented! Only Prewitt available."); 
		    alert.showAndWait();
		    return;
		}

		if(!vertical && !horizontal) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("No filter type selected!");
			alert.setHeaderText(null); 
			alert.setContentText("Please select at least one type of filter."); 
			alert.showAndWait(); 
			return;
		}
		
	    System.out.println(image1);
	    System.out.println(image2);
	    
	    if(image1 != "" && image2!= "") {
	    	//ApplyPrewittOperator obj = new ApplyPrewittOperator();
	    }
	    
	}
	
	private void openSecondScreen() {
	    Stage secondStage = new Stage();
	    Label secondLabel = new Label("Welcome to the second screen!");
	    Button closeButton = new Button("Close");
	    closeButton.setOnAction(e -> secondStage.close());

	    VBox layout = new VBox(10);
	    layout.getChildren().addAll(secondLabel, closeButton);
	    layout.setAlignment(Pos.CENTER);

	    Scene secondScene = new Scene(layout, 300, 200);
	    secondStage.setScene(secondScene);
	    secondStage.setTitle("Second Screen");
	    secondStage.show();
	}
	
	public void start(Stage primaryStage) {
	    int width = 800;
	    int height = 600;

	    Button secondScreenButton = new Button("Open Second Screen");
	    secondScreenButton.setOnAction(e -> openSecondScreen());

	    Label label = new Label("An edge detection tool that analyzes images using \nthe Sobel, Prewitt, and Scharr algorithms to identify and highlight edges.");
	    label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
	    
	    Hyperlink link = new Hyperlink();
	    link.setText("Project requirement.");
	    link.setOnAction((ActionEvent e) -> {
	    	try {
				Desktop.getDesktop().browse(new URI("https://curs.upb.ro/2023/pluginfile.php/303474/mod_resource/content/2/2024_Teme_lucrare_JavaFX.pdf"));
			} catch (IOException | URISyntaxException e1) {
				e1.printStackTrace();
			}
	    });

	    ToggleButton toggleButton = new ToggleButton("Toggle");


	    GridPane grid1 = new GridPane();
	    grid1.setVgap(5);
	    grid1.setHgap(10);
	    grid1.setPadding(new Insets(10, 25, 10, 25));
	    Label choiceLabel = new Label("Select the Algorithm: ");
	    choiceLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
	    ChoiceBox<String> choiceBox = new ChoiceBox<>();
	    choiceBox.setItems(FXCollections.observableArrayList("Prewitt", "Sobel", "Scharr"));
	    choiceBox.setTooltip(new Tooltip("Select the Algorithm"));
	    grid1.add(choiceLabel, 0, 0);
	    grid1.add(choiceBox, 0, 1);
	    
	    
	    GridPane grid2 = new GridPane();
	    grid2.setVgap(5);
	    grid2.setHgap(10);
	    grid2.setPadding(new Insets(10, 25, 10, 25));

	    Text formText = new Text("Introduce the images paths: ");
	    formText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
	    grid2.add(formText, 0, 0, 2, 1);

	    Label labelImage1 = new Label("Image Source: ");
	    grid2.add(labelImage1, 0, 1);
	    TextField img1 = new TextField();
	    img1.setPromptText("Image source path");
	    final Tooltip toolTipSource = new Tooltip("Source path");
	    img1.setTooltip(toolTipSource);
	    grid2.add(img1, 1, 1);

	    Label labelImage2 = new Label("Image Destination: ");
	    grid2.add(labelImage2, 0, 2);
	    TextField img2 = new TextField(); 
	    img2.setPromptText("Image destination path");
	    final Tooltip toolTipDest = new Tooltip("Destination path");
	    img2.setTooltip(toolTipDest);
	    grid2.add(img2, 1, 2);
	    RadioButton radioButton = new RadioButton("Save result");
	    grid2.add(radioButton, 0, 3);
	    
	    GridPane grid3 = new GridPane();
	    grid3.setVgap(5);
	    grid3.setHgap(10);
	    grid3.setPadding(new Insets(10, 25, 10, 25));
	    
	    CheckBox checkBoxVertical = new CheckBox("Vertical");
	    CheckBox checkBoxHorizontal = new CheckBox("Horizontal");
	    Text filterType = new Text("Choose the type of the filter: ");
	    filterType.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
	    
	    grid3.add(filterType, 0, 0);
	    grid3.add(checkBoxVertical, 0, 1);
	    grid3.add(checkBoxHorizontal, 0, 2);
	    
	    GridPane grid4 = new GridPane();
	    grid4.setVgap(5);
	    grid4.setHgap(10);
	    grid4.setPadding(new Insets(10, 25, 10, 25));
	    Label progressLabel = new Label("Progress: ");
	    ProgressBar progressBar = new ProgressBar();
	    progressBar.setMinWidth(500);
	    progressBar.setMaxWidth(500);
	    progressBar.setProgress(0.6f);
	    grid4.add(progressLabel, 0, 0);
	    grid4.add(progressBar, 1, 0);
	    
	    Button startButton = new Button("START"); 
	    startButton.setOnAction(e -> startAlgorithm(img1.getText(),
	    		img2.getText(),
	    		choiceBox.getValue(),
	    		checkBoxVertical.isSelected(),
	    		checkBoxHorizontal.isSelected()
	    		));
	    startButton.setAlignment(Pos.BOTTOM_RIGHT);

	    final Separator separator1 = new Separator();
	    final Separator separator2 = new Separator();
	    final Separator separator3 = new Separator();
	    final Separator separator4 = new Separator();
	    final Separator separator5 = new Separator();
	    
	    final ScrollBar scrollBar = new ScrollBar();
	    scrollBar.setLayoutX(width-scrollBar.getWidth());
	    scrollBar.setMin(0);
	    scrollBar.setOrientation(Orientation.VERTICAL);
	    scrollBar.setPrefHeight(180);
	    scrollBar.setMax(360);
	    
	    BorderPane root = new BorderPane();
	    root.setCenter(grid2);
	    root.setRight(scrollBar);
	    
	    Button startButton1 = new Button("START"); 
	    Button startButton2 = new Button("START"); 
	    Button startButton3 = new Button("START"); 
	    
	    VBox content = new VBox(10);
	    content.setPadding(new Insets(10, 25, 10, 25));
	    content.getChildren().addAll(
	        label,
	        link,
	        separator1,
	        grid2,
	        separator2,
	        grid1,
	        separator3,
	        grid3,
	        separator4,
	        grid4,
	        separator5,
	        toggleButton,
	        secondScreenButton,
	        startButton,
	        startButton1,
	        startButton2,
	        startButton3
	    );
	    
	    root.setCenter(content);
	    

	    
	    scrollBar.valueProperty().addListener((ObservableValue<? extends Number> ov, 
	    		Number old_val, Number new_val) -> {
	    			root.setLayoutY(-new_val.doubleValue());
	    		}); 
	    
	    Scene scene = new Scene(root, width, height);
	    primaryStage.setScene(scene);
	    primaryStage.setTitle("IGU Project");
	    primaryStage.show();
	}
	    
    public static void main(String[] args) throws InterruptedException {
        launch(args);
        
        
        
    }
}