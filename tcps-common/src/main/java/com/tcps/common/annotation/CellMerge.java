package com.tcps.common.annotation;

import com.tcps.common.excel.CellMergeStrategy;

import java.lang.annotation.*;

/**
 * excel 列单元格合并(合并列相同项)
 *
 * 需搭配 {@link CellMergeStrategy} 策略使用
 *
 * @author Tao Guang
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CellMerge {

	/**
	 * col index
	 */
	int index() default -1;

}
