import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserInterface extends Application {

	// make a textfield and a button to submit
	// textfield chooses port number

	TextField portInputField;
	Button submitButton;
	VBox pane;

	/**
	 *
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		portInputField = new TextField();
		submitButton = new Button("Submit");
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					String serverAddress = "127.0.0.1";
					// obtain port number from textfield and convert to integer
					int port = Integer.parseInt(portInputField.getText());
					Socket s = new Socket(serverAddress, port);
					BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
					String answer = input.readLine();
					// JOptionPane.showMessageDialog(null, answer);
					System.out.println(answer);
					System.exit(0);
					s.close();
				} catch (Exception e) {
					System.out.println("Error: " + e);
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

	public static void main(String[] args) {
		launch(args);
	}
}
