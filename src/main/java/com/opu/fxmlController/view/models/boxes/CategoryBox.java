package com.opu.fxmlController.view.models.boxes;

import com.opu.database.controllers.EntitiesController;
import com.opu.database.entities.Category;
import com.opu.database.entities.Note;
import com.opu.fxmlController.SceneController;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;

import static com.opu.fxmlController.view.models.sceneRes.Scene.MAIN_PAGE;

// Класс представления категории на главной странице
public class CategoryBox extends VBox{
    private Category category;

    public CategoryBox(Category category){

        this.category = category;

        setMinWidth(160);
        setMinHeight(170);
        setPrefWidth(160);
        setPrefHeight(170);
        setStyle("-fx-background-color:#ffffff;");
        setStyle("-fx-border-color:#00c6d2;");
        setPadding(new Insets(0, 0, 0, 20));
        setCursor(Cursor.HAND);

        Button deleteButton = new Button();
        deleteButton.setStyle("-fx-background-image:  url('/image/close-icon.png')");
        deleteButton.setMinWidth(31);
        deleteButton.setMinHeight(30);
        deleteButton.setFocusTraversable(false);
        setMargin(deleteButton,new Insets(5,5,0,100));

        // Обработчик кнопки удаления категории
        deleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                EntitiesController ec = new EntitiesController();
                SceneController sc = new SceneController();
                ArrayList<Note> notes = ec.getNotesByCategoryId(category.getId());
                for(Note note : notes){
                    ec.deleteNote(note.getId());
                }
                ec.deleteCategory(category.getId());
                sc.changeScene(MAIN_PAGE,deleteButton);
            }
        });

        Label name = new Label(category.getCategoryName());
        name.setMaxWidth(160);
        name.setAlignment(Pos.CENTER);
        name.setFont(Font.font("",17));

        Label num = new Label(new EntitiesController().getNotesNum(category.getId()) + " ");
        num.setPrefWidth(160);
        num.setAlignment(Pos.CENTER);
        num.setFont(Font.font(14));

        getChildren().addAll(deleteButton,name,num);
        setMargin(name,new Insets(30,15,0,0));
        setMargin(num,new Insets(10,15,0,0));
    }

    public int getCategoryId(){
        return this.category.getId();
    }
}
