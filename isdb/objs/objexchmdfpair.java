/**
 * objexchmdfpair.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці EXCHMDFPAIRS
 * @version 1.0 final, 20-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objexchmdfpair extends isdbobj
{

    // Поля таблиці EXCHMDFPAIRS
    // EWSD
    public static final String COL_ATE_ID = "ATE_ID";
    public static final String COL_ATE = "ATE";
    public static final String COL_MDFHORZ = "MDFHORZ";
    public static final String COL_MDFVERT = "MDFVERT";
    public static final String COL_MDFPIN = "MDFPIN";
    // 5ESS
    public static final String COL_MDFSECTION = "MDFSECTION";
    public static final String COL_MDFCOLUMN = "MDFCOLUMN";
    public static final String COL_MDFBLOCKROW = "MDFBLOCKROW";
    public static final String COL_MDFBLOCKCOLUMN = "MDFBLOCKCOLUMN";
    // Загальни
    public static final String COL_MDFROW = "MDFROW";

    private String strSwitch = "";

    /**
    * Конструктор
    */
    public objexchmdfpair ()
    {
        super (isdb.miscs.dclrs.TBL_EXCHMDF, isdbobj.COL_DIRNR_ID);
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
        if (strSwitch.equals (isdb.miscs.dclrs.OPTPAR_EWSD))      // яка використовуеться станція?
        {
            oSQLData.setColumn ("DIRNR_ID, MDFHORZ||'-'||MDFVERT||'-'||MDFPIN||' ('||ATE||')'");
            oSQLData.setFrom ("ATES");
            oSQLData.setFrom ("EXCHMDFPAIRS");
            oSQLData.setWhere ("ATES.ID=ATE_ID");
            oSQLData.setOrder ("to_char(MDFHORZ, 99)||to_char(MDFVERT, 99)||MDFPIN||ATE");
        }
        else
        {
            oSQLData.setColumn ("DIRNR_ID, MDFSECTION||'-'||MDFROW||'-'||MDFCOLUMN||'-'||MDFBLOCKROW||'-'||MDFBLOCKCOLUMN");
            oSQLData.setFrom ("EXCHMDFPAIRS");
            oSQLData.setOrder ("MDFSECTION||'-'||MDFROW||'-'||MDFCOLUMN||'-'||MDFBLOCKROW||'-'||MDFBLOCKCOLUMN");
        }
        oDBData.setSQLData (oSQLData);
    }

    /**
     * Проведення транзакції
     * @param oDBData поточни дані об'екта
     * @param oPoolData дані, яки були раніше зібрани, та збережени в пулі даних
     */
    public void writeData (dbdata oDBData, pooldata oPoolData)
    {
        oDBData.removeVal (objloclentrace.COL_LEN);
        super.writeData (oDBData, oPoolData);
    }
}

