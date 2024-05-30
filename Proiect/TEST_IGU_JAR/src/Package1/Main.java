package Package1;



import Package2.ApplyPrewittOperator;
import Package2.ProgressListener;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ScrollPane;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ComboBox;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;



public class Main extends Application implements ProgressListener{
	ProgressBar progressBar;
	int width = 800;
    int height = 650;
    
    Boolean hor, vert;
    
    String in, out;
    
    BooleanProperty buttonStatus = new SimpleBooleanProperty(true);

	public void startAlgorithm(String image1,
			String image2,
			String algorithm,
			Boolean vertical,
			Boolean horizontal,
			Boolean save
			) throws InterruptedException, URISyntaxException {
		
		hor = horizontal;
		vert = vertical;
		
		in = image1;
		out = image2;
		
		if (in == "" || getClass().getResource("/" + in) == null || !Files.exists(Paths.get(getClass().getResource("/" + in).toURI()))) {
		    Platform.runLater(() -> {
		        Alert alert = new Alert(Alert.AlertType.INFORMATION);
		        alert.setTitle("File Not Found");
		        alert.setHeaderText(null);
		        alert.setContentText("The input image file does not exist.");
		        alert.showAndWait();
		    });
		    return;
		}
		
		if (out == null || out.isEmpty() || !out.endsWith(".png")) {
		    Platform.runLater(() -> {
		        Alert alert = new Alert(Alert.AlertType.INFORMATION);
		        alert.setTitle("Invalid Output Filename");
		        alert.setHeaderText(null);
		        alert.setContentText("Output filename must be specified and end with .png.");
		        alert.showAndWait();
		    });
		    return;
		}
		
		if(algorithm == null) {
			Platform.runLater(() -> {
				Alert alert = new Alert(Alert.AlertType.INFORMATION); 
			    alert.setTitle("No algorithm selected!"); 
			    alert.setHeaderText(null); 
			    alert.setContentText("Please select an algorithm!"); 
			    alert.showAndWait(); 
			});
			return;
		} else if(algorithm != "Prewitt") {
			Platform.runLater(() -> {
				Alert alert = new Alert(Alert.AlertType.INFORMATION); 
			    alert.setTitle("Algorithm not available"); 
			    alert.setHeaderText(null);
			    alert.setContentText(algorithm + " not yet implemented! Only Prewitt available."); 
			    alert.showAndWait();
			});
			return;
		}

		if(!vertical && !horizontal) {
			Platform.runLater(() -> {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("No filter type selected!");
				alert.setHeaderText(null); 
				alert.setContentText("Please select at least one type of filter."); 
				alert.showAndWait(); 
			});
			return;
		}
		
	    ApplyPrewittOperator obj = new ApplyPrewittOperator();
	    obj.prewittStart(in, out, 0, this, save, vertical, horizontal);
	    
	    Platform.runLater(() -> {
	        buttonStatus.set(false);
	    });
	}
	
	public void onProgressUpdate(float percentage) {
		Platform.runLater(() -> {
			progressBar.setProgress(percentage);
			
		});
	}
	
