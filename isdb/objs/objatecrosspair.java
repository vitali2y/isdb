/**
 * objatecrosspair.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці ATECROSSPAIRS.
 * @version 1.0 final, 28-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objatecrosspair extends isdbobj
{

    // Поля таблиці ATECROSSPAIRS
    public static final String COL_ATE_ID = "ATE_ID";
    public static final String COL_DIRNR_ID = "DIRNR_ID";
    public static final String COL_CROSSSIDE = "CROSSSIDE";
    public static final String COL_CROSSFRAME = "CROSSFRAME";
    public static final String COL_CROSSROW = "CROSSROW";
    public static final String COL_CROSSPAIR = "CROSSPAIR";

    /**
     * Конструктор
     */
    public objatecrosspair ()
    {
        super (isdb.miscs.dclrs.TBL_ATECROSSPAIR, isdbobj.COL_DIRNR_ID);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setColumn ("ATECROSSPAIRS.DIRNR_ID, ATE||', '||nvl (CROSSSIDE, '')||'-'||CROSSFRAME||'-'||CROSSROW||'-'||CROSSPAIR");
        oSQLData.setFrom ("ATECROSSPAIRS, ATES");
        oSQLData.setWhere ("ATE_ID = ATES.ID");
        oSQLData.setOrder ("ATE||CROSSSIDE||CROSSFRAME||CROSSROW||CROSSPAIR");
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
     * Проведення транзакції.
     * @param oDBData поточни данні об'екта
     * @param
     */
    /*
        public void writeData (dbdata oDBData, pooldata oPoolData)
        {
            transactiondata oTransactionData = new transactiondata ();
            if (oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME).equals (isdb.miscs.dclrs.REGIME_INSCOMMIT))
                oDBData.setVal (COL_DIRNR_ID, oDBData.getVal (COL_ID));

            ///
            Exception e66 = new Exception ("writeData: " + oDBData.toString () + "/" + oPoolData.toString (oDBData.getSession ()));
            e66.printStackTrace ();
            ///

            super.writeData (oDBData, oPoolData, oTransactionData);
        }
    */
}


