package it.test.mail_bench;

import it.test.controller.Controller;
import it.test.model.BenchmarkModel;
import it.test.view.BenchmarkView;

public class App {
	public static void main( String[] args ) {			
		new Controller(new BenchmarkView(), new BenchmarkModel()).start();	
	}
}
