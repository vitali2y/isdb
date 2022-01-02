/**
 * objsession.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці SESSIONS.
 * @version 1.0 final, 10-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objsession extends isdbobj
{

    // Поля таблиці SESSIONS
    public static final String COL_SESN_DATE = "SESN_DATE";
    public static final String COL_USERNAME = "USERNAME";
    public static final String COL_PHONE_A = "PHONE_A";
    public static final String COL_SESN_DUR = "SESN_DUR";
    public static final String COL_REGION_ID = "REGION_ID";
    public static final String COL_CUSTOMER_ID = "CUSTOMER_ID";
    public static final String COL_CHANNEL_B = "CHANNEL_B";
    public static final String COL_WWW = "WWW";
    public static final String COL_WEBHOSTING = "WEBHOSTING";
    public static final String COL_MAILBOXES = "MAILBOXES";
    public static final String COL_BILLING_ID = "BILLING_ID";

    /**
     * Конструктор.
     */
    public objsession ()
    {
        super (isdb.miscs.dclrs.TBL_SESSION);
    }

    /*
    select to_char(SESN_DATE,'DDMMYY')||' '||to_char(SESN_DATE,'MIHH24SS')||' '||rpad(USER_NAME,8)||' '||rpad(PHONE_A,11)||' '||rpad(CUSTOMER_ID,8)||' '||rpad(REGION_ID,2)||' '||rpad(SESN_DUR,7)||' '||rpad(CHANNEL_B,2)||' '||WWW||' '||WEBHOSTING||' '||rpad(MAILBOXES,2)
    from SESSIONS
    -- where SESSIONSID=124000

    select to_char(SESN_DATE,'DDMMYY')||' '||to_char(SESN_DATE,'MIHH24SS')||' '||rpad(USER_NAME,8)||' '||rpad(PHONE_A,11)||' '||rpad(CUSTOMER_ID,8)||' '||rpad(REGION_ID,2)||' '||rpad(SESN_DUR,7)||' '||rpad(CHANNEL_B,2)||' '||WWW||' '||WEBHOSTING||' '||rpad(MAILBOXES,2)
    from SESSIONS
    where to_char(SESN_DATE,'MMYYYY') like '022000'
    */

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setDistinct ();
        oSQLData.setColumn ("to_char(SESN_DATE,'MM-YYYY'),to_char(SESN_DATE,'MM-YYYY')");
        oSQLData.setFrom (isdb.miscs.dclrs.TBL_SESSION);
        oDBData.setSQLData (oSQLData);
    }

    /**
     * Повернення списку полів об'екта.
     * @param strNameId ідент-р поля об'екта
     * @param oDBData поточні дані об'екта
     * @param oOutData поточні вихідні дані об'екта
     * @return сформований з назвою список полів об'екта (в форматі HTML).
     */
    public String list (String strNameId, dbdata oDBData, outdata oOutData)
    {
        return super.list (COL_SESN_DATE, oDBData, oOutData);
    }

    /**
     * Повернення ознаки обов'язковості введення знвчення поля об'екта.
     * @param strKey назва поля
     * @param oDBData поточні дані об'екта
     * @return ознака обов'язковості введення нового знвчення
     */
    public boolean isObligatory (String strKey, dbdata oDBData)
    {
        return false;
    }

    /**
     * Редагуеми поля об'екта.
     * @param oDBData поточни дані об'екта
     * @param oOutData поточні вихідні дані об'екта
     * @return сформовані редагуеми поля
     */
    public String fields (dbdata oDBData, outdata oOutData)
    {
        // загрузка даних?
        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_UPLOAD))
        {
            sqldata oInternetSessionData = new sqldata ();
            oInternetSessionData.setColumn ("to_char(SESN_DATE,'DDMMYY')||' '" +
                                            "||to_char(SESN_DATE,'MIHH24SS')||' '" +
                                            "||rpad(USERNAME,8)||' '" +
                                            "||rpad(PHONE_A,11)||' '" +
                                            "||rpad(CUSTOMER_ID,8)||' '" +
                                            "||rpad(REGION_ID,2)||' '" +
                                            "||rpad(SESN_DUR,7)||' '" +
                                            "||rpad(CHANNEL_B,2)||' '" +
                                            "||WWW||' '" +
                                            "||WEBHOSTING||' '" +
                                            "||rpad(MAILBOXES,2)");
            oInternetSessionData.setFrom (isdb.miscs.dclrs.TBL_SESSION);
            oInternetSessionData.setWhere ("to_char(SESN_DATE,'MM-YYYY')='" + oDBData.getVal (COL_SESN_DATE) + "'");
            oDBData.setSQLData (oInternetSessionData);
            // return super.fields (oDBData);
        }
        return super.fields (oDBData, oOutData);
    }
}

