package org.vinogradov.myserver.serverLogic.dataBaseService;

import org.apache.commons.codec.digest.DigestUtils;

public class TestDataB {
    public static void main(String[] args) throws Exception {
        String sdf = "Меня зовут Слава!";
        String md5 = DigestUtils.md5Hex(sdf);
        String md6 = DigestUtils.md5Hex(sdf);
        System.out.println(sdf);
        System.out.println(md5);
        System.out.println(md6);

    }
}
