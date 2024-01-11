package org.luvx.boot.tools.service.oss;

import com.google.common.io.ByteStreams;
import org.luvx.coding.common.consts.Properties;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;

@Service
public class OssService {
    public static final String IMG_HOME = STR."\{Properties.DIR_USER_HOME}/data/luvx/oss";

    public byte[] img(String scope, String fileName) throws Exception {
        File file = Path.of(IMG_HOME, scope, fileName).toFile();
        InputStream inputStream = new FileInputStream(file);
        return ByteStreams.toByteArray(inputStream);
    }
}
