package com.jianfei.frame.utils;

import java.math.BigInteger;

/**
 *
 */
public class RSAKeyInfo {
    BigInteger modulus;
    BigInteger exponent;

    public RSAKeyInfo(BigInteger modulus, BigInteger exponent) {
        this.modulus = modulus;
        this.exponent = exponent;
    }

    public BigInteger getModulus() {
        return modulus;
    }

    public BigInteger getExponent() {
        return exponent;
    }
}