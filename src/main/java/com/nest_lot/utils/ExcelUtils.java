package com.nest_lot.utils;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by wuxiaoxing
 */
public class ExcelUtils {

	/**
	 * 导出填充excel表格数据 param 0 开始时间 1 结束时间 2是日夜类型，3金额总计
	 * 
	 * @param columns
	 * @param dataLists
	 * @param sheetName
	 * @return
	 */
	public static HSSFWorkbook exportHSSFWorkToExcel(List<String> param, List<String> columns, List<List<String>> dataLists, String sheetName) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 建立新的sheet对象（excel的表单）
		HSSFSheet sheet = workbook.createSheet(sheetName);
		// 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
		HSSFRow row1 = sheet.createRow(0);
		// 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
		HSSFCell cell1 = row1.createCell(0);
		// 设置单元格内容
		if (Tools.isEmpty(param.get(0)) && Tools.isEmpty(param.get(1))) {
			cell1.setCellValue("开始时间:---" + "     " + "结束时间:---");
		} else if (!Tools.isEmpty(param.get(0)) && !Tools.isEmpty(param.get(1))) {
			cell1.setCellValue("开始时间:" + param.get(0) + "     " + "结束时间:" + param.get(1));
		} else if (Tools.isEmpty(param.get(0))) {
			cell1.setCellValue("开始时间:---" + "     " + "结束时间:" + param.get(0));
		} else {
			cell1.setCellValue("开始时间:" + param.get(1) + "     " + "结束时间:---");
		}

		// 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
		// HSSFSheet sheet = workbook.createSheet(sheetName);
		// 创建标题栏
		HSSFRow row = sheet.createRow(1);
		HSSFCell cell = null;
		for (int i = 0; i < columns.size(); i++) {
			cell = row.createCell(i);
			cell.setCellValue(columns.get(i));
		}
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		HSSFDataFormat format = workbook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("@"));
		for (int i = 0, t = 2; i < dataLists.size(); i++, t++) {
			row = sheet.createRow(t);
			for (int i1 = 0; i1 < dataLists.get(i).size(); i1++) {
				cell = row.createCell(i1);
				cell.setCellValue(dataLists.get(i).get(i1));
				cell.setCellStyle(cellStyle);
			}
		}
		return workbook;
	}

	/**
	 * 导出excel表格
	 *
	 * @param columns
	 * @param dataLists
	 * @param sheetName
	 * @param exportName
	 * @param response
	 */
	public static void exportToExcel(List<String> param, List<String> columns, List<List<String>> dataLists, String sheetName, String exportName,
			HttpServletResponse response) {
		HSSFWorkbook workbook = exportHSSFWorkToExcel(param, columns, dataLists, sheetName);
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			response.reset();
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Type", "application/octet-stream");
			response.setHeader("Content-Type", "application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String((exportName).getBytes("gb2312"), "ISO8859-1"));
			workbook.write(out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			workbook.cloneSheet(0);
		}
	}
}
