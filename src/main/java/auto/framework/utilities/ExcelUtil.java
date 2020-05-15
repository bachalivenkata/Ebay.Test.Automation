package auto.framework.utilities;

import auto.framework.config.ConfigReader;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.util.Hashtable;
import java.util.Iterator;

public class ExcelUtil {

    static Workbook book = null;
    static Sheet sheet;
    static Hashtable dict= new Hashtable();

    static int totalCol;

    public static void InitialiseWorkBook() throws Exception {
        //Creating workbook
        book = WorkbookFactory.create(new File(ConfigReader.GetGlobalProperty("TEST_DATA_PATH")));

        //Getting Sheet at index zero
        sheet = book.getSheetAt(0);

        //Invoke Column Dictionary to store column Names
        ColumnDictionary();
    }

    public static String GetCellData(String columnName, String rowName) throws Exception {
        InitialiseWorkBook();
        int rowNumber = GetRowNumber(rowName);
        String cellData = ReadCell(columnName, rowNumber);
        return cellData;
    }

    //Create Column Dictionary to hold all the Column Names
    private static void ColumnDictionary()
    {
        //Iterate through all the columns in the Excel sheet and store the value in Hashtable
        DataFormatter dataFormatter = new DataFormatter();
        Iterator<Row> rowIt = sheet.rowIterator();
        while (rowIt.hasNext()) {
            Row row = rowIt.next();

            //Iterate over the columns of the current row
            Iterator<Cell> cellIterator = row.cellIterator();

            int col=0;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                String cellValue = dataFormatter.formatCellValue(cell);
                dict.put(cellValue, col);
                col++;
            }
            totalCol = col;
        }
    }

    private static int GetRowNumber(String expRowName){
        Iterator<Row> rowIt = sheet.rowIterator();

        int rowNumber = 0;
        while (rowIt.hasNext()) {

            String actRowName = rowIt.next().getCell(0).getStringCellValue();
            if(actRowName.equals(expRowName)){
                break;
            }
            rowNumber++;
        }
        return rowNumber;
    }

    //Returns the Cell value by taking row and Column values as argument
    private static String ReadCell(int column,int row)
    {
        String cellData="";
        DataFormatter dataFormatter = new DataFormatter();
        try{
            cellData = dataFormatter.formatCellValue(sheet.getRow(row).getCell(column));
        } catch (Exception e) {
            //No need to catch the exception
        }
        return cellData;
    }

    public static String ReadCell(String columnName, int rowNumber)
    {
        return ReadCell(GetCell(columnName), rowNumber);
    }

    //Read Column Names
    private static int GetCell(String colName)
    {
        try {
            int value;
            value = ((Integer) dict.get(colName)).intValue();
            return value;
        } catch (NullPointerException e) {
            return (0);
        }
    }
}
