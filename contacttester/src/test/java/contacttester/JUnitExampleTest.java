package contacttester;

import org.junit.Test;

import obj.Contact;
import obj.ContactDAO;

public class JUnitExampleTest {
	
	@Test
	public void test1()
	{
	//testing functionality
	Contact contact1 = new Contact("Sub Zero","Lin Kuei", "Earthrealm", "002210255", "Cold@Temp.co.uk");
	Contact contact2 = new Contact("Scorpion","", "Earthrealm", "66600", "Over@Here.com");
	Contact contact3 = new Contact("Baraka","", "Outworld", "750", "@fang.com");//will fail validation
	Contact contact4 = new Contact("Reptile","Kombat", "Outworld", "f4452", "snake@slime.");//will fail validation
	Contact contact5 = new Contact("Dvorah","Elder Gods", "Outworld", "7745555", "bugs@bigs.com.lb");
	Contact contact6 = new Contact("Johnny Cage","Humans", "Earthrealm", "294442", "money@bags.com");
	Contact contact7 = new Contact("Quan Chi","Elder Gods", "Netherrealm", "22356", "Magic@mage.com");

	ContactDAO dao = new ContactDAO();
	//test validation
	System.out.println(contact1.getName()+" validation: "+dao.validate(contact1));
	System.out.println(contact2.getName()+" validation: "+dao.validate(contact2));
	System.out.println(contact3.getName()+" validation: "+dao.validate(contact3));
	System.out.println(contact4.getName()+" validation: "+dao.validate(contact4));
	//test conversion to Json String
	System.out.println(contact7.getName()+" as JsonString:"+dao.toJsonString(contact7));
	//test for elasticsearch:
	//post data
	System.out.println(contact1.getName()+" post results:"+dao.elasticPost(contact1));
	System.out.println(contact2.getName()+" post results:"+dao.elasticPost(contact2));
	System.out.println(contact6.getName()+" post results:"+dao.elasticPost(contact6));
	//get data
	System.out.println(contact1.getName()+" get results:"+dao.elasticGet(contact1.getName()));
	System.out.println(contact6.getName()+" get results:"+dao.elasticGet(contact6.getName()));
	//check exists
	System.out.println(contact5.getName()+" exists results:"+dao.elasticExists(contact5.getName()));
	System.out.println(contact2.getName()+" exists results:"+dao.elasticExists(contact2.getName()));
	//put data
	contact6.setCompany_name("Hollywood");
	contact6.setPhone_num("");
	System.out.println(contact6.getName()+" put results:"+dao.elasticPut(contact6));
	System.out.println(contact6.getName()+" get results:"+dao.elasticGet(contact6.getName()));
	//get query
	String query = "\"query\" : {\"query_string\" : { \"default_field\" : \"address\","+"\"query\" : \"(Earthrealm*) \""+"}}";
	System.out.println("pageSize to 5, and page to 0, queryStringQuery: "+query);
	System.out.println("results of query: "+dao.elasticGet("5","0",query));
	System.out.println("Change pageSize to 2, and page to 1");
	System.out.println("results of query: "+dao.elasticGet("2","1",query));
	//delete
	System.out.println(contact6.getName()+" delete results:"+dao.elasticDelete(contact6.getName()));
	System.out.println(contact6.getName()+" exists results:"+dao.elasticExists(contact6.getName()));
	//need to test the ContactServices
	
	
	}
}
