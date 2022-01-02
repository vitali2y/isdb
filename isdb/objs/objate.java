/**
 * objate.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці ATES
 * @version 1.0betta, 13-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objate extends isdbobj
{

    // Поля таблиці ATES
    public static final String COL_ATE = "ATE";

    /**
     * Конструктор
     */
    public objate ()
    {
        super (isdb.miscs.dclrs.TBL_ATE);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        if (String.valueOf (oDBData.getVal (isdb.miscs.dclrs.PAR_NEXTREGIME)) != "null")
        {
            if (oDBData.getVal (isdb.miscs.dclrs.PAR_NEXTREGIME).equals (isdb.miscs.dclrs.REGIME_MAINT))
            {
                // додаткові кнопки навігації
                // створити новий запис
                buttondata oCreateNewRecordButton = new buttondata ();
                oCreateNewRecordButton.setUrl ("isdbform");
                oCreateNewRecordButton.setName (isdb.miscs.dclrs.TITLE_REG_NEW_RECORD);
                oCreateNewRecordButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_ATE);
                oCreateNewRecordButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_INSERT);
                oCreateNewRecordButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
                oCreateNewRecordButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
                oDBData.setButton (oCreateNewRecordButton.getButton (oOutData));
            }
        }
        oSQLData.setColumn ("ID,ATE");
        oSQLData.setFrom ("ATES");
        oSQLData.setOrder ("ATE");
        oDBData.setSQLData (oSQLData);
    }

    /**
     * Повернення ознаки обов'язковості введення знвчення поля об'екта.
     * @param strKey назва поля
     * @param oDBData поточні дані об'екта
     * @return ознака обов'язковості введення нового знвчення
     */
    public boolean isObligatory (String strKey, dbdata oDBData)
    {
        if (strKey.equals (objatecrosspair.COL_ATE_ID)) // ссилочне поле?
            return true;
        return super.isObligatory (strKey, oDBData);
    }
}

