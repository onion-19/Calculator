package edu.calculator.entry;

//import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOpt;
//import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.IllegalArgumentException;
//import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.MissingOptArgException;

import edu.calculator.entity.NumberType;
import edu.calculator.entity.TypeSelector;
import edu.calculator.getopt.GetOpt;
import edu.calculator.getopt.IllegalArgumentException;
import edu.calculator.utils.Comparator;
import edu.calculator.utils.FileOperator;
import edu.calculator.utils.Generator;

import java.util.List;

public class Entry {
    public static void main(String[] args) {
        GetOpt getOpt = new GetOpt(args, "n:r:e:a:");
        int param;
        int sum = -1;
        Integer range = -1;
        String exercisefile = null, answerfile = null;
        try {
            while(-1 != (param = getOpt.getNextOption())) {
                switch(param) {
                    case 'n':
                        sum = Integer.parseInt(getOpt.getOptionArg());
                        if(sum <= 0) {
                            throw new NumberFormatException();
                        }
                        break;
                    case 'r':
                        range = Integer.parseInt(getOpt.getOptionArg());
                        if(range < 1) {
                            throw new NumberFormatException();
                        }
                        break;
                    case 'e':
                        exercisefile = getOpt.getOptionArg();
                        break;
                    case 'a':
                        answerfile = getOpt.getOptionArg();
                        break;
                    default:
                        //TODO 提示帮助信息
                        break;
                }
            }
            if(range != -1 && sum != -1) {
                TypeSelector type = new TypeSelector(range, sum);
                Generator generator = new Generator();
                List<String> exercisesList = generator.exercisesGenerator(type);
                List<String> answersList = generator.getAnswersList();
                FileOperator.writeFile(exercisesList, answersList);
            }
            if(exercisefile != null && answerfile != null) {
                List<NumberType> correctAns = FileOperator.readExercises(exercisefile);
                List<NumberType> ans = FileOperator.readAnswers(answerfile);
                if(correctAns == null || ans == null) {
                    System.exit(0);
                } else {
                    Comparator.compare(correctAns, ans);
                }
            }
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        } catch(NumberFormatException e) {
            e.printStackTrace();
            e.printStackTrace();
        }
    }

}
