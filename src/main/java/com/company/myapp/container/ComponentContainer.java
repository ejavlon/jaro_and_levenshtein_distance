package com.company.myapp.container;

import javafx.scene.Scene;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComponentContainer {

    public static final String APP_NAME = "Jaro and Levenshtein distance";
    public static final File FILE = new File("src/main/resources/com/company/myapp/file/words.txt");

    public static final List<String> ALGORITHMS = new ArrayList<>(Arrays.asList("Jaro Winkler Distance","Levenshein Distance"));

    public static final String INFO = "Iltimos algoritmni tanlang!";

   public static List<String> WORDSLIST = new ArrayList<>(98560);
    public static Scene SCENE;
}
