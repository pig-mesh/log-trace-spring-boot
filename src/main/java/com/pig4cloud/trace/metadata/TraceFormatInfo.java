package com.pig4cloud.trace.metadata;

import java.util.Objects;

/**
 * 日志格式 信息
 *
 * @author <a href="mailto:yaoonlyi@gmail.com">purgeyao</a>
 * @since 1.0.0
 */
public class TraceFormatInfo {

	/**
	 * 格式字段
	 */
	private String formatField;

	/**
	 * 字段显示内容
	 */
	private String fieldVal;

	public String getFormatField() {
		return formatField;
	}

	public void setFormatField(String formatField) {
		this.formatField = formatField;
	}

	public String getFieldVal() {
		return fieldVal;
	}

	public void setFieldVal(String fieldVal) {
		this.fieldVal = fieldVal;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		TraceFormatInfo that = (TraceFormatInfo) o;
		return Objects.equals(formatField, that.formatField) && Objects.equals(fieldVal, that.fieldVal);
	}

	@Override
	public int hashCode() {
		return Objects.hash(formatField, fieldVal);
	}

}
