package org.luvx.boot.web.entity.validate.custom;

import java.util.List;

import com.google.common.collect.Lists;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;
import org.luvx.boot.web.entity.validate.ValidationVo;
import org.luvx.boot.web.entity.validate.ValidationVo.AddGroup;
import org.luvx.boot.web.entity.validate.ValidationVo.UpdateGroup;

/**
 * https://blog.csdn.net/f641385712/article/details/99725482
 */
public class VoProvider implements DefaultGroupSequenceProvider<ValidationVo> {
    @Override
    public List<Class<?>> getValidationGroups(ValidationVo vo) {
        List<Class<?>> result = Lists.newArrayList(ValidationVo.class);
        if (vo == null) {
            return result;
        }
        Integer id = vo.getId();
        result.add(
                id != null ? UpdateGroup.class : AddGroup.class
        );
        return result;
    }
}
