/**
 * objperson.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці PERSONS.
 * @version 1.0 final, 28-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objperson extends isdbobj
{
    // Поля таблиці PERSONS
    public static final String COL_PERSON = "PERSON";
    public static final String COL_DEPT_ID = "DEPT_ID";
    public static final String COL_NOTUSED = "NOTUSED";
    public static final String COL_USERNAME = "USERNAME";

    /**
     * Конструктор.
     */
    public objperson ()
    {
        super (isdb.miscs.dclrs.TBL_PERSON);
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
                oCreateNewRecordButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_PERSON);
                oCreateNewRecordButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_INSERT);
                oCreateNewRecordButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
                oCreateNewRecordButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
                oDBData.setButton (oCreateNewRecordButton.getButton (oOutData));
            }
        }
        oSQLData.setColumn ("PERSONS.ID,PERSON");
        oSQLData.setFrom ("DEPTS,PERSONS");
        oSQLData.setWhere ("DEPTS.ID=DEPT_ID");
        oSQLData.setWhere ("PERSONS.ID!=0");
        if (oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT).equals (isdb.miscs.dclrs.DEPT_MNGR))
            oSQLData.setOrder ("PERSON||DEPARTMENT");
        else
        {
            oSQLData.setWhere ("DEPTNAME = '" + oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT) + "'");
            oSQLData.setOrder ("PERSON||DEPARTMENT");
        }
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
        if (strKey.equals (objfirm.COL_PERSON_ID)) // ссилочне поле?
            return true;
        return super.isObligatory (strKey, oDBData);
    }

    /**
     * Редагуеми поля об'екта.
     * @param oDBData поточни дані об'екта
     * @param oOutData поточні вихідні дані об'екта
     * @return сформовані редагуеми поля
     */
    public String fields (dbdata oDBData, outdata oOutData)
    {
        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_INSERT))
            setHideColumn (COL_NOTUSED);
        return super.fields (oDBData, oOutData);
    }
}

