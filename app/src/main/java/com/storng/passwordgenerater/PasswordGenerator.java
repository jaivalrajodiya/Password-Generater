package com.storng.passwordgenerater;

import java.util.ArrayList;
import java.util.Random;


public final class PasswordGenerator {
    private static final String DIGITS = "0123456789";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String PUNCTUATION = "!@#$%&*()_+-=[]|,./?><";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private boolean useDigits;
    private boolean useLower;
    private boolean usePunctuation;
    private boolean useUpper;

    private PasswordGenerator() {
        throw new UnsupportedOperationException("Empty constructor is not supported.");
    }

    private PasswordGenerator(Builder builder) {
        this.useLower = builder.useLower;
        this.useUpper = builder.useUpper;
        this.useDigits = builder.useDigits;
        this.usePunctuation = builder.usePunctuation;
    }

    
    public static class Builder {
        private boolean useLower = false;
        private boolean useUpper = false;
        private boolean useDigits = false;
        private boolean usePunctuation = false;

        public Builder useLower(boolean z) {
            this.useLower = z;
            return this;
        }

        public Builder useUpper(boolean z) {
            this.useUpper = z;
            return this;
        }

        public Builder useDigits(boolean z) {
            this.useDigits = z;
            return this;
        }

        public Builder usePunctuation(boolean z) {
            this.usePunctuation = z;
            return this;
        }

        public PasswordGenerator build() {
            return new PasswordGenerator(this);
        }
    }

    public String generate(int i) {
        if (i <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(i);
        Random random = new Random(System.nanoTime());
        ArrayList arrayList = new ArrayList(4);
        if (this.useLower) {
            arrayList.add(LOWER);
        }
        if (this.useUpper) {
            arrayList.add(UPPER);
        }
        if (this.useDigits) {
            arrayList.add(DIGITS);
        }
        if (this.usePunctuation) {
            arrayList.add(PUNCTUATION);
        }
        if (arrayList.size() == 0) {
            arrayList.add(UPPER);
        }
        for (int i2 = 0; i2 < i; i2++) {
            String str = (String) arrayList.get(random.nextInt(arrayList.size()));
            sb.append(str.charAt(random.nextInt(str.length())));
        }
        return new String(sb);
    }
}
