import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.collections.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import sec6_db.Student;
import sec6_db.dbConn;

public class db_207 extends Application {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    ObservableList<Student> data;
    TableView<Student> table;

    private VBox vBoxMainLayout = new VBox();
    private Scene scene;
    private Button themeToggleButton = new Button("Dark Mode");
    private final String LIGHT_THEME_PATH = "/sec6_db/light-theme.css";
    private final String DARK_THEME_PATH = "/sec6_db/dark-theme.css";

    @Override
    public void start(Stage primaryStage) {
        Label SearchLabel = new Label("Search for course by id");

        TextField SearchTextField = new TextField();
        SearchTextField.setPrefWidth(200);
        Button srcBtn = new Button("Search");

        Label result = new Label("");
        HBox h = new HBox(SearchLabel, SearchTextField, srcBtn, result);
        h.setPadding(new Insets(20, 0 ,20, 200));
        h.setSpacing(50);
        Button btn1 = new Button();
        btn1.setText("Add");

        Button btn2 = new Button();
        btn2.setText("Clear");

        Button btnclear = new Button();
        btnclear.setText("Clear table view");

        Text txt1 = new Text("Add New Course");

        Label l1 = new Label("Code");
        l1.getStyleClass().add("labelstyle");
        Label l2 = new Label("Title");
        l2.getStyleClass().add("labelstyle");
        Label l3 = new Label("Prerequest");
        l3.getStyleClass().add("labelstyle");

        TextField t1 = new TextField();
        TextField t2 = new TextField();
        TextField t3 = new TextField();

        GridPane g1 = new GridPane();
        g1.add(txt1, 0, 0, 2, 1);
        g1.add(btnclear, 2, 0);
        g1.add(themeToggleButton, 3, 0);
        g1.add(l1, 0,1);
        g1.add(t1, 1,1);
        g1.add(l2, 0,2);
        g1.add(t2, 1,2);
        g1.add(l3, 0,3);
        g1.add(t3, 1,3);

        g1.add(btn1, 0,6);
        g1.add(btn2, 1,6);

        g1.setVgap(10);
        g1.setHgap(10);
        g1.setAlignment(Pos.CENTER);
        g1.setPadding(new Insets(20));

        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("No rows to display"));

        TableColumn c1 = new TableColumn("Code");
        c1.setCellValueFactory(new PropertyValueFactory<>("code"));

        TableColumn c2 = new TableColumn("Title");
        c2.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn c3 = new TableColumn("Prerequest");
        c3.setCellValueFactory(new PropertyValueFactory<>("prerequest"));

        
        table.getColumns().addAll(c1, c2, c3);
        VBox v = new VBox(table);
        v.setPadding(new Insets(20));

        try {
            show();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        btn1.setOnAction((ActionEvent event) -> {
            
            conn = dbConn.DBConnection();
            String sql = "Insert into courses (code, title, prerequest) Values(?,?,?)";
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, t1.getText());
                pst.setString(2, t2.getText());
                pst.setString(3, t3.getText());
                int i = pst.executeUpdate();
                if (i == 1) {
                    System.out.println("Data is inserted");
                }
                t1.setText("");
                t2.setText("");
                t3.setText("");

                pst.close();
                conn.close();
                show();
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        });
        srcBtn.setOnAction((ActionEvent event) -> {

            String id = SearchTextField.getText();

            if (id.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR, "id field is empty");
                alert.show();
            }

            else {

                String sql = "SELECT title FROM courses WHERE id = ?";

                conn = dbConn.DBConnection();
                try {
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, id);
                    res = pst.executeQuery();
                    if (res.next()) {
                        result.setText(res.getString(1));
                    }
                    pst.close();
                    conn.close();

                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
        });

        // Container for grid and other buttons
        VBox vBoxControls = new VBox(10); // spacing between nodes
        vBoxControls.getChildren().addAll(h,g1,v);
        vBoxControls.setPadding(new Insets(10));

        // Container that will be scrolled
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vBoxControls);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPannable(true);

        scrollPane.prefHeightProperty().bind(primaryStage.heightProperty());
        
        InputStream testStream = getClass().getResourceAsStream("/sec6_db/dark-theme.css");
    if (testStream == null) {
        System.err.println("Failed to load the light-theme.css stylesheet. File does not exist.");
    } else {
        System.out.println("Successfully located the light-theme.css stylesheet.");
        try {
            testStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        vBoxMainLayout.getChildren().addAll(g1, scrollPane); // Adding g1, table, scrollPane to your main layout
        vBoxMainLayout.getStyleClass().add("custom-grid-pane");
        themeToggleButton.setOnAction(event -> toggleTheme());

        
        scene = new Scene(vBoxMainLayout, 1000, 500);
        applyCssTheme("light-theme.css"); // Apply the light theme

        primaryStage.setTitle("DB Connection");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Listener for width changes to hide ScrollPane bars when the window is large enough
        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
            boolean wideEnough = Double.compare(newValue.doubleValue(), vBoxControls.getWidth()) > 0;
            scrollPane.setHbarPolicy(wideEnough ? ScrollPane.ScrollBarPolicy.NEVER : ScrollPane.ScrollBarPolicy.AS_NEEDED);
        });

        // Listener for height changes to hide ScrollPane bars when the window is tall enough
        primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> {
            boolean tallEnough = Double.compare(newValue.doubleValue(), vBoxControls.getHeight()) >0;
            scrollPane.setVbarPolicy(tallEnough ? ScrollPane.ScrollBarPolicy.NEVER : ScrollPane.ScrollBarPolicy.AS_NEEDED);
        });
    }

    public void show() throws SQLException {

        data = FXCollections.observableArrayList();
        conn = dbConn.DBConnection();

        pst = conn.prepareStatement("select code , title , prerequest from courses");
        res = pst.executeQuery();

        while (res.next()) {
            data.add(new Student(res.getString(1), res.getString(2), res.getString(3)));

        }
        pst.close();
        conn.close();
        table.setItems(data);
    }
    public void clear() throws SQLException {

        data = FXCollections.observableArrayList();
        conn = dbConn.DBConnection();

        pst = conn.prepareStatement("Delete code , title , prerequest from courses");
        res = pst.executeQuery();

        while (res.next()) {
            data.remove(new Student (res.getString(1), res.getString(2), res.getString(3)));

        }
        pst.close();
        conn.close();
        table.setItems(data);
    }
    private void toggleTheme() {
    if ("Dark Mode".equals(themeToggleButton.getText())) {
        applyCssTheme("dark-theme.css"); 
        themeToggleButton.setText("Light Mode");
    } else if ("Light Mode".equals(themeToggleButton.getText())) {
        applyCssTheme("light-theme.css"); 
        themeToggleButton.setText("Dark Mode");
    }
}

private void applyCssTheme(String path) {
    scene.getStylesheets().clear();
    InputStream stylesheetStream = getClass().getResourceAsStream("/sec6_db/" + path);
    if (stylesheetStream == null) {
        System.err.println("Cannot load stylesheet at " + path + ". Make sure the file is in the correct directory.");
    } else {
        try {
            String stylesheet = getClass().getResource("/sec6_db/" + path).toExternalForm();
            scene.getStylesheets().add(stylesheet);
        } finally {
            try {
                stylesheetStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

}