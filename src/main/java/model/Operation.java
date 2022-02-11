package model;
import javafx.util.Pair;
import org.tinylog.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * {@code Operation} MMűveletek összerakását, illetve elvégzését szolgáló osztály.
 */
public class Operation {
        private int leftoperand;
        private int rightoperand;
        private OperandSymbols operandsymbol;
        private int result;

        public Operation(OperandSymbols operationsymbol) {
            Random random = new Random();
            this.leftoperand = random.nextInt(10);
            this.rightoperand = random.nextInt(10);
            this.operandsymbol = calculate(leftoperand, rightoperand, operationsymbol).getValue();
            this.result=calculate(leftoperand, rightoperand, this.operandsymbol).getKey();
            Logger.info("Létrejön egy művelet");

        }


    public Pair<Integer,OperandSymbols> calculate(int leftoperand, int rightoperand, OperandSymbols operandsymbol) {
            int returnvalue = 0;
            OperandSymbols operandSymbols;
            Pair<Integer,OperandSymbols> returnValues = new Pair<>(null, null);
            Logger.info("Elvégződik egy művelet");
            switch (operandsymbol) {
                case SUM:
                    returnvalue = leftoperand + rightoperand;
                    return new Pair<>(returnvalue, operandsymbol);
                case SUBTRACTION:
                    returnvalue = leftoperand - rightoperand;
                    return new Pair<>(returnvalue, operandsymbol);
                case MULTIPLY:
                    returnvalue = leftoperand * rightoperand;
                    return new Pair<>(returnvalue, operandsymbol);
                case ALLOPERATION:
                    Random rand = new Random();
                    int random = rand.nextInt(3);
                    OperandSymbols tempsymbol = OperandSymbols.values()[random];
                    System.out.println(tempsymbol);
                    returnValues = calculate(leftoperand, rightoperand, tempsymbol);
                    return new Pair<>(returnValues.getKey(), returnValues.getValue());
                default:
                    throw new IllegalStateException("Unexpected value: " + operandsymbol);
            }
        }

    /**
     * {@code getResult} getter metódus.
     * @return Az eredményt adja vissza.
     */
        public int getResult() {
            return result;
        }


    public OperandSymbols getOperandsymbol() { return operandsymbol; }

    public String getOperationAsText(OperandSymbols operandsymbol){

            String operationText = "";
            switch (operandsymbol) {
                case SUM:
                    operationText = "Mennyi " + Integer.toString(leftoperand) + " + " + Integer.toString(rightoperand) + "?";
                    break;
                case SUBTRACTION:
                    operationText = "Mennyi " + Integer.toString(leftoperand) + " - " + Integer.toString(rightoperand) + "?";
                    break;
                case MULTIPLY:
                    operationText = "Mennyi " + Integer.toString(leftoperand) + " * " + Integer.toString(rightoperand) + "?";
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + operandsymbol);
            }
            return operationText;
        }

}
