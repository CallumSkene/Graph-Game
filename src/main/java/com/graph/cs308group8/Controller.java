package com.graph.cs308group8;

import javafx.application.Application;

public class Controller implements ControllerADT {
    private final String filename;
    public static ModelADT model;

    public Controller(final String filename) {
        this.filename = filename;
        Controller.model = new Model(this.filename);
    }

    public static void updateView() {
        Pair<NodeADT, NodeADT> pair = Controller.model.createRandomPair();

        View.pair = pair;
        View.routes = Controller.model.calcRoutes(pair.key(), pair.value());
    }

    public void start() {
        View.model = model;
        Controller.updateView();

        Application.launch(View.class);

    }

}
