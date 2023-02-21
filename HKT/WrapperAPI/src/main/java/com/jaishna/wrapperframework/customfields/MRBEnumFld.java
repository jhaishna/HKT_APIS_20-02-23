package com.jaishna.wrapperframework.customfields;

import com.portal.pcm.EnumField;

public class MRBEnumFld extends EnumField {
        private static MRBEnumFld a;
        public static final int id = 906;
        public static String name;

    public MRBEnumFld(int i, int i1, String name) {
        super(i,i1,name);
    }

    public static MRBEnumFld getInst(int value,String name) {
                a = new MRBEnumFld(value, 3,name);
            return a;
        }

}
