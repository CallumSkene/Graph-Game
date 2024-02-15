package com.graph.cs308group8;

public class Main {
    public static void main(String[] args) {
        ControllerADT controller = new Controller("src/main/java/com/graph/cs308group8/data.txt");
        controller.start();
    }
}
