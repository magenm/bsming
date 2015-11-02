package com.bsming.common.util;

import org.apache.poi.xwpf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: magenm
 * Date: 2015/6/27
 * Time: 14:10
 */
public class ExportWordUtil {
    public static void main(String[] args)throws Exception{
        Map<String, Object> params = new HashMap<>();
        params.put("userName", "2014-02-28");
        params.put("userMobile", "100.00");
        params.put("cityCode", "市工单号：111");
        params.put("cityBackTime", "2014-02-28");
        new ExportWordUtil().templateWrite(params,"E:\\非紧急救助表单.docx","E:\\非紧急救助表单1.docx");
    }

    /**
     * 用一个docx文档作为模板，然后替换其中的内容，再写入目标文档中。
     * @throws Exception
     */
    public void templateWrite(Map<String, Object> params,String templatePath,String filePath) throws Exception {
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("reportDate", "2014-02-28");
//        params.put("appleAmt", "100.00");
//        params.put("bananaAmt", "200.00");
//        params.put("totalAmt", "300.00");
//        String filePath = "D:\\word\\template.docx";
        InputStream is = new FileInputStream(templatePath);
        XWPFDocument doc = new XWPFDocument(is);
        //替换段落里面的变量
        this.replaceInPara(doc, params);
        //替换表格里面的变量
        this.replaceInTable(doc, params);
        OutputStream os = new FileOutputStream(filePath);
        doc.write(os);
        this.close(os);
        this.close(is);
    }

    /**
     * 导出word
     *
     * @param resp
     * @param params
     * @param templatePath
     * @throws Exception
     */
    public void templateWrite(HttpServletResponse resp,Map<String, Object> params,String templatePath,String fileName) throws Exception {
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("reportDate", "2014-02-28");
//        params.put("appleAmt", "100.00");
//        params.put("bananaAmt", "200.00");
//        params.put("totalAmt", "300.00");
//        String filePath = "D:\\word\\template.docx";
        InputStream is = new FileInputStream(templatePath);
        XWPFDocument doc = new XWPFDocument(is);
        //替换段落里面的变量
        this.replaceInPara(doc, params);
        //替换表格里面的变量
        this.replaceInTable(doc, params);
//        OutputStream os = new FileOutputStream(filePath);
//        doc.write(os);
//        this.close(os);
//        this.close(is);
        try {
            resp.setContentType("application/msword");
            resp.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") ); //下载用
            resp.getOutputStream();
            OutputStream os = resp.getOutputStream();
            doc.write(os);
            this.close(os);
            this.close(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 替换段落里面的变量
     * @param doc 要替换的文档
     * @param params 参数
     */
    private void replaceInPara(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph para;
        while (iterator.hasNext()) {
            para = iterator.next();
            this.replaceInPara(para, params);
        }
    }

    /**
     * 替换段落里面的变量
     * @param para 要替换的段落
     * @param params 参数
     */
//    private void replaceInPara(XWPFParagraph para, Map<String, Object> params) {
//        List<XWPFRun> runs;
//        Matcher matcher;
//        if (this.matcher(para.getParagraphText()).find()) {
//            runs = para.getRuns();
//            for (int i=0; i<runs.size(); i++) {
//                XWPFRun run = runs.get(i);
//                String runText = run.toString();
//                matcher = this.matcher(runText);
//                if (matcher.find()) {
//                    while ((matcher = this.matcher(runText)).find()) {
//                        runText = matcher.replaceFirst(String.valueOf(params.get(matcher.group(1))));
//                    }
//                    runText = String.valueOf(params.get(runText));
//                    //直接调用XWPFRun的setText()方法设置文本时，在底层会重新创建一个XWPFRun，把文本附加在当前文本后面，
//                    //所以我们不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。
//                    para.removeRun(i);
//                    para.insertNewRun(i).setText(runText);
//                }
//            }
//        }
//    }

    /**
     * 替换段落里面的变量
     * @param para 要替换的段落
     * @param params 参数
     */
    private void replaceInPara(XWPFParagraph para, Map<String, Object> params) {
        List<XWPFRun> runs;
        Matcher matcher;
        String oldText = para.getParagraphText();
        if (this.matcher(oldText).find()) {
            runs = para.getRuns();
            for (int i=0; i<runs.size(); i++) {
                XWPFRun run = runs.get(i);
                String runText = run.toString();
                if (params.containsKey(runText)) {
                    runText = String.valueOf(params.get(runText));
                    //直接调用XWPFRun的setText()方法设置文本时，在底层会重新创建一个XWPFRun，把文本附加在当前文本后面，
                    //所以我们不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。
                    para.removeRun(i);
                    para.insertNewRun(i).setText(runText);
                    //移除 ${ }
                    para.removeRun(i+1);
                    para.removeRun(i-1);
                }
            }
        }
    }


    /**
     * 替换表格里面的变量
     * @param doc 要替换的文档
     * @param params 参数
     */
    private void replaceInTable(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        while (iterator.hasNext()) {
            table = iterator.next();
            rows = table.getRows();
            for (XWPFTableRow row : rows) {
                cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    paras = cell.getParagraphs();
                    for (XWPFParagraph para : paras) {
                        this.replaceInPara(para, params);
                    }
                }
            }
        }
    }

    /**
     * 正则匹配字符串
     * @param str
     * @return
     */
    private Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

    /**
     * 关闭输入流
     * @param is
     */
    private void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭输出流
     * @param os
     */
    private void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
