package formats.xml;

import static formats.xml.PhoneType.*;

import javax.xml.bind.annotation.XmlElement;

public class PhoneNumber {

	private String number;
	private PhoneType type = HOME;

	@XmlElement(name = "phoneNumber")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public PhoneType getType() {
		return type;
	}

	public void setType(PhoneType type) {
		this.type = type;
	}

}
