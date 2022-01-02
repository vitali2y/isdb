/**
 * deptsales.java
 * ISDBj
 */

package isdb.depts;

import isdb.depts.deptobj;

/**
 * Клас відділу продажу.
 * @version 1.0 final, 27-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class deptsales extends deptobj
{
    /**
     * Конструктор.
     */
    public deptsales ()
    {
        super (isdb.miscs.dclrs.DEPT_SALES);
        putLogo (180, 117);
    }

    /**
     * Повернення назви відділу.
     * @return назва відділу
     */
    public String getName ()
    {
        return "Відділ продажу";
    }
}

