package ee.ut.math.tvt.team4;

import org.apache.log4j.Logger;


public class Intro {
	private static final Logger log = Logger.getLogger(Intro.class);
	public static void main(String[] args) {

		
		IntroUI intro = new IntroUI();
		log.info("New window has been opened, Should SHOW event time and name of the component (invocated class name) ");
		intro.showInfo();
		
		
	}
}
	