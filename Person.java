package application;

import javafx.beans.property.SimpleStringProperty;

public  class Person {
	private final SimpleStringProperty  cName;
    private final SimpleStringProperty  sm;
    private final SimpleStringProperty  cour;
	public Person (String courseName, String symbol, String course)
	{
		this.cName = new SimpleStringProperty(courseName);
		this.sm = new SimpleStringProperty (symbol);
		this.cour = new SimpleStringProperty (course);
	}
	
	public String getCName()
	{
		return cName.get();
	}
	
	public void setCName(String courseName) {
		cName.set(courseName);
    }
	public String getSm()
	{
		return sm.get();
	}
	
	public void setSm(String symbol)
	{
		sm.set(symbol);
	}
	
	public String getCour()
	{
		return cour.get();
	}
	
	public void setCour(String course)
	{
		cour.set(course);
	}

}
