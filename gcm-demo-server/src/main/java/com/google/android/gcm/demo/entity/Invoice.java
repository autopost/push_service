package com.google.android.gcm.demo.entity;

import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.sql.Date;

/**
 * Created by arkadii.tetelman on 3/13/14.
 */

@Entity
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "invoiceId")
	long invoiceId;

	@Column
	String invoiceName;
	@Column
	Double invoiceAmount;
	@Column
	Date invoiceSubmitedTS;
	@Column
	Date invoiceCompletedTS;
	@Column
	User user;

	public Invoice() {
	}

	public Invoice(long invoiceId, String invoiceName, Double invoiceAmount,
			Date invoiceSubmitedTS, Date invoiceCompletedTS, User user) {
		this.invoiceId = invoiceId;
		this.invoiceName = invoiceName;
		this.invoiceAmount = invoiceAmount;
		this.invoiceSubmitedTS = invoiceSubmitedTS;
		this.invoiceCompletedTS = invoiceCompletedTS;
		this.user = user;
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
}
