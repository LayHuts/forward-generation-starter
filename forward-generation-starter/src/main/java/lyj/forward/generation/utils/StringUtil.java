package lyj.forward.generation.utils;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/5/7 16:20
 */
public class StringUtil
{

    /** 下划线 */
    private static final char SEPARATOR = '_';

    /**
     * 驼峰首字符小写
     */
    public static String uncapitalize(String str)
    {
        int strLen;
        if (str == null || (strLen = str.length()) == 0)
        {
            return str;
        }
        return new StringBuffer(strLen).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString();
    }


    /**
     * 将大写字母转换为下划线
     */
    public static String uncapitalizeToUnderLine(String str)
    {
        int strLen;
        str = uncapitalize(str);
        StringBuilder builder = new StringBuilder();
        // 判断字母大写
        for (int i = 0; i < str.length(); i++) {
            // 如果大写转小写
            if (Character.isUpperCase(str.charAt(i))) {
                builder.append("_").append(str.substring(i,i+1).toLowerCase());
            }else {
                builder.append(str.charAt(i));
            }
        }


        return builder.toString();
    }


    public static void main(String[] args)
    {
        System.out.println(uncapitalizeToUnderLine("loginName"));

    }

    /**
     * 下划线转驼峰命名
     */
    public static String toUnderScoreCase(String s)
    {
        if (s == null)
        {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1))
            {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i > 0) && Character.isUpperCase(c))
            {
                if (!upperCase || !nextUpperCase)
                {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            }
            else
            {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }
}
