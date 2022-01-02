/**
 * sqldata.java
 * ISDBj
 */

package isdb.datas;

import java.util.Hashtable;
import java.util.Enumeration;

/**
 * Поточни дані SQL об'екта (DML команди).
 * @version 1.0 final, 30-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class sqldata
{
    /** Поточний тип SQL команди */
    private static String PAR_CMD = isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + "CMD";
    private static String PAR_DISTINCT = isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + "DISTINCT";
    /** Поточні поля SQL команди */
    private static String PAR_COLUMN = isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + "COLUMN";
    /** Поточне груповання SQL команди */
    private static String PAR_GROUPBY = isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + "GROUP BY";
    /** Поточна сортіровка SQL команди */
    private static String PAR_ORDERBY = isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + "ORDER BY";
    /** Поточний признак сортіровки в убиваюч. порядку SQL команди */
    private static String PAR_DESC = isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + "DESC";
    /** Поточні таблиці вибірки SQL команди */
    private static String PAR_FROM = isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + "FROM";
    /** Поточний кріткрій вібірки SQL команди */
    private static String PAR_CRITERIA = isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + "CRITERIA";
    /** Поточні обмеження вібірки SQL команди */
    private static String PAR_WHERE = isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + "WHERE";

    /** Дані SQL об'екта */
    private Hashtable hashSQLData;

    // Частини SQL команд
    /** SQL: WHERE */
    public static String SQL_WHERE = " where ";
    /** SQL: SELECT */
    public static String SQL_SELECT = "select ";
    /** SQL: UPDATE */
    public static String SQL_UPDATE = "update ";
    /** SQL: INSERT ONTO */
    public static String SQL_INSERT = "insert into ";
    /** SQL: FROM */
    public static String SQL_FROM = " from ";
    /** SQL: ORDER BY */
    public static String SQL_ORDERBY = " order by ";
    /** SQL: GROUP BY */
    public static String SQL_GROUPBY = " group by ";
    /** SQL: AND */
    public static String SQL_AND = " and ";
    /** SQL: IS */
    public static String SQL_IS = " is ";
    /** SQL: SET */
    public static String SQL_SET = " set ";
    /** SQL: DISTINCT */
    public static String SQL_DISTINCT = "distinct ";
    /** SQL: DESC */
    public static String SQL_DESC = " desc";
    /** SQL: NOT */
    public static String SQL_NOT = " NOT";
    /** SQL: LIKE */
    public static String SQL_LIKE = " LIKE ";

    /**
     * Конструктор SQL команди.
     * @param strSQLCmd тип SQL команди
     * @param oDBData поточни дані об'екта
     */
    public sqldata (String strSQLCmd, dbdata oDBData)
    {
        hashSQLData = new Hashtable ();
        if (String.valueOf (strSQLCmd) == "null")
            strSQLCmd = isdb.miscs.dclrs.SQL_SELECT;
        hashSQLData.put (PAR_CMD, strSQLCmd);
        if (oDBData != null)
            hashSQLData.put (PAR_CRITERIA, oDBData.getCriteriaObj ());
        hashSQLData.put (PAR_DESC, "");
    }

    /**
     * Конструктор SQL команди (типу SELECT)
     * @param oDBData поточни дані об'екта
     * @see #sqldata (String, dbdata)
     */
    public sqldata (dbdata oDBData)
    {
        this (null, oDBData);
    }

    /**
     * Конструктор SQL команди (типу SELECT)
     * @see #sqldata (String, dbdata)
     */
    public sqldata ()
    {
        this (null, null);
    }

    /**
     * Встановлення признака уникнення вибірки дубльованих полів
     */
    public void setDistinct ()
    {
        hashSQLData.put (PAR_DISTINCT, SQL_DISTINCT);
    }

    /**
     * Встановлення поля SQL команди
     * @param strColumn назва поля
     */
    public void setColumn (String strCulumn)
    {
        String strVal = (String) hashSQLData.get (PAR_COLUMN);
        // if ((String.valueOf (strVal) == "null") ||
        // strVal.equals (""))
        if (String.valueOf (strVal) == "null")
            strVal = "";
        else
            strVal += ",";
        hashSQLData.put (PAR_COLUMN, strVal += strCulumn);
    }

    /**
     * Встановлення об'екта(ів), з яким(и) буде проводитися робота
     * @param strFrom назва об'екта(ів)
     */
    public void setFrom (String strFrom)
    {
        String strVal = (String) hashSQLData.get (PAR_FROM);
        if ((String.valueOf (strVal) == "null") ||
                strVal.equals (""))
            strVal = "";
        else
            strVal += ",";
        hashSQLData.put (PAR_FROM, strVal += strFrom);
    }

    /**
     * Встановлення обмеження вібірки SQL команди
     * @param strWhere обмеження вібірки
     */
    public void setWhere (String strWhere)
    {
        String strVal = (String) hashSQLData.get (PAR_WHERE);
        if (String.valueOf (strVal) == "null")
            strVal = "";
        else
            strVal += SQL_AND;
        if ((String.valueOf (strWhere) == "null") ||
                strWhere.equals (""));
        else
            hashSQLData.put (PAR_WHERE, strVal += strWhere);
    }

    /**
     * Встановлення крітерія вібірки SQL команди
     * @param strCriteria крітерій вібірки
     */
    public void setCriteria (String strCriteria)
    {
        setWhere (strCriteria);
    }

    /**
     * Встановлення груповання полів SQL команди
     * @param strGroupBy поле, по якому проводиться груповання
     * @param bDesc в убиваючому порядку
     */
    public void setGroup (String strGroupBy)
    {
        String strVal = (String) hashSQLData.get (PAR_GROUPBY);
        if ((String.valueOf (strVal) == "null") ||
                strVal.equals (""))
            strVal = "";
        else
            strVal += ",";
        hashSQLData.put (PAR_GROUPBY, strVal += strGroupBy);
    }

    /**
     * Встановлення сортування SQL команди
     * @param strOrderBy поле, по якому проводиться сортіровка
     * @param bDesc в убиваючому порядку
     */
    public void setOrder (String strOrderBy, boolean bDesc)
    {
        hashSQLData.put (PAR_ORDERBY, strOrderBy);
        if (bDesc)  // сортування в убиваючому порядку?
            hashSQLData.put (PAR_DESC, SQL_DESC);
    }

    /**
     * Встановлення сортування SQL команди
     * @param strOrderBy поле, по якому проводиться сортіровка
     * @see #setOrder (String, boolean)
     */
    public void setOrder (String strOrderBy)
    {
        setOrder (strOrderBy, false);
    }

    /**
     * Одержання сформованої SQL команди
     * @return сформована SQL команда
     */
    public String getData ()
    {
        String strVal = null;
        String strTmp = null;
        strVal = (String) hashSQLData.get (PAR_CMD);
        strTmp = (String) hashSQLData.get (PAR_DISTINCT);
        if ((String.valueOf (strTmp) == "null") ||
                strTmp.equals (""));
        else strVal += strTmp;
        strVal += (String) hashSQLData.get (PAR_COLUMN);
        strVal += SQL_FROM + (String) hashSQLData.get (PAR_FROM);
        strTmp = (String) hashSQLData.get (PAR_WHERE);
        if ((String.valueOf (strTmp) == "null") ||
                strTmp.equals (""));
        else strVal += SQL_WHERE + strTmp;

        ///
        // Exception enew = new Exception ("getData 1: (" + (String) hashSQLData.get (PAR_COLUMN) + "="+ strTmp + ")");
        // enew.printStackTrace ();
        ///

        strTmp = (String) hashSQLData.get (PAR_GROUPBY);
        if (String.valueOf (strTmp) != "null")
            strVal += SQL_GROUPBY + strTmp;
        strTmp = (String) hashSQLData.get (PAR_ORDERBY);
        if (String.valueOf (strTmp) != "null")
            strVal += SQL_ORDERBY + strTmp;
        strVal += (String) hashSQLData.get (PAR_DESC);

        ///
        Exception enew = new Exception ("getData: sql=" + strVal + "\n"+ hashSQLData.toString ());
        enew.printStackTrace ();
        ///

        return strVal;
    }
}

