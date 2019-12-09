/**
 * 
 */
package com.nest_lot.utils;

import java.io.*;
import java.util.*;

/**
 * 敏感词过滤
 * TTMP算法实现
 * http://www.cnblogs.com/sumtec/archive/2008/02/01/1061742.html
 * Created by 树朾 on 2015/12/28 0028.
 */
public class WordFilter {

    int MAX_WORD_LENGTH = 0;//敏感词最长数
    final int MAX_CHAR = (int) Character.MAX_VALUE;//字符最大值
    final boolean[] first = new boolean[MAX_CHAR];//开始字符
    final boolean[] end = new boolean[MAX_CHAR];//结束字符
    final Map<Integer, Set<String>> words = new HashMap<Integer, Set<String>>();//敏感词

    /**
     * 普通构造函数
     */
    public WordFilter() {
    }

    /**
     * 字典构造函数
     *
     * @param filename 字典名称
     */
    public WordFilter(String filename) {
        loadDictionary(filename);
    }

    /**
     * 字典构造函数
     *
     * @param file 字典文件
     */
    public WordFilter(File file) {
        loadDictionary(file);
    }


    /**
     * 从文件中加载字典
     *
     * @param file 字典文件
     */
    public boolean loadDictionary(File file) {
        try {
            loadDictionary(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }


    /**
     * 从包资源中加载字典
     *
     * @param filename 字典名称
     */
    public void loadDictionary(String filename) {
        loadDictionary(WordFilter.class.getClassLoader().getResourceAsStream(filename));
    }

    /**
     * 从输入流中加载字典
     *
     * @param input 字典输入流 （不管成功失败，流将被关闭）
     */
    public void loadDictionary(InputStream input) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加单个敏感词
     *
     * @param word 敏感词
     */
    public void add(String word) {
        int len = word.length();
        if (len > 0) {
            first[(int) word.charAt(0)] = true;
            end[(int) word.charAt(len - 1)] = true;
            int hash = hash(0, len, word);
            Set<String> values = words.get(hash);
            if (values == null) {
                values = new HashSet<String>();
                words.put(hash, values);
            }
            if (len > MAX_WORD_LENGTH) {
                MAX_WORD_LENGTH = len;
            }
            values.add(word);
        }
    }

    /**
     * 计算哈希
     *
     * @param hash 原始哈希（默认传0）
     * @param len  word的长度
     * @param word 计算哈希原串
     * @return 新的哈希值
     */
    private int hash(int hash, int len, String word) {
        for (int i = len; i-- > 0; ) {
            hash = hash(hash, word.charAt(i));
        }
        return hash;
    }

    /**
     * 可迭代的哈希计算核心
     *
     * @param hash 原始哈希（默认传0）
     * @param ch   迭代值
     * @return 新的哈希值
     */
    private int hash(int hash, Character ch) {
        return 31 * hash + ch;
    }

    /**
     * 替换text中的敏感词为repval
     *
     * @param text   原文本（不变）
     * @param repval 替换值
     * @return 返回替换后的值
     */
    public String replace(String text, Character repval) {
        int tlen = text.length();
        int[] starts = new int[MAX_WORD_LENGTH];
        StringBuilder builder = new StringBuilder(text);
        for (int index = 0, sindex = 0, soffset = 0; index < tlen; index++) {
            char ch = builder.charAt(index);
            if (first[(int) ch]) {
                soffset = (sindex < MAX_WORD_LENGTH) ? 0 : (1 + (sindex % MAX_WORD_LENGTH)) % MAX_WORD_LENGTH;
                starts[sindex++ % MAX_WORD_LENGTH] = index;
            }
            if (end[(int) ch] && sindex > 0) {
                int hash = 0, ssindex = sindex - 1;
                for (int i = index + 1; i-- > starts[soffset]; ) {
                    hash = hash(hash, builder.charAt(i));
                    if (i == starts[ssindex % MAX_WORD_LENGTH]) {
                        ssindex--;
                        if (words.containsKey(hash)) {
                            for (int j = i; j <= index; j++) {
                                builder.setCharAt(j, repval);
                            }
                            break;
                        }
                    }
                }
            }
        }
        return builder.toString();
    }

    /**
     * 验证text中是否有敏感词
     *
     * @param text 原文本
     * @return true 有
     */
    public boolean verification(String text) {
        int tlen = text.length();
        int[] starts = new int[MAX_WORD_LENGTH];
        StringBuilder builder = new StringBuilder(text);
        for (int index = 0, sindex = 0, soffset = 0; index < tlen; index++) {
            char ch = builder.charAt(index);
            if (first[(int) ch]) {
                soffset = (sindex < MAX_WORD_LENGTH) ? 0 : (1 + (sindex % MAX_WORD_LENGTH)) % MAX_WORD_LENGTH;
                starts[sindex++ % MAX_WORD_LENGTH] = index;
            }
            if (end[(int) ch] && sindex > 0) {
                int hash = 0, ssindex = sindex - 1;
                for (int i = index + 1; i-- > starts[soffset]; ) {
                    hash = hash(hash, builder.charAt(i));
                    if (i == starts[ssindex % MAX_WORD_LENGTH]) {
                        ssindex--;
                        if (words.containsKey(hash)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 查找出text中的敏感词
     *
     * @param text 原文本
     * @return 敏感词（去重的）
     */
    public Set<String> judge(String text) {
        int tlen = text.length();
        int[] starts = new int[MAX_WORD_LENGTH];
        Set<String> judges = new HashSet<String>();
        for (int index = 0, sindex = 0, soffset = 0; index < tlen; index++) {
            char ch = text.charAt(index);
            if (first[(int) ch]) {
                soffset = (sindex < MAX_WORD_LENGTH) ? 0 : (1 + (sindex % MAX_WORD_LENGTH)) % MAX_WORD_LENGTH;
                starts[sindex++ % MAX_WORD_LENGTH] = index;
            }
            if (end[(int) ch] && sindex > 0) {
                int hash = 0, ssindex = sindex - 1;
                for (int i = index + 1; i-- > starts[soffset]; ) {
                    hash = hash(hash, text.charAt(i));
                    if (i == starts[ssindex % MAX_WORD_LENGTH]) {
                        ssindex--;
                        if (words.containsKey(hash)) {
                            judges.add(text.substring(i, index + 1));
                            break;
                        }
                    }
                }
            }
        }
        return judges;
    }

}