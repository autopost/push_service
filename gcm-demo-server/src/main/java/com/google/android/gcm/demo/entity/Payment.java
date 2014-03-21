package com.google.android.gcm.demo.entity;

import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.Field;
import org.eclipse.persistence.nosql.annotations.JoinField;
import org.eclipse.persistence.nosql.annotations.NoSql;


import javax.persistence.*;


/** * Created by arkadii.tetelman on 3/19/14. */
@Entity
@NoSql(dataFormat= DataFormatType.MAPPED,dataType="payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Field(name = "paymentId")
    private long paymentId;
    @Basic
    private Double amount;
    @ManyToOne
    @JoinField(name="invoiceId")
    private Invoice invoice;


    public Payment(long paymentId, Double amount) {
        this.setPaymentId(paymentId);
        this.setAmount(amount);
    }

    public Payment() {
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }


    //TODO: override ToString
}
