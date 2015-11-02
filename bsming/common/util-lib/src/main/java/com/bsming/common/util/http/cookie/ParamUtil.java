package com.bsming.common.util.http.cookie;

/**
 *
 * @author hongtaozhang
 *
 */
public class ParamUtil {


    private ParamUtil() {
    }

    public static int getInt(String stringNumber, int defaultValue) {
        if (stringNumber == null) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(stringNumber);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static int getInt(String stringNumber, int min, int defaultValue) {
        try {
            int returnInt = getInt(stringNumber, defaultValue);

            if (returnInt >= min) {
                return returnInt;
            } else {
                return defaultValue;
            }
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static int getInt(String stringNumber, int min, int max, int defaultValue) {
        try {
            int returnInt = getInt(stringNumber, min, defaultValue);

            if (returnInt <= max) {
                return returnInt;
            } else {
                return defaultValue;
            }
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static long getLong(String stringNumber, long defaultValue) {
        if (stringNumber == null) {
            return defaultValue;
        }

        try {
            return Long.parseLong(stringNumber);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static long getLong(String stringNumber, long min, long defaultValue) {
        try {
            long returnInt = getLong(stringNumber, defaultValue);

            if (returnInt >= min) {
                return returnInt;
            } else {
                return defaultValue;
            }
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static long getLong(String stringNumber, long min, long max, long defaultValue) {
        try {
            long returnInt = getLong(stringNumber, min, defaultValue);

            if (returnInt <= max) {
                return returnInt;
            } else {
                return defaultValue;
            }
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static String getString(String input, String defaultValue) {
        if (input == null) {
            return defaultValue.trim();
        }

        return input.trim();
    }

    public static String getString(String input, String[] validValues, String defaultValue) {
        boolean caseSensitive = false;

        return getString(input, validValues, defaultValue, caseSensitive);
    }

    public static String getString(String input, String[] validValues, String defaultValue, boolean caseSensitive) {
        if (input == null) {
            return defaultValue;
        }
        if (caseSensitive == false) {
            input = input.toLowerCase();

            for (int i = 0, n = validValues.length; i < n; i++) {
                validValues[i] = validValues[i].toLowerCase();
            }
        }
        if (validValues != null) {
            for (int i = 0, n = validValues.length; i < n; i++) {
                if (input.equals(validValues[i])) {
                    return input;
                }
            }
        }
        return defaultValue;
    }


}
