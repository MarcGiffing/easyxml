package com.giffing.easyxml.jaxb.example.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbSpecialNote implements ParseNote {

	private Long id;

	private String specialContent;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSpecialContent() {
		return specialContent;
	}

	public void setSpecialContent(String specialContent) {
		this.specialContent = specialContent;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlAttribute(name = "type")
	private String type;

}
