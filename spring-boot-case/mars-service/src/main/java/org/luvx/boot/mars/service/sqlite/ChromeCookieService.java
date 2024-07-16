package org.luvx.boot.mars.service.sqlite;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.luvx.boot.mars.dao.config.SqliteConfig;
import org.luvx.coding.common.CallCmd;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChromeCookieService {
    private String password = "";

    // @Resource(name = "sqliteJdbcTemplate")
    // private JdbcTemplate jdbcTemplate;

    private final JdbcTemplate jdbcTemplate = SqliteConfig.sqliteUrl(STR."\{SqliteConfig.SQLITE_HOME}/Cookies");

    public String getCookieStrByHost(@Nonnull Collection<String> hosts, @Nullable String name) {
        String cookie = "";
        try {
            cookie = getCookieByHost(hosts, name).entrySet().stream()
                .map(e -> STR."\{e.getKey()}=\{e.getValue()}")
                .collect(Collectors.joining("; "));
        } catch (Exception ignore) {
        }
        return cookie;
    }

    public Map<String, String> getCookieByHost(@Nonnull Collection<String> hosts, @Nullable String name) throws Exception {
        String sql = STR."""
                select *
                from cookies
                where true
                  and host_key in (:hosts)
                  \{StringUtils.isEmpty(name) ? "" : STR."and name = '\{name}'"}
                order by host_key, name
                ;
                """;
        Map<String, Object> args = Map.of("hosts", hosts);
        NamedParameterJdbcTemplate givenParamJdbcTemp = new NamedParameterJdbcTemplate(jdbcTemplate);
        List<Map<String, Object>> maps = givenParamJdbcTemp.queryForList(sql, args);
        byte[] masterKey = masterKey();
        Map<String, String> result = Maps.newHashMapWithExpectedSize(maps.size());
        for (Map<String, Object> map : maps) {
            String host_key = MapUtils.getString(map, "host_key"), _name = MapUtils.getString(map, "name");
            byte[] encryptedCookie = (byte[]) map.get("encrypted_value");
            byte[] byEnd = decrypt(masterKey, encryptedCookie, null);
            String s = new String(byEnd);
            result.put(_name, s);
            // System.out.println(STR."\{host_key}|\{_name}|\{s}");
        }
        return result;
    }

    private byte[] decrypt(byte[] masterKey, byte[] encryptedCookie, byte[] iv) throws Exception {
        if (encryptedCookie.length < 3) {
            return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        byte[] ciphertextTag = Arrays.copyOfRange(encryptedCookie, 3, encryptedCookie.length);
        if (ciphertextTag.length < 16 || ciphertextTag.length % 16 != 0) {
            return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        // Decrypt
        if (ArrayUtils.isEmpty(iv)) {
            iv = new byte[]{32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32};
        }
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(masterKey, "AES"), new IvParameterSpec(iv));
        return cipher.doFinal(ciphertextTag);
    }

    private byte[] masterKey() throws Exception {
        if (StringUtils.isEmpty(password)) {
            String[] array = {"security", "find-generic-password", "-wa", "Microsoft Edge"};
            List<String> result = CallCmd.callShell(array);
            if (CollectionUtils.isEmpty(result)) {
                throw new RuntimeException();
            }
            password = result.getFirst();
        }

        String salt = "saltysalt";
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 1003, 128);
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey secretKey = f.generateSecret(spec);
        return secretKey.getEncoded();
    }
}
