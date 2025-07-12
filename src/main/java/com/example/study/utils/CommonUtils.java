package com.example.study.utils;

import com.example.study.dto._event.EventMessage;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * 常用工具組
 */
@Component
public class CommonUtils {

	/**
	 * 自動輸出例外工具
	 * 
	 * @param code        事件編號
	 * @param description 事件資訊
	 * @param data      data 給予的資料
	 * @return ResponseDto<T>
	 */
	public static <OBJECT> EventMessage<OBJECT> setExceptionEventMessage(String code, String description, OBJECT data) {
		EventMessage<OBJECT> result = new EventMessage<>();
		result.setEventCode(code);
		result.setEventDescription(description);
		result.setEventMessage(data);
		return result;
	}

	/**
	 * 自動輸出工具
	 * 
	 * @param data data 給予的資料
	 * @return ResponseDto<T>
	 */
	public static <OBJECT> EventMessage<OBJECT> setDefaultEventMessage(OBJECT data) {
		EventMessage<OBJECT> result = new EventMessage<>();
		result.setEventCode("0000");
		result.setEventDescription("成功執行");
		result.setEventMessage(data);
		return result;
	}
	
	/**
	 * 取得物件中所有空值欄位
	 * 
	 * @param source 一個任意物件
	 * @return String[] 將是null的物件的key打包成一個陣列
	 */
	public static String[] getNullPropertyNames(Object source) {

		Set<String> nullValues = new HashSet<>();
		BeanWrapper bean = new BeanWrapperImpl(source);
		PropertyDescriptor[] proDescrs = bean.getPropertyDescriptors();
		Object sourceVal;
		for (PropertyDescriptor proDescr : proDescrs) {
			// 將欄位值取出
			sourceVal = bean.getPropertyValue(proDescr.getName());
			// 比對後將沒有值的欄位add進散列集
			if (sourceVal == null)
				nullValues.add(proDescr.getName());
		}
		String[] result = new String[nullValues.size()];
		return nullValues.toArray(result);
	}

}
