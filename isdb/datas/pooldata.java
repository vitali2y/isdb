/**
 * pooldata.java
 * ISDBj
 */

package isdb.datas;

import java.util.Hashtable;

/**
 * Пул значеннь об'ектів БД користувачів
 * <P><B>Увага!</B><BR>Використання пулу значеннь об'ектів
 * можливо ТІЛЬКИ з використанням номеру сесії користувача!
 * @version 1.0final, 16-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class pooldata
{
    /** Хеш-таблиця для збереження значеннь об'ектів БД,
     * які приймають участь у транзакції.
     */
    private static Hashtable hashPoolData;

    /**
     * Конструктор.
     */
    public pooldata ()
    {
        hashPoolData = new Hashtable ();
    }

    /**
     * Збереження значення об'екта в пулі даних.
     * <P><B>Увага!</B><P>Нульове значення (oKey=null або oValue == null) не заноситься в пул!
     * @param strSesnId номер сесії
     * @param oKey назва зберегаемого поля
     * @param oValue значення зберегаемого поля
     */
    public void setVal (String strSesnId, Object oKey, Object oValue)
    {
        if ((oKey == null) ||      // нема чого заносити?
                (oValue == null))
            return;
        Hashtable hashLocalPool = (Hashtable) hashPoolData.get (strSesnId);
        if (hashLocalPool == null)            // первинне звернення?
            hashLocalPool = new Hashtable ();
        hashLocalPool.put (oKey, oValue);
        hashPoolData.put (strSesnId, hashLocalPool);
    }

    /**
     * Повернення значення об'екта.
     * @param strSesnId номер сесії
     * @param oKey назва зберегаемого поля
     * @return значення поля об'екта
     */
    public String getVal (String strSesnId, Object oKey)
    {
        Hashtable hashLocalPool = (Hashtable) hashPoolData.get (strSesnId);
        if (hashLocalPool == null)
            return null;
        else
            return (String) hashLocalPool.get (oKey);
    }

    /**
     * Перевірка присутності значення об'екта в пулі.
     * @param strSesnId номер сесії
     * @param oKey назва перевіряемого поля
     * @return true - якщо значення об'екта присутнє в пулі
     */
    public boolean containsKey (String strSesnId, Object oKey)
    {
        Hashtable hashLocalPool = (Hashtable) hashPoolData.get (strSesnId);
        if (hashLocalPool == null)
            return false;
        else
            return hashLocalPool.containsKey ((oKey.toString ().substring (5) + "_ID").toUpperCase ());
    }

    /**
     * Вилучення значення з пула даних.
     * @param strSesnId номер сесії
     * @param oKey назва вилучаемого поля
     */
    public void removeVal (String strSesnId, Object oKey)
    {
        ((Hashtable) hashPoolData.get (strSesnId)).remove (oKey);
    }

    /**
     * Очищення пула об'екта користувача.
     * @param strSesnId номер сесії користувача
     */
    public void clear (String strSesnId)
    {
        hashPoolData.remove (strSesnId);
    }

    /**
     * Відображення об'екта в текстовій формі.
     * @param strSesnId номер сесії користувача
     * @return параметри об'екта в текстовій формі
     */
    public String toString (String strSesnId)
    {
        String strTmp = "pooldata:\n";
        Hashtable hashLocalPool = (Hashtable) hashPoolData.get (strSesnId);
        if (hashLocalPool != null)
        {
            java.util.Enumeration enumVals = hashLocalPool.keys ();
            int i = 0;
            while (enumVals.hasMoreElements ())
            {
                String strKey = (String) enumVals.nextElement ();
                strTmp += strKey + "=" +
                          hashLocalPool.get (strKey) + "\n";
            }
        }
        return strTmp;
    }
}

