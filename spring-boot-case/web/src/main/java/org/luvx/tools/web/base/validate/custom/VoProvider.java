package org.luvx.tools.web.base.validate.custom;

import com.google.common.collect.Lists;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;
import org.luvx.tools.web.base.validate.ValidationVo;
import org.luvx.tools.web.base.validate.ValidationVo.AddGroup;
import org.luvx.tools.web.base.validate.ValidationVo.UpdateGroup;

import java.util.List;

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
