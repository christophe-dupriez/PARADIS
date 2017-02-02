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
import org.apache.commons.lang.StringUtils;

import be.labo.auxil.AuxilServlet;
import be.labo.auxil.CategoryInterval;
import be.labo.auxil.FlotSymbol;
import be.labo.auxil.Seconds;
import be.labo.data.auto._DataType;
import be.labo.util.StaticUtil;

public class DataType extends _DataType implements NamedObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -375867623118918539L;

	/** Read-only access to PK */
	public Integer getPK() {
		return DataObjectUtils.intPKForObject(this);
	}

	public void setName(String language, String name) {
		if (language ==  null) language = ""; // empty string if no language specified
		NameType aName = this.findName(language);
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
				aName = context.newObject(NameType.class);
				if (aName != null) {
					aName.setLanguage(language);
					aName.setName(name);
					aName.setSince(new Date());
					aName.setNameType(this);
					context.commitChanges();
				}
			}
		}

	}

	public NameType findName(String language) {
		if (language == null) return null;

		ObjectContext context = this.getObjectContext();
		List<Expression> list = new ArrayList<Expression>();
		list.add(ExpressionFactory.matchExp(NameType.NAME_TYPE_PROPERTY, 
				this.getPK()));
		list.add(ExpressionFactory.matchExp(NameType.LANGUAGE_PROPERTY, 
				language));
		SelectQuery query = new SelectQuery(NameType.class,ExpressionFactory.joinExp(Expression.AND, list));
		List<NameType> names = context.performQuery(query);
		if (names == null || names.isEmpty()) return null;
		return names.get(0);   
	}

	public Category[] getCategories() {
		Byte nbCategs = this.getNbcategories();
		if (nbCategs != null) {
			Category[] categories = new Category[nbCategs];
			if (nbCategs > 0) {
				categories[0] = this.getCateg0();
				if (nbCategs > 1) {
					categories[1] = this.getCateg1();
					if (nbCategs > 2) {
						categories[2] = this.getCateg2();
						if (nbCategs > 3) {
							categories[3] = this.getCateg3();
							if (nbCategs > 4) {
								categories[4] = this.getCateg4();
								if (nbCategs > 5) {
									categories[5] = this.getCateg5();
									if (nbCategs > 6) {
										categories[6] = this.getCateg6();
										if (nbCategs > 7) {
											categories[7] = this.getCateg7();
											if (nbCategs > 8) {
												categories[8] = this.getCateg8();
												if (nbCategs > 9) {
													categories[9] = this.getCateg9();
												}
											}
										}
									}
								}
							}
						}
					}
				}
				return categories;
			}
		}
		return null;
	}

	/*
	 * Makes a local image of categories for fast categorization
	 */
	public CategoryInterval[] getIntervals() {
		CategoryInterval[] categories = CategoryInterval.typeCategories.get(this.getPK());
		if (categories == null) synchronized(this) {
			categories = CategoryInterval.typeCategories.get(this.getPK());
			if (categories == null) {
				Byte nbCategs = this.getNbcategories();
				if (nbCategs != null) {
					categories = new CategoryInterval[nbCategs];
					if (nbCategs > 0) {
						CategoryInterval fullRange = new CategoryInterval(this.getCateg0(),this.getMin(),this.getMax());
						if (nbCategs > 1) {
							categories[0] = new CategoryInterval(this.getCateg0(),this.getMin(),this.getMin1(), fullRange);
							if (nbCategs > 2) {
								categories[1] = new CategoryInterval(this.getCateg1(),this.getMin1(),this.getMin2(), fullRange);
								if (nbCategs > 3) {
									categories[2] = new CategoryInterval(this.getCateg2(),this.getMin2(),this.getMin3(), fullRange);
									if (nbCategs > 4) {
										categories[3] = new CategoryInterval(this.getCateg3(),this.getMin3(),this.getMin4(), fullRange);
										if (nbCategs > 5) {
											categories[4] = new CategoryInterval(this.getCateg4(),this.getMin4(),this.getMin5(), fullRange);
											if (nbCategs > 6) {
												categories[5] = new CategoryInterval(this.getCateg5(),this.getMin5(),this.getMin6(), fullRange);
												if (nbCategs > 7) {
													categories[6] = new CategoryInterval(this.getCateg6(),this.getMin6(),this.getMin7(), fullRange);
													if (nbCategs > 8) {
														categories[7] = new CategoryInterval(this.getCateg7(),this.getMin7(),this.getMin8(), fullRange);
														if (nbCategs > 9) {
															categories[8] = new CategoryInterval(this.getCateg8(),this.getMin8(),this.getMin9(), fullRange);
															categories[9] = new CategoryInterval(this.getCateg9(),this.getMin9(),this.getMax(), fullRange);
														} else {
															categories[8] = new CategoryInterval(this.getCateg8(),this.getMin8(),this.getMax(), fullRange);
														}
													} else {
														categories[7] = new CategoryInterval(this.getCateg7(),this.getMin7(),this.getMax(), fullRange);
													}
												} else {
													categories[6] = new CategoryInterval(this.getCateg6(),this.getMin6(),this.getMax(), fullRange);													
												}
											} else {
												categories[5] = new CategoryInterval(this.getCateg5(),this.getMin5(),this.getMax(), fullRange);
											}
										} else {
											categories[4] = new CategoryInterval(this.getCateg4(),this.getMin4(),this.getMax(), fullRange);
										}
									} else {
										categories[3] = new CategoryInterval(this.getCateg3(),this.getMin3(),this.getMax(), fullRange);
									}
								} else {
									categories[2] = new CategoryInterval(this.getCateg2(),this.getMin2(),this.getMax(), fullRange);
								}
							} else {
								categories[1] = new CategoryInterval(this.getCateg1(),this.getMin1(),this.getMax(), fullRange);								
							}
						} else {
							categories[0] = new CategoryInterval(this.getCateg0(),this.getMin(),this.getMax(), fullRange);
						}
					}
				} else {
					categories = new CategoryInterval[0];
				}
				CategoryInterval.typeCategories.put(this.getPK(),categories);
			}
		}
		return categories;
	}

	public void setNbcategories(Byte nbcategories) {
		CategoryInterval.typeCategories.remove(this.getPK());  // Ensure reload of categories if NbCategories is set: This should also be done with setMin, setMax, setColor...
		super.setNbcategories(nbcategories);
	}
	public void setMin(Double value) {
		CategoryInterval.typeCategories.remove(this.getPK());  // Ensure reload of categories if NbCategories is set: This should also be done with setMin, setMax, setColor...
		super.setMin(value);
	}
	public void setMax(Double value) {
		CategoryInterval.typeCategories.remove(this.getPK());  // Ensure reload of categories if NbCategories is set: This should also be done with setMin, setMax, setColor...
		super.setMax(value);
	}

	@Override
	public LinkedHashMap<String,String> getNamesMap() {
		return AuxilServlet.getNamesMap(this,NameType.class, NameType.NAME_TYPE_PROPERTY);
	}

	@Override
	public String getBestName(HttpServletRequest request) {
		return AuxilServlet.chooseName(request, this.getTypeName());
	}

	public static DataType findById(ObjectContext context, String id) {
		try {
			Integer idNum = Integer.valueOf(id);
			ObjectId idDataType = new ObjectId("DataType", DataType.ID_PK_COLUMN, idNum);

			// this constructor implicitly uses "CACHE_REFRESH" policy, so a fresh object will be returned
			ObjectIdQuery query = new ObjectIdQuery(idDataType);

			DataType object = (DataType) DataObjectUtils.objectForQuery(context, query);
			return object;
		} catch (NumberFormatException ne) {
			return null;
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
	

   public boolean getFixedDataType() {
	   return this.getInheader() > 4; // Tabs 1 to 4 are for assignable data types, 5 to 9 are for fields with FIXED datatypes (cannot be edited)
   }
   
   public Seconds getDefaultSeconds() {
	   Integer ts = this.getDefaulttimespan();
	   if (ts==null) return null;
	   else {
		   return new Seconds(ts);
	   }
   }
   
   public String getPrecision() {
	   Byte decimals = this.getDecimals();
	   if (decimals == null) return "";
	   if (decimals <= 0) return "1";
	   return "0."+StringUtils.repeat("0", decimals-1)+"1";
   }
   
	public String getMaxString() {
		Double aMax = getMax();
		if (aMax == null) return "";
		return StaticUtil.niceDouble(getMax());
	}

	@Override
	public int compareTo(NamedObject arg0) {
		// TODO Auto-generated method stub
		if (arg0 == null) throw new NullPointerException();
		if (arg0.getClass() != DataType.class) throw new InvalidParameterException();
		return this.getPK()-arg0.getPK();
	}

}
