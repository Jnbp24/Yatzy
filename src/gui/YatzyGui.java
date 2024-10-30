package gui;

import models.Die;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.event.Event;
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
    private YatzyResultCalculator yatzyCal;
    private TextField[] comboFields = new TextField[combinations.length];
    private TextField[] oneToSix = new TextField[6];
    private int[] scores = new int[combinations.length];
    private TextField txfPoint = new TextField();
    Label labeltxf = new Label();


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
            oneToSix[j - 1] = txfnumbers;
            txfnumbers.setPrefWidth(10);


            VBox vBox = new VBox(txfnumbers);
            vBox.setAlignment(Pos.CENTER_LEFT);
            pane.add(vBox, 1, 5 + j);


        }

        // Labels for Sum and Bonus textfields
        Label label1 = new Label("Sum");
        Label label2 = new Label("Bonus");
        Label label3 = new Label("Total");
        pane.add(label1, 4, 12);
        pane.add(label2, 4, 13);
        pane.add(label3, 4, 14);

        // Textfields for Sum and Bonus
        for (int k = 0; k < 3; k++)
        {
            txfPoint = new TextField();
            txfPoint.setPrefWidth(10);
            pane.add(txfPoint, 3, k + 12);
        }


        for (int i = 0; i < combinations.length; i++)
        {
            Label comboLabel = new Label(combinations[i]);
            TextField comboTextField = new TextField();
            comboTextField.setPrefWidth(60);
            comboFields[i] = comboTextField;


            // Add the label and text field to the GridPane in the same row
            pane.add(comboLabel, 0, 14 + i);
            pane.add(comboTextField, 1, 14 + i);

            comboFields[i].setOnMouseClicked(this::chooseFieldAction);

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


    private void updatePotentialScores()
    {
        Die[] dice = new Die[5];
        for (int i = 0; i < diceLabels.size(); i++)
        {
            dice[i] = new Die(Integer.parseInt(diceLabels.get(i).getText()));

        }
        yatzyCal = new YatzyResultCalculator(dice);

        for (int j = 0; j < 6; j++)
        {
            oneToSix[j].setText(String.valueOf(yatzyCal.upperSectionScore(j + 1)));

        }


        for (int i = 0; i < combinations.length; i++)
        {
            TextField comboTextField = comboFields[i];
            switch (i)
            {
                case 0:
                    comboTextField.setText(String.valueOf(yatzyCal.onePairScore()));
                    break;
                case 1:
                    comboTextField.setText(String.valueOf(yatzyCal.twoPairScore()));
                    break;
                case 2:
                    comboTextField.setText(String.valueOf(yatzyCal.threeOfAKindScore()));
                    break;
                case 3:
                    comboTextField.setText(String.valueOf(yatzyCal.fourOfAKindScore()));
                    break;
                case 4:
                    comboTextField.setText(String.valueOf(yatzyCal.smallStraightScore()));
                    break;
                case 5:
                    comboTextField.setText(String.valueOf(yatzyCal.largeStraightScore()));
                    break;
                case 6:
                    comboTextField.setText(String.valueOf(yatzyCal.fullHouseScore()));
                    break;
                case 7:
                    comboTextField.setText(String.valueOf(yatzyCal.chanceScore()));
                    break;
                case 8:
                    comboTextField.setText(String.valueOf(yatzyCal.yatzyScore()));
                    break;
            }
        }
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
                Die[] dice = new Die[5];
                for (int i = 0; i < diceLabels.size(); i++)
                {
                    dice[i] = new Die(Integer.parseInt(diceLabels.get(i).getText()));

                }

                yatzyCal = new YatzyResultCalculator(dice);
                updatePotentialScores();
                btnRoll.setDisable(true);
            }
        }
    }

    private void startNewTurn(Button btnRoll)
    {
        rollCount = 0;
        btnRoll.setDisable(false);
        remainingRolls.setText("Rolls remaining:" + (maxRolls - rollCount));

        for (CheckBox checkBox : holdCheckBoxes)
        {
            checkBox.setSelected(false);
        }
        for (TextField comboField : comboFields)
        {
            if(!comboField.isDisabled()){
                comboField.clear();
            }

        }
        for (int i = 0; i < 6; i++)
        {
            oneToSix[i].clear();
        }
    }

    private void updateTotalScore()
    {
        int totalScore = 0;

        // Calculate the total from the comboFields (assuming they store integer scores)
        for (TextField comboField : comboFields)
        {
            if (comboField.isDisabled()){
                int score = comboField.getText().isEmpty() ? 0 : Integer.parseInt(comboField.getText());
                totalScore += score;
            }
            // Parse the score from the text field, default to 0 if empty

        }

        // Set the total score text field with the calculated total
        txfPoint.setText(String.valueOf(totalScore));
    }


    public void chooseFieldAction(Event event)
    {
        TextField textField = (TextField) event.getSource();
        int clickIndex = GridPane.getRowIndex(textField);

        int index = -1; // Initialize index to track which field was clicked
        for (int i = 0; i < comboFields.length; i++)
        {
            if (comboFields[i] == textField)
            { // Compare reference to find the correct field
                index = i;
                break; // Exit the loop once found
            }
        }
        if (index != -1)
        { // If a valid index is found
            // Retrieve the score from the text field
            int score = Integer.parseInt(textField.getText());

            // Save the score in the scores array
            scores[index] = score; // Store the score for the selected combination

            // Optionally, disable the field to prevent further editing
            textField.setEditable(false);

            //optionally disable the textfield for future use.
            textField.setDisable(true);

            // Update the total score
            updateTotalScore(); // Call the method to update total score
        }

        for (int i = 0; i < oneToSix.length; i++)
        {
            if (oneToSix[i] == textField)
            { // Compare reference to find the correct field
                index = i;
                break; // Exit the loop once found
            }
        }
        if (index != -1)
        { // If a valid index is found
            // Retrieve the score from the text field
            int score = Integer.parseInt(textField.getText());

            // Save the score in the scores array
            scores[index] = score; // Store the score for the selected combination

            // Optionally, disable the field to prevent further editing
            textField.setEditable(false);

            //optionally disable the textfield for future use.
            textField.setDisable(true);

            // Update the total score
            updateTotalScore(); // Call the method to update total score
        }
    }
    }




