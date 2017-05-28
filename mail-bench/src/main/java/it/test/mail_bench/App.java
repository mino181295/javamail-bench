package it.test.mail_bench;

import it.test.controller.Controller;
import it.test.model.BenchmarkModel;
import it.test.model.Model;
import it.test.view.BenchmarkView;
import it.test.view.View;

public class App {
	public static void main( String[] args ) {
//		User sender = new User("test.diennea@gmail.com", "test2017");
//		User reciver = new User("test.diennea@gmail.com");
		
		View view = new BenchmarkView();
		Model model = new BenchmarkModel();		
		Controller controller = new Controller(view, model);
		controller.start();
		
	}
}
