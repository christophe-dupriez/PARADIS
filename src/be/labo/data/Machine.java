package be.labo.data;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.ObjectId;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.ObjectIdQuery;
import org.apache.cayenne.query.SelectQuery;

import be.labo.auxil.AuxilServlet;
import be.labo.auxil.FlotSymbol;
import be.labo.auxil.LatLongElev;
import be.labo.auxil.WebColor;
import be.labo.data.auto._Machine;

public class Machine extends _Machine implements NamedObject {

	/** Read-only access to PK */
	public Integer getPK() {
		return DataObjectUtils.intPKForObject(this);
	}
	/*	   
	   public String getHexMacmsb() {
		   if (this.getMacmsb() == null) return "";
		   return Integer.toString(this.getMacmsb(),16);
	   }
	 */
	public String getHexMachineMAC() {
		if (this.getMachinemac() == null) return "";
		return Integer.toString(this.getMachinemac(),16);
	}
	/*
	 * select measure.id
   from measure
   where ($machinekey=measure.machine) AND ($timestamp=measure.whenmeasured)
	 */
	public List<Measure> findMeasuresBy(Date timestamp) {
		Integer machineKey = this.getPK();
		if (machineKey == null) return null;

		ObjectContext context = this.getObjectContext();
		List<Expression> list = new ArrayList<Expression>();
		list.add(ExpressionFactory.matchExp(Measure.MEASURE_MACHINE_PROPERTY, machineKey));
		list.add(ExpressionFactory.matchExp(Measure.WHENMEASURED_PROPERTY, timestamp));
		SelectQuery queryMeasures = new SelectQuery(Measure.class,ExpressionFactory.joinExp(Expression.AND, list));
		List<Measure> measures = context.performQuery(queryMeasures);
		if (measures == null || measures.isEmpty()) return null;
		return measures;   
	}


	public void setName(String language, String name) {
		if (language ==  null) language = ""; // empty string if no language specified
		NameMachine aName = this.findName(language);
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
				aName = context.newObject(NameMachine.class);
				if (aName != null) {
					aName.setLanguage(language);
					aName.setName(name);
					aName.setSince(new Date());
					aName.setNameMachine(this);
					context.commitChanges();
				}
			}
		}   
	}

	public NameMachine findName(String language) {
		if (language == null) return null;

		ObjectContext context = this.getObjectContext();
		List<Expression> list = new ArrayList<Expression>();
		list.add(ExpressionFactory.matchExp(NameMachine.NAME_MACHINE_PROPERTY, 
				this.getPK()));
		list.add(ExpressionFactory.matchExp(NameMachine.LANGUAGE_PROPERTY, 
				language));
		SelectQuery query = new SelectQuery(NameMachine.class,ExpressionFactory.joinExp(Expression.AND, list));
		List<NameMachine> names = context.performQuery(query);
		if (names == null || names.isEmpty()) return null;
		return names.get(0);   
	}

	public LinkedHashMap<String,String> getNamesMap() {
		ObjectContext context = this.getObjectContext();
		return AuxilServlet.getNamesMap(this, NameMachine.class, NameMachine.NAME_MACHINE_PROPERTY);
	}

	public String getBestName(HttpServletRequest request) {
		return AuxilServlet.chooseName(request, this.getNameMachine());
	}

	public static Machine findById(ObjectContext context, String id) {
		try {
			Integer idNum = Integer.valueOf(id);
			ObjectId idMachine = new ObjectId("Machine", Machine.ID_PK_COLUMN, idNum);

			// this constructor implicitly uses "CACHE_REFRESH" policy, so a fresh object will be returned
			ObjectIdQuery query = new ObjectIdQuery(idMachine);

			Machine machine = (Machine) DataObjectUtils.objectForQuery(context, query);
			return machine;
		} catch (NumberFormatException ne) {
			return null;
		}
	}

	public static Machine findByMAC(ObjectContext context, String mac24, boolean createIfMissing) {
		try {
			if (mac24 == null || mac24.isEmpty()) return null;
			
			Integer macNum = Integer.parseInt(mac24,16);
			SelectQuery querymachineid = new SelectQuery(Machine.class,ExpressionFactory.matchExp(Machine.MACHINEMAC_PROPERTY, macNum));
			List<Machine> machines = context.performQuery(querymachineid);
			if (machines == null || machines.isEmpty()) {
				if (createIfMissing) {
					Machine machine = (Machine)context.newObject(Machine.class);
					machine.setMachinemac(macNum);
					context.commitChanges();
					return machine;
				} else {
					return null;
				}
			} else {
				return machines.get(0);
			}
		} catch (NumberFormatException ne) {
			return null;
		}
	}
	
	@Override
	public int compareTo(NamedObject arg0) {
		if (arg0 == null) throw new NullPointerException();
		if (arg0.getClass() != Machine.class) throw new InvalidParameterException();
		return this.getPK()-arg0.getPK();
	}

}
