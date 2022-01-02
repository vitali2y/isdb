/**
 * objlen.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці LENS.
 * @version 1.0betta, 27-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objlen extends isdbobj
{
    // Поля таблиці LENS
    // EWSD
    public static final String COL_DLU = "DLU";
    public static final String COL_SHELF = "SHELF";
    public static final String COL_MODULE = "MODULE";
    public static final String COL_PORT = "PORT";
    // 5ESS
    public static final String COL_LENTYPE = "LENTYPE";
    public static final String COL_LENNUMBER = "LENNUMBER";

    private String strSwitch = null;

    /**
     * Конструктор.
     */
    public objlen ()
    {
        super (isdb.miscs.dclrs.TBL_LEN, isdbobj.COL_DIRNR_ID);
        strSwitch = isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.OPT_SWITCH);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setFrom ("LENS");
        if (strSwitch.equals (isdb.miscs.dclrs.OPTPAR_EWSD))      // яка використовуеться станція?
        {
            oSQLData.setColumn ("DIRNR_ID, DLU||'-'||SHELF||'-'||MODULE||'-'||PORT");
            oSQLData.setOrder ("to_char(DLU, 99)||SHELF||to_char(MODULE, 99)||PORT");
        }
        else
        {
            oSQLData.setColumn ("DIRNR_ID, LENTYPE||'-'||to_char(LENNUMBER,'00000000000')");
            oSQLData.setOrder ("LENTYPE||to_char(LENNUMBER,'00000000000')");
        }
        oDBData.setSQLData (oSQLData);
    }

    /**
     * Проведення транзакції.
     * @param oDBData поточни дані об'екта
     * @param oPoolData дані, яки були раніше зібрани, та збережени в пулі даних
     */
    public void writeData (dbdata oDBData, pooldata oPoolData)
    {
        oDBData.removeVal (objloclentrace.COL_LEN);
        super.writeData (oDBData, oPoolData);
    }

    /**
     * Перевірка можливості збереження кількох унікальних записів об'екта.
     * @return true - можливо зберегати кілька унікальних записів, інакше - false
     */
    public boolean isAllowMultipled ()
    {
        return true;
    }
}

