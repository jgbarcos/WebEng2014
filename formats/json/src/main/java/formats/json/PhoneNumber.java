package formats.json;

import static formats.json.PhoneType.*;

public class PhoneNumber {

	public String number;
	public PhoneType type = HOME;

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
