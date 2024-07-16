package org.luvx.boot.mars.dao.entity;

import com.github.phantomthief.util.MoreSuppliers.CloseableSupplier;
import io.mybatis.provider.Entity.Column;
import io.mybatis.provider.Entity.Table;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections4.MapUtils;

import java.time.LocalDateTime;
import java.util.Map;

import static com.github.phantomthief.util.MoreSuppliers.lazy;
import static org.luvx.coding.common.util.JsonUtils.fromJson;

@Getter
@Setter
@ToString
@Table("oss_file")
public class OssFile {
    @Column(id = true)
    private long   id;
    private String scope;
    private String file;
    private long   visitCount;
    @Column(updatable = false)
    private String extData = "\"\"";

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private final transient CloseableSupplier<Map<String, Object>> resolved = lazy(() -> fromJson(extData));

    public void setExtData(String extData) {
        this.extData = extData;
        resolved.tryClose();
    }

    @Nullable
    public String getOriginal() {
        return MapUtils.getString(resolved.get(), OssFileKey.original.name());
    }

    @Nullable
    public String getLastVisitTime() {
        return MapUtils.getString(resolved.get(), OssFileKey.lastVisitTime.name());
    }

    public enum OssFileKey {
        original,
        lastVisitTime,
    }
}
