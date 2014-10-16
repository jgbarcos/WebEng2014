package formats.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;

public class Person {

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<PhoneNumber> getPhoneList() {
		return phoneList;
	}

	public void setPhoneList(List<PhoneNumber> phones) {
		this.phoneList = phones;
	}

	private String name;
	private int id;
	private String email;
	private List<PhoneNumber> phoneList = new ArrayList<PhoneNumber>();

	public void addPhone(PhoneNumber phone) {
		phoneList.add(phone);
	}

	public boolean hasEmail() {
		return email != null;
	}

}
