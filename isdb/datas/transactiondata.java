/**
 * transactiondata.java
 * ISDBj
 */

package isdb.datas;

import java.util.Hashtable;
import java.util.Enumeration;

/**
 * Додаткові транзакції об'екта
 * @version 1.0final, 16-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class transactiondata
{

    private int iCurrentPreTransaction = 0;
    private int iCurrentPostTransaction = 0;

    /** Додаткові транзакції об'екта після головної транзакції */
    private Hashtable hashPreTransaction = null;

    /** Додаткові транзакції об'екта поперед головної транзакції */
    private Hashtable hashPostTransaction = null;

    /**
     * Конструктор
     * @param
     */
    public transactiondata ()
    {
        hashPreTransaction = new Hashtable ();
        hashPostTransaction = new Hashtable ();
    }

    /**
     * Додавання додаткової транзакції поперед головної
     * @param
     * @return
     */
    public void setPreTransaction (String strAdditTransaction)
    {
        hashPreTransaction.put (new Integer (iCurrentPreTransaction++), strAdditTransaction);
    }

    /**
     * Додавання додаткової транзакції після головної
     * @param
     * @return
     */
    public void setPostTransaction (String strAdditTransaction)
    {
        hashPostTransaction.put (new Integer (iCurrentPostTransaction++), strAdditTransaction);
    }

    /**
     * Повернення всіх pre додаткових транзакцій об'екта
     * @return додаткові pre транзакції об'екта
     */
    public Enumeration getPreTransactions ()
    {
        return hashPreTransaction.elements ();
    }

    /**
     * Повернення всіх post додаткових транзакцій об'екта
     * @return додаткові post транзакції об'екта
     */
    public Enumeration getPostTransactions ()
    {
        return hashPostTransaction.elements ();
    }

    /**
     *
     */
    public String debug ()
    {
        String strTmp = "transactiondata:\npre: ";
        Enumeration enumVals = getPreTransactions ();
        while (enumVals.hasMoreElements ())
        {
            String strKey = (String) enumVals.nextElement ();
            strTmp += strKey + "=" + (String) hashPreTransaction.get (strKey) + "\n";
        }
        strTmp += "\npost: ";
        Enumeration enumVals1 = getPostTransactions ();
        while (enumVals1.hasMoreElements ())
        {
            String strKey = (String) enumVals1.nextElement ();
            strTmp += strKey + "=" + (String) hashPostTransaction.get (strKey) + "\n";
        }
        return strTmp;
    }
}

