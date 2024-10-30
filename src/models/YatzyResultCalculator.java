package models;

/**
 * Used to calculate the score of throws with 5 dice
 */
public class YatzyResultCalculator {

    /**
     * @param dice
     */

    private Die[] dice;

    public YatzyResultCalculator(Die[] dice) {
        //TODO: implement YatzyResultCalculator constructor.
        this.dice = dice;
    }

    /**
     * Calculates the score for Yatzy uppersection
     *
     * @param eyes eye value to calculate score for. eyes should be between 1 and 6
     * @return the score for specified eye value
     */
    public int upperSectionScore(int eyes) {
        //TODO: Implement upperSectionScore method.
        // Denne metode beregner pointene for øjnene i øverste sektion (1’ere, 2’ere osv.).
        int score = 0;
        for (Die die : dice) {
            if (die.getValue() == eyes) {
                score += eyes;
            }
        }
        return score;
    }

    public int onePairScore() {
        //TODO: implement onePairScore method.
        // Her finder vi den højeste værdi, der forekommer mindst to gange. Returner summen af de to terninger, ellers 0 hvis der ikke er par.
        int[] counts = new int[6];
        for (Die die : dice) {
            counts[die.getValue() - 1]++;
        }
        for (int i = 5; i >= 0; i--) {
            if (counts[i] >= 2) {
                return (i + 1) * 2;
            }
        }
        return 0;
    }

    public int twoPairScore() {
        //TODO: implement twoPairScore method.
        // Her finder vi to forskellige værdier, der hver forekommer mindst to gange.
        int[] counts = new int[6];
        int score = 0;
        int pairs = 0;
        for (Die die : dice) {
            counts[die.getValue() - 1]++;
        }
        for (int i = 5; i >= 0; i--) {
            if (counts[i] >= 2) {
                score += (i + 1) * 2;
                pairs++;
                if (pairs == 2) return score;
            }
        }
        return 0;
    }

    public int threeOfAKindScore() {
        //TODO: implement threeOfAKindScore method.
        // Med disse metoder finder vi tre eller fire ens terninger og returnerer summen.
        int[] counts = new int[6];
        for (Die die : dice) {
            counts[die.getValue() - 1]++;
        }
        for (int i = 5; i >= 0; i--) {
            if (counts[i] >= 3) {
                return (i + 1) * 3;
            }
        }
        return 0;
    }

    public int fourOfAKindScore() {
        //TODO: implement fourOfAKindScore method.
        int[] counts = new int[6];
        for (Die die : dice) {
            counts[die.getValue() - 1]++;
        }
        for (int i = 5; i >= 0; i--) {
            if (counts[i] >= 4) {
                return (i + 1) * 4;
            }
        }
        return 0;
    }

    public int smallStraightScore() {
        //TODO: implement smallStraightScore method.
        // Her tjekker vi om terningekastet har rækkefølgerne 1-5 eller 2-6 for henholdsvis Small og Large Straight.
        int[] counts = new int[6];
        for (Die die : dice) {
            counts[die.getValue() - 1]++;
        }
        return (counts[0] == 1 && counts[1] == 1 && counts[2] == 1 && counts[3] == 1 && counts[4] == 1) ? 15 : 0;
    }

    public int largeStraightScore() {
        //TODO: implement largeStraightScore method.
        int[] counts = new int[6];
        for (Die die : dice) {
            counts[die.getValue() - 1]++;
        }
        return (counts[1] == 1 && counts[2] == 1 && counts[3] == 1 && counts[4] == 1 && counts[5] == 1) ? 20 : 0;
    }

    public int fullHouseScore() {
        //TODO: implement fullHouseScore method.
        // Her tjekker vi om der er en kombination af tre ens og to ens. Hvis ja, returneres summen af disse terninger.
        int[] counts = new int[6];
        int twoOfAKind = 0, threeOfAKind = 0;
        for (Die die : dice) {
            counts[die.getValue() - 1]++;
        }
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] == 3) threeOfAKind = (i + 1) * 3;
            if (counts[i] == 2) twoOfAKind = (i + 1) * 2;
        }
        return (twoOfAKind > 0 && threeOfAKind > 0) ? twoOfAKind + threeOfAKind : 0;
    }

    public int chanceScore() {
        //TODO: implement chanceScore method.
        // Her returneres summen af alle terninger
        int sum = 0;
        for (Die die : dice) {
            sum += die.getValue();
        }
        return sum;
    }

    public int yatzyScore() {
        //TODO: implement yatzyScore method.
        // Hvis alle terninger viser det samme, returneres 50, ellers rertuneres 0
        int firstValue = dice[0].getValue();
        for (Die die : dice) {
            if (die.getValue() != firstValue) {
                return 0;
            }
        }
        return 50;
    }
}