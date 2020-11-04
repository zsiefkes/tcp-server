import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserInterface extends Application {

	// client details
//	private String clientIP;
	private String timeStamp;
//	private int clientPort;
	private int localPort;

	// JavaFX fields
	TextField portInputField;
	Button submitButton;
	VBox pane;

	@Override
	public void start(Stage primaryStage) throws Exception {
		portInputField = new TextField();
		submitButton = new Button("Submit");
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				timeStamp = connectToDateServer();
				if (!timeStamp.equals("")) {
					writeToDB(timeStamp);
				}
			}
		});

		pane = new VBox();
		pane.getChildren().add(portInputField);
		pane.getChildren().add(submitButton);
		Scene scene = new Scene(pane, 600, 400);

		primaryStage.setTitle("TCP Client");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private String connectToDateServer() {
		try {
			String serverAddress = "127.0.0.1";
			// obtain port number from textfield and convert to integer
			localPort = Integer.parseInt(portInputField.getText());
			Socket s = new Socket(serverAddress, localPort);
			BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String answer = input.readLine();
			// JOptionPane.showMessageDialog(null, answer);
//			System.out.println(answer);
//			System.exit(0);
			s.close();
			// return data from server
			return answer;
		} catch (Exception e) {
			System.out.println("Error: " + e);
			return "";
		}

	}

	private void writeToDB(String timeStamp) {
// insert into client_details values ('192.168.56.1', 55252, Now() );
		try {
			String databaseUser = "dizach";
			String databaseUserPass = "123";
			Class.forName("org.postgresql.Driver");
			
			Connection connection = null;
			String url = "jdbc:postgresql://localhost/testdb";
			connection = DriverManager.getConnection(url, databaseUser, databaseUserPass);
			
			Statement s = connection.createStatement();
			s.executeUpdate("insert into timestamps values ('" + timeStamp + "');");
			connection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Login error: " + e.toString());
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
