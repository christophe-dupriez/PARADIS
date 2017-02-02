package be.labo.data;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.cayenne.DataObject;

public interface NamedObject extends DataObject, Comparable<NamedObject> {
	   /** Read-only access to PK */
	   public Integer getPK();

	   public void setName(String language, String name);
	   
	   public LinkedHashMap<String,String> getNamesMap();
	   
	   public String getBestName(HttpServletRequest request);
	   
}
