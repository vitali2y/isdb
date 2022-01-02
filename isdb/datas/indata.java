/**
 * indata.java
 * ISDBj
 */

package isdb.isdb;

import java.util.Hashtable;

/**
 * Вхідні дані, отримані після HTTP операції.
 * @version 1.0 final, 5-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class indata
{
    /** Хеш-таблиця для збереження вхідних даних */
    private Hashtable hashInData;

    /**
     * Конструктор.
     */
    public indata ()
    {
        hashInData = new Hashtable ();
    }

    /**
     * Одержання вхідних HTTP параметрів.
     * @param req потік вхідних HTTP параметрів
     */
    public void getData (javax.servlet.http.HttpServletRequest req)
    {
        int iI;
        String strKey;
        String strVal;
        String strTmp;
        String strValues [];
        java.util.Enumeration enumPars = req.getParameterNames ();
        while (enumPars.hasMoreElements ())
        {
            strKey = (String) enumPars.nextElement ();
            strValues = (String []) req.getParameterValues (strKey);
            iI = 0;
            while (iI < strValues.length)                // сканування вхідних значеннь запит. даних або параметрів форм
            {
                strVal = strValues [iI++];
                if (String.valueOf (strVal) == "null")
                    strVal = isdb.miscs.dclrs.OBJ_NULL;
                strTmp = getVal (strKey);
                if (String.valueOf (strTmp) == "null")  // вже існує такий параметр?
                    strTmp = strVal;
                else
                {
                    if (strVal.equals (isdb.miscs.dclrs.OBJ_NULL) ||
                            strVal.equals (strTmp));
                    else
                        strTmp += "," + strVal;             // формування багаторазового значення
                }
                setVal (strKey, strTmp);
            }
        }
    }

    /**
     * Збереження значення в глобальному сховище даних.
     * @param oKey назва зберегаемого поля
     * @param oValue значення зберегаемого поля
     */
    public void setVal (Object oKey, Object oValue)
    {
        hashInData.put (oKey, oValue);
    }

    /**
     * Повернення значення з глобального сховища даних.
     * @param oKey назва зберегаемого поля
     * @return значення поля
     */
    public String getVal (Object oKey)
    {
        return (String) hashInData.get (oKey);
    }

    /**
     * Відображення об'екта в текстовій формі.
     * @return параметри об'екту в текстовій формі
     */
    public String toString ()
    {
        String strTmp = "\nindata:\n";
        String strKey = null;
        java.util.Enumeration enumVals = hashInData.keys ();
        while (enumVals.hasMoreElements ())
        {
            strKey = (String) enumVals.nextElement ();
            strTmp += strKey + "=" + (String) hashInData.get (strKey) + "\n";
        }
        return strTmp;
    }
}

