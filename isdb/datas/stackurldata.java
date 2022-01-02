/**
 * stackurldata.java
 * ISDBj
 */

package isdb.datas;

import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Stack;

/**
 * Стек збереження попередніх URL адрес, яки виконувалися користувачем
 * під час проведення транзакцій.
 * @version 1.0 final, 19-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class stackurldata
{
    /** Стек збереження URL адрес */
    private static Hashtable hashStackURL = new Hashtable ();
    /** Встановлен чі ні режим тестування? (по замовчанню: false - ні) */
    private static boolean MODE_DEBUG = true; // false;

    /**
     * Занесення нової URL адреси в стек даних.
     * @param strAppl назва використов. сервлета
     * @param oDBData поточни дані об'екта
     */
    public static void pushURL (String strAppl, dbdata oDBData)
    {
        Stack stackTmpURL = (Stack) hashStackURL.get (oDBData.getSession ());
        if (stackTmpURL == null)    // ще не був створен стек URL адрес для ціеї сесії?
            stackTmpURL = new Stack ();
        dbdata oTmpDBData = oDBData.cloneData ();
        oTmpDBData.setVal (isdb.miscs.dclrs.PAR_FAILAPPL, strAppl);
        stackTmpURL.push (oTmpDBData);
        hashStackURL.put (oDBData.getSession (), stackTmpURL);
        if (MODE_DEBUG)
        {
            Exception e = new Exception ("pushURL: oTmpDBData=" + oTmpDBData.toString () +"\nhashStackURL=" + toString (oDBData) + "\nstackTmpURL=" + stackTmpURL.toString ());
            e.printStackTrace ();
        }
    }

    /**
     * Повернення самої останньої URL адреси.
     * @param oDBData поточни дані об'екта
     * @return остання URL адреса
     */
    public static dbdata popURL (dbdata oDBData)
    {
        Stack stackTmpURL = (Stack) hashStackURL.get (oDBData.getSession ());
        dbdata oTmpDBData = (dbdata) stackTmpURL.pop ();
        hashStackURL.put (oDBData.getSession (), stackTmpURL);
        if (MODE_DEBUG)
        {
            Exception e = new Exception ("popURL: hashStackURL=" + toString (oDBData) + "\noTmpDBData=" + oTmpDBData.toString ());
            e.printStackTrace ();
        }
        return oTmpDBData;
    }

    /**
     * Перевірка заповненості стека URL адрес.
     * @param oDBData поточни дані об'екта
     * @return true - стек URL адрес пустий
     */
    public static boolean isEmpty (dbdata oDBData)
    {
        return ((Stack) hashStackURL.get (oDBData.getSession ())).empty ();
    }

    /**
     * Очіщення стека URL адрес.
     * @param oDBData поточни дані об'екта
     */
    public static void clear (dbdata oDBData)
    {
        /*
                while (isEmpty (oDBData))
                    popURL (oDBData);
        */
        Stack stackTmpURL = (Stack) hashStackURL.get (oDBData.getSession ());
        stackTmpURL = new Stack ();
        hashStackURL.put (oDBData.getSession (), stackTmpURL);
    }

    /**
     * Повернення зберегаемих URL адрес.
     * @param oDBData поточни дані об'екта
     * @return зберегаеми URL адреси
     */
    public static String toString (dbdata oDBData)
    {
        if ((Stack) hashStackURL.get (oDBData.getSession ()) == null)
            return "\nstackurldata is empty!";
        return "\nstackurldata: " + ((Stack) hashStackURL.get (oDBData.getSession ())).toString () + "\n";
    }
}

