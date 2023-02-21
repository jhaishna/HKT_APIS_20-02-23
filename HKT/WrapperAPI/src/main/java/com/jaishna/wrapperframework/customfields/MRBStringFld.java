package com.jaishna.wrapperframework.customfields;

import com.portal.pcm.StrField;

public class MRBStringFld extends StrField {
        private static MRBStringFld a;
        public final int id = 906;
        public static String name;

    public MRBStringFld(int i, int i1, String name) {
        super(i,i1,name);
    }

    public static MRBStringFld getInst(int value,String name) {
            a = new MRBStringFld(value, 5,name);
            return a;
        }

}
