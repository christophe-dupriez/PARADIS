package be.labo.data;

import java.util.List;

import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.ObjectId;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;

import be.labo.data.auto._Rawtables;

public class Rawtables extends _Rawtables {

	public static Rawtables ensure(ObjectContext context, byte recordType, String description) {
		SelectQuery query = new SelectQuery(Rawtables.class,ExpressionFactory.matchExp(Rawtables.RAWTYPE_PROPERTY, recordType));
		List<Rawtables> someTables = context.performQuery(query);
		Rawtables rt;
		if (someTables != null && !someTables.isEmpty()) {
			rt = someTables.get(0);
		} else { // Create the table record
			rt = (Rawtables)context.newObject(Rawtables.class);
			rt.setRawtype(recordType);
			rt.setDescription(description);
			context.commitChanges();
		}
		return rt;
	}

	public static Rawtables findByCode (ObjectContext context, String code, boolean createIfMissing) {
		try {
			if (code == null || code.isEmpty()) return null;
			
			Byte typeNum = Byte.parseByte(code);
			return ensure (context, typeNum,"table "+typeNum);
		} catch (NumberFormatException ne) {
			return null;
		}
	}

	/** Read-only access to PK */
	public Integer getPK() {
		return DataObjectUtils.intPKForObject(this);
	}

	public RawDataFields ensureField(byte inHeader, byte bitsSize, String description) {
		SelectQuery query = new SelectQuery(RawDataFields.class,ExpressionFactory.matchExp(RawDataFields.DESCRIPTION_PROPERTY, description));
		ObjectContext context = this.getObjectContext();
		List<RawDataFields> someFields = context.performQuery(query);
		RawDataFields rdf;
		boolean somethingToCommit = false;
		if (someFields != null && !someFields.isEmpty()) {
			rdf = someFields.get(0);
		} else { // Create the table record
			rdf = (RawDataFields)context.newObject(RawDataFields.class);
			rdf.setDescription(description);
			rdf.setInheader(inHeader);
			rdf.setPrecision(bitsSize);
			somethingToCommit = true;
		}

		List<RawTableField>ft_rdf = rdf.getFieldTable();
		List<RawTableField>ft_rt = this.getTableField();
		if (ft_rdf != null && !ft_rdf.isEmpty() && ft_rt != null && !ft_rt.isEmpty()) {
			ft_rdf.retainAll(ft_rt);
			if (ft_rdf.isEmpty()) {
				RawTableField ft = (RawTableField)context.newObject(RawTableField.class);
				ft.setFieldTable(this);
				ft.setTableField(rdf);
				somethingToCommit = true;
			}			
		} else {
			RawTableField ft = (RawTableField)context.newObject(RawTableField.class);
			ft.setFieldTable(this);
			ft.setTableField(rdf);
			somethingToCommit = true;
		}

		if (somethingToCommit) context.commitChanges();
		return rdf;
	}


}
