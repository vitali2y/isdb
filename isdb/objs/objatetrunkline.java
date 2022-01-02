/**
 * objatetrunkline.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці ATETRUNKLINES
 * @version 1.0betta, 18-I-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objatetrunkline extends isdbobj
{

    // Поля таблиці ATETRUNKLINES
    // EWSD
    public static final String COL_ATE_ID = "ATE_ID";
    public static final String COL_ATE = "ATE";
    public static final String COL_TRUNK = "TRUNK";
    // 5ESS
    public static final String COL_TRUNK_ID = "TRUNK_ID";
    public static final String COL_PAIR = "PAIR";

    private String strSwitch = "";

    /**
     * Конструктор
     */
    public objatetrunkline ()
    {
        super (isdb.miscs.dclrs.TBL_ATETRUNKLINE, isdbobj.COL_DIRNR_ID);
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
            oSQLData.setColumn ("DIRNR_ID, TRUNK||' ('||ATE||')'");
            oSQLData.setFrom ("ATETRUNKLINES, ATES");
            oSQLData.setWhere ("ATES.ID = ATE_ID");
            oSQLData.setOrder ("ATE||to_char(TRUNK,9999)");
        }
        else
        {
            oDBData.setModeObj ("select DIRNR_ID, TRUNK||' ('||ATE||')'");
            oSQLData.setFrom ("ATETRUNKLINES, ATETRUNKS, ATES");
            oSQLData.setWhere ("ATES.ID = ATE_ID");
            oSQLData.setWhere ("ATETRUNKS.ID = TRUNK_ID");
            oSQLData.setOrder ("ATE||to_char(TRUNK, 9999)");
        }
        oDBData.setSQLData (oSQLData);
    }

    /**
     * Редагуеми поля об'екта.
     * @param oDBData поточни дані об'екта
     * @param oOutData поточні вихідні дані об'екта
     * @return сформовані редагуеми поля
     */
    public String fields (dbdata oDBData, outdata oOutData)
    {
        if (oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME).equals (isdb.miscs.dclrs.REGIME_INSERT))
            oDBData.setVal (isdb.miscs.dclrs.PAR_IMMEDUPDATE, isdb.miscs.dclrs.YES);
        return super.fields (oDBData, oOutData);
    }

    /**
     * Повернення назви ссилочного об'екта
     * @param strNameRefKey орігінальна назва ссилочного поля об'екта
     * @param oDBData поточни дані об'екта
     * @return назва ссилочного об'екта
     */
    public String getRefObj (String strNameRefKey, dbdata oDBData)
    {
        if (strSwitch.equals (isdb.miscs.dclrs.OPTPAR_5ESS))      // станція - 5ESS?
        {
            if (strNameRefKey.equals ("TRUNK_ID"))
                return "ATETRUNKS";
        }
        return super.getRefObj (strNameRefKey, oDBData);
    }

    /**
     * Проведення транзакції
     * @param oDBData поточни дані об'екта
     * @param oPoolData дані, яки були раніше зібрани, та збережени в пулі даних
     */
    public void writeData (dbdata oDBData, pooldata oPoolData)
    {
        transactiondata oTransactionData = new transactiondata ();
        if (oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME).equals (isdb.miscs.dclrs.REGIME_INSCOMMIT))
            oDBData.setVal (COL_DIRNR_ID, oDBData.getVal (COL_ID));
        super.writeData (oDBData, oPoolData, oTransactionData);
    }
}

