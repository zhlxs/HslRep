
package com.jrwp.payMent.help;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.jrwp.payMent.entity.*;
import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.jrwp.core.entity.Core_User;

/**
 * 数据统计导出Execl
 * 
 * @author kedashuat
 * 
 */
public class EXCELHelper {

	// 订单查询导出excel表
//	public static String printExcel(String fileName, List<PayWanInfo> orderdetails) {
//		delAllFile(fileName);
//		WritableWorkbook wwb = null;
//		Subject currentUser = SecurityUtils.getSubject();
//		Core_User user = (Core_User) currentUser.getSession().getAttribute(
//				"user");
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + "" + "MM" + ""
//				+ "dd" + "" + "H" + "" + "mm" + "" + "ss");
//		// 导出文件名
//		String now_date = sdf.format(new Date());
//		File file = new File(fileName + now_date + ".xls");
//
//		try {
//			if (!file.exists()) {
//				file.createNewFile();
//			}
//			wwb = Workbook.createWorkbook(file);// 创建 xls表
//			WritableSheet sheet = wwb.createSheet("办证申请", 0);// 创建sheet表格
//			CellView cellView = new CellView();
//
//			WritableFont count = new WritableFont(
//					WritableFont.createFont("宋体"), 11); // 单元格内容
//			WritableCellFormat cell = new WritableCellFormat(count);
//			if (cell != null) {
//				cell.setBorder(Border.ALL, BorderLineStyle.THIN,
//						jxl.format.Colour.BLACK);
//				cell.setAlignment(Alignment.CENTRE);
//				cell.setVerticalAlignment(VerticalAlignment.CENTRE);
//
//				// dept
//				WritableFont count4 = new WritableFont(
//						WritableFont.createFont("宋体"), 13); // 单元格内容
//				WritableCellFormat cell4 = new WritableCellFormat(count4);
//				cell.setAlignment(Alignment.CENTRE);
//				cell.setVerticalAlignment(VerticalAlignment.CENTRE);
//				// 头
//				WritableFont count1 = new WritableFont(
//						WritableFont.createFont("微软雅黑"), 14);
//				WritableCellFormat cell2 = new WritableCellFormat(count1);
//				cell2.setBorder(Border.ALL, BorderLineStyle.THIN,
//						jxl.format.Colour.BLACK);
//				cell2.setAlignment(Alignment.CENTRE);
//				cell2.setVerticalAlignment(VerticalAlignment.CENTRE);
//
//				// TOP
//				WritableFont count3 = new WritableFont(
//						WritableFont.createFont("微软雅黑"), 18);
//				WritableCellFormat cell3 = new WritableCellFormat(count3);
//				cell3.setAlignment(Alignment.CENTRE);
//				cell3.setVerticalAlignment(VerticalAlignment.CENTRE);
//
//				sheet.setColumnView(0, 5);
//				sheet.setColumnView(1, 20);
//				sheet.setColumnView(2, 28);
//				sheet.setColumnView(3, 27);
//				sheet.setColumnView(4, 15);
//				sheet.setColumnView(5, 17);
//				sheet.setColumnView(6, 27);
//				sheet.mergeCells(0, 0, 9, 0);
//				sheet.setRowView(0, 900);
//
//				Label dept = new Label(1, 1, "" + "所属单位:", cell4);
//				Label dept1 = new Label(2, 1, user.getDept().getDeptName(),
//						cell4);
//				Label labelxh = new Label(0, 2, "序号", cell2);
//				Label labelordernumber = new Label(1, 2, "订单号", cell2);
//				Label labeldept = new Label(2, 2, "部门", cell2);
//				Label labelmacname = new Label(3, 2, "设备名称", cell2);
//				Label labelzffs = new Label(4, 2, "支付方式", cell2);
//				Label labelzfzt = new Label(5, 2, "支付状态", cell2);
//				Label labelzfje= new Label(6, 2, "支付金额", cell2);
//				Label labelskf = new Label(7, 2, "收款方", cell2);
//				Label labelxdsj = new Label(8, 2, "下单时间", cell2);
//				Label labelzfsj = new Label(9, 2, "支付时间", cell2);
//
//				Label head = new Label(0, 0, "订单信息统计表", cell3);
//
//				// Add
//				sheet.addCell(dept);
//				sheet.addCell(dept1);
//				sheet.addCell(labelxh);
//				sheet.addCell(labelordernumber);
//				sheet.addCell(labeldept);
//				sheet.addCell(labelmacname);
//				sheet.addCell(labelzffs);
//				sheet.addCell(labelzfzt);
//				sheet.addCell(labelzfje);
//				sheet.addCell(labelskf);
//				sheet.addCell(labelxdsj);
//				sheet.addCell(labelzfsj);
//				sheet.addCell(head);
//
//				for (int i = 0; i <= orderdetails.size() + 1; i++) {
//					// 设置列宽
//					sheet.setRowView(i + 1, 450);
//				}
//				// 循环写入表格信息
//				for (int i = 0; i < orderdetails.size(); i++) {
//					Label labelxh_i = new Label(0, i + 3, i + 1 + "", cell);
//					Label labelordernumber_i = new Label(1, i + 3, orderdetails.get(i).getOrderNumber()
//							+ "", cell);
//					Label labeldept_i = new Label(2, i + 3, orderdetails.get(i)
//							.getDeptName(), cell);
//					Label labelmacname_i = new Label(3, i + 3,
//							orderdetails.get(i).getMachineName()
//							+ "", cell);
//					Label labelzffs_i = new Label(4, i + 3,
//							orderdetails.get(i).getPayType()
//							+ "", cell);
//					Label labelzfzt_i = new Label(5, i + 3,
//							orderdetails.get(i).getPayStatus()
//							+ "", cell);
//					Label labelzfje_i = new Label(6, i + 3, orderdetails.get(i)
//							.getPayMoney()
//							+ "", cell);
//					Label labelskf_i = new Label(7, i + 3, orderdetails.get(i)
//							.getConfigName()
//							+ "", cell);
//					Label labelxdsj_i = new Label(8, i + 3, orderdetails.get(i)
//							.getCreateTime()
//							+ "", cell);
//					Label labelzfsj_i = new Label(9, i + 3, orderdetails.get(i)
//							.getPayTime()
//							+ "", cell);
//
//					sheet.addCell(labelxh_i);
//					sheet.addCell(labelordernumber_i);
//					sheet.addCell(labeldept_i);
//					sheet.addCell(labelmacname_i);
//					sheet.addCell(labelzffs_i);
//					sheet.addCell(labelzfzt_i);
//					sheet.addCell(labelzfje_i);
//					sheet.addCell(labelskf_i);
//					sheet.addCell(labelxdsj_i);
//					sheet.addCell(labelzfsj_i);
//				}
//				wwb.write();
//				wwb.close();
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return now_date;
//	}
//

