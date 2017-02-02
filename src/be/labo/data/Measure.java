package be.labo.data;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;

import be.labo.auxil.AuxilServlet;
import be.labo.data.auto._Measure;

public class Measure extends _Measure implements NamedObject {

	   /** Read-only access to PK */
	   public Integer getPK() {
		   return (int)DataObjectUtils.longPKForObject(this);
	   }

	   public void setName(String language, String name) {
		   if (language ==  null) language = ""; // empty string if no language specified
		   NameMeasure aName = this.findName(language);
		   if (aName != null) {
			   ObjectContext context = aName.getObjectContext();
			   if (name == null || name.isEmpty()) { // no name, delete it! 
				   context.deleteObject(aName);
				   context.commitChanges();
			   } else {
				   if (name != aName.getName()) {
					   aName.setName(name);
					   aName.setSince(new Date());
					   context.commitChanges();
				   }
			   }
		   } else { // no existing name
			   if (name != null && !name.isEmpty()) {
				   ObjectContext context = this.getObjectContext();
				   aName = context.newObject(NameMeasure.class);
				   if (aName != null) {
					   aName.setLanguage(language);
					   aName.setName(name);
					   aName.setSince(new Date());
					   aName.setNameMeasure(this);
					   context.commitChanges();
				   }
			   }
		   }
		   
	   }

	   public NameMeasure findName(String language) {
		   if (language == null) return null;

		   ObjectContext context = this.getObjectContext();
		   List<Expression> list = new ArrayList<Expression>();
		   list.add(ExpressionFactory.matchExp(NameMeasure.NAME_MEASURE_PROPERTY, 
				   this.getPK()));
		   list.add(ExpressionFactory.matchExp(NameMeasure.LANGUAGE_PROPERTY, 
				   language));
		   SelectQuery query = new SelectQuery(NameMeasure.class,ExpressionFactory.joinExp(Expression.AND, list));
		   List<NameMeasure> names = context.performQuery(query);
		   if (names == null || names.isEmpty()) return null;
		   return names.get(0);   
	   }
	   @Override
	   public LinkedHashMap<String,String> getNamesMap() {
		   return AuxilServlet.getNamesMap(this, NameMeasure.class, NameMeasure.NAME_MEASURE_PROPERTY);
	   }

	   @Override
	   public String getBestName(HttpServletRequest request) {
		   return AuxilServlet.chooseName(request, this.getMeasureName());
	   }
	   
		@Override
		public int compareTo(NamedObject arg0) {
			// TODO Auto-generated method stub
			if (arg0 == null) throw new NullPointerException();
			if (arg0.getClass() != Measure.class) throw new InvalidParameterException();
			return this.getPK()-arg0.getPK();
		}

}
