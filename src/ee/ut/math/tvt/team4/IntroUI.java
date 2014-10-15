package ee.ut.math.tvt.team4;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class IntroUI extends Application {
	
	static TeamInfo info = new TeamInfo("application.properties","version.properties");
	
	
	public void showInfo(){
		String args = "";
		launch(args);
	}
	
	@Override
	public void start(Stage stage) {
		
		Group root = new Group();
		GridPane gp = new GridPane();
		root.getChildren().add(gp);
		
		Text team = new Text("Team Name:  ");
		gp.add(team, 1, 1);
		
		
		Text teamName = new Text(info.getTeamName());
		gp.add(teamName, 2, 1);
		
		Text leaderText = new Text("Team Leader:  ");
		gp.add(leaderText, 1, 2);
		
		Text teamLeader = new Text(info.getTeamLeader());
		gp.add(teamLeader, 2, 2);
		
		Text email = new Text("Team leaders e-mail:  ");
		gp.add(email, 1, 3);
		
		Text leaderEmail = new Text(info.getLeaderEmail());
		gp.add(leaderEmail, 2, 3);
		
		
		Text tMemberTitle = new Text("Team members:");
		gp.add(tMemberTitle, 1, 4);
		
		VBox vbox = new VBox();
		gp.add(vbox, 2, 6);
		
		for (String member : info.getTeamMembers()) {
			Text tMember = new Text(member);
			vbox.getChildren().add(tMember);
		}
		
		
		Text versionNr = new Text("Version Number:  ");
		gp.add(versionNr, 1, 7);
		
		Text version = new Text(info.getVersion());
		gp.add(version, 2, 7);
		
		
		
		
		Scene scene = new Scene(root,400,400);
		stage.setScene(scene);
		stage.setTitle("Team4");
		stage.centerOnScreen();
		stage.show();
	}
	
}
