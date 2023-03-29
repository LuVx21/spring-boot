package org.luvx.boot.web.validate.custom;

import java.util.List;

import com.google.common.collect.Lists;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;
import org.luvx.boot.web.validate.ValidationVo;
import org.luvx.boot.web.validate.ValidationVo.AddGroup;
import org.luvx.boot.web.validate.ValidationVo.UpdateGroup;

public class VoProvider implements DefaultGroupSequenceProvider<ValidationVo> {
    @Override
    public List<Class<?>> getValidationGroups(ValidationVo vo) {
        List<Class<?>> result = Lists.newArrayList(ValidationVo.class);
        Integer id = vo.getId();
        if (id != null) {
            result.add(UpdateGroup.class);
        }
        result.add(
                id != null ? UpdateGroup.class : AddGroup.class
        );
        return result;
    }
}
