package com.globallogic.push_service_poc.bo;


import com.globallogic.push_service_poc.entity.Payment;
import com.globallogic.push_service_poc.entity.User;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.SupervisedTrainingElement;
import org.neuroph.core.learning.TrainingElement;
import org.neuroph.core.learning.TrainingSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.LMS;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Created by VladyslavPrytula on 3/23/14.
 */

@Service
public class PaymentPredictor {
    private int maxIterations;

    public PaymentPredictor() {
        maxIterations = 10000;
    }

    public Double predict(User user) {

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
        //TODO:throws null pointer, since paymentList can be zero
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

        return neuralNet.getOutput().get(0)*maxAmount;
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
