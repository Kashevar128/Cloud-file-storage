package org.vinogradov.myserver.serverLogic.consoleService;

import org.vinogradov.common.commonClasses.Constants;

import java.util.HashMap;
import java.util.Map;

public class SizeConstants {

    private final Map<String, Long> constantConsole;

    public SizeConstants() {
        this.constantConsole = new HashMap<>();
        constantConsole.put("Gb2", Constants.GB_2);
        constantConsole.put("Gb5", Constants.GB_5);
        constantConsole.put("Gb10", Constants.GB_10);
        constantConsole.put("Gb20", Constants.GB_20);
    }

    public long getConstant(String consoleNameConstant) {
        Long aLong = constantConsole.get(consoleNameConstant);
        if (aLong == null) return 0L;
        return aLong;
    }
}
