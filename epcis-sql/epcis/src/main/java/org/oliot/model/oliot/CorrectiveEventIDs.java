//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.10.10 at 02:39:29 PM KST 
//

package org.oliot.model.oliot;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for CorrectiveEventIDsType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="CorrectiveEventIDsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="correctiveEventID" type="{urn:epcglobal:epcis:xsd:1}EventIDType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CorrectiveEventIDsType", propOrder = { "correctiveEventID" })
@Entity
public class CorrectiveEventIDs {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="correctiveeventids_seq")
	//@SequenceGenerator(name="correctiveeventids_seq", sequenceName="correctiveeventids_seq", allocationSize=1)
	private int id;
	
	//@ElementCollection
	@OneToMany
	@JoinTable(name ="CorrectiveIDs_correctiveID")
	protected List<CorrectiveEventID> correctiveEventID;

	public List<CorrectiveEventID> getCorrectiveEventID() {
		return correctiveEventID;
	}

	public void setCorrectiveEventID(List<CorrectiveEventID> correctiveEventID) {
		this.correctiveEventID = correctiveEventID;
	}

	/**
	 * Gets the value of the correctiveEventID property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the correctiveEventID property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getCorrectiveEventID().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link String }
	 * 
	 * 
	 */
	

}