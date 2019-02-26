package service;

import static spark.Spark.*;


import obj.Contact;
import obj.ContactDAO;


public class ContactService {

	public static void main(String[] args) 
	{
		ContactDAO dao = new ContactDAO();
		
		get("/contact", (req, res)->
				{
//					GET/contact?pageSize={}&page={}&query={}
//					pageSize is max contacts per page
//					page is the current page number
//					query is a query in queryStringQuery (for Elasticsearch)
					 String pageSize = req.queryParams("pageSize");
					 String page = req.queryParams("page");
					 String query = req.queryParams("query");
					 //set default values
					 if(pageSize == null||pageSize.length()==0)
					 {
						 pageSize = "5";
					 }
					 if(page == null||page.length()==0)
					 {
						 page = "0";
					 }
					 if(query == null||query.length()==0)
					 {
						 //will get all contacts
						 query = "\"query\" : {\"query_string\" : { \"default_field\" : \"name\","+
						            "\"query\" : \"(*) \"}}";
					 }
					 
					 res.body(dao.elasticGet(pageSize,page,query)); 
				return res;
				});
		
		

		post("/contact", (req, res)->
		{//		POST/contact
//			Create contact but name should be unique
			Contact newContact = dao.fillContact(req.body());
			//validate new contact
			String val = dao.validate(newContact);
			if (val.length()>0)
			{
				res.body(val);
				res.status(400);
			}
			else
			{
			//use elasticsearch
			//check that there isn't an existing contact with same name
				if(dao.elasticExists(newContact.getName()))
				{
					res.body(newContact.getName()+ " already exists!");
					res.status(400);
				}
				else
				{
				//use elasticsearch to insert contact
					res.body(dao.elasticPost(newContact));
				}
			}
		return res;
		});
		

		get("/contact/:name", (req, res)->
		{//		GET/contact/{name}
//			gets contact based on unique name
			String name = req.params(":name");
			res.type("application/json");
			//use elasticsearch to get find contact
			res.body(dao.elasticGet(name)) ;	
			if(!res.body().contains("\"found\":true"))
			{
				res.status(404);
			}
		return res;
		});
		

		put("/contact/:name", (req, res)->
		{//		PUT/contact/{name}
//			update a contact based on name
			Contact newContact = dao.fillContact(req.body());
			
			//validate new contact
			String val = dao.validate(newContact);
			if (val.length()>0)
			{
				res.body(val);
				res.status(400);
			}
			else
			{
			//use elasticsearch
			//check that there is an existing contact to update
				if(dao.elasticExists(newContact.getName()))
				{
					//use elasticsearch to update contact
					res.body(dao.elasticPut(newContact));
				}
				else
				{
					res.body(newContact.getName()+ " doesn't exists!");
					res.status(404);
				}
			
			}
			return res;
		});
		

		delete("/contact/:name", (req, res)->
		{//		DELETE/contact/{name}
//			deletes contact based on name
			String name = req.params(":name");
			
			//use elasticsearch
			//check that there is an existing contact
			if(dao.elasticExists(name))
			{
				//use elasticsearch to delete contact
				dao.elasticDelete(name);
			}
			else
			{
				res.body(name+ " doesn't exists!");
				res.status(404);
			}
		return res;
		});
		
	}
	
}
