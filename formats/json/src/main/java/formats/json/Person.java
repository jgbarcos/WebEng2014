package formats.json;

import java.util.ArrayList;
import java.util.List;

public class Person {

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public String name;
	public int id;
	public String email;
	public List<PhoneNumber> phoneList = new ArrayList<PhoneNumber>();

	public void addPhone(PhoneNumber phone) {
		phoneList.add(phone);
	}

	public boolean hasEmail() {
		return email != null;
	}

}
