package ee.ut.math.tvt.team4;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
		
		try {
			Image logo = getLogo(info.getLogoPath());
			ImageView iv = new ImageView(logo);
			iv.setFitWidth(200);
	        iv.setPreserveRatio(true);
	        iv.setSmooth(true);
	        iv.setCache(true);
			
			gp.add(iv, 1, 8);
		} catch (IOException e) {
			System.out.println(e);
		}
		
		
		Scene scene = new Scene(root,400,400);
		stage.setScene(scene);
		stage.setTitle("Team4");
		stage.centerOnScreen();
		stage.show();
		
	}
	
	private Image getLogo(String logoPath) throws IOException{
		FileInputStream in = new FileInputStream(new File(logoPath));
		Image logo = new Image(in);
		in.close();
		return logo;
	}

	public void setAlwaysOnTop(boolean b) {
		
		
	}
	
	private void onTop(Stage stage, boolean value){
		stage.setAlwaysOnTop(value);
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
	
}
