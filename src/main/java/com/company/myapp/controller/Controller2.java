package com.company.myapp.controller;

import com.company.myapp.algorithm.LevenshteinDistance;
import com.company.myapp.container.ComponentContainer;
import com.company.myapp.algorithm.JaroWinklerDistance;
import com.company.myapp.model.Result;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.*;

public class Controller2 {

    @FXML
    private Button btn;

    @FXML
    private ListView<String> listView;

    @FXML
    private TextField textFileld1;

    @FXML
    private TextField textFileld2;

    @FXML
    private ToggleButton toggleBtn;

    @FXML
    private ComboBox<String> comBox;

    @FXML
    void initialize(){
        comBox.getItems().addAll(ComponentContainer.ALGORITHMS);
    }

    @FXML
    void btnClick(ActionEvent event) {
        if (listView.getItems().size() != 0) {
            listView.getItems().clear();
            listView.refresh();
        }

        if (comBox.getSelectionModel().isSelected(0)){
            String str1 = textFileld1.getText().toLowerCase().trim();
            if (!textFileld2.isDisable()){
                String str2 = textFileld2.getText().toLowerCase().trim();
                double distance = JaroWinklerDistance.compute(str1,str2);

                listView.getItems().add(String.format("%s va %s => %s",str1,str2,distance));
            }else {

                Comparator<Result>mycom = new Comparator<Result>() {
                    @Override
                    public int compare(Result o1, Result o2) {
                        return -Double.compare(o1.getDistance(),o2.getDistance());
                    }
                };


                List<Result> resultList = new ArrayList<>();

                for (String str2 : ComponentContainer.WORDSLIST) {
                    Double distance = JaroWinklerDistance.compute(str1, str2);
                    Result result = new Result(str1 + "=>" + str2,distance);
                    resultList.add(result);
                }

                int count = 1;
                resultList.sort(mycom);

                for (Result result : resultList) {
                    if (result.getDistance() >= 0.6){
                        listView.getItems().add(String.format("%s.%s  => %s",count++,result.getStr(),result.getDistance()));
                    }
                }
            }
        }else if (comBox.getSelectionModel().isSelected(1)){
            if (!textFileld2.isDisable()){
                String str1 = textFileld1.getText().toLowerCase().trim();
                String str2 = textFileld2.getText().toLowerCase().trim();
                double distance = LevenshteinDistance.compute(str1,str2);

                listView.getItems().add(String.format("%s va %s => %s",str1,str2,distance));
            }else {
                String str1 = textFileld1.getText().toLowerCase().trim();
                double distance = -1;

                for (String str2 : ComponentContainer.WORDSLIST) {
                    distance = LevenshteinDistance.compute(str1,str2);
                    if (distance >= 0.7){
                        listView.getItems().add(String.format("%s  => %s",str2,distance));
                    }
                }
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,ComponentContainer.INFO,ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    void  toggleClick(){
        if (toggleBtn.isSelected()){
            textFileld2.setDisable(true);
            toggleBtn.setText("Off");
        }else {
            textFileld2.setDisable(false);
            toggleBtn.setText("On");
        }
    }
}