	private void openFifthScreen() {
		Stage FourthStage = new Stage();
		
		List<String[]> contributors = Arrays.asList(
	            new String[]{"Adrian Virlan", "adrian.virlan2903@stud.acs.upb.ro"},
	            new String[]{"Adrian Virlan", "adrian.virlan2001@gmail.com"}
	        );          
        TreeItem<String[]> root = new TreeItem<>(new String[]{"Contributors", ""});
        root.setExpanded(true);

        contributors.forEach(employee -> root.getChildren().add(new TreeItem<>(employee)));

        TreeTableColumn<String[], String> empColumn = new TreeTableColumn<>("Employee");
        empColumn.setPrefWidth(150);
        empColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getValue()[0]));

        TreeTableColumn<String[], String> emailColumn = new TreeTableColumn<>("Email");
        emailColumn.setPrefWidth(190);
        emailColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getValue()[1]));

        TreeTableView<String[]> treeTableView = new TreeTableView<>(root);
        treeTableView.getColumns().setAll(empColumn, emailColumn);
        
		VBox layout = new VBox(10);
		   layout.getChildren().addAll(
				  treeTableView
		   		);
		layout.setAlignment(Pos.TOP_CENTER);
		Scene FourthScene= new Scene(layout, width-400, height-200);
		FourthStage.setScene(FourthScene);
		FourthStage.setTitle("Fifth Screen");
		FourthStage.show();
	}
	
	private void openFourthScreen() {
		Stage FourthStage = new Stage();
		
		TreeItem<String> rootItem = new TreeItem<>("src");
        rootItem.setExpanded(true);

        // Package1
        TreeItem<String> package1 = new TreeItem<>("Package1");
        TreeItem<String> mainJava = new TreeItem<>("Main.java");
        package1.getChildren().add(mainJava);

        // Package2
        TreeItem<String> package2 = new TreeItem<>("Package2");
        package2.getChildren().add(new TreeItem<>("ApplyPrewittOperator.java"));
        package2.getChildren().add(new TreeItem<>("Buffer.java"));
        package2.getChildren().add(new TreeItem<>("Consumer.java"));
        package2.getChildren().add(new TreeItem<>("Convolution.java"));
        package2.getChildren().add(new TreeItem<>("InterfaceConvolution.java"));
        package2.getChildren().add(new TreeItem<>("PrewittOperator.java"));
        package2.getChildren().add(new TreeItem<>("Producer.java"));
        package2.getChildren().add(new TreeItem<>("ProgressListener.java"));
        package2.getChildren().add(new TreeItem<>("horizontal_out.png"));
        package2.getChildren().add(new TreeItem<>("img1.bmp"));
        package2.getChildren().add(new TreeItem<>("img2.bmp"));
        package2.getChildren().add(new TreeItem<>("vertical_out.png"));
        rootItem.getChildren().add(package1);
        rootItem.getChildren().add(package2);
        TreeView<String> tree = new TreeView<>(rootItem);

        Label selectedLabel = new Label("Selected Item Path:");

        // Add listener to tree selection
        tree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue) {
                if (newValue != null) {
                    StringBuilder path = new StringBuilder();
                    TreeItem<String> current = newValue;
                    while (current != null) {
                        path.insert(0, "/" + current.getValue());
                        current = current.getParent();
                    }
                    selectedLabel.setText("Selected Item Path: " + path.toString());
                }
            }
        });
		
		VBox layout = new VBox(10);
		   layout.getChildren().addAll(
				   tree,
				   selectedLabel
		   		);
		layout.setAlignment(Pos.TOP_CENTER);
		
		Scene FourthScene= new Scene(layout, width-400, height-200);
		FourthStage.setScene(FourthScene);
		FourthStage.setTitle("Fourth Screen");
		FourthStage.show();
	}
	
	private void openThirdScreen() {
		Stage thirdStage = new Stage();
		 
		GridPane grid1 = new GridPane();
		grid1.setVgap(5);
		grid1.setHgap(10);
		grid1.setPadding(new Insets(10, 25, 10, 25));
		Label passLabel = new Label("Your password:");
		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Your password");
		grid1.add(passLabel, 0, 0);
		grid1.add(passwordField, 1, 0);
		 
		
		GridPane grid2 = new GridPane();
		grid2.setMinWidth(200);
		grid2.setVgap(5);
		grid2.setHgap(10);
		grid2.setPadding(new Insets(10, 25, 10, 25));
		ObservableList<String> data = FXCollections.observableArrayList();
	    ListView<String> listView = new ListView<String>(data);
        listView.setPrefSize(200, 250);
        listView.setEditable(true);
        data.add("1. Label\r\n");
        data.add("2. Button\r\n");
		data.add("3. Radio Button\r\n");
		data.add("4. Toggle Button\r\n");
		data.add("5. Checkbox\r\n");
		data.add("6. Choice Box\r\n");
		data.add("7. Text Field\r\n");
		data.add("8. Password Field\r\n");
		data.add("9. Scroll Bar\r\n");
		data.add("10. Scroll Pane\r\n");
		data.add("11. List View\r\n");
		data.add("12. Table View\r\n");
		data.add("13. Tree View\r\n");
		data.add("14. Tree Table View\r\n");
		data.add("15. Combo Box\r\n");
		data.add("16. Separator\r\n");
		data.add("17. Slider\r\n");
		data.add("18. Progress Bar and Progress Indicator\r\n");
		data.add("19. Hyperlink\r\n");
		data.add("20. Tooltip\r\n");
		data.add("21. HTML Editor\r\n");
		data.add("22. Titled Pane and Accordion\r\n");
		data.add("23. Menu\r\n");
		data.add("24. Color Picker\r\n");
		data.add("25. Date Picker\r\n");
		data.add("26. Pagination Control\r\n");
		data.add("27. File Chooser\r\n");
        listView.setItems(data);
        listView.setCellFactory(ComboBoxListCell.forListView(data));   
        Label req = new Label("Project Requirements:");
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        	req.setText("Project Requirements: " + newValue);
        });
        
        TableView table = new TableView();
		table.setEditable(true);
	    ObservableList<String[]> dataTable = FXCollections.observableArrayList(
	            new String[]{"1", "Label", "Implemented"},
	            new String[]{"2", "Button", "Implemented"},
	            new String[]{"3", "Radio Button", "Implemented"},
	            new String[]{"4", "Toggle Button", "Implemented"},
	            new String[]{"5", "Checkbox", "Implemented"},
	            new String[]{"6", "Choice Box", "Implemented"},
	            new String[]{"7", "Text Field", "Implemented"},
	            new String[]{"8", "Password Field", "Implemented"},
	            new String[]{"9", "Scroll Bar", "Implemented"},
	            new String[]{"10", "Scroll Pane", "Implemented"},
	            new String[]{"11", "List View", "Implemented"},
	            new String[]{"12", "Table View", "Implemented"},
	            new String[]{"13", "Tree View", "Implemented"},
	            new String[]{"14", "Tree Table View", "Implemented"},
	            new String[]{"15", "Combo Box", "Implemented"},
	            new String[]{"16", "Separator", "Implemented"},
	            new String[]{"17", "Slider", "Implemented"},
	            new String[]{"18", "Progress Bar", "Implemented"},
	            new String[]{"18", "Progress Indicator", "Implemented"},
	            new String[]{"19", "Hyperlink", "Implemented"},
	            new String[]{"20", "Tooltip", "Implemented"},
	            new String[]{"21", "HTML Editor", "Not Implemented"},
	            new String[]{"22", "Titled Pane and Accordion", "Not Implemented"},
	            new String[]{"23", "Menu", "Not Implemented"},
	            new String[]{"24", "Color Picker", "Not Implemented"},
	            new String[]{"25", "Date Picker", "Not Implemented"},
	            new String[]{"26", "Pagination Control", "Not Implemented"},
	            new String[]{"27", "File Chooser", "Not Implemented"}
	        );
	    TableColumn<String[], String> noCol = new TableColumn<>("No.");
        noCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        TableColumn<String[], String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
        TableColumn<String[], String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));
        table.setItems(dataTable);
        table.getColumns().addAll(noCol, nameCol, statusCol);
        noCol.setPrefWidth(50);
        nameCol.setPrefWidth(200);
        statusCol.setPrefWidth(150);
        Label selectedLabel = new Label("Selected Row Details");

        // Add listener to table selection
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                String[] selectedRow = (String[]) newSelection;
                selectedLabel.setText("No: " + selectedRow[0] + ", Name: " + selectedRow[1] + ", Status: " + selectedRow[2]);
            }
        });
	    grid2.add(req, 0, 0);
        grid2.add(listView, 0, 1);
        grid2.add(selectedLabel,1,0);
        grid2.add(table, 1, 1);
        
        
        GridPane grid3 = new GridPane();
        grid3.setVgap(5);
		grid3.setHgap(10);
		grid3.setPadding(new Insets(10, 25, 10, 25));
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(100);
        slider.setValue(40);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);
        Label sliderLabel = new Label();
        sliderLabel.textProperty().bind(Bindings.format("%.2f", slider.valueProperty()));
        grid3.add(sliderLabel, 1, 0);
        grid3.add(slider, 0, 0);
        
        
		VBox layout = new VBox(10);
		   layout.getChildren().addAll(
		   		grid1,
		   		grid2,
		   		grid3);
		layout.setAlignment(Pos.TOP_CENTER);
		 
		Scene thirdScene = new Scene(layout, width, height);
		thirdStage.setScene(thirdScene);
		thirdStage.setTitle("Third Screen");
		thirdStage.show();
	 }

	private void openSecondScreen(String wantedFilter, Boolean saveResult) {
	    Stage secondStage = new Stage();
	    Label secondLabel = new Label("Result analyzer");
	    
	    String text = "";
	    
	    if(!saveResult) {
			Platform.runLater(() -> {
				Alert alert = new Alert(Alert.AlertType.INFORMATION); 
			    alert.setTitle("No saving has been done"); 
			    alert.setHeaderText(null); 
			    alert.setContentText("Save result was not checked!"); 
			    alert.showAndWait(); 
			});
			return;
	    }
	    
	    if(wantedFilter == "vertical") {
	    	if(vert) {
	    		text = "vertical_";
	    	} else {
	    		Platform.runLater(() -> {
		    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("No image was created with vertical filter");
					alert.setHeaderText(null); 
					alert.setContentText("Chose another filter type"); 
					alert.showAndWait(); 
	    		});
				return;
	    	}
	    } else if(wantedFilter == "horizontal") {
	    	if(hor) {
	    		text = "horizontal_";
	    	} else {
	    		Platform.runLater(() -> {
		    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("No image was created with horizontal filter");
					alert.setHeaderText(null); 
					alert.setContentText("Chose another filter type"); 
					alert.showAndWait(); 
	    		});
				return;
	    	}
	    }
	    
	    System.out.println("generated/" + text + out);
	    File imageFile = new File("generated/" + text + out);
	    Image image = new Image(imageFile.toURI().toString());
	    ImageView imageView = new ImageView(image);

	    ScrollPane scrollPane = new ScrollPane(imageView);
	    scrollPane.setContent(imageView);
	    
	    Button closeButton = new Button("Close");
	    closeButton.setOnAction(e -> secondStage.close());

	    VBox layout = new VBox(10);
	    layout.getChildren().addAll(secondLabel, scrollPane, closeButton);
	    layout.setAlignment(Pos.TOP_CENTER);

	    Scene secondScene = new Scene(layout, width, height);
	    secondStage.setScene(secondScene);
	    secondStage.setTitle("Second Screen");
	    secondStage.show();
	}
	
	
	public void start(Stage primaryStage) {
	    
	    progressBar = new ProgressBar(0);

	    Label label = new Label("An edge detection tool.");
	    label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
	    
	    Hyperlink link = new Hyperlink();
	    link.setText("Project requirements");
	    link.setOnAction((ActionEvent e) -> {
	    	try {
				Desktop.getDesktop().browse(new URI("https://curs.upb.ro/2023/pluginfile.php/303474/mod_resource/content/2/2024_Teme_lucrare_JavaFX.pdf"));
			} catch (IOException | URISyntaxException e1) {
				e1.printStackTrace();
			}
	    });

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
	    Text formText = new Text("Introduce the images names: ");
	    formText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
	    grid2.add(formText, 0, 0, 2, 1);
	    Label labelImage1 = new Label("Input image: ");
	    grid2.add(labelImage1, 0, 1);
	    TextField img1 = new TextField();
	    img1.setPromptText("Enter input image");
	    final Tooltip toolTipSource = new Tooltip("Source image");
	    img1.setTooltip(toolTipSource);
	    grid2.add(img1, 1, 1);
	    Label labelImage2 = new Label("Output image: ");
	    grid2.add(labelImage2, 0, 2);
	    TextField img2 = new TextField(); 
	    img2.setPromptText("Enter output image");
	    final Tooltip toolTipDest = new Tooltip("Destination image");
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
	    Label progressLabel = new Label("Progress:");
	    progressBar.setMinWidth(500);
	    progressBar.setMaxWidth(500);
	    grid4.add(progressLabel, 0, 0);
	    grid4.add(progressBar, 1, 0);
	    
	    
	    GridPane grid5 = new GridPane();
	    grid5.setVgap(5);
	    grid5.setHgap(10);
	    grid5.setPadding(new Insets(10, 25, 10, 25));
	    Button startButton = new Button("START"); 
	    startButton.setOnAction(e -> {
	    	new Thread(() -> {
	            try {
	                startAlgorithm(
	                    img1.getText(),
	                    img2.getText(),
	                    choiceBox.getValue(),
	                    checkBoxVertical.isSelected(),
	                    checkBoxHorizontal.isSelected(),
	                    radioButton.isSelected()
	                );
	            } catch (InterruptedException e1) {
	                e1.printStackTrace();
	            } catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        }).start();
		});
	    ToggleButton results = new ToggleButton("See results");
	    results.disableProperty().bind(buttonStatus);
	    ComboBox<String> resultType = new ComboBox<>();
	    resultType.getItems().addAll("horizontal", "vertical");
	    resultType.setPromptText("Rezult type");
	    resultType.setValue("horizontal");
	    resultType.disableProperty().bind(buttonStatus);
	    results.setOnAction(e -> {
		    openSecondScreen(resultType.getValue(), radioButton.isSelected());
		});
	    
	    grid5.add(startButton, 2,0);
	    grid5.add(results, 2, 1);
	    grid5.add(resultType, 1,1);
	    grid5.setAlignment(Pos.BOTTOM_RIGHT);
	    
	    GridPane grid6 = new GridPane();
	    grid6.setVgap(5);
	    grid6.setHgap(10);
	    grid6.setPadding(new Insets(10, 25, 10, 25));
	    Button thirdScreen = new Button("Project Requirements");
	    thirdScreen.setOnAction(e -> {
	    	openThirdScreen();
		});
	    Button fourthScreen = new Button("Project Structure");
	    fourthScreen.setOnAction(e -> {
	    	openFourthScreen();
		});
	    Button fifthScreen = new Button("Additional information");
	    fifthScreen.setOnAction(e -> {
	    	openFifthScreen();
		});
	    grid6.add(fourthScreen, 0, 0);
	    grid6.add(thirdScreen, 1, 0);
	    grid6.add(fifthScreen, 2, 0);
	    
	    final Separator separator1 = new Separator();
	    final Separator separator2 = new Separator();
	    final Separator separator3 = new Separator();
	    final Separator separator4 = new Separator();
	    final Separator separator5 = new Separator();
	    final Separator separator6 = new Separator();
	    
	    final ScrollBar scrollBar = new ScrollBar();
	    scrollBar.setLayoutX(width-scrollBar.getWidth());
	    scrollBar.setMin(0);
	    scrollBar.setOrientation(Orientation.VERTICAL);
	    scrollBar.setPrefHeight(180);
	    scrollBar.setMax(360);
	    
	    BorderPane root = new BorderPane();
	    root.setCenter(grid2);
	    root.setRight(scrollBar);
	    
	    
	    
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
	        grid5,
	        separator6,
	        grid6
	        
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