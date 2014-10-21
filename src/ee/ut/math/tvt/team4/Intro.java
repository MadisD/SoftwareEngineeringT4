package ee.ut.math.tvt.team4;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.controller.impl.SalesDomainControllerImpl;
import ee.ut.math.tvt.salessystem.ui.ConsoleUI;
import ee.ut.math.tvt.salessystem.ui.SalesSystemUI;


public class Intro {
	private static final Logger log = Logger.getLogger(Intro.class);
	private static final String MODE = "console";
	
	public static void main(String[] args) {
		
		final SalesDomainController domainController = new SalesDomainControllerImpl();
		

		if (args.length == 1 && args[0].equals(MODE)) {
			log.debug("Mode: " + MODE);

			ConsoleUI cui = new ConsoleUI(domainController);
			cui.run();
		}
		else {
		
		IntroUI intro = new IntroUI();
		log.info("New window has been opened");
		intro.showInfo();
		
		final SalesSystemUI ui = new SalesSystemUI(domainController);
		ui.setVisible(true);

		intro.setAlwaysOnTop(false);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		intro.setVisible(false);
		
		
		}
		
	}
}
	