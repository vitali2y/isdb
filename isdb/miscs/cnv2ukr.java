/**
 * cnv2ukr.java
 * ISDBj
 */

package isdb.miscs;

/**
 * Українізація обробляемої інформації
 * @version 1.0final, 16-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class cnv2ukr
{

    /**
     * Кодування строки на українську мову
     * @param strToConvert строка, яку потрібно перекодувати
     * @return сформована строка в українському кодуванні
     */
    public static String cnv2ukr (String strToConvert)
    {
        int iLen = strToConvert.length ();
        char [] strToChar = new char [iLen];
        strToChar = strToConvert.toCharArray ();
        for (int iI = 0; iI < iLen; iI++)
        {
            switch (strToChar [iI])
            {
            case 0xB3:       // укр. мал. і
                strToChar [iI] = 1110;
                break;
            case 0xB2:       // укр. вел. І
                strToChar [iI] = 1030;
                break;
            case 0xBF:       // укр. мал. ї
                strToChar [iI] = 1111;
                break;
            case 0xAF:       // укр. вел. Ї
                strToChar [iI] = 1031;
                break;
            case 0xBA:       // укр. мал. є
                strToChar [iI] = 1108;
                break;
            case 0xAA:       // укр. вел. Є
                strToChar [iI] = 1028;
                break;
            default:
                if ((strToChar [iI] >= 192) & (strToChar [iI] <= 255))
                    strToChar [iI] += (1024 - 176);
            }
        }
        return new String (strToChar);
    }
}

