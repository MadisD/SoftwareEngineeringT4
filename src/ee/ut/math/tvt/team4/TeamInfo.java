package ee.ut.math.tvt.team4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TeamInfo {
	private String teamName;
	private String teamLeader;
	private String leaderEmail;
	private String[] teamMembers;
	private String logoPath;
	private String version;

public TeamInfo(String applicationPath,String versionPath){
	readInfo(applicationPath);
	readVersion(versionPath);
}

private void readVersion(String filePath){
	
	try 
	(BufferedReader info = new BufferedReader(new FileReader(filePath));)
	{
		String majorNr = info.readLine();
		String minorNr = info.readLine();
		String revNr = info.readLine();
		this.version = majorNr+"."+minorNr+"."+revNr;
		
		
	} catch (IOException e) {
		System.out.println("File Not Found");
	}
	
	
}
	
private void readInfo(String filePath){
	try 
	(BufferedReader info = new BufferedReader(new FileReader(filePath));)
	{
		String line;
	      while ((line = info.readLine()) != null){
	       // System.out.println(line);
	        String[] pieces = line.split(":");
	        String spec = pieces[0].trim();
	        String data = pieces[1].trim();
	        
	        switch (spec) {
				case "name":
					this.teamName = data;
					
				case "leader":
					this.teamLeader = data;
				case "email":
					this.leaderEmail = data;
				case "members":
					String[] members = data.split(",");
					this.teamMembers = members;
				case "logo":
					this.logoPath = data;
					

			}
	    }
		
	} 
	catch (IOException e) {
		System.out.println("File Not Found");
	}
	
	}

public String getTeamName() {
	return teamName;
}

public String getTeamLeader() {
	return teamLeader;
}

public String getLeaderEmail() {
	return leaderEmail;
}

public String[] getTeamMembers() {
	return teamMembers;
}

public String getLogoPath() {
	return logoPath;
}

public String getVersion() {
	return version;
}


	

}