package obj;

public class Contact 
{
	private String name;
	private String company_name;
	private String address;
	private String phone_num;
	private String email;
	
	public Contact()
	{
		this.name = this.address = this.email = this.company_name = this.phone_num= "";
	}
	
	public Contact(String name,String com,String add,String phone, String mail)
	{
		//in case it is sent null we want the values to be empty sting
		if (name!=null)
		{
			setName(name);
		}
		else
		{
			setName("");
		}
		
		if(com !=null)
		{
			setCompany_name(com);
		}
		else
		{
			setCompany_name("");
		}
		
		if(add!=null)
		{
			setAddress(add);	
		}
		else
		{
			setAddress("");
		}
		
		if(phone!=null)
		{
			setPhone_num(phone);
		}
		else
		{
			setPhone_num("");
		}
		
		if(mail!=null)
		{
			setEmail(mail);
		}
		else
		{
			setEmail("");
		}
		
	}

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
