package com.globallogic.push_service_poc.entity;

import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.Field;
import org.eclipse.persistence.nosql.annotations.JoinField;
import org.eclipse.persistence.nosql.annotations.NoSql;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by arkadii.tetelman on 3/13/14.
 */

@Entity
@NoSql(dataFormat= DataFormatType.MAPPED,dataType="invoice")
public class Invoice {

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Field(name = "invoiceId")
	private long invoiceId;

	@Basic
	private String invoiceName;
    @Basic
    private Double invoiceAmount;
    @Basic
    @Temporal(TemporalType.DATE)
    private Date invoiceSubmitedTS;
    @Basic
    @Temporal(TemporalType.DATE)
    private Date invoiceCompletedTS;
    @ManyToOne
    @JoinField(name="userId")
    private User user;
    @OneToMany
    @JoinField(name = "paymentId")
    private List<Payment> paymentList;


	public Invoice() {
	}

	public Invoice(long invoiceId, String invoiceName, Double invoiceAmount,
			Date invoiceSubmitedTS, Date invoiceCompletedTS, User user) {
		this.invoiceId = invoiceId;
		this.invoiceName = invoiceName;
		this.invoiceAmount = invoiceAmount;
		this.invoiceSubmitedTS = invoiceSubmitedTS;
		this.invoiceCompletedTS = invoiceCompletedTS;
		this.setUser(user);
	}




    public long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}

	public Double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public Date getInvoiceSubmitedTS() {
		return invoiceSubmitedTS;
	}

	public void setInvoiceSubmitedTS(Date invoiceSubmitedTS) {
		this.invoiceSubmitedTS = invoiceSubmitedTS;
	}

	public Date getInvoiceCompletedTS() {
		return invoiceCompletedTS;
	}

	public void setInvoiceCompletedTS(Date invoiceCompletedTS) {
		this.invoiceCompletedTS = invoiceCompletedTS;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


    //TODO: override ToString
}
