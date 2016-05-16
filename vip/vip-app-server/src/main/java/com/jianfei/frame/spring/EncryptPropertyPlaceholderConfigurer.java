package com.jianfei.frame.spring;


import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.io.Files;

import com.jianfei.frame.utils.RSAKeyInfo;
import com.jianfei.frame.utils.RSAUtil;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.RSAPrivateKeySpec;
import java.util.*;

/**
 * Author: yuwang@iflytek.com
 * Date: 2015/9/7 17:32
 */
public class EncryptPropertyPlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer {

    private Logger logger = LoggerFactory.getLogger(EncryptPropertyPlaceholderConfigurer.class);

    private static final String SPLITTER = "|";

    private RSAPrivateKey privateKey;

    private Properties properties;

    /**
     * 秘钥路径.
     */
    private String privateKeyPath;

    /**
     * 需要加密的属性名.
     */
    private String[] encryptPropNames;

    /**
     * 是否开启加密.
     */
    private boolean enableEncrypt;

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }

    public String[] getEncryptPropNames() {
        return encryptPropNames;
    }

    public void setEncryptPropNames(String[] encryptPropNames) {
        this.encryptPropNames = encryptPropNames;
    }

    public boolean isEnableEncrypt() {
        return enableEncrypt;
    }

    public void setEnableEncrypt(boolean enableEncrypt) {
        this.enableEncrypt = enableEncrypt;
    }

    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        List<Map<String,Object>> encryptPropList = findEncryptProps(propertyValue);
        if (enableEncrypt && encryptPropList.size()>0) {
            try {
                for(int i=0;i<encryptPropList.size();i++) {
                    String decryptValue = decrypt(String.valueOf(encryptPropList.get(i).get("encryptProp")));
                    propertyValue = StringUtils.replace(propertyValue,"$["+encryptPropList.get(i).get("encryptProp")+"]",decryptValue);
                }
            } catch (Exception ex) {
                logger.error("decrypt propertyName:" + propertyName + " exception, properValue is:" + propertyValue);
                return nullValue;
            }
            return propertyValue;
        } else {
            return propertyValue;
        }
    }

    @Override
    protected Properties mergeProperties() throws IOException {
        Properties prop = super.mergeProperties();
        this.properties = prop;
        this.convertProperties(prop);
        return prop;
    }


    /**
     * 判断是否是加密的属性.
     *
     * @param propertyName 属性名称
     */
    private boolean isEncryptProp(String propertyName) {

        if (encryptPropNames == null) {
            return false;
        }

        for (String encryptPropName : encryptPropNames) {
            if (encryptPropName.equals(propertyName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 解密.
     *
     * @param text 密文
     * @return 解密后的文件
     */
    public String decrypt(String text) throws Exception {
        String decryptStr = RSAUtil.decryptByPrivateKey(text, getPrivateKey());
        return decryptStr;
    }

    private RSAPrivateKey getPrivateKey() throws Exception {

        if (privateKey == null) {
            // read public key
            if (Strings.isNullOrEmpty(privateKeyPath)) {
                privateKeyPath = "cloud-service-private.key";
                logger.info("enableEncrypt is enable, privateKeyPath is not specified, use the default path: cloud-service-private.key");
            }

            File privateKeyFile = new File(privateKeyPath);

            if (privateKeyFile.isFile()) {
                try {
                    privateKey = getPrivateKey(privateKeyPath);
                    return privateKey;
                } catch (Exception ex) {
                    logger.error("get private key exception", ex);
                    throw new Exception("get private key failed");
                }
            } else {
                logger.error("enableEncrypt is enable, but private key is not found, please put the correct private key file. config path: {}, resolve path: {}", privateKeyPath, privateKeyFile.getAbsolutePath());
                throw new IOException("private key is not found");
            }
        }

        return privateKey;
    }

    /**
     * 获取解密私钥.
     *
     * @param privateKeyPath 私钥路径
     */
    private RSAPrivateKey getPrivateKey(String privateKeyPath) throws Exception {
        RSAKeyInfo privateKeyInfo = getRASKeyInfo(privateKeyPath);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(privateKeyInfo.getModulus(), privateKeyInfo.getExponent());
        RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 获取秘钥信息.
     *
     * @param keyFilePath 秘钥文件地址
     */
    private static RSAKeyInfo getRASKeyInfo(String keyFilePath) throws Exception {
        List<String> lines = Files.readLines(new File(keyFilePath), Charsets.UTF_8);

        if (lines.size() < 5) {
            throw new Exception("invalid key file");
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 4; i < lines.size(); i++) {
            builder.append(lines.get(i));
        }

        byte[] bytes = new BASE64Decoder().decodeBuffer(builder.toString());
        String keyStr = new String(bytes, "UTF8");

        Iterator<String> data = Splitter.on(SPLITTER).split(keyStr).iterator();
        RSAKeyInfo key = new RSAKeyInfo(new BigInteger(data.next(), 16), new BigInteger(data.next(), 16));
        return key;
    }

    private String getProperty(String key) {
        return (String) this.properties.get(key);
    }

    /**
     * @return
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * @param key
     * @return
     */
    public boolean getBoolean(String key) {
        Boolean bVal = this.getBoolean(key, (Boolean) null);
        if (bVal != null) {
            return bVal.booleanValue();
        } else {
            throw new NoSuchElementException('\'' + key + "\' doesn\'t map to an existing object");
        }
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        return this.getBoolean(key, BooleanUtils.toBooleanObject(defaultValue)).booleanValue();
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     */
    public Boolean getBoolean(String key, Boolean defaultValue) {
        String value = this.getProperty(key);
       /* if (value == null) {
            return defaultValue;
        } else {
            try {
                return PropertyConverter.toBoolean(value);
            } catch (ConversionException var5) {
                throw new ConversionException('\'' + key + "\' doesn\'t map to a Boolean object", var5);
            }
        }*/
        return defaultValue;
    }

    /**
     * @param key
     * @return
     */
    public byte getByte(String key) {
        Byte b = this.getByte(key, (Byte) null);
        if (b != null) {
            return b.byteValue();
        } else {
            throw new NoSuchElementException('\'' + key + " doesn\'t map to an existing object");
        }
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     */
    public byte getByte(String key, byte defaultValue) {
        return this.getByte(key, new Byte(defaultValue)).byteValue();
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     */
    public Byte getByte(String key, Byte defaultValue) {
        String value = this.getProperty(key);
      /*  if (value == null) {
            return defaultValue;
        } else {
            try {
                return PropertyConverter.toByte(value);
            } catch (ConversionException var5) {
                throw new ConversionException('\'' + key + "\' doesn\'t map to a Byte object", var5);
            }
        }*/
        return defaultValue;
    }

    /**
     * @param key
     * @return
     */
    public double getDouble(String key) {
        Double d = this.getDouble(key, (Double) null);
        if (d != null) {
            return d.doubleValue();
        } else {
            throw new NoSuchElementException('\'' + key + "\' doesn\'t map to an existing object");
        }
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     */
    public double getDouble(String key, double defaultValue) {
        return this.getDouble(key, new Double(defaultValue)).doubleValue();
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     */
    public Double getDouble(String key, Double defaultValue) {
        String value = this.getProperty(key);
       /* if (value == null) {
            return defaultValue;
        } else {
            try {
                return PropertyConverter.toDouble(value);
            } catch (ConversionException var5) {
                throw new ConversionException('\'' + key + "\' doesn\'t map to a Double object", var5);
            }
        }*/
        return defaultValue;
    }

    /**
     * @param key
     * @return
     */
    public float getFloat(String key) {
        Float f = this.getFloat(key, (Float) null);
        if (f != null) {
            return f.floatValue();
        } else {
            throw new NoSuchElementException('\'' + key + "\' doesn\'t map to an existing object");
        }
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     */
    public float getFloat(String key, float defaultValue) {
        return this.getFloat(key, new Float(defaultValue)).floatValue();
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     */
    public Float getFloat(String key, Float defaultValue) {
        String value = this.getProperty(key);
      /*  if (value == null) {
            return defaultValue;
        } else {
            try {
                return PropertyConverter.toFloat(value);
            } catch (ConversionException var5) {
                throw new ConversionException('\'' + key + "\' doesn\'t map to a Float object", var5);
            }
        }*/
        return defaultValue;
    }

    /**
     * @param key
     * @return
     */
    public int getInt(String key) {
        Integer i = this.getInteger(key, (Integer) null);
        if (i != null) {
            return i.intValue();
        } else {
            throw new NoSuchElementException('\'' + key + "\' doesn\'t map to an existing object");
        }
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     */
    public int getInt(String key, int defaultValue) {
        Integer i = this.getInteger(key, (Integer) null);
        return i == null ? defaultValue : i.intValue();
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     */
    public Integer getInteger(String key, Integer defaultValue) {
        String value = this.getProperty(key);
       /* if (value == null) {
            return defaultValue;
        } else {
            try {
                return PropertyConverter.toInteger(value);
            } catch (ConversionException var5) {
                throw new ConversionException('\'' + key + "\' doesn\'t map to an Integer object", var5);
            }
        }*/
        return defaultValue;
    }

    /**
     * @param key
     * @return
     */
    public long getLong(String key) {
        Long l = this.getLong(key, (Long) null);
        if (l != null) {
            return l.longValue();
        } else {
            throw new NoSuchElementException('\'' + key + "\' doesn\'t map to an existing object");
        }
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     */
    public long getLong(String key, long defaultValue) {
        return this.getLong(key, new Long(defaultValue)).longValue();
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     */
    public Long getLong(String key, Long defaultValue) {
        String value = this.getProperty(key);
       /* if (value == null) {
            return defaultValue;
        } else {
            try {
                return PropertyConverter.toLong(value);
            } catch (ConversionException var5) {
                throw new ConversionException('\'' + key + "\' doesn\'t map to a Long object", var5);
            }
        }*/
        return defaultValue;
    }

    /**
     * @param key
     * @return
     */
    public short getShort(String key) {
        Short s = this.getShort(key, (Short) null);
        if (s != null) {
            return s.shortValue();
        } else {
            throw new NoSuchElementException('\'' + key + "\' doesn\'t map to an existing object");
        }
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     */
    public short getShort(String key, short defaultValue) {
        return this.getShort(key, new Short(defaultValue)).shortValue();
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     */
    public Short getShort(String key, Short defaultValue) {
        String value = this.getProperty(key);
       /* if (value == null) {
            return defaultValue;
        } else {
            try {
                return PropertyConverter.toShort(value);
            } catch (ConversionException var5) {
                throw new ConversionException('\'' + key + "\' doesn\'t map to a Short object", var5);
            }
        }*/
        return defaultValue;
    }

    /**
     * @param key
     * @return
     */
    public String getString(String key) {
        String sVal= this.getString(key, null);
        return sVal != null ? sVal : null;
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     */
    public String getString(String key, String defaultValue) {
        String value = this.getProperty(key);
        return value instanceof String ? value : defaultValue;
    }

    public List<Map<String,Object>> findEncryptProps(String value){
        List<Map<String,Object>> list = new ArrayList<>();
        while (value.length()>3) {
            Map<String,Object> map = new HashMap<>();
            int start = org.apache.commons.lang.StringUtils.indexOf(value, "$[");
            if (start!= -1 && start < value.length()-1) {
                int end = org.apache.commons.lang.StringUtils.indexOf(value, ']', start);
                String var = value.substring(start+2,end);
                map.put("start",start);
                map.put("end",end);
                map.put("encryptProp",var);
                list.add(map);
                if(end !=-1 && end<value.length()-2){
                    value = value.substring(end+1);
                }else {
                    break;
                }
            }else {
                break;
            }
        }
        return list;
    }
}
