package com.ipc.server;

import com.ipc.server.ffmpeg.FFmpegCommandBuilder;
import com.ipc.server.ffmpeg.FFmpegGatherer;
import com.ipc.server.service.IPCDeviceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * @Author 胡学汪
 * @Description FFmpegGatherer测试类
 * @Date 创建于 2021/9/17 15:02
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class FFmpegGathererTest {

    @Autowired
    private IPCDeviceService ipcDeviceService;

    @Autowired
    private FFmpegGatherer fFmpegGatherer;
    @Autowired
    private FFmpegCommandBuilder fFmpegCommandBuilder;

    private String input = "rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov";
//    private String input = "rtsp://admin:qwer@1234@192.168.1.108:554/cam/realmonitor?channel=1&subtype=0";

    /**
     * <a href="http://localhost:8081/websocket/ipc1.html"/>
     */
    @Test
    public void testWebSocket() throws InterruptedException {
        String output = "http://127.0.0.1:8081/data/receive/d1";
        fFmpegGatherer.execute("d1", fFmpegCommandBuilder.buildMpegtsCommand(input, output, "240x160"));
        Thread.sleep(5 * 60 * 1000);
    }

    /**
     * <a href="http://localhost:8081/flv/live.html'/>
     */
    @Test
    public void testNginx() throws InterruptedException {
        String output = "rtmp://192.168.11.129:1935/mylive/1";
        fFmpegGatherer.execute("d1", fFmpegCommandBuilder.buildFlvCommand(input, output));
        Thread.sleep(5 * 60 * 1000);
    }

    /*@Test
    public void add() {
        String namePath = "C:\\Users\\320288\\Desktop\\监控\\副本监控和小智云联动.xlsx";

        try {
            List<Map<String, String>> lists  = loadExcel(namePath);
            for (Map<String, String> map : lists) {
                System.out.println(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    /*public List<Map<String, String>> loadExcel(String filepath) throws Exception {
        List<Map<String, String>> datas = new ArrayList<>();
        //导入.xls与.xlsx判断
        if (filepath.contains(".xlsx")) {//=======导入.xlsx文件
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(filepath));
            XSSFSheet sheet0 = xssfWorkbook.getSheetAt(0);
            int rows0 = sheet0.getPhysicalNumberOfRows();
            for (int i = 2; i < rows0; i++) {
                XSSFRow xssfRow = sheet0.getRow(i);
                // 行不为空
                if (xssfRow != null) {
                    IPCDeviceEntity entity = new IPCDeviceEntity();
                    entity.setDeviceUsername("admin");
                    entity.setDevicePassword("admin123");
                    entity.setDeviceSupplierCode("DH");
                    entity.setDevicePort(554);

                    // 获取到Excel文件中的所有的列
                    int cells0 = xssfRow.getPhysicalNumberOfCells();
                    String value = "";
                    // 遍历列
                    for (int j = 0; j < cells0; j++) {
                        // 获取到列的值
                        XSSFCell cell = xssfRow.getCell(j);
                        if (cell != null) {

                            switch (cell.getCellType()) {
                                case XSSFCell.CELL_TYPE_FORMULA:
                                    break;
                                case XSSFCell.CELL_TYPE_NUMERIC:
                                    value += cell.getNumericCellValue() + ",";
                                    break;
                                case XSSFCell.CELL_TYPE_STRING:
                                    if(j == 1)
                                    {
                                        System.out.println("setDeviceIp" + cell.getStringCellValue());
                                        entity.setDeviceIp(cell.getStringCellValue());
                                    }
                                    if (j == 2)
                                    {
                                        System.out.println("setDeviceId" + cell.getStringCellValue());
                                        entity.setDeviceId(cell.getStringCellValue());
                                    }
                                    value += cell.getStringCellValue() + ",";
                                    break;
                                default:
                                    value += "0";
                                    break;
                            }
                        }
                    }
                    // 将数据插入到mysql数据库中
                    String[] val = value.split(",");
                    if (!"".equals(val[0]) && val[0] != null) {
                        Map map = new HashMap<String, String>();
                        for (int k = 0; k < val.length; k++) {
                            map.put("" + k, val[k]);
                        }
                        datas.add(map);
                    }
                    // 持久化
                    ipcDeviceService.save(entity);
                }

            }
        } else if (filepath.contains(".xls")) { //=======导入.xls文件
            // 创建对Excel工作簿文件的引用
            HSSFWorkbook wookbook = new HSSFWorkbook(new FileInputStream(filepath));
            // 在Excel文档中，第一张工作表的缺省索引是0
            // 其语句为：HSSFSheet sheet = workbook.getSheetAt(0);
            HSSFSheet sheet = wookbook.getSheetAt(0);
            //HSSFSheet sheet = wookbook.getSheet("Sheet1");
            // 获取到Excel文件中的所有行数
            int rows = sheet.getPhysicalNumberOfRows();
            for (int i = 1; i < rows; i++) {
                // 读取左上端单元格
                HSSFRow row = sheet.getRow(i);
                // 行不为空
                if (row != null) {
                    // 获取到Excel文件中的所有的列
                    int cells = row.getPhysicalNumberOfCells();
                    String value = "";
                    // 遍历列
                    for (int j = 0; j < cells; j++) {
                        // 获取到列的值
                        HSSFCell cell = row.getCell(j);
                        if (cell != null) {
                            switch (cell.getCellType()) {
                                case HSSFCell.CELL_TYPE_FORMULA:
                                    break;
                                case HSSFCell.CELL_TYPE_NUMERIC:
                                    value += cell.getNumericCellValue() + ",";
                                    break;
                                case HSSFCell.CELL_TYPE_STRING:
                                    value += cell.getStringCellValue() + ",";
                                    break;
                                default:
                                    value += "0";
                                    break;
                            }
                        }
                    }
                    // 将数据插入到mysql数据库中
                    String[] val = value.split(",");
                    if (!"".equals(val[0]) && val[0] != null) {
                        Map map = new HashMap<String, String>();
                        for (int k = 0; k < val.length; k++) {
                            map.put("" + k, val[k]);
                        }
                        datas.add(map);
                    }
                }
            }
        }
        return datas;
    }*/

}
