package com.google.android.gcm.demo.bo;

import com.google.android.gcm.demo.custqualifiers.Loggable;
import com.google.android.gcm.demo.entity.Payment;
import com.google.android.gcm.demo.entity.User;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.SupervisedTrainingElement;
import org.neuroph.core.learning.TrainingElement;
import org.neuroph.core.learning.TrainingSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.LMS;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by VladyslavPrytula on 3/23/14.
 */
@Loggable
public class PaymentPredictor {
    private int maxIterations;

    public PaymentPredictor() {
        maxIterations = 10000;
    }

    public void predict(User user) {

         NeuralNetwork neuralNet = new MultiLayerPerceptron(4, 9, 1);
        ((LMS) neuralNet.getLearningRule()).setMaxError(0.001);//0-1
        ((LMS) neuralNet.getLearningRule()).setLearningRate(0.7);//0-1
        ((LMS) neuralNet.getLearningRule()).setMaxIterations(maxIterations);//0-1

        TrainingSet trainingSet = new TrainingSet();

//        To find the max value of Payment : maxPayment = max(days [k], k =0, days.length-1))
//        To calculate normalized values:
//        paymentNorm [i] = (days [i] [3] / maxPayment)*0.8+0.1, where 0.8 and 0.1 will be used to avoid the very small (0.0...) and very big (0.9999) values.
//        We have carried out a simplification, have simply divided on 10000.
       // double daxmax = 10000.0D;

        List<Payment> paymentList = user.getPaymentList();
        Double maxAmount = computeMaxPaymentAmount(paymentList);

        for(int i=0; i<paymentList.size()-5; i++){
            trainingSet.addElement(new SupervisedTrainingElement(new double[]{
                    paymentList.get(i).getAmount() / maxAmount,
                    paymentList.get(i+1).getAmount() / maxAmount,
                    paymentList.get(i+2).getAmount() / maxAmount,
                    paymentList.get(i+3).getAmount() / maxAmount},
                    new double[]{paymentList.get(i+4).getAmount() / maxAmount}));
        }

        neuralNet.learnInSameThread(trainingSet);
        System.out.println("Time stamp N2:" + new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:MM").format(new Date()));

        TrainingSet testSet = new TrainingSet();
        testSet.addElement(new TrainingElement(new double[]{paymentList.get(paymentList.size()-4).getAmount()/ maxAmount,
                paymentList.get(paymentList.size()-3).getAmount()/ maxAmount,
                paymentList.get(paymentList.size()-2).getAmount()/ maxAmount,
                paymentList.get(paymentList.size()-1).getAmount()/ maxAmount}));

        for (TrainingElement testElement : testSet.trainingElements()) {
            neuralNet.setInput(testElement.getInput());
            neuralNet.calculate();
            Vector<Double> networkOutput = neuralNet.getOutput();
            System.out.print("Input: " + testElement.getInput());
            System.out.println(" Output: " + networkOutput);
        }

        System.out.println("Time stamp N3:" + new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:MM").format(new Date()));
        System.exit(0);

    }

    private Double computeMaxPaymentAmount(List<Payment> paymentList){
        Double max = 0d;
        for (Payment pmnt:paymentList){
            if (pmnt.getAmount()>= max){
                max = pmnt.getAmount();
            }
        }
        return max;
    }

    private Double computeMinPaymentAmount(Date startDate, Date endDate){
        return 1.0d;
    }

}
