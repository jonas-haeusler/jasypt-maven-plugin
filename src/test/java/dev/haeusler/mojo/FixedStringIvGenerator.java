package dev.haeusler.mojo;

import org.jasypt.iv.StringFixedIvGenerator;

public class FixedStringIvGenerator extends StringFixedIvGenerator {
    public FixedStringIvGenerator() {
        super("0123456789123456789");
    }
}
