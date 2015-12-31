package org.leopub.seat;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Generator {
    public static String generateExcel(String courseName, Room room, List<Unit> units, String startTime) throws SeatFileException {
        String filename = courseName + "_" + room.getName() + ".xlsx";
        Workbook workbook = new XSSFWorkbook();
        generateSheet(workbook, courseName, room, units, startTime);
        try {
            workbook.write(new FileOutputStream(filename));
            workbook.close();
        } catch (FileNotFoundException e) {
            throw new SeatFileException(e.getMessage());
        } catch (IOException e) {
            throw new SeatFileException(e.getMessage());
        }
        return filename;
    }

    private static void generateSheet(Workbook workbook, String courseName, Room room, List<Unit> units, String startTime) throws SeatFileException {
        Sheet sheet = workbook.createSheet("Sheet1");
        Font itemFont = workbook.createFont();
        itemFont.setFontHeight((short) 350);
        CellStyle itemStyle = workbook.createCellStyle();
        itemStyle.setFont(itemFont);
        itemStyle.setAlignment(CellStyle.ALIGN_CENTER);
        itemStyle.setBorderBottom(CellStyle.BORDER_THIN);
        itemStyle.setBorderTop(CellStyle.BORDER_THIN);
        itemStyle.setBorderLeft(CellStyle.BORDER_THIN);
        itemStyle.setBorderRight(CellStyle.BORDER_THIN);

        Font h1Font = workbook.createFont();
        h1Font.setFontHeight((short) 500);
        CellStyle h1Style = workbook.createCellStyle();
        h1Style.setFont(itemFont);
        h1Style.setAlignment(CellStyle.ALIGN_CENTER);
        h1Style.setBorderBottom(CellStyle.BORDER_NONE);
        h1Style.setBorderTop(CellStyle.BORDER_NONE);
        h1Style.setBorderLeft(CellStyle.BORDER_NONE);
        h1Style.setBorderRight(CellStyle.BORDER_NONE);

        List<String> names = new ArrayList<String>();
        Util.randomizeList(units);
        for (Unit unit : units) {
            List<String> members = unit.getMembers();
            Util.randomizeList(members);
            names.addAll(members);
        }
        List< List<String> > nameTable = generateNameTable(room, names);
        // set column width
        List<String> firstLine = nameTable.get(0);
        int sheetWidth = firstLine.size();
        for (int i = 0; i < sheetWidth; i++) {
            if (firstLine.get(i) == "|") {
                sheet.setColumnWidth(i, 1000);
            } else {
                sheet.setColumnWidth(i, 3000);
            }
        }
        // line 0
        Cell line1Cell = sheet.createRow(0).createCell(0);
        line1Cell.setCellStyle(h1Style);
        line1Cell.setCellValue(courseName);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, sheetWidth - 1));
        // line 1
        Cell line2Cell = sheet.createRow(1).createCell(0);
        line2Cell.setCellStyle(h1Style);
        line2Cell.setCellValue(startTime + "    " + room.getName().replace("-", "教"));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, sheetWidth - 1));
        // line 2
        String unitsName = new String();
        for (Unit unit : units) {
            unitsName += unit.getName() + "(" + unit.getMembers().size() + "人) ";
        }
        Cell line3Cell = sheet.createRow(2).createCell(0);
        line3Cell.setCellValue(unitsName);
        line3Cell.setCellStyle(h1Style);
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, sheetWidth - 1));
        // line 3
        Row row = sheet.createRow(3);
        for (int i = 1; i < sheetWidth - 1; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(itemStyle);
            if (i == 1) {
                cell.setCellValue("讲台");
            }
        }
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, sheetWidth - 2));
        
        // write grid
        itemFont.setFontHeight((short)400);
        itemStyle.setBorderBottom(CellStyle.BORDER_THIN);
        itemStyle.setBorderTop(CellStyle.BORDER_THIN);
        itemStyle.setBorderLeft(CellStyle.BORDER_THIN);
        itemStyle.setBorderRight(CellStyle.BORDER_THIN);
        int y = 4;
        for (List<String> nameRow : nameTable) {
            Row sheetRow = sheet.createRow(y);
            for (int j = 0; j < nameRow.size(); j++) {
                String name = nameRow.get(j);
                if (name != "|" && name != "-") {
                    Cell cell = sheetRow.createCell(j);
                    cell.setCellValue(nameRow.get(j));
                    cell.setCellStyle(itemStyle);
                }
            }
            y++;
        }
        sheet.getPrintSetup().setLandscape(true);
        sheet.setHorizontallyCenter(true);
    }
    public static List< List<String> > generateNameTable(Room room, List<String> names) {
        List< List<String> > res = new ArrayList< List<String> >();
        int interval = 2;
        while(true) {
            int nLeft = names.size();
            for (String rowOfSeat : room.getSeats()) {
                List<String> rowOfPeople = new ArrayList<String>();
                int seatCounter = 0;
                int holderCounter = 0;
                for (char seat : rowOfSeat.toCharArray()) {
                    if (seat == '*') {
                        holderCounter = 0;
                        if (seatCounter == 0) {
                            if (nLeft > 0) {
                                rowOfPeople.add("*");
                                nLeft--;
                            } else {
                                rowOfPeople.add("");
                            }
                        }
                        seatCounter += 1;
                        if (seatCounter > interval) {
                            seatCounter = 0;
                        }
                    } else if (seat == '|') {
                        rowOfPeople.add("|");
                        seatCounter = 0;
                        holderCounter = 0;
                    } else if (seat == '-') {
                        seatCounter = 0;
                        if (holderCounter == 0) {
                            rowOfPeople.add("-");
                        }
                        holderCounter += 1;
                        if (holderCounter > interval) {
                            holderCounter = 0;
                        }
                    }
                }
                res.add(rowOfPeople);
            }
            if (nLeft == 0) {
                break;
            }
            interval -= 1;
        }
        int height = res.size();
        int x = 0;
        int y = 0;
        for (String name : names) {
            while (res.get(y).get(x) != "*") {
                if (y + 1 < height) {
                    y++;
                } else {
                    y = 0;
                    x++;
                }
            }
            if (res.get(y).get(x) == "*") {
                res.get(y).set(x, name);
            }
        }
        return res;
    }
}
