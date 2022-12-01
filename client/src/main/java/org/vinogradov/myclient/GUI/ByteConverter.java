package org.vinogradov.myclient.GUI;

import org.vinogradov.common.commonClasses.Constants;

public class ByteConverter {

    private final long sizeFile;

    public ByteConverter(long sizeFile) {
        this.sizeFile = sizeFile;
    }

    public String getSizeFileStringFormat() {
        double size;
        String item = sizeFile + " b";
        if (sizeFile >= Constants.KB_1 && sizeFile < Constants.MB_1) {
            size = (double) sizeFile / (double) Constants.KB_1;
            item = doubleFormatString(size) + " Kb";
        }
        if (sizeFile >= Constants.MB_1 && sizeFile < Constants.GB_1) {
            size = (double) sizeFile / (double) Constants.MB_1;
            item = doubleFormatString(size) + " Mb";
        }

        if (sizeFile >= Constants.GB_1) {
            size = (double) sizeFile / (double) Constants.GB_1;
            item = doubleFormatString(size) + " Gb";
        }
        return item;
    }

    private String doubleFormatString(double item) {
        return String.format("%.1f", item);
    }
}
