package com.jaishna.wrapperframework.customfields;

import com.portal.pcm.IntField;

public class MRBIntFld extends IntField {
        private static MRBIntFld a;
        public final int id = 915;
        public static String name;

    public MRBIntFld(int i, int i1, String name) {
        super(i,i1,name);
    }

    public static MRBIntFld getInst(int value,String name) {
             a = new MRBIntFld(value, 1,name);
            return a;
    }

}