	// 删除此时同名的表
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				flag = true;
			}
		}
		return flag;
	}

	//数据统计导出excel表
	public static String printsjtjExcel(String fileName, List<WanCount> ordercount,String type) {
		delAllFile(fileName);
		WritableWorkbook wwb = null;
		Subject currentUser = SecurityUtils.getSubject();
		Core_User user = (Core_User) currentUser.getSession().getAttribute(
				"user");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + "" + "MM" + ""
				+ "dd" + "" + "H" + "" + "mm" + "" + "ss");
		// 导出文件名
		String now_date = sdf.format(new Date());
		File file = new File(fileName + now_date + ".xls");

		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			wwb = Workbook.createWorkbook(file);// 创建 xls表
			WritableSheet sheet = wwb.createSheet("数据统计", 0);// 创建sheet表格
			CellView cellView = new CellView();

			WritableFont count = new WritableFont(
					WritableFont.createFont("宋体"), 11); // 单元格内容
			WritableCellFormat cell = new WritableCellFormat(count);
			if (cell != null) {
				cell.setBorder(Border.ALL, BorderLineStyle.THIN,
						jxl.format.Colour.BLACK);
				cell.setAlignment(Alignment.CENTRE);
				cell.setVerticalAlignment(VerticalAlignment.CENTRE);

				// dept
				WritableFont count4 = new WritableFont(
						WritableFont.createFont("宋体"), 13); // 单元格内容
				WritableCellFormat cell4 = new WritableCellFormat(count4);
				cell.setAlignment(Alignment.CENTRE);
				cell.setVerticalAlignment(VerticalAlignment.CENTRE);
				// 头
				WritableFont count1 = new WritableFont(
						WritableFont.createFont("微软雅黑"), 14);
				WritableCellFormat cell2 = new WritableCellFormat(count1);
				cell2.setBorder(Border.ALL, BorderLineStyle.THIN,
						jxl.format.Colour.BLACK);
				cell2.setAlignment(Alignment.CENTRE);
				cell2.setVerticalAlignment(VerticalAlignment.CENTRE);

				// TOP
				WritableFont count3 = new WritableFont(
						WritableFont.createFont("微软雅黑"), 18);
				WritableCellFormat cell3 = new WritableCellFormat(count3);
				cell3.setAlignment(Alignment.CENTRE);
				cell3.setVerticalAlignment(VerticalAlignment.CENTRE);

				sheet.setColumnView(0, 5);
				sheet.setColumnView(1, 20);
				sheet.setColumnView(2, 15);
				sheet.setColumnView(3, 15);
				sheet.setColumnView(4, 15);
				sheet.mergeCells(0, 0, 9, 0);
				sheet.setRowView(0, 900);

				Label dept = new Label(1, 1, "" + "所属单位:", cell4);
				Label dept1 = new Label(2, 1, user.getDept().getDeptName(),
						cell4);
				Label labelxh = new Label(0, 2, "序号", cell2);
				Label labelyh=null;
				if (type.equals("macgrid")) {
					 labelyh = new Label(1, 2, "设备名称", cell2);
				}else{
					 labelyh = new Label(1, 2, "收款方", cell2);
				}
				Label labelddywmc = new Label(2, 2, "订单数量", cell2);
				Label labelddsl = new Label(3, 2, "合计", cell2);
				//Label labeldj = new Label(3, 2, "单价", cell2);
				//Label labelzj = new Label(4, 2, "总计", cell2);
				

				Label head = new Label(0, 0, "订单信息数据统计表", cell3);

				// Add
				sheet.addCell(dept);
				sheet.addCell(dept1);
				sheet.addCell(labelxh);
				sheet.addCell(labelyh);
				sheet.addCell(labelddywmc);
				sheet.addCell(labelddsl);
				//sheet.addCell(labeldj);
				//sheet.addCell(labelzj);
				sheet.addCell(head);

				for (int i = 0; i <= ordercount.size() + 1; i++) {
					// 设置列宽
					sheet.setRowView(i + 1, 450);
				}
				// 循环写入表格信息
				for (int i = 0; i < ordercount.size(); i++) {
					Label labelxh_i = new Label(0, i + 3, i + 1 + "", cell);
					Label labelddywmc_i = new Label(1, i + 3, ordercount.get(i).getName()
							+ "", cell);
					Label labelddsl_i = new Label(2, i + 3, ordercount.get(i)
							.getCount().toString(), cell);
					Label labeldj_i = new Label(3, i + 3, ordercount.get(i).getTotal()+"", cell);
//					Label labelzj_i = new Label(4, i + 3,
//							ordercount.get(i).getTotal().toString()
//							+ "", cell);

					sheet.addCell(labelxh_i);
					sheet.addCell(labelddywmc_i);
					sheet.addCell(labelddsl_i);
					sheet.addCell(labeldj_i);
					//sheet.addCell(labelzj_i);
				}
				wwb.write();
				wwb.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return now_date;
	}

}
	
	





