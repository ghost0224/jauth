/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hp.security.jauth.core.exception.AuthException;
import com.hp.security.jauth.core.filter.SystemInit;


/**
 * @author huangyiq
 *
 */
@Service
public class RuleUtil {

    public static Map<String, int[][]> ruleMap = new HashMap<String, int[][]>();
    private Logger log = Logger.getLogger(this.getClass().getName());

    public void initRules() {
        String[] rules = SystemInit.rules.split(",");
        for (String rule : rules) {
            if (validate(rule)) {
                RuleUtil.ruleMap.put(getKey(rule), analyse(rule));
            } else {
            	String message = MessageContext.getMessage("AUTH_ERROR_0", new String[]{"rule", rule});
                log.error(message);
                throw new AuthException("AUTH_ERROR_0", message);
            }
        }
    }

    public boolean validate(String rule) {
        int count1 = 0;
        int count2 = 0;
        if (!rule.startsWith("*")) {
            return false;
        }
        if (rule.endsWith("/")) {
            return false;
        }
        for (int i = 0; i < rule.length(); i++) {
            char c = rule.charAt(i);
            if (c == '.') {
                count1++;
            } else if (c == '!') {
                count2++;
            }
        }
        if (count1 > 1 || count2 > 1) {
            return false;
        }
        return true;
    }
    
