/**
 * reportdata.java
 * ISDBj
 */

package isdb.datas;

import java.util.Hashtable;
import java.util.Enumeration;

/**
 * Об'ект, який формує звіти об'ектів БД.
 * <P>Можливі два типа звітів:
 * <UL>
 * <LI>з вертикальним розміщенням (по замовчанню);
 * <LI>з горізонтальним розміщенням.
 * </UL>
 * @version 1.0 final, 16-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class reportdata
{
    /** Тип розміщення звіту: вертикальне */
    public static int TYPE_RPT_VERT = 1;
    /** Тип розміщення звіту: горізонтальне */
    public static int TYPE_RPT_HORZ = 2;

    private static String PAR_TTL = isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + "TTL";
    private static String PAR_SUBTTL = isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + "SUBTTL";
    private static String PAR_SIZE = isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + "SIZE";
    private static String PAR_SELSIZE = isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + "SELSIZE";
    private static String PAR_COL = isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + "COL";
    private static String PAR_RPT = isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + "RPT";
    private static String PAR_CRIT = isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + "CRIT";
    /** Встановлен чі ні режим тестування? (по замовчанню: false - ні) */
    private static boolean MODE_DEBUG = false;

    /** Поточний номер рядка  */
    private int iRow;
    /** Поточний номер колонки  */
    private int iColumn;
    /** Поточний тип звіту  */
    private int iReportType;
    /** Поточний розмір фонта шапки звіту */
    private int iHeadFontSize;
    /** Поточний розмір фонта тексту звіту */
    private int iTextFontSize;
    /** Поточний кольор шапки */
    private String strColorTitle;
    /** Поточний кольор нумерації полів звіту */
    private String strColorNumb;
    /** Поточний кольор полів звіту */
    private String strFieldColor;
    /** Дані звіта об'екта */
    private Hashtable hashReport;
    /** Друкувати ітоговий запис */
    private boolean bWithSummary;

    /**
     * Конструктор.
     * @param strReportTitle назва звіту
     * @param iReportType тип звіту
     * @see #TYPE_RPT_VERT TYPE_RPT_VERT - вертикальне розміщення
     * @see #TYPE_RPT_HORZ TYPE_RPT_HORZ - горізонтальне розміщення
     */
    public reportdata (String strReportTitle, int iReportType)
    {
        hashReport = new Hashtable ();
        hashReport.put (PAR_RPT, strReportTitle);
        this.iReportType = iReportType;
        this.strColorTitle = "000090";
        this.strColorNumb = "800080";
        this.strFieldColor = "000000";
        this.iHeadFontSize = 3;
        this.iTextFontSize = 2;
        this.iColumn = 0;
        this.iRow = 0;
        this.bWithSummary = true;
    }

    /**
     * Встановлення розміру фонта шапки звіту.
     * @param iFontSize розмір фонта шапки звіту
     */
    public void setHeadFontSize (int iFontSize)
    {
        this.iHeadFontSize = iFontSize;
    }

    /**
     * Встановлення розміру фонта тексту звіту.
     * @param iFontSize розмір фонта тексту звіту
     */
    public void setTextFontSize (int iFontSize)
    {
        this.iTextFontSize = iFontSize;
    }

    /**
     * Конструктор.
     * <P>Тип звіту - вертикальне розміщення (по замовчанню).
     * @param iReportTitle назва звіту
     */
    public reportdata (String strReportTitle)
    {
        this (strReportTitle, TYPE_RPT_VERT);
    }

    /**
     * Одержання назви звіту.
     * @return назва звіту
     */
    public String getTitle ()
    {
        return (String) hashReport.get (PAR_RPT);
    }

    /**
     * Встановлення відображення звіту без ітогового запису.
     * <P>По замовчанню - з ітоговим записом.
     */
    public void setWithoutSummary ()
    {
        this.bWithSummary = false;
    }

    /**
     * Створення нового рядка для звіту з горізонтальним розміщенням.
     * @param strTitle назва рядка
     * @param iSizeRow розмір рядка
     */
    public void createRow (String strTitle, int iSizeRow)
    {
        ++this.iRow;
        hashReport.put (PAR_TTL + new Integer (this.iRow).toString (), strTitle);
        hashReport.put (PAR_SIZE + new Integer (this.iRow).toString (), new Integer (iSizeRow));
    }

    /**
     * Створення нової колонки для звіту з вертікальним розміщенням.
     * @param strTitle назва колонки
     * @param iSizeColumn розмір колонки
     */
    public void createColumn (String strTitle, int iSizeColumn)
    {
        ++this.iColumn;
        hashReport.put (PAR_TTL + new Integer (this.iColumn).toString (), strTitle);
        hashReport.put (PAR_SIZE + new Integer (this.iColumn).toString (), new Integer (iSizeColumn));
    }

    /**
     * Одержання кількості рядків.
     * @return кількість колонок
     */
    public int getNumberOfRows ()
    {
        return this.iRow;
    }

    /**
     * Одержання кількості колонок.
     * @return кількість колонок
     */
    public int getNumberOfColumns ()
    {
        return this.iColumn;
    }

    /**
     * Встановлення критерія вибірки звіта.
     * @param iPos колонка/рядок звіта, як(ій/ому) відповідає критерій вибірки
     * @param strSelection критерій вибірки звіта в форматі SQL
     * @param iSelectionSize реальна кількість використовуемих колонок
     */
    public void setModeData (int iPos, String strSelection, int iSelectionSize)
    {
        hashReport.put (PAR_CRIT + new Integer (iPos).toString (), strSelection);
        if (iSelectionSize != 0) // встановлена кількість використовуемих колонок?
            hashReport.put (PAR_SELSIZE + new Integer (iPos).toString (), new Integer (iSelectionSize).toString ());
    }

    /**
     * Встановлення критерія вибірки для всіх полів звіта.
     * @param iPos колонка/рядок звіта, як(ій/ому) відповідає критерій вибірки
     * @param strSelection критерій вибірки звіта в форматі SQL
     * @see #setModeData (int, String, int)
     */
    public void setModeData (int iPos, String strSelection)
    {
        setModeData (iPos, strSelection, 0);
    }

    /**
     * Встановлення критерія вибірки для всіх полів звіта.
     * @param strSelection критерій вибірки звіта в форматі SQL
     * @see #setModeData (int, String, int)
     */
    public void setModeData (String strSelection)
    {
        setModeData (0, strSelection, 0);
    }

    /**
     * Одержання критерія вибірки звіта.
     * @param iPos колонка/рядок звіта, як(ій/ому) відповідає критерій вибірки
     * @return критерій вибірки звіта в форматі SQL
     */
    public String getModeData (int iPos)
    {
        return (String) hashReport.get (PAR_CRIT + new Integer (iPos).toString ());
    }

    /**
     * Одержання критерія вибірки для всіх полів звіта.
     * @return критерій вибірки звіта в форматі SQL
     * @see #getModeData (int)
     */
    public String getModeData ()
    {
        return getModeData (0);
    }

    /**
     * Одержання реальної кількості використовуемих колонок для критерію звіта.
     * @return реальна кількість використовуемих колонок.
     * Якщо не встановлена, то використовуеться кількість полів SQL команди
     * згідно крітерію вибірки
     * @see #setModeData (int, String, int)
     */
    public int getOutputSize (int iPosColumn)
    {
        String strColSize = (String) hashReport.get (PAR_SELSIZE + new Integer (iPosColumn).toString ());
        if (String.valueOf (strColSize) == "null") // встановлена кількість використовуемих колонок?
            return 0;
        else
            return new Integer (strColSize).intValue ();
    }

    /**
     * Встановлення для ячейки потрібного значення.
     * @param iNumberOfColumn номер колонки
     * @param iNumberOfRow номер строки
     * @param strColumn значення ячейки
     */
    public void setVal (int iNumberOfColumn, int iNumberOfRow, String strColumn)
    {
        if (String.valueOf (strColumn) == "null")
            strColumn = isdb.miscs.dclrs.OBJ_NULL;
        hashReport.put (PAR_COL + new Integer (iNumberOfColumn).toString () + new Integer (iNumberOfRow).toString (), strColumn);
        if (this.iRow < iNumberOfRow)     // нове значення нового рядка?
            this.iRow = iNumberOfRow;
    }

    /**
     * Встановлення кольору шапки звіту.
     * @param strColorTitle колор шапки звіту
     */
    public void setColorTitle (String strColorTitle)
    {
        this.strColorTitle = strColorTitle;
    }

    /**
     * Встановлення кольору нумерації полів звіту.
     * @param strColorNumb колор нумерації полів звіту
     */
    public void setColorNumbering (String strColorNumb)
    {
        this.strColorNumb = strColorNumb;
    }

    /**
     * Встановлення кольору полів звіту.
     * @param strFieldColor колор полів звіту
     */
    public void setColorField (String strFieldColor)
    {
        this.strFieldColor = strFieldColor;
    }

    /**
     * Повернення сформованого звіту.
     * @return сформований звіт (в форматі HTML таблиці)
     */
    public String getReport ()
    {
        String strVal = null;
        String strRow = "";
        String strReport = "";

        if (this.iReportType == TYPE_RPT_VERT)  // вертикальне розміщення звіту?
        {
            // назви полів
            for (int iI = 1; iI <= this.iColumn; iI++)
            {
                strRow += isdb.ifaces.htmli.cell (
                            isdb.ifaces.htmli.bold (
                              isdb.ifaces.htmli.font ((String) hashReport.get (PAR_TTL + new Integer (iI).toString ()), this.strColorTitle, this.iHeadFontSize)),
                            ((Integer) hashReport.get (PAR_SIZE + new Integer (iI).toString ())).intValue ());
            }
            strReport = isdb.ifaces.htmli.row (strRow);

            if (MODE_DEBUG)                // тестувал. режим?
            {
                Exception e1 = new Exception ("getReport 1:" + strReport);
                e1.printStackTrace ();
            }

            // номера полів
            strRow = "";
            for (int iI = 1; iI <= this.iColumn; iI++)
            {
                strRow +=
                  isdb.ifaces.htmli.cell (
                    isdb.ifaces.htmli.font (new Integer (iI).toString (), this.strColorNumb, 2),
                    ((Integer) hashReport.get (PAR_SIZE + new Integer (iI).toString ())).intValue ());
            }
            strReport += isdb.ifaces.htmli.row (strRow);

            // значення полів
            for (int iRow = 1; iRow <= this.iRow; iRow++)
            {
                strRow = "";
                for (int iColumn = 1; iColumn <= this.iColumn; iColumn++)
                {
                    strVal = (String) hashReport.get (PAR_COL + new Integer (iColumn).toString () + new Integer (iRow).toString ());
                    if (String.valueOf (strVal) == "null")
                        strVal = "";
                    strRow +=
                      isdb.ifaces.htmli.cell (
                        isdb.ifaces.htmli.font (isdb.ifaces.htmli.value (strVal, this.strFieldColor, false), this.iTextFontSize),
                        ((Integer) hashReport.get (PAR_SIZE + new Integer (iColumn).toString ())).intValue (), "left");               // чорний колір
                }
                strReport += isdb.ifaces.htmli.row (strRow);
            }
        }

        // всього
        if (this.bWithSummary)
            strReport += isdb.ifaces.htmli.row (isdb.ifaces.htmli.cell (isdb.ifaces.htmli.hline () +
                                                isdb.ifaces.htmli.value ("Всього записів: " + new Integer (this.iRow), this.strFieldColor), 10, "left"));
        return strReport;
    }
}

