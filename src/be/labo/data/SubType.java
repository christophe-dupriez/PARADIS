package be.labo.data;

import java.security.InvalidParameterException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.ObjectId;
import org.apache.cayenne.query.ObjectIdQuery;

import be.labo.auxil.AuxilServlet;
import be.labo.auxil.CategoryInterval;
import be.labo.data.auto._SubType;

public class SubType extends _SubType implements NamedObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8621240988711770486L;

	boolean categoriesInvalid = true;
	CategoryInterval[] categories = null;

	/** Read-only access to PK */
	public Integer getPK() {
		return DataObjectUtils.intPKForObject(this);
	}

	/*
	 * Find the applicable Scaling table at a given time for a raw value Subtype
	 * If the "since" field is null, it means "beginning of times"!
	 */
	public Scaling getSubtypeScaling(Date aTimestamp) {
		if (aTimestamp == null) return null;
		List<ScalingSubType> scaST = this.getSubtypeScaling();
		Scaling theScale = null;
		if (scaST != null && !scaST.isEmpty()) {
			long minTime = 0;
			for (ScalingSubType aScaST : scaST) {
				Date since = aScaST.getSince();
				long sinceTime = 0;
				if (since != null) sinceTime = since.getTime();
				if (sinceTime >= minTime && sinceTime <= aTimestamp.getTime()) {
					theScale = aScaST.getRawScaling();
					minTime = sinceTime;
				}
			}
		}
		return theScale;
	}

	/*
	 * Makes a local image of categories for fast categorization
	 */
	public CategoryInterval[] getCategories() {
		CategoryInterval[] categories = CategoryInterval.subTypeCategories.get(this.getPK());
		if (categories == null) synchronized(this) {
			categories = CategoryInterval.subTypeCategories.get(this.getPK());
			if (categories == null) {
				DataType aType = this.getOwner();
				if (aType != null) {
					CategoryInterval[] typeCategories = aType.getIntervals();
					categories = new CategoryInterval[typeCategories.length];
					CategoryInterval fullRange = new CategoryInterval((Category)null,this.getMin(),this.getMax());
					if (typeCategories != null) {
						categories[0] = new CategoryInterval(typeCategories[0],this.getMin(),this.getMin1(), fullRange);
						if (typeCategories.length > 2) {
							categories[1] = new CategoryInterval(typeCategories[1],this.getMin1(),this.getMin2(), fullRange);
							if (typeCategories.length > 3) {
								categories[2] = new CategoryInterval(typeCategories[2],this.getMin2(),this.getMin3(), fullRange);
								if (typeCategories.length > 4) {
									categories[3] = new CategoryInterval(typeCategories[3],this.getMin3(),this.getMin4(), fullRange);
									if (typeCategories.length > 5) {
										categories[4] = new CategoryInterval(typeCategories[4],this.getMin4(),this.getMin5(), fullRange);
										if (typeCategories.length > 6) {
											categories[5] = new CategoryInterval(typeCategories[5],this.getMin5(),this.getMin6(), fullRange);
											if (typeCategories.length > 7) {
												categories[6] = new CategoryInterval(typeCategories[6],this.getMin6(),this.getMin7(), fullRange);
												if (typeCategories.length > 8) {
													categories[7] = new CategoryInterval(typeCategories[7],this.getMin7(),this.getMin8(), fullRange);
													if (typeCategories.length > 9) {
														categories[8] = new CategoryInterval(typeCategories[8],this.getMin8(),this.getMin9(), fullRange);
														categories[9] = new CategoryInterval(typeCategories[9],this.getMin9(),this.getMax(), fullRange);
													} else {
														categories[8] = new CategoryInterval(typeCategories[8],this.getMin8(),this.getMax(), fullRange);
													}
												} else {
													categories[7] = new CategoryInterval(typeCategories[7],this.getMin7(),this.getMax(), fullRange);
												}
											} else {
												categories[6] = new CategoryInterval(typeCategories[6],this.getMin6(),this.getMax(), fullRange);													
											}
										} else {
											categories[5] = new CategoryInterval(typeCategories[5],this.getMin5(),this.getMax(), fullRange);
										}
									} else {
										categories[4] = new CategoryInterval(typeCategories[4],this.getMin4(),this.getMax(), fullRange);
									}
								} else {
									categories[3] = new CategoryInterval(typeCategories[3],this.getMin3(),this.getMax(), fullRange);
								}
							} else {
								categories[2] = new CategoryInterval(typeCategories[2],this.getMin2(),this.getMax(), fullRange);
							}
						} else {
							categories[1] = new CategoryInterval(typeCategories[1],this.getMin1(),this.getMax(), fullRange);								
						}
					} else {
						categories[0] = new CategoryInterval(typeCategories[0],this.getMin(),this.getMax(), fullRange);
					}
				} else {
					categories = new CategoryInterval[0];
				}
				CategoryInterval.typeCategories.put(this.getPK(),categories);
			}
		}
		return categories;
	}


	public void setMin(Double value) {
		CategoryInterval.subTypeCategories.remove(this.getPK()); // Ensure reload of categories if setMin is set: This should also be done with setMin1.., setMax1.., setColor1..
		super.setMin(value);
	}

	public void setMax(Double value) {
		CategoryInterval.subTypeCategories.remove(this.getPK()); // Ensure reload of categories if setMin is set: This should also be done with setMin1.., setMax1.., setColor1..
		super.setMax(value);
	}

	public byte getCategoryNumber(double value) {
		return CategoryInterval.find(getCategories(), value);
	}

	public int getColor(byte categNum) {
		CategoryInterval aCateg = CategoryInterval.get(getCategories(),categNum);
		if (aCateg == null) return 0;
		return aCateg.getColor();
	}

	public int getTinyColor(byte categNum) {
		CategoryInterval aCateg = CategoryInterval.get(getCategories(),categNum);
		if (aCateg == null) return 0;
		return aCateg.getTinyColor();
	}

	@Override
	public LinkedHashMap<String,String> getNamesMap() {
		LinkedHashMap<String,String> subtypeNames = new LinkedHashMap<String,String>(); 
		DataType datatype = this.getOwner();
		Place place = this.getSubtypePlace();
		if (datatype != null) {
			LinkedHashMap<String,String> typeNames = AuxilServlet.getNamesMap(datatype,NameType.class, NameType.NAME_TYPE_PROPERTY);
			if (place != null) {
				LinkedHashMap<String,String> placeNames = AuxilServlet.getNamesMap(place,NamePlace.class, NamePlace.NAME_PLACE_PROPERTY);
				for (Map.Entry entry : typeNames.entrySet()) {
					subtypeNames.put((String)entry.getKey(), entry.getValue()+" ("+placeNames.get(entry.getKey())+')' );
				}
			} else {
				subtypeNames = typeNames;
			}
		} else if (place != null) {
			LinkedHashMap<String,String> placeNames = AuxilServlet.getNamesMap(place,NamePlace.class, NamePlace.NAME_PLACE_PROPERTY);
			for (Map.Entry entry : placeNames.entrySet()) {
				subtypeNames.put((String)entry.getKey(), "("+entry.getValue()+')' );
			}

		}
		return subtypeNames;
	}

	@Override
	public String getBestName(HttpServletRequest request) {
		DataType datatype = this.getOwner();
		Place place = this.getSubtypePlace();
		String subtypeName = "";
		if (datatype != null) {
			subtypeName = AuxilServlet.chooseName(request, datatype.getTypeName());
			if (place != null) {
				subtypeName = subtypeName + " (" + AuxilServlet.chooseName(request, place.getPlacename())+')';
			}
		} else if (place != null) {
			subtypeName = '(' + AuxilServlet.chooseName(request, place.getPlacename())+')';
		}
		return subtypeName;
	}

	public static SubType findById(ObjectContext context, String id) {
		try {
			Integer idNum = Integer.valueOf(id);
			ObjectId idSubType = new ObjectId("SubType", DataType.ID_PK_COLUMN, idNum);

			// this constructor implicitly uses "CACHE_REFRESH" policy, so a fresh object will be returned
			ObjectIdQuery query = new ObjectIdQuery(idSubType);

			SubType object = (SubType) DataObjectUtils.objectForQuery(context, query);
			return object;
		} catch (NumberFormatException ne) {
			return null;
		}
	}

	@Override
	public int compareTo(NamedObject arg0) {
		if (arg0 == null) throw new NullPointerException();
		if (arg0.getClass() != SubType.class) throw new InvalidParameterException();
		return this.getPK()-arg0.getPK();
	}

	@Override
	public void setName(String language, String name) {
		// TODO Auto-generated method stub		
	}


}
