package sec6_db;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/*
Q2
 */

public class Final_Q2 extends Application {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    ObservableList<courses> data;
    TableView<courses> table;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Label SearchLabel = new Label("Search for Course by ID");

        TextField SearchTextField = new TextField();
        SearchTextField.setPrefWidth(200);

        Button srcBtn = new Button("Search");

        Label result = new Label("");

        HBox h = new HBox(SearchLabel, SearchTextField, srcBtn, result);
        h.setPadding(new Insets(20, 0 ,20, 200));
        h.setSpacing(50);

        Text txt1 = new Text("Add New course");

        Label idLabel = new Label("ID");
        Label coursenamelabel = new Label("Course Name");
        Label coursecredithor = new Label("Course Credit hour");
        
        Label profname = new Label("Professor Name");
        Label numberlabel = new Label("Number of Students");
        TextField idTextField = new TextField();
        TextField coursenametxt = new TextField();
        TextField coursecedit = new TextField();
        TextField profnametxt = new TextField();
        TextField numberxt = new TextField();
        Button addBtn = new Button("Add");
        Button clearBtn = new Button("Clear");

        GridPane g1 = new GridPane();
        g1.add(txt1, 0, 0, 2, 1);
        g1.add(idLabel, 0, 1);
        g1.add(idTextField, 1, 1);
        g1.add(coursenamelabel, 0, 2);
        g1.add(coursenametxt, 1, 2);
        g1.add(coursecredithor, 0, 3);
        g1.add(coursecedit, 1, 3);
        g1.add(profname, 0, 4);
        g1.add(profnametxt, 1, 4);
        g1.add(numberlabel, 0, 5);
        g1.add(numberxt, 1, 5);
        
        g1.add(addBtn, 0, 6);
        g1.add(clearBtn, 1, 6);

        g1.setVgap(10);
        g1.setHgap(10);
        g1.setAlignment(Pos.CENTER);
        g1.setPadding(new Insets(20));

        Label deleteLabel = new Label("Delete Course by ID");
        Label idLabel2 = new Label("ID");
        TextField idTextField2 = new TextField();

        Button deleteBtn = new Button("Delete");

        GridPane g2 = new GridPane();

        g2.add(deleteLabel, 0, 0, 2, 1);
        g2.add(idLabel2, 0, 1);
        g2.add(idTextField2, 1, 1);
        g2.add(deleteBtn, 0, 2, 2, 1);

