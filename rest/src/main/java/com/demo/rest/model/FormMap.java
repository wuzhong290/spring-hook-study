package com.demo.rest.model;

import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 * SpringMvc 把请求的所有参数封装到Map中,提供最常用的方法
 * 
 * 
 * @version
 * 
 */
public class FormMap<K, V> extends HashMap<K, V> implements Serializable {

	private static final long serialVersionUID = 1L;

	public void set(K key, V value) {
		this.put(key, value);
	}

	/**
	 * Get attribute of mysql type: varchar, char, enum, set, text, tinytext,
	 * mediumtext, longtext
	 */
	public String getStr(String attr) {
		return ObjectUtils.allNotNull( this.get(attr)) ?  this.get(attr).toString() : null;
	}

	/**
	 * Get attribute of mysql type: int, integer, tinyint(n) n > 1, smallint,
	 * mediumint
	 */
	public Integer getInt(String attr) {
		if(ObjectUtils.allNotNull( this.get(attr)))
		{
			return (Integer) this.get(attr);
		}else{
			return null;
		}
			
	//	return ObjectUtils.allNotNull( this.get(attr)) ? (Integer) this.get(attr) : null;
	}

	/**
	 * Get attribute of mysql type: bigint, unsign int
	 */
	public Long getLong(String attr) {
		return ObjectUtils.allNotNull( this.get(attr)) ? (Long) this.get(attr) : null;
	}

	/**
	 * Get attribute of mysql type: unsigned bigint
	 */
	public java.math.BigInteger getBigInteger(String attr) {
		return ObjectUtils.allNotNull( this.get(attr)) ? (java.math.BigInteger) this.get(attr) : null;
	}

	/**
	 * Get attribute of mysql type: date, year
	 */
	public java.util.Date getDate(String attr) {
		return ObjectUtils.allNotNull( this.get(attr)) ? (java.util.Date) this.get(attr) : null;
	}

	/**
	 * Get attribute of mysql type: time
	 */
	public java.sql.Time getTime(String attr) {
		return ObjectUtils.allNotNull( this.get(attr)) ? (java.sql.Time) this.get(attr) : null;
	}

	/**
	 * Get attribute of mysql type: timestamp, datetime
	 */
	public java.sql.Timestamp getTimestamp(String attr) {
		return ObjectUtils.allNotNull( this.get(attr)) ? (java.sql.Timestamp) this.get(attr) : null;
	}

	/**
	 * Get attribute of mysql type: real, double
	 */
	public Double getDouble(String attr) {
		return ObjectUtils.allNotNull( this.get(attr)) ? (Double) this.get(attr) : null;
	}

	/**
	 * Get attribute of mysql type: float
	 */
	public Float getFloat(String attr) {
		return ObjectUtils.allNotNull( this.get(attr)) ? (Float) this.get(attr) : null;
	}

	/**
	 * Get attribute of mysql type: bit, tinyint(1)
	 */
	public Boolean getBoolean(String attr) {
		return ObjectUtils.allNotNull( this.get(attr)) ? (Boolean) this.get(attr) : null;
	}

	/**
	 * Get attribute of mysql type: decimal, numeric
	 */
	public java.math.BigDecimal getBigDecimal(String attr) {
		return ObjectUtils.allNotNull( this.get(attr)) ? (java.math.BigDecimal) this.get(attr) : null;
	}

	/**
	 * Get attribute of mysql type: binary, varbinary, tinyblob, blob,
	 * mediumblob, longblob
	 */
	public byte[] getBytes(String attr) {
		return ObjectUtils.allNotNull( this.get(attr)) ? (byte[]) this.get(attr) : null;
	}

	/**
	 * Get attribute of any type that extends from Number
	 */
	public Number getNumber(String attr) {
		return ObjectUtils.allNotNull( this.get(attr)) ? (Number) this.get(attr) : null;
	}

	/**
	 * Return attribute names of this model.
	 */
	public String[] getAttrNames() {
		Set<K> attrNameSet = this.keySet();
		return attrNameSet.toArray(new String[attrNameSet.size()]);
	}

	/**
	 * Return attribute values of this model.
	 */
	public Object[] getAttrValues() {
		Collection<V> attrValueCollection = values();
		return attrValueCollection.toArray(new Object[attrValueCollection
				.size()]);
	}

}
