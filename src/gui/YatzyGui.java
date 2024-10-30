package gui;

import com.sun.glass.events.KeyEvent;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import jdk.jfr.Event;
import models.Die;
import models.RaffleCup;
import models.YatzyResultCalculator;

import java.util.ArrayList;
import java.util.List;

public class YatzyGui extends Application
{
    private final List<Label> diceLabels = new ArrayList<>();
    private final List<Die> dice = new ArrayList<>();
    private int rollCount = 0;
    private final int maxRolls = 3;
    private final String[] combinations = {"One Pair", "Two Pair", "Three of a kind", "Four of a kind", "Small straight", "Large straight", "Full house", "Chance", "Yatzy"};
    private ArrayList<CheckBox> holdCheckBoxes = new ArrayList<>();
    private Label remainingRolls = new Label("Rolls remaining" + " " + (maxRolls - rollCount));
    private RaffleCup raffleCup = new RaffleCup();
    private YatzyResultCalculator yatzyCal = new YatzyResultCalculator(raffleCup.getDice());

    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle("Gui Demo 1");
        GridPane pane = new GridPane();
        this.initContent(pane);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setMinWidth(700);
        stage.setMinHeight(800);
        stage.show();
    }

    public void initContent(GridPane pane)
    {


        // enable this to show grid lines
        pane.setGridLinesVisible(false);

        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        // Adds a rectangle containing the values of the dice rolls
        for (int i = 0; i < 5; i++)
        {
            Rectangle rectangle = new Rectangle(50, 50, Color.LIGHTBLUE);
            rectangle.setArcHeight(20);
            rectangle.setArcWidth(20);

            Label myNum = new Label();
            diceLabels.add(myNum);
            Die die = new Die();
            dice.add(die);

            // Makes sure that the numbers are correctly alligned in the rectangle
            StackPane stackPane = new StackPane(rectangle, myNum);
            stackPane.setAlignment(Pos.CENTER);
            pane.add(stackPane, i, 0);

            // Creates checkboxes for each of the numbers rolled
            CheckBox holdCheckBox = new CheckBox("Hold");
            holdCheckBoxes.add(holdCheckBox);
            pane.add(holdCheckBox, i, 1);


        }

        // Creates labels for showing potential points
        for (int j = 1; j < 7; j++)
        {
            Label labeltxf = new Label((j) + "'ere");
            pane.add(labeltxf, 0, j + 5);

            // Creates textfields for the potential points
            TextField txfnumbers = new TextField();
            txfnumbers.setPrefWidth(10);
            VBox vBox = new VBox(txfnumbers);
            vBox.setAlignment(Pos.CENTER_LEFT);
            pane.add(vBox, 1, 5 + j);

            txfnumbers.setOnMouseClicked(event -> );

        }


        // Labels for Sum and Bonus textfields
        Label label1 = new Label("Sum");
        Label label2 = new Label("Bonus");
        pane.add(label1, 4, 12);
        pane.add(label2, 4, 13);

        // Textfields for Sum and Bonus
        for (int k = 0; k < 2; k++)
        {
            TextField txfPoint = new TextField();
            txfPoint.setPrefWidth(10);
            pane.add(txfPoint, 3, k + 12);
        }


        for (int i = 0; i < combinations.length; i++)
        {
            Label comboLabel = new Label(combinations[i]);
            TextField comboTextField = new TextField();
            comboTextField.setPrefWidth(60);

            // Add the label and text field to the GridPane in the same row
            pane.add(comboLabel, 0, 14 + i);
            pane.add(comboTextField, 1, 14 + i);
        }


        // Button for rolling dice
        Button btnRoll = new Button("Roll Dice");
        pane.add(btnRoll, 3, 2);
        btnRoll.setOnAction(event -> rollDice(btnRoll));


        pane.add(remainingRolls, 0, 2);

        // Button to reset the roll dice button after 3 rolls
        Button btnNewPlayer = new Button("Next player");
        pane.add(btnNewPlayer, 4, 2);
        btnNewPlayer.setOnAction(event -> startNewTurn(btnRoll));


    }






    private void rollDice(Button btnRoll)
    {
        if (rollCount < maxRolls)
        {
            for (int i = 0; i < dice.size(); i++)
            {
                if (!holdCheckBoxes.get(i).isSelected())
                {
                    dice.get(i).roll();
                    diceLabels.get(i).setText(String.valueOf(dice.get(i).getValue()));
                }
            }
            rollCount++;

            remainingRolls.setText("Rolls remaining:" + (maxRolls - rollCount));


            if (rollCount >= maxRolls)
            {
                btnRoll.setDisable(true);
            }
        }
    }

    private void startNewTurn(Button btnRoll)
    {
        rollCount = 0;
        btnRoll.setDisable(false);
        remainingRolls.setText("Rolls remaining:" + (maxRolls-rollCount));

        for (CheckBox checkBox: holdCheckBoxes)
        {
            checkBox.setSelected(false);
        }
    }
    public void chooseFieldAction(Event event)
    {
        TextField txfnumbers = (TextField) event.();
    }

}


