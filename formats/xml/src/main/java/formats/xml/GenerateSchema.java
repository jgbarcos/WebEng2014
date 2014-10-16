package formats.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

public class GenerateSchema {

	public static void main(String[] args) throws JAXBException, IOException {
		final File baseDir = new File(".");
		JAXBContext context = JAXBContext.newInstance(AddressBook.class,
				Person.class, PhoneNumber.class, PhoneType.class);
		context.generateSchema(new SchemaOutputResolver() {

			@Override
			public Result createOutput(String namespaceUri,
					String suggestedFileName) throws IOException {
				return new StreamResult(new File(baseDir, suggestedFileName));
			}
		});
	}

}