        g2.setVgap(10);
        g2.setHgap(10);
        g2.setPadding(new Insets(20));



        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("No rows to display"));
        table.setPrefSize(700, 400);
        
        TableColumn c1 = new TableColumn("ID");
        c1.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn c2 = new TableColumn("Name");
        c2.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn c3 = new TableColumn("Hours");
        c3.setCellValueFactory(new PropertyValueFactory<>("hours"));

        TableColumn c4 = new TableColumn("Prof");
        c4.setCellValueFactory(new PropertyValueFactory<>("prof"));
        
        TableColumn c5 = new TableColumn("No of Student");
        c5.setCellValueFactory(new PropertyValueFactory<>("no"));
        
        table.getColumns().addAll(c1, c2, c3, c4,c5);
        VBox v = new VBox(table);
        v.setPadding(new Insets(20));
        v.setSpacing(10);
        v.setAlignment(Pos.CENTER);

        try {
            show();
        } catch (Exception e) {
            System.err.println(e.toString());
        }

        srcBtn.setOnAction((ActionEvent event) -> {

            String id = SearchTextField.getText();

            if (id.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR, "id field is empty");
                alert.show();
            }

            else {

                String sql = "SELECT Name FROM courses WHERE id = ?";

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

        Label updateLabel = new Label("Update Course number of Students by ID");
        Label idLabel1 = new Label("ID");
        TextField idTextField1 = new TextField();
        Label jobLabel1 = new Label("Nof . Of students");
        TextField jobTextField1 = new TextField();
        Button updateBtn = new Button("Update");

        GridPane g3 = new GridPane();

        g3.add(updateLabel, 0, 0, 2, 1);
        g3.add(idLabel1, 0, 1);
        g3.add(idTextField1, 1, 1);
        g3.add(jobLabel1, 0, 2);
        g3.add(jobTextField1, 1, 2);
        g3.add(updateBtn, 0, 3, 2, 1);

        g3.setVgap(10);
        g3.setHgap(10);
        g3.setPadding(new Insets(20));
        VBox left = new VBox(g1, g3);
        
        Label allLabel = new Label("Calculate Total number of students");
        Button allBtn = new Button("Calculate Total number of students");
        Label empty = new Label();

        GridPane g4 = new GridPane();

        g4.add(allLabel, 0, 0, 2, 1);
        g4.add(allBtn, 0, 1);
        g4.add(empty, 1, 1);

        g4.setVgap(10);
        g4.setHgap(10);
        g4.setPadding(new Insets(20));

        Label maxLabel = new Label("Maximum course Hour");
        Button maxBtn = new Button("Maximum course Hour");
        Label empty1 = new Label();

        GridPane g5 = new GridPane();

        g5.add(maxLabel, 0, 0, 2, 1);
        g5.add(maxBtn, 0, 1);
        g5.add(empty1, 0, 2);

        g5.setVgap(10);
        g5.setHgap(10);
        g5.setPadding(new Insets(20));

        VBox vbox = new VBox(g2, g4, g5);

        addBtn.setOnAction((ActionEvent event) -> {

            String id = idTextField.getText();
            String name = coursenametxt.getText();
            String course = coursecedit.getText();
            String profnamee = profnametxt.getText();
            String number = numberxt.getText();
            if (id.isEmpty() || name.isEmpty() || course.isEmpty() || profnamee.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Field is empty");
                alert.show();
            }
            else {
                conn = dbConn.DBConnection();

                String sql = "INSERT INTO courses (id, name, hours,prof,numbers) Values(?,?,?,?,?)";

                try {
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, idTextField.getText());
                    pst.setString(2, coursenametxt.getText());
                    pst.setString(3, coursecedit.getText());
                    pst.setString(4, profnametxt.getText());
                    pst.setString(5, numberxt.getText());
                    int i = pst.executeUpdate();
                    pst.close();
                    conn.close();
                    show();
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
        });


        updateBtn.setOnAction((ActionEvent event) -> {
            String id = idTextField1.getText();
            String job = numberxt.getText();

            if(id.isEmpty() || job.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Field is empty");
                alert.show();
            }
            else {

                String sql = "UPDATE courses set numbers = ? where id = ?";

                conn = dbConn.DBConnection();
                try {
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, job);
                    pst.setString(2, id);
                    pst.executeUpdate();
                    pst.close();
                    conn.close();
                    show();
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
        });

        deleteBtn.setOnAction(e -> {
            String id = idTextField2.getText();

            if (id.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Field is empty");
                alert.show();
            }

            else {

                String sql = "DELETE FROM courses WHERE id = ?";

                conn = dbConn.DBConnection();
                try {
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, idTextField2.getText());
                    pst.executeUpdate();
                    pst.close();
                    conn.close();
                    show();
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
        });

        allBtn.setOnAction(e -> {

            String sql = "SELECT SUM(numbers) FROM courses";

            conn = dbConn.DBConnection();
            try {
                pst = conn.prepareStatement(sql);
                res = pst.executeQuery();
                if (res.next()) {
                    empty.setText("There are " + Integer.toString(res.getInt(1)) + " students");
                }
                pst.close();
                conn.close();

            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        });

        maxBtn.setOnAction(e -> {

            String sql = "SELECT MAX(hours) FROM courses";

            conn = dbConn.DBConnection();
            try {
                pst = conn.prepareStatement(sql);
                res = pst.executeQuery();
                if (res.next()) {
                    empty1.setText(Integer.toString(res.getInt(1)) );
                }
                pst.close();
                conn.close();

            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        });


        FlowPane f = new FlowPane(left, vbox,v);
        VBox root = new VBox(h,f);
        Scene scene = new Scene(root, 900, 550);
        scene.getStylesheets().add((new File("C:\\Users\\LeaderTech\\Documents\\NetBeansProjects\\DB_207_1_3\\src\\sec6_db\\style.css")).toURI().toString());
        primaryStage.setTitle("Exam");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void show() throws SQLException {

        data = FXCollections.observableArrayList();
        conn = dbConn.DBConnection();

        pst = conn.prepareStatement("SELECT id,name,hours,prof,numbers from courses");
        res = pst.executeQuery();

        while (res.next()) {
            data.add(new courses(res.getInt(1), res.getString(2), res.getInt(3), res.getString(4),res.getInt(5)));
        }
        table.setItems(data);
    }

}
