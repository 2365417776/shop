package com.yuan.tools;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVFileGenerator {
    /**
     *
     * @param head 文件头
     * @param dataList 数据列表
     * @param outPutPath 文件输出路径
     * @param fileName 文件名
     * @return 文件
     */
    public static File createCSVFile(List<Object> head, List<List<Object>> dataList, String outPutPath, String fileName){
        File csvFile = null;
        BufferedWriter csvWriter = null;
        try{
            csvFile = new File(outPutPath + File.separator + fileName + ".csv");
            File parent = csvFile.getParentFile();
            if(parent != null && !parent.exists()){
                parent.mkdirs();
            }
            csvFile.createNewFile();

            //GB2312正确读取分隔符
            csvWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GBK"), 1024);
            //写入文件头部
            writeRow(head, csvWriter);
            //写入文件内容
            for (List<Object> row : dataList){
                writeRow(row, csvWriter);
            }
            csvWriter.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                csvWriter.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return csvFile;
    }
    private static void writeRow(List<Object> row, BufferedWriter csvWriter)throws IOException {
        //写入文件头部
        for (Object data : row){
            StringBuffer sb = new StringBuffer();
            String rowStr = sb.append("\"").append(data).append("\",").toString();
            csvWriter.write(rowStr);
        }
        csvWriter.newLine();
    }
    public static void main(String[] args) {
        List<Object> exportData = new ArrayList<Object>();
        exportData.add("第一列");
        exportData.add("第二列");
        exportData.add("第三列");
        List<List<Object>> datalist = new ArrayList<List<Object>>();
        List<Object> data=new ArrayList<Object>();
        data.add("111");
        data.add("222");
        data.add("333");
        List<Object> data1=new ArrayList<Object>();
        data1.add("444");
        data1.add("555");
        data1.add("\t2020-09-16 01:15:16\t");
        datalist.add(data);
        datalist.add(data1);
        String path = "C:/Users/23654/Desktop/exportCsv/";
        String fileName = "文件导出";

        File file = createCSVFile(exportData, datalist, path, fileName);
        String fileName2 = file.getName();
        System.out.println("文件名称：" + fileName2);
    }
}