    public String getKey(String str) {
        StringBuffer key = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '/' || c == '!' || c == '.') {
                key.append(c);
            }
        }
        if(key.length() == 0)
        	key.append("*");
        return key.toString();
    }

    public int[][] match(String url) {
        String key = getKey(url);
        return ruleMap.get(key);
    }

    public Map<String, String> getControllerAndOperation(String url, String authO) {
        Map<String, String> result = new HashMap<String, String>();
        String controller = "";
        String operation = "";
        int[][] rule = match(url);
        if (null != rule) {
            controller = url;
            if (authO != null && authO.length() > 0) {
                if (controller.lastIndexOf("!") > 0) {
                    controller = controller.substring(0, url.lastIndexOf("!") - 1);
                }
                if (controller.lastIndexOf(".") > 0) {
                    controller = controller.substring(0, url.lastIndexOf(".") - 1);
                }
                operation = authO;
            } else {
                if (rule[0][0] > 0) {
                    for (int i = 0; i < rule[0][0]; i++) {
                        controller = controller.substring(0, controller.lastIndexOf(rule[0][1]));
                    }
                }
                if (rule[1][0] > 0) {
                    operation = url;
                    for (int i = 0; i < rule[1][0]; i++) {
                        operation = operation.substring(0, operation.lastIndexOf(rule[1][1]));
                    }
                    operation = url.substring(operation.length() + 1);
                }
                if (rule[2][0] > 0) {
                    for (int i = 0; i < rule[2][0]; i++) {
                        operation = operation.substring(0, operation.lastIndexOf(rule[2][1]));
                    }
                }
            }
            //if empty set default value
        	if (operation == null || operation.trim().length() == 0) {
        		operation = Constants.DEFAULT_OPERATION;
        	}
            result.put(Constants.AUTH_C, controller);
            result.put(Constants.AUTH_O, operation);
            log.debug("url:" + url + ", controller from rule:" + controller + ", operation from rule: " + operation);
            return result;
        } else {
            log.error("can not find a matched rule!");
            return null;
        }
    }

    public static int[][] analyse(String matcher) {
        int[][] index = new int[][] { { -1, -1 }, { -1, -1 }, { -1, -1 } };
        String target = matcher;
        if (matcher.indexOf(Constants.AUTH_O_MATCH) >= 0) {
            // operation start index
            target = target.substring(target.indexOf(Constants.AUTH_O_MATCH) - 1);
            index[1][1] = target.substring(0, 1).charAt(0);
            index[1][0] = target.length() - target.replaceAll("\\" + (char) index[1][1], "").length();
            // controller end index
            if (matcher.indexOf(".") > 0 && matcher.indexOf(".") < matcher.lastIndexOf(Constants.AUTH_O_MATCH)) {
                matcher = matcher.substring(matcher.indexOf("."));
                index[0][1] = '.';
                index[0][0] = matcher.length() - matcher.replaceAll("\\" + (char) index[0][1], "").length();
            } else {
                index[0][1] = index[1][1];
                index[0][0] = index[1][0];
            }
            if (!matcher.endsWith(Constants.AUTH_O_MATCH)) {
                // operation end index
                target = target.substring(target.indexOf(Constants.AUTH_O_MATCH) + Constants.AUTH_C_MATCH.length());
                index[2][1] = target.substring(0, 1).charAt(0);
                index[2][0] = target.length() - target.replaceAll("\\" + (char) index[2][1], "").length();
            }
        } else {
            if (matcher.indexOf(".") > 0) {
                index[0][1] = '.';
                index[0][0] = matcher.length() - matcher.replaceAll("\\" + (char) index[0][1], "").length();
            }
        }
        return index;
    }

    @Deprecated
    public int[][] analyse(String mather, String a) {
        int[][] index = new int[][] { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } };
        int moduleStart = 0;
        int moduleEnd = 0;
        int operationStart = 0;
        int operationEnd = 0;
        if (mather.indexOf(Constants.AUTH_C_MATCH) >= 0) {
            String target = mather;
            // locate authM's begin and end
            int temp = target.indexOf("/");
            String nextStr = target;
            while (temp >= 0 && !nextStr.startsWith(Constants.AUTH_C_MATCH)) {
                nextStr = nextStr.substring(temp + 1);
                temp = nextStr.indexOf("/");
                moduleStart++;
            }
            if (nextStr.startsWith(Constants.AUTH_C_MATCH)) {
                index[0][1] = 47;
                if (nextStr.length() > 7) {
                    temp = nextStr.indexOf("/");
                    if (temp >= 0) {
                        String tempStr = nextStr.substring(0, temp);
                        if (tempStr.equals(Constants.AUTH_C_MATCH)) {
                            index[1][1] = 47;
                            moduleEnd = moduleStart + 1;
                        } else {
                            tempStr = tempStr.replaceAll(Constants.AUTH_C_MATCH_P, "");
                            tempStr = tempStr.substring(0, 1);
                            if (!tempStr.equals("!")) {
                                moduleEnd = 1;
                                index[1][1] = tempStr.charAt(0);
                            }
                        }
                    } else {
                        if (nextStr.indexOf("!") >= 0) {
                            temp = nextStr.indexOf("!");
                            String tempStr = nextStr.substring(0, temp);
                            if (tempStr.equals(Constants.AUTH_C_MATCH)) {
                                index[1][1] = 33;
                                moduleEnd = 1;
                            } else {
                                tempStr = tempStr.replaceAll(Constants.AUTH_C_MATCH_P, "");
                                tempStr = tempStr.substring(0, 1);
                                if (!tempStr.equals("!")) {
                                    moduleEnd = 1;
                                    index[1][1] = tempStr.charAt(0);
                                }
                            }
                        } else {
                            String tempStr = nextStr.replaceAll(Constants.AUTH_C_MATCH_P, "");
                            tempStr = tempStr.substring(0, 1);
                            if (!tempStr.equals("!")) {
                                moduleEnd = 1;
                                index[1][1] = tempStr.charAt(0);
                            }
                            temp = 0;
                        }
                    }
                } else {
                    moduleEnd = -1;
                    temp = 0;
                }
                nextStr = nextStr.substring(temp);
                // locate authO's begin and end
                if (nextStr.indexOf(Constants.AUTH_O_MATCH) >= 0) {
                    String operationStr = nextStr.substring(1);
                    if (operationStr.startsWith(Constants.AUTH_O_MATCH)) {
                        index[2][1] = nextStr.substring(0, 1).toCharArray()[0];
                        operationStart = moduleEnd;
                        if (operationStr.length() > 7) {
                            temp = operationStr.indexOf("/");
                            if (temp >= 0 && operationStr.substring(0, temp).equals(Constants.AUTH_O_MATCH)) {
                                index[3][1] = 47;
                                operationEnd = operationStart + 1;
                            } else {
                                temp = operationStr.indexOf(".");
                                if (temp >= 0 && operationStr.substring(0, temp).equals(Constants.AUTH_O_MATCH)) {
                                    index[3][1] = '.';
                                    operationEnd = 1;
                                }
                            }
                        } else {
                            operationEnd = -1;
                        }
                    } else {
                        temp = 0;
                        operationStart = moduleStart;
                        while (temp >= 0 && !operationStr.startsWith(Constants.AUTH_O_MATCH)) {
                            operationStr = operationStr.substring(temp + 1);
                            temp = operationStr.indexOf("/");
                            operationStart++;
                            index[2][1] = 47;
                        }
                        if (operationStr.startsWith(Constants.AUTH_O_MATCH)) {
                            if (operationStr.length() > 7) {
                                temp = operationStr.indexOf("/");
                                if (temp >= 0 && operationStr.substring(0, temp).equals(Constants.AUTH_O_MATCH)) {
                                    index[3][1] = 47;
                                    operationEnd = operationStart + 1;
                                } else {
                                    temp = operationStr.indexOf(".");
                                    if (temp >= 0 && operationStr.substring(0, temp).equals(Constants.AUTH_O_MATCH)) {
                                        index[3][1] = '.';
                                        operationEnd = 1;
                                    }
                                }
                            } else {
                                operationEnd = -1;
                            }
                        }
                    }
                } else {
                    operationStart = -1;
                    operationEnd = -1;
                }
            } else {
                moduleStart = -1;
            }
            index[0][0] = moduleStart;
            index[1][0] = moduleEnd;
            index[2][0] = operationStart;
            index[3][0] = operationEnd;
        } else {
            String target = mather;
            int temp = target.indexOf("/");
            String nextStr = target;
            while (temp >= 0 && !nextStr.startsWith(Constants.AUTH_O_MATCH)) {
                nextStr = nextStr.substring(temp + 1);
                temp = nextStr.indexOf("/");
                operationStart++;
            }
            if (nextStr.startsWith(Constants.AUTH_O_MATCH)) {
                index[2][1] = 47;
                if (nextStr.length() > 7) {
                    temp = nextStr.indexOf("/");
                    if (temp >= 0 && nextStr.substring(0, temp).equals(Constants.AUTH_O_MATCH)) {
                        index[3][1] = 47;
                        operationEnd = operationStart + 1;
                    } else {
                        temp = nextStr.indexOf(".");
                        if (temp >= 0 && nextStr.substring(0, temp).equals(Constants.AUTH_O_MATCH)) {
                            index[3][1] = '.';
                            operationEnd = 1;
                        }
                    }
                } else {
                    operationEnd = -1;
                }
                index[0][0] = -1;
                index[1][0] = -1;
                index[2][0] = operationStart;
                index[3][0] = operationEnd;
            } else {
                for (int i = 0; i <= 3; i++) {
                    index[i][0] = -1;
                }
            }
        }
        return index;
    }
}
