package obj;

import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

public class ContactDAO 
{
	
		public String validate(Contact contact)
		{//This function will check if there are any issues with the data
			//it will return a string so we can put it in the body of the
			//response so the user can get an idea of why it failed
			//if string is empty then there was no errors
			String result = "";
			
			Pattern digitPattern = Pattern.compile("(.*)(\\d+)(.*)");
			Pattern charsPattern = Pattern.compile("(.*)(\\D+)(.*)");
			Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9+_-]+(.[a-z]+)+$");
			//Name shouldn't be empty or have numbers 
			if(contact.getName().replaceAll(" ","").length()!=0)
			{
				if(digitPattern.matcher(contact.getName()).matches())
				{
					result += "Name can't contain numbers!\n";
				};
			}
			else
			{
				result += "Name can't be empty!\n";
			}
			
			//Company can contain anything, even empty so no checks done
			
			//Address can be contain anything but since this is an address book, it should contain something
			if (contact.getAddress().length() == 0)
			{
					result += "Address can't be empty!\n";
			}
			
			//Phone Numbers can be a max length of 15 and min 5, but only digits. Can be empty
			if (contact.getPhone_num().length()>15||contact.getPhone_num().length()<5)
			{
				result += "Phone numbers must be between 5 and 15 digits long!\n";
			}
			else if (contact.getPhone_num().length() != 0)
			{
				if(charsPattern.matcher(contact.getPhone_num()).matches())
				{
					result += "Phone numbers can only contain digits!\n";
				};
			}
			
			//Email can be empty but if not, should be of the format x@y.z (with .z being repeated ex: .co.uk) 
			if (contact.getEmail().length() != 0)
			{
				if(!emailPattern.matcher(contact.getEmail()).matches())
				{
					result += "Invalid Email format!\n";
				};
			}
			
			return result;
		}
		
		public String toJsonString(Contact contact)
		{
			return "{" +
        "\"name\":\""+contact.getName()+"\"," +
        "\"company\":\""+contact.getCompany_name()+"\"," +
        "\"address\":\""+contact.getAddress()+"\"," +
        "\"phone_number\":\""+contact.getPhone_num()+"\"," +
        "\"email\":\""+contact.getEmail()+"\"" +
        "}";
		}
		
		public String elasticGet(String contact)
		{
			String resp = "";
			try {
				RestClient restClient = RestClient.builder(
				    new HttpHost(ElasticSearchConfig.HOST1, ElasticSearchConfig.PORT1, ElasticSearchConfig.SCHEME1),
				    new HttpHost(ElasticSearchConfig.HOST2, ElasticSearchConfig.PORT1, ElasticSearchConfig.SCHEME2)).build();
			
					Request request = new Request("GET","/addressbook/contact/"+contact.replaceAll(" ", "_"));   
					
					Response response = restClient.performRequest(request);
					String responseBody = EntityUtils.toString(response.getEntity()); 
					resp = responseBody;
					restClient.close();	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					resp = e.getMessage();
				}			
			return resp;
		}
	
		public String elasticGet(String pageSize,String page, String query)
		{
			String resp = "";
			try {
				RestClient restClient = RestClient.builder(
					new HttpHost(ElasticSearchConfig.HOST1, ElasticSearchConfig.PORT1, ElasticSearchConfig.SCHEME1),
					new HttpHost(ElasticSearchConfig.HOST2, ElasticSearchConfig.PORT1, ElasticSearchConfig.SCHEME2)).build();
					
					Request request = new Request("POST","/addressbook/contact/_search");   
					
					HttpEntity entity = new NStringEntity("{ \"from\" : "+page+", \"size\" : "+pageSize+","+query+"}", ContentType.APPLICATION_JSON);
					request.setEntity(entity);
					Response response = restClient.performRequest(request);
					String responseBody = EntityUtils.toString(response.getEntity()); 
					resp = responseBody;
					restClient.close();	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					resp = e.getMessage();
				}		
			return resp;
		}
		
		public boolean elasticExists(String contact)
		{
			boolean resp = true;
			try 
			{
				if(!elasticGet(contact).contains("\"found\":true"))
				{
					resp = false;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}			
			return resp;
		}
		
		public String elasticPost(Contact contact)
		{
			String resp = "";
			try {
				RestClient restClient = RestClient.builder(
					new HttpHost(ElasticSearchConfig.HOST1, ElasticSearchConfig.PORT1, ElasticSearchConfig.SCHEME1),
					new HttpHost(ElasticSearchConfig.HOST2, ElasticSearchConfig.PORT1, ElasticSearchConfig.SCHEME2)).build();
					
					Request request = new Request("POST","/addressbook/contact/"+contact.getName().replaceAll(" ", "_"));   
					
					String jsonString = toJsonString(contact);
					HttpEntity entity = new NStringEntity(jsonString, ContentType.APPLICATION_JSON);
					request.setEntity(entity);
					Response response = restClient.performRequest(request);
					String responseBody = EntityUtils.toString(response.getEntity()); 
					resp = responseBody;
					restClient.close();	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					resp = e.getMessage();
				}			
			return resp;
		}

		public String elasticPut(Contact contact)
		{
			String resp = "";
			try {
				RestClient restClient = RestClient.builder(
					new HttpHost(ElasticSearchConfig.HOST1, ElasticSearchConfig.PORT1, ElasticSearchConfig.SCHEME1),
					new HttpHost(ElasticSearchConfig.HOST2, ElasticSearchConfig.PORT1, ElasticSearchConfig.SCHEME2)).build();
					
					Request request = new Request("PUT","/addressbook/contact/"+contact.getName().replaceAll(" ", "_"));   
					
					String jsonString = toJsonString(contact);
					HttpEntity entity = new NStringEntity(jsonString, ContentType.APPLICATION_JSON);
					request.setEntity(entity);
					Response response = restClient.performRequest(request);
					String responseBody = EntityUtils.toString(response.getEntity()); 
					resp = responseBody;
					restClient.close();	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					resp = e.getMessage();
				}			
			return resp;
		}

		public String elasticDelete(String contact)
		{
			String resp = "";
			try {
				RestClient restClient = RestClient.builder(
					new HttpHost(ElasticSearchConfig.HOST1, ElasticSearchConfig.PORT1, ElasticSearchConfig.SCHEME1),
					new HttpHost(ElasticSearchConfig.HOST2, ElasticSearchConfig.PORT1, ElasticSearchConfig.SCHEME2)).build();
					
					Request request = new Request("DELETE","/addressbook/contact/"+contact.replaceAll(" ", "_"));   
					
					Response response = restClient.performRequest(request);
					String responseBody = EntityUtils.toString(response.getEntity()); 
					resp = responseBody;
					restClient.close();	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					resp = e.getMessage();
				}			
			return resp;
		}
	
}
