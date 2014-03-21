package com.google.android.gcm.demo.entity;

import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.Field;
import org.eclipse.persistence.nosql.annotations.JoinField;
import org.eclipse.persistence.nosql.annotations.NoSql;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by VladyslavPrytula on 3/21/14.
 */
@Embeddable
@NoSql(dataFormat= DataFormatType.MAPPED,dataType="payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Field(name = "paymentId")
    long paymentId;

    @Basic
    private String paymentNumber;
    @Basic
    private Double paymentAmaunt;
    @Basic
    @Temporal(TemporalType.DATE)
    private Date debitDate;
    @Basic
    @Temporal(TemporalType.DATE)
    private Date creditDate;




    public Payment() {
    }


    //TODO: override ToString

}
