package it.matteo.bench;

import it.matteo.controller.Controller;
import it.matteo.model.BenchmarkModel;
import it.matteo.view.BenchmarkView;

public class App {

    public static void main(String[] args) {
        new Controller(new BenchmarkView(), new BenchmarkModel()).start();
    }
}
