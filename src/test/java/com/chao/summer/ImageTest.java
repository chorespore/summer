package com.chao.summer;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class ImageTest {

    @Test
    public void cutImage() {
        ImgUtil.cut(
                FileUtil.file("C:\\Users\\Chao\\Desktop\\ocr\\id1.PNG"),
                FileUtil.file("C:\\Users\\Chao\\Desktop\\ocr\\id1CUT.PNG"),
                new Rectangle(200, 200, 100, 100)//裁剪的矩形区域
        );
    }
}
