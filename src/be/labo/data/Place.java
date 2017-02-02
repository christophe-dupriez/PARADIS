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
import be.labo.data.auto._Place;

public class Place extends _Place implements NamedObject {

	/** Read-only access to PK */
	public Integer getPK() {
		return DataObjectUtils.intPKForObject(this);
	}

	public String getLatLongElev() {
		LatLongElev lle = new LatLongElev(this.getLatitude(),this.getLongitude(),this.getElevation());
		return lle.toString();
	}

	public WebColor getWebColor() {
		Byte color = this.getColor();
		WebColor wColor;
		if (color == null) {
			wColor = new WebColor(0,true); // Black!
		} else {
			wColor = new WebColor(color,true);
		}
		return wColor;
	}

	public String getColorHex() {
		return "#"+getWebColor().toString();
	}

	public NamePlace findName(String language) {
		if (language == null) return null;

		ObjectContext context = this.getObjectContext();
		List<Expression> list = new ArrayList<Expression>();
		list.add(ExpressionFactory.matchExp(NamePlace.NAME_PLACE_PROPERTY, 
				this.getPK()));
		list.add(ExpressionFactory.matchExp(NamePlace.LANGUAGE_PROPERTY, 
				language));
		SelectQuery query = new SelectQuery(NamePlace.class,ExpressionFactory.joinExp(Expression.AND, list));
		List<NamePlace> names = context.performQuery(query);
		if (names == null || names.isEmpty()) return null;
		return names.get(0);   
	}

	public LinkedHashMap<String,String> getNamesMap() {
		ObjectContext context = this.getObjectContext();
		return AuxilServlet.getNamesMap(this, NamePlace.class, NamePlace.NAME_PLACE_PROPERTY);
	}

	public String getBestName(HttpServletRequest request) {
		return AuxilServlet.chooseName(request, this.getPlacename());
	}

	public static Place findById(ObjectContext context, String id) {
		try {
			Integer idNum = Integer.valueOf(id);
			ObjectId idPlace = new ObjectId("Place", Place.ID_PK_COLUMN, idNum);

			// this constructor implicitly uses "CACHE_REFRESH" policy, so a fresh object will be returned
			ObjectIdQuery query = new ObjectIdQuery(idPlace);

			Place place = (Place) DataObjectUtils.objectForQuery(context, query);
			return place;
		} catch (NumberFormatException ne) {
			return null;
		}
	}

	@Override
	public int compareTo(NamedObject arg0) {
		if (arg0 == null) throw new NullPointerException();
		if (arg0.getClass() != Place.class) throw new InvalidParameterException();
		return this.getPK()-arg0.getPK();
	}

	@Override
	public void setName(String language, String name) {
		if (language ==  null) language = ""; // empty string if no language specified
		NamePlace aName = this.findName(language);
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
				aName = context.newObject(NamePlace.class);
				if (aName != null) {
					aName.setLanguage(language);
					aName.setName(name);
					aName.setSince(new Date());
					aName.setNamePlace(this);
					context.commitChanges();
				}
			}
		}   
	}


	public String getSymbolBlack() {
		Byte symb = this.getSymbol();
		if (symb == null) {
			return FlotSymbol.circle.getBlackEntity();
		} else {
			FlotSymbol[] vals = FlotSymbol.values();
			if (symb >= 0 && symb < vals.length) {
				return FlotSymbol.values()[symb].getBlackEntity();
			} else return FlotSymbol.circle.getBlackEntity();
		}
	}

	public String getSymbolWhite() {
		Byte symb = this.getSymbol();
		if (symb == null) {
			return FlotSymbol.circle.getWhiteEntity();
		} else {
			FlotSymbol[] vals = FlotSymbol.values();
			if (symb >= 0 && symb < vals.length) {
				return FlotSymbol.values()[symb].getWhiteEntity();
			} else return FlotSymbol.circle.getWhiteEntity();
		}
	}
	

}
