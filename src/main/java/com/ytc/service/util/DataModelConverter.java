package com.ytc.service.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import com.ytc.common.model.Model;
import com.ytc.dal.model.DalModel;



public class DataModelConverter implements IDataModelConverter {

	//private Logger logger = LoggerFactory.getLogger(DataModelConverter.class);

	

	private Set<Class<?>> knownJavaTypes;
	private Set<Class<? extends DalModel>> knownDALtypes;
	private Set<Class<? extends Model>> knownSVCtypes;

	public void setKnownJavaTypes(Set<Class<?>> knownJavaTypes) {
		this.knownJavaTypes = knownJavaTypes;
	}

	public void setKnownDALtypes(Set<Class<? extends DalModel>> knownDALtypes) {
		this.knownDALtypes = knownDALtypes;
	}

	public void setKnownSVCtypes(Set<Class<? extends Model>> knownSVCtypes) {
		this.knownSVCtypes = knownSVCtypes;
	}

	public Map<String, PropertyDescriptor> getPropMap(Class<?> clazz) {
		Map<String, PropertyDescriptor> propMap = new HashMap<String, PropertyDescriptor>();
		PropertyDescriptor[] props = PropertyUtils.getPropertyDescriptors(clazz);
		for (PropertyDescriptor prop : props) {
			Class<?> type = prop.getPropertyType();
			if (knownJavaTypes.contains(type) || knownDALtypes.contains(type) || knownSVCtypes.contains(type) || type.isEnum()) {
				propMap.put(prop.getName(), prop);
			}
		}
		return propMap;
	}


	@Override
	public void convert(Object source, Object target) {
		// logger.debug("Data model conversion from " + source.getClass() + " to " + target.getClass());

		Map<String, PropertyDescriptor> sourceProps = getPropMap(source.getClass());
		Map<String, PropertyDescriptor> targetProps = getPropMap(target.getClass());

		for (Entry<String, PropertyDescriptor> prop : targetProps.entrySet()) {
			String propName = prop.getKey();
			PropertyDescriptor targetProp = prop.getValue();

			Class<?> targetType = targetProp.getPropertyType();
			PropertyDescriptor sourceProp = sourceProps.get(propName);

			if (sourceProp != null) {
				Class<?> sourceType = sourceProp.getPropertyType();
				if ((targetType.isEnum() || java.lang.Enum.class.isAssignableFrom(targetType)) && sourceType == String.class) {
					stringToEnum(propName, source, target, targetType);
				} else if ((sourceType.isEnum() || java.lang.Enum.class.isAssignableFrom(sourceType)) && targetType == String.class) {
					copyMatchingPropertyType(propName, source, target);
				} 
			} else {
				copyMatchingPropertyType(propName, source, target);
			}

		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void stringToEnum(String propName, Object source, Object target, Class<?> targetEnumType) {
		try {
			// logger.debug("copy string to enum: " + propName + " ");
			String enumStr = (String) PropertyUtils.getProperty(source, propName);
			if (StringUtils.isNotBlank(enumStr)) {
				try {
					// class of enum is specific such as FolderType.class
					PropertyUtils.setProperty(target, propName, Enum.valueOf((Class) targetEnumType, enumStr));

				} catch (Exception e) {
					// class of enum is generic..
					ParameterizedType parameterizedType = (ParameterizedType) target.getClass().getGenericSuperclass();
					Class enumClazz = (Class) parameterizedType.getActualTypeArguments()[0];
					PropertyUtils.setProperty(target, propName, Enum.valueOf(enumClazz, enumStr));

				}
			}
		} catch (Exception e) {
			// logger.debug("Could not convert string to enum for " + propName);
		}

	}

	private void copyMatchingPropertyType(String propName, Object source, Object target) {
		Object value;
		String stringValue;
		try {
			value = PropertyUtils.getProperty(source, propName);
			if (value == null) {
				return;
			}
			if (value instanceof String) {
				stringValue = (String) value;
				stringValue = stringValue.trim();
				BeanUtils.copyProperty(target, propName, stringValue);
			} else {
				BeanUtils.copyProperty(target, propName, value);
			}
		} catch (Exception e) {
			// logger.debug("could not copy  prop " + propName + " from src=" + source.getClass() + " to target=" +
			// target.getClass());
		}
	}

}
