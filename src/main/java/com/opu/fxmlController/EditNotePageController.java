package com.opu.fxmlController;

import com.opu.database.Controllers.EntitiesController;
import com.opu.database.entities.Note;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Created by antipavitaly on 5/26/17.
 */
public class EditNotePageController {

        @FXML
        private Button button;

        @FXML
        private TextField noteNameField;

        @FXML
        private Slider progressSlider;

        @FXML
        private TextArea noteSubnoteField;

        @FXML
        private TextArea noteCommentField;

        @FXML
        private DatePicker finalDatePicker;

        @FXML
        private ChoiceBox categoriesNameField;

        @FXML
        private TextField startDateField;

        int noteId;

        private String noteName;
        private String noteCategory;
        private String noteComment;
        private String noteSubnote;
        private String noteStartDate;
        private String noteFinalDate;
        private float progress;

        Note note;

        EntitiesController ec;


        public void initialize(int noteId) {




            ec = new EntitiesController();

            note = ec.getNoteById(noteId);

            getAndSetValues(note);
            initSlider(progressSlider);


            button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {

                    if (noteNameField.getText().equals("") || categoriesNameField.getValue().toString().equals(null) || noteCommentField.getText().equals("") || noteSubnoteField.getText().equals("") || finalDatePicker.getValue() == null) {

                        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
                        dialog.setHeaderText("Error");
                        dialog.setContentText("Fill all the fields!");
                        dialog.showAndWait();


                    } else {

                        noteName = noteNameField.getText();
                        noteCategory = categoriesNameField.getValue().toString();
                        noteComment = noteCommentField.getText();
                        noteSubnote = noteSubnoteField.getText();
                        noteFinalDate = finalDatePicker.getValue().toString();

                        ec.updateNote(noteId,noteName, noteSubnote, noteFinalDate, noteComment, noteCategory, progress);


                        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
                        dialog.setHeaderText("Success");
                        dialog.setContentText("The note was created!");
                        dialog.showAndWait();

                    }


                }

            });


        }

    private void getAndSetValues(Note note){
            noteName = note.getNoteName();
            noteComment = note.getNoteComment();
            noteSubnote = note.getNoteSubnote();
            noteFinalDate = note.getNoteFinalDate();
            progress = note.getProgress();
            noteStartDate = note.getNoteStartDate();
            noteCategory = note.getCategory().getCategoryName();

            choiceBoxValues(categoriesNameField);
            categoriesNameField.setValue(noteCategory);



            noteNameField.setText(noteName);
            noteCommentField.setText(noteComment);
            noteSubnoteField.setText(noteSubnote);
            finalDatePicker.setValue(localDate(noteFinalDate));
            categoriesNameField.setValue(noteStartDate);
            startDateField.setText(noteStartDate);

            startDateField.setEditable(false);
    }

    private void choiceBoxValues(ChoiceBox chB){
        ArrayList<String> categoryNames =  ec.getCategoryNames();
        ObservableList<String> names = FXCollections.observableArrayList();

        for (String n: categoryNames)
        {

            names.add(n);
        }

        chB.setItems(names);

    }
    private void initSlider(Slider slider){

                slider.setMin(0.0);
                slider.setMax(100.0);
                slider.setValue(progress);
                slider.setShowTickMarks(true);
                slider.valueProperty().addListener(((observable, oldValue, newValue) -> {

                        progress = newValue.floatValue();

                }));
        }
    private LocalDate localDate (String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }


    }


